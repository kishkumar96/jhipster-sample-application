package com.mycompany.myapp.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The Enter the entity name entity.\n@author A true hipster
 */
@ApiModel(description = "The Enter the entity name entity.\n@author A true hipster")
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * fieldName
     */
    @ApiModelProperty(value = "fieldName")
    @Type(type = "uuid-char")
    @Column(name = "project_code", length = 36)
    private UUID projectCode;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "objective")
    private String objective;

    @Column(name = "work_plan")
    private String workPlan;

    @Column(name = "kra")
    private String kra;

    @Column(name = "output")
    private String output;

    @Column(name = "evaluation")
    private String evaluation;

    @Column(name = "budget")
    private Float budget;

    @Column(name = "head_off_project")
    private String headOffProject;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "target_audience")
    private String targetAudience;

    @Column(name = "overview_about_project")
    private String overviewAboutProject;

    @OneToMany(mappedBy = "projectCode")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Course> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getProjectCode() {
        return projectCode;
    }

    public Project projectCode(UUID projectCode) {
        this.projectCode = projectCode;
        return this;
    }

    public void setProjectCode(UUID projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public Project projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getObjective() {
        return objective;
    }

    public Project objective(String objective) {
        this.objective = objective;
        return this;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getWorkPlan() {
        return workPlan;
    }

    public Project workPlan(String workPlan) {
        this.workPlan = workPlan;
        return this;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }

    public String getKra() {
        return kra;
    }

    public Project kra(String kra) {
        this.kra = kra;
        return this;
    }

    public void setKra(String kra) {
        this.kra = kra;
    }

    public String getOutput() {
        return output;
    }

    public Project output(String output) {
        this.output = output;
        return this;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public Project evaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Float getBudget() {
        return budget;
    }

    public Project budget(Float budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public String getHeadOffProject() {
        return headOffProject;
    }

    public Project headOffProject(String headOffProject) {
        this.headOffProject = headOffProject;
        return this;
    }

    public void setHeadOffProject(String headOffProject) {
        this.headOffProject = headOffProject;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Project startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Project endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public Project targetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
        return this;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getOverviewAboutProject() {
        return overviewAboutProject;
    }

    public Project overviewAboutProject(String overviewAboutProject) {
        this.overviewAboutProject = overviewAboutProject;
        return this;
    }

    public void setOverviewAboutProject(String overviewAboutProject) {
        this.overviewAboutProject = overviewAboutProject;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Project courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Project addCourse(Course course) {
        this.courses.add(course);
        course.setProjectCode(this);
        return this;
    }

    public Project removeCourse(Course course) {
        this.courses.remove(course);
        course.setProjectCode(null);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", projectCode='" + getProjectCode() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", objective='" + getObjective() + "'" +
            ", workPlan='" + getWorkPlan() + "'" +
            ", kra='" + getKra() + "'" +
            ", output='" + getOutput() + "'" +
            ", evaluation='" + getEvaluation() + "'" +
            ", budget=" + getBudget() +
            ", headOffProject='" + getHeadOffProject() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", targetAudience='" + getTargetAudience() + "'" +
            ", overviewAboutProject='" + getOverviewAboutProject() + "'" +
            "}";
    }
}
