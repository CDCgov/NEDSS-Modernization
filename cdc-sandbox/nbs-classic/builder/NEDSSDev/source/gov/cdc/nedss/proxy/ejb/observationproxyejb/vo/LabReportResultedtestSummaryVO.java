package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import java.sql.*;
import java.util.*;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.systemservice.util.*;

/**
 * <p>Title:LabReportResultedtestSummaryVO</p>
 * <p>Description: LabReportSummaryVO class</p>
 * <p>Copyright: CSC Copyright (c) 2003</p>
 * <p>Company: CSC</p>
 * @author Pradeep Kumar Sharma
 * @version 1.0
 */

public class LabReportResultedtestSummaryVO extends AbstractVO  implements  RootDTInterface{



  private Long observationUid;
  private String status;
  private Integer versionCtrlNbr;
  private String sharedInd;
  private Long programJurisdictionOid;
  private java.sql.Timestamp addTime;
  private Long uid;
  private String superclass;
  private java.sql.Timestamp statusTime;
  private String statusCd;
  private java.sql.Timestamp recordStatusTime;
  private String recordStatusCd;
  private String lastChgReasonCd;
  private Long addUserId;
  private java.sql.Timestamp lastChgTime;
  private String progAreaCd;
  private String jurisdictionCd;
  private Long lastChgUserId;
  private String localId;
  private String jurisdiction;
  private String programArea;
  private String resultedTest;
  private String resultedTestCd;
  private String resultedTestCdSystemCd;
  private String ctrlCdDisplayForm;
  private String electronicInd;
  private java.sql.Timestamp dateReceived;
  private String personLocalId;
  private Long personUid;
  private String lastNm;
  private String firstNm;
  private Long personParentUid;
  private String currSexCd;
  private java.sql.Timestamp birthTime;
  
  
  public LabReportResultedtestSummaryVO() {
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
  public Long getObservationUid() {
    return observationUid;
  }
  public void setObservationUid(Long observationUid) {
    this.observationUid = observationUid;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public Integer getVersionCtrlNbr() {
    return versionCtrlNbr;
  }
  public void setVersionCtrlNbr(Integer versionCtrlNbr) {
    this.versionCtrlNbr = versionCtrlNbr;
  }
  public String getSharedInd() {
    return sharedInd;
  }
  public void setSharedInd(String sharedInd) {
    this.sharedInd = sharedInd;
  }
  public Long getProgramJurisdictionOid() {
    return programJurisdictionOid;
  }
  public void setProgramJurisdictionOid(Long programJurisdictionOid) {
    this.programJurisdictionOid = programJurisdictionOid;
  }
  public java.sql.Timestamp getAddTime() {
    return addTime;
  }
  public void setAddTime(java.sql.Timestamp addTime) {
    this.addTime = addTime;
  }
  public Long getUid() {
    return uid;
  }
  public void setUid(Long uid) {
    this.uid = uid;
  }
  public String getSuperclass() {
    return superclass;
  }
  public void setSuperclass(String superclass) {
    this.superclass = superclass;
  }
  public java.sql.Timestamp getStatusTime() {
    return statusTime;
  }
  public void setStatusTime(java.sql.Timestamp statusTime) {
    this.statusTime = statusTime;
  }
  public String getStatusCd() {
    return statusCd;
  }
  public void setStatusCd(String statusCd) {
    this.statusCd = statusCd;
  }
  public java.sql.Timestamp getRecordStatusTime() {
    return recordStatusTime;
  }
  public void setRecordStatusTime(java.sql.Timestamp recordStatusTime) {
    this.recordStatusTime = recordStatusTime;
  }
  public String getRecordStatusCd() {
    return recordStatusCd;
  }
  public void setRecordStatusCd(String recordStatusCd) {
    this.recordStatusCd = recordStatusCd;
  }
  public String getLastChgReasonCd() {
    return lastChgReasonCd;
  }
  public void setLastChgReasonCd(String lastChgReasonCd) {
    this.lastChgReasonCd = lastChgReasonCd;
  }
  public Long getAddUserId() {
    return addUserId;
  }
  public void setAddUserId(Long addUserId) {
    this.addUserId = addUserId;
  }
  public java.sql.Timestamp getLastChgTime() {
    return lastChgTime;
  }
  public void setLastChgTime(java.sql.Timestamp lastChgTime) {
    this.lastChgTime = lastChgTime;
  }
  public String getProgAreaCd() {
    return progAreaCd;
  }
  public void setProgAreaCd(String progAreaCd) {
    this.progAreaCd = progAreaCd;
  }
  public String getJurisdictionCd() {
    return jurisdictionCd;
  }
  public void setJurisdictionCd(String jurisdictionCd) {
    this.jurisdictionCd = jurisdictionCd;
  }
  public Long getLastChgUserId() {
    return lastChgUserId;
  }
  public void setLastChgUserId(Long lastChgUserId) {
    this.lastChgUserId = lastChgUserId;
  }
  public String getLocalId() {
    return localId;
  }
  public void setLocalId(String localId) {
    this.localId = localId;
  }
  public String getJurisdiction() {
    return jurisdiction;
  }
  public void setJurisdiction(String jurisdiction) {
    this.jurisdiction = jurisdiction;
  }
  public String getProgramArea() {
    return programArea;
  }
  public void setProgramArea(String programArea) {
    this.programArea = programArea;
  }
  public String getResultedTest() {
    return resultedTest;
  }
  public void setResultedTest(String resultedTest) {
    this.resultedTest = resultedTest;
  }
  public String getResultedTestCd() {
    return resultedTestCd;
  }
  public void setResultedTestCd(String resultedTestCd) {
    this.resultedTestCd = resultedTestCd;
  }
  public String getResultedTestCdSystemCd() {
    return resultedTestCdSystemCd;
  }
  public void setResultedTestCdSystemCd(String resultedTestCdSystemCd) {
    this.resultedTestCdSystemCd = resultedTestCdSystemCd;
  }
  public String getCtrlCdDisplayForm() {
    return ctrlCdDisplayForm;
  }
  public void setCtrlCdDisplayForm(String ctrlCdDisplayForm) {
    this.ctrlCdDisplayForm = ctrlCdDisplayForm;
  }
  public java.sql.Timestamp getDateReceived() {
    return dateReceived;
  }
  public void setDateReceived(java.sql.Timestamp dateReceived) {
    this.dateReceived = dateReceived;
  }

public String getElectronicInd() {
	return electronicInd;
}

public void setElectronicInd(String electronicInd) {
	this.electronicInd = electronicInd;
}

public String getPersonLocalId() {
	return personLocalId;
}

public void setPersonLocalId(String personLocalId) {
	this.personLocalId = personLocalId;
}

public Long getPersonUid() {
	return personUid;
}

public void setPersonUid(Long personUid) {
	this.personUid = personUid;
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

public String getCurrSexCd() {
	return currSexCd;
}

public void setCurrSexCd(String currSexCd) {
	this.currSexCd = currSexCd;
}

public java.sql.Timestamp getBirthTime() {
	return birthTime;
}

public void setBirthTime(java.sql.Timestamp birthTime) {
	this.birthTime = birthTime;
}

}