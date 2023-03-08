package gov.cdc.nbs.entity.odse;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Observation> observations;

    @OneToMany(mappedBy = "targetActUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActRelationship> targetActRelationships;

    @OneToMany(mappedBy = "sourceActUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ActRelationship> actRelationships;

    public void addParticipation(final Participation participation) {
        participation.setActUid(this);
        ensureParticipation().add(participation);
    }

    private List<Participation> ensureParticipation() {
        if(this.participations == null) {
            this.participations = new ArrayList<>();
        }

        return this.participations;
    }
}
