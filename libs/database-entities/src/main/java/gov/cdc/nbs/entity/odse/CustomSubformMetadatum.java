package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Custom_subform_metadata")
public class CustomSubformMetadatum {
    @Id
    @Column(name = "custom_subform_metadata_uid", nullable = false)
    private Long id;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "admin_comment", length = 300)
    private String adminComment;

    @Column(name = "business_object_nm", length = 50)
    private String businessObjectNm;

    @Column(name = "class_cd", length = 20)
    private String classCd;

    @Column(name = "condition_cd", length = 20)
    private String conditionCd;

    @Column(name = "condition_desc_txt", length = 100)
    private String conditionDescTxt;

    @Column(name = "display_order_nbr", nullable = false)
    private Integer displayOrderNbr;

    @Lob
    @Column(name = "html_data", columnDefinition = "TEXT")
    private String htmlData;

    @Column(name = "import_version_nbr", nullable = false)
    private Long importVersionNbr;

    @Column(name = "deployment_cd", length = 20)
    private String deploymentCd;

    @Column(name = "page_set_id", length = 50)
    private String pageSetId;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "state_cd", length = 20)
    private String stateCd;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "subform_oid", length = 50)
    private String subformOid;

    @Column(name = "subform_nm", length = 100)
    private String subformNm;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

}
