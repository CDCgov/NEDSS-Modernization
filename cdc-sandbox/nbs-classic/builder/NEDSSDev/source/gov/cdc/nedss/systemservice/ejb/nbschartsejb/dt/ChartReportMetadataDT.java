package gov.cdc.nedss.systemservice.ejb.nbschartsejb.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

/**
 * @author vsiram2
 *
 */
public class ChartReportMetadataDT extends AbstractVO implements RootDTInterface, Comparable<Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long chart_report_metadata_uid;
	private String chart_report_cd;
	private String chart_report_desc_txt;
	private String external_class_nm;
	private String external_method_nm;
	private String x_axis_title;
	private String y_axis_title;
	private String secured_ind_cd; 
	private Long chart_type_uid;
	private String record_status_cd;
	private Long add_user_id;
	private Timestamp add_time;
	private Long last_chg_user_id;
	private Timestamp last_chg_time;
	private String chart_report_short_desc_txt; 
	private String object_nm;  		
	private String operation_nm; 		
	private String secured_by_object_operation;
	private String defaultIndCd;
	
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
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Long getChart_report_metadata_uid() {
		return chart_report_metadata_uid;
	}

	public void setChart_report_metadata_uid(Long chart_report_metadata_uid) {
		this.chart_report_metadata_uid = chart_report_metadata_uid;
	}

	public String getChart_report_cd() {
		return chart_report_cd;
	}

	public void setChart_report_cd(String chart_report_cd) {
		this.chart_report_cd = chart_report_cd;
	}

	public String getChart_report_desc_txt() {
		return chart_report_desc_txt;
	}

	public void setChart_report_desc_txt(String chart_report_desc_txt) {
		this.chart_report_desc_txt = chart_report_desc_txt;
	}

	public String getExternal_class_nm() {
		return external_class_nm;
	}

	public void setExternal_class_nm(String external_class_nm) {
		this.external_class_nm = external_class_nm;
	}

	public String getExternal_method_nm() {
		return external_method_nm;
	}

	public void setExternal_method_nm(String external_method_nm) {
		this.external_method_nm = external_method_nm;
	}

	public String getX_axis_title() {
		return x_axis_title;
	}

	public void setX_axis_title(String x_axis_title) {
		this.x_axis_title = x_axis_title;
	}

	public String getY_axis_title() {
		return y_axis_title;
	}

	public void setY_axis_title(String y_axis_title) {
		this.y_axis_title = y_axis_title;
	}

	public String getSecured_ind_cd() {
		return secured_ind_cd;
	}

	public void setSecured_ind_cd(String secured_ind_cd) {
		this.secured_ind_cd = secured_ind_cd;
	}

	public Long getChart_type_uid() {
		return chart_type_uid;
	}

	public void setChart_type_uid(Long chart_type_uid) {
		this.chart_type_uid = chart_type_uid;
	}

	public String getRecord_status_cd() {
		return record_status_cd;
	}

	public void setRecord_status_cd(String record_status_cd) {
		this.record_status_cd = record_status_cd;
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

	public String getChart_report_short_desc_txt() {
		return chart_report_short_desc_txt;
	}

	public void setChart_report_short_desc_txt(String chart_report_short_desc_txt) {
		this.chart_report_short_desc_txt = chart_report_short_desc_txt;
	}

	public String getObject_nm() {
		return object_nm;
	}

	public void setObject_nm(String object_nm) {
		this.object_nm = object_nm;
	}

	public String getOperation_nm() {
		return operation_nm;
	}

	public void setOperation_nm(String operation_nm) {
		this.operation_nm = operation_nm;
	}

	public String getSecured_by_object_operation() {
		return secured_by_object_operation;
	}

	public void setSecured_by_object_operation(String secured_by_object_operation) {
		this.secured_by_object_operation = secured_by_object_operation;
	}

	public String getDefaultIndCd() {
		return defaultIndCd;
	}

	public void setDefaultIndCd(String defaultIndCd) {
		this.defaultIndCd = defaultIndCd;
	}
	

}