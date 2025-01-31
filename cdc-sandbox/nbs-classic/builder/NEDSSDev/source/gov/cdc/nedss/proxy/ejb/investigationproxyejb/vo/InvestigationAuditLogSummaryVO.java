package gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo;

import java.sql.Timestamp;
import java.util.Collection;

import gov.cdc.nedss.systemservice.util.*;
import gov.cdc.nedss.util.AbstractVO;

public class InvestigationAuditLogSummaryVO extends AbstractVO implements RootDTInterface {

	private Long  lastChgUserId ;
	private String jurisdictionCd ;
	private String caseStatusCd ;
	private Timestamp lastChgTime ;
	private Integer versionCtrlNbr;
	private String JuridictionText ;
	private String caseStatusText ;
	private String userName ;
	private String changeDate ;
	private Long  addUserId;
	private Timestamp addTime ;
	
	
	
	
	public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass)
	   {
	    return true;
	   }  
	/**
	    * A getter for last change user id
	    * @return Long
	    * @roseuid 3C73C11500C4
	    */
	    public Long getLastChgUserId(){return lastChgUserId ;};

	    /**
	    * A setter for last change user id
	    * @param aLastChgUserId
	    * @roseuid 3C73D82701FD
	    */
	    public void setLastChgUserId(Long aLastChgUserId){
	    	lastChgUserId = aLastChgUserId ;
	    }

	    /**
	    * A getter for jurisdiction code
	    * @return String
	    * @roseuid 3C73D8D1030F
	    */
	    public String getJurisdictionCd(){return jurisdictionCd;};

	    /**
	    * A setter for jurisdiction code
	    * @param aJurisdictionCd
	    * @roseuid 3C73D8E5000B
	    */
	    public void setJurisdictionCd(String aJurisdictionCd){
	    	jurisdictionCd = aJurisdictionCd;
	    	
	    }

	    /**
	    * A getter for program area code
	    * @return String
	    * @roseuid 3C73D90A0145
	    */
	    public String getProgAreaCd(){return null;};

	    /**
	    * A setter for program area code
	    * @param aProgAreaCd
	    * @roseuid 3C73D91703E2
	    */
	    public void setProgAreaCd(String aProgAreaCd){};

	    /**
	    * A getter for last change time
	    * @return java.sql.Timestamp
	    * @roseuid 3C73D9C502AC
	    */
	    public Timestamp getLastChgTime(){return lastChgTime;}

	    /**
	    * A setter for last change time
	    * @param aLastChgTime
	    * @roseuid 3C73D9D800AB
	    */
	    public void setLastChgTime(java.sql.Timestamp aLastChgTime){
	    	lastChgTime = aLastChgTime ;
	    }

	    /**
	    * A getter for local id
	    * @return String
	    * @roseuid 3C73DA200253
	    */
	    public String getLocalId(){return null;};

	    /**
	    * A setter for local id
	    * @param aLocalId
	    * @roseuid 3C73DA2C00CA
	    */
	    public void setLocalId(String aLocalId){};

	    /**
	    * A getter for add user id
	    * @return Long
	    * @roseuid 3C73DA4701B9
	    */
	    public Long getAddUserId(){return addUserId;};

	    /**
	    * A stter for add user id
	    * @param aAddUserId
	    * @roseuid 3C73DA550123
	    */
	    public void setAddUserId(Long aAddUserId){
	    	this.addUserId = aAddUserId ;
	    	
	    };

	    /**
	    * A getter for last change reason code
	    * @return String
	    * @roseuid 3C73DABD00F0
	    */
	    public String getLastChgReasonCd(){return null;};

	    /**
	    * A setter for last change reason code
	    * @param aLastChgReasonCd
	    * @roseuid 3C73DAC60360
	    */
	    public void setLastChgReasonCd(String aLastChgReasonCd){};

	    /**
	    * A getter for record status code
	    * @return String
	    * @roseuid 3C73DAFD023D
	    */
	    public String getRecordStatusCd(){return null;};

	    /**
	    * A setter for record status code
	    * @param aRecordStatusCd
	    * @roseuid 3C73DB0C02AC
	    */
	    public void setRecordStatusCd(String aRecordStatusCd){};

	    /**
	    * A getter for record status time
	    * @return java.sql.Timestamp
	    * @roseuid 3C73DB260015
	    */
	    public Timestamp getRecordStatusTime(){return null;};

	    /**
	    * A setter for record status time
	    * @param aRecordStatusTime
	    * @roseuid 3C73DB35002A
	    */
	    public void setRecordStatusTime(java.sql.Timestamp aRecordStatusTime){};

	    /**
	    * A getter for status code
	    * @return String
	    * @roseuid 3C73DB60004A
	    */
	    public String getStatusCd(){return null;};

	    /**
	    * A setter for status code
	    * @param aStatusCd
	    * @roseuid 3C73DB6A030C
	    */
	    public void setStatusCd(String aStatusCd){};

	    /**
	    * A getter for status time
	    * @return java.sql.Timestamp
	    * @roseuid 3C73DB6F0381
	    */
	    public Timestamp getStatusTime(){return null;};

	    /**
	    * A setter for status time
	    * @param aStatusTime
	    * @roseuid 3C73DB74018A
	    */
	    public void setStatusTime(java.sql.Timestamp aStatusTime){};

	    /**
	    * Implement base to return class type - currently CLASSTYPE_ACT or
	    * CLASSTYPE_ENTITY
	    *
	    * @return String
	    * @roseuid 3C73FD5C0343
	    */
	    public String getSuperclass(){return null;};

	    /**
	    * A getter for uid
	    * @return Long
	    * @roseuid 3C7407A80249
	    */
	    public Long getUid(){return null;};

	    /**
	    * A setter for add time
	    * @param aAddTime
	    * @roseuid 3C7412520078
	    */
	    public void setAddTime(java.sql.Timestamp aAddTime){
	    	this.addTime = aAddTime;
	    }

	    /**
	    * A getter for add time
	    * @return java.sql.Timestamp
	    * @roseuid 3C74125B0003
	    */
	    public Timestamp getAddTime(){
	    	return addTime;
	    	
	    };

	    /**
	    * A checker for the new flag
	    * @return boolean
	    * @roseuid 3C7440F0021D
	    */
	    public boolean isItNew(){return true;};

	    /**
	    * A setter for the new flag
	    * @param itNew
	    * @roseuid 3C7441030329
	    */
	    public void setItNew(boolean itNew){};

	    /**
	    * A checker for the dirty flag
	    * @return boolean
	    * @roseuid 3C74410A00DA
	    */
	    public boolean isItDirty(){return true ;};

	    /**
	    * A setter for the dirty flag
	    * @param itDirty
	    * @roseuid 3C74410F02C2
	    */
	    public void setItDirty(boolean itDirty){};

	    /**
	    * A checker for the delete flag
	    * @return boolean
	    * @roseuid 3C74411402B5
	    */
	    public boolean isItDelete(){return true;};

	    /**
	    * A setter for the delete flag
	    * @param itDelete
	    * @roseuid 3C74412E012C
	    */
	    public void setItDelete(boolean itDelete){};

	    /**
	    * A getter for program jurisdiction oid
	    * @return Long
	    * @roseuid 3CF7906002AE
	    */
	    public Long getProgramJurisdictionOid(){return null;};

	    /**
	    * A setter for the program jurisdiction oid
	    * @param aProgramJurisdictionOid
	    * @roseuid 3CF7974902A7
	    */
	    public void setProgramJurisdictionOid(Long aProgramJurisdictionOid){};

	    /**
	    * A getter for shared indicator
	    * @return String
	    * @roseuid 3CFBB5DA00CD
	    */
	    public String getSharedInd(){return null;};

	    /**
	    * A setter for shared indicator
	    * @param aSharedInd
	    * @roseuid 3CFBB5EB01F4
	    */
	    public void setSharedInd(String aSharedInd){};

	    /**
	     * A getter for version control number
	     * @return  Integer
	     */
	    public Integer getVersionCtrlNbr(){return versionCtrlNbr;}
		
	    public String getCaseStatusCd() {
			return caseStatusCd;
		}
		public void setCaseStatusCd(String caseStatusCd) {
			this.caseStatusCd = caseStatusCd;
		}
		public void setVersionCtrlNbr(Integer versionCtrlNbr) {
			this.versionCtrlNbr = versionCtrlNbr;
		}
		
		public String getJuridictionText() {
			return JuridictionText;
		}
		public void setJuridictionText(String juridictionText) {
			JuridictionText = juridictionText;
		}
		public String getCaseStatusText() {
			return caseStatusText;
		}
		public void setCaseStatusText(String caseStatusText) {
			this.caseStatusText = caseStatusText;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getChangeDate() {
			return changeDate;
		}
		public void setChangeDate(String changeDate) {
			this.changeDate = changeDate;
		}
		
		
		
		
}
