package gov.cdc.nedss.page.ejb.portproxyejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.webapp.nbs.action.pagemanagement.portpage.util.PortPageUtil;

import java.sql.Timestamp;

public class ManagePageDT  extends AbstractVO implements RootDTInterface{
	private Long nbsConversionPageMgmtUid;
	private String eventTypeCd;
	private String mapName;
	private Long fromPageWaTemplateUid;
	private Long toPageWaTemplateUid;
	private String mappingStatusCd = PortPageUtil.NBS_PAGE_MAPPING_STATUS_MAPPING_IN_PROGRESS;
	private String conditionCd;
	private Timestamp portTime;
	private String portStatusCd;
	private Timestamp addTime;
	private Long addUserId;	
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private String portComment;
	
	private String fromPageName;
	private String toPageName;
	private String conditionDescText;
	private String conditionDescWithLink;

	private String mappingStatusDescText;
	private String eventTypeDescTxt;
	
	private String fromPageFormCd;
	private String toPageFormCd;
	private boolean isMappingLocked = false;
	
	public Long getNbsConversionPageMgmtUid() {
		return nbsConversionPageMgmtUid;
	}
	public void setNbsConversionPageMgmtUid(Long nbsConversionPageMgmtUid) {
		this.nbsConversionPageMgmtUid = nbsConversionPageMgmtUid;
	}
	public String getEventTypeCd() {
		return eventTypeCd;
	}
	public void setEventTypeCd(String eventTypeCd) {
		this.eventTypeCd = eventTypeCd;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public Long getFromPageWaTemplateUid() {
		return fromPageWaTemplateUid;
	}
	public void setFromPageWaTemplateUid(Long fromPageWaTemplateUid) {
		this.fromPageWaTemplateUid = fromPageWaTemplateUid;
	}
	public Long getToPageWaTemplateUid() {
		return toPageWaTemplateUid;
	}
	public void setToPageWaTemplateUid(Long toPageWaTemplateUid) {
		this.toPageWaTemplateUid = toPageWaTemplateUid;
	}
	public String getMappingStatusCd() {
		return mappingStatusCd;
	}
	public void setMappingStatusCd(String mappingStatusCd) {
		this.mappingStatusCd = mappingStatusCd;
	}
	public String getConditionCd() {
		return conditionCd;
	}
	public void setConditionCd(String conditionCd) {
		this.conditionCd = conditionCd;
	}
	public Timestamp getPortTime() {
		return portTime;
	}
	public void setPortTime(Timestamp portTime) {
		this.portTime = portTime;
	}
	public String getPortStatusCd() {
		return portStatusCd;
	}
	public void setPortStatusCd(String portStatusCd) {
		this.portStatusCd = portStatusCd;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}
	public String getPortComment() {
		return portComment;
	}
	public void setPortComment(String portComment) {
		this.portComment = portComment;
	}
	public String getFromPageName() {
		return fromPageName;
	}
	public void setFromPageName(String fromPageName) {
		this.fromPageName = fromPageName;
	}
	public String getToPageName() {
		return toPageName;
	}
	public void setToPageName(String toPageName) {
		this.toPageName = toPageName;
	}
	public String getConditionDescText() {
		return conditionDescText;
	}
	public void setConditionDescText(String conditionDescText) {
		this.conditionDescText = conditionDescText;
	}
	public String getMappingStatusDescText() {
		return mappingStatusDescText;
	}
	public void setMappingStatusDescText(String mappingStatusDescText) {
		this.mappingStatusDescText = mappingStatusDescText;
	}
	public String getEventTypeDescTxt() {
		return eventTypeDescTxt;
	}
	public void setEventTypeDescTxt(String eventTypeDescTxt) {
		this.eventTypeDescTxt = eventTypeDescTxt;
	}
	
	public String getFromPageFormCd() {
		return fromPageFormCd;
	}
	public void setFromPageFormCd(String fromPageFormCd) {
		this.fromPageFormCd = fromPageFormCd;
	}
	public String getToPageFormCd() {
		return toPageFormCd;
	}
	public void setToPageFormCd(String toPageFormCd) {
		this.toPageFormCd = toPageFormCd;
	}
	public boolean isMappingLocked() {
		return isMappingLocked;
	}
	public void setMappingLocked(boolean isMappingLocked) {
		this.isMappingLocked = isMappingLocked;
	}
	public String getConditionDescWithLink() {
		return conditionDescWithLink;
	}
	public void setConditionDescWithLink(String conditionDescWithLink) {
		this.conditionDescWithLink = conditionDescWithLink;
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
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLocalId(String aLocalId) {
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
	
		
}
