package gov.cdc.nedss.pagemanagement.wa.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

public class PageMetadataDT extends AbstractVO implements RootDTInterface {

	private static final long serialVersionUID = 1L;
	private Long waRdbMetadataUid;
	private String page_nm;
	

	private Integer order_nbr;
	private String question_identifier;
	private String question_nm;
	private String desc_txt;
	private String question_oid;
	private String question_oid_system_txt;
	private String question_label;
	private String question_type;
	private String question_tool_tip;
	private String data_location;
	private String data_type;
	private String code_set_nm;
	private String value_set_code;
	private String value_set_nm;
	private String repeats_ind_cd;
	private String ui_display_type;
	private String group_nm;
	private String participation_type;
	private String data_cd;
	private String data_use_cd;
	private String sub_group_nm;
	private String display_ind;
	private String enable_ind;
	private String required_ind;
	private String other_value_ind_cd;
	private String future_date_ind_cd;
	private String publish_ind_cd;
	private String default_value;
	private Long max_length;
	private String field_size;
	private String mask;
	private Long min_value;
	private Long max_value;
	private String unit_type_cd;
	private String unit_value;
	private String question_unit_identifier;
	private String unit_parent_identifier;
	private String block_nm;
	private Integer block_pivot_nbr;
	private String rdb_table_nm;
	private String rdb_column_nm;
	private String data_mart_column_nm;
	private String rpt_admin_column_nm;
	private String question_identifier_nnd;
	private String question_label_nnd;
	private String question_required_nnd;
	private String question_data_type_nnd;
	private String hl7_segment_field;
	private String order_group_id;
	private String admin_comment;
	private String part_type_cd;
	private String standard_question_ind_cd;
	private String standard_nnd_ind_cd;
	private String coinfection_ind_cd;
	private String repeat_group_seq_nbr;
	private Integer question_group_seq_nbr;
	private String batch_table_appear_ind_cd;
	private Integer batch_table_column_width;
	private String batch_table_header;
	private String indicator_cd;
	
	
	
	private String question_map;
	
	private Timestamp recStatusTime;
	private Timestamp addTime;
	private Long addUserId;
	private Timestamp lastChgTime;
	private Long lastChgUserId;
	private Integer versionCtrlNbr;
	private String localId;
	private String recordStatusCd;
	private Timestamp recordStatusTime;
	
	private Long nbs_ui_component_uid;
	private Long code_set_group_id;
	private Integer version_ctrl_nbr;
	private String legacy_data_location;
	private String entry_method;
	
	private String code;
	private String code_desc_txt;
	private String code_short_desc_txt;
	private String status_cd;
	private String concept_code;
	private String concept_nm;
	private String concept_preferred_nm;
	private String code_system_cd;
	private String code_system_desc_txt;
	private String concept_type_cd;
	private Timestamp effective_from_time;
	private Timestamp effective_to_time;
	private Timestamp add_time;
	
    
    
    

	/**
	 * @param objectname1
	 * @param objectname2
	 * @param voClass
	 * @return boolean
	 */
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		return true;
	}

	/**
	 * @param itDirty
	 */
	public void setItDirty(boolean itDirty) {
		this.itDirty = itDirty;
	}

	/**
	 * @return boolean
	 */
	public boolean isItDirty() {
		return this.itDirty;
	}

	/**
	 * @param itNew
	 */
	public void setItNew(boolean itNew) {
		this.itNew = itNew;
	}

	/**
	 * @return boolean
	 */
	public boolean isItNew() {
		return this.itNew;
	}

	/**
	 * @param itDelete
	 */
	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
	}

	/**
	 * @return boolean
	 */
	public boolean isItDelete() {
		return this.itDelete;
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

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalId() {
		return localId;
	}

	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return recordStatusCd;
	}

	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return recordStatusTime;
	}

	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setJurisdictionCd(String jurisdictionCd) {
		// TODO Auto-generated method stub

	}

	public void setLastChgReasonCd(String lastChgReasonCd) {
		// TODO Auto-generated method stub

	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public void setProgAreaCd(String progAreaCd) {
		// TODO Auto-generated method stub

	}

	public void setProgramJurisdictionOid(Long programJurisdictionOid) {
		// TODO Auto-generated method stub

	}

	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;

	}

	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;

	}

	public void setSharedInd(String sharedInd) {
		// TODO Auto-generated method stub

	}

	public void setStatusCd(String statusCd) {
		// TODO Auto-generated method stub

	}

	public void setStatusTime(Timestamp statusTime) {
		// TODO Auto-generated method stub

	}


	public Long getWaRdbMetadataUid() {
		return waRdbMetadataUid;
	}

	public void setWaRdbMetadataUid(Long waRdbMetadataUid) {
		this.waRdbMetadataUid = waRdbMetadataUid;
	}

	public Timestamp getRecStatusTime() {
		return recStatusTime;
	}

	public void setRecStatusTime(Timestamp recStatusTime) {
		this.recStatusTime = recStatusTime;
	}

	public Integer getVersionCtrlNbr() {
		return versionCtrlNbr;
	}

	public void setVersionCtrlNbr(Integer versionCtrlNbr) {
		this.versionCtrlNbr = versionCtrlNbr;
	}
	public String getPage_nm() {
		return page_nm;
	}

	public void setPage_nm(String page_nm) {
		this.page_nm = page_nm;
	}

	public Integer getOrder_nbr() {
		return order_nbr;
	}

	public void setOrder_nbr(Integer order_nbr) {
		this.order_nbr = order_nbr;
	}

	public String getQuestion_identifier() {
		return question_identifier;
	}

	public void setQuestion_identifier(String question_identifier) {
		this.question_identifier = question_identifier;
	}

	public String getQuestion_nm() {
		return question_nm;
	}

	public void setQuestion_nm(String question_nm) {
		this.question_nm = question_nm;
	}

	public String getDesc_txt() {
		return desc_txt;
	}

	public void setDesc_txt(String desc_txt) {
		this.desc_txt = desc_txt;
	}

	public String getQuestion_oid() {
		return question_oid;
	}

	public void setQuestion_oid(String question_oid) {
		this.question_oid = question_oid;
	}

	public String getQuestion_oid_system_txt() {
		return question_oid_system_txt;
	}

	public void setQuestion_oid_system_txt(String question_oid_system_txt) {
		this.question_oid_system_txt = question_oid_system_txt;
	}

	public String getQuestion_label() {
		return question_label;
	}

	public void setQuestion_label(String question_label) {
		this.question_label = question_label;
	}

	public String getQuestion_tool_tip() {
		return question_tool_tip;
	}

	public void setQuestion_tool_tip(String question_tool_tip) {
		this.question_tool_tip = question_tool_tip;
	}

	public String getData_location() {
		return data_location;
	}

	public void setData_location(String data_location) {
		this.data_location = data_location;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getCode_set_nm() {
		return code_set_nm;
	}

	public void setCode_set_nm(String code_set_nm) {
		this.code_set_nm = code_set_nm;
	}

	public String getUi_display_type() {
		return ui_display_type;
	}

	public void setUi_display_type(String ui_display_type) {
		this.ui_display_type = ui_display_type;
	}

	public String getGroup_nm() {
		return group_nm;
	}

	public void setGroup_nm(String group_nm) {
		this.group_nm = group_nm;
	}

	public String getParticipation_type() {
		return participation_type;
	}

	public void setParticipation_type(String participation_type) {
		this.participation_type = participation_type;
	}

	public String getData_cd() {
		return data_cd;
	}

	public void setData_cd(String data_cd) {
		this.data_cd = data_cd;
	}

	public String getData_use_cd() {
		return data_use_cd;
	}

	public void setData_use_cd(String data_use_cd) {
		this.data_use_cd = data_use_cd;
	}

	public String getSub_group_nm() {
		return sub_group_nm;
	}

	public void setSub_group_nm(String sub_group_nm) {
		this.sub_group_nm = sub_group_nm;
	}

	public String getDisplay_ind() {
		return display_ind;
	}

	public void setDisplay_ind(String display_ind) {
		this.display_ind = display_ind;
	}

	public String getEnable_ind() {
		return enable_ind;
	}

	public void setEnable_ind(String enable_ind) {
		this.enable_ind = enable_ind;
	}

	public String getRequired_ind() {
		return required_ind;
	}

	public void setRequired_ind(String required_ind) {
		this.required_ind = required_ind;
	}

	public String getOther_value_ind_cd() {
		return other_value_ind_cd;
	}

	public void setOther_value_ind_cd(String other_value_ind_cd) {
		this.other_value_ind_cd = other_value_ind_cd;
	}

	public String getFuture_date_ind_cd() {
		return future_date_ind_cd;
	}

	public void setFuture_date_ind_cd(String future_date_ind_cd) {
		this.future_date_ind_cd = future_date_ind_cd;
	}

	public String getPublish_ind_cd() {
		return publish_ind_cd;
	}

	public void setPublish_ind_cd(String publish_ind_cd) {
		this.publish_ind_cd = publish_ind_cd;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public Long getMax_length() {
		return max_length;
	}

	public void setMax_length(Long max_length) {
		this.max_length = max_length;
	}

	public String getField_size() {
		return field_size;
	}

	public void setField_size(String field_size) {
		this.field_size = field_size;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public Long getMin_value() {
		return min_value;
	}

	public void setMin_value(Long min_value) {
		this.min_value = min_value;
	}

	public Long getMax_value() {
		return max_value;
	}

	public void setMax_value(Long max_value) {
		this.max_value = max_value;
	}

	public String getUnit_type_cd() {
		return unit_type_cd;
	}

	public void setUnit_type_cd(String unit_type_cd) {
		this.unit_type_cd = unit_type_cd;
	}

	public String getUnit_value() {
		return unit_value;
	}

	public void setUnit_value(String unit_value) {
		this.unit_value = unit_value;
	}

	public String getQuestion_unit_identifier() {
		return question_unit_identifier;
	}

	public void setQuestion_unit_identifier(String question_unit_identifier) {
		this.question_unit_identifier = question_unit_identifier;
	}

	public String getUnit_parent_identifier() {
		return unit_parent_identifier;
	}

	public void setUnit_parent_identifier(String unit_parent_identifier) {
		this.unit_parent_identifier = unit_parent_identifier;
	}

	public String getBlock_nm() {
		return block_nm;
	}

	public void setBlock_nm(String block_nm) {
		this.block_nm = block_nm;
	}

	public Integer getBlock_pivot_nbr() {
		return block_pivot_nbr;
	}

	public void setBlock_pivot_nbr(Integer block_pivot_nbr) {
		this.block_pivot_nbr = block_pivot_nbr;
	}

	public String getRdb_table_nm() {
		return rdb_table_nm;
	}

	public void setRdb_table_nm(String rdb_table_nm) {
		this.rdb_table_nm = rdb_table_nm;
	}

	public String getRdb_column_nm() {
		return rdb_column_nm;
	}

	public void setRdb_column_nm(String rdb_column_nm) {
		this.rdb_column_nm = rdb_column_nm;
	}

	public String getData_mart_column_nm() {
		return data_mart_column_nm;
	}

	public void setData_mart_column_nm(String data_mart_column_nm) {
		this.data_mart_column_nm = data_mart_column_nm;
	}

	public String getRpt_admin_column_nm() {
		return rpt_admin_column_nm;
	}

	public void setRpt_admin_column_nm(String rpt_admin_column_nm) {
		this.rpt_admin_column_nm = rpt_admin_column_nm;
	}

	public String getQuestion_identifier_nnd() {
		return question_identifier_nnd;
	}

	public void setQuestion_identifier_nnd(String question_identifier_nnd) {
		this.question_identifier_nnd = question_identifier_nnd;
	}

	public String getQuestion_label_nnd() {
		return question_label_nnd;
	}

	public void setQuestion_label_nnd(String question_label_nnd) {
		this.question_label_nnd = question_label_nnd;
	}

	public String getQuestion_required_nnd() {
		return question_required_nnd;
	}

	public void setQuestion_required_nnd(String question_required_nnd) {
		this.question_required_nnd = question_required_nnd;
	}

	public String getQuestion_data_type_nnd() {
		return question_data_type_nnd;
	}

	public void setQuestion_data_type_nnd(String question_data_type_nnd) {
		this.question_data_type_nnd = question_data_type_nnd;
	}

	public String getHl7_segment_field() {
		return hl7_segment_field;
	}

	public void setHl7_segment_field(String hl7_segment_field) {
		this.hl7_segment_field = hl7_segment_field;
	}

	public String getOrder_group_id() {
		return order_group_id;
	}

	public void setOrder_group_id(String order_group_id) {
		this.order_group_id = order_group_id;
	}

	public String getAdmin_comment() {
		return admin_comment;
	}

	public void setAdmin_comment(String admin_comment) {
		this.admin_comment = admin_comment;
	}

	public String getQuestion_type() {
		return question_type;
	}

	public void setQuestion_type(String question_type) {
		this.question_type = question_type;
	}

	public String getValue_set_code() {
		return value_set_code;
	}

	public void setValue_set_code(String value_set_code) {
		this.value_set_code = value_set_code;
	}

	public String getValue_set_nm() {
		return value_set_nm;
	}

	public void setValue_set_nm(String value_set_nm) {
		this.value_set_nm = value_set_nm;
	}

	public String getRepeats_ind_cd() {
		return repeats_ind_cd;
	}

	public void setRepeats_ind_cd(String repeats_ind_cd) {
		this.repeats_ind_cd = repeats_ind_cd;
	}

	public String getPart_type_cd() {
		return part_type_cd;
	}

	public void setPart_type_cd(String part_type_cd) {
		this.part_type_cd = part_type_cd;
	}

	public String getStandard_question_ind_cd() {
		return standard_question_ind_cd;
	}

	public void setStandard_question_ind_cd(String standard_question_ind_cd) {
		this.standard_question_ind_cd = standard_question_ind_cd;
	}

	public String getStandard_nnd_ind_cd() {
		return standard_nnd_ind_cd;
	}

	public void setStandard_nnd_ind_cd(String standard_nnd_ind_cd) {
		this.standard_nnd_ind_cd = standard_nnd_ind_cd;
	}

	public String getCoinfection_ind_cd() {
		return coinfection_ind_cd;
	}

	public void setCoinfection_ind_cd(String coinfection_ind_cd) {
		this.coinfection_ind_cd = coinfection_ind_cd;
	}

	public String getRepeat_group_seq_nbr() {
		return repeat_group_seq_nbr;
	}

	public void setRepeat_group_seq_nbr(String repeat_group_seq_nbr) {
		this.repeat_group_seq_nbr = repeat_group_seq_nbr;
	}

	public Integer getQuestion_group_seq_nbr() {
		return question_group_seq_nbr;
	}

	public void setQuestion_group_seq_nbr(Integer question_group_seq_nbr) {
		this.question_group_seq_nbr = question_group_seq_nbr;
	}

	public String getBatch_table_appear_ind_cd() {
		return batch_table_appear_ind_cd;
	}

	public void setBatch_table_appear_ind_cd(String batch_table_appear_ind_cd) {
		this.batch_table_appear_ind_cd = batch_table_appear_ind_cd;
	}

	public Integer getBatch_table_column_width() {
		return batch_table_column_width;
	}

	public void setBatch_table_column_width(Integer batch_table_column_width) {
		this.batch_table_column_width = batch_table_column_width;
	}

	public String getBatch_table_header() {
		return batch_table_header;
	}

	public void setBatch_table_header(String batch_table_header) {
		this.batch_table_header = batch_table_header;
	}

	public String getIndicator_cd() {
		return indicator_cd;
	}

	public void setIndicator_cd(String indicator_cd) {
		this.indicator_cd = indicator_cd;
	}

	public String getQuestion_map() {
		return question_map;
	}

	public void setQuestion_map(String question_map) {
		this.question_map = question_map;
	}

	public Long getNbs_ui_component_uid() {
		return nbs_ui_component_uid;
	}

	public void setNbs_ui_component_uid(Long nbs_ui_component_uid) {
		this.nbs_ui_component_uid = nbs_ui_component_uid;
	}

	public Long getCode_set_group_id() {
		return code_set_group_id;
	}

	public void setCode_set_group_id(Long code_set_group_id) {
		this.code_set_group_id = code_set_group_id;
	}

	public Integer getVersion_ctrl_nbr() {
		return version_ctrl_nbr;
	}

	public void setVersion_ctrl_nbr(Integer version_ctrl_nbr) {
		this.version_ctrl_nbr = version_ctrl_nbr;
	}

	public String getLegacy_data_location() {
		return legacy_data_location;
	}

	public void setLegacy_data_location(String legacy_data_location) {
		this.legacy_data_location = legacy_data_location;
	}
	public String getEntry_method() {
		return entry_method;
	}

	public void setEntry_method(String entry_method) {
		this.entry_method = entry_method;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode_desc_txt() {
		return code_desc_txt;
	}

	public void setCode_desc_txt(String code_desc_txt) {
		this.code_desc_txt = code_desc_txt;
	}

	public String getCode_short_desc_txt() {
		return code_short_desc_txt;
	}

	public void setCode_short_desc_txt(String code_short_desc_txt) {
		this.code_short_desc_txt = code_short_desc_txt;
	}

	public String getStatus_cd() {
		return status_cd;
	}

	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}

	public String getConcept_code() {
		return concept_code;
	}

	public void setConcept_code(String concept_code) {
		this.concept_code = concept_code;
	}

	public String getConcept_nm() {
		return concept_nm;
	}

	public void setConcept_nm(String concept_nm) {
		this.concept_nm = concept_nm;
	}

	public String getConcept_preferred_nm() {
		return concept_preferred_nm;
	}

	public void setConcept_preferred_nm(String concept_preferred_nm) {
		this.concept_preferred_nm = concept_preferred_nm;
	}

	public String getCode_system_cd() {
		return code_system_cd;
	}

	public void setCode_system_cd(String code_system_cd) {
		this.code_system_cd = code_system_cd;
	}

	public String getCode_system_desc_txt() {
		return code_system_desc_txt;
	}

	public void setCode_system_desc_txt(String code_system_desc_txt) {
		this.code_system_desc_txt = code_system_desc_txt;
	}

	public String getConcept_type_cd() {
		return concept_type_cd;
	}

	public void setConcept_type_cd(String concept_type_cd) {
		this.concept_type_cd = concept_type_cd;
	}

	public Timestamp getEffective_from_time() {
		return effective_from_time;
	}

	public void setEffective_from_time(Timestamp effective_from_time) {
		this.effective_from_time = effective_from_time;
	}

	public Timestamp getEffective_to_time() {
		return effective_to_time;
	}

	public void setEffective_to_time(Timestamp effective_to_time) {
		this.effective_to_time = effective_to_time;
	}

	public Timestamp getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}
}
