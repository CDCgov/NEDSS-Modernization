package gov.cdc.nbs.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static NbsUserDetails getUserDetails() {
        return (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static class Operations {
        private Operations() {
        }

        public static final String ADD = "ADD";
        public static final String ANY = "ANY";
        public static final String VIEW = "VIEW";
        public static final String EDIT = "EDIT";
        public static final String FIND = "FIND";
        public static final String MERGE = "MERGE";
        public static final String CREATE = "CREATE";
        public static final String DELETE = "DELETE";
        public static final String REVIEW = "REVIEW";
        public static final String MANAGE = "MANAGE";
        public static final String MESSAGE = "MESSAGE";
        public static final String SRTADMIN = "SRTADMIN";
        public static final String RUNREPORT = "RUNREPORT";
        public static final String GEOCODING = "GEOCODING";
        public static final String VIEWWORKUP = "VIEWWORKUP";
        public static final String LASTUPDATE = "LASTUPDATE";
        public static final String AUTOCREATE = "AUTOCREATE";
        public static final String REPORTADMIN = "REPORTADMIN";
        public static final String HIVQUESTIONS = "HIVQUESTIONS";
        public static final String EPILINKADMIN = "EPILINKADMIN";
        public static final String FINDINACTIVE = "FINDINACTIVE";
        public static final String EXPORTREPORT = "EXPORTREPORT";
        public static final String MARKREVIEWED = "MARKREVIEWED";
        public static final String ADDINACTIVATE = "ADDINACTIVATE";
        public static final String ASSIGNSECURITY = "ASSIGNSECURITY";
        public static final String DELETEEXTERNAL = "DELETEEXTERNAL";
        public static final String VIEWELRACTIVITY = "VIEWELRACTIVITY";
        public static final String CHANGECONDITION = "CHANGECONDITION";
        public static final String SUPERVISORREVIEW = "SUPERVISORREVIEW";
        public static final String EDITREPORTPUBLIC = "EDITREPORTPUBLIC";
        public static final String VIEWREPORTPUBLIC = "VIEWREPORTPUBLIC";
        public static final String VIEWPHCRACTIVITY = "VIEWPHCRACTIVITY";
        public static final String DECISION_SUPPORT_ADMIN = "ALERTADMIN";
        public static final String EDITREPORTPRIVATE = "EDITREPORTPRIVATE";
        public static final String VIEWREPORTPRIVATE = "VIEWREPORTPRIVATE";
        public static final String LDFADMINISTRATION = "LDFADMINISTRATION";
        public static final String IMPORTEXPORTADMIN = "IMPORTEXPORTADMIN";
        public static final String ASSOCIATEDOCUMENTS = "ASSOCIATEDOCUMENTS";
        public static final String CREATEREPORTPUBLIC = "CREATEREPORTPUBLIC";
        public static final String DELETEREPORTPUBLIC = "DELETEREPORTPUBLIC";
        public static final String VIEWREPORTTEMPLATE = "VIEWREPORTTEMPLATE";
        public static final String ROLEADMINISTRATION = "ROLEADMINISTRATION";
        public static final String MAINTAINOUTBREAKNM = "MAINTAINOUTBREAKNM";
        public static final String MERGEINVESTIGATION = "MERGEINVESTIGATION";
        public static final String TRANSFERPERMISSIONS = "TRANSFERPERMISSIONS";
        public static final String CREATEREPORTPRIVATE = "CREATEREPORTPRIVATE";
        public static final String DELETEREPORTPRIVATE = "DELETEREPORTPRIVATE";
        public static final String CREATENEEDSAPPROVAL = "CREATENEEDSAPPROVAL";
        public static final String ASSOCIATETREATMENTS = "ASSOCIATETREATMENTS";
        public static final String CREATECASEREPORTING = "CREATECASEREPORTING";
        public static final String INVESTIGATIONRVCTHIV = "INVESTIGATIONRVCTHIV";
        public static final String SECURITYADMINISTRATION = "SECURITYADMINISTRATION";
        public static final String CREATESUMMARYNOTIFICATION = "CREATESUMMARYNOTIFICATION";
        public static final String SELECTFILTERCRITERIAPUBLIC = "SELECTFILTERCRITERIAPUBLIC";
        public static final String ASSIGNSECURITYFORDATAENTRY = "ASSIGNSECURITYFORDATAENTRY";
        public static final String SELECTFILTERCRITERIAPRIVATE = "SELECTFILTERCRITERIAPRIVATE";
        public static final String EDITREPORTREPORTINGFACILITY = "EDITREPORTREPORTINGFACILITY";
        public static final String VIEWREPORTREPORTINGFACILITY = "VIEWREPORTREPORTINGFACILITY";
        public static final String DISASSOCIATEINITIATINGEVENT = "DISASSOCIATEINITIATINGEVENT";
        public static final String SELECTFILTERCRITERIATEMPLATE = "SELECTFILTERCRITERIATEMPLATE";
        public static final String CREATEREPORTREPORTINGFACILITY = "CREATEREPORTREPORTINGFACILITY";
        public static final String DELETEREPORTREPORTINGFACILITY = "DELETEREPORTREPORTINGFACILITY";
        public static final String ASSOCIATEOBSERVATIONLABREPORTS = "ASSOCIATEOBSERVATIONLABREPORTS";
        public static final String ASSOCIATEOBSERVATIONMORBIDITYREPORTS = "ASSOCIATEOBSERVATIONMORBIDITYREPORTS";
        public static final String SELECTFILTERCRITERIAREPORTINGFACILITY = "SELECTFILTERCRITERIAREPORTINGFACILITY";
    }

    public static class BusinessObjects {
        private BusinessObjects() {
        }

        public static final String SRT = "SRT";
        public static final String PLACE = "PLACE";
        public static final String QUEUES = "QUEUES";
        public static final String GLOBAL = "GLOBAL";
        public static final String SYSTEM = "SYSTEM";
        public static final String PATIENT = "PATIENT";
        public static final String DOCUMENT = "DOCUMENT";
        public static final String MATERIAL = "material";
        public static final String PROVIDER = "PROVIDER";
        public static final String INTERVIEW = "INTERVIEW";
        public static final String REPORTING = "REPORTING";
        public static final String TREATMENT = "TREATMENT";
        public static final String CT_CONTACT = "CT_CONTACT";
        public static final String PUBLICQUEUES = "PUBLICQUEUES";
        public static final String ORGANIZATION = "ORGANIZATION";
        public static final String NOTIFICATION = "NOTIFICATION";
        public static final String CASEREPORTING = "CASEREPORTING";
        public static final String INVESTIGATION = "INVESTIGATION";
        public static final String SUMMARYREPORT = "SUMMARYREPORT";
        public static final String OBSERVATIONLABREPORT = "OBSERVATIONLABREPORT";
        public static final String OBSERVATIONMORBREPORT = "OBSERVATIONMORBREPORT";
        public static final String INTERVENTIONVACCINERECORD = "INTERVENTIONVACCINERECORD";
        public static final String OBSERVATIONMORBIDITYREPORT = "OBSERVATIONMORBIDITYREPORT";
        public static final String OBSERVATIONGENERICOBSERVATION = "OBSERVATIONGENERICOBSERVATION";
        public static final String ASSOCIATEINTERVENTIONVACCINERECORDS = "ASSOCIATEINTERVENTIONVACCINERECORDS";
    }

}
