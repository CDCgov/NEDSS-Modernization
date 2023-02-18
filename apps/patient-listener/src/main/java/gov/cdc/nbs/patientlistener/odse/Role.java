package gov.cdc.nbs.patientlistener.odse;

import java.time.Instant;


/*
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;*/

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role {
    @EmbeddedId
    private RoleId id;

    @MapsId("subjectEntityUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_entity_uid", nullable = false)
    private NBSEntity subjectEntityUid;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "effective_duration_amt", length = 20)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "scoping_class_cd", length = 10)
    private String scopingClassCd;

    @Column(name = "scoping_entity_uid")
    private Long scopingEntityUid;

    @Column(name = "scoping_role_cd", length = 20)
    private String scopingRoleCd;

    @Column(name = "scoping_role_seq")
    private Short scopingRoleSeq;

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "subject_class_cd", length = 10)
    private String subjectClassCd;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

}