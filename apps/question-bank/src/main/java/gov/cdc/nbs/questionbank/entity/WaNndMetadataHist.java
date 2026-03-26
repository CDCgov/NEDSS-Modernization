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
@Table(name = "WA_NND_metadata_hist", catalog = "NBS_ODSE")
public class WaNndMetadataHist {
  @Id
  @Column(name = "wa_nnd_metadata_hist_uid", nullable = false)
  private Long id;

  @Column(name = "wa_nnd_metadata_uid", nullable = false)
  private Long waNndMetadataUid;

  @Column(name = "wa_template_uid", nullable = false)
  private Long waTemplateUid;

  @Column(name = "question_identifier_nnd", length = 50)
  private String questionIdentifierNnd;

  @Column(name = "question_label_nnd", length = 150)
  private String questionLabelNnd;

  @Column(name = "question_required_nnd", nullable = false)
  private Character questionRequiredNnd;

  @Column(name = "question_data_type_nnd", nullable = false, length = 10)
  private String questionDataTypeNnd;

  @Column(name = "hl7_segment_field", nullable = false, length = 30)
  private String hl7SegmentField;

  @Column(name = "order_group_id", length = 5)
  private String orderGroupId;

  @Column(name = "translation_table_nm", length = 30)
  private String translationTableNm;

  @Column(name = "add_time", nullable = false)
  private Instant addTime;

  @Column(name = "add_user_id", nullable = false)
  private Long addUserId;

  @Column(name = "last_chg_time", nullable = false)
  private Instant lastChgTime;

  @Column(name = "last_chg_user_id", nullable = false)
  private Long lastChgUserId;

  @Column(name = "record_status_cd", nullable = false, length = 20)
  private String recordStatusCd;

  @Column(name = "record_status_time", nullable = false)
  private Instant recordStatusTime;

  @Column(name = "question_identifier", length = 50)
  private String questionIdentifier;

  @Column(name = "xml_path", length = 2000)
  private String xmlPath;

  @Column(name = "xml_tag", length = 300)
  private String xmlTag;

  @Column(name = "xml_data_type", length = 50)
  private String xmlDataType;

  @Column(name = "part_type_cd", length = 50)
  private String partTypeCd;

  @Column(name = "repeat_group_seq_nbr")
  private Integer repeatGroupSeqNbr;

  @Column(name = "question_order_nnd")
  private Integer questionOrderNnd;

  @Column(name = "local_id", length = 50)
  private String localId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wa_template_hist_uid", nullable = false)
  private PageHistoryEntity waTemplateHistUid;

  @Column(name = "wa_ui_metadata_uid", nullable = false)
  private Long waUiMetadataUid;

  @Column(name = "question_map", length = 2000)
  private String questionMap;

  @Column(name = "indicator_cd", length = 2000)
  private String indicatorCd;
}
