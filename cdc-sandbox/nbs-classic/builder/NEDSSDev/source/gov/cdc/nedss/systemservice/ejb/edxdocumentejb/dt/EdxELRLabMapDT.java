package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;

import java.sql.Timestamp;

/**
 * 
 * @author Pradeep Kumar Sharma
 *EdxELRLabMapDT is a utility class for easy mapping from XML to NMBS Objects
 */
public class EdxELRLabMapDT {

	private Long subjectEntityUid;
	private String roleCd; 
	private String roleCdDescTxt; 
	private Integer eoleSeq; 
	private String roleSubjectClassCd;
	private Long participationEntityUid;
	private Long participationActUid;
	private String participationActClassCd;
	private String participationCd;
	private Integer participationRoleSeq;
	private String participationSubjectClassCd;
	private String participationSubjectEntityCd;
	private String participationTypeCd;
	private String participationTypeDescTxt;
	private Timestamp addTime;
	
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	private Long entityUid; 
	private String entityCd;
	private String entityCdDescTxt; 
	private String entityStandardIndustryClassCd; 
	private String entityStandardIndustryDescTxt; 
	private String entityDisplayNm;
	private String entityElectronicInd; 
	private Timestamp asOfDate; 
	private Long rootObservationUid; 

	public Timestamp getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}
	public Long getRootObservationUid() {
		return rootObservationUid;
	}
	public void setRootObservationUid(Long rootObservationUid) {
		this.rootObservationUid = rootObservationUid;
	}
	//entity_name.nm_txt
	//entity_name.entity_name_seq 
	//entity_name.nm_use_cd =’L’
	//Entity.entity_uid = <sender uid>
	//Entity_id.entity_id_seq
	//Entity_id.entity_uid;
	private String entityIdRootExtensionTxt;
	private String entityIdAssigningAuthorityCd ;
	private String entityIdAssigningAuthorityDescTxt;
	private String entityIdTypeCd;
	private String entityIdTypeDescTxt;
	//Entity_id.record_status_cd=’ACTIVE’
	//Entity_id.as_of_date should be set
	public Long getSubjectEntityUid() {
		return subjectEntityUid;
	}
	public void setSubjectEntityUid(Long subjectEntityUid) {
		this.subjectEntityUid = subjectEntityUid;
	}
	public String getRoleCd() {
		return roleCd;
	}
	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}
	public String getRoleCdDescTxt() {
		return roleCdDescTxt;
	}
	public void setRoleCdDescTxt(String roleCdDescTxt) {
		this.roleCdDescTxt = roleCdDescTxt;
	}
	public Integer getEoleSeq() {
		return eoleSeq;
	}
	public void setEoleSeq(Integer eoleSeq) {
		this.eoleSeq = eoleSeq;
	}
	public String getRoleSubjectClassCd() {
		return roleSubjectClassCd;
	}
	public void setRoleSubjectClassCd(String roleSubjectClassCd) {
		this.roleSubjectClassCd = roleSubjectClassCd;
	}
	public Long getParticipationEntityUid() {
		return participationEntityUid;
	}
	public void setParticipationEntityUid(Long participationEntityUid) {
		this.participationEntityUid = participationEntityUid;
	}
	public Long getParticipationActUid() {
		return participationActUid;
	}
	public void setParticipationActUid(Long participationActUid) {
		this.participationActUid = participationActUid;
	}
	public String getParticipationActClassCd() {
		return participationActClassCd;
	}
	public void setParticipationActClassCd(String participationActClassCd) {
		this.participationActClassCd = participationActClassCd;
	}
	public String getParticipationCd() {
		return participationCd;
	}
	public void setParticipationCd(String participationCd) {
		this.participationCd = participationCd;
	}
	public Integer getParticipationRoleSeq() {
		return participationRoleSeq;
	}
	public void setParticipationRoleSeq(Integer participationRoleSeq) {
		this.participationRoleSeq = participationRoleSeq;
	}
	public String getParticipationSubjectClassCd() {
		return participationSubjectClassCd;
	}
	public void setParticipationSubjectClassCd(String participationSubjectClassCd) {
		this.participationSubjectClassCd = participationSubjectClassCd;
	}
	public String getParticipationSubjectEntityCd() {
		return participationSubjectEntityCd;
	}
	public void setParticipationSubjectEntityCd(String participationSubjectEntityCd) {
		this.participationSubjectEntityCd = participationSubjectEntityCd;
	}
	public String getParticipationTypeCd() {
		return participationTypeCd;
	}
	public void setParticipationTypeCd(String participationTypeCd) {
		this.participationTypeCd = participationTypeCd;
	}
	public String getParticipationTypeDescTxt() {
		return participationTypeDescTxt;
	}
	public void setParticipationTypeDescTxt(String participationTypeDescTxt) {
		this.participationTypeDescTxt = participationTypeDescTxt;
	}
	public Long getEntityUid() {
		return entityUid;
	}
	public void setEntityUid(Long entityUid) {
		this.entityUid = entityUid;
	}
	public String getEntityCd() {
		return entityCd;
	}
	public void setEntityCd(String entityCd) {
		this.entityCd = entityCd;
	}
	public String getEntityCdDescTxt() {
		return entityCdDescTxt;
	}
	public void setEntityCdDescTxt(String entityCdDescTxt) {
		this.entityCdDescTxt = entityCdDescTxt;
	}
	public String getEntityStandardIndustryClassCd() {
		return entityStandardIndustryClassCd;
	}
	public void setEntityStandardIndustryClassCd(
			String entityStandardIndustryClassCd) {
		this.entityStandardIndustryClassCd = entityStandardIndustryClassCd;
	}
	public String getEntityStandardIndustryDescTxt() {
		return entityStandardIndustryDescTxt;
	}
	public void setEntityStandardIndustryDescTxt(
			String entityStandardIndustryDescTxt) {
		this.entityStandardIndustryDescTxt = entityStandardIndustryDescTxt;
	}
	public String getEntityDisplayNm() {
		return entityDisplayNm;
	}
	public void setEntityDisplayNm(String entityDisplayNm) {
		this.entityDisplayNm = entityDisplayNm;
	}
	public String getEntityElectronicInd() {
		return entityElectronicInd;
	}
	public void setEntityElectronicInd(String entityElectronicInd) {
		this.entityElectronicInd = entityElectronicInd;
	}
	public String getEntityIdRootExtensionTxt() {
		return entityIdRootExtensionTxt;
	}
	public void setEntityIdRootExtensionTxt(String entityIdRootExtensionTxt) {
		this.entityIdRootExtensionTxt = entityIdRootExtensionTxt;
	}
	public String getEntityIdAssigningAuthorityCd() {
		return entityIdAssigningAuthorityCd;
	}
	public void setEntityIdAssigningAuthorityCd(String entityIdAssigningAuthorityCd) {
		this.entityIdAssigningAuthorityCd = entityIdAssigningAuthorityCd;
	}
	public String getEntityIdAssigningAuthorityDescTxt() {
		return entityIdAssigningAuthorityDescTxt;
	}
	public void setEntityIdAssigningAuthorityDescTxt(
			String entityIdAssigningAuthorityDescTxt) {
		this.entityIdAssigningAuthorityDescTxt = entityIdAssigningAuthorityDescTxt;
	}
	public String getEntityIdTypeCd() {
		return entityIdTypeCd;
	}
	public void setEntityIdTypeCd(String entityIdTypeCd) {
		this.entityIdTypeCd = entityIdTypeCd;
	}
	public String getEntityIdTypeDescTxt() {
		return entityIdTypeDescTxt;
	}
	public void setEntityIdTypeDescTxt(String entityIdTypeDescTxt) {
		this.entityIdTypeDescTxt = entityIdTypeDescTxt;
	}
}
