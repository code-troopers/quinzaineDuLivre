package org.fol37.quinzainedulivre.web.rest;

import org.fol37.quinzainedulivre.Application;
import org.fol37.quinzainedulivre.domain.Livre;
import org.fol37.quinzainedulivre.repository.LivreRepository;

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
 * Test class for the LivreResource REST controller.
 *
 * @see LivreResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LivreResourceTest {

    private static final String DEFAULT_TITRE = "AAAAA";
    private static final String UPDATED_TITRE = "BBBBB";
    private static final String DEFAULT_SOUS_TITRE = "AAAAA";
    private static final String UPDATED_SOUS_TITRE = "BBBBB";
    private static final String DEFAULT_AUTEURS = "AAAAA";
    private static final String UPDATED_AUTEURS = "BBBBB";
    private static final String DEFAULT_ILLUSTRATEUR = "AAAAA";
    private static final String UPDATED_ILLUSTRATEUR = "BBBBB";
    private static final String DEFAULT_CODE_BARRE = "AAAAA";
    private static final String UPDATED_CODE_BARRE = "BBBBB";
    private static final String DEFAULT_RESUME = "AAAAA";
    private static final String UPDATED_RESUME = "BBBBB";
    private static final String DEFAULT_COMMENTAIRES = "AAAAA";
    private static final String UPDATED_COMMENTAIRES = "BBBBB";

    private static final Boolean DEFAULT_RESERVE_HDV = false;
    private static final Boolean UPDATED_RESERVE_HDV = true;

    private static final Boolean DEFAULT_COUP_COEUR = false;
    private static final Boolean UPDATED_COUP_COEUR = true;

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;
    private static final String DEFAULT_URL_IMAGE = "AAAAA";
    private static final String UPDATED_URL_IMAGE = "BBBBB";

    @Inject
    private LivreRepository livreRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLivreMockMvc;

    private Livre livre;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LivreResource livreResource = new LivreResource();
        ReflectionTestUtils.setField(livreResource, "livreRepository", livreRepository);
        this.restLivreMockMvc = MockMvcBuilders.standaloneSetup(livreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        livre = new Livre();
        livre.setTitre(DEFAULT_TITRE);
        livre.setSousTitre(DEFAULT_SOUS_TITRE);
        livre.setAuteurs(DEFAULT_AUTEURS);
        livre.setIllustrateur(DEFAULT_ILLUSTRATEUR);
        livre.setCodeBarre(DEFAULT_CODE_BARRE);
        livre.setResume(DEFAULT_RESUME);
        livre.setCommentaires(DEFAULT_COMMENTAIRES);
        livre.setReserveHDV(DEFAULT_RESERVE_HDV);
        livre.setCoupCoeur(DEFAULT_COUP_COEUR);
        livre.setPrix(DEFAULT_PRIX);
        livre.setUrlImage(DEFAULT_URL_IMAGE);
    }

    @Test
    @Transactional
    public void createLivre() throws Exception {
        int databaseSizeBeforeCreate = livreRepository.findAll().size();

        // Create the Livre

        restLivreMockMvc.perform(post("/api/livres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livre)))
                .andExpect(status().isCreated());

        // Validate the Livre in the database
        List<Livre> livres = livreRepository.findAll();
        assertThat(livres).hasSize(databaseSizeBeforeCreate + 1);
        Livre testLivre = livres.get(livres.size() - 1);
        assertThat(testLivre.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testLivre.getSousTitre()).isEqualTo(DEFAULT_SOUS_TITRE);
        assertThat(testLivre.getAuteurs()).isEqualTo(DEFAULT_AUTEURS);
        assertThat(testLivre.getIllustrateur()).isEqualTo(DEFAULT_ILLUSTRATEUR);
        assertThat(testLivre.getCodeBarre()).isEqualTo(DEFAULT_CODE_BARRE);
        assertThat(testLivre.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testLivre.getCommentaires()).isEqualTo(DEFAULT_COMMENTAIRES);
        assertThat(testLivre.getReserveHDV()).isEqualTo(DEFAULT_RESERVE_HDV);
        assertThat(testLivre.getCoupCoeur()).isEqualTo(DEFAULT_COUP_COEUR);
        assertThat(testLivre.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testLivre.getUrlImage()).isEqualTo(DEFAULT_URL_IMAGE);
    }

    @Test
    @Transactional
    public void getAllLivres() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get all the livres
        restLivreMockMvc.perform(get("/api/livres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(livre.getId().intValue())))
                .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
                .andExpect(jsonPath("$.[*].sousTitre").value(hasItem(DEFAULT_SOUS_TITRE.toString())))
                .andExpect(jsonPath("$.[*].auteurs").value(hasItem(DEFAULT_AUTEURS.toString())))
                .andExpect(jsonPath("$.[*].illustrateur").value(hasItem(DEFAULT_ILLUSTRATEUR.toString())))
                .andExpect(jsonPath("$.[*].codeBarre").value(hasItem(DEFAULT_CODE_BARRE.toString())))
                .andExpect(jsonPath("$.[*].resume").value(hasItem(DEFAULT_RESUME.toString())))
                .andExpect(jsonPath("$.[*].commentaires").value(hasItem(DEFAULT_COMMENTAIRES.toString())))
                .andExpect(jsonPath("$.[*].reserveHDV").value(hasItem(DEFAULT_RESERVE_HDV.booleanValue())))
                .andExpect(jsonPath("$.[*].coupCoeur").value(hasItem(DEFAULT_COUP_COEUR.booleanValue())))
                .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
                .andExpect(jsonPath("$.[*].urlImage").value(hasItem(DEFAULT_URL_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

        // Get the livre
        restLivreMockMvc.perform(get("/api/livres/{id}", livre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(livre.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.sousTitre").value(DEFAULT_SOUS_TITRE.toString()))
            .andExpect(jsonPath("$.auteurs").value(DEFAULT_AUTEURS.toString()))
            .andExpect(jsonPath("$.illustrateur").value(DEFAULT_ILLUSTRATEUR.toString()))
            .andExpect(jsonPath("$.codeBarre").value(DEFAULT_CODE_BARRE.toString()))
            .andExpect(jsonPath("$.resume").value(DEFAULT_RESUME.toString()))
            .andExpect(jsonPath("$.commentaires").value(DEFAULT_COMMENTAIRES.toString()))
            .andExpect(jsonPath("$.reserveHDV").value(DEFAULT_RESERVE_HDV.booleanValue()))
            .andExpect(jsonPath("$.coupCoeur").value(DEFAULT_COUP_COEUR.booleanValue()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.urlImage").value(DEFAULT_URL_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLivre() throws Exception {
        // Get the livre
        restLivreMockMvc.perform(get("/api/livres/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

		int databaseSizeBeforeUpdate = livreRepository.findAll().size();

        // Update the livre
        livre.setTitre(UPDATED_TITRE);
        livre.setSousTitre(UPDATED_SOUS_TITRE);
        livre.setAuteurs(UPDATED_AUTEURS);
        livre.setIllustrateur(UPDATED_ILLUSTRATEUR);
        livre.setCodeBarre(UPDATED_CODE_BARRE);
        livre.setResume(UPDATED_RESUME);
        livre.setCommentaires(UPDATED_COMMENTAIRES);
        livre.setReserveHDV(UPDATED_RESERVE_HDV);
        livre.setCoupCoeur(UPDATED_COUP_COEUR);
        livre.setPrix(UPDATED_PRIX);
        livre.setUrlImage(UPDATED_URL_IMAGE);

        restLivreMockMvc.perform(put("/api/livres")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livre)))
                .andExpect(status().isOk());

        // Validate the Livre in the database
        List<Livre> livres = livreRepository.findAll();
        assertThat(livres).hasSize(databaseSizeBeforeUpdate);
        Livre testLivre = livres.get(livres.size() - 1);
        assertThat(testLivre.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testLivre.getSousTitre()).isEqualTo(UPDATED_SOUS_TITRE);
        assertThat(testLivre.getAuteurs()).isEqualTo(UPDATED_AUTEURS);
        assertThat(testLivre.getIllustrateur()).isEqualTo(UPDATED_ILLUSTRATEUR);
        assertThat(testLivre.getCodeBarre()).isEqualTo(UPDATED_CODE_BARRE);
        assertThat(testLivre.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testLivre.getCommentaires()).isEqualTo(UPDATED_COMMENTAIRES);
        assertThat(testLivre.getReserveHDV()).isEqualTo(UPDATED_RESERVE_HDV);
        assertThat(testLivre.getCoupCoeur()).isEqualTo(UPDATED_COUP_COEUR);
        assertThat(testLivre.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testLivre.getUrlImage()).isEqualTo(UPDATED_URL_IMAGE);
    }

    @Test
    @Transactional
    public void deleteLivre() throws Exception {
        // Initialize the database
        livreRepository.saveAndFlush(livre);

		int databaseSizeBeforeDelete = livreRepository.findAll().size();

        // Get the livre
        restLivreMockMvc.perform(delete("/api/livres/{id}", livre.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Livre> livres = livreRepository.findAll();
        assertThat(livres).hasSize(databaseSizeBeforeDelete - 1);
    }
}
