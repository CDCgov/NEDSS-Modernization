package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt;

import java.io.Serializable;
import java.sql.Timestamp;

public class EdxCDAInformationDT implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private Timestamp addTime;
	private long patientUid;
	private long userId;
	private int nextUid;
	private long entityUid;
	private boolean isProviderEntity = false;
	private boolean isOrganizationEntity = false;
	private boolean isPlaceEntity = false;
	private EdxRuleAlgorothmManagerDT ruleAlgorothmManagerDT;
	private long targetActUid;
	private String sourcePatientId;
	private Timestamp asOfDate;

	public String getSourcePatientId() {
		return sourcePatientId;
	}

	public void setSourcePatientId(String sourcePatientId) {
		this.sourcePatientId = sourcePatientId;
	}

	public EdxCDAInformationDT(EdxRuleAlgorothmManagerDT ruleAlgorothmManagerDT) {
		this.setRuleAlgorothmManagerDT(ruleAlgorothmManagerDT);
	}

	public void setProviderEntity(long entityUid) {
		this.entityUid = entityUid;
		isProviderEntity = true;
		isOrganizationEntity = false;
		isPlaceEntity = false;
	}

	public void setOrganizationEntity(long entityUid) {
		this.entityUid = entityUid;
		isProviderEntity = false;
		isOrganizationEntity = true;
		isPlaceEntity = false;
	}
	
	public void setPlaceEntity(long entityUid) {
		this.entityUid = entityUid;
		isProviderEntity = false;
		isOrganizationEntity = false;
		isPlaceEntity = true;
	}
	
	
	public long getTargetActUid() {
		return targetActUid;
	}
	
	public void setTargetActUid(long targetActUid) {
		this.targetActUid = targetActUid;
	}
	
	public EdxCDAInformationDT() {
	}
	
	public boolean isProviderEntity() {
		return isProviderEntity;
	}

	public boolean isOrganizationEntity() {
		return isOrganizationEntity;
	}

	public long getEntityUid() {
		return entityUid;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public long getPatientUid() {
		return patientUid;
	}

	public void setPatientUid(long patientUid) {
		this.patientUid = patientUid;
		this.entityUid = patientUid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getNextUid() {
		return nextUid;
	}

	public void setNextUid(int nextUid) {
		this.nextUid = nextUid;
	}

	public EdxRuleAlgorothmManagerDT getRuleAlgorothmManagerDT() {
		return ruleAlgorothmManagerDT;
	}

	public void setRuleAlgorothmManagerDT(EdxRuleAlgorothmManagerDT ruleAlgorothmManagerDT) {
		this.ruleAlgorothmManagerDT = ruleAlgorothmManagerDT;
	}

	public boolean isPlaceEntity() {
		return isPlaceEntity;
	}

	public Timestamp getAsOfDate() {
		return asOfDate;
	}

	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}
}
