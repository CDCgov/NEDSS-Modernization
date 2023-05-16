package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Act {
    @Id
    @Column(name = "act_uid", nullable = false)
    private Long id;

    @Column(name = "class_cd", nullable = false, length = 10)
    private String classCd;

    @Column(name = "mood_cd", nullable = false, length = 10)
    private String moodCd;

    @OneToMany(mappedBy = "actUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PublicHealthCase> publicHealthCases;

    @OneToMany(mappedBy = "id.actUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActId> actIds;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Observation> observations;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Intervention> interventions;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Treatment> treatments;

    @OneToMany(mappedBy = "targetActUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActRelationship> targetActRelationships;

    @OneToMany(
        mappedBy = "sourceActUid",
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
        }
    )
    private List<ActRelationship> actRelationships;

    public Act(final long identifier, final String classCd) {
        this.id = identifier;
        this.classCd = classCd;
        this.moodCd = "EVN";
    }

    public void addParticipation(final Participation participation) {
        participation.setActUid(this);
        ensureParticipation().add(participation);
    }

    private List<Participation> ensureParticipation() {
        if (this.participations == null) {
            this.participations = new ArrayList<>();
        }

        return this.participations;
    }

    private List<ActRelationship> ensureRelationships() {
        if(this.actRelationships == null) {
            this.actRelationships = new ArrayList<>();
        }
        return this.actRelationships;
    }

    public void addRelationship(final Act target, final String type) {
        ActRelationship relationship = new ActRelationship(
            this,
            target,
            type
        );
        ensureRelationships().add(relationship);
    }
}
