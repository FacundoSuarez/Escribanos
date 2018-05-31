package municipalidad.web.rest;

import municipalidad.EscribanosApp;

import municipalidad.domain.Presentacion;
import municipalidad.repository.PresentacionRepository;
import municipalidad.service.PresentacionService;
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
import java.util.List;

import static municipalidad.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PresentacionResource REST controller.
 *
 * @see PresentacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscribanosApp.class)
public class PresentacionResourceIntTest {

    private static final String DEFAULT_CUIT_ESCRIBANO = "AAAAAAAAAA";
    private static final String UPDATED_CUIT_ESCRIBANO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    @Autowired
    private PresentacionRepository presentacionRepository;

    @Autowired
    private PresentacionService presentacionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPresentacionMockMvc;

    private Presentacion presentacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PresentacionResource presentacionResource = new PresentacionResource(presentacionService);
        this.restPresentacionMockMvc = MockMvcBuilders.standaloneSetup(presentacionResource)
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
    public static Presentacion createEntity(EntityManager em) {
        Presentacion presentacion = new Presentacion()
            .cuitEscribano(DEFAULT_CUIT_ESCRIBANO)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO);
        return presentacion;
    }

    @Before
    public void initTest() {
        presentacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPresentacion() throws Exception {
//        int databaseSizeBeforeCreate = presentacionRepository.findAll().size();
//
//        // Create the Presentacion
//        restPresentacionMockMvc.perform(post("/api/presentacions")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(presentacion)))
//            .andExpect(status().isCreated());
//
//        // Validate the Presentacion in the database
//        List<Presentacion> presentacionList = presentacionRepository.findAll();
//        assertThat(presentacionList).hasSize(databaseSizeBeforeCreate + 1);
//        Presentacion testPresentacion = presentacionList.get(presentacionList.size() - 1);
//        assertThat(testPresentacion.getCuitEscribano()).isEqualTo(DEFAULT_CUIT_ESCRIBANO);
//        assertThat(testPresentacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
//        assertThat(testPresentacion.getApellido()).isEqualTo(DEFAULT_APELLIDO);
    }

    @Test
    @Transactional
    public void createPresentacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = presentacionRepository.findAll().size();

        // Create the Presentacion with an existing ID
        presentacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPresentacionMockMvc.perform(post("/api/presentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentacion)))
            .andExpect(status().isBadRequest());

        // Validate the Presentacion in the database
        List<Presentacion> presentacionList = presentacionRepository.findAll();
        assertThat(presentacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPresentacions() throws Exception {
//        // Initialize the database
//        presentacionRepository.saveAndFlush(presentacion);
//
//        // Get all the presentacionList
//        restPresentacionMockMvc.perform(get("/api/presentacions?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(presentacion.getId().intValue())))
//            .andExpect(jsonPath("$.[*].cuitEscribano").value(hasItem(DEFAULT_CUIT_ESCRIBANO.toString())))
//            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
//            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())));
    }

    @Test
    @Transactional
    public void getPresentacion() throws Exception {
        // Initialize the database
        presentacionRepository.saveAndFlush(presentacion);

        // Get the presentacion
        restPresentacionMockMvc.perform(get("/api/presentacions/{id}", presentacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(presentacion.getId().intValue()))
            .andExpect(jsonPath("$.cuitEscribano").value(DEFAULT_CUIT_ESCRIBANO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPresentacion() throws Exception {
        // Get the presentacion
        restPresentacionMockMvc.perform(get("/api/presentacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePresentacion() throws Exception {
//        // Initialize the database
//        presentacionService.save(presentacion);
//
//        int databaseSizeBeforeUpdate = presentacionRepository.findAll().size();
//
//        // Update the presentacion
//        Presentacion updatedPresentacion = presentacionRepository.findOne(presentacion.getId());
//        // Disconnect from session so that the updates on updatedPresentacion are not directly saved in db
//        em.detach(updatedPresentacion);
//        updatedPresentacion
//            .cuitEscribano(UPDATED_CUIT_ESCRIBANO)
//            .nombre(UPDATED_NOMBRE)
//            .apellido(UPDATED_APELLIDO);
//
//        restPresentacionMockMvc.perform(put("/api/presentacions")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedPresentacion)))
//            .andExpect(status().isOk());
//
//        // Validate the Presentacion in the database
//        List<Presentacion> presentacionList = presentacionRepository.findAll();
//        assertThat(presentacionList).hasSize(databaseSizeBeforeUpdate);
//        Presentacion testPresentacion = presentacionList.get(presentacionList.size() - 1);
//        assertThat(testPresentacion.getCuitEscribano()).isEqualTo(UPDATED_CUIT_ESCRIBANO);
//        assertThat(testPresentacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
//        assertThat(testPresentacion.getApellido()).isEqualTo(UPDATED_APELLIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingPresentacion() throws Exception {
//        int databaseSizeBeforeUpdate = presentacionRepository.findAll().size();
//
//        // Create the Presentacion
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restPresentacionMockMvc.perform(put("/api/presentacions")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(presentacion)))
//            .andExpect(status().isCreated());
//
//        // Validate the Presentacion in the database
//        List<Presentacion> presentacionList = presentacionRepository.findAll();
//        assertThat(presentacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePresentacion() throws Exception {
//        // Initialize the database
//        presentacionService.save(presentacion);
//
//        int databaseSizeBeforeDelete = presentacionRepository.findAll().size();
//
//        // Get the presentacion
//        restPresentacionMockMvc.perform(delete("/api/presentacions/{id}", presentacion.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Presentacion> presentacionList = presentacionRepository.findAll();
//        assertThat(presentacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Presentacion.class);
        Presentacion presentacion1 = new Presentacion();
        presentacion1.setId(1L);
        Presentacion presentacion2 = new Presentacion();
        presentacion2.setId(presentacion1.getId());
        assertThat(presentacion1).isEqualTo(presentacion2);
        presentacion2.setId(2L);
        assertThat(presentacion1).isNotEqualTo(presentacion2);
        presentacion1.setId(null);
        assertThat(presentacion1).isNotEqualTo(presentacion2);
    }
}
