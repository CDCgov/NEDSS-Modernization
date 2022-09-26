package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person_merge")
public class PersonMerge {
    @EmbeddedId
    private PersonMergeId id;

    @MapsId("survivingPersonUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "surviving_person_uid", nullable = false)
    private Person survivingPersonUid;

    @Column(name = "superceded_parent_uid", nullable = false)
    private Long supercededParentUid;

    @Column(name = "surviving_version_ctrl_nbr")
    private Short survivingVersionCtrlNbr;

    @Column(name = "surviving_parent_uid", nullable = false)
    private Long survivingParentUid;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "merge_user_id", length = 20)
    private String mergeUserId;

    @Column(name = "merge_time")
    private Instant mergeTime;

}