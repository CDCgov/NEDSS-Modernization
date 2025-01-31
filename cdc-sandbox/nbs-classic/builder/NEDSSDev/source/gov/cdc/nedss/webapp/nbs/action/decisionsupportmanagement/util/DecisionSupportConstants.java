package gov.cdc.nedss.webapp.nbs.action.decisionsupportmanagement.util;

public class DecisionSupportConstants {
	
	//Basic Criteria constants
	public static final String ALGORITHM_NM = "AlgorithmName";
	public static final String EVENT_TYPE = "Event";
	public static final String CONDITIONS = "ApplyToConditions";
	public static final String PUBLISHED_CONDITION = "PublishedCondition";
	public static final String FREQUENCY = "Frequency";
	public static final String APPLY_TO = "AppliesToEntryMethods";
	public static final String SENDING_SYS = "ApplyToSendingSystems";
	//public static final String REPORTING_FACILITY = "REPORTING_FACILITY";
	public static final String ADMINISTRATIVE_COMMENTS = "Comment";
	public static final String ACTION = "Action";
	public static final String ON_FAILURE_TO_CREATE_INV = "OnFailureToCreateInvestigation";
	public static final String ON_FAILURE_TO_MARK_REVIEWED = "OnFailureToMarkAsReviewed";
	public static final String NO_ACTION_REASON = "NoActionReason";
	public static final String ADDITIONAL_COMMENTS = "AdditionalComment";
	public static final String NOTES = "Notes";
	public static final String UPDATE_ACTION = "UpdateAction";
	public static final String AND_OR_LOGIC = "AndOrLogic";
	public static final String AND_AND_OR_LOGIC = "AND";
	public static final String OR_AND_OR_LOGIC = "OR";
	public static final String USE_EVENT_DATE_LOGIC = "UseEventDateLogic";
	public static final String USE_INV_CRITERIA_LOGIC = "UseInvCriteriaLogic";
	public static final String USE_EVENT_DATE_LOGIC_NO = "N";
	public static final String USE_EVENT_DATE_LOGIC_YES = "Y";
	public static final String NBS_EVENT_DATE_SELECTED = "NbsEventDateSelected";
	public static final String TIMEFRAME_OPERATOR_SELECTED = "TimeFrameOperatorSelected";
	public static final String TIMEFRAME_DAYS = "TimeframeDays";
	public static final String CURRENT_SELECT_DATE_LOGIC="CurrentSelectDateLogic";
	public static final String CURRENT_CURRENT_SELECT_DATE_LOGIC="CURRENT";
	public static final String SELECT_CURRENT_SELECT_DATE_LOGIC="SELECT";
	
	//public static final String CREATE_ALERT = "CREATE_ALERT";
	public static final String BEHAVIOR = "DefaultBehavior";
	public static final String VALUE = "DefaultValue";
	public static final String ENTITY_VALUE = "DefaultEntityValue";
	public static final String ENTITY_CLASS = "DefaultEntityClass";
	public static final String PARTICIPATION_TYPE = "DefaultParticipationType";
	public static final String PARTICIPATION_SEARCH_RESULT = "DefaultParticipationSearchResult";
	public static final String QUESTION = "DefaultQuestion";
	public static final String ON_FAILURE_TO_CREATE_NOTIFICATION = "OnFailureToCreateNND";
	public static final String QUEUE_FOR_APPROVAL = "QueueForApproval";
	public static final String NOTIFICATION_COMMENTS = "NNDComment";
	public static final String INVESTIGATION_TYPE_RELATED_PAGE = "InvestigationType";
	//public static final String PUBLISHED_CONDITION_DROPDOWN = "ApplyToConditions";
	public static final String CRITERIA_QUESTION = "CriteriaQuestion";
	public static final String CRITERIA_LOGIC = "CriteriaLogic";
	public static final String CRITERIA_VALUE = "CriteriaValue";
	public static final String CRITERIA_UID = "CriteriaUid";
	
	public static final String ADV_INV_ENTITY_VALUE = "AdvInvDefaultEntityValue";
	public static final String ADV_INV_ENTITY_CLASS = "AdvInvDefaultEntityClass";
	public static final String ADV_INV_CRITERIA_QUESTION = "AdvInvCriteriaQuestion";
	public static final String ADV_INV_CRITERIA_LOGIC = "AdvInvCriteriaLogic";
	public static final String ADV_INV_CRITERIA_VALUE = "AdvInvCriteriaValue";
	public static final String ADV_INV_CRITERIA_UID = "AdvInvCriteriaUid";
	
	//Decision Support Page Titles
	public static final String ADD_ALGORITHM = "Manage Workflow Decision Support: Add Algorithm";
	public static final String EDIT_ALGORITHM = "Manage Workflow Decision Support: Edit Algorithm";
	public static final String VIEW_ALGORITHM = "Manage Workflow Decision Support: View Algorithm";
	
	//Default values for disabled fields
	public static final String FREQUENCY_VALUE = "1";
	public static final String APPLYTO_VALUE = "1";
	public static final String UPDATEACTION_VALUE = "3";
	public static final String CREATE_INVESTIGATION_VALUE = "1";
	public static final String CREATE_INVESTIGATION_WITH_NND_VALUE = "2";
	public static final String ON_FAILURE_TO_CREATE_NOTIFICATION_VALUE = "3";
	public static final String RESULTEDTEST_CODE = "RESULTEDTESTCODE";
	public static final String RESULTEDTEST_NAME = "RESULTEDTESTNAME";
	public static final String CODED_RESULT = "CODEDRESULT";
	public static final String CODED_RESULT_TXT = "CODEDRESULTTXT";
	public static final String NUMERIC_RESULT = "NUMERICRESULT";
	public static final String NUMERIC_RESULT_HIGH = "NUMERICRESULTHIGH";
	public static final String NUMERIC_RESULT_TYPE = "NUMERICRESULTTYPE";
	public static final String TEXT_RESULT_CRITERIA = "TEXTRESULTCRITERIA";
	public static final String TEXT_RESULT_CRITERIA_TXT = "TEXTRESULTCRITERIATXT";
	public static final String NUMERIC_RESULT_CRITERIA = "NUMERICRESULTCRITERIA";
	public static final String NUMERIC_RESULT_CRITERIA_TXT = "NUMERICRESULTCRITERIATXT";
	public static final String NUMERIC_RESULT_TYPE_TXT = "NUMERICRESULTTYPETXT";
	public static final String TEXT_RESULT = "TEXTRESULT";
	public static final String RESULT_NAME = "RESULTNAME";
	public static final String MARK_AS_REVIEWED = "3";
	public static final String NBS_EVENT_DATE_SELECTED_VALUE = "SCD";
	
	public static final String NUMERIC_RESULT_SEPERATOR = "-";
	public static final String CORE_INV_FORM = "CORE_INV_FORM";
	
}
