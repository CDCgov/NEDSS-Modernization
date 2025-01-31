

package gov.cdc.nedss.nnd.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import java.util.Comparator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * Name:		CaseNotificationDataDT.java
 * Description:	VO to retrieve Notification Elements from NBS Question metadata tables
 * and NBS Answer to construct Notification Messages (NND Intermediary Message, Notification Transitional Message, etc)
 * Copyright:	Copyright (c) 2008
 * Company: 	Computer Sciences Corporation
 * @author	Beau Bannerman
 */
public class CaseNotificationDataDT extends AbstractVO implements RootDTInterface 
{
	private static final long serialVersionUID = 1L;
	private Long uid;
	private Long observationUid;//for representing the lab uid, result uid and susceptibility uid
	private String questionIdentifierNND;
	private String questionLabelNND;
	private Integer questionOrderNND;
	private String questionOID;
	private String questionOIDSystemTxt;
	
	private String questionIdentifier;
	private String nbsQuestionLabel;
	private String uiMetadataQuestionLabel;

	private String questionUnitIdentifier;
	private String unitParentIdentifier;

	private String hl7SegmentField;
	private String orderGroupId;

	private Integer questionGroupSeqNbr;
	private Integer answerGroupSeqNbr;
	
	private String questionRequiredNND;

	private String observationSubID;
	
	private String questionDataTypeNND;
	private String translationTableNm;

	private Long codesetGroupId;
	private String codeSetNm;
	private String classCd;

	private String answerTxt;
	private String answerLargeTxt;

	private String codedValue;
	private String codedValueDescription;
	private String codedValueCodingSystem;
	private String localCodedValue;
	private String localCodedValueDescription;
	private String localCodedValueCodingSystem;
	private String originalText;

	private String dataLocation;
	private String legacyDataLocation;

	private String dataCd;
	private String dataType;
	private String dataUseCd;
	private String partTypeCd;

	private String xmlPath;
	private String xmlTag;
	private String xmlDataType;
	
	private String unitTypeCd;
    private String unitValue;
    private String otherValueIndCd;

    private String batchTableHeader;
    private Integer batchTableColumnWidth;
    
    private Integer repeatGroupSeqNbr;
    private String questionMap;
    private String indicatorCd;

	private String namespaceId;
    private String universalId;
    private String universalIdType;
    
    
    /**
	 * @return the namespaceId
	 */
	public String getNamespaceId() {
		return namespaceId;
	}

	/**
	 * @param namespaceId the namespaceId to set
	 */
	public void setNamespaceId(String namespaceId) {
		this.namespaceId = namespaceId;
	}

	/**
	 * @return the universalId
	 */
	public String getUniversalId() {
		return universalId;
	}

	/**
	 * @param universalId the universalId to set
	 */
	public void setUniversalId(String universalId) {
		this.universalId = universalId;
	}

	/**
	 * @return the universalIdType
	 */
	public String getUniversalIdType() {
		return universalIdType;
	}

	/**
	 * @param universalIdType the universalIdType to set
	 */
	public void setUniversalIdType(String universalIdType) {
		this.universalIdType = universalIdType;
	}


	public String getQuestionIdentifierNND() {
		return questionIdentifierNND;
	}

	public void setQuestionIdentifierNND(String questionIdentifierNND) {
		this.questionIdentifierNND = questionIdentifierNND;
	}

	public String getQuestionLabelNND() {
		return questionLabelNND;
	}

	public void setQuestionLabelNND(String questionLabelNND) {
		this.questionLabelNND = questionLabelNND;
	}

	public Integer getQuestionOrderNND() {
		return questionOrderNND;
	}

	public void setQuestionOrderNND(Integer questionOrderNND) {
		this.questionOrderNND = questionOrderNND;
	}

	public String getQuestionOID() {
		return questionOID;
	}

	public void setQuestionOID(String questionOID) {
		this.questionOID = questionOID;
	}

	public String getQuestionOIDSystemTxt() {
		return questionOIDSystemTxt;
	}

	public void setQuestionOIDSystemTxt(String questionOIDSystemTxt) {
		this.questionOIDSystemTxt = questionOIDSystemTxt;
	}

	public String getQuestionIdentifier() {
		return questionIdentifier;
	}

	public void setQuestionIdentifier(String questionIdentifier) {
		this.questionIdentifier = questionIdentifier;
	}

	public String getNbsQuestionLabel() {
		return nbsQuestionLabel;
	}

	public void setNbsQuestionLabel(String nbsQuestionLabel) {
		this.nbsQuestionLabel = nbsQuestionLabel;
	}

	public String getUiMetadataQuestionLabel() {
		return uiMetadataQuestionLabel;
	}

	public void setUiMetadataQuestionLabel(String uiMetadataQuestionLabel) {
		this.uiMetadataQuestionLabel = uiMetadataQuestionLabel;
	}

	public String getQuestionUnitIdentifier() {
		return questionUnitIdentifier;
	}

	public void setQuestionUnitIdentifier(String questionUnitIdentifier) {
		this.questionUnitIdentifier = questionUnitIdentifier;
	}


	public String getUnitParentIdentifier() {
		return unitParentIdentifier;
	}

	public void setUnitParentIdentifier(String unitParentIdentifier) {
		this.unitParentIdentifier = unitParentIdentifier;
	}

	public String getHl7SegmentField() {
		return hl7SegmentField;
	}

	public void setHl7SegmentField(String hl7SegmentField) {
		this.hl7SegmentField = hl7SegmentField;
	}

	public String getOrderGroupId() {
		return orderGroupId;
	}

	public void setOrderGroupId(String orderGroupId) {
		this.orderGroupId = orderGroupId;
	}

	public Integer getQuestionGroupSeqNbr() {
		return questionGroupSeqNbr;
	}

	public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
		this.questionGroupSeqNbr = questionGroupSeqNbr;
	}


	public String getQuestionRequiredNND() {
		return questionRequiredNND;
	}

	public void setQuestionRequiredNND(String questionRequiredNND) {
		this.questionRequiredNND = questionRequiredNND;
	}

	//Used to set this value with value from answer_group_seq
	public void setObservationSubID(Integer observationSubID) {
		if (observationSubID != null)
			this.observationSubID = observationSubID.toString();
	}

	public void setObservationSubID(String observationSubID) {
		this.observationSubID = observationSubID;
	}

	public String getObservationSubID() {
		return observationSubID;
	}

	public String getQuestionDataTypeNND() {
		return questionDataTypeNND;
	}

	public void setQuestionDataTypeNND(String questionDataTypeNND) {
		this.questionDataTypeNND = questionDataTypeNND;
	}

	public String getTranslationTableNm() {
		return translationTableNm;
	}

	public void setTranslationTableNm(String translationTableNm) {
		this.translationTableNm = translationTableNm;
	}

	public Long getCodesetGroupId() {
		return codesetGroupId;
	}

	public void setCodesetGroupId(Long codesetGroupId) {
		this.codesetGroupId = codesetGroupId;
	}

	/**
	 * @return the codeSetNm
	 */
	public String getCodeSetNm() {
		return codeSetNm;
	}

	/**
	 * @param codeSetNm the codeSetNm to set
	 */
	public void setCodeSetNm(String codeSetNm) {
		this.codeSetNm = codeSetNm;
	}

	/**
	 * @return the classCd
	 */
	public String getClassCd() {
		return classCd;
	}

	/**
	 * @param classCd the classCd to set
	 */
	public void setClassCd(String classCd) {
		this.classCd = classCd;
	}

	public String getAnswerTxt() {
		return answerTxt;
	}

	public void setAnswerTxt(String answerTxt) {
		this.answerTxt = answerTxt;
	}

	public String getAnswerLargeTxt() {
		return answerLargeTxt;
	}

	public void setAnswerLargeTxt(String answerLargeTxt) {
		this.answerLargeTxt = answerLargeTxt;
	}

	/**
	 * @return the codedValue
	 */
	public String getCodedValue() {
		return codedValue;
	}

	/**
	 * @param codedValue the codedValue to set
	 */
	public void setCodedValue(String codedValue) {
		this.codedValue = codedValue;
	}

	/**
	 * @return the codedValueDescription
	 */
	public String getCodedValueDescription() {
		return codedValueDescription;
	}

	/**
	 * @param codedValueDescription the codedValueDescription to set
	 */
	public void setCodedValueDescription(String codedValueDescription) {
		this.codedValueDescription = codedValueDescription;
	}

	/**
	 * @return the codedValueCodingSystem
	 */
	public String getCodedValueCodingSystem() {
		return codedValueCodingSystem;
	}

	/**
	 * @param codedValueCodingSystem the codedValueCodingSystem to set
	 */
	public void setCodedValueCodingSystem(String codedValueCodingSystem) {
		this.codedValueCodingSystem = codedValueCodingSystem;
	}

	/**
	 * @return the localCodedValue
	 */
	public String getLocalCodedValue() {
		return localCodedValue;
	}

	/**
	 * @param localCodedValue the localCodedValue to set
	 */
	public void setLocalCodedValue(String localCodedValue) {
		this.localCodedValue = localCodedValue;
	}

	/**
	 * @return the localCodedValueDescription
	 */
	public String getLocalCodedValueDescription() {
		return localCodedValueDescription;
	}

	/**
	 * @param localCodedValueDescription the localCodedValueDescription to set
	 */
	public void setLocalCodedValueDescription(String localCodedValueDescription) {
		this.localCodedValueDescription = localCodedValueDescription;
	}

	/**
	 * @return the localCodedValueCodingSystem
	 */
	public String getLocalCodedValueCodingSystem() {
		return localCodedValueCodingSystem;
	}

	/**
	 * @param localCodedValueCodingSystem the localCodedValueCodingSystem to set
	 */
	public void setLocalCodedValueCodingSystem(String localCodedValueCodingSystem) {
		this.localCodedValueCodingSystem = localCodedValueCodingSystem;
	}

	public String getDataLocation() {
		return dataLocation;
	}

	public void setDataLocation(String dataLocation) {
		this.dataLocation = dataLocation;
	}

	public String getLegacyDataLocation() {
		return legacyDataLocation;
	}

	public void setLegacyDataLocation(String legacyDataLocation) {
		this.legacyDataLocation = legacyDataLocation;
	}

	public String getDataCd() {
		return dataCd;
	}

	public void setDataCd(String dataCd) {
		this.dataCd = dataCd;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataUseCd() {
		return dataUseCd;
	}

	public void setDataUseCd(String dataUseCd) {
		this.dataUseCd = dataUseCd;
	}

	public String getPartTypeCd() {
		return partTypeCd;
	}

	public void setPartTypeCd(String partTypeCd) {
		this.partTypeCd = partTypeCd;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String getXmlTag() {
		return xmlTag;
	}

	public void setXmlTag(String xmlTag) {
		this.xmlTag = xmlTag;
	}

	public String getXmlDataType() {
		return xmlDataType;
	}

	public void setXmlDataType(String xmlDataType) {
		this.xmlDataType = xmlDataType;
	}

	//RootDTInterface methods that are not implemented in new code

	/**
	 * @return the addReasonCd
	 */
	public String getAddReasonCd() {
		return null;
	}
	/**
	 * @param addReasonCd the addReasonCd to set
	 */
	public void setAddReasonCd(String addReasonCd) {
	}
	/**
	 * @return the addTime
	 */
	public Timestamp getAddTime() {
		return null;
	}
	/**
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Timestamp addTime) {
	}
	/**
	 * @return the addUserId
	 */
	public Long getAddUserId() {
		return null;
	}
	/**
	 * @param addUserId the addUserId to set
	 */
	public void setAddUserId(Long addUserId) {
	}
	/**
	 * @return the lastChgReasonCd
	 */
	public String getLastChgReasonCd() {
		return null;
	}
	/**
	 * @param lastChgReasonCd the lastChgReasonCd to set
	 */
	public void setLastChgReasonCd(String lastChgReasonCd) {
	}
	/**
	 * @return the lastChgTime
	 */
	public Timestamp getLastChgTime() {
		return null;
	}
	/**
	 * @param lastChgTime the lastChgTime to set
	 */
	public void setLastChgTime(Timestamp lastChgTime) {
	}
	/**
	 * @return the lastChguserId
	 */
	public Long getLastChgUserId() {
		return null;
	}
	/**
	 * @param lastChguserId the lastChguserId to set
	 */
	public void setLastChgUserId(Long lastChgUserId) {
	}


	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub

	}
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub

	}
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub

	}
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub

	}
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub

	}
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	//Represents the parent of the caseNotificationDataDT. This has been implemented for Labs, where the parent of the result is the order, and the parent of the susceptibility is the result
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid=uid;
	}
	
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub

	}
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub

	}
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub

	}
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub

	}
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub

	}
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the recordStatusCd
	 */
	public String getRecordStatusCd() {
		return null;
	}
	/**
	 * @param recordStatusCd the recordStatusCd to set
	 */
	public void setRecordStatusCd(String recordStatusCd) {
	}
	/**
	 * @return the recordStatusTime
	 */
	public Timestamp getRecordStatusTime() {
		return null;
	}
	/**
	 * @param recordStatusTime the recordStatusTime to set
	 */
	public void setRecordStatusTime(Timestamp recordStatusTime) {
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CaseNotificationDataDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}

	public void setUnitTypeCd(String unitTypeCd) {
		this.unitTypeCd = unitTypeCd;
	}

	public String getUnitTypeCd() {
		return unitTypeCd;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setOtherValueIndCd(String otherValueIndCd) {
		this.otherValueIndCd = otherValueIndCd;
	}

	public String getOtherValueIndCd() {
		return otherValueIndCd;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setBatchTableHeader(String batchTableHeader) {
		this.batchTableHeader = batchTableHeader;
	}

	public String getBatchTableHeader() {
		return batchTableHeader;
	}

	public void setBatchTableColumnWidth(Integer batchTableColumnWidth) {
		this.batchTableColumnWidth = batchTableColumnWidth;
	}

	public Integer getBatchTableColumnWidth() {
		return batchTableColumnWidth;
	}

	public void setAnswerGroupSeqNbr(Integer answerGroupSeqNbr) {
		this.answerGroupSeqNbr = answerGroupSeqNbr;
	}

	public Integer getAnswerGroupSeqNbr() {
		return answerGroupSeqNbr;
	}

	public Integer getRepeatGroupSeqNbr() {
		return repeatGroupSeqNbr;
	}

	public void setRepeatGroupSeqNbr(Integer repeatGroupSeqNbr) {
		this.repeatGroupSeqNbr = repeatGroupSeqNbr;
	}

	public String getQuestionMap() {
		return questionMap;
	}

	public void setQuestionMap(String questionMap) {
		this.questionMap = questionMap;
	}

	public String getIndicatorCd() {
		return indicatorCd;
	}

	public void setIndicatorCd(String indicatorCd) {
		this.indicatorCd = indicatorCd;
	}


	// Sort by part_type_cd and then by xml_path
	public static Comparator<Object> PHDCSort = new Comparator<Object>()
	{

		public int compare(Object obj1, Object obj2) {
			CaseNotificationDataDT npcd1 = (CaseNotificationDataDT) obj1;
			CaseNotificationDataDT npcd2 = (CaseNotificationDataDT) obj2;

			String partType1 = npcd1.getPartTypeCd();
			String partType2 = npcd2.getPartTypeCd();

			String xmlPath1 = npcd1.getXmlPath();
			String xmlPath2 = npcd2.getXmlPath();


			//No part_type - check the xml_path and just sort by it
			if (partType1 == null || partType2 == null) {
				if (xmlPath1 != null && xmlPath2 != null)
					return xmlPath1.compareTo(xmlPath2);
				else
					return 0;
			}

			//Compare part_type_cd (want to sort by these)
			int compareResult = partType1.compareTo(partType2);

			//If equal, check to see if there is an xml_path to sort by
			if (compareResult == 0) {
				if (xmlPath1 != null && xmlPath2 != null)
					return xmlPath1.compareTo(xmlPath2);
				else
					return compareResult;
			} else
				return compareResult;
		}
	};

	// Sort by questionGroupSeqNbr, answerGroupSeqNbr, QuestionIdentifier
	// Purpose is to sort repeating questions together and then the group of answer for each repeat of the question group
	public static Comparator<Object> PHDCDiseaseSpecificSort = new Comparator<Object>()
	{

		public int compare(Object obj1, Object obj2) {
			CaseNotificationDataDT npcd1 = (CaseNotificationDataDT) obj1;
			CaseNotificationDataDT npcd2 = (CaseNotificationDataDT) obj2;

			Integer questionGroupSeqNbr1 = npcd1.getQuestionGroupSeqNbr();
			Integer questionGroupSeqNbr2 = npcd2.getQuestionGroupSeqNbr();

			Integer answerGroupSeqNbr1 = npcd1.getAnswerGroupSeqNbr();
			Integer answerGroupSeqNbr2 = npcd2.getAnswerGroupSeqNbr();
			
			String questionIdentifierNbr1 = npcd1.getQuestionIdentifier();
			String questionIdentifierNbr2 = npcd2.getQuestionIdentifier();


			//Compare questionGroupSeqNbr (want to sort by these)
			if (questionGroupSeqNbr1==null || questionGroupSeqNbr2 ==null) {
				//No questionGroupSeqNbrs nor answerGroupSeqNbr, so return compare of QuestionIdentifiers
				return questionIdentifierNbr1.compareTo(questionIdentifierNbr2);
			} else {
				int compareResult = questionGroupSeqNbr1.compareTo(questionGroupSeqNbr2);
	
				//If equal, check to see if there are answerGroupSeqNbr's to sort by
				if (compareResult == 0) {
					int compareResult2 = 0;
					if (answerGroupSeqNbr1 != null && answerGroupSeqNbr2 != null) {
						compareResult2 = answerGroupSeqNbr1.compareTo(answerGroupSeqNbr2);
						if (compareResult2 == 0) {
							//Same questionGroupSeqNbrs and same answerGroupSeqNbrs, so return compare of QuestionIdentifiers
							return questionIdentifierNbr1.compareTo(questionIdentifierNbr2);
						} else {
							//Different answerGroupSeqNbrs
							return compareResult2;
						}
					} else {
						//Same questionGroupSeqNbrs, no answerGroupSeqNbrs, so return compare of QuestionIdentifiers
						return questionIdentifierNbr1.compareTo(questionIdentifierNbr2);
					}
				} else
					//Different questionGroupSeqNbrs
					return compareResult;
			}
		}
	};
	
	
	// Sort repeating OBX's to bottom and then ordered correctly.  If neither are populated, then sort order
	// from query and then later Collection<Object>  adds will be in effect.
	// So sort is:
	//		o First sort by observationSubID (so sort observationSubID ASC with NULLs first)
	//		o Then sort by questionOrderNND
	//      o Lastly, if all other are equal (or more likely null) - sort by questionIdentifierNND
	public static Comparator<Object> HL7MessageSort = new Comparator<Object>()
	{
		public int compare(Object obj1, Object obj2) {
			CaseNotificationDataDT ncd1 = (CaseNotificationDataDT) obj1;
			CaseNotificationDataDT ncd2 = (CaseNotificationDataDT) obj2;

			String observationSubID = ncd1.getObservationSubID();
			String observationSubID2 = ncd2.getObservationSubID();

			Integer questionOrderNND = ncd1.getQuestionOrderNND();
			Integer questionOrderNND2 = ncd2.getQuestionOrderNND();

			String nndIdentifier = ncd1.getQuestionIdentifier();
			String nndIdentifier2 = ncd2.getQuestionIdentifier();
			
			
			if (observationSubID != null && observationSubID2 == null)
				return 1;

			if (observationSubID == null && observationSubID2 != null) 
				return -1;

			if (observationSubID != null && observationSubID2 != null) {
				int obsSubID = Integer.parseInt(observationSubID);
				int obsSubID2 = Integer.parseInt(observationSubID2);
				if (obsSubID < obsSubID2)
					return -1;
				if (obsSubID > obsSubID2)
					return 1;
			}
			
			if (questionOrderNND != null && questionOrderNND2 == null)
				return 1;

			if (questionOrderNND == null && questionOrderNND2 != null) 
				return -1;
			
			if (questionOrderNND != null && questionOrderNND2 != null) {
				int compare = questionOrderNND.compareTo(questionOrderNND2);
				return compare;
			} 

			if (nndIdentifier != null && nndIdentifier2 == null)
				return 1;

			if (nndIdentifier == null && nndIdentifier2 != null) 
				return -1;
			
			if (nndIdentifier != null && nndIdentifier2 != null) {
				int compare = nndIdentifier.compareTo(nndIdentifier2);
				return compare;
			} else
				return 0;
		}
	};

	public Long getObservationUid() {
		return observationUid;
	}

	public void setObservationUid(Long observationUid) {
		this.observationUid = observationUid;
	}
	public Object deepCopy() throws Exception
	 {
          Object deepCopy = null;
          try {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          ObjectOutputStream oos = new ObjectOutputStream(baos);
          oos.writeObject(this);
          ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
          ObjectInputStream ois = new ObjectInputStream(bais);
          deepCopy = ois.readObject();
          } catch (Exception e) {
            e.printStackTrace();
            throw e;
          }
          return  deepCopy;

	 }

}

