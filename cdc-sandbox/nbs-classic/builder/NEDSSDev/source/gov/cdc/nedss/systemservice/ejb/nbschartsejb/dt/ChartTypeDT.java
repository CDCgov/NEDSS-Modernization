package gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * @author vsiram2
 *
 */
public class ChartTypeDT extends AbstractVO implements RootDTInterface, Comparable<Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long chart_type_uid;
	private String chart_type_cd;
	private String chart_type_desc_txt;
	private String record_status_cd;
	private Timestamp record_status_time;
	private Long add_user_id;
	private Timestamp add_time;
	private Long last_chg_user_id;
	private Timestamp last_chg_time;

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
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
		return false;
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAddTime(Timestamp addTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAddUserId(Long addUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastChgTime(Timestamp lastChgTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLastChgUserId(Long lastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocalId(String localId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRecordStatusCd(String recordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Long getChart_type_uid() {
		return chart_type_uid;
	}

	public void setChart_type_uid(Long chart_type_uid) {
		this.chart_type_uid = chart_type_uid;
	}

	public String getChart_type_cd() {
		return chart_type_cd;
	}

	public void setChart_type_cd(String chart_type_cd) {
		this.chart_type_cd = chart_type_cd;
	}

	public String getChart_type_desc_txt() {
		return chart_type_desc_txt;
	}

	public void setChart_type_desc_txt(String chart_type_desc_txt) {
		this.chart_type_desc_txt = chart_type_desc_txt;
	}

	public String getRecord_status_cd() {
		return record_status_cd;
	}

	public void setRecord_status_cd(String record_status_cd) {
		this.record_status_cd = record_status_cd;
	}

	public Timestamp getRecord_status_time() {
		return record_status_time;
	}

	public void setRecord_status_time(Timestamp record_status_time) {
		this.record_status_time = record_status_time;
	}

	public Long getAdd_user_id() {
		return add_user_id;
	}

	public void setAdd_user_id(Long add_user_id) {
		this.add_user_id = add_user_id;
	}

	public Timestamp getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}

	public Long getLast_chg_user_id() {
		return last_chg_user_id;
	}

	public void setLast_chg_user_id(Long last_chg_user_id) {
		this.last_chg_user_id = last_chg_user_id;
	}

	public Timestamp getLast_chg_time() {
		return last_chg_time;
	}

	public void setLast_chg_time(Timestamp last_chg_time) {
		this.last_chg_time = last_chg_time;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}