/**
* Name:        JNDINames.java
* Description:    This class is used to store the JNDI names of various entities.
*               Change made here should be reflected in the deployment descriptors.
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    Brent Chen & NEDSS Development Team
* @version    1.0
**/


package gov.cdc.nedss.util;

import gov.cdc.nedss.act.clinicaldocument.ejb.bean.ClinicalDocumentHome;
import gov.cdc.nedss.act.ctcontact.ejb.bean.CTContactHome;
import gov.cdc.nedss.act.intervention.ejb.bean.InterventionHome;
import gov.cdc.nedss.act.interview.ejb.bean.InterviewHome;
import gov.cdc.nedss.act.notification.ejb.bean.NotificationHome;
import gov.cdc.nedss.act.observation.ejb.bean.ObservationHome;
import gov.cdc.nedss.act.patientencounter.ejb.bean.PatientEncounterHome;
import gov.cdc.nedss.act.publichealthcase.ejb.bean.PublicHealthCaseHome;
import gov.cdc.nedss.act.referral.ejb.bean.ReferralHome;
import gov.cdc.nedss.act.treatment.ejb.bean.TreatmentHome;
import gov.cdc.nedss.alert.ejb.alertejb.AlertHome;
import gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerHome;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerHome;
import gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean.DeDuplicationProcessorHome;
import gov.cdc.nedss.entity.entitygroup.ejb.bean.EntityGroupHome;
import gov.cdc.nedss.entity.material.ejb.bean.MaterialHome;
import gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.bean.NonPersonLivingSubjectHome;
import gov.cdc.nedss.entity.organization.ejb.bean.OrganizationHome;
import gov.cdc.nedss.entity.person.ejb.bean.PersonHome;
import gov.cdc.nedss.entity.place.ejb.bean.PlaceHome;
import gov.cdc.nedss.geocoding.ejb.geocodingservice.bean.GeoCodingServiceHome;
import gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.LDFMetaDataHome;
import gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupHome;
import gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportHome;
import gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean.SubformMetaDataHome;
import gov.cdc.nedss.localfields.ejb.localfieldsejb.bean.LocalFieldsHome;
import gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.bean.NNDMessageProcessorHome;
import gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyHome;
import gov.cdc.nedss.page.ejb.portproxyejb.bean.PortProxyHome;
import gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.bean.PageManagementProxyHome;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.bean.InterventionProxyHome;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyHome;
import gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyHome;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyHome;
import gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyHome;
import gov.cdc.nedss.proxy.ejb.queue.bean.QueueHome;
import gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean.TaskListProxyHome;
import gov.cdc.nedss.proxy.ejb.treatmentproxyejb.bean.TreatmentProxyHome;
import gov.cdc.nedss.proxy.ejb.workupproxyejb.bean.WorkupProxyHome;
import gov.cdc.nedss.report.ejb.datasourceejb.bean.DataSourceHome;
import gov.cdc.nedss.report.ejb.reportcontrollerejb.bean.ReportControllerHome;
import gov.cdc.nedss.report.ejb.reportejb.bean.ReportHome;
import gov.cdc.nedss.report.ejb.sas.bean.SASEngineHome;
import gov.cdc.nedss.srtadmin.ejb.srtadminejb.bean.SRTAdminHome;
import gov.cdc.nedss.systemservice.ejb.casenotificationejb.bean.CaseNotificationHome;
import gov.cdc.nedss.systemservice.ejb.dbauthejb.bean.DbAuthHome;
import gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithmHome;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocumentHome;
import gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.JurisdictionHome;
import gov.cdc.nedss.systemservice.ejb.jurisdictionserviceejb.bean.JurisdictionServiceHome;
import gov.cdc.nedss.systemservice.ejb.logcontrollerejb.bean.LogControllerHome;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommandHome;
import gov.cdc.nedss.systemservice.ejb.mprupdateejb.bean.MPRUpdateEngineHome;
import gov.cdc.nedss.systemservice.ejb.nbschartsejb.bean.NBSChartHome;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentHome;
import gov.cdc.nedss.systemservice.ejb.pamconversionejb.bean.PamConversionHome;
import gov.cdc.nedss.systemservice.ejb.questionmapejb.bean.QuestionMapHome;
import gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManagerHome;
import gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapHome;
import gov.cdc.nedss.systemservice.ejb.triggercodeejb.bean.TrigCodeHome;
import gov.cdc.nedss.systemservice.ejb.uidgenerator.bean.UidgeneratorHome;

public interface JNDINames
{
//  public static final String ENTITY_GROUP_EJB = "EntityGroupEJB";
//  public static final String MATERIAL_EJB = "MaterialEJB";
//  public static final String ORGANIZATION_EJB = "OrganizationEJBRef";
//  public static final String PERSON_EBJ = "PersonComEJB";
//  public static final String PLACE_EJB = "PlaceEJB";

    /**
     * JNDI names of EJB home objects
     */
  /*  public static final String CDMSESSIONFACADE_EJB_HOME = "CdmFacade";
    public static final String PERSON_EJB_HOME = "/ejb/PersonEntityBean";
    public static final String PERSONSEARCHER_EJB_HOME = "/ejb/EJBPersonSearcher";
    public static final String ORGANIZATION_EJB_HOME = "/ejb/OrganizationEJB";
    public static final String NONPERSON_EJB_HOME = "/ejb/NonPersonEJB";
    public static final String ENTITYCONTROLLER_EJB_HOME = "/ejb/EntityControllerEJB";
    public static final String ENTITYGROUP_EJB_HOME = "/ejb/entitygroup";
*/

    /**
     * JNDI Names of data sources.
     */
    public static final String NEDSS_DATASOURCE = "/datasources/NedssDataSource";
    public static final String MSGIN_DATASOURCE = "/datasources/MsgInDataSource";
    public static final String MSGOUT_DATASOURCE = "/datasources/MsgOutDataSource";
    public static final String ELRXREF_DATASOURCE = "/datasources/ElrXrefDataSource";
    /**
     * JNDI names of DAO objects
     */

    public static final String DEDUPLICATION_ACTIVITYLOGDAO_CLASS = "gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao.DeDuplicationActivityLogDAOImpl";
    public static final String GEOCODING_ACTIVITYLOGDAO_CLASS = "gov.cdc.nedss.geocoding.ejb.geocodingservice.dao.GeoCodingActivityLogDAOImpl";

    //gov.cdc.nedss.entity.person.ejb.dao.
    public static final String PERSON_ROOT_DAO_CLASS = "gov.cdc.nedss.entity.person.ejb.dao.PersonRootDAOImpl";
    public static final String PERSON_DAO_CLASS = "gov.cdc.nedss.entity.person.ejb.dao.PersonDAOImpl";
    public static final String PERSON_NAME_DAO_CLASS = "gov.cdc.nedss.entity.person.ejb.dao.PersonNameDAOImpl";
    public static final String PERSON_RACE_DAO_CLASS = "gov.cdc.nedss.entity.person.ejb.dao.PersonRaceDAOImpl";
    public static final String PERSON_ETHNIC_GROUP_DAO_CLASS = "gov.cdc.nedss.entity.person.ejb.dao.PersonEthnicGroupDAOImpl";

    //gov.cdc.nedss.entity.entityid.dao.
    public static final String ENTITY_ID_DAO_CLASS = "gov.cdc.nedss.entity.entityid.dao.EntityIdDAOImpl";

    //gov.cdc.nedss.locator.dao.
    public static final String ENTITY_LOCATOR_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.locator.dao.EntityLocatorParticipationDAOImpl";
    public static final String PHYSICAL_LOCATOR_DAO_CLASS = "gov.cdc.nedss.locator.dao.PhysicalLocatorDAOImpl";
    public static final String POSTAL_LOCATOR_DAO_CLASS = "gov.cdc.nedss.locator.dao.PostalLocatorDAOImpl";
    public static final String TELE_LOCATOR_DAO_CLASS = "gov.cdc.nedss.locator.dao.TeleLocatorDAOImpl";

    //gov.cdc.nedss.entity.organization.ejb.dao.
    public static final String ORGANIZATION_ROOT_DAO_CLASS = "gov.cdc.nedss.entity.organization.ejb.dao.OrganizationRootDAOImpl";
    public static final String ORGANIZATION_DAO_CLASS = "gov.cdc.nedss.entity.organization.ejb.dao.OrganizationDAOImpl";
    public static final String ORGANIZATION_NAME_DAO_CLASS = "gov.cdc.nedss.entity.organization.ejb.dao.OrganizationNameDAOImpl";

    //gov.cdc.nedss.entity.material.ejb.dao.
    public static final String MATERIAL_ROOT_DAO_CLASS = "gov.cdc.nedss.entity.material.ejb.dao.MaterialRootDAOImpl";
    public static final String MATERIAL_DAO_CLASS = "gov.cdc.nedss.entity.material.ejb.dao.MaterialDAOImpl";
    public static final String MANUFACTURED_MATERIAL_DAO_CLASS = "gov.cdc.nedss.entity.material.ejb.dao.ManufacturedMaterialDAOImpl";

    //gov.cdc.nedss.entity.place.ejb.dao.
    public static final String PLACE_ROOT_DAO_CLASS = "gov.cdc.nedss.entity.place.ejb.dao.PlaceRootDAOImpl";
    public static final String PLACE_DAO_CLASS = "gov.cdc.nedss.entity.place.ejb.dao.PlaceDAOImpl";

    //gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.dao.
    public static final String NONPERSONLIVINGSUBJECT_ROOT_DAO_CLASS = "gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.dao.NonPersonLivingSubjectRootDAOImpl";
    public static final String NONPERSONLIVINGSUBJECT_DAO_CLASS = "gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.dao.NonPersonLivingSubjectDAOImpl";

    //gov.cdc.nedss.entity.entitygroup.ejb.dao.
    public static final String ENTITYGROUP_DAO_CLASS = "gov.cdc.nedss.entity.entitygroup.ejb.dao.EntityGroupDAOImpl";
    public static final String ENTITYGROUP_ROOT_DAO_CLASS = "gov.cdc.nedss.entity.entitygroup.ejb.dao.EntityGroupRootDAOImpl";

    //gov.cdc.nedss.association.dao.
    public static final String ORGANIZATION_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.association.dao.ParticipationDAOImpl";
    public static final String PERSON_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.association.dao.ParticipationDAOImpl";
    public static final String MATERIAL_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.association.dao.ParticipationDAOImpl";
    public static final String PLACE_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.association.dao.ParticipationDAOImpl";
    public static final String NONPERSONLIVINGSUBJECT_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.association.dao.ParticipationDAOImpl";
    public static final String ENTITY_GROUP_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.association.dao.ParticipationDAOImpl";
    public static final String ACT_RELATIONSHIP_DAO_CLASS = "gov.cdc.nedss.association.dao.ActRelationshipDAOImpl";
    public static final String ACT_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.association.dao.ParticipationDAOImpl";
    public static final String ENTITY_ROLE_DAO_CLASS = "gov.cdc.nedss.association.dao.RoleDAOImpl";
    public static final String ROLE_DAO_CLASS = "gov.cdc.nedss.association.dao.RoleDAOImpl";
    public static final String PAM_DAO_CLASS = "gov.cdc.nedss.pam.dao.PamRootDAO";



    /**
     * JNDI names of DAO objects
     */
    //gov.cdc.nedss.act.publichealthcase.ejb.dao
    public static final String PUBLIC_HEALTH_CASE_ROOT_DAO_CLASS = "gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseRootDAOImpl";
    public static final String PUBLIC_HEALTH_CASE_DAO_CLASS = "gov.cdc.nedss.act.publichealthcase.ejb.dao.PublicHealthCaseDAOImpl";
    public static final String CONFIRMATION_METHOD_DAO_CLASS = "gov.cdc.nedss.act.publichealthcase.ejb.dao.ConfirmationMethodDAOImpl";
    public static final String CASE_MANAGEMENT_DAO_CLASS = "gov.cdc.nedss.act.publichealthcase.ejb.dao.CaseManagementDAOImpl";

    //gov.cdc.nedss.act.observation.ejb.dao
    public static final String OBSERVATION_ROOT_DAO_CLASS = "gov.cdc.nedss.act.observation.ejb.dao.ObservationRootDAOImpl";
    public static final String OBSERVATION_DAO_CLASS = "gov.cdc.nedss.act.observation.ejb.dao.ObservationDAOImpl";
    public static final String OBSERVATION_REASON_DAO_CLASS = "gov.cdc.nedss.act.observation.ejb.dao.ObservationReasonDAOImpl";
    public static final String OBSERVATION_INTERP_DAO_CLASS = "gov.cdc.nedss.act.observation.ejb.dao.ObservationInterpDAOImpl";
    public static final String OBS_VALUE_CODED_DAO_CLASS = "gov.cdc.nedss.act.observation.ejb.dao.ObsValueCodedDAOImpl";
    public static final String OBS_VALUE_TXT_DAO_CLASS = "gov.cdc.nedss.act.observation.ejb.dao.ObsValueTxtDAOImpl";
    public static final String OBS_VALUE_DATE_DAO_CLASS = "gov.cdc.nedss.act.observation.ejb.dao.ObsValueDateDAOImpl";
    public static final String OBS_VALUE_NUMERIC_DAO_CLASS = "gov.cdc.nedss.act.observation.ejb.dao.ObsValueNumericDAOImpl";

    //gov.cdc.nedss.act.actid.dao
    public static final String ACTIVITY_ID_DAO_CLASS = "gov.cdc.nedss.act.actid.dao.ActivityIdDAOImpl";

    //gov.cdc.nedss.act.notification.ejb.dao
    public static final String NOTIFICATION_DAO_CLASS = "gov.cdc.nedss.act.notification.ejb.dao.NotificationDAOImpl";
    public static final String UPDATED_NOTIFICATION_DAO_CLASS = "gov.cdc.nedss.proxy.ejb.notificationproxyejb.dao.UpdatedNotificationDAOImpl";
    public static final String NOTIFICATION_ROOT_DAO_CLASS = "gov.cdc.nedss.act.notification.ejb.dao.NotificationRootDAOImpl";

    //gov.cdc.nedss.act.file.ejb.dao
    public static final String WORKUP_ROOT_DAO_CLASS = "gov.cdc.nedss.act.file.ejb.dao.WorkupRootDAOImpl";
    public static final String WORKUP_DAO_CLASS = "gov.cdc.nedss.act.file.ejb.dao.WorkupDAOImpl";

    //gov.cdc.nedss.act.referral.ejb.dao
    public static final String REFERRAL_ROOT_DAO_CLASS = "gov.cdc.nedss.act.referral.ejb.dao.ReferralRootDAOImpl";
    public static final String REFERRAL_DAO_CLASS = "gov.cdc.nedss.act.referral.ejb.dao.ReferralDAOImpl";

    //gov.cdc.nedss.act.clinicaldocument.ejb.dao
    public static final String CLINICALDOCUMENT_ROOT_DAO_CLASS = "gov.cdc.nedss.act.clinicaldocument.ejb.dao.ClinicalDocumentRootDAOImpl";
    public static final String CLINICALDOCUMENT_DAO_CLASS = "gov.cdc.nedss.act.clinicaldocument.ejb.dao.ClinicalDocumentDAOImpl";

    //gov.cdc.nedss.act.patientencounter.ejb.dao
    public static final String PATIENTENCOUNTER_ROOT_DAO_CLASS = "gov.cdc.nedss.act.patientencounter.ejb.dao.PatientEncounterRootDAOImpl";
    public static final String PATIENTENCOUNTER_DAO_CLASS = "gov.cdc.nedss.act.patientencounter.ejb.dao.PatientEncounterDAOImpl";


    //gov.cdc.nedss.act.intervention.ejb.dao
    public static final String INTERVENTION_ROOT_DAO_CLASS = "gov.cdc.nedss.act.intervention.ejb.dao.InterventionRootDAOImpl";
    public static final String INTERVENTION_DAO_CLASS = "gov.cdc.nedss.act.intervention.ejb.dao.InterventionDAOImpl";
    public static final String INTERVENTION_PROCEDURE1_DAO_CLASS = "gov.cdc.nedss.act.intervention.ejb.dao.Procedure1DAOImpl";
    public static final String INTERVENTION_SUBSTANCE_ADMINISTRATION_DAO_CLASS = "gov.cdc.nedss.act.intervention.ejb.dao.SubstanceAdministrationDAOImpl";

    //gov.cdc.nedss.act.treatment.ejb.dao
    public static final String TREATMENT_ROOT_DAO_CLASS = "gov.cdc.nedss.act.treatment.ejb.dao.TreatmentRootDAOImpl";
    public static final String TREATMENT_DAO_CLASS = "gov.cdc.nedss.act.treatment.ejb.dao.TreatmentDAOImpl";
    public static final String TREATMENT_PROCEDURE_DAO_CLASS = "gov.cdc.nedss.act.treatment.ejb.dao.TreatmentProcedureDAOImpl";
    public static final String TREATMENT_ADMINISTERED_DAO_CLASS = "gov.cdc.nedss.act.treatment.ejb.dao.TreatmentAdministeredDAOImpl";
    public static final String TreatmentEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.treatment.ejb.bean.TreatmentEJB!"+TreatmentHome.class.getName();


    //
    public static final String ACTIVITY_LOCATOR_PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.locator.dao.ActivityLocatorParticipationDAOImpl";

    //gov.cdc.nedss.association.dao
   // public static final String ACT_RELATIONSHIP_DAO_CLASS = "gov.cdc.nedss.association.dao.ActRelationshipDAOImpl";
    public static final String PARTICIPATION_DAO_CLASS = "gov.cdc.nedss.association.dao.ParticipationDAOImpl";
  //  public static final String ROLE_DAO_CLASS = "gov.cdc.nedss.association.dao.RoleDAOImpl";

    //gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao
    public static final String DEDUPLICATION_DAO_CLASS = "gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.dao.DeDuplicationDAO";
    public static final String GEOCODING_DAO_CLASS = "gov.cdc.nedss.geocoding.ejb.geocodingservice.dao.GeoCodingDAO";

    // gov.cdc.nedss.geocoding.dao
    public static final String GEOCODING_RESULT_DAO_CLASS = "gov.cdc.nedss.geocoding.dao.GeoCodingResultDAOImpl";
    public static final String GEOCODING_RESULT_HIST_DAO_CLASS = "gov.cdc.nedss.geocoding.dao.GeoCodingResultHistDAOImpl";

    //SRT Admin DAOs
    public static final String LABTEST_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.LabTestDAOImpl";
    public static final String LABTEST_LOINC_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.LabTestLoincDAOImpl";
    public static final String LOINC_CONDITION_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.LoincConditionDAOImpl";
    public static final String CODE_VALUE_GENERAL_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.CodeValueGeneralDAOImpl";
    public static final String LOCALLY_DEFINED_LABORATORY_RESULT_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.LabResultDAO";
    public static final String TRIGGER_CODES_RESULT_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.TriggerCodeDAO";
    public static final String LABRESULT_SNOMED_DAO_CLASS="gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.LocallyDefinedResultAndSnomedDAOImpl";
    public static final String SNOMED_DAO_CLASS="gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.SnomedCodeDAOImpl";
    public static final String LABORATORY_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.LabCodingSystemDAOImpl";
    public static final String MANAGE_LOINCS_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.ManageLoincsDAOImpl";
    public static final String RESET_CACHE_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.ResetCacheDAOImpl";
    public static final String SNOMED_CONDITION_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.SnomedConditionDAOImpl";
    public static final String CODE_SET_DAO_CLASS = "gov.cdc.nedss.srtadmin.ejb.srtadminejb.dao.ManageCodeSetDAOImpl";
    public static final String CONDITION_DAO_CLASS = "gov.cdc.nedss.pagemanagement.wa.dao.ConditionDAOImpl";

    //Charts DAOs
    public static final String NBS_CHART_DAO_CLASS= "gov.cdc.nedss.systemservice.ejb.nbschartsejb.dao.HomePageChartsDAO";
	public static final String NBS_CHART_METADATA_DAO_CLASS = "gov.cdc.nedss.systemservice.ejb.nbschartsejb.dao.ChartReportMetadataDAO";

    public static final String ENTITY_GROUP = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.entity.entitygroup.ejb.bean.EntityGroupEJB!"+EntityGroupHome.class.getName();
    public static final String MATERIAL = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.entity.material.ejb.bean.MaterialEJB!"+MaterialHome.class.getName();
    public static final String PERSONEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.entity.person.ejb.bean.PersonEJB!"+PersonHome.class.getName();
    public static final String PLACE = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.entity.place.ejb.bean.PlaceEJB!"+PlaceHome.class.getName();
    public static final String ObservationEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.observation.ejb.bean.ObservationEJB!"+ObservationHome.class.getName();
    public static final String PublicHealthCaseEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.publichealthcase.ejb.bean.PublicHealthCaseEJB!"+PublicHealthCaseHome.class.getName();
    public static final String NotificationEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.notification.ejb.bean.NotificationEJB!"+NotificationHome.class.getName();
    public static final String InterventionEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.intervention.ejb.bean.InterventionEJB!"+InterventionHome.class.getName();
    public static final String InterviewEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.interview.ejb.bean.InterviewEJB!"+InterviewHome.class.getName();
    public static final String ReferralEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.referral.ejb.bean.ReferralEJB!"+ReferralHome.class.getName();
    public static final String PatientEncounterEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.patientencounter.ejb.bean.PatientEncounterEJB!"+PatientEncounterHome.class.getName();
    public static final String ClinicalDocumentEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.clinicaldocument.ejb.bean.ClinicalDocumentEJB!"+ClinicalDocumentHome.class.getName();
    public static final String EntityControllerEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityControllerEJB!"+EntityControllerHome.class.getName();
    public static final String ActControllerEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.controller.ejb.actcontrollerejb.bean.ActControllerEJB!"+ActControllerHome.class.getName();
    public static final String NND_MESSAGE_PROCESSOR_EJB_REF = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.bean.NNDMessageProcessorEJB!"+NNDMessageProcessorHome.class.getName();
    public static final String NONPERSONLIVINGSUBJECTEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.entity.nonpersonlivingsubject.ejb.bean.NonPersonEJB!"+NonPersonLivingSubjectHome.class.getName();
    public static final String ORGANIZATIONEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.entity.organization.ejb.bean.OrganizationEJB!"+OrganizationHome.class.getName();
    public static final String NOTIFICATION_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.notificationproxyejb.bean.NotificationProxyEJB!"+NotificationProxyHome.class.getName();
    public static final String TASKLIST_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.tasklistproxyejb.bean.TaskListProxyEJB!"+TaskListProxyHome.class.getName();
    public static final String ENTITY_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyEJB!"+EntityProxyHome.class.getName();
    public static final String MPR_UPDATE_ENGINE_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.mprupdateejb.bean.MPRUpdateEngineEJB!"+MPRUpdateEngineHome.class.getName();
    public static final String DEDUPLICATION_PROCESSOR_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.deduplication.ejb.deduplicationprocessor.bean.DeDuplicationProcessorEJB!"+DeDuplicationProcessorHome.class.getName();
    public static final String JURISDICTIONSERVICE_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.jurisdictionserviceejb.bean.JurisdictionServiceEJB!"+JurisdictionServiceHome.class.getName();
    public static final String WORKUP_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.workupproxyejb.bean.WorkupProxyEJB!"+WorkupProxyHome.class.getName();
    public static final String INVESTIGATION_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.investigationproxyejb.bean.InvestigationProxyEJB!"+InvestigationProxyHome.class.getName();
    public static final String TREATMENT_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.TreatmentProxyEJB!"+TreatmentProxyHome.class.getName();
    public static final String OBSERVATION_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.observationproxyejb.bean.ObservationProxyEJB!"+ObservationProxyHome.class.getName();
    // remember to remove when old directory structure is removed
    public static final String JURISDICTION_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.jurisdictionejb.bean.JurisdictionEJB!"+JurisdictionHome.class.getName();
    public static final String SRT_CACHEMANAGER_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.srtcachemanagerejb.bean.SRTCacheManagerEJB!"+SRTCacheManagerHome.class.getName();
    public static final String LDFMetaData_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.ldf.ejb.ldfmetadataejb.bean.LDFMetaDataEJB!"+LDFMetaDataHome.class.getName();
    public static final String SUBFORMMetaDataEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean.SubformMetaDataEJB!"+SubformMetaDataHome.class.getName();
    public static final String CUSTOM_DATAIMPORT_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.ldf.importer.ejb.bean.CustomDataImportEJB!"+CustomDataImportHome.class.getName();
    public static final String SRT_CACHE_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.srtmapejb.bean.SRTMapEJB!"+SRTMapHome.class.getName();
    public static final String GEOCODING_SERVICE_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.geocoding.ejb.geocodingservice.bean.GeoCodingServiceEJB!"+GeoCodingServiceHome.class.getName();
    public static final String PAM_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.pamproxyejb.bean.PamProxyEJB!"+PamProxyHome.class.getName();
    public static final String SRT_ADMIN_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.srtadmin.ejb.SRTAdminEJB!"+SRTAdminHome.class.getName();
    public static final String ALERT_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.alert.ejb.alertejb.AlertEJB!"+AlertHome.class.getName();
    public static final String LOCAL_FIELDS_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.localfields.ejb.localfieldsejb.bean.LocalFieldsEJB!"+LocalFieldsHome.class.getName();
    public static final String CASE_NOTIFICATION_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.casenotificationejb.bean.CaseNotificationEJB!"+CaseNotificationHome.class.getName();
    public static final String TRIGGER_CODE_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.triggercodeejb.bean.TrigCodeEJB!"+TrigCodeHome.class.getName();
    public static final String  NBS_DOCUMENT_EJB= "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.bean.NbsDocumentEJB!"+NbsDocumentHome.class.getName();
    public static final String NBS_CHART_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.nbschartsejb.NBSChartEJB!"+NBSChartHome.class.getName();
    public static final String PAGE_MANAGEMENT_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.pagemanagement.ejb.pagemanagementproxyejb.bean.PageManagementProxyEJB!"+PageManagementProxyHome.class.getName();
    public static final String PAGE_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.page.ejb.pageproxyejb.bean.PageProxyEJB!"+PageProxyHome.class.getName();
    public static final String  EDX_PHCR_DOCUMENT_EJB=  "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.edxdocumentejb.bean.EdxPHCRDocumentEJB!"+EdxPHCRDocumentHome.class.getName();
    public static final String DSMAlgorithmEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithmEJB!"+DSMAlgorithmHome.class.getName();
    public static final String NBS_DB_SECURITY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.dbauthejb.bean.DbAuthEJB!"+DbAuthHome.class.getName();
    public static final String MAIN_CONTROL_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainControlEJB!"+MainSessionCommandHome.class.getName();
    public static final String SUBFORM_METADATA_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.ldf.subform.ejb.subformmetadataejb.bean.SubformMetaDataEJB!"+SubformMetaDataHome.class.getName();
    public static final String INTERVENTION_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.interventionproxyejb.bean.InterventionProxyEJB!"+InterventionProxyHome.class.getName();
    public static final String REPORT_CONTROLLER_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.report.ejb.reportcontrollerejb.bean.ReportControllerEJB!"+ReportControllerHome.class.getName();
    public static final String REPORT_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.report.ejb.reportejb.bean.ReportEJB!"+ReportHome.class.getName();
    public static final String DATA_SOURCE_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.report.ejb.datasourceejb.bean.DataSourceEJB!"+DataSourceHome.class.getName();
    public static final String SASEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.report.ejb.sas.bean.SASEngineEJB!"+SASEngineHome.class.getName();
    public static final String PAM_CONVERSION_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.pamconversionejb.bean.PamConversionEJB!"+PamConversionHome.class.getName();
    public static final String UIDGENERATOR_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.uidgenerator.bean.UidgeneratorEJB!"+UidgeneratorHome.class.getName();
    public static final String DEFINED_FIELD_SUBFORMGROUP_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.ldf.group.ejb.bean.DefinedFieldSubformGroupEJB!"+DefinedFieldSubformGroupHome.class.getName();
    public static final String QUEUE_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.proxy.ejb.queue.bean.QueueEJB!"+QueueHome.class.getName();
    public static final String LOG_CONTROLLER_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.logcontrollerejb.bean.LogControllerEJB!"+LogControllerHome.class.getName();
    public static final String QUESTION_MAP_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.questionmapejb.bean.QuestionMapEJB!"+QuestionMapHome.class.getName();
    
    public static final String PORT_PAGE_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.page.ejb.portproxyejb.bean.PortProxyEJB!"+PortProxyHome.class.getName();
    //LocalFields DAO
    public static final String LOCAL_FIELD_METADATA_DAO_CLASS = "gov.cdc.nedss.localfields.ejb.dao.LocalFieldMetaDataDAOImpl";
    public static final String NBS_QUESTION_METADATA_DAO_CLASS = "gov.cdc.nedss.localfields.ejb.dao.NBSQuestionDAOImpl";
    public static final String NBS_UI_METADATA_DAO_CLASS = "gov.cdc.nedss.localfields.ejb.dao.NBSUIMetaDataDAOImpl";
    public static final String CASE_NOTIFICATION_DAO_CLASS = "gov.cdc.nedss.systemservice.ejb.casenotificationejb.dao.CaseNotificationDAOImpl";
    public static final String CTContactEJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.act.ctcontact.ejb.bean.CTContactEJB!"+CTContactHome.class.getName();
    public static final String CTCONTACT_ANSWER_DAO = "gov.cdc.nedss.act.ctcontact.ejb.dao.CTContactRootDAO";

    public static final String TEMPLATE_DAO = "gov.cdc.nedss.pagemanagement.wa.dao.TemplatesDAOImpl";
    public static final String DSM_PROXY_EJB = "ejb:nbs/NEDSSEJB/gov.cdc.nedss.systemservice.ejb.decisionsupportejb.bean.DSMAlgorithmEJB!"+DSMAlgorithmHome.class.getName();
    public static final String INTERVIEW_ROOT_DAO_CLASS = "gov.cdc.nedss.act.interview.ejb.dao.InterviewRootDAOImpl";
    public static final String INTERVIEW_DAO_CLASS = "gov.cdc.nedss.act.interview.ejb.dao.InterviewDAOImpl";
    public static final String INTERVIEW_ANSWER_DAO_CLASS    = "gov.cdc.nedss.act.interview.ejb.dao.InterviewAnswerDAOImpl";

}//end of JNDINames interface
