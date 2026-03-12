package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_RDB_metadata", catalog = "NBS_ODSE")
@SuppressWarnings({
  "javaarchitecture:S7027",
  "javaarchitecture:S7091"
}) //  Bidirectional mappings require knowledge of each other
public class WaRdbMetadata {
  public static final String ACTIVE = "Active";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wa_ui_metadata_uid", nullable = false)
  private WaUiMetadata waUiMetadataUid;

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

  public void groupSubsectionQuestions(PageContentCommand.GroupSubsection command) {
    this.blockPivotNbr = command.repeatingNbr();
    changed(command);
  }

  public void unGroupSubsectionQuestions(PageContentCommand.UnGroupSubsection command) {
    this.blockPivotNbr = null;
    changed(command);
  }

  public void changed(PageContentCommand command) {
    this.lastChgTime = command.requestedOn();
    this.lastChgUserId = command.userId();
  }

  public WaRdbMetadata() {
    this.recordStatusCd = ACTIVE;
  }

  public WaRdbMetadata(
      WaTemplate template, WaUiMetadata waUiMetadata, Instant addedOn, long addedBy) {
    this();
    this.waTemplateUid = template;
    this.waUiMetadataUid = waUiMetadata;
    this.addTime = addedOn;
    this.addUserId = addedBy;
    this.recordStatusTime = addedOn;
  }

  public WaRdbMetadata(
      WaTemplate page, WaUiMetadata waUiMetadata, PageContentCommand.AddQuestion command) {
    WaQuestion question = command.question();
    this.setRdbColumnNm(question.getRdbColumnNm());
    this.setRdbTableNm(question.getRdbTableNm());
    this.setRptAdminColumnNm(question.getRptAdminColumnNm());
    this.setUserDefinedColumnNm(question.getUserDefinedColumnNm());
    this.setWaTemplateUid(page);
    this.setQuestionIdentifier(question.getQuestionIdentifier());
    this.setLocalId(question.getLocalId());
    this.setWaUiMetadataUid(waUiMetadata);
    added(command);
  }

  public WaRdbMetadata(WaUiMetadata waUiMetadata, PageContentCommand.QuestionUpdate command) {
    this.setRdbColumnNm(command.datamartInfo().rdbColumnName());
    this.setRdbTableNm(command.datamartInfo().defaultRdbTableName());
    this.setRptAdminColumnNm(command.datamartInfo().reportLabel());
    this.setUserDefinedColumnNm(command.datamartInfo().dataMartColumnName());
    this.setWaTemplateUid(waUiMetadata.getWaTemplateUid());
    this.setQuestionIdentifier(waUiMetadata.getQuestionIdentifier());
    this.setWaUiMetadataUid(waUiMetadata);
    added(command);
  }

  private void added(PageContentCommand command) {
    this.addTime = command.requestedOn();
    this.addUserId = command.userId();
    this.lastChgTime = command.requestedOn();
    this.lastChgUserId = command.userId();
    this.recordStatusCd = ACTIVE;
    this.recordStatusTime = command.requestedOn();
  }

  public void update(
      String defaultLabelInReport, String dataMartColumnName, long user, Instant requestedOn) {
    this.rptAdminColumnNm = defaultLabelInReport;
    this.userDefinedColumnNm = dataMartColumnName;
    this.lastChgTime = requestedOn;
    this.lastChgUserId = user;
  }

  public static WaRdbMetadata clone(
      WaRdbMetadata original, WaTemplate template, WaUiMetadata metadata) {
    return new WaRdbMetadata(
        null,
        template,
        original.userDefinedColumnNm,
        original.recordStatusCd,
        original.recordStatusTime,
        original.addUserId,
        original.addTime,
        original.lastChgTime,
        original.lastChgUserId,
        original.localId,
        metadata,
        original.rdbTableNm,
        original.rptAdminColumnNm,
        original.rdbColumnNm,
        original.questionIdentifier,
        original.blockPivotNbr);
  }
}
