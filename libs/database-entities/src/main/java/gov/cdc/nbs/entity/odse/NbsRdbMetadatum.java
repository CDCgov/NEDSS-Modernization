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
@Table(name = "NBS_rdb_metadata")
public class NbsRdbMetadatum {
    @Id
    @Column(name = "nbs_rdb_metadata_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nbs_page_uid")
    private NbsPage nbsPageUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_ui_metadata_uid", nullable = false)
    private NbsUiMetadatum nbsUiMetadataUid;

    @Column(name = "rdb_table_nm", length = 30)
    private String rdbTableNm;

    @Column(name = "user_defined_column_nm", length = 30)
    private String userDefinedColumnNm;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "rpt_admin_column_nm", length = 50)
    private String rptAdminColumnNm;

    @Column(name = "rdb_column_nm", length = 30)
    private String rdbColumnNm;

    @Column(name = "block_pivot_nbr")
    private Integer blockPivotNbr;

}
