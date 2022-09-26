package gov.cdc.nbs.entity;

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
@Table(name = "NBS_note")
public class NbsNote {
    @Id
    @Column(name = "nbs_note_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "note_parent_uid", nullable = false)
    private PublicHealthCase noteParentUid;

    @Column(name = "record_status_cd", length = 20)
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

    @Column(name = "note", nullable = false, length = 2000)
    private String note;

    @Column(name = "private_ind_cd", nullable = false)
    private Character privateIndCd;

    @Column(name = "type_cd", nullable = false, length = 20)
    private String typeCd;

}