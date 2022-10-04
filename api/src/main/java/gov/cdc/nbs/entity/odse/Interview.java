package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Interview {
    @Id
    @Column(name = "interview_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "interview_uid", nullable = false)
    private Act act;

    @Column(name = "interview_status_cd", length = 20)
    private String interviewStatusCd;

    @Column(name = "interview_date")
    private Instant interviewDate;

    @Column(name = "interviewee_role_cd", length = 20)
    private String intervieweeRoleCd;

    @Column(name = "interview_type_cd", length = 20)
    private String interviewTypeCd;

    @Column(name = "interview_loc_cd", length = 20)
    private String interviewLocCd;

    @Column(name = "local_id", nullable = false, length = 50)
    private String localId;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

}