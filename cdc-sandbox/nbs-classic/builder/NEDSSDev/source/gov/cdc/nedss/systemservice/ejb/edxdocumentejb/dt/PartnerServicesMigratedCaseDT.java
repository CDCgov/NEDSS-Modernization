package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;

import java.sql.Timestamp;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

/**
 * PartnerServicesMigratedCaseDT - Used by DAO for migrated data (i.e. STD*MIS imports).
 * @author Gregory Tucker
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: CSRA for CDC</p>
 * Dec 22nd, 2016
 * @version 0.9
 */

public class PartnerServicesMigratedCaseDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	private Long edxEventProcessUid; 
	private Long nbsDocumentUid;
	private Long nbsInvestigationUid;
	private String nbsInvestigationLocalId;
	private String nbsDocumentLocalId;
	private String nbsPatientLocalId;
	private String legacyCaseNo; //STD*MIS
	private String legacyPatientId; //STD*MIS
	
	


	public Long getEdxEventProcessUid() {
		return edxEventProcessUid;
	}

	public void setEdxEventProcessUid(Long edxEventProcessUid) {
		this.edxEventProcessUid = edxEventProcessUid;
	}

	public Long getNbsDocumentUid() {
		return nbsDocumentUid;
	}

	public void setNbsDocumentUid(Long nbsDocumentUid) {
		this.nbsDocumentUid = nbsDocumentUid;
	}

	public Long getNbsInvestigationUid() {
		return nbsInvestigationUid;
	}

	public void setNbsInvestigationUid(Long nbsInvestigationUid) {
		this.nbsInvestigationUid = nbsInvestigationUid;
	}

	public String getNbsInvestigationLocalId() {
		return nbsInvestigationLocalId;
	}

	public void setNbsInvestigationLocalId(String nbsInvestigationLocalId) {
		this.nbsInvestigationLocalId = nbsInvestigationLocalId;
	}
	
	public String getNbsPatientLocalId() {
		return nbsPatientLocalId;
	}

	public void setNbsPatientLocalId(String nbsPatientLocalId) {
		this.nbsPatientLocalId = nbsPatientLocalId;
	}

	public String getNbsDocumentLocalId() {
		return nbsDocumentLocalId;
	}

	public void setNbsDocumentLocalId(String nbsDocumentLocalId) {
		this.nbsDocumentLocalId = nbsDocumentLocalId;
	}

	public String getLegacyCaseNo() {
		return legacyCaseNo;
	}

	public void setLegacyCaseNo(String migratedCaseNo) {
		this.legacyCaseNo = migratedCaseNo;
	}

	public String getLegacyPatientId() {
		return legacyPatientId;
	}

	public void setLegacyPatientId(String migratedPatientId) {
		this.legacyPatientId = migratedPatientId;
	}
	
	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
