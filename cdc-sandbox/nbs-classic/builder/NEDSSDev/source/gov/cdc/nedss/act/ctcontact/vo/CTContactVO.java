package gov.cdc.nedss.act.ctcontact.vo;

import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.association.dt.ActRelationshipDT;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.ReportSummaryInterface;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.dt.EDXEventProcessDT;
import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CTContactVO extends AbstractVO implements ReportSummaryInterface, RootDTInterface{

	private static final long serialVersionUID = 1L;
	private CTContactDT cTContactDT =  new CTContactDT();
	private PersonVO contactPersonVO = new PersonVO();
	private Map<Object, Object> ctContactAnswerDTMap;
	private Collection<Object> actEntityDTCollection;
	private Collection<Object> noteDTCollection;
	private Map<Object, Object> repeatingAnswerDTMap;
	   private Collection<EDXEventProcessDT> edxEventProcessDTCollection;
	   private Collection<ActRelationshipDT> theActRelationshipDTCollection;
	   private boolean isAssociateDissasociate = false;
	

	public Collection<ActRelationshipDT> getTheActRelationshipDTCollection() {
		return theActRelationshipDTCollection;
	}
	public void setTheActRelationshipDTCollection(
			Collection<ActRelationshipDT> theActRelationshipDTCollection) {
		this.theActRelationshipDTCollection = theActRelationshipDTCollection;
	}
	public Collection<EDXEventProcessDT> getEdxEventProcessDTCollection() {
		return edxEventProcessDTCollection;
	}
	public void setEdxEventProcessDTCollection(
			Collection<EDXEventProcessDT> edxEventProcessDTCollection) {
		this.edxEventProcessDTCollection = edxEventProcessDTCollection;
	}
	public Collection<Object> getNoteDTCollection() {
		return noteDTCollection;
	}
	public void setNoteDTCollection(Collection<Object> noteDTCollection) {
		this.noteDTCollection = noteDTCollection;
	}
	public Collection<Object> getActEntityDTCollection() {
		return actEntityDTCollection;
	}
	public void setActEntityDTCollection(Collection<Object> actEntityDTCollection) {
		this.actEntityDTCollection = actEntityDTCollection;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	public CTContactDT getcTContactDT() {
		return cTContactDT;
	}
	public void setcTContactDT(CTContactDT cTContactDT) {
		this.cTContactDT = cTContactDT;
	}
	public PersonVO getContactPersonVO() {
		return contactPersonVO;
	}
	public void setContactPersonVO(PersonVO contactPersonVO) {
		this.contactPersonVO = contactPersonVO;
	}
	public Map<Object, Object> getCtContactAnswerDTMap() {
		return ctContactAnswerDTMap == null ? new HashMap<Object, Object>() : ctContactAnswerDTMap;
	}


	public void setCtContactAnswerDTMap(Map<Object, Object> ctContactAnswerDTMap) {
		this.ctContactAnswerDTMap = ctContactAnswerDTMap;
	}
	@Override
	public Timestamp getActivityFromTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean getIsAssociated() {	
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean getIsTouched() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Long getObservationUid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setActivityFromTime(Timestamp aActivityFromTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItAssociated(boolean associated) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItTouched(boolean touched) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setObservationUid(Long observationUid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Timestamp getAddTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getLastChgTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
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
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return itDelete;
	}
	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return itDirty;
	}
	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return itNew;
	}
	@Override
	public void setAddTime(Timestamp aAddTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgTime(Timestamp aLastChgTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CTContactDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}
	public Map<Object, Object> getRepeatingAnswerDTMap() {
		return repeatingAnswerDTMap;
	}
	public void setRepeatingAnswerDTMap(Map<Object, Object> repeatingAnswerDTMap) {
		this.repeatingAnswerDTMap = repeatingAnswerDTMap;
	}
	public boolean isAssociateDissasociate() {
		return isAssociateDissasociate;
	}
	public void setAssociateDissasociate(boolean isAssociateDissasociate) {
		this.isAssociateDissasociate = isAssociateDissasociate;
	}

}
