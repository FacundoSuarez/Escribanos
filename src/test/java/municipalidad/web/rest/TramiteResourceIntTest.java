package municipalidad.web.rest;

import municipalidad.EscribanosApp;

import municipalidad.domain.Tramite;
import municipalidad.repository.TramiteRepository;
import municipalidad.service.TramiteService;
import municipalidad.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static municipalidad.web.rest.TestUtil.sameInstant;
import static municipalidad.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TramiteResource REST controller.
 *
 * @see TramiteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscribanosApp.class)
public class TramiteResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private TramiteService tramiteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTramiteMockMvc;

    private Tramite tramite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TramiteResource tramiteResource = new TramiteResource(tramiteService);
        this.restTramiteMockMvc = MockMvcBuilders.standaloneSetup(tramiteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tramite createEntity(EntityManager em) {
        Tramite tramite = new Tramite()
            .fecha(DEFAULT_FECHA)
            .fechaFin(DEFAULT_FECHA_FIN)
            .observaciones(DEFAULT_OBSERVACIONES);
        return tramite;
    }

    @Before
    public void initTest() {
        tramite = createEntity(em);
    }

    @Test
    @Transactional
    public void createTramite() throws Exception {
//        int databaseSizeBeforeCreate = tramiteRepository.findAll().size();
//
//        // Create the Tramite
//        restTramiteMockMvc.perform(post("/api/tramites")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(tramite)))
//            .andExpect(status().isCreated());
//
//        // Validate the Tramite in the database
//        List<Tramite> tramiteList = tramiteRepository.findAll();
//        assertThat(tramiteList).hasSize(databaseSizeBeforeCreate + 1);
//        Tramite testTramite = tramiteList.get(tramiteList.size() - 1);
//        assertThat(testTramite.getFecha()).isEqualTo(DEFAULT_FECHA);
//        assertThat(testTramite.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
//        assertThat(testTramite.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createTramiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tramiteRepository.findAll().size();

        // Create the Tramite with an existing ID
        tramite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTramiteMockMvc.perform(post("/api/tramites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tramite)))
            .andExpect(status().isBadRequest());

        // Validate the Tramite in the database
        List<Tramite> tramiteList = tramiteRepository.findAll();
        assertThat(tramiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTramites() throws Exception {
        // Initialize the database
        tramiteRepository.saveAndFlush(tramite);

        // Get all the tramiteList
        restTramiteMockMvc.perform(get("/api/tramites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tramite.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(sameInstant(DEFAULT_FECHA_FIN))))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())));
    }

    @Test
    @Transactional
    public void getTramite() throws Exception {
        // Initialize the database
        tramiteRepository.saveAndFlush(tramite);

        // Get the tramite
        restTramiteMockMvc.perform(get("/api/tramites/{id}", tramite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tramite.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.fechaFin").value(sameInstant(DEFAULT_FECHA_FIN)))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTramite() throws Exception {
//        // Get the tramite
//        restTramiteMockMvc.perform(get("/api/tramites/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTramite() throws Exception {
//        // Initialize the database
//        tramiteService.save(tramite);
//
//        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();
//
//        // Update the tramite
//        Tramite updatedTramite = tramiteRepository.findOne(tramite.getId());
//        // Disconnect from session so that the updates on updatedTramite are not directly saved in db
//        em.detach(updatedTramite);
//        updatedTramite
//            .fecha(UPDATED_FECHA)
//            .fechaFin(UPDATED_FECHA_FIN)
//            .observaciones(UPDATED_OBSERVACIONES);
//
//        restTramiteMockMvc.perform(put("/api/tramites")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedTramite)))
//            .andExpect(status().isOk());
//
//        // Validate the Tramite in the database
//        List<Tramite> tramiteList = tramiteRepository.findAll();
//        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate);
//        Tramite testTramite = tramiteList.get(tramiteList.size() - 1);
//        assertThat(testTramite.getFecha()).isEqualTo(UPDATED_FECHA);
//        assertThat(testTramite.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
//        assertThat(testTramite.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingTramite() throws Exception {
//        int databaseSizeBeforeUpdate = tramiteRepository.findAll().size();
//
//        // Create the Tramite
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restTramiteMockMvc.perform(put("/api/tramites")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(tramite)))
//            .andExpect(status().isCreated());
//
//        // Validate the Tramite in the database
//        List<Tramite> tramiteList = tramiteRepository.findAll();
//        assertThat(tramiteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTramite() throws Exception {
//        // Initialize the database
//        tramiteService.save(tramite);
//
//        int databaseSizeBeforeDelete = tramiteRepository.findAll().size();
//
//        // Get the tramite
//        restTramiteMockMvc.perform(delete("/api/tramites/{id}", tramite.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Tramite> tramiteList = tramiteRepository.findAll();
//        assertThat(tramiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tramite.class);
        Tramite tramite1 = new Tramite();
        tramite1.setId(1L);
        Tramite tramite2 = new Tramite();
        tramite2.setId(tramite1.getId());
        assertThat(tramite1).isEqualTo(tramite2);
        tramite2.setId(2L);
        assertThat(tramite1).isNotEqualTo(tramite2);
        tramite1.setId(null);
        assertThat(tramite1).isNotEqualTo(tramite2);
    }
}
