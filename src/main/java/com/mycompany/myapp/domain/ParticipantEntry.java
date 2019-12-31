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

import com.mycompany.myapp.domain.enumeration.Country;

import com.mycompany.myapp.domain.enumeration.Gender;

import com.mycompany.myapp.domain.enumeration.Title;

import com.mycompany.myapp.domain.enumeration.Sector;

import com.mycompany.myapp.domain.enumeration.Specialgeneral;

import com.mycompany.myapp.domain.enumeration.Specialdisastermanagement;

import com.mycompany.myapp.domain.enumeration.Educationlevel;

import com.mycompany.myapp.domain.enumeration.Trainer;

/**
 * The Entity entity.\n@author A true hipster
 */
@ApiModel(description = "The Entity entity.\n@author A true hipster")
@Entity
@Table(name = "participant_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParticipantEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * fieldName
     */
    @ApiModelProperty(value = "fieldName")
    @Type(type = "uuid-char")
    @Column(name = "individual_code", length = 36)
    private UUID individualCode;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "title")
    private Title title;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "organization")
    private String organization;

    @Enumerated(EnumType.STRING)
    @Column(name = "sector")
    private Sector sector;

    @Column(name = "position")
    private String position;

    @Column(name = "contact_address")
    private String contactAddress;

    @Column(name = "work_phone")
    private Integer workPhone;

    @Column(name = "fax_number")
    private Integer faxNumber;

    @Column(name = "home_phone")
    private Integer homePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "previous_employment")
    private String previousEmployment;

    @Enumerated(EnumType.STRING)
    @Column(name = "special_general")
    private Specialgeneral specialGeneral;

    @Enumerated(EnumType.STRING)
    @Column(name = "special_disaster_management")
    private Specialdisastermanagement specialDisasterManagement;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level")
    private Educationlevel educationLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "trainer")
    private Trainer trainer;

    @Column(name = "comments")
    private String comments;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "participant_entry_session_code",
               joinColumns = @JoinColumn(name = "participant_entry_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "session_code_id", referencedColumnName = "id"))
    private Set<Session> sessionCodes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIndividualCode() {
        return individualCode;
    }

    public ParticipantEntry individualCode(UUID individualCode) {
        this.individualCode = individualCode;
        return this;
    }

    public void setIndividualCode(UUID individualCode) {
        this.individualCode = individualCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public ParticipantEntry firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ParticipantEntry lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Country getCountry() {
        return country;
    }

    public ParticipantEntry country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Gender getGender() {
        return gender;
    }

    public ParticipantEntry gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Title getTitle() {
        return title;
    }

    public ParticipantEntry title(Title title) {
        this.title = title;
        return this;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ParticipantEntry dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOrganization() {
        return organization;
    }

    public ParticipantEntry organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Sector getSector() {
        return sector;
    }

    public ParticipantEntry sector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getPosition() {
        return position;
    }

    public ParticipantEntry position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public ParticipantEntry contactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
        return this;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Integer getWorkPhone() {
        return workPhone;
    }

    public ParticipantEntry workPhone(Integer workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public void setWorkPhone(Integer workPhone) {
        this.workPhone = workPhone;
    }

    public Integer getFaxNumber() {
        return faxNumber;
    }

    public ParticipantEntry faxNumber(Integer faxNumber) {
        this.faxNumber = faxNumber;
        return this;
    }

    public void setFaxNumber(Integer faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Integer getHomePhone() {
        return homePhone;
    }

    public ParticipantEntry homePhone(Integer homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public void setHomePhone(Integer homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public ParticipantEntry email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreviousEmployment() {
        return previousEmployment;
    }

    public ParticipantEntry previousEmployment(String previousEmployment) {
        this.previousEmployment = previousEmployment;
        return this;
    }

    public void setPreviousEmployment(String previousEmployment) {
        this.previousEmployment = previousEmployment;
    }

    public Specialgeneral getSpecialGeneral() {
        return specialGeneral;
    }

    public ParticipantEntry specialGeneral(Specialgeneral specialGeneral) {
        this.specialGeneral = specialGeneral;
        return this;
    }

    public void setSpecialGeneral(Specialgeneral specialGeneral) {
        this.specialGeneral = specialGeneral;
    }

    public Specialdisastermanagement getSpecialDisasterManagement() {
        return specialDisasterManagement;
    }

    public ParticipantEntry specialDisasterManagement(Specialdisastermanagement specialDisasterManagement) {
        this.specialDisasterManagement = specialDisasterManagement;
        return this;
    }

    public void setSpecialDisasterManagement(Specialdisastermanagement specialDisasterManagement) {
        this.specialDisasterManagement = specialDisasterManagement;
    }

    public Educationlevel getEducationLevel() {
        return educationLevel;
    }

    public ParticipantEntry educationLevel(Educationlevel educationLevel) {
        this.educationLevel = educationLevel;
        return this;
    }

    public void setEducationLevel(Educationlevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public ParticipantEntry trainer(Trainer trainer) {
        this.trainer = trainer;
        return this;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getComments() {
        return comments;
    }

    public ParticipantEntry comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Session> getSessionCodes() {
        return sessionCodes;
    }

    public ParticipantEntry sessionCodes(Set<Session> sessions) {
        this.sessionCodes = sessions;
        return this;
    }

    public ParticipantEntry addSessionCode(Session session) {
        this.sessionCodes.add(session);
        session.getIndividualCodes().add(this);
        return this;
    }

    public ParticipantEntry removeSessionCode(Session session) {
        this.sessionCodes.remove(session);
        session.getIndividualCodes().remove(this);
        return this;
    }

    public void setSessionCodes(Set<Session> sessions) {
        this.sessionCodes = sessions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParticipantEntry)) {
            return false;
        }
        return id != null && id.equals(((ParticipantEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ParticipantEntry{" +
            "id=" + getId() +
            ", individualCode='" + getIndividualCode() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", country='" + getCountry() + "'" +
            ", gender='" + getGender() + "'" +
            ", title='" + getTitle() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", sector='" + getSector() + "'" +
            ", position='" + getPosition() + "'" +
            ", contactAddress='" + getContactAddress() + "'" +
            ", workPhone=" + getWorkPhone() +
            ", faxNumber=" + getFaxNumber() +
            ", homePhone=" + getHomePhone() +
            ", email='" + getEmail() + "'" +
            ", previousEmployment='" + getPreviousEmployment() + "'" +
            ", specialGeneral='" + getSpecialGeneral() + "'" +
            ", specialDisasterManagement='" + getSpecialDisasterManagement() + "'" +
            ", educationLevel='" + getEducationLevel() + "'" +
            ", trainer='" + getTrainer() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
