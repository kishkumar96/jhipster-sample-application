package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Session;
import com.mycompany.myapp.repository.SessionRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Country;
import com.mycompany.myapp.domain.enumeration.SessionType;
/**
 * Integration tests for the {@link SessionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class SessionResourceIT {

    private static final UUID DEFAULT_SESSION_CODE = UUID.randomUUID();
    private static final UUID UPDATED_SESSION_CODE = UUID.randomUUID();

    private static final String DEFAULT_NAME_OF_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_ACTIVITY = "BBBBBBBBBB";

    private static final Country DEFAULT_LOCATION = Country.ANTARTICA;
    private static final Country UPDATED_LOCATION = Country.AMERICAN_SAMOA;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ATTENDEES_NUMBER = 1;
    private static final Integer UPDATED_ATTENDEES_NUMBER = 2;

    private static final SessionType DEFAULT_SESSION_TYPE = SessionType.IN_COUNTRY_TECHNICAL_ASSISTANCE;
    private static final SessionType UPDATED_SESSION_TYPE = SessionType.INTERNATIONAL_COURSE;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSessionMockMvc;

    private Session session;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SessionResource sessionResource = new SessionResource(sessionRepository);
        this.restSessionMockMvc = MockMvcBuilders.standaloneSetup(sessionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createEntity(EntityManager em) {
        Session session = new Session()
            .sessionCode(DEFAULT_SESSION_CODE)
            .nameOfActivity(DEFAULT_NAME_OF_ACTIVITY)
            .location(DEFAULT_LOCATION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .attendeesNumber(DEFAULT_ATTENDEES_NUMBER)
            .sessionType(DEFAULT_SESSION_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .comment(DEFAULT_COMMENT);
        return session;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Session createUpdatedEntity(EntityManager em) {
        Session session = new Session()
            .sessionCode(UPDATED_SESSION_CODE)
            .nameOfActivity(UPDATED_NAME_OF_ACTIVITY)
            .location(UPDATED_LOCATION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .attendeesNumber(UPDATED_ATTENDEES_NUMBER)
            .sessionType(UPDATED_SESSION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .comment(UPDATED_COMMENT);
        return session;
    }

    @BeforeEach
    public void initTest() {
        session = createEntity(em);
    }

    @Test
    @Transactional
    public void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getSessionCode()).isEqualTo(DEFAULT_SESSION_CODE);
        assertThat(testSession.getNameOfActivity()).isEqualTo(DEFAULT_NAME_OF_ACTIVITY);
        assertThat(testSession.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testSession.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSession.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSession.getAttendeesNumber()).isEqualTo(DEFAULT_ATTENDEES_NUMBER);
        assertThat(testSession.getSessionType()).isEqualTo(DEFAULT_SESSION_TYPE);
        assertThat(testSession.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSession.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createSessionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session with an existing ID
        session.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionMockMvc.perform(post("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get all the sessionList
        restSessionMockMvc.perform(get("/api/sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionCode").value(hasItem(DEFAULT_SESSION_CODE.toString())))
            .andExpect(jsonPath("$.[*].nameOfActivity").value(hasItem(DEFAULT_NAME_OF_ACTIVITY)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].attendeesNumber").value(hasItem(DEFAULT_ATTENDEES_NUMBER)))
            .andExpect(jsonPath("$.[*].sessionType").value(hasItem(DEFAULT_SESSION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(session.getId().intValue()))
            .andExpect(jsonPath("$.sessionCode").value(DEFAULT_SESSION_CODE.toString()))
            .andExpect(jsonPath("$.nameOfActivity").value(DEFAULT_NAME_OF_ACTIVITY))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.attendeesNumber").value(DEFAULT_ATTENDEES_NUMBER))
            .andExpect(jsonPath("$.sessionType").value(DEFAULT_SESSION_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }

    @Test
    @Transactional
    public void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        Session updatedSession = sessionRepository.findById(session.getId()).get();
        // Disconnect from session so that the updates on updatedSession are not directly saved in db
        em.detach(updatedSession);
        updatedSession
            .sessionCode(UPDATED_SESSION_CODE)
            .nameOfActivity(UPDATED_NAME_OF_ACTIVITY)
            .location(UPDATED_LOCATION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .attendeesNumber(UPDATED_ATTENDEES_NUMBER)
            .sessionType(UPDATED_SESSION_TYPE)
            .description(UPDATED_DESCRIPTION)
            .comment(UPDATED_COMMENT);

        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSession)))
            .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessionList.get(sessionList.size() - 1);
        assertThat(testSession.getSessionCode()).isEqualTo(UPDATED_SESSION_CODE);
        assertThat(testSession.getNameOfActivity()).isEqualTo(UPDATED_NAME_OF_ACTIVITY);
        assertThat(testSession.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSession.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSession.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSession.getAttendeesNumber()).isEqualTo(UPDATED_ATTENDEES_NUMBER);
        assertThat(testSession.getSessionType()).isEqualTo(UPDATED_SESSION_TYPE);
        assertThat(testSession.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSession.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingSession() throws Exception {
        int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Create the Session

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionMockMvc.perform(put("/api/sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(session)))
            .andExpect(status().isBadRequest());

        // Validate the Session in the database
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Delete the session
        restSessionMockMvc.perform(delete("/api/sessions/{id}", session.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Session> sessionList = sessionRepository.findAll();
        assertThat(sessionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
