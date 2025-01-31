//Source file: C:\\BLDC_Dev_Development\\development\\source\\gov\\cdc\\nedss\\nbssecurity\\helpers\\NBSOperationLookup.java

package gov.cdc.nedss.systemservice.nbssecurity;

import java.util.TreeMap;

/**
 * Title:        Security
 * Description:  Security interface
 * Copyright:    Copyright (c) 2001
 * Company:      CSC
 * @author Jim Verrelli
 * @version 1.0
 */
public class NBSOperationLookup
{
   public static final String ANY  = "ANY";
   public static final String VIEW = "VIEW";
   public static final String VIEWWORKUP = "VIEWWORKUP";
   public static final String ASSIGNSECURITY = "ASSIGNSECURITY";
   public static final String CREATE = "CREATE";
   public static final String MERGE = "MERGE";
   public static final String DELETE = "DELETE";
   public static final String EDIT = "EDIT";
   public static final String ADD = "ADD";
   public static final String TRANSFERPERMISSIONS = "TRANSFERPERMISSIONS";
   public static final String SECURITYADMINISTRATION = "SECURITYADMINISTRATION";
   public static final String FINDINACTIVE = "FINDINACTIVE";
   public static final String LASTUPDATE = "LASTUPDATE";
   public static final String CREATEREPORTPUBLIC = "CREATEREPORTPUBLIC";
   public static final String DELETEREPORTPUBLIC = "DELETEREPORTPUBLIC";
   public static final String EDITREPORTPUBLIC = "EDITREPORTPUBLIC";
   public static final String SELECTFILTERCRITERIAPUBLIC = "SELECTFILTERCRITERIAPUBLIC";
   public static final String VIEWREPORTPUBLIC = "VIEWREPORTPUBLIC";
   public static final String CREATEREPORTPRIVATE = "CREATEREPORTPRIVATE";
   public static final String DELETEREPORTPRIVATE = "DELETEREPORTPRIVATE";
   public static final String EDITREPORTPRIVATE = "EDITREPORTPRIVATE";
   public static final String EXPORTREPORT = "EXPORTREPORT";
   public static final String SELECTFILTERCRITERIAPRIVATE = "SELECTFILTERCRITERIAPRIVATE";
   public static final String VIEWREPORTPRIVATE = "VIEWREPORTPRIVATE";
   public static final String RUNREPORT = "RUNREPORT";
   public static final String SELECTFILTERCRITERIATEMPLATE = "SELECTFILTERCRITERIATEMPLATE";
   public static final String VIEWREPORTTEMPLATE = "VIEWREPORTTEMPLATE";
   public static final String CREATENEEDSAPPROVAL = "CREATENEEDSAPPROVAL";
   public static final String ROLEADMINISTRATION = "ROLEADMINISTRATION";
   public static final String REVIEW = "REVIEW";
   public static final String FIND = "FIND";
   public static final String CREATESUMMARYNOTIFICATION = "CREATESUMMARYNOTIFICATION";
   public static final String AUTOCREATE = "AUTOCREATE";
   public static final String MAINTAINOUTBREAKNM = "MAINTAINOUTBREAKNM";
   public static final String VIEWELRACTIVITY = "VIEWELRACTIVITY";
   public static final String VIEWPHCRACTIVITY = "VIEWPHCRACTIVITY";
   public static final String MANAGE = "MANAGE";
   public static final String ASSOCIATEOBSERVATIONLABREPORTS = "ASSOCIATEOBSERVATIONLABREPORTS";
   public static final String ASSOCIATEOBSERVATIONMORBIDITYREPORTS = "ASSOCIATEOBSERVATIONMORBIDITYREPORTS";
   public static final String ASSOCIATEINTERVENTIONVACCINERECORDS = "ASSOCIATEINTERVENTIONVACCINERECORDS";
   public static final String ASSOCIATETREATMENTS = "ASSOCIATETREATMENTS";
   public static final String CHANGECONDITION = "CHANGECONDITION";
   public static final String ASSIGNSECURITYFORDATAENTRY = "ASSIGNSECURITYFORDATAENTRY";
   public static final String DELETEEXTERNAL = "DELETEEXTERNAL";
   public static final String LDFADMINISTRATION = "LDFADMINISTRATION";
   public static final String GEOCODING = "GEOCODING";
   public static final String INVESTIGATIONRVCTHIV = "INVESTIGATIONRVCTHIV";
   public static final String REPORTADMIN = "REPORTADMIN";
   public static final String SRTADMIN = "SRTADMIN";
   /**
    * Changed constant from ALERTADMIN to DECISION_SUPPORT_ADMIN
    *  
    */
   public static final String DECISION_SUPPORT_ADMIN = "ALERTADMIN";
   public static final String IMPORTEXPORTADMIN = "IMPORTEXPORTADMIN";
   public static final String MARKREVIEWED = "MARKREVIEWED";
   public static final String CREATEREPORTREPORTINGFACILITY = "CREATEREPORTREPORTINGFACILITY";
   public static final String DELETEREPORTREPORTINGFACILITY = "DELETEREPORTREPORTINGFACILITY";
   public static final String EDITREPORTREPORTINGFACILITY = "EDITREPORTREPORTINGFACILITY";
   public static final String SELECTFILTERCRITERIAREPORTINGFACILITY = "SELECTFILTERCRITERIAREPORTINGFACILITY";
   public static final String VIEWREPORTREPORTINGFACILITY = "VIEWREPORTREPORTINGFACILITY";
   public static final String CREATECASEREPORTING = "CREATECASEREPORTING";
   public static final String ASSOCIATEDOCUMENTS="ASSOCIATEDOCUMENTS";
   public static final String MESSAGE="MESSAGE";
   public static final String ADDINACTIVATE="ADDINACTIVATE";
   public static final String SUPERVISORREVIEW="SUPERVISORREVIEW";
   public static final String HIVQUESTIONS="HIVQUESTIONS";
   public static final String EPILINKADMIN="EPILINKADMIN";
   public static final String DISASSOCIATEINITIATINGEVENT = "DISASSOCIATEINITIATINGEVENT";
   public static final String MERGEINVESTIGATION = "MERGEINVESTIGATION";
   
 
   


   private static int operationCount;
   private static TreeMap<Object,Object> operationNameMap;
   private static TreeMap<Object,Object> operationIndexMap;
   private static TreeMap<Object,Object> operationLookupName;

   static
   {
    initNBSOperationLookup();
   }

   /**
    * Called by the Static Initializer.
    * @roseuid 3CEB0F42025C
    */
   private static void initNBSOperationLookup()
   {
      // Create operationIndexMap
     operationIndexMap = new TreeMap();
     operationIndexMap.put(VIEW,new Integer(0));
     operationIndexMap.put(VIEWWORKUP,new Integer(1));
     operationIndexMap.put(ASSIGNSECURITY,new Integer(2));
     operationIndexMap.put(CREATE,new Integer(3));
     operationIndexMap.put(MERGE,new Integer(4));
     operationIndexMap.put(DELETE,new Integer(5));
     operationIndexMap.put(EDIT,new Integer(6));
     operationIndexMap.put(ADD,new Integer(7));
     operationIndexMap.put(TRANSFERPERMISSIONS,new Integer(8));
     operationIndexMap.put(SECURITYADMINISTRATION,new Integer(9));
     operationIndexMap.put(FINDINACTIVE,new Integer(10));
     operationIndexMap.put(LASTUPDATE,new Integer(11));
     operationIndexMap.put(CREATEREPORTPUBLIC,new Integer(12));
     operationIndexMap.put(DELETEREPORTPUBLIC,new Integer(13));
     operationIndexMap.put(EDITREPORTPUBLIC,new Integer(14));
     operationIndexMap.put(SELECTFILTERCRITERIAPUBLIC,new Integer(15));
     operationIndexMap.put(VIEWREPORTPUBLIC,new Integer(16));
     operationIndexMap.put(CREATEREPORTPRIVATE,new Integer(17));
     operationIndexMap.put(DELETEREPORTPRIVATE,new Integer(18));
     operationIndexMap.put(EDITREPORTPRIVATE,new Integer(19));
     operationIndexMap.put(EXPORTREPORT,new Integer(20));
     operationIndexMap.put(SELECTFILTERCRITERIAPRIVATE,new Integer(21));
     operationIndexMap.put(VIEWREPORTPRIVATE,new Integer(22));
     operationIndexMap.put(RUNREPORT,new Integer(23));
     operationIndexMap.put(SELECTFILTERCRITERIATEMPLATE,new Integer(24));
     operationIndexMap.put(VIEWREPORTTEMPLATE,new Integer(25));
     operationIndexMap.put(CREATENEEDSAPPROVAL,new Integer(26));
     operationIndexMap.put(ROLEADMINISTRATION,new Integer(27));
     operationIndexMap.put(REVIEW,new Integer(28));
     operationIndexMap.put(FIND,new Integer(29));
     operationIndexMap.put(CREATESUMMARYNOTIFICATION,new Integer(30));
     operationIndexMap.put(AUTOCREATE,new Integer(31));
     operationIndexMap.put(MAINTAINOUTBREAKNM,new Integer(32));
     operationIndexMap.put(VIEWELRACTIVITY,new Integer(33));
     operationIndexMap.put(MANAGE,new Integer(34));
     operationIndexMap.put(ASSOCIATEOBSERVATIONLABREPORTS,new Integer(35));
     operationIndexMap.put(ASSOCIATEOBSERVATIONMORBIDITYREPORTS,new Integer(36));
     operationIndexMap.put(ASSOCIATEINTERVENTIONVACCINERECORDS,new Integer(37));
     operationIndexMap.put(ASSOCIATETREATMENTS,new Integer(38));
     operationIndexMap.put(ASSIGNSECURITYFORDATAENTRY,new Integer(39));
     operationIndexMap.put(DELETEEXTERNAL,new Integer(40));
     operationIndexMap.put(LDFADMINISTRATION,new Integer(41));
     operationIndexMap.put(GEOCODING,new Integer(42));
     operationIndexMap.put(INVESTIGATIONRVCTHIV,new Integer(43));
     operationIndexMap.put(REPORTADMIN,new Integer(44));
     operationIndexMap.put(SRTADMIN,new Integer(45));
     operationIndexMap.put(DECISION_SUPPORT_ADMIN,new Integer(46));
     operationIndexMap.put(MARKREVIEWED,new Integer(47));
     operationIndexMap.put(CREATEREPORTREPORTINGFACILITY,new Integer(48));
     operationIndexMap.put(DELETEREPORTREPORTINGFACILITY,new Integer(49));
     operationIndexMap.put(EDITREPORTREPORTINGFACILITY,new Integer(50));
     operationIndexMap.put(SELECTFILTERCRITERIAREPORTINGFACILITY,new Integer(51));
     operationIndexMap.put(VIEWREPORTREPORTINGFACILITY,new Integer(52));  
     operationIndexMap.put(CREATECASEREPORTING,new Integer(53)); 
     operationIndexMap.put(IMPORTEXPORTADMIN,new Integer(54)); 
     operationIndexMap.put(ASSOCIATEDOCUMENTS,new Integer(55));
     operationIndexMap.put(CHANGECONDITION,new Integer(56));
     operationIndexMap.put(VIEWPHCRACTIVITY, new Integer(57));
     operationIndexMap.put(MESSAGE,new Integer(58)); 
     operationIndexMap.put(SUPERVISORREVIEW,new Integer(59));
     operationIndexMap.put(HIVQUESTIONS, new Integer(62));
     operationIndexMap.put(EPILINKADMIN, new Integer(63));
     operationIndexMap.put(DISASSOCIATEINITIATINGEVENT, new Integer(61));
     operationIndexMap.put(MERGEINVESTIGATION, new Integer(60));
     operationIndexMap.put(ADDINACTIVATE, new Integer(64));
     
     
     // Initialize operationCount
     operationCount = operationIndexMap.size();

    // Create operationNameMap
     operationNameMap = new TreeMap();
     operationNameMap.put(VIEW,"View");
     operationNameMap.put(VIEWWORKUP,"View Workup");
     operationNameMap.put(ASSIGNSECURITY,"Assign Security");
     operationNameMap.put(CREATE,"Create");
     operationNameMap.put(MERGE,"Merge");
     operationNameMap.put(DELETE,"Delete");
     operationNameMap.put(EDIT,"Edit");
     operationNameMap.put(ADD,"Add");
     operationNameMap.put(TRANSFERPERMISSIONS,"Transfer Permissions");
     operationNameMap.put(SECURITYADMINISTRATION,"Security Administration");
     operationNameMap.put(FINDINACTIVE,"Find Inactive");
     operationNameMap.put(LASTUPDATE,"Last Update");
     operationNameMap.put(CREATEREPORTPUBLIC,"Public: Create Report/Query Design");
     operationNameMap.put(DELETEREPORTPUBLIC,"Public: Delete Report/Query Design");
     operationNameMap.put(EDITREPORTPUBLIC,"Public: Edit Report/Query Design");
     operationNameMap.put(SELECTFILTERCRITERIAPUBLIC,"Public: Select Filter Criteria");
     operationNameMap.put(VIEWREPORTPUBLIC,"Public: View Report/Query Design");
     operationNameMap.put(CREATEREPORTPRIVATE,"Private: Create Report/Query Design");
     operationNameMap.put(DELETEREPORTPRIVATE,"Private: Delete Report/Query Design");
     operationNameMap.put(EDITREPORTPRIVATE,"Private: Edit Report/Query Design");
     operationNameMap.put(EXPORTREPORT,"Export Report");
     operationNameMap.put(SELECTFILTERCRITERIAPRIVATE,"Private: Select Filter Criteria");
     operationNameMap.put(VIEWREPORTPRIVATE,"Private: View Report/Query Design");
     operationNameMap.put(RUNREPORT,"Run Report/Query Design");
     operationNameMap.put(SELECTFILTERCRITERIATEMPLATE,"Template: Select Filter Criteria");
     operationNameMap.put(VIEWREPORTTEMPLATE,"Template: View Report/Query Design");
     operationNameMap.put(CREATENEEDSAPPROVAL,"Create Needs Approval");
     operationNameMap.put(ROLEADMINISTRATION,"Role Administration");
     operationNameMap.put(REVIEW,"Review");
     operationNameMap.put(FIND,"Find");
     operationNameMap.put(CREATESUMMARYNOTIFICATION,"Create Summary Notification");
     operationNameMap.put(AUTOCREATE,"Autocreate");
     operationNameMap.put(MAINTAINOUTBREAKNM,"Maintain Outbreak NM");
     operationNameMap.put(VIEWELRACTIVITY,"View ELR Activity");
     operationNameMap.put(VIEWPHCRACTIVITY, "View PHCR Activity");
     operationNameMap.put(MANAGE,"Manage");
     operationNameMap.put(ASSOCIATEOBSERVATIONLABREPORTS,"Associate Observation Lab Reports");
     operationNameMap.put(ASSOCIATEOBSERVATIONMORBIDITYREPORTS,"Associate Observation Lab Reports");
     operationNameMap.put(ASSOCIATEINTERVENTIONVACCINERECORDS,"Associate Intervention Vaccine Records");
     operationNameMap.put(ASSOCIATETREATMENTS,"Associate Treatments");
     operationNameMap.put(CHANGECONDITION,"Change Condition");
     operationNameMap.put(ASSIGNSECURITYFORDATAENTRY,"Assign Security for Data Entry");
     operationNameMap.put(DELETEEXTERNAL,"Delete External");
     operationNameMap.put(LDFADMINISTRATION,"Locally Defined Field Administration");
     operationNameMap.put(GEOCODING,"Geocoding");
     operationNameMap.put(REPORTADMIN,"Report Administration");
     operationNameMap.put(SRTADMIN,"SRT Administration");
     operationNameMap.put(DECISION_SUPPORT_ADMIN,"ALERT Administration");
     operationNameMap.put(MARKREVIEWED,"MARK AS REVIEWED");
     operationNameMap.put(CREATEREPORTREPORTINGFACILITY,"ReportingFacility: Create Report/Query Design");
     operationNameMap.put(DELETEREPORTREPORTINGFACILITY,"ReportingFacility: Delete Report/Query Design");
     operationNameMap.put(EDITREPORTREPORTINGFACILITY,"ReportingFacility: Edit Report/Query Design");
     operationNameMap.put(SELECTFILTERCRITERIAREPORTINGFACILITY,"ReportingFacility: Select Filter Criteria");
     operationNameMap.put(VIEWREPORTREPORTINGFACILITY,"ReportingFacility: View Report/Query Design");
     operationNameMap.put(CREATECASEREPORTING,"Create Case Reporting");
     operationNameMap.put(IMPORTEXPORTADMIN,"Import Export Administration");
     operationNameMap.put(ASSOCIATEDOCUMENTS,"Associate Documents");
     
     operationNameMap.put(MESSAGE,"Message Queue");
     operationNameMap.put(SUPERVISORREVIEW,"Supervisor Review Queue");
     operationNameMap.put(HIVQUESTIONS,"Access to HIV Fields");
     

     operationNameMap.put(INVESTIGATIONRVCTHIV,"TB HIV Security");
     
     operationNameMap.put(EPILINKADMIN,"Manage Epi Link Id");
     operationNameMap.put(DISASSOCIATEINITIATINGEVENT, "Disassociate Initiating Event");
     operationNameMap.put(ADDINACTIVATE, "Add/Inactivate");

     // Create OperationLookupName
     operationLookupName = new TreeMap();
     operationLookupName.put(new Integer(0), VIEW);
     operationLookupName.put(new Integer(1), VIEWWORKUP);
     operationLookupName.put(new Integer(2), ASSIGNSECURITY);
     operationLookupName.put(new Integer(3), CREATE);
     operationLookupName.put(new Integer(4), MERGE);
     operationLookupName.put(new Integer(5), DELETE);
     operationLookupName.put(new Integer(6), EDIT);
     operationLookupName.put(new Integer(7), ADD);
     operationLookupName.put(new Integer(8), TRANSFERPERMISSIONS);
     operationLookupName.put(new Integer(9), SECURITYADMINISTRATION);
     operationLookupName.put(new Integer(10), FINDINACTIVE);
     operationLookupName.put(new Integer(11), LASTUPDATE);
     operationLookupName.put(new Integer(12), CREATEREPORTPUBLIC);
     operationLookupName.put(new Integer(13), DELETEREPORTPUBLIC);
     operationLookupName.put(new Integer(14), EDITREPORTPUBLIC);
     operationLookupName.put(new Integer(15), SELECTFILTERCRITERIAPUBLIC);
     operationLookupName.put(new Integer(16), VIEWREPORTPUBLIC);
     operationLookupName.put(new Integer(17), CREATEREPORTPRIVATE);
     operationLookupName.put(new Integer(18), DELETEREPORTPRIVATE);
     operationLookupName.put(new Integer(19), EDITREPORTPRIVATE);
     operationLookupName.put(new Integer(20), EXPORTREPORT);
     operationLookupName.put(new Integer(21), SELECTFILTERCRITERIAPRIVATE);
     operationLookupName.put(new Integer(22), VIEWREPORTPRIVATE);
     operationLookupName.put(new Integer(23), RUNREPORT);
     operationLookupName.put(new Integer(24), SELECTFILTERCRITERIATEMPLATE);
     operationLookupName.put(new Integer(25), VIEWREPORTTEMPLATE);
     operationLookupName.put(new Integer(26), CREATENEEDSAPPROVAL);
     operationLookupName.put(new Integer(27), ROLEADMINISTRATION);
     operationLookupName.put(new Integer(28), REVIEW);
     operationLookupName.put(new Integer(29), FIND);
     operationLookupName.put(new Integer(30), CREATESUMMARYNOTIFICATION);
     operationLookupName.put(new Integer(31), AUTOCREATE);
     operationLookupName.put(new Integer(32), MAINTAINOUTBREAKNM);
     operationLookupName.put(new Integer(33), VIEWELRACTIVITY);
     operationLookupName.put(new Integer(34), MANAGE);
     operationLookupName.put(new Integer(35), ASSOCIATEOBSERVATIONLABREPORTS);
     operationLookupName.put(new Integer(36), ASSOCIATEOBSERVATIONMORBIDITYREPORTS);
     operationLookupName.put(new Integer(37), ASSOCIATEINTERVENTIONVACCINERECORDS);
     operationLookupName.put(new Integer(38), ASSOCIATETREATMENTS);
     operationLookupName.put(new Integer(39), ASSIGNSECURITYFORDATAENTRY);
     operationLookupName.put(new Integer(40), DELETEEXTERNAL);
     operationLookupName.put(new Integer(41), LDFADMINISTRATION);
     operationLookupName.put(new Integer(42), GEOCODING);
     operationLookupName.put(new Integer(43), INVESTIGATIONRVCTHIV);
     operationLookupName.put(new Integer(44), REPORTADMIN);
     operationLookupName.put(new Integer(45), SRTADMIN);
     operationLookupName.put(new Integer(46), DECISION_SUPPORT_ADMIN);
     operationLookupName.put(new Integer(47), MARKREVIEWED);     
     operationLookupName.put(new Integer(48), CREATEREPORTREPORTINGFACILITY);
     operationLookupName.put(new Integer(49), DELETEREPORTREPORTINGFACILITY);
     operationLookupName.put(new Integer(50), EDITREPORTREPORTINGFACILITY);
     operationLookupName.put(new Integer(51), SELECTFILTERCRITERIAREPORTINGFACILITY);
     operationLookupName.put(new Integer(52), VIEWREPORTREPORTINGFACILITY);  
     operationLookupName.put(new Integer(53), CREATECASEREPORTING);
     operationLookupName.put(new Integer(54), IMPORTEXPORTADMIN);
     operationLookupName.put(new Integer(55), ASSOCIATEDOCUMENTS);
     operationLookupName.put(new Integer(56), CHANGECONDITION);
     operationLookupName.put(new Integer(57), VIEWPHCRACTIVITY);
     operationLookupName.put(new Integer(58), MESSAGE);
     operationLookupName.put(new Integer(59), SUPERVISORREVIEW);
     operationLookupName.put(new Integer(62), HIVQUESTIONS);
     operationLookupName.put(new Integer(63), EPILINKADMIN);
     operationLookupName.put(new Integer(61), DISASSOCIATEINITIATINGEVENT);
     operationLookupName.put(new Integer(60), MERGEINVESTIGATION);
     operationLookupName.put(new Integer(64), ADDINACTIVATE);
     
     
     

   }

   /**
    * Returns the index of the operation.
    * @param operation
    * @return int
    * @roseuid 3CEB0FD401C6
    */
   public static int getOperationIndex(String operation)
   {
     return ((Integer)operationIndexMap.get(operation.toUpperCase())).intValue();
   }

   /**
    * Returns the Name of the operation
    * @param operation
    * @return String
    * @roseuid 3CEB0FE20220
    */
   public static String getOperationName(String operation)
   {
    return (String)operationNameMap.get(operation);
   }

   /**
    * Returns the number of operations
    * @return int
    * @roseuid 3CEB0FED00A9
    */
   public static int getOperationCount()
   {
    return operationCount;
   }

   /**
    * Access method for the operationNameMap property.
    * @return   the current value of the operationNameMap property
    * @roseuid 3CEB1D5801E9
    */
   public static TreeMap<Object,Object> getOperationNameMap()
   {
      return operationNameMap;
   }

   /**
    * Access method for the operationIndexMap property.
    * @return   the current value of the operationIndexMap property
    * @roseuid 3CEB1D580293
    */
   public static TreeMap<Object,Object> getOperationIndexMap()
   {
      return operationIndexMap;
   }

   /**
    * Returns the operation lookup name for the operation.
    *
    * @param index
    * @return String
    * @roseuid 3CEB1DEE02DF
    */
   public static String getOperationLookupName(int index)
   {
	return operationLookupName.get(new Integer(index)).toString();
   }
}

