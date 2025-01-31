package gov.cdc.nedss.report.javaRepot.util;

/**
 * Title:PA1Const
 * Description: STD/HIV Program Activity Report Constants
 * Copyright:    Copyright (c) 2015
 * Company: SRA International
 * @author: Gregory Tucker
 * @version 1.0
 */

public class PA1Const {

    public static final String STD_REPORT_TYPE = "STD"; 
    public static final String HIV_REPORT_TYPE = "HIV";
    public static final String ZERO_COUNT = "0";
    public static final String ZERO_PERCENT = "0.0%";
    public static final String ZERO_INDEX = "0.0";
    
//Misc Data Constants
    public static final String INITIAL_ORIGINAL_INTERVIEW = "Initial/Original";
    public static final String RE_INTERVIEW = "Re-Interview";
    public static final String YES = "Yes";
    public static final String FIELD_FOLLOWUP = "Field Follow-up";
    public static final String RECORD_SEARCH_CLOSURE = "Record Search Closure";
//Investigator or Summary
    public static final String WORKER_LABEL = "Worker:";
    public static final String WORKER_SUMMARY = "Summary";
//Case Assignments and Outcomes Section - Col1
    public static final String CASE_ASSIGNMENTS_AND_OUTCOMES_SECTION_NAME = "CASE ASSIGNMENTS & OUTCOMES";
    public static final String NUM_CASES_ASSIGNED_LABEL = "Num. Cases Assigned:";
    public static final String CASES_CLOSED_LABEL = "Cases Closed:";
    public static final String CASES_IXD_LABEL= "Cases IX'd";
    public static final String CASES_IXD_WI_3D_LABEL = "IX'd W/In 3 days:";
    public static final String CASES_IXD_WI_5D_LABEL = "IX'd W/In 5 days:";
    public static final String CASES_IXD_WI_7D_LABEL = "IX'd W/In 7 days:";
    public static final String CASES_IXD_WI_14D_LABEL = "IX'd W/In 14 days:";
    public static final String CASES_REINTERVIEWED_LABEL = "Cases ReInterviewed:";
    

//Case Assignments and Outcomes Section - Col2
    public static final String HIV_PREV_POS_LABEL = "HIV Prev. Positive:";
    public static final String HIV_PRETEST_COUNSEL_LABEL = "HIV PreTest Counsel:";
    public static final String HIV_TESTED_LABEL ="HIV Tested:";
    public static final String HIV_NEW_POSITIVE_LABEL = "HIV New Positive:";
    public static final String HIV_POST_TEST_COUNSEL_LABEL = "HIV Post Test Counsel:";
//STD Only..
    public static final String DISEASE_INTERVENTION_INDEX_LABEL = "Disease Intervention Index:";
    public static final String TREATMENT_INDEX_LABEL = "Treatment Index:";
    public static final String CASES_WITH_SOURCE_IDENTIFIED_LABEL = "Cases w/ Source Identified:";

//Hiv Only..
    public static final String PARTNER_NOTIFICATION_INDEX_LABEL = "Partner Notification Index:";
    public static final String TESTING_INDEX_LABEL = "Texting Index:";

//Partners and Clusters Initiated - Col1
    public static final String PARTNERS_AND_CLUSTERS_INITIATED_SECTION_NAME = "PARTNERS AND CLUSTERS INITIATED";
    public static final String TOTAL_PERIOD_PARTNERS_LABEL = "Total Period Partners:";
    public static final String TOTAL_PARTNERS_INITIATED_LABEL ="Total Partners Initiated:";
    public static final String PARTNERS_INITIATED_FROM_OI_LABEL = "From OI:";
    public static final String PARTNERS_INITIATED_FROM_RI_LABEL = "From RI:";
    public static final String CONTACT_INDEX_LABEL = "Contact Index:";
    public static final String CASES_WITH_NO_PARTNER_LABEL = "Cases w/ No Partners:";
    public static final String TOTAL_CLUSTER_INITIATED_LABEL = "Total Clusters Initiated:";
    public static final String CLUSTER_INDEX_LABEL = "Cluster Index:";
    public static final String CASES_WITH_NO_CLUSTERS_LABEL = "Cases w/ No Clusters:";

// STD Dispositions - Partners and Clusters - Col1
    public static final String DISPOSITIONS_PARTNERS_AND_CLUSTERS_SECTION_NAME = "DISPOSITIONS - PARTNERS AND CLUSTERS";
    public static final String NEW_PARTNERS_EXAMINED_LABEL = "New Partners Examined:";
    public static final String NEW_PARTNERS_PREVENTATIVE_RX_LABEL = "Preventative Rx:";
    public static final String NEW_PARTNERS_REFUSED_PREVENTATIVE_RX_LABEL = "Refused Preventative Rx:";
    public static final String NEW_PARTNERS_INFECTED_RX_LABEL = "Infected Rx'd:";
    public static final String NEW_PARTNERS_INFECTED_NO_RX_LABEL = "Infected No Rx:";
    public static final String NEW_PARTNERS_NOT_INFECTED_LABEL = "Not Infected:";
    public static final String NEW_PARTNERS_NO_EXAM_LABEL = "New Partners No Exam:";
    public static final String NEW_PARTNERS_INSUFFICIENT_INFO_LABEL = "Insufficient Info:";
    public static final String NEW_PARTNERS_UNABLE_TO_LOCATE_LABEL = "Unable To Locate:";
    public static final String NEW_PARTNERS_REFUSED_EXAM_LABEL = "Refused Exam:";
    public static final String NEW_PARTNERS_OOJ_LABEL = "OOJ:";
    public static final String NEW_PARTNERS_OTHER_LABEL = "Other:";
    public static final String NEW_PARTNERS_PREVIOUS_RX_LABEL = "Previous Rx:";
    public static final String NEW_PARTNERS_STD_OPEN_LABEL = "Open:";

//STD Dispositions - Partners and Clusters - Col2

    public static final String NEW_CLUSTERS_EXAMINED_LABEL = "New CLusters Examined:";
    public static final String NEW_CLUSTERS_PREVENTATIVE_RX_LABEL = "Preventative Rx:";
    public static final String NEW_CLUSTERS_REFUSED_PREVENTATIVE_RX_LABEL = " Refused Prev. Rx:";
    public static final String NEW_CLUSTERS_INFECTED_RX_LABEL = "Infected Rx'd:";
    public static final String NEW_CLUSTERS_INFECTED_NO_RX_LABEL = "Infected No Rx:";
    public static final String NEW_CLUSTERS_NOT_INFECTED_LABEL = "Not Infected:";
    public static final String NEW_CLUSTERS_NO_EXAM_LABEL = "New Clusters No Exam:";
    public static final String NEW_CLUSTERS_INSUFFICIENT_INFO_LABEL = "Insufficient Info:";
    public static final String NEW_CLUSTERS_UNABLE_TO_LOCATE_LABEL = "Unable to Locate:";
    public static final String NEW_CLUSTERS_REFUSED_EXAM_LABEL = "Refused Exam:";
    public static final String NEW_CLUSTERS_OOJ_LABEL = "OOJ:";
    public static final String NEW_CLUSTERS_OTHER_LABEL = "Other:";
    public static final String NEW_CLUSTERS_PREVIOUS_RX_LABEL = "Previous Rx:";
    public static final String NEW_CLUSTERS_STD_OPEN_LABEL ="Open:";


//HIV Dispositions - Partners and Clusters - Col1
    public static final String NEW_PARTNERS_NOTIFIED_LABEL = "New Partners Notified:";
    public static final String NEW_PARTNERS_PREV_NEG_NEW_POS_LABEL = "Prev Neg, New Pos:";
    public static final String NEW_PARTNERS_PREV_NEG_STILL_NEG_LABEL = "Prev Neg, Still Neg:";
    public static final String NEW_PARTNERS_PREV_NEG_NO_TEST_LABEL = "Prev. Neg, No Test:";
//no
    public static final String NEW_PARTNERS_NO_PREV_NEW_POS_LABEL = "No Prev. Test, New Pos:";
    public static final String NEW_PARTNERS_NO_PREV_NEW_NEG_LABEL = "No Prev. Test, New Neg:";
    public static final String NEW_PARTNERS_NO_PREV_NO_TEST_LABEL = "No Prev. Test, No Test:";
    public static final String NEW_PARTNERS_NOT_NOTIFIED_LABEL = "New Partners Not Notified:";
    public static final String NEW_PARTNERS_NOT_NOTIFIED_INSUFFICIENT_INFO_LABEL = "Insufficient Info:";
    public static final String NEW_PARTNERS_NOT_NOTIFIED_UNABLE_TO_LOCATE_LABEL = "Unable to Locate:";
    public static final String NEW_PARTNERS_NOT_NOTIFIED_REFUSED_EXAM_LABEL = "Refused Exam:";
    public static final String NEW_PARTNERS_NOT_NOTIFIED_OOJ_LABEL = "OOJ:";
    public static final String NEW_PARTNERS_NOT_NOTIFIED_OTHER_LABEL = "Other:";
    public static final String NEW_PARTNERS_PREV_POS_LABEL = "Previous Pos:";
    public static final String NEW_PARTNERS_HIV_OPEN_LABEL = "Open:";

//HIV Dispositions - Partners and Clusters - Col2

    public static final String NEW_CLUSTERS_NOTIFIED_LABEL = "New Clusters Notified:"; 
    public static final String NEW_CLUSTERS_PREV_NEG_NEW_POS_LABEL = "Prev. Neg, New Pos:";
    public static final String NEW_CLUSTERS_PREV_NEG_STILL_NEG_LABEL = "Prev. Neg, Still Neg:";
    public static final String NEW_CLUSTERS_PREV_NEG_NO_TEST_LABEL = "Prev. Neg, No Test:";

//no
    public static final String NEW_CLUSTERS_NO_PREV_NEW_POS_LABEL = "No Prev. Test, New Pos:";
    public static final String NEW_CLUSTERS_NO_PREV_NEW_NEG_LABEL = "No Prev. Test, New Neg:";
    public static final String NEW_CLUSTERS_NO_PREV_NO_TEST_LABEL = "No Prev. Test, No Test:";
    public static final String NEW_CLUSTERS_NOT_NOTIFIED_LABEL = "New Clusters Not Notified:";
    public static final String NEW_CLUSTERS_NOT_NOTIFIED_INSUFFICIENT_INFO_LABEL = "Insufficient Info:";
    public static final String NEW_CLUSTERS_NOT_NOTIFIED_UNABLE_TO_LOCATE_LABEL = "Unable to Locate:";
    public static final String NEW_CLUSTERS_NOT_NOTIFIED_REFUSED_EXAM_LABEL = "Refused Exam:";
    public static final String NEW_CLUSTERS_NOT_NOTIFIED_OOJ_LABEL = "OOJ:";
    public static final String NEW_CLUSTERS_NOT_NOTIFIED_OTHER_LABEL = "Other:";
    public static final String NEW_CLUSTERS_HIV_PREV_POS_LABEL = "Previous Pos:";
    public static final String NEW_CLUSTERS_HIV_OPEN_LABEL = "Open:";


//STD Speed of Exam - Partners and Clusters Col1
    public static final String SPEED_OF_EXAM_STD_SECTION_NAME = "SPEED OF EXAM - PARTNERS AND CLUSTERS";
    public static final String SE_NEW_PARTNERS_EXAMINED_LABEL = "New Partners Examined:";
    public static final String SE_NEW_PARTNERS_EXAMINED_WI3D_LABEL = "Within 3 Days:";
    public static final String SE_NEW_PARTNERS_EXAMINED_WI5D_LABEL = "Within 5 Days:";
    public static final String SE_NEW_PARTNERS_EXAMINED_WI7D_LABEL   = "Within 7 Days:";
    public static final String SE_NEW_PARTNERS_EXAMINED_WI14D_LABEL = "Within 14 Days:";


//STD Speed of Exam - Partners and Clusters Col2
    public static final String SE_NEW_CLUSTERS_EXAMINED_LABEL = "New Clusters Examined:";
    public static final String SE_NEW_CLUSTERS_EXAMINED_WI3D_LABEL =  "Within 3 Days:";
    public static final String SE_NEW_CLUSTERS_EXAMINED_WI5D_LABEL  = "Within 5 Days:";
    public static final String SE_NEW_CLUSTERS_EXAMINED_WI7D_LABEL =  "Within 7 Days:";
    public static final String SE_NEW_CLUSTERS_EXAMINED_WI14D_LABEL = "Within 14 Days:";


//HIV Speed of Exam - Partners and Clusters Col1
    public static final String SE_NEW_PARTNERS_NOTIFIED_LABEL = "New Partners Notified:";
    public static final String SE_NEW_PARTNERS_NOTIFIED_WI3D_LABEL =  "Within 3 Days:";
    public static final String SE_NEW_PARTNERS_NOTIFIED_WI5D_LABEL  = "Within 5 Days:";
    public static final String SE_NEW_PARTNERS_NOTIFIED_WI7D_LABEL =  "Within 7 Days:";
    public static final String SE_NEW_PARTNERS_NOTIFIED_WI14D_LABEL = "Within 14 Days:";


//HIVSpeed of Exam - Partners and Clusters Col2
    public static final String SE_NEW_CLUSTERS_NOTIFIED_LABEL = "New Clusters Notified:";
    public static final String SE_NEW_CLUSTERS_NOTIFIED_WI3D_LABEL  = "Within 3 Days:";
    public static final String SE_NEW_CLUSTERS_NOTIFIED_WI5D_LABEL  = "Within 5 Days:";
    public static final String SE_NEW_CLUSTERS_NOTIFIED_WI7D_LABEL  = "Within 7 Days:";
    public static final String SE_NEW_CLUSTERS_NOTIFIED_WI14D_LABEL = "Within 14 Days:";

	
}
