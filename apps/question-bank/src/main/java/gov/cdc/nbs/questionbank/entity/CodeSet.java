package gov.cdc.nbs.questionbank.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.entity.srte.CodeValueGeneralId;
import gov.cdc.nbs.entity.srte.CodeValueGeneral.CodeValueGeneralBuilder;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "codeset")
public class CodeSet {
	@EmbeddedId
	private CodeSetId id;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_set_group_id")
	private CodeSetGroupMetaDatum codeSetGroup;

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

	public CodeSet(final ValueSetCommand.AddValueSet  request) {
		this.assigningAuthorityCd = request.assigningAuthorityCd();
		this.assigningAuthorityDescTxt = request.assigningAuthorityDescTxt();
		this.codeSetDescTxt = request.codeSetDescTxt();
		this.effectiveFromTime = request.effectiveFromTime();
		this.effectiveToTime = request.effectiveToTime();
		this.isModifiableInd = request.isModifiableInd();
		this.nbsUid = request.nbsUid();
		this.sourceVersionTxt = request.sourceVersionTxt();
		this.sourceDomainNm = request.sourceDomainNm();
		this.statusCd = request.statusCd();
		this.statusToTime = request.statusToTime();
		this.codeSetGroup = request.codeSetGroup();
		this.adminComments = request.adminComments();
		this.valueSetNm = request.valueSetNm();
		this.ldfPicklistIndCd = request.ldfPicklistIndCd();
		this.valueSetCode = request.valueSetCode();
		this.valueSetTypeCd = request.valueSetTypeCd();
		this.valueSetOid = request.valueSetOid();
		this.valueSetStatusCd = request.valueSetStatusCd();
		this.valueSetStatusTime = request.valueSetStatusTime();
		this.parentIsCd = request.parentIsCd();
		this.addTime = request.addTime();
		this.addUserId = request.addUserId();

	}

}