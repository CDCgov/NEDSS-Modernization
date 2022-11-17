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
@Table(name = "WA_RDB_metadata")
public class WaRdbMetadatum {
    @Id
    @Column(name = "wa_rdb_metadata_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wa_template_uid", nullable = false)
    private WaTemplate waTemplateUid;

    @Column(name = "user_defined_column_nm", length = 30)
    private String userDefinedColumnNm;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "wa_ui_metadata_uid")
    private Long waUiMetadataUid;

    @Column(name = "rdb_table_nm", length = 30)
    private String rdbTableNm;

    @Column(name = "rpt_admin_column_nm", length = 50)
    private String rptAdminColumnNm;

    @Column(name = "rdb_column_nm", length = 30)
    private String rdbColumnNm;

    @Column(name = "question_identifier", length = 50)
    private String questionIdentifier;

    @Column(name = "block_pivot_nbr")
    private Integer blockPivotNbr;

}