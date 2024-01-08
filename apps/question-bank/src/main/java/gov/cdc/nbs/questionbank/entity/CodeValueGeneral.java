package gov.cdc.nbs.questionbank.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import gov.cdc.nbs.questionbank.valueset.command.ConceptCommand;
import gov.cdc.nbs.questionbank.valueset.request.AddConceptRequest.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "Code_value_general")
public class CodeValueGeneral {
  @EmbeddedId
  private CodeValueGeneralId id;

  @Column(name = "code_desc_txt", length = 300)
  private String codeDescTxt;

  @Column(name = "code_short_desc_txt", length = 100)
  private String codeShortDescTxt;

  @Column(name = "code_system_cd", length = 300)
  private String codeSystemCd;

  @Column(name = "code_system_desc_txt", length = 100)
  private String codeSystemDescTxt;

  @Column(name = "effective_from_time")
  private Instant effectiveFromTime;

  @Column(name = "effective_to_time")
  private Instant effectiveToTime;

  @Column(name = "indent_level_nbr")
  private Short indentLevelNbr;

  @Column(name = "is_modifiable_ind")
  private Character isModifiableInd;

  @Column(name = "nbs_uid")
  private Integer nbsUid;

  @Column(name = "parent_is_cd", length = 20)
  private String parentIsCd;

  @Column(name = "source_concept_id", length = 20)
  private String sourceConceptId;

  @Column(name = "super_code_set_nm", length = 256)
  private String superCodeSetNm;

  @Column(name = "super_code", length = 20)
  private String superCode;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "status_time")
  private Instant statusTime;

  @Column(name = "concept_type_cd", length = 20)
  private String conceptTypeCd;

  @Column(name = "concept_code", length = 256)
  private String conceptCode;

  @Column(name = "concept_nm", length = 256)
  private String conceptNm;

  @Column(name = "concept_preferred_nm", length = 256)
  private String conceptPreferredNm;

  @Column(name = "concept_status_cd", length = 256)
  private String conceptStatusCd;

  @Column(name = "concept_status_time")
  private Instant conceptStatusTime;

  @Column(name = "code_system_version_nbr", length = 256)
  private String codeSystemVersionNbr;

  @Column(name = "concept_order_nbr")
  private Integer conceptOrderNbr;

  @Column(name = "admin_comments", length = 2000)
  private String adminComments;

  @Column(name = "add_time")
  private Instant addTime;

  @Column(name = "add_user_id")
  private Long addUserId;

  public CodeValueGeneral() {
    this.indentLevelNbr = 1;
    this.isModifiableInd = 'Y';
  }

  public CodeValueGeneral(ConceptCommand.AddConcept command) {
    // Default
    this();

    this.id = new CodeValueGeneralId(command.codeset(), command.code());
    this.codeDescTxt = command.displayName();
    this.codeShortDescTxt = command.shortDisplayName();

    this.statusCd = command.statusCode().equals(StatusCode.A) ? 'A' : 'I';
    this.statusTime = command.requestedOn();
    this.conceptStatusCd = command.statusCode().equals(StatusCode.A) ? "Active" : "Inactive";
    this.conceptStatusTime = command.requestedOn();
    this.adminComments = command.adminComments();
    this.conceptCode = command.conceptCode();
    this.conceptNm = command.conceptName();
    this.conceptPreferredNm = command.preferredConceptName();
    this.codeSystemDescTxt = command.codeSystem();
    this.codeSystemCd = command.codeSystemId();
    this.conceptTypeCd = command.conceptTypeCd();

    added(command);
  }

  public CodeValueGeneral updatValueGeneral(ConceptCommand.UpdateConcept command) {

    this.codeShortDescTxt = command.displayName();
    this.effectiveFromTime = Instant.now();
    this.effectiveToTime = (command.effectiveToTime() == null) ? this.effectiveToTime : command.effectiveToTime();
    this.statusCd = (command.active()) ? 'A' : 'I';
    this.conceptStatusCd = (command.active()) ? "Active" : "Inactive";
    this.conceptCode = (command.conceptCode() == null) ? this.conceptCode : command.conceptCode();
    this.conceptNm = (command.conceptName() == null) ? this.conceptNm : command.conceptName();
    this.conceptPreferredNm =
        (command.preferredConceptName() == null) ? this.conceptPreferredNm : command.preferredConceptName();
    this.codeSystemCd = (command.codeSystem() == null) ? this.codeSystemCd : command.codeSystem();
    return this;
  }

  private void added(ConceptCommand command) {
    this.addTime = command.requestedOn();
    this.addUserId = command.userId();
  }



}
