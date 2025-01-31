/**
 * Title: ObservationSummaryDisplayVO class.
 * Description: A ValueObject comprising of selected labsummaryVO and MorbSummary
 * fields to display in the Observation Needing Review for available Observations
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.proxy.ejb.observationproxyejb.vo;

import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.PropertyUtil;

import java.io.*;
import java.util.*;



/**
 *
 */
public class ObservationSummaryDisplayVO extends AbstractVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String type;
	private String typeLnk;
	private String typePrint;
	
  private String status;
  private String firstName;
  private String lastName;
  private String observationId;
  private String observationIdPrint;
  private String localId;
  private Collection<Object>  testCollection;
  private java.sql.Timestamp dateReceived;
  private String dateReceivedS;
  private String dateReceivedPrint;
  private Long observationUID;
  private String programArea;
  private String programAreaSTDHIV;//It is used only to know if the PA is STD/HIV or not
  private String description;
  private String descriptionPrint;
  private String providerReportingFacility;
  private String providerReportingFacilityPrint;
  private String sendingFacilityNm;
  private String electronicInd;
  private String personLocalId;
  private HashMap<String, String> mapInvCond = new HashMap<String, String>();
  private HashMap<String, String> mapInvClass = new HashMap<String, String>();
  private HashMap<String, Long> mapInvUid = new HashMap<String, Long>();
  private String testsStringNoLnk;
  private String testsStringPrint;
	private String providerFirstName;
	private String providerLastName;
	private String providerPrefix;
	private String providerSuffix;
	private String providerDegree;
	private String providerUid;
	private String reportingFacility;
	private Collection<Object> invSummaryVOs;

private String jurisdiction;
  private Long MPRUid;
  private String testsString;
  private String fullName;
  private ArrayList<String> condition;
  private ArrayList<String> descriptions;
  private String fullNameNoLnk;
  private String currSexCd;
  private String birthTime;
  private String sharedInd;
  
	public String getCurrSexCd() {
	return currSexCd;
}

public void setCurrSexCd(String currSexCd) {
	this.currSexCd = currSexCd;
}

public String getBirthTime() {
	return birthTime;
}

public void setBirthTime(String birthTime) {
	this.birthTime = birthTime;
}

	private boolean isMarkAsReviewed;
	private String checkBoxId;

	public boolean getIsMarkAsReviewed() {
		return isMarkAsReviewed;
	}

	public void setIsMarkAsReviewed(boolean isMarkAsReviewed) {
		this.isMarkAsReviewed = isMarkAsReviewed;
	}


	public String getCheckBoxId() {
		if (isMarkAsReviewed) {
			checkBoxId = "checked=\"true\"";
		}
		return checkBoxId;
	}
	public void setCheckBoxId(String checkBoxId) {
		this.checkBoxId = checkBoxId;
	}

public void setCondition(ArrayList<String> condition) {
	this.condition = condition;
}
public String getFullNameNoLnk() {
	return fullNameNoLnk;
}
public void setFullNameNoLnk(String fullNameNoLnk) {
	this.fullNameNoLnk = fullNameNoLnk;
}
public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getObservationId() {
    return observationId;
  }
  public void setObservationId(String observationId) {
    this.observationId = observationId;
  }
  public void setTestCollection(Collection<Object> testCollection) {
     this.testCollection  = testCollection;
   }
   public Collection<Object>  getTestCollection() {
    return testCollection;
  }
  public java.sql.Timestamp getDateReceived() {
    return dateReceived;
  }
  public String getDateReceivedS() {
	    return dateReceivedS;
	  }
  public void setDateReceived(java.sql.Timestamp dateReceived) {
    this.dateReceived = dateReceived;
  }
  public void setDateReceived(String dateReceived) {
	    this.dateReceivedS = dateReceived;
	  }
  public Long getObservationUID() {
    return observationUID;
  }
  public void setObservationUID(Long observationUID) {
    this.observationUID = observationUID;
  }
  public String getProgramArea() {
    return programArea;
  }
  public void setProgramArea(String programArea) {
    this.programArea = programArea;
  }
  public String getJurisdiction() {
    return jurisdiction;
  }
  public void setJurisdiction(String jurisdiction) {
    this.jurisdiction = jurisdiction;
  }
  public Long getMPRUid() {
    return MPRUid;
  }
  public void setMPRUid(Long MPRUid) {
    this.MPRUid = MPRUid;
  }
public String getTestsString() {
	return testsString;
}
public void setTestsString(String testsString) {
	this.testsString = testsString;
}
public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}

public ArrayList<String> getCondition() {
	return condition;//.replaceAll("<BR>", ", ");
}


public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}
public String getProviderFirstName() {
return providerFirstName;
}

public void setProviderFirstName(String providerFirstName) {
this.providerFirstName = providerFirstName;
}

public String getProviderLastName() {
return providerLastName;
}

public void setProviderLastName(String providerLastName) {
this.providerLastName = providerLastName;
}

public String getProviderPrefix() {
return providerPrefix;
}

public void setProviderPrefix(String providerPreffix) {
this.providerPrefix = providerPreffix;
}

public String getProviderSuffix() {
return providerSuffix;
}

public void setProviderSuffix(String providerSuffix) {
this.providerSuffix = providerSuffix;
}

public String getProviderDegree() {
return providerDegree;
}

public void setProviderDegree(String providerDegree) {
this.providerDegree = providerDegree;
}

public String getProviderUid() {
return providerUid;
}

public void setProviderUid(String providerUid) {
this.providerUid = providerUid;
}

public String getProviderReportingFacility() {
	return providerReportingFacility;
}

public void setProviderReportingFacility(String providerReportingFacility) {
	this.providerReportingFacility = providerReportingFacility;
}

public String getReportingFacility() {
	return reportingFacility;
}

public void setReportingFacility(String reportingFacility) {
	this.reportingFacility = reportingFacility;
}

public String getSendingFacilityNm() {
	return sendingFacilityNm;
}

public void setSendingFacilityNm(String sendingFacilityNm) {
	this.sendingFacilityNm = sendingFacilityNm;
}

public String getTypeLnk() {
	return typeLnk;
}

public void setTypeLnk(String typeNoLnk) {
	this.typeLnk = typeNoLnk;
}

public String getElectronicInd() {
	return electronicInd;
}

public void setElectronicInd(String electronicInd) {
	this.electronicInd = electronicInd;
}

public Collection<Object> getInvSummaryVOs() {
	return invSummaryVOs;
}

public void setInvSummaryVOs(Collection<Object> invSummaryVOs) {
	this.invSummaryVOs = invSummaryVOs;
}



public HashMap<String, String> getMapInvCond() {
	return mapInvCond;
}


public void setMapInvCond(HashMap<String, String> mapInvCond) {
	this.mapInvCond = mapInvCond;
}

public HashMap<String, String> getMapInvClass() {
	return mapInvClass;
}

public void setMapInvClass(HashMap<String, String> mapInvClass) {
	this.mapInvClass = mapInvClass;
}

public String getTestsStringNoLnk() {
	return testsStringNoLnk;
}

public void setTestsStringNoLnk(String getTestsStringNoLnk) {
	this.testsStringNoLnk = getTestsStringNoLnk;
}

public HashMap<String, Long> getMapInvUid() {
	return mapInvUid;
}

public void setMapInvUid(HashMap<String, Long> mapInvUid) {
	this.mapInvUid = mapInvUid;
}

public String getPersonLocalId() {
	return personLocalId;
}
public void setPersonLocalId(String personLocalId) {
	this.personLocalId = personLocalId;
}


public String getTypePrint() {
	return typePrint;
}

public void setTypePrint(String typePrint) {
	this.typePrint = typePrint;
}

public String getProviderReportingFacilityPrint() {
	return providerReportingFacilityPrint;
}

public void setProviderReportingFacilityPrint(
		String providerReportingFacilityPrint) {
	this.providerReportingFacilityPrint = providerReportingFacilityPrint;
}

public String getDescriptionPrint() {
	return descriptionPrint;
}

public void setDescriptionPrint(String descriptionPrint) {
	this.descriptionPrint = descriptionPrint;
}

public String getTestsStringPrint() {
	return testsStringPrint;
}

public void setTestsStringPrint(String testsStringPrint) {
	this.testsStringPrint = testsStringPrint;
}

public String getObservationIdPrint() {
	return observationIdPrint;
}

public void setObservationIdPrint(String observationIdPrint) {
	this.observationIdPrint = observationIdPrint;
}

public String getDateReceivedPrint() {
	return dateReceivedPrint;
}

public void setDateReceivedPrint(String dateReceivedPrint) {
	this.dateReceivedPrint = dateReceivedPrint;
}

public ArrayList<String> getDescriptions() {
	return descriptions;
}

public void setDescriptions(ArrayList<String> descriptions) {
	this.descriptions = descriptions;
}

public String getLocalId() {
	return localId;
}

public void setLocalId(String localId) {
	this.localId = localId;
}

public String getSharedInd() {
	return sharedInd;
}

public void setSharedInd(String sharedInd) {
	this.sharedInd = sharedInd;
}

public String getProgramAreaSTDHIV() {
	return programAreaSTDHIV;
}

public void setProgramAreaSTDHIV(String programArea) {
	programAreaSTDHIV = PropertyUtil.isStdOrHivProgramArea(programArea)+"";
}

@Override
public void setItDirty(boolean itDirty) {
	// TODO Auto-generated method stub
	
}

@Override
public boolean isItDirty() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void setItNew(boolean itNew) {
	// TODO Auto-generated method stub
	
}

@Override
public boolean isItNew() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void setItDelete(boolean itDelete) {
	// TODO Auto-generated method stub
	
}

@Override
public boolean isItDelete() {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
	// TODO Auto-generated method stub
	return false;
}

}