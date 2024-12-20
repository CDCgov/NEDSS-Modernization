package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "codeset")
public class Codeset {
  @EmbeddedId
  private CodesetId id;

  @Column(name = "assigning_authority_cd", length = 199)
  private String assigningAuthorityCd;

  @Column(name = "assigning_authority_desc_txt", length = 100)
  private String assigningAuthorityDescTxt;

  @Column(name = "code_set_desc_txt", length = 2000)
  private String codeSetDescTxt;

  @Column(name = "effective_from_time")
  private Instant effectiveFromTime;

  @Column(name = "effective_to_time")
  private Instant effectiveToTime;

  @Column(name = "is_modifiable_ind")
  private Character isModifiableInd;

  @Column(name = "nbs_uid")
  private Integer nbsUid;

  @Column(name = "source_version_txt", length = 20)
  private String sourceVersionTxt;

  @Column(name = "source_domain_nm", length = 50)
  private String sourceDomainNm;

  @Column(name = "status_cd", length = 1)
  private String statusCd;

  @Column(name = "status_to_time")
  private Instant statusToTime;

  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  @ManyToOne(fetch = FetchType.LAZY, cascade = {
      CascadeType.MERGE,
      CascadeType.REMOVE,
      CascadeType.PERSIST
  })
  @JoinColumn(name = "code_set_group_id")
  private CodeSetGroupMetadatum codeSetGroup;

  @Column(name = "admin_comments", length = 2000)
  private String adminComments;

  @Column(name = "value_set_nm", length = 256)
  private String valueSetNm;

  @Column(name = "ldf_picklist_ind_cd")
  private Character ldfPicklistIndCd;

  @Column(name = "value_set_code", length = 256)
  private String valueSetCode;

  @Column(name = "value_set_type_cd", length = 20)
  private String valueSetTypeCd;

  @Column(name = "value_set_oid", length = 256)
  private String valueSetOid;

  @Column(name = "value_set_status_cd", length = 256)
  private String valueSetStatusCd;

  @Column(name = "value_set_status_time")
  private Instant valueSetStatusTime;

  @Column(name = "parent_is_cd")
  private Long parentIsCd;

  @Column(name = "add_time")
  private Instant addTime;

  @Column(name = "add_user_id")
  private Long addUserId;


  public Codeset(final ValueSetCommand.Add request) {
    String valueSetCodeUpper = request.code().toUpperCase();
    this.valueSetCode = valueSetCodeUpper;
    this.valueSetTypeCd = request.type();
    this.valueSetNm = request.name();
    this.codeSetDescTxt = request.description();
    this.id = new CodesetId("code_value_general", valueSetCodeUpper);
    this.codeSetGroup = new CodeSetGroupMetadatum(
        request.codesetId(),
        request.description(),
        request.name(),
        valueSetCodeUpper);
    created(request.addUserId(), request.addTime());
  }

  public Codeset update(final ValueSetCommand.Update command) {
    this.valueSetNm = command.name();
    this.codeSetDescTxt = command.description();

    this.codeSetGroup.update(command);
    return this;
  }


  private void created(long user, Instant time) {
    this.ldfPicklistIndCd = 'Y';
    this.assigningAuthorityCd = "L";
    this.assigningAuthorityDescTxt = "Local";
    this.isModifiableInd = 'Y';
    this.statusCd = "A";
    this.statusToTime = time;
    this.addTime = time;
    this.addUserId = user;
    this.effectiveFromTime = time;
    this.effectiveToTime = time;
  }

}
