package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NND_metadata_hist")
public class NndMetadataHist {
    @Id
    @Column(name = "nnd_metadata_hist_uid", nullable = false)
    private Long id;

    @Column(name = "nnd_metadata_uid", nullable = false)
    private Long nndMetadataUid;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "HL7_segment_field", length = 30)
    private String hl7SegmentField;

    @Column(name = "investigation_form_cd", length = 50)
    private String investigationFormCd;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "order_group_id", length = 5)
    private String orderGroupId;

    @Column(name = "question_identifier_nnd", length = 50)
    private String questionIdentifierNnd;

    @Column(name = "question_identifier", nullable = false, length = 50)
    private String questionIdentifier;

    @Column(name = "question_label_nnd", length = 150)
    private String questionLabelNnd;

    @Column(name = "question_required_nnd", nullable = false)
    private Character questionRequiredNnd;

    @Column(name = "question_data_type_nnd", length = 10)
    private String questionDataTypeNnd;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "translation_table_nm", length = 30)
    private String translationTableNm;

    @Column(name = "repeat_group_seq_nbr")
    private Integer repeatGroupSeqNbr;

    @Column(name = "question_order_nnd")
    private Integer questionOrderNnd;

    @Column(name = "msg_trigger_ind_cd")
    private Character msgTriggerIndCd;

    @Column(name = "xml_path", length = 2000)
    private String xmlPath;

    @Column(name = "xml_tag", length = 300)
    private String xmlTag;

    @Column(name = "xml_data_type", length = 50)
    private String xmlDataType;

    @Column(name = "part_type_cd", length = 50)
    private String partTypeCd;

    @Column(name = "nbs_page_uid")
    private Long nbsPageUid;

    @Column(name = "nbs_ui_metadata_uid")
    private Long nbsUiMetadataUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Integer versionCtrlNbr;

    @Column(name = "question_map", length = 2000)
    private String questionMap;

    @Column(name = "indicator_cd", length = 2000)
    private String indicatorCd;

}