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
@Table(name = "NBS_table_metadata")
public class NbsTableMetadatum {
    @Id
    @Column(name = "nbs_table_metadata_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_table_uid", nullable = false)
    private NbsTable nbsTableUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_indicator_uid", nullable = false)
    private NbsIndicator nbsIndicatorUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_aggregate_uid", nullable = false)
    private NbsAggregate nbsAggregateUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_question_uid", nullable = false)
    private NbsQuestion nbsQuestionUid;

    @Column(name = "datamart_column_nm", length = 30)
    private String datamartColumnNm;

    @Column(name = "aggregate_seq_nbr", nullable = false)
    private Integer aggregateSeqNbr;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "indicator_seq_nbr", nullable = false)
    private Integer indicatorSeqNbr;

    @Column(name = "msg_exclude_ind_cd", length = 20)
    private String msgExcludeIndCd;

    @Column(name = "question_identifier", length = 50)
    private String questionIdentifier;

}