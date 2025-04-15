package gov.cdc.nbs.questionbank.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
// Bidirectional mappings require knowledge of each other
@SuppressWarnings({ "javaarchitecture:S7027", "javaarchitecture:S7091" })
@Table(name = "WA_NND_metadata", catalog = "NBS_ODSE")
public class WaNndMetadatum {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wa_nnd_metadata_uid", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wa_template_uid", nullable = false)
  private WaTemplate waTemplateUid;

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

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wa_ui_metadata_uid", nullable = false)
  private WaUiMetadata waUiMetadataUid;

  @Column(name = "question_map", length = 2000)
  private String questionMap;

  @Column(name = "indicator_cd", length = 2000)
  private String indicatorCd;

  public WaNndMetadatum(
      WaUiMetadata uiMetadata,
      String messageVariableId,
      String label,
      boolean required,
      String hl7DataType,
      long user,
      Instant requestedOn) {
    this.waTemplateUid = uiMetadata.getWaTemplateUid();
    this.waUiMetadataUid = uiMetadata;
    this.hl7SegmentField = "OBX-3.0";
    this.orderGroupId = "2";

    this.questionRequiredNnd = required ? 'R' : 'O';
    this.questionIdentifierNnd = messageVariableId;
    this.questionLabelNnd = label;
    this.questionDataTypeNnd = hl7DataType;
    this.questionIdentifier = uiMetadata.getQuestionIdentifier();

    added(user, requestedOn);
  }

  public void update(
      String messageVariableId,
      String labelInMessage,
      boolean requiredInMessage,
      String hl7DataType,
      long user,
      Instant requestedOn) {
    this.questionIdentifierNnd = messageVariableId;
    this.questionLabelNnd = labelInMessage;
    this.questionRequiredNnd = requiredInMessage ? 'R' : 'O';
    this.questionDataTypeNnd = hl7DataType;

    changed(user, requestedOn);
  }

  private void added(long user, Instant requestedOn) {
    this.addUserId = user;
    this.addTime = requestedOn;
    this.lastChgUserId = user;
    this.lastChgTime = requestedOn;
    this.recordStatusCd = "Active";
    this.recordStatusTime = requestedOn;
  }

  private void changed(long user, Instant requestedOn) {
    this.lastChgUserId = user;
    this.lastChgTime = requestedOn;
  }

  public static WaNndMetadatum clone(WaNndMetadatum original, WaTemplate template, WaUiMetadata metadata) {
    return new WaNndMetadatum(
        null,
        template,
        original.questionIdentifierNnd,
        original.questionLabelNnd,
        original.questionRequiredNnd,
        original.questionDataTypeNnd,
        original.hl7SegmentField,
        original.orderGroupId,
        original.translationTableNm,
        original.addTime,
        original.addUserId,
        original.lastChgTime,
        original.lastChgUserId,
        original.recordStatusCd,
        original.recordStatusTime,
        original.questionIdentifier,
        original.xmlPath,
        original.xmlTag,
        original.xmlDataType,
        original.partTypeCd,
        original.repeatGroupSeqNbr,
        original.questionOrderNnd,
        original.localId,
        metadata,
        original.questionMap,
        original.indicatorCd);
  }

}
