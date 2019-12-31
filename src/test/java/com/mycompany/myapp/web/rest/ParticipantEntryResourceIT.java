package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.ParticipantEntry;
import com.mycompany.myapp.repository.ParticipantEntryRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Country;
import com.mycompany.myapp.domain.enumeration.Gender;
import com.mycompany.myapp.domain.enumeration.Title;
import com.mycompany.myapp.domain.enumeration.Sector;
import com.mycompany.myapp.domain.enumeration.Specialgeneral;
import com.mycompany.myapp.domain.enumeration.Specialdisastermanagement;
import com.mycompany.myapp.domain.enumeration.Educationlevel;
import com.mycompany.myapp.domain.enumeration.Trainer;
/**
 * Integration tests for the {@link ParticipantEntryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ParticipantEntryResourceIT {

    private static final UUID DEFAULT_INDIVIDUAL_CODE = UUID.randomUUID();
    private static final UUID UPDATED_INDIVIDUAL_CODE = UUID.randomUUID();

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Country DEFAULT_COUNTRY = Country.ANTARTICA;
    private static final Country UPDATED_COUNTRY = Country.AMERICAN_SAMOA;

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Title DEFAULT_TITLE = Title.MR;
    private static final Title UPDATED_TITLE = Title.MRS;

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final Sector DEFAULT_SECTOR = Sector.CIVIL_SERVICE;
    private static final Sector UPDATED_SECTOR = Sector.INTERNATIONAL_ORGANIZATION;

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_WORK_PHONE = 1;
    private static final Integer UPDATED_WORK_PHONE = 2;

    private static final Integer DEFAULT_FAX_NUMBER = 1;
    private static final Integer UPDATED_FAX_NUMBER = 2;

    private static final Integer DEFAULT_HOME_PHONE = 1;
    private static final Integer UPDATED_HOME_PHONE = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PREVIOUS_EMPLOYMENT = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_EMPLOYMENT = "BBBBBBBBBB";

    private static final Specialgeneral DEFAULT_SPECIAL_GENERAL = Specialgeneral.ADMINISTRATION;
    private static final Specialgeneral UPDATED_SPECIAL_GENERAL = Specialgeneral.AGRICULTURE;

    private static final Specialdisastermanagement DEFAULT_SPECIAL_DISASTER_MANAGEMENT = Specialdisastermanagement.COORDINATION;
    private static final Specialdisastermanagement UPDATED_SPECIAL_DISASTER_MANAGEMENT = Specialdisastermanagement.DAMAGE_ASSEMENT_AND_PLANNING;

    private static final Educationlevel DEFAULT_EDUCATION_LEVEL = Educationlevel.PRIMARY;
    private static final Educationlevel UPDATED_EDUCATION_LEVEL = Educationlevel.SECONDARY;

    private static final Trainer DEFAULT_TRAINER = Trainer.YES;
    private static final Trainer UPDATED_TRAINER = Trainer.NO;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private ParticipantEntryRepository participantEntryRepository;

    @Mock
    private ParticipantEntryRepository participantEntryRepositoryMock;

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

    private MockMvc restParticipantEntryMockMvc;

    private ParticipantEntry participantEntry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParticipantEntryResource participantEntryResource = new ParticipantEntryResource(participantEntryRepository);
        this.restParticipantEntryMockMvc = MockMvcBuilders.standaloneSetup(participantEntryResource)
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
    public static ParticipantEntry createEntity(EntityManager em) {
        ParticipantEntry participantEntry = new ParticipantEntry()
            .individualCode(DEFAULT_INDIVIDUAL_CODE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .country(DEFAULT_COUNTRY)
            .gender(DEFAULT_GENDER)
            .title(DEFAULT_TITLE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .organization(DEFAULT_ORGANIZATION)
            .sector(DEFAULT_SECTOR)
            .position(DEFAULT_POSITION)
            .contactAddress(DEFAULT_CONTACT_ADDRESS)
            .workPhone(DEFAULT_WORK_PHONE)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .homePhone(DEFAULT_HOME_PHONE)
            .email(DEFAULT_EMAIL)
            .previousEmployment(DEFAULT_PREVIOUS_EMPLOYMENT)
            .specialGeneral(DEFAULT_SPECIAL_GENERAL)
            .specialDisasterManagement(DEFAULT_SPECIAL_DISASTER_MANAGEMENT)
            .educationLevel(DEFAULT_EDUCATION_LEVEL)
            .trainer(DEFAULT_TRAINER)
            .comments(DEFAULT_COMMENTS);
        return participantEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParticipantEntry createUpdatedEntity(EntityManager em) {
        ParticipantEntry participantEntry = new ParticipantEntry()
            .individualCode(UPDATED_INDIVIDUAL_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .country(UPDATED_COUNTRY)
            .gender(UPDATED_GENDER)
            .title(UPDATED_TITLE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .organization(UPDATED_ORGANIZATION)
            .sector(UPDATED_SECTOR)
            .position(UPDATED_POSITION)
            .contactAddress(UPDATED_CONTACT_ADDRESS)
            .workPhone(UPDATED_WORK_PHONE)
            .faxNumber(UPDATED_FAX_NUMBER)
            .homePhone(UPDATED_HOME_PHONE)
            .email(UPDATED_EMAIL)
            .previousEmployment(UPDATED_PREVIOUS_EMPLOYMENT)
            .specialGeneral(UPDATED_SPECIAL_GENERAL)
            .specialDisasterManagement(UPDATED_SPECIAL_DISASTER_MANAGEMENT)
            .educationLevel(UPDATED_EDUCATION_LEVEL)
            .trainer(UPDATED_TRAINER)
            .comments(UPDATED_COMMENTS);
        return participantEntry;
    }

    @BeforeEach
    public void initTest() {
        participantEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createParticipantEntry() throws Exception {
        int databaseSizeBeforeCreate = participantEntryRepository.findAll().size();

        // Create the ParticipantEntry
        restParticipantEntryMockMvc.perform(post("/api/participant-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantEntry)))
            .andExpect(status().isCreated());

        // Validate the ParticipantEntry in the database
        List<ParticipantEntry> participantEntryList = participantEntryRepository.findAll();
        assertThat(participantEntryList).hasSize(databaseSizeBeforeCreate + 1);
        ParticipantEntry testParticipantEntry = participantEntryList.get(participantEntryList.size() - 1);
        assertThat(testParticipantEntry.getIndividualCode()).isEqualTo(DEFAULT_INDIVIDUAL_CODE);
        assertThat(testParticipantEntry.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testParticipantEntry.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testParticipantEntry.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testParticipantEntry.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testParticipantEntry.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testParticipantEntry.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testParticipantEntry.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testParticipantEntry.getSector()).isEqualTo(DEFAULT_SECTOR);
        assertThat(testParticipantEntry.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testParticipantEntry.getContactAddress()).isEqualTo(DEFAULT_CONTACT_ADDRESS);
        assertThat(testParticipantEntry.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testParticipantEntry.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testParticipantEntry.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testParticipantEntry.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testParticipantEntry.getPreviousEmployment()).isEqualTo(DEFAULT_PREVIOUS_EMPLOYMENT);
        assertThat(testParticipantEntry.getSpecialGeneral()).isEqualTo(DEFAULT_SPECIAL_GENERAL);
        assertThat(testParticipantEntry.getSpecialDisasterManagement()).isEqualTo(DEFAULT_SPECIAL_DISASTER_MANAGEMENT);
        assertThat(testParticipantEntry.getEducationLevel()).isEqualTo(DEFAULT_EDUCATION_LEVEL);
        assertThat(testParticipantEntry.getTrainer()).isEqualTo(DEFAULT_TRAINER);
        assertThat(testParticipantEntry.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createParticipantEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = participantEntryRepository.findAll().size();

        // Create the ParticipantEntry with an existing ID
        participantEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipantEntryMockMvc.perform(post("/api/participant-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantEntry)))
            .andExpect(status().isBadRequest());

        // Validate the ParticipantEntry in the database
        List<ParticipantEntry> participantEntryList = participantEntryRepository.findAll();
        assertThat(participantEntryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParticipantEntries() throws Exception {
        // Initialize the database
        participantEntryRepository.saveAndFlush(participantEntry);

        // Get all the participantEntryList
        restParticipantEntryMockMvc.perform(get("/api/participant-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participantEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].individualCode").value(hasItem(DEFAULT_INDIVIDUAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION)))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].contactAddress").value(hasItem(DEFAULT_CONTACT_ADDRESS)))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER)))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].previousEmployment").value(hasItem(DEFAULT_PREVIOUS_EMPLOYMENT)))
            .andExpect(jsonPath("$.[*].specialGeneral").value(hasItem(DEFAULT_SPECIAL_GENERAL.toString())))
            .andExpect(jsonPath("$.[*].specialDisasterManagement").value(hasItem(DEFAULT_SPECIAL_DISASTER_MANAGEMENT.toString())))
            .andExpect(jsonPath("$.[*].educationLevel").value(hasItem(DEFAULT_EDUCATION_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].trainer").value(hasItem(DEFAULT_TRAINER.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllParticipantEntriesWithEagerRelationshipsIsEnabled() throws Exception {
        ParticipantEntryResource participantEntryResource = new ParticipantEntryResource(participantEntryRepositoryMock);
        when(participantEntryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restParticipantEntryMockMvc = MockMvcBuilders.standaloneSetup(participantEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restParticipantEntryMockMvc.perform(get("/api/participant-entries?eagerload=true"))
        .andExpect(status().isOk());

        verify(participantEntryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllParticipantEntriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ParticipantEntryResource participantEntryResource = new ParticipantEntryResource(participantEntryRepositoryMock);
            when(participantEntryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restParticipantEntryMockMvc = MockMvcBuilders.standaloneSetup(participantEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restParticipantEntryMockMvc.perform(get("/api/participant-entries?eagerload=true"))
        .andExpect(status().isOk());

            verify(participantEntryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getParticipantEntry() throws Exception {
        // Initialize the database
        participantEntryRepository.saveAndFlush(participantEntry);

        // Get the participantEntry
        restParticipantEntryMockMvc.perform(get("/api/participant-entries/{id}", participantEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participantEntry.getId().intValue()))
            .andExpect(jsonPath("$.individualCode").value(DEFAULT_INDIVIDUAL_CODE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION))
            .andExpect(jsonPath("$.sector").value(DEFAULT_SECTOR.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.contactAddress").value(DEFAULT_CONTACT_ADDRESS))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.previousEmployment").value(DEFAULT_PREVIOUS_EMPLOYMENT))
            .andExpect(jsonPath("$.specialGeneral").value(DEFAULT_SPECIAL_GENERAL.toString()))
            .andExpect(jsonPath("$.specialDisasterManagement").value(DEFAULT_SPECIAL_DISASTER_MANAGEMENT.toString()))
            .andExpect(jsonPath("$.educationLevel").value(DEFAULT_EDUCATION_LEVEL.toString()))
            .andExpect(jsonPath("$.trainer").value(DEFAULT_TRAINER.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    public void getNonExistingParticipantEntry() throws Exception {
        // Get the participantEntry
        restParticipantEntryMockMvc.perform(get("/api/participant-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParticipantEntry() throws Exception {
        // Initialize the database
        participantEntryRepository.saveAndFlush(participantEntry);

        int databaseSizeBeforeUpdate = participantEntryRepository.findAll().size();

        // Update the participantEntry
        ParticipantEntry updatedParticipantEntry = participantEntryRepository.findById(participantEntry.getId()).get();
        // Disconnect from session so that the updates on updatedParticipantEntry are not directly saved in db
        em.detach(updatedParticipantEntry);
        updatedParticipantEntry
            .individualCode(UPDATED_INDIVIDUAL_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .country(UPDATED_COUNTRY)
            .gender(UPDATED_GENDER)
            .title(UPDATED_TITLE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .organization(UPDATED_ORGANIZATION)
            .sector(UPDATED_SECTOR)
            .position(UPDATED_POSITION)
            .contactAddress(UPDATED_CONTACT_ADDRESS)
            .workPhone(UPDATED_WORK_PHONE)
            .faxNumber(UPDATED_FAX_NUMBER)
            .homePhone(UPDATED_HOME_PHONE)
            .email(UPDATED_EMAIL)
            .previousEmployment(UPDATED_PREVIOUS_EMPLOYMENT)
            .specialGeneral(UPDATED_SPECIAL_GENERAL)
            .specialDisasterManagement(UPDATED_SPECIAL_DISASTER_MANAGEMENT)
            .educationLevel(UPDATED_EDUCATION_LEVEL)
            .trainer(UPDATED_TRAINER)
            .comments(UPDATED_COMMENTS);

        restParticipantEntryMockMvc.perform(put("/api/participant-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParticipantEntry)))
            .andExpect(status().isOk());

        // Validate the ParticipantEntry in the database
        List<ParticipantEntry> participantEntryList = participantEntryRepository.findAll();
        assertThat(participantEntryList).hasSize(databaseSizeBeforeUpdate);
        ParticipantEntry testParticipantEntry = participantEntryList.get(participantEntryList.size() - 1);
        assertThat(testParticipantEntry.getIndividualCode()).isEqualTo(UPDATED_INDIVIDUAL_CODE);
        assertThat(testParticipantEntry.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testParticipantEntry.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testParticipantEntry.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testParticipantEntry.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testParticipantEntry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testParticipantEntry.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testParticipantEntry.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testParticipantEntry.getSector()).isEqualTo(UPDATED_SECTOR);
        assertThat(testParticipantEntry.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testParticipantEntry.getContactAddress()).isEqualTo(UPDATED_CONTACT_ADDRESS);
        assertThat(testParticipantEntry.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testParticipantEntry.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testParticipantEntry.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testParticipantEntry.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParticipantEntry.getPreviousEmployment()).isEqualTo(UPDATED_PREVIOUS_EMPLOYMENT);
        assertThat(testParticipantEntry.getSpecialGeneral()).isEqualTo(UPDATED_SPECIAL_GENERAL);
        assertThat(testParticipantEntry.getSpecialDisasterManagement()).isEqualTo(UPDATED_SPECIAL_DISASTER_MANAGEMENT);
        assertThat(testParticipantEntry.getEducationLevel()).isEqualTo(UPDATED_EDUCATION_LEVEL);
        assertThat(testParticipantEntry.getTrainer()).isEqualTo(UPDATED_TRAINER);
        assertThat(testParticipantEntry.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingParticipantEntry() throws Exception {
        int databaseSizeBeforeUpdate = participantEntryRepository.findAll().size();

        // Create the ParticipantEntry

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantEntryMockMvc.perform(put("/api/participant-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participantEntry)))
            .andExpect(status().isBadRequest());

        // Validate the ParticipantEntry in the database
        List<ParticipantEntry> participantEntryList = participantEntryRepository.findAll();
        assertThat(participantEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParticipantEntry() throws Exception {
        // Initialize the database
        participantEntryRepository.saveAndFlush(participantEntry);

        int databaseSizeBeforeDelete = participantEntryRepository.findAll().size();

        // Delete the participantEntry
        restParticipantEntryMockMvc.perform(delete("/api/participant-entries/{id}", participantEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParticipantEntry> participantEntryList = participantEntryRepository.findAll();
        assertThat(participantEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
