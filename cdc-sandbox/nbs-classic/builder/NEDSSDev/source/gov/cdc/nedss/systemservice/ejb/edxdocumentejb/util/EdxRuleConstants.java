package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.util;

public interface EdxRuleConstants {

	public static final String EDX_RULE_NAME = "EDX_RULE_NAME";
	public static final String EDX_RULE_EVENT = "EDX_RULE_EVENT";
	public static final String EDX_RULE_FREQUENCY= "EDX_RULE_FREQUENCY";
	public static final String EDX_RULE_APPLIED_TO_ENTRY_METHODS = "EDX_RULE_APPLIED_TO_ENTRY_METHODS";
	public static final String EDX_RULE_APPLIED_TO_CONDITIONS= "EDX_RULE_APPLIED_TO_CONDITIONS";
	public static final String EDX_RULE_APPLIED_TO_SENDING_FACILITIES= "EDX_RULE_APPLIED_TO_SENDING_FACILITIES";
	public static final String EDX_RULE_ADVANCE_CRITERIA= "EDX_RULE_ADVANCE_CRITERIA";
	public static final String EDX_RULE_CONDITION_LIST= "EDX_RULE_CONDITION_LIST";
	
	
	public static final String EDX_RULE_ACTION= "EDX_RULE_ACTION";
	public static final String CREATE_INV="CREATE_INV";
	public static final String CREATE_INV_AND_NND="CREATE_INV_AND_NND";

	/**
	 * Notification rollback codes
	 * Should match values in table nbs_srte..code_value_general, key: NBS_NOT_FAILURE_RESPONSE
	 */
	public static final String PURGE_INV_RETAIN_EV		=	"2";
	public static final String RETAIN_INV_AND_EVENT		=	"3";
	public static final String ROLL_BACK_ALL			=	"1";
	
	/*
	 * Investigation rollback codes
	 * Should match values in table nbs_srte..code_value_general, key: NBS_FAILURE_RESPONSE
	 */
	public static final String RETAIN_EVENT				=	"2";
	public static final String INV_ROLL_BACK_ALL		=	"1";
	
	/**
	 * NBS_EVENT_ACTION codes
	 */
	public static final String CREATE_INVESTIGATION=	"1";
	public static final String CREATE_INVESTIGATION_AND_NND=	"2";
	public static final String MARK_AS_REVIEWED=	"3";
	
	
	public static final String EDX_FAIL_INV_ON_REQ_FIELDS=	"EDX_FAIL_INV_ON_REQ_FIELDS";
	
}
