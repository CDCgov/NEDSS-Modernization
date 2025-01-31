package gov.cdc.nedss.nnd.helper;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * PSFIndexDT - Used to store data from PSFClientDT table.
 * @author Fatima Lopez Calzado
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: GDIT</p>
 * August 6th, 2018
 * @version 1
 */

public class PSFClientDT extends AbstractVO implements RootDTInterface{

	private static final long serialVersionUID = 1L;
	
	private String localClientId;
	private String clientIdSTDMIS;
	private String clientIdPSID;
	private String clientIdLocalId;
	private String clientFirstName;
	private String clientLastName;
	private Timestamp clientDOB;
	private String birthYear;
	//private String caseNumberPS;
	//private String caseNumberSTDMIS;
	//private String caseNumberLegacyID;
	//private String caseNumberLocalID;
	private String ethnicity;
	private String raceValueCode1;
	private String raceValueCode2;
	private String raceValueCode3;
	private String raceValueCode4;
	private String raceValueCode5;
	private String birthGenderValueCode;
	private String currentGenderValueCode;
	//private Timestamp collectedDateForClient;
	private String eHarsStateNumber;
	//private Timestamp dateCollectedForRiskProfile;
	//private String previousHivTestValueCode;
	//private String previousHIVTestResult;
	//private String withMale;
	//private String withFemale;
	//private String withTransgender;
	//private String vaginalOrAnalSexWithoutCondomPS;
	//private String injectionDrugUse;
	private Timestamp lastModifiedDate;
	private Timestamp patientLastChgTime;
	private Timestamp patientAddTime;
	private String patientStatusCd; 
//	private Timestamp invLastChgTime;
//	private String invStatusCd;
	private String clientUid;
	

	

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
	
	public Timestamp getPatientAddTime() {
		return patientAddTime;
	}
	public void setPatientAddTime(Timestamp patientAddTime) {
		this.patientAddTime = patientAddTime;
	}
	public Timestamp getPatientLastChgTime() {
		return patientLastChgTime;
	}
	public void setPatientLastChgTime(Timestamp patientLastChgTime) {
		this.patientLastChgTime = patientLastChgTime;
	}
	public String getPatientStatusCd() {
		if(patientStatusCd==null)
			patientStatusCd="";
		return patientStatusCd;
	}
	public void setPatientStatusCd(String patientStatusCd) {
		this.patientStatusCd = patientStatusCd;
	}
	
	public String getBirthYear() {
		if(birthYear==null)
			birthYear="";
		return birthYear;
	}
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	public String getEthnicity() {
		if(ethnicity==null)
			ethnicity="";
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getRaceValueCode1() {
		return raceValueCode1;
	}
	public void setRaceValueCode1(String raceValueCode1) {
		this.raceValueCode1 = raceValueCode1;
	}
	public String getRaceValueCode2() {
		return raceValueCode2;
	}
	public void setRaceValueCode2(String raceValueCode2) {
		this.raceValueCode2 = raceValueCode2;
	}
	public String getRaceValueCode3() {
		return raceValueCode3;
	}
	public void setRaceValueCode3(String raceValueCode3) {
		this.raceValueCode3 = raceValueCode3;
	}
	public String getRaceValueCode4() {
		return raceValueCode4;
	}
	public void setRaceValueCode4(String raceValueCode4) {
		this.raceValueCode4 = raceValueCode4;
	}
	public String getRaceValueCode5() {
		return raceValueCode5;
	}
	public void setRaceValueCode5(String raceValueCode5) {
		this.raceValueCode5 = raceValueCode5;
	}
	public String getBirthGenderValueCode() {
		if(birthGenderValueCode==null)
			birthGenderValueCode="";
		return birthGenderValueCode;
	}
	public void setBirthGenderValueCode(String birthGenderValueCode) {
		this.birthGenderValueCode = birthGenderValueCode;
	}
	public String getCurrentGenderValueCode() {
		if(currentGenderValueCode==null)
			currentGenderValueCode="";
		return currentGenderValueCode;
	}
	public void setCurrentGenderValueCode(String currentGenderValueCode) {
		this.currentGenderValueCode = currentGenderValueCode;
	}

	public String getClientUid() {
		if(clientUid==null)
			clientUid="";
		return clientUid;
	}
	public void setClientUid(String clientUid) {
		this.clientUid = clientUid;
	}
	public String getEHarsStateNumber() {
		if(eHarsStateNumber==null)
			eHarsStateNumber="";
		return eHarsStateNumber;
	}
	public void setEHarsStateNumber(String eHarsStateNumber) {
		this.eHarsStateNumber = eHarsStateNumber;
	}
	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}}