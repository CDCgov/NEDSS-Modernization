package gov.cdc.nedss.nnd.helper;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * PSFSessionDT - Used to store data from PSF_SESSION table.
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * August 6th, 2018
 * @version 1
 */

public class PSFSessionDT extends AbstractVO implements RootDTInterface{

	private static final long serialVersionUID = 1L;
	
	private String localClientId;
	private String clientIdSTDMIS;
	private String clientIdPSID;
	private String clientIdLocalId;
	private String clientFirstName;
	private String clientLastName;
	private Timestamp clientDOB;
	private String caseNumberPS;
	private String caseNumberSTDMIS;
	private String caseNumberLegacyId;
	private String caseNumberLocalId;
	private Timestamp sessionDate;
	private String siteId;
	private String siteTypeValueCode;
	private String irStatusCd;
	private String careStatusAtInterview;
	private Timestamp irAddTime;
	private Timestamp irLastChgTime;
	private String irLocalId;
	private String invLocalId;
	
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

	public String getLocalClientId() {
		if(localClientId==null)
			localClientId="";
		return localClientId;
	}
	public void setLocalClientId(String localClientId) {
		this.localClientId = localClientId;
	}
	public String getClientIdSTDMIS() {
		if(clientIdSTDMIS==null)
			clientIdSTDMIS="";
		return clientIdSTDMIS;
	}
	public void setClientIdSTDMIS(String clientIdSTDMIS) {
		this.clientIdSTDMIS = clientIdSTDMIS;
	}
	public String getClientIdPSID() {
		if(clientIdPSID==null)
			clientIdPSID="";
		return clientIdPSID;
	}
	public void setClientIdPSID(String clientIdPSID) {
		this.clientIdPSID = clientIdPSID;
	}
	public String getClientIdLocalId() {
		if(clientIdLocalId==null)
			clientIdLocalId="";
		return clientIdLocalId;
	}
	public void setClientIdLocalId(String clientIdLocalId) {
		this.clientIdLocalId = clientIdLocalId;
	}
	public String getClientFirstName() {
		if(clientFirstName==null)
			clientFirstName="";
		return clientFirstName;
	}
	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}
	public String getClientLastName() {
		if(clientLastName==null)
			clientLastName="";
		return clientLastName;
	}
	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}
	public Timestamp getClientDOB() {
		return clientDOB;
	}
	public void setClientDOB(Timestamp clientDOB) {
		this.clientDOB = clientDOB;
	}
	public String getCaseNumberPS() {
		if(caseNumberPS==null)
			caseNumberPS="";
		return caseNumberPS;
	}
	public void setCaseNumberPS(String caseNumberPS) {
		this.caseNumberPS = caseNumberPS;
	}
	public String getCaseNumberSTDMIS() {
		if(caseNumberSTDMIS==null)
			caseNumberSTDMIS="";
		return caseNumberSTDMIS;
	}
	public void setCaseNumberSTDMIS(String caseNumberSTDMIS) {
		this.caseNumberSTDMIS = caseNumberSTDMIS;
	}
	public String getCaseNumberLegacyId() {
		if(caseNumberLegacyId==null)
			caseNumberLegacyId="";
		return caseNumberLegacyId;
	}
	public void setCaseNumberLegacyId(String caseNumberLegacyId) {
		this.caseNumberLegacyId = caseNumberLegacyId;
	}
	public String getCaseNumberLocalId() {
		if(caseNumberLocalId==null)
			caseNumberLocalId="";
		return caseNumberLocalId;
	}
	public void setCaseNumberLocalId(String caseNumberLocalId) {
		this.caseNumberLocalId = caseNumberLocalId;
	}
	public Timestamp getSessionDate() {
		return sessionDate;
	}
	public void setSessionDate(Timestamp sessionDate) {
		this.sessionDate = sessionDate;
	}
	public String getSiteId() {
		if(siteId==null)
			siteId="";
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteTypeValueCode() {
		if(siteTypeValueCode==null)
			siteTypeValueCode="";
		return siteTypeValueCode;
	}
	public void setSiteTypeValueCode(String siteTypeValueCode) {
		this.siteTypeValueCode = siteTypeValueCode;
	}
	public String getIrStatusCd() {
		if(irStatusCd==null)
			irStatusCd="";
		return irStatusCd;
	}
	public void setIrStatusCd(String irStatusCd) {
		this.irStatusCd = irStatusCd;
	}
	public Timestamp getIrAddTime() {
		return irAddTime;
	}
	public void setIrAddTime(Timestamp irAddTime) {
		this.irAddTime = irAddTime;
	}
	public Timestamp getIrLastChgTime() {
		return irLastChgTime;
	}
	public void setIrLastChgTime(Timestamp irLastChgTime) {
		this.irLastChgTime = irLastChgTime;
	}
	public String getIrLocalId() {
		if(irLocalId==null)
			irLocalId="";
		return irLocalId;
	}
	public void setIrLocalId(String irLocalId) {
		this.irLocalId = irLocalId;
	}
	public String getCareStatusAtInterview() {
		if(careStatusAtInterview==null)
			careStatusAtInterview="";
		return careStatusAtInterview;
	}
	public void setCareStatusAtInterview(String careStatusAtInterview) {
		this.careStatusAtInterview = careStatusAtInterview;
	}
	public String getInvLocalId() {
		return invLocalId;
	}
	public void setInvLocalId(String invLocalId) {
		this.invLocalId = invLocalId;
	}

	
}
