package org.fol37.quinzainedulivre.web.rest;

import org.fol37.quinzainedulivre.Application;
import org.fol37.quinzainedulivre.domain.Age;
import org.fol37.quinzainedulivre.repository.AgeRepository;

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
 * Test class for the AgeResource REST controller.
 *
 * @see AgeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AgeResourceTest {

    private static final String DEFAULT_LIBELLE = "AAAAA";
    private static final String UPDATED_LIBELLE = "BBBBB";

    private static final Integer DEFAULT_A_PARTIR_DE = 1;
    private static final Integer UPDATED_A_PARTIR_DE = 2;

    @Inject
    private AgeRepository ageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAgeMockMvc;

    private Age age;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AgeResource ageResource = new AgeResource();
        ReflectionTestUtils.setField(ageResource, "ageRepository", ageRepository);
        this.restAgeMockMvc = MockMvcBuilders.standaloneSetup(ageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        age = new Age();
        age.setLibelle(DEFAULT_LIBELLE);
        age.setaPartirDe(DEFAULT_A_PARTIR_DE);
    }

    @Test
    @Transactional
    public void createAge() throws Exception {
        int databaseSizeBeforeCreate = ageRepository.findAll().size();

        // Create the Age

        restAgeMockMvc.perform(post("/api/ages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(age)))
                .andExpect(status().isCreated());

        // Validate the Age in the database
        List<Age> ages = ageRepository.findAll();
        assertThat(ages).hasSize(databaseSizeBeforeCreate + 1);
        Age testAge = ages.get(ages.size() - 1);
        assertThat(testAge.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testAge.getaPartirDe()).isEqualTo(DEFAULT_A_PARTIR_DE);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = ageRepository.findAll().size();
        // set the field null
        age.setLibelle(null);

        // Create the Age, which fails.

        restAgeMockMvc.perform(post("/api/ages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(age)))
                .andExpect(status().isBadRequest());

        List<Age> ages = ageRepository.findAll();
        assertThat(ages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAges() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

        // Get all the ages
        restAgeMockMvc.perform(get("/api/ages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(age.getId().intValue())))
                .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
                .andExpect(jsonPath("$.[*].aPartirDe").value(hasItem(DEFAULT_A_PARTIR_DE)));
    }

    @Test
    @Transactional
    public void getAge() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

        // Get the age
        restAgeMockMvc.perform(get("/api/ages/{id}", age.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(age.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.aPartirDe").value(DEFAULT_A_PARTIR_DE));
    }

    @Test
    @Transactional
    public void getNonExistingAge() throws Exception {
        // Get the age
        restAgeMockMvc.perform(get("/api/ages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAge() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

		int databaseSizeBeforeUpdate = ageRepository.findAll().size();

        // Update the age
        age.setLibelle(UPDATED_LIBELLE);
        age.setaPartirDe(UPDATED_A_PARTIR_DE);

        restAgeMockMvc.perform(put("/api/ages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(age)))
                .andExpect(status().isOk());

        // Validate the Age in the database
        List<Age> ages = ageRepository.findAll();
        assertThat(ages).hasSize(databaseSizeBeforeUpdate);
        Age testAge = ages.get(ages.size() - 1);
        assertThat(testAge.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testAge.getaPartirDe()).isEqualTo(UPDATED_A_PARTIR_DE);
    }

    @Test
    @Transactional
    public void deleteAge() throws Exception {
        // Initialize the database
        ageRepository.saveAndFlush(age);

		int databaseSizeBeforeDelete = ageRepository.findAll().size();

        // Get the age
        restAgeMockMvc.perform(delete("/api/ages/{id}", age.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Age> ages = ageRepository.findAll();
        assertThat(ages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
