package gov.cdc.nedss.nnd.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 * Name:		SummaryCaseRepeatingDataDT.java
 * Description:	VO to retrieve Agg Summary Case data from NBS Question metadata tables
 * and NBS Answer to construct Summary Notification Messages (NND Intermediary Message)
 * Copyright:	Copyright (c) 2009
 * Company: 	Computer Sciences Corporation
 * @author	Beau Bannerman
 */
public class SummaryCaseRepeatingDataDT extends AbstractVO implements RootDTInterface {
	private static final long serialVersionUID = 1L;
	
	private String indAnswerTxt;
	private String indQuestionIdentifier;
	private String indQuestionIdentifierNND;
	private Integer indQuestionOrderNND;
	private Long indCodesetGroupId;
	private String indQuestionOID;
	private String indQuestionOIDSystemTxt;
	private String indOrderGroupId;
	private String indQuestionRequiredNND;
	private String indDataLocation;
	private String indQuestionDataTypeNND;
	private String indHl7SegmentField;
	private String indNbsQuestionLabel;
	private String indQuestionLabelNND;

	private String aggAnswerTxt;
	private String aggQuestionIdentifier;
	private String aggQuestionIdentifierNND;
	private Integer aggQuestionOrderNND;
	private Long aggCodesetGroupId;
	private String aggQuestionOID;
	private String aggQuestionOIDSystemTxt;
	private String aggOrderGroupId;
	private String aggQuestionRequiredNND;
	private String aggDataLocation;
	private String aggQuestionDataTypeNND;
	private String aggHl7SegmentField;
	private String aggNbsQuestionLabel;
	private String aggQuestionLabelNND;

	private String tcAnswerTxt;
	private String tcQuestionIdentifier;
	private String tcQuestionIdentifierNND;
	private Integer tcQuestionOrderNND;
	private Long tcCodesetGroupId;
	private String tcQuestionOID;
	private String tcQuestionOIDSystemTxt;
	private String tcOrderGroupId;
	private String tcQuestionRequiredNND;
	private String tcDataLocation;
	private String tcQuestionDataTypeNND;
	private String tcHl7SegmentField;
	private String tcNbsQuestionLabel;
	private String tcQuestionLabelNND;

	private Integer aggSeqNbr;
	private Integer indSeqNbr;
	
	
	public String getIndAnswerTxt() {
		return indAnswerTxt;
	}
	public void setIndAnswerTxt(String indAnswerTxt) {
		this.indAnswerTxt = indAnswerTxt;
	}
	public String getIndQuestionIdentifier() {
		return indQuestionIdentifier;
	}
	public void setIndQuestionIdentifier(String indQuestionIdentifier) {
		this.indQuestionIdentifier = indQuestionIdentifier;
	}
	public String getIndQuestionIdentifierNND() {
		return indQuestionIdentifierNND;
	}
	public void setIndQuestionIdentifierNND(
			String indQuestionIdentifierNND) {
		this.indQuestionIdentifierNND = indQuestionIdentifierNND;
	}
	public Integer getIndQuestionOrderNND() {
		return indQuestionOrderNND;
	}
	public void setIndQuestionOrderNND(Integer indQuestionOrderNND) {
		this.indQuestionOrderNND = indQuestionOrderNND;
	}
	public Long getIndCodesetGroupId() {
		return indCodesetGroupId;
	}
	public void setIndCodesetGroupId(Long indCodesetGroupId) {
		this.indCodesetGroupId = indCodesetGroupId;
	}
	public String getIndQuestionOID() {
		return indQuestionOID;
	}
	public void setIndQuestionOID(String indQuestionOID) {
		this.indQuestionOID = indQuestionOID;
	}
	public String getIndQuestionOIDSystemTxt() {
		return indQuestionOIDSystemTxt;
	}
	public void setIndQuestionOIDSystemTxt(
			String indQuestionOIDSystemTxt) {
		this.indQuestionOIDSystemTxt = indQuestionOIDSystemTxt;
	}
	public String getIndOrderGroupId() {
		return indOrderGroupId;
	}
	public void setIndOrderGroupId(String indOrderGroupId) {
		this.indOrderGroupId = indOrderGroupId;
	}
	public String getIndQuestionRequiredNND() {
		return indQuestionRequiredNND;
	}
	public void setIndQuestionRequiredNND(String indQuestionRequiredNND) {
		this.indQuestionRequiredNND = indQuestionRequiredNND;
	}
	public String getIndDataLocation() {
		return indDataLocation;
	}
	public void setIndDataLocation(String indDataLocation) {
		this.indDataLocation = indDataLocation;
	}
	public String getIndQuestionDataTypeNND() {
		return indQuestionDataTypeNND;
	}
	public void setIndQuestionDataTypeNND(String indQuestionDataTypeNND) {
		this.indQuestionDataTypeNND = indQuestionDataTypeNND;
	}
	public String getIndHl7SegmentField() {
		return indHl7SegmentField;
	}
	public void setIndHl7SegmentField(String indHl7SegmentField) {
		this.indHl7SegmentField = indHl7SegmentField;
	}
	public String getIndNbsQuestionLabel() {
		return indNbsQuestionLabel;
	}
	public void setIndNbsQuestionLabel(String indNbsQuestionLabel) {
		this.indNbsQuestionLabel = indNbsQuestionLabel;
	}
	public String getIndQuestionLabelNND() {
		return indQuestionLabelNND;
	}
	public void setIndQuestionLabelNND(String indQuestionLabelNND) {
		this.indQuestionLabelNND = indQuestionLabelNND;
	}
	public String getAggAnswerTxt() {
		return aggAnswerTxt;
	}
	public void setAggAnswerTxt(String aggAnswerTxt) {
		this.aggAnswerTxt = aggAnswerTxt;
	}
	public String getAggQuestionIdentifier() {
		return aggQuestionIdentifier;
	}
	public void setAggQuestionIdentifier(String aggQuestionIdentifier) {
		this.aggQuestionIdentifier = aggQuestionIdentifier;
	}
	public String getAggQuestionIdentifierNND() {
		return aggQuestionIdentifierNND;
	}
	public void setAggQuestionIdentifierNND(
			String aggQuestionIdentifierNND) {
		this.aggQuestionIdentifierNND = aggQuestionIdentifierNND;
	}
	public Integer getAggQuestionOrderNND() {
		return aggQuestionOrderNND;
	}
	public void setAggQuestionOrderNND(Integer aggQuestionOrderNND) {
		this.aggQuestionOrderNND = aggQuestionOrderNND;
	}
	public Long getAggCodesetGroupId() {
		return aggCodesetGroupId;
	}
	public void setAggCodesetGroupId(Long aggCodesetGroupId) {
		this.aggCodesetGroupId = aggCodesetGroupId;
	}
	public String getAggQuestionOID() {
		return aggQuestionOID;
	}
	public void setAggQuestionOID(String aggQuestionOID) {
		this.aggQuestionOID = aggQuestionOID;
	}
	public String getAggQuestionOIDSystemTxt() {
		return aggQuestionOIDSystemTxt;
	}
	public void setAggQuestionOIDSystemTxt(
			String aggQuestionOIDSystemTxt) {
		this.aggQuestionOIDSystemTxt = aggQuestionOIDSystemTxt;
	}
	public String getAggOrderGroupId() {
		return aggOrderGroupId;
	}
	public void setAggOrderGroupId(String aggOrderGroupId) {
		this.aggOrderGroupId = aggOrderGroupId;
	}
	public String getAggQuestionRequiredNND() {
		return aggQuestionRequiredNND;
	}
	public void setAggQuestionRequiredNND(String aggQuestionRequiredNND) {
		this.aggQuestionRequiredNND = aggQuestionRequiredNND;
	}
	public String getAggDataLocation() {
		return aggDataLocation;
	}
	public void setAggDataLocation(String aggDataLocation) {
		this.aggDataLocation = aggDataLocation;
	}
	public String getAggQuestionDataTypeNND() {
		return aggQuestionDataTypeNND;
	}
	public void setAggQuestionDataTypeNND(String aggQuestionDataTypeNND) {
		this.aggQuestionDataTypeNND = aggQuestionDataTypeNND;
	}
	public String getAggHl7SegmentField() {
		return aggHl7SegmentField;
	}
	public void setAggHl7SegmentField(String aggHl7SegmentField) {
		this.aggHl7SegmentField = aggHl7SegmentField;
	}
	public String getAggNbsQuestionLabel() {
		return aggNbsQuestionLabel;
	}
	public void setAggNbsQuestionLabel(String aggNbsQuestionLabel) {
		this.aggNbsQuestionLabel = aggNbsQuestionLabel;
	}
	public String getAggQuestionLabelNND() {
		return aggQuestionLabelNND;
	}
	public void setAggQuestionLabelNND(String aggQuestionLabelNND) {
		this.aggQuestionLabelNND = aggQuestionLabelNND;
	}
	public String getTcAnswerTxt() {
		return tcAnswerTxt;
	}
	public void setTcAnswerTxt(String tcAnswerTxt) {
		this.tcAnswerTxt = tcAnswerTxt;
	}
	public String getTcQuestionIdentifier() {
		return tcQuestionIdentifier;
	}
	public void setTcQuestionIdentifier(String tcQuestionIdentifier) {
		this.tcQuestionIdentifier = tcQuestionIdentifier;
	}
	public String getTcQuestionIdentifierNND() {
		return tcQuestionIdentifierNND;
	}
	public void setTcQuestionIdentifierNND(
			String tcQuestionIdentifierNND) {
		this.tcQuestionIdentifierNND = tcQuestionIdentifierNND;
	}
	public Integer getTcQuestionOrderNND() {
		return tcQuestionOrderNND;
	}
	public void setTcQuestionOrderNND(Integer tcQuestionOrderNND) {
		this.tcQuestionOrderNND = tcQuestionOrderNND;
	}
	public Long getTcCodesetGroupId() {
		return tcCodesetGroupId;
	}
	public void setTcCodesetGroupId(Long tcCodesetGroupId) {
		this.tcCodesetGroupId = tcCodesetGroupId;
	}
	public String getTcQuestionOID() {
		return tcQuestionOID;
	}
	public void setTcQuestionOID(String tcQuestionOID) {
		this.tcQuestionOID = tcQuestionOID;
	}
	public String getTcQuestionOIDSystemTxt() {
		return tcQuestionOIDSystemTxt;
	}
	public void setTcQuestionOIDSystemTxt(
			String tcQuestionOIDSystemTxt) {
		this.tcQuestionOIDSystemTxt = tcQuestionOIDSystemTxt;
	}
	public String getTcOrderGroupId() {
		return tcOrderGroupId;
	}
	public void setTcOrderGroupId(String tcOrderGroupId) {
		this.tcOrderGroupId = tcOrderGroupId;
	}
	public String getTcQuestionRequiredNND() {
		return tcQuestionRequiredNND;
	}
	public void setTcQuestionRequiredNND(
			String tcQuestionRequiredNND) {
		this.tcQuestionRequiredNND = tcQuestionRequiredNND;
	}
	public String getTcDataLocation() {
		return tcDataLocation;
	}
	public void setTcDataLocation(String tcDataLocation) {
		this.tcDataLocation = tcDataLocation;
	}
	public String getTcQuestionDataTypeNND() {
		return tcQuestionDataTypeNND;
	}
	public void setTcQuestionDataTypeNND(
			String tcQuestionDataTypeNND) {
		this.tcQuestionDataTypeNND = tcQuestionDataTypeNND;
	}
	public String getTcHl7SegmentField() {
		return tcHl7SegmentField;
	}
	public void setTcHl7SegmentField(String tcHl7SegmentField) {
		this.tcHl7SegmentField = tcHl7SegmentField;
	}
	public String getTcNbsQuestionLabel() {
		return tcNbsQuestionLabel;
	}
	public void setTcNbsQuestionLabel(String tcNbsQuestionLabel) {
		this.tcNbsQuestionLabel = tcNbsQuestionLabel;
	}
	public String getTcQuestionLabelNND() {
		return tcQuestionLabelNND;
	}
	public void setTcQuestionLabelNND(String tcQuestionLabelNND) {
		this.tcQuestionLabelNND = tcQuestionLabelNND;
	}
	public Integer getAggSeqNbr() {
		return aggSeqNbr;
	}           
	public void setAggSeqNbr(Integer aggSeqNbr) {
		this.aggSeqNbr = aggSeqNbr;
	}
	public Integer getIndSeqNbr() {
		return indSeqNbr;
	}
	public void setIndSeqNbr(Integer indSeqNbr) {
		this.indSeqNbr = indSeqNbr;
	}
	
	public Collection<Object>  toCaseNotificationDTs(String observationSubID) {
		ArrayList<Object> caseNotificationDTCollection  = new ArrayList<Object> ();
		CaseNotificationDataDT tcCaseNotificationDT = new CaseNotificationDataDT();
		CaseNotificationDataDT aggCaseNotificationDT = new CaseNotificationDataDT();
		CaseNotificationDataDT indCaseNotificationDT = new CaseNotificationDataDT();
		
		tcCaseNotificationDT.setObservationSubID(observationSubID);
		tcCaseNotificationDT.setQuestionGroupSeqNbr(Integer.decode(observationSubID));
		tcCaseNotificationDT.setQuestionIdentifierNND(this.getTcQuestionIdentifierNND());
		tcCaseNotificationDT.setQuestionLabelNND(this.getTcQuestionLabelNND());
		tcCaseNotificationDT.setQuestionOrderNND(this.getTcQuestionOrderNND());
		tcCaseNotificationDT.setQuestionOID(this.getTcQuestionOID());
		tcCaseNotificationDT.setQuestionOIDSystemTxt(this.getTcQuestionOIDSystemTxt());
		tcCaseNotificationDT.setQuestionIdentifier(this.getTcQuestionIdentifier());
		tcCaseNotificationDT.setNbsQuestionLabel(this.getTcNbsQuestionLabel());
		tcCaseNotificationDT.setHl7SegmentField(this.getTcHl7SegmentField());
		tcCaseNotificationDT.setOrderGroupId(this.getTcOrderGroupId());
		tcCaseNotificationDT.setQuestionRequiredNND(this.getTcQuestionRequiredNND());
		tcCaseNotificationDT.setQuestionDataTypeNND(this.getTcQuestionDataTypeNND());
		tcCaseNotificationDT.setCodesetGroupId(this.getTcCodesetGroupId());
		tcCaseNotificationDT.setAnswerTxt(this.getTcAnswerTxt());
		
		aggCaseNotificationDT.setObservationSubID(observationSubID);
		aggCaseNotificationDT.setQuestionGroupSeqNbr(Integer.decode(observationSubID));
		aggCaseNotificationDT.setQuestionIdentifierNND(this.getAggQuestionIdentifierNND());
		aggCaseNotificationDT.setQuestionLabelNND(this.getAggQuestionLabelNND());
		aggCaseNotificationDT.setQuestionOrderNND(this.getAggQuestionOrderNND());
		aggCaseNotificationDT.setQuestionOID(this.getAggQuestionOID());
		aggCaseNotificationDT.setQuestionOIDSystemTxt(this.getAggQuestionOIDSystemTxt());
		aggCaseNotificationDT.setQuestionIdentifier(this.getAggQuestionIdentifier());
		aggCaseNotificationDT.setNbsQuestionLabel(this.getAggNbsQuestionLabel());
		aggCaseNotificationDT.setHl7SegmentField(this.getAggHl7SegmentField());
		aggCaseNotificationDT.setOrderGroupId(this.getAggOrderGroupId());
		aggCaseNotificationDT.setQuestionRequiredNND(this.getAggQuestionRequiredNND());
		aggCaseNotificationDT.setQuestionDataTypeNND(this.getAggQuestionDataTypeNND());
		aggCaseNotificationDT.setCodesetGroupId(this.getAggCodesetGroupId());
		aggCaseNotificationDT.setAnswerTxt(this.getAggAnswerTxt());

		indCaseNotificationDT.setObservationSubID(observationSubID);
		indCaseNotificationDT.setQuestionGroupSeqNbr(Integer.decode(observationSubID));
		indCaseNotificationDT.setQuestionIdentifierNND(this.getIndQuestionIdentifierNND());
		indCaseNotificationDT.setQuestionLabelNND(this.getIndQuestionLabelNND());
		indCaseNotificationDT.setQuestionOrderNND(this.getIndQuestionOrderNND());
		indCaseNotificationDT.setQuestionOID(this.getIndQuestionOID());
		indCaseNotificationDT.setQuestionOIDSystemTxt(this.getIndQuestionOIDSystemTxt());
		indCaseNotificationDT.setQuestionIdentifier(this.getIndQuestionIdentifier());
		indCaseNotificationDT.setNbsQuestionLabel(this.getIndNbsQuestionLabel());
		indCaseNotificationDT.setHl7SegmentField(this.getIndHl7SegmentField());
		indCaseNotificationDT.setOrderGroupId(this.getIndOrderGroupId());
		indCaseNotificationDT.setQuestionRequiredNND(this.getIndQuestionRequiredNND());
		indCaseNotificationDT.setQuestionDataTypeNND(this.getIndQuestionDataTypeNND());
		indCaseNotificationDT.setCodesetGroupId(this.getIndCodesetGroupId());
		indCaseNotificationDT.setAnswerTxt(this.getIndAnswerTxt());
		
		caseNotificationDTCollection.add(tcCaseNotificationDT);
		caseNotificationDTCollection.add(aggCaseNotificationDT);
		caseNotificationDTCollection.add(indCaseNotificationDT);
		return caseNotificationDTCollection;
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


	public boolean isEqual(Object objectname1, Object objectname2, Class voClass) {
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
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
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
			Field[] f = SummaryCaseRepeatingDataDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}


	// Need to sort by Agg Sequence number and then Ind Sequence number
	public static Comparator AggIndSort = new Comparator()
	{
		public int compare(Object obj1, Object obj2) {
			SummaryCaseRepeatingDataDT sumCase1 = (SummaryCaseRepeatingDataDT) obj1;
			SummaryCaseRepeatingDataDT sumCase2 = (SummaryCaseRepeatingDataDT) obj2;

			Integer aggSeqNbr1 = sumCase1.getAggSeqNbr();
			Integer aggSeqNbr2 = sumCase2.getAggSeqNbr();

			Integer indSeqNbr1 = sumCase1.getIndSeqNbr();
			Integer indSeqNbr2 = sumCase2.getIndSeqNbr();


			if (aggSeqNbr1 == null && aggSeqNbr2 == null)
				return 0;

			if (aggSeqNbr1 != null && aggSeqNbr2 == null)
				return 1;

			if (aggSeqNbr1 == null && aggSeqNbr2 != null)
				return -1;

			//Compare aggSeqNbr (want to sort by these)
			int compareResult = aggSeqNbr1.compareTo(aggSeqNbr2);

			//If equal, check to see if there is a question_identifier_nnd to sort by
			if (compareResult == 0) {
				if (indSeqNbr1 != null && indSeqNbr2 != null)
					return indSeqNbr1.compareTo(indSeqNbr2);
				else
					return compareResult;
			} else
				return compareResult;
		}
	};
	
	
}
