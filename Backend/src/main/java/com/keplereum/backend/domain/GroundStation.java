package com.keplereum.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GroundStation.
 */
@Entity
@Table(name = "ground_station")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "groundstation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GroundStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String name;

    @NotNull
    @Column(name = "country", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String country;

    @NotNull
    @Column(name = "contact_email", nullable = false)
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String contactEmail;

    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "access_level")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Integer)
    private Integer accessLevel;

    @Column(name = "status")
    @org.springframework.data.elasticsearch.annotations.Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private String status;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groundStation")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = { "sensors", "trajectories", "model", "networkNode", "groundStation" }, allowSetters = true)
    private Set<Satellite> satellites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GroundStation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public GroundStation name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return this.country;
    }

    public GroundStation country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public GroundStation contactEmail(String contactEmail) {
        this.setContactEmail(contactEmail);
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Integer getAccessLevel() {
        return this.accessLevel;
    }

    public GroundStation accessLevel(Integer accessLevel) {
        this.setAccessLevel(accessLevel);
        return this;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getStatus() {
        return this.status;
    }

    public GroundStation status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public GroundStation latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public GroundStation longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<Satellite> getSatellites() {
        return this.satellites;
    }

    public void setSatellites(Set<Satellite> satellites) {
        if (this.satellites != null) {
            this.satellites.forEach(i -> i.setGroundStation(null));
        }
        if (satellites != null) {
            satellites.forEach(i -> i.setGroundStation(this));
        }
        this.satellites = satellites;
    }

    public GroundStation satellites(Set<Satellite> satellites) {
        this.setSatellites(satellites);
        return this;
    }

    public GroundStation addSatellite(Satellite satellite) {
        this.satellites.add(satellite);
        satellite.setGroundStation(this);
        return this;
    }

    public GroundStation removeSatellite(Satellite satellite) {
        this.satellites.remove(satellite);
        satellite.setGroundStation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroundStation)) {
            return false;
        }
        return getId() != null && getId().equals(((GroundStation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroundStation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", country='" + getCountry() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", accessLevel=" + getAccessLevel() +
            ", status='" + getStatus() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
