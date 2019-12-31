package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.mycompany.myapp.domain.enumeration.Method;

import com.mycompany.myapp.domain.enumeration.Period;

/**
 * The Entity entity.\n@author A true hipster
 */
@ApiModel(description = "The Entity entity.\n@author A true hipster")
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * fieldName
     */
    @ApiModelProperty(value = "fieldName")
    @Type(type = "uuid-char")
    @Column(name = "course_code", length = 36)
    private UUID courseCode;

    @Column(name = "course_title")
    private String courseTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private Method method;

    @Column(name = "duration")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "period")
    private Period period;

    @Column(name = "description")
    private String description;

    @Column(name = "comments")
    private String comments;

    @OneToMany(mappedBy = "courseCode")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Session> sessions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("courses")
    private Project projectCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCourseCode() {
        return courseCode;
    }

    public Course courseCode(UUID courseCode) {
        this.courseCode = courseCode;
        return this;
    }

    public void setCourseCode(UUID courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public Course courseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
        return this;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Method getMethod() {
        return method;
    }

    public Course method(Method method) {
        this.method = method;
        return this;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Integer getDuration() {
        return duration;
    }

    public Course duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Period getPeriod() {
        return period;
    }

    public Course period(Period period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public Course description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public Course comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public Course sessions(Set<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public Course addSession(Session session) {
        this.sessions.add(session);
        session.setCourseCode(this);
        return this;
    }

    public Course removeSession(Session session) {
        this.sessions.remove(session);
        session.setCourseCode(null);
        return this;
    }

    public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

    public Project getProjectCode() {
        return projectCode;
    }

    public Course projectCode(Project project) {
        this.projectCode = project;
        return this;
    }

    public void setProjectCode(Project project) {
        this.projectCode = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseCode='" + getCourseCode() + "'" +
            ", courseTitle='" + getCourseTitle() + "'" +
            ", method='" + getMethod() + "'" +
            ", duration=" + getDuration() +
            ", period='" + getPeriod() + "'" +
            ", description='" + getDescription() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
