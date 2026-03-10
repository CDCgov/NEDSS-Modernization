package gov.cdc.nbs.questionbank.entity.condition;

import gov.cdc.nbs.questionbank.condition.command.ConditionCommand;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Condition_code")
@SuppressWarnings(
    "javaarchitecture:S7027") //  Bidirectional mappings require knowledge of each other
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

  @Column(name = "prog_area_cd")
  private String progAreaCd;

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

  @Column(name = "rhap_parse_nbs_ind")
  private String rhapParseNbsInd;

  @Column(name = "rhap_action_value")
  private String rhapActionValue;

  @OneToMany(mappedBy = "conditionCd", cascade = CascadeType.ALL)
  private List<LdfPageSet> ldfPageSets = new ArrayList<>();

  public ConditionCode(final ConditionCommand.AddCondition command) {
    Instant now = Instant.now();
    this.id = command.id();
    this.codeSystemDescTxt = command.codeSystemDescTxt();
    this.conditionShortNm = command.conditionShortNm();
    this.conditionDescTxt = command.conditionShortNm();
    this.nndInd = command.nndInd();
    this.progAreaCd = command.progAreaCd();
    this.reportableMorbidityInd = command.reportableMorbidityInd();
    this.reportableSummaryInd = command.reportableSummaryInd();
    this.contactTracingEnableInd = command.contactTracingEnableInd();
    this.familyCd = command.familyCd();
    this.coinfectionGrpCd = command.coinfectionGrpCd();

    this.nndInd = command.nndInd();
    this.reportableMorbidityInd = command.reportableMorbidityInd();
    this.reportableSummaryInd = command.reportableSummaryInd();
    this.contactTracingEnableInd = command.contactTracingEnableInd();
    this.isModifiableInd = 'N';

    // Defaults
    this.indentLevelNbr = 1;
    this.conditionCodesetNm = "PHC_TYPE";
    this.conditionSeqNum = 1;
    this.assigningAuthorityCd = "2.16.840.1.114222";
    this.assigningAuthorityDescTxt = "Centers for Disease Control";

    this.effectiveFromTime = now;
    this.statusCd = 'A';
    this.statusTime = now;

    this.vaccineEnableInd = 'N';
    this.treatmentEnableInd = 'Y';
    this.labReportEnableInd = 'N';
    this.morbReportEnableInd = 'Y';
    this.portReqIndCd = 'F';

    // code system
    if (codeSystemDescTxt.equalsIgnoreCase("Local")) {
      this.codeSystemCd = "L";
    } else if (codeSystemDescTxt.equalsIgnoreCase("SNOMED-CT")) {
      this.codeSystemCd = "2.16.840.1.113883.6.96";
    } else {
      this.codeSystemCd = "2.16.840.1.114222.4.5.277";
    }

    this.ldfPageSets = new ArrayList<>();
  }

  public void setLdf(LdfPageSet... ldf) {
    this.ldfPageSets.addAll(Arrays.asList(ldf));
  }
}
