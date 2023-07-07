package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Role_hist")
public class RoleHist {
    @EmbeddedId
    private RoleHistId id;

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

    @Column(name = "status_time", nullable = false)
    private Instant statusTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "subject_class_cd", length = 10)
    private String subjectClassCd;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

}
