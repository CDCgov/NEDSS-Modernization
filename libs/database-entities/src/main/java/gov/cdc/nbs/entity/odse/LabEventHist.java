package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lab_event_hist")
public class LabEventHist {
    @Id
    @Column(name = "lab_event_hist_uid", nullable = false)
    private Long id;

    @Column(name = "lab_event_uid", nullable = false)
    private Long labEventUid;

    @Column(name = "observation_uid")
    private Long observationUid;

    @Column(name = "investigation_uid")
    private Long investigationUid;

    @Column(name = "notification_uid")
    private Long notificationUid;

    @Column(name = "result_uid")
    private Long resultUid;

    @Column(name = "susceptibility_uid")
    private Long susceptibilityUid;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "lab_result_status_cd")
    private Character labResultStatusCd;

    @Column(name = "ref_range_frm", length = 20)
    private String refRangeFrm;

    @Column(name = "ref_range_to", length = 20)
    private String refRangeTo;

    @Column(name = "specimen_qty", length = 20)
    private String specimenQty;

    @Column(name = "ordered_lab_test_cd", length = 50)
    private String orderedLabTestCd;

    @Column(name = "numeric_value_2", precision = 38, scale = 5)
    private BigDecimal numericValue2;

    @Column(name = "comparator_cd_1", length = 10)
    private String comparatorCd1;

    @Column(name = "numeric_value_1", length = 100)
    private String numericValue1;

    @Column(name = "separator_cd", length = 10)
    private String separatorCd;

    @Column(name = "numeric_unit_cd", length = 20)
    private String numericUnitCd;

    @Column(name = "interpretation_cd", length = 2000)
    private String interpretationCd;

    @Column(name = "reason_for_test_cd", length = 2000)
    private String reasonForTestCd;

    @Column(name = "target_site_cd", length = 20)
    private String targetSiteCd;

    @Column(name = "lab_rpt_status_cd")
    private Character labRptStatusCd;

    @Column(name = "result_rpt_dt")
    private Instant resultRptDt;

    @Column(name = "lab_result_txt_val", length = 2000)
    private String labResultTxtVal;

    @Column(name = "resultedtest_cd", length = 20)
    private String resultedtestCd;

    @Column(name = "specimen_analyzed_dt")
    private Instant specimenAnalyzedDt;

    @Column(name = "specimen_src_desc", length = 100)
    private String specimenSrcDesc;

    @Column(name = "person_uid")
    private Long personUid;

    @Column(name = "specimen_collection_dt")
    private Instant specimenCollectionDt;

    @Column(name = "accession_nbr", length = 100)
    private String accessionNbr;

    @Column(name = "specimen_qty_unit", length = 20)
    private String specimenQtyUnit;

    @Column(name = "lab_result_comments", length = 2000)
    private String labResultComments;

    @Column(name = "test_method_cd", length = 2000)
    private String testMethodCd;

    @Column(name = "organization_uid")
    private Long organizationUid;

    @Column(name = "specimen_src_cd", length = 50)
    private String specimenSrcCd;

    @Column(name = "resulted_lab_test_cd", length = 50)
    private String resultedLabTestCd;

    @Column(name = "resulted_lab_test_drug_cd", length = 50)
    private String resultedLabTestDrugCd;

    @Column(name = "suscep_lab_result_txt_val", length = 2000)
    private String suscepLabResultTxtVal;

    @Column(name = "suscep_resultedtest_cd", length = 20)
    private String suscepResultedtestCd;

    @Column(name = "suscep_comparator_cd_1", length = 10)
    private String suscepComparatorCd1;

    @Column(name = "suscep_numeric_value_1", length = 100)
    private String suscepNumericValue1;

    @Column(name = "suscep_separator_cd", length = 10)
    private String suscepSeparatorCd;

    @Column(name = "suscep_numeric_value_2", precision = 15, scale = 5)
    private BigDecimal suscepNumericValue2;

    @Column(name = "suscep_numeric_unit_cd", length = 20)
    private String suscepNumericUnitCd;

    @Column(name = "suscep_ref_range_frm", length = 20)
    private String suscepRefRangeFrm;

    @Column(name = "suscep_ref_range_to", length = 20)
    private String suscepRefRangeTo;

    @Column(name = "suscep_lab_result_comments", length = 2000)
    private String suscepLabResultComments;

    @Column(name = "suscep_lab_rslt_status_cd")
    private Character suscepLabRsltStatusCd;

    @Column(name = "suscep_test_method_cd", length = 2000)
    private String suscepTestMethodCd;

    @Column(name = "suscep_interpretation_cd", length = 20)
    private String suscepInterpretationCd;

    @Column(name = "suscep_lab_rpt_status_cd")
    private Character suscepLabRptStatusCd;

    @Column(name = "suscep_accession_nbr", length = 100)
    private String suscepAccessionNbr;

    @Column(name = "suscep_ordered_lab_test_cd", length = 50)
    private String suscepOrderedLabTestCd;

    @Column(name = "suscep_specimen_collection_dt")
    private Instant suscepSpecimenCollectionDt;

    @Column(name = "suscep_result_rpt_dt")
    private Instant suscepResultRptDt;

}
