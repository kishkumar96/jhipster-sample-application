package com.mycompany.myapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

import com.mycompany.myapp.domain.enumeration.Country;

import com.mycompany.myapp.domain.enumeration.SessionType;

/**
 * The Enter the entity name entity.\n@author A true hipster
 */
@ApiModel(description = "The Enter the entity name entity.\n@author A true hipster")
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * fieldName
     */
    @ApiModelProperty(value = "fieldName")
    @Type(type = "uuid-char")
    @Column(name = "session_code", length = 36)
    private UUID sessionCode;

    @Column(name = "name_of_activity")
    private String nameOfActivity;

    @Enumerated(EnumType.STRING)
    @Column(name = "location")
    private Country location;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "attendees_number")
    private Integer attendeesNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type")
    private SessionType sessionType;

    @Column(name = "description")
    private String description;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JsonIgnoreProperties("sessions")
    private Course courseCode;

    @ManyToMany(mappedBy = "sessionCodes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ParticipantEntry> individualCodes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getSessionCode() {
        return sessionCode;
    }

    public Session sessionCode(UUID sessionCode) {
        this.sessionCode = sessionCode;
        return this;
    }

    public void setSessionCode(UUID sessionCode) {
        this.sessionCode = sessionCode;
    }

    public String getNameOfActivity() {
        return nameOfActivity;
    }

    public Session nameOfActivity(String nameOfActivity) {
        this.nameOfActivity = nameOfActivity;
        return this;
    }

    public void setNameOfActivity(String nameOfActivity) {
        this.nameOfActivity = nameOfActivity;
    }

    public Country getLocation() {
        return location;
    }

    public Session location(Country location) {
        this.location = location;
        return this;
    }

    public void setLocation(Country location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Session startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Session endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getAttendeesNumber() {
        return attendeesNumber;
    }

    public Session attendeesNumber(Integer attendeesNumber) {
        this.attendeesNumber = attendeesNumber;
        return this;
    }

    public void setAttendeesNumber(Integer attendeesNumber) {
        this.attendeesNumber = attendeesNumber;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public Session sessionType(SessionType sessionType) {
        this.sessionType = sessionType;
        return this;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public String getDescription() {
        return description;
    }

    public Session description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public Session comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Course getCourseCode() {
        return courseCode;
    }

    public Session courseCode(Course course) {
        this.courseCode = course;
        return this;
    }

    public void setCourseCode(Course course) {
        this.courseCode = course;
    }

    public Set<ParticipantEntry> getIndividualCodes() {
        return individualCodes;
    }

    public Session individualCodes(Set<ParticipantEntry> participantEntries) {
        this.individualCodes = participantEntries;
        return this;
    }

    public Session addIndividualCode(ParticipantEntry participantEntry) {
        this.individualCodes.add(participantEntry);
        participantEntry.getSessionCodes().add(this);
        return this;
    }

    public Session removeIndividualCode(ParticipantEntry participantEntry) {
        this.individualCodes.remove(participantEntry);
        participantEntry.getSessionCodes().remove(this);
        return this;
    }

    public void setIndividualCodes(Set<ParticipantEntry> participantEntries) {
        this.individualCodes = participantEntries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", sessionCode='" + getSessionCode() + "'" +
            ", nameOfActivity='" + getNameOfActivity() + "'" +
            ", location='" + getLocation() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", attendeesNumber=" + getAttendeesNumber() +
            ", sessionType='" + getSessionType() + "'" +
            ", description='" + getDescription() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
