package gov.cdc.nbs.questionbank.valueset.request;

import java.time.Instant;
import java.util.List;
import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetaDatum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValueSetRequest {

	private String assigningAuthorityCd;
	private String assigningAuthorityDescTxt;
	private String codeSetDescTxt;
	private Instant effectiveFromTime;
	private Instant effectiveToTime;
	private Character isModifiableInd;
	private Integer nbsUid;
	private String sourceVersionTxt;
	private String sourceDomainNm;
	private String statusCd;
	private Instant statusToTime;
	private CodeSetGroupMetaDatum codeSetGroup;
	private String adminComments;
	private String valueSetNm;
	private Character ldfPicklistIndCd;
	private String valueSetCode;
	private String valueSetTypeCd;
	private String valueSetOid;
	private String valueSetStatusCd;
	private Instant valueSetStatusTime;
	private Long parentIsCd;
	private Instant addTime;
	private Long addUserId;
	private List<CreateCodedValue> values;

	// ValueSet Concept attached to value set
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateCodedValue {
		private String codeDescTxt;
		private String codeShortDescTxt;
		private String codeSystemCd;
		private String codeSystemDescTxt;
		private Instant effectiveFromTime;
		private Instant effectiveToTime;
		private Short indentLevelNbr;
		private Character isModifiableInd;
		private Integer nbsUid;
		private String parentIsCd;
		private String sourceConceptId;
		private String superCodeSetNm;
		private String superCode;
		private Character statusCd;
		private Instant statusTime;
		private String conceptTypeCd;
		private String conceptCode;
		private String conceptNm;
		private String conceptPreferredNm;
		private String conceptStatusCd;
		private Instant conceptStatusTime;
		private String codeSystemVersionNbr;
		private Integer conceptOrderNbr;
		private String adminComments;
		private Instant addTime;
		private Long addUserId;

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CreateCodesetGroupMetadatum {
		private String codeSetNm;
		private String vadsValueSetCode;
		private String codeSetDescTxt;
		private String codeSetShortDescTxt;
		private Character ldfPicklistIndCd;
		private Character phinStdValInd;

	}
}
