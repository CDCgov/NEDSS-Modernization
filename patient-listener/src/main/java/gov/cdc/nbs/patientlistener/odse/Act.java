package gov.cdc.nbs.patientlistener.odse;

import java.util.List;

/*
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;*/

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
}