package com.ttcnpm.g36.sharexe.model;

import com.ttcnpm.g36.sharexe.model.audit.OwnerSetting;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "trip")
public class Trip extends OwnerSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String startingPoint;

    @NotNull
    private String destination;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date beginAt;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date endAt;

    @NotNull
    @Size(min = 2)
    private Integer maxCapacity; // this doesn't include driver

    @NotNull
    private Float pricePerPerson;

    private String description;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @OneToMany(mappedBy = "tripInfo", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TripRestriction> restrictions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "participants_in_trip",
            joinColumns = {@JoinColumn(name = "trip_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> participants = new ArrayList<>(); // including both driver and passengers

    public void addRestriction(TripRestriction restriction) {
        this.restrictions.add(restriction);
        restriction.setTripInfo(this);
    }

    public void removeRestriction(TripRestriction restriction) {
        restriction.setTripInfo(null);
        this.restrictions.remove(restriction);
    }

    public void setBeginAt(Date beginAt) {
        this.beginAt = beginAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setPricePerPerson(Float pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    public void setRestrictions(List<TripRestriction> restrictions) {
        this.restrictions = restrictions;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}
