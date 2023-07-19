package gov.cdc.nbs.questionbank.entity.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Condition_code")
public class ConditionCode implements Serializable {
    @Id
    @Column(name = "condition_cd", nullable = false, length = 20)
    private String id;

    @Column(name = "condition_codeset_nm", length = 256)
    private String conditionCodesetNm;

    @Column(name = "condition_seq_num")
    private Short conditionSeqNum;

    @Column(name = "assigning_authority_cd", length = 199)
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "code_system_cd", length = 300)
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt", length = 100)
    private String codeSystemDescTxt;

    @Column(name = "condition_desc_txt", length = 300)
    private String conditionDescTxt;

    @Column(name = "condition_short_nm", length = 50)
    private String conditionShortNm;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "indent_level_nbr")
    private Short indentLevelNbr;

    @Column(name = "investigation_form_cd", length = 50)
    private String investigationFormCd;

    @Column(name = "is_modifiable_ind")
    private Character isModifiableInd;

    @Column(name = "nbs_uid")
    private Long nbsUid;

    @Column(name = "nnd_ind", nullable = false)
    private Character nndInd;

    @Column(name = "parent_is_cd", length = 20)
    private String parentIsCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prog_area_cd")
    private ProgramAreaCode progAreaCd;

    @Column(name = "reportable_morbidity_ind", nullable = false)
    private Character reportableMorbidityInd;

    @Column(name = "reportable_summary_ind", nullable = false)
    private Character reportableSummaryInd;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "nnd_entity_identifier", length = 200)
    private String nndEntityIdentifier;

    @Column(name = "nnd_summary_entity_identifier", length = 200)
    private String nndSummaryEntityIdentifier;

    @Column(name = "summary_investigation_form_cd", length = 50)
    private String summaryInvestigationFormCd;

    @Column(name = "contact_tracing_enable_ind")
    private Character contactTracingEnableInd;

    @Column(name = "vaccine_enable_ind")
    private Character vaccineEnableInd;

    @Column(name = "treatment_enable_ind")
    private Character treatmentEnableInd;

    @Column(name = "lab_report_enable_ind")
    private Character labReportEnableInd;

    @Column(name = "morb_report_enable_ind")
    private Character morbReportEnableInd;

    @Column(name = "port_req_ind_cd")
    private Character portReqIndCd;

    @Column(name = "family_cd", length = 256)
    private String familyCd;

    @Column(name = "coinfection_grp_cd", length = 20)
    private String coinfectionGrpCd;

    @OneToMany(mappedBy = "conditionCd")
    private Set<LdfPageSet> ldfPageSets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "defaultConditionCd")
    private Set<LabResult> labResults = new LinkedHashSet<>();

    @OneToMany(mappedBy = "conditionCd")
    private Set<LoincSnomedCondition> loincSnomedConditions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "defaultConditionCd")
    private Set<LabTest> labTests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "id.snomedCd")
    private Set<SnomedCondition> snomedConditions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "conditionCd")
    private Set<LoincCondition> loincConditions = new LinkedHashSet<>();

    public ConditionCode(final ConditionCommand.AddCondition request) {
        this.id = request.getId();
        this.conditionCodesetNm = request.getConditionCodesetNm();
        this.conditionSeqNm = request.getConditionSeqNm();
        this.assigningAuthorityCd = request.getAssigningAuthorityCd();
        this.assigningAuthorityDescText = request.getAssigningAuthorityDescText();
        this.codeSystemCd = request.getCodeSystemCd();
        this.codeSystemDescTxt = request.getCodeSystemDescTxt();
        this.conditionDescTxt = request.getConditionDescTxt();
        this.conditionShortNm = request.getConditionShortNm();
        this.effectiveFromTime = request.getEffectiveFromTime();
        this.effectiveToTime = request.getEffectiveToTime();
        this.indentLevelNbr = request.getIndentLevelNbr();
        this.investigationFormCd = request.getInvestigationFormCd();
        this.isModifiableInd = request.getIsModifiableInd();
        this.nbsUid = request.getNbsUid();
        this.nndInd = request.getNndInd();
        this.parentIsCd = request.getParentIsCd();
        this.progAreaCd = request.getProgAreaCd();
        this.reportableMorbidityInd = request.getReportableMorbidityInd();
        this.reportableSummaryInd = request.getReportableSummaryInd();
        this.statusCd = request.getStatusCd();
        this.statusTime = request.getStatusTime();
        this.nndEntityIdentifier = request.getNndEntityIdentifier();
        this.nndSummaryEntityIdentifier = request.getNndSummaryEntityIdentifier();
        this.summaryInvestigationFormCd = request.getSummaryInvestigationFormCd();
        this.contactTracingEnable = request.getContactTracingEnable();
        this.vaccineEnable = request.getVaccineEnable();
        this.treatmentEnable = request.getTreatmentEnable();
        this.labReportEnable = request.getLabReportEnable();
        this.morbReportEnable = request.getMorbReportEnable();
        this.portReqIndCd = request.getPortReqIndCd();
        this.familyCd = request.getFamilyCd();
        this.coinfectionGrpCd = request.getCoinfectionGrpCd();
        this.rhapParseNbsInd = request.getRhapParseNbsInd();
        this.rhapActionValue = request.getRhapActionValue();
    }

}