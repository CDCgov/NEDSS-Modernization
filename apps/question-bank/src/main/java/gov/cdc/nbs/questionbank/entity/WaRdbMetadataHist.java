package gov.cdc.nbs.questionbank.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_RDB_metadata_hist", catalog = "NBS_ODSE")
public class WaRdbMetadataHist {
  @Id
  @Column(name = "wa_rdb_metadata_hist_uid", nullable = false)
  private Long id;

  @Column(name = "wa_rdb_metadata_uid", nullable = false)
  private Long waRdbMetadataUid;

  @Column(name = "wa_template_uid", nullable = false)
  private Long waTemplateUid;

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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wa_template_hist_uid", nullable = false)
  private PageHistoryEntity waTemplateHistUid;

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
