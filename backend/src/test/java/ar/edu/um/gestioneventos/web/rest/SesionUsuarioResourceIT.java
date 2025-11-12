package ar.edu.um.gestioneventos.web.rest;

import static ar.edu.um.gestioneventos.domain.SesionUsuarioAsserts.*;
import static ar.edu.um.gestioneventos.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.gestioneventos.IntegrationTest;
import ar.edu.um.gestioneventos.domain.SesionUsuario;
import ar.edu.um.gestioneventos.domain.User;
import ar.edu.um.gestioneventos.repository.SesionUsuarioRepository;
import ar.edu.um.gestioneventos.repository.UserRepository;
import ar.edu.um.gestioneventos.service.SesionUsuarioService;
import ar.edu.um.gestioneventos.service.dto.SesionUsuarioDTO;
import ar.edu.um.gestioneventos.service.mapper.SesionUsuarioMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SesionUsuarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SesionUsuarioResourceIT {

    private static final String DEFAULT_ESTADO_FLUJO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_FLUJO = "BBBBBBBBBB";

    private static final String DEFAULT_DATOS_TEMPORALES = "AAAAAAAAAA";
    private static final String UPDATED_DATOS_TEMPORALES = "BBBBBBBBBB";

    private static final Instant DEFAULT_ULTIMA_ACTUALIZACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ULTIMA_ACTUALIZACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sesion-usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SesionUsuarioRepository sesionUsuarioRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private SesionUsuarioRepository sesionUsuarioRepositoryMock;

    @Autowired
    private SesionUsuarioMapper sesionUsuarioMapper;

    @Mock
    private SesionUsuarioService sesionUsuarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSesionUsuarioMockMvc;

    private SesionUsuario sesionUsuario;

    private SesionUsuario insertedSesionUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SesionUsuario createEntity(EntityManager em) {
        SesionUsuario sesionUsuario = new SesionUsuario()
            .estadoFlujo(DEFAULT_ESTADO_FLUJO)
            .datosTemporales(DEFAULT_DATOS_TEMPORALES)
            .ultimaActualizacion(DEFAULT_ULTIMA_ACTUALIZACION);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        sesionUsuario.setUser(user);
        return sesionUsuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SesionUsuario createUpdatedEntity(EntityManager em) {
        SesionUsuario updatedSesionUsuario = new SesionUsuario()
            .estadoFlujo(UPDATED_ESTADO_FLUJO)
            .datosTemporales(UPDATED_DATOS_TEMPORALES)
            .ultimaActualizacion(UPDATED_ULTIMA_ACTUALIZACION);
        // Add required entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();
        updatedSesionUsuario.setUser(user);
        return updatedSesionUsuario;
    }

    @BeforeEach
    void initTest() {
        sesionUsuario = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedSesionUsuario != null) {
            sesionUsuarioRepository.delete(insertedSesionUsuario);
            insertedSesionUsuario = null;
        }
    }

    @Test
    @Transactional
    void createSesionUsuario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);
        var returnedSesionUsuarioDTO = om.readValue(
            restSesionUsuarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SesionUsuarioDTO.class
        );

        // Validate the SesionUsuario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSesionUsuario = sesionUsuarioMapper.toEntity(returnedSesionUsuarioDTO);
        assertSesionUsuarioUpdatableFieldsEquals(returnedSesionUsuario, getPersistedSesionUsuario(returnedSesionUsuario));

        assertSesionUsuarioMapsIdRelationshipPersistedValue(sesionUsuario, returnedSesionUsuario);

        insertedSesionUsuario = returnedSesionUsuario;
    }

    @Test
    @Transactional
    void createSesionUsuarioWithExistingId() throws Exception {
        // Create the SesionUsuario with an existing ID
        sesionUsuario.setId(1L);
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSesionUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateSesionUsuarioMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Add a new parent entity
        User user = UserResourceIT.createEntity();
        em.persist(user);
        em.flush();

        // Load the sesionUsuario
        SesionUsuario updatedSesionUsuario = sesionUsuarioRepository.findById(sesionUsuario.getId()).orElseThrow();
        assertThat(updatedSesionUsuario).isNotNull();
        // Disconnect from session so that the updates on updatedSesionUsuario are not directly saved in db
        em.detach(updatedSesionUsuario);

        // Update the User with new association value
        updatedSesionUsuario.setUser(user);
        SesionUsuarioDTO updatedSesionUsuarioDTO = sesionUsuarioMapper.toDto(updatedSesionUsuario);
        assertThat(updatedSesionUsuarioDTO).isNotNull();

        // Update the entity
        restSesionUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSesionUsuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSesionUsuarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        /**
         * Validate the id for MapsId, the ids must be same
         * Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
         * Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
         * assertThat(testSesionUsuario.getId()).isEqualTo(testSesionUsuario.getUser().getId());
         */
    }

    @Test
    @Transactional
    void checkUltimaActualizacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sesionUsuario.setUltimaActualizacion(null);

        // Create the SesionUsuario, which fails.
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        restSesionUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSesionUsuarios() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        // Get all the sesionUsuarioList
        restSesionUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sesionUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].estadoFlujo").value(hasItem(DEFAULT_ESTADO_FLUJO)))
            .andExpect(jsonPath("$.[*].datosTemporales").value(hasItem(DEFAULT_DATOS_TEMPORALES)))
            .andExpect(jsonPath("$.[*].ultimaActualizacion").value(hasItem(DEFAULT_ULTIMA_ACTUALIZACION.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSesionUsuariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(sesionUsuarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSesionUsuarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sesionUsuarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSesionUsuariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sesionUsuarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSesionUsuarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sesionUsuarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSesionUsuario() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        // Get the sesionUsuario
        restSesionUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, sesionUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sesionUsuario.getId().intValue()))
            .andExpect(jsonPath("$.estadoFlujo").value(DEFAULT_ESTADO_FLUJO))
            .andExpect(jsonPath("$.datosTemporales").value(DEFAULT_DATOS_TEMPORALES))
            .andExpect(jsonPath("$.ultimaActualizacion").value(DEFAULT_ULTIMA_ACTUALIZACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSesionUsuario() throws Exception {
        // Get the sesionUsuario
        restSesionUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSesionUsuario() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesionUsuario
        SesionUsuario updatedSesionUsuario = sesionUsuarioRepository.findById(sesionUsuario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSesionUsuario are not directly saved in db
        em.detach(updatedSesionUsuario);
        updatedSesionUsuario
            .estadoFlujo(UPDATED_ESTADO_FLUJO)
            .datosTemporales(UPDATED_DATOS_TEMPORALES)
            .ultimaActualizacion(UPDATED_ULTIMA_ACTUALIZACION);
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(updatedSesionUsuario);

        restSesionUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sesionUsuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSesionUsuarioToMatchAllProperties(updatedSesionUsuario);
    }

    @Test
    @Transactional
    void putNonExistingSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sesionUsuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSesionUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesionUsuario using partial update
        SesionUsuario partialUpdatedSesionUsuario = new SesionUsuario();
        partialUpdatedSesionUsuario.setId(sesionUsuario.getId());

        restSesionUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSesionUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSesionUsuario))
            )
            .andExpect(status().isOk());

        // Validate the SesionUsuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSesionUsuarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSesionUsuario, sesionUsuario),
            getPersistedSesionUsuario(sesionUsuario)
        );
    }

    @Test
    @Transactional
    void fullUpdateSesionUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sesionUsuario using partial update
        SesionUsuario partialUpdatedSesionUsuario = new SesionUsuario();
        partialUpdatedSesionUsuario.setId(sesionUsuario.getId());

        partialUpdatedSesionUsuario
            .estadoFlujo(UPDATED_ESTADO_FLUJO)
            .datosTemporales(UPDATED_DATOS_TEMPORALES)
            .ultimaActualizacion(UPDATED_ULTIMA_ACTUALIZACION);

        restSesionUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSesionUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSesionUsuario))
            )
            .andExpect(status().isOk());

        // Validate the SesionUsuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSesionUsuarioUpdatableFieldsEquals(partialUpdatedSesionUsuario, getPersistedSesionUsuario(partialUpdatedSesionUsuario));
    }

    @Test
    @Transactional
    void patchNonExistingSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sesionUsuarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sesionUsuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSesionUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sesionUsuario.setId(longCount.incrementAndGet());

        // Create the SesionUsuario
        SesionUsuarioDTO sesionUsuarioDTO = sesionUsuarioMapper.toDto(sesionUsuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSesionUsuarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sesionUsuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SesionUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSesionUsuario() throws Exception {
        // Initialize the database
        insertedSesionUsuario = sesionUsuarioRepository.saveAndFlush(sesionUsuario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sesionUsuario
        restSesionUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, sesionUsuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sesionUsuarioRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected SesionUsuario getPersistedSesionUsuario(SesionUsuario sesionUsuario) {
        return sesionUsuarioRepository.findById(sesionUsuario.getId()).orElseThrow();
    }

    protected void assertPersistedSesionUsuarioToMatchAllProperties(SesionUsuario expectedSesionUsuario) {
        assertSesionUsuarioAllPropertiesEquals(expectedSesionUsuario, getPersistedSesionUsuario(expectedSesionUsuario));
    }

    protected void assertPersistedSesionUsuarioToMatchUpdatableProperties(SesionUsuario expectedSesionUsuario) {
        assertSesionUsuarioAllUpdatablePropertiesEquals(expectedSesionUsuario, getPersistedSesionUsuario(expectedSesionUsuario));
    }
}
