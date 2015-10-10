package org.fol37.quinzainedulivre.web.rest;

import org.fol37.quinzainedulivre.Application;
import org.fol37.quinzainedulivre.domain.Editeur;
import org.fol37.quinzainedulivre.repository.EditeurRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the EditeurResource REST controller.
 *
 * @see EditeurResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EditeurResourceTest {

    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";

    @Inject
    private EditeurRepository editeurRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEditeurMockMvc;

    private Editeur editeur;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EditeurResource editeurResource = new EditeurResource();
        ReflectionTestUtils.setField(editeurResource, "editeurRepository", editeurRepository);
        this.restEditeurMockMvc = MockMvcBuilders.standaloneSetup(editeurResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        editeur = new Editeur();
        editeur.setNom(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createEditeur() throws Exception {
        int databaseSizeBeforeCreate = editeurRepository.findAll().size();

        // Create the Editeur

        restEditeurMockMvc.perform(post("/api/editeurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(editeur)))
                .andExpect(status().isCreated());

        // Validate the Editeur in the database
        List<Editeur> editeurs = editeurRepository.findAll();
        assertThat(editeurs).hasSize(databaseSizeBeforeCreate + 1);
        Editeur testEditeur = editeurs.get(editeurs.size() - 1);
        assertThat(testEditeur.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = editeurRepository.findAll().size();
        // set the field null
        editeur.setNom(null);

        // Create the Editeur, which fails.

        restEditeurMockMvc.perform(post("/api/editeurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(editeur)))
                .andExpect(status().isBadRequest());

        List<Editeur> editeurs = editeurRepository.findAll();
        assertThat(editeurs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEditeurs() throws Exception {
        // Initialize the database
        editeurRepository.saveAndFlush(editeur);

        // Get all the editeurs
        restEditeurMockMvc.perform(get("/api/editeurs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(editeur.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void getEditeur() throws Exception {
        // Initialize the database
        editeurRepository.saveAndFlush(editeur);

        // Get the editeur
        restEditeurMockMvc.perform(get("/api/editeurs/{id}", editeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(editeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEditeur() throws Exception {
        // Get the editeur
        restEditeurMockMvc.perform(get("/api/editeurs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEditeur() throws Exception {
        // Initialize the database
        editeurRepository.saveAndFlush(editeur);

		int databaseSizeBeforeUpdate = editeurRepository.findAll().size();

        // Update the editeur
        editeur.setNom(UPDATED_NOM);

        restEditeurMockMvc.perform(put("/api/editeurs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(editeur)))
                .andExpect(status().isOk());

        // Validate the Editeur in the database
        List<Editeur> editeurs = editeurRepository.findAll();
        assertThat(editeurs).hasSize(databaseSizeBeforeUpdate);
        Editeur testEditeur = editeurs.get(editeurs.size() - 1);
        assertThat(testEditeur.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void deleteEditeur() throws Exception {
        // Initialize the database
        editeurRepository.saveAndFlush(editeur);

		int databaseSizeBeforeDelete = editeurRepository.findAll().size();

        // Get the editeur
        restEditeurMockMvc.perform(delete("/api/editeurs/{id}", editeur.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Editeur> editeurs = editeurRepository.findAll();
        assertThat(editeurs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
