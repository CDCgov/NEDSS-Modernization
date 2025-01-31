package gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo;

import java.sql.*;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.*;

public class InvestigationPersonInfoVO extends AbstractVO implements RootDTInterface {

  private static final long serialVersionUID = 1L;
  private Long publicHealthCaseUid;
  private String lastNm;
  private String firstNm;
  private String personLocalId;
  private Long personParentUid;
  private String currSexCd;
  private Timestamp birthTime;
  
  public InvestigationPersonInfoVO() {
  }
  

  public boolean isEqual(java.lang.Object objectname1,
                         java.lang.Object objectname2, Class<?> voClass) {
    return true;
  }

  public void setItDirty(boolean itDirty) {
  }

  public boolean isItDirty() {
    return true;
  }

  public void setItNew(boolean itNew) {
  }

  public boolean isItNew() {
    return true;
  }

  public void setItDelete(boolean itDelete) {
  }

  public boolean isItDelete() {
    return true;
  }
   
  public String getLastNm() {
    return lastNm;
  }
  public void setLastNm(String lastNm) {
    this.lastNm = lastNm;
  }
  public String getFirstNm() {
    return firstNm;
  }
  public void setFirstNm(String firstNm) {
    this.firstNm = firstNm;
  }
  public Long getPersonParentUid() {
    return personParentUid;
  }
  public void setPersonParentUid(Long personParentUid) {
    this.personParentUid = personParentUid;
  }

/**
 * @return the currSexCd
 */
public String getCurrSexCd() {
	return currSexCd;
}

/**
 * @param currSexCd the currSexCd to set
 */
public void setCurrSexCd(String currSexCd) {
	this.currSexCd = currSexCd;
}

/**
 * @return the birthTime
 */
public Timestamp getBirthTime() {
	return birthTime;
}

/**
 * @param birthTime the birthTime to set
 */
public void setBirthTime(Timestamp birthTime) {
	this.birthTime = birthTime;
}

public String getPersonLocalId() {
	return personLocalId;
}

public void setPersonLocalId(String personLocalId) {
	this.personLocalId = personLocalId;
}

public Long getPublicHealthCaseUid() {
	return publicHealthCaseUid;
}

public void setPublicHealthCaseUid(Long publicHealthCaseUid) {
	this.publicHealthCaseUid = publicHealthCaseUid;
}

@Override
public String toString() {
	return "InvestigationPersonInfoVO [publicHealthCaseUid=" + publicHealthCaseUid + ", lastNm=" + lastNm + ", firstNm="
			+ firstNm + ", personLocalId=" + personLocalId + ", personParentUid=" + personParentUid + ", currSexCd="
			+ currSexCd + ", birthTime=" + birthTime + "]";
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

}