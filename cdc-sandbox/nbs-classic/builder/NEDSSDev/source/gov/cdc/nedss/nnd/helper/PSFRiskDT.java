package gov.cdc.nedss.nnd.helper;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * PSFRiskDT - Used to store data from PSFRisk table.
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * August 6th, 2018
 * @version 1
 */

public class PSFRiskDT extends AbstractVO implements RootDTInterface{

	private static final long serialVersionUID = 1L;
	
	private String localClientId;
	private String clientIdSTDMIS;
	private String clientIdPSID;
	private String clientIdLocalId;
	private String clientFirstName;
	private String clientLastName;
	private Timestamp clientDOB;
	private String invLocalId;
	private Timestamp dateCollectedForRiskProfile;
	private String withMale;
	private String withFemale;
	private String withTransgender;
	private String vaginalOrAnalSexWithoutCondomPS;
	private String injectionDrugUse;
	private Timestamp invAddTime;
	private Timestamp invLastChgTime;
	private String invStatusCd;
	

	

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

	public Timestamp getDateCollectedForRiskProfile() {
		return dateCollectedForRiskProfile;
	}
	public void setDateCollectedForRiskProfile(Timestamp dateCollectedForRiskProfile) {
		this.dateCollectedForRiskProfile = dateCollectedForRiskProfile;
	}
	public String getWithMale() {
		if(withMale==null)
			withMale="";
		return withMale;
	}
	public void setWithMale(String withMale) {
		this.withMale = withMale;
	}
	public String getWithFemale() {
		if(withFemale==null)
			withFemale="";
		return withFemale;
	}
	public void setWithFemale(String withFemale) {
		this.withFemale = withFemale;
	}
	public String getWithTransgender() {
		if(withTransgender==null)
			withTransgender="";
		return withTransgender;
	}
	public void setWithTransgender(String withTransgender) {
		this.withTransgender = withTransgender;
	}
	public String getVaginalOrAnalSexWithoutCondomPS() {
		if(vaginalOrAnalSexWithoutCondomPS==null)
			vaginalOrAnalSexWithoutCondomPS="";
		return vaginalOrAnalSexWithoutCondomPS;
	}
	public void setVaginalOrAnalSexWithoutCondomPS(
			String vaginalOrAnalSexWithoutCondomPS) {
		this.vaginalOrAnalSexWithoutCondomPS = vaginalOrAnalSexWithoutCondomPS;
	}
	public String getInjectionDrugUse() {
		if(injectionDrugUse==null)
			injectionDrugUse="";
		return injectionDrugUse;
	}
	public void setInjectionDrugUse(String injectionDrugUse) {
		this.injectionDrugUse = injectionDrugUse;
	}
	public Timestamp getInvAddTime() {
		return invAddTime;
	}
	public void setInvAddTime(Timestamp invAddTime) {
		this.invAddTime = invAddTime;
	}
	public Timestamp getInvLastChgTime() {
		return invLastChgTime;
	}
	public void setInvLastChgTime(Timestamp invLastChgTime) {
		this.invLastChgTime = invLastChgTime;
	}
	public String getInvStatusCd() {
		if(invStatusCd==null)
			invStatusCd="";
		return invStatusCd;
	}
	public void setInvStatusCd(String invStatusCd) {
		this.invStatusCd = invStatusCd;
	}
	public String getInvLocalId() {
		if(invLocalId==null)
			invLocalId="";
		return invLocalId;
	}
	public void setInvLocalId(String invLocalId) {
		this.invLocalId = invLocalId;
	}
	
}