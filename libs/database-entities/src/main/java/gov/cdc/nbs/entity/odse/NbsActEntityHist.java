package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NBS_act_entity_hist")
public class NbsActEntityHist {
    @Id
    @Column(name = "nbs_act_entity_hist_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "act_uid", nullable = false)
    private Act actUid;

    @Column(name = "act_version_ctrl_nbr", nullable = false)
    private Short actVersionCtrlNbr;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_uid", nullable = false)
    private NBSEntity nbsEntityUid;

    @Column(name = "entity_version_ctrl_nbr", nullable = false)
    private Short entityVersionCtrlNbr;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "nbs_act_entity_uid", nullable = false)
    private Long nbsActEntityUid;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "type_cd", length = 50)
    private String typeCd;

}
