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
@Table(name = "state_defined_field_metadata")
public class StateDefinedFieldMetadatum {
    @Id
    @Column(name = "ldf_uid", nullable = false)
    private Long id;

    @Column(name = "active_ind")
    private Character activeInd;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "admin_comment", length = 300)
    private String adminComment;

    @Column(name = "business_object_nm", length = 50)
    private String businessObjectNm;

    @Column(name = "category_type", length = 50)
    private String categoryType;

    @Column(name = "cdc_national_id", length = 50)
    private String cdcNationalId;

    @Column(name = "class_cd", length = 20)
    private String classCd;

    @Column(name = "code_set_nm", length = 256)
    private String codeSetNm;

    @Column(name = "condition_cd", length = 10)
    private String conditionCd;

    @Column(name = "condition_desc_txt", length = 100)
    private String conditionDescTxt;

    @Column(name = "data_type", length = 50)
    private String dataType;

    @Column(name = "deployment_cd", length = 10)
    private String deploymentCd;

    @Column(name = "display_order_nbr")
    private Integer displayOrderNbr;

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    @Column(name = "label_txt", length = 300)
    private String labelTxt;

    @Column(name = "ldf_page_id", length = 50)
    private String ldfPageId;

    @Column(name = "required_ind")
    private Character requiredInd;

    @Column(name = "state_cd", length = 10)
    private String stateCd;

    @Column(name = "validation_txt", length = 50)
    private String validationTxt;

    @Column(name = "validation_jscript_txt", length = 3000)
    private String validationJscriptTxt;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_subform_metadata_uid")
    private CustomSubformMetadatum customSubformMetadataUid;

    @Column(name = "html_tag", length = 50)
    private String htmlTag;

    @Column(name = "import_version_nbr")
    private Long importVersionNbr;

    @Column(name = "nnd_ind")
    private Character nndInd;

    @Column(name = "ldf_oid", length = 50)
    private String ldfOid;

    @Column(name = "version_ctrl_nbr")
    private Short versionCtrlNbr;

    @Column(name = "NBS_QUESTION_UID")
    private Long nbsQuestionUid;

}
