package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Project;
import com.mycompany.myapp.repository.ProjectRepository;
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

/**
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ProjectResourceIT {

    private static final UUID DEFAULT_PROJECT_CODE = UUID.randomUUID();
    private static final UUID UPDATED_PROJECT_CODE = UUID.randomUUID();

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_PLAN = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PLAN = "BBBBBBBBBB";

    private static final String DEFAULT_KRA = "AAAAAAAAAA";
    private static final String UPDATED_KRA = "BBBBBBBBBB";

    private static final String DEFAULT_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUT = "BBBBBBBBBB";

    private static final String DEFAULT_EVALUATION = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION = "BBBBBBBBBB";

    private static final Float DEFAULT_BUDGET = 1F;
    private static final Float UPDATED_BUDGET = 2F;

    private static final String DEFAULT_HEAD_OFF_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_OFF_PROJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TARGET_AUDIENCE = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_AUDIENCE = "BBBBBBBBBB";

    private static final String DEFAULT_OVERVIEW_ABOUT_PROJECT = "AAAAAAAAAA";
    private static final String UPDATED_OVERVIEW_ABOUT_PROJECT = "BBBBBBBBBB";

    @Autowired
    private ProjectRepository projectRepository;

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

    private MockMvc restProjectMockMvc;

    private Project project;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectResource projectResource = new ProjectResource(projectRepository);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
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
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .projectCode(DEFAULT_PROJECT_CODE)
            .projectName(DEFAULT_PROJECT_NAME)
            .objective(DEFAULT_OBJECTIVE)
            .workPlan(DEFAULT_WORK_PLAN)
            .kra(DEFAULT_KRA)
            .output(DEFAULT_OUTPUT)
            .evaluation(DEFAULT_EVALUATION)
            .budget(DEFAULT_BUDGET)
            .headOffProject(DEFAULT_HEAD_OFF_PROJECT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .targetAudience(DEFAULT_TARGET_AUDIENCE)
            .overviewAboutProject(DEFAULT_OVERVIEW_ABOUT_PROJECT);
        return project;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .projectCode(UPDATED_PROJECT_CODE)
            .projectName(UPDATED_PROJECT_NAME)
            .objective(UPDATED_OBJECTIVE)
            .workPlan(UPDATED_WORK_PLAN)
            .kra(UPDATED_KRA)
            .output(UPDATED_OUTPUT)
            .evaluation(UPDATED_EVALUATION)
            .budget(UPDATED_BUDGET)
            .headOffProject(UPDATED_HEAD_OFF_PROJECT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .targetAudience(UPDATED_TARGET_AUDIENCE)
            .overviewAboutProject(UPDATED_OVERVIEW_ABOUT_PROJECT);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProjectCode()).isEqualTo(DEFAULT_PROJECT_CODE);
        assertThat(testProject.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProject.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testProject.getWorkPlan()).isEqualTo(DEFAULT_WORK_PLAN);
        assertThat(testProject.getKra()).isEqualTo(DEFAULT_KRA);
        assertThat(testProject.getOutput()).isEqualTo(DEFAULT_OUTPUT);
        assertThat(testProject.getEvaluation()).isEqualTo(DEFAULT_EVALUATION);
        assertThat(testProject.getBudget()).isEqualTo(DEFAULT_BUDGET);
        assertThat(testProject.getHeadOffProject()).isEqualTo(DEFAULT_HEAD_OFF_PROJECT);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProject.getTargetAudience()).isEqualTo(DEFAULT_TARGET_AUDIENCE);
        assertThat(testProject.getOverviewAboutProject()).isEqualTo(DEFAULT_OVERVIEW_ABOUT_PROJECT);
    }

    @Test
    @Transactional
    public void createProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project with an existing ID
        project.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectCode").value(hasItem(DEFAULT_PROJECT_CODE.toString())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].objective").value(hasItem(DEFAULT_OBJECTIVE)))
            .andExpect(jsonPath("$.[*].workPlan").value(hasItem(DEFAULT_WORK_PLAN)))
            .andExpect(jsonPath("$.[*].kra").value(hasItem(DEFAULT_KRA)))
            .andExpect(jsonPath("$.[*].output").value(hasItem(DEFAULT_OUTPUT)))
            .andExpect(jsonPath("$.[*].evaluation").value(hasItem(DEFAULT_EVALUATION)))
            .andExpect(jsonPath("$.[*].budget").value(hasItem(DEFAULT_BUDGET.doubleValue())))
            .andExpect(jsonPath("$.[*].headOffProject").value(hasItem(DEFAULT_HEAD_OFF_PROJECT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].targetAudience").value(hasItem(DEFAULT_TARGET_AUDIENCE)))
            .andExpect(jsonPath("$.[*].overviewAboutProject").value(hasItem(DEFAULT_OVERVIEW_ABOUT_PROJECT)));
    }
    
    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.projectCode").value(DEFAULT_PROJECT_CODE.toString()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.objective").value(DEFAULT_OBJECTIVE))
            .andExpect(jsonPath("$.workPlan").value(DEFAULT_WORK_PLAN))
            .andExpect(jsonPath("$.kra").value(DEFAULT_KRA))
            .andExpect(jsonPath("$.output").value(DEFAULT_OUTPUT))
            .andExpect(jsonPath("$.evaluation").value(DEFAULT_EVALUATION))
            .andExpect(jsonPath("$.budget").value(DEFAULT_BUDGET.doubleValue()))
            .andExpect(jsonPath("$.headOffProject").value(DEFAULT_HEAD_OFF_PROJECT))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.targetAudience").value(DEFAULT_TARGET_AUDIENCE))
            .andExpect(jsonPath("$.overviewAboutProject").value(DEFAULT_OVERVIEW_ABOUT_PROJECT));
    }

    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .projectCode(UPDATED_PROJECT_CODE)
            .projectName(UPDATED_PROJECT_NAME)
            .objective(UPDATED_OBJECTIVE)
            .workPlan(UPDATED_WORK_PLAN)
            .kra(UPDATED_KRA)
            .output(UPDATED_OUTPUT)
            .evaluation(UPDATED_EVALUATION)
            .budget(UPDATED_BUDGET)
            .headOffProject(UPDATED_HEAD_OFF_PROJECT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .targetAudience(UPDATED_TARGET_AUDIENCE)
            .overviewAboutProject(UPDATED_OVERVIEW_ABOUT_PROJECT);

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProjectCode()).isEqualTo(UPDATED_PROJECT_CODE);
        assertThat(testProject.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProject.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testProject.getWorkPlan()).isEqualTo(UPDATED_WORK_PLAN);
        assertThat(testProject.getKra()).isEqualTo(UPDATED_KRA);
        assertThat(testProject.getOutput()).isEqualTo(UPDATED_OUTPUT);
        assertThat(testProject.getEvaluation()).isEqualTo(UPDATED_EVALUATION);
        assertThat(testProject.getBudget()).isEqualTo(UPDATED_BUDGET);
        assertThat(testProject.getHeadOffProject()).isEqualTo(UPDATED_HEAD_OFF_PROJECT);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getTargetAudience()).isEqualTo(UPDATED_TARGET_AUDIENCE);
        assertThat(testProject.getOverviewAboutProject()).isEqualTo(UPDATED_OVERVIEW_ABOUT_PROJECT);
    }

    @Test
    @Transactional
    public void updateNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Create the Project

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
