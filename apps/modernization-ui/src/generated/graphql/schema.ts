import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
const defaultOptions = {} as const;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
  Date: any;
};

export type ActId = {
  __typename?: 'ActId';
  actIdSeq?: Maybe<Scalars['Int']>;
  id?: Maybe<Scalars['Int']>;
  lastChangeTime?: Maybe<Scalars['Date']>;
  recordStatus?: Maybe<Scalars['String']>;
  rootExtensionTxt?: Maybe<Scalars['String']>;
  typeCd?: Maybe<Scalars['String']>;
};

export type AssociatedInvestigation = {
  __typename?: 'AssociatedInvestigation';
  actRelationshipLastChgTime?: Maybe<Scalars['Date']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  lastChgTime?: Maybe<Scalars['Date']>;
  localId?: Maybe<Scalars['String']>;
  publicHealthCaseUid?: Maybe<Scalars['Int']>;
};

export enum CaseStatus {
  Confirmed = 'CONFIRMED',
  NotACase = 'NOT_A_CASE',
  Probable = 'PROBABLE',
  Suspect = 'SUSPECT',
  Unknown = 'UNKNOWN'
}

export type CaseStatuses = {
  includeUnassigned: Scalars['Boolean'];
  statusList: Array<CaseStatus>;
};

export type ConditionCode = {
  __typename?: 'ConditionCode';
  conditionDescTxt?: Maybe<Scalars['String']>;
  id: Scalars['String'];
};

export type CountryCode = {
  __typename?: 'CountryCode';
  assigningAuthorityCd?: Maybe<Scalars['String']>;
  assigningAuthorityDescTxt?: Maybe<Scalars['String']>;
  codeDescTxt?: Maybe<Scalars['String']>;
  codeSetNm?: Maybe<Scalars['String']>;
  codeShortDescTxt?: Maybe<Scalars['String']>;
  codeSystemCd?: Maybe<Scalars['String']>;
  codeSystemDescTxt?: Maybe<Scalars['String']>;
  effectiveFromTime?: Maybe<Scalars['Date']>;
  effectiveToTime?: Maybe<Scalars['Date']>;
  excludedTxt?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['ID']>;
  indentLevelNbr?: Maybe<Scalars['Int']>;
  isModifiableInd?: Maybe<Scalars['String']>;
  keyInfoTxt?: Maybe<Scalars['String']>;
  nbsUid?: Maybe<Scalars['Int']>;
  parentIsCd?: Maybe<Scalars['String']>;
  seqNum?: Maybe<Scalars['Int']>;
  sourceConceptId?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['Date']>;
};

export type CountyCode = {
  __typename?: 'CountyCode';
  codeDescTxt?: Maybe<Scalars['String']>;
  codeShortDescTxt?: Maybe<Scalars['String']>;
  id: Scalars['String'];
};

export enum Deceased {
  N = 'N',
  Unk = 'UNK',
  Y = 'Y'
}

export enum EntryMethod {
  Electronic = 'ELECTRONIC',
  Manual = 'MANUAL'
}

export type Ethnicity = {
  __typename?: 'Ethnicity';
  codeDescTxt: Scalars['String'];
  id: EthnicityId;
};

export type EthnicityId = {
  __typename?: 'EthnicityId';
  code: Scalars['String'];
};

export type EthnicityResults = {
  __typename?: 'EthnicityResults';
  content: Array<Maybe<Ethnicity>>;
  total: Scalars['Int'];
};

export enum EventStatus {
  New = 'NEW',
  Update = 'UPDATE'
}

export enum Gender {
  F = 'F',
  M = 'M',
  U = 'U'
}

export type Identification = {
  assigningAuthority?: InputMaybe<Scalars['String']>;
  identificationNumber: Scalars['String'];
  identificationType: Scalars['String'];
};

export type IdentificationType = {
  __typename?: 'IdentificationType';
  codeDescTxt: Scalars['String'];
  id: IdentificationTypeId;
};

export type IdentificationTypeId = {
  __typename?: 'IdentificationTypeId';
  code: Scalars['String'];
};

export type Investigation = {
  __typename?: 'Investigation';
  actIds?: Maybe<Array<Maybe<ActId>>>;
  activityFromTime?: Maybe<Scalars['Date']>;
  activityToTime?: Maybe<Scalars['Date']>;
  addTime?: Maybe<Scalars['Date']>;
  addUserId?: Maybe<Scalars['Int']>;
  caseClassCd?: Maybe<Scalars['String']>;
  caseTypeCd?: Maybe<Scalars['String']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  currProcessStateCd?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['ID']>;
  investigationStatusCd?: Maybe<Scalars['String']>;
  jurisdictionCd?: Maybe<Scalars['Int']>;
  jurisdictionCodeDescTxt?: Maybe<Scalars['String']>;
  lastChangeTime?: Maybe<Scalars['Date']>;
  lastChangeUserId?: Maybe<Scalars['Int']>;
  localId?: Maybe<Scalars['String']>;
  moodCd?: Maybe<Scalars['String']>;
  notificationAddTime?: Maybe<Scalars['Date']>;
  notificationLastChgTime?: Maybe<Scalars['Date']>;
  notificationLocalId?: Maybe<Scalars['String']>;
  notificationRecordStatusCd?: Maybe<Scalars['String']>;
  organizationParticipations?: Maybe<Array<Maybe<OrganizationParticipation>>>;
  outbreakName?: Maybe<Scalars['String']>;
  personParticipations?: Maybe<Array<Maybe<PersonParticipation>>>;
  pregnantIndCd?: Maybe<Scalars['String']>;
  progAreaCd?: Maybe<Scalars['String']>;
  publicHealthCaseLastChgTime?: Maybe<Scalars['Date']>;
  publicHealthCaseUid?: Maybe<Scalars['Int']>;
  recordStatus?: Maybe<Scalars['String']>;
  rptFormCmpltTime?: Maybe<Scalars['Date']>;
};

export type InvestigationEventDateSearch = {
  eventDateType: InvestigationEventDateType;
  from: Scalars['Date'];
  to: Scalars['Date'];
};

export enum InvestigationEventDateType {
  DateOfReport = 'DATE_OF_REPORT',
  InvestigationClosedDate = 'INVESTIGATION_CLOSED_DATE',
  InvestigationCreateDate = 'INVESTIGATION_CREATE_DATE',
  InvestigationStartDate = 'INVESTIGATION_START_DATE',
  LastUpdateDate = 'LAST_UPDATE_DATE',
  NotificationCreateDate = 'NOTIFICATION_CREATE_DATE'
}

export enum InvestigationEventIdType {
  AbcsCaseId = 'ABCS_CASE_ID',
  CityCountyCaseId = 'CITY_COUNTY_CASE_ID',
  InvestigationId = 'INVESTIGATION_ID',
  NotificationId = 'NOTIFICATION_ID',
  StateCaseId = 'STATE_CASE_ID'
}

export type InvestigationFilter = {
  caseStatuses?: InputMaybe<CaseStatuses>;
  conditions?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  createdBy?: InputMaybe<Scalars['String']>;
  eventDateSearch?: InputMaybe<InvestigationEventDateSearch>;
  eventId?: InputMaybe<Scalars['String']>;
  eventIdType?: InputMaybe<InvestigationEventIdType>;
  investigationStatus?: InputMaybe<InvestigationStatus>;
  investigatorId?: InputMaybe<Scalars['ID']>;
  jurisdictions?: InputMaybe<Array<InputMaybe<Scalars['ID']>>>;
  lastUpdatedBy?: InputMaybe<Scalars['String']>;
  notificationStatuses?: InputMaybe<NotificationStatuses>;
  outbreakNames?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  patientId?: InputMaybe<Scalars['Int']>;
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatuses?: InputMaybe<ProcessingStatuses>;
  programAreas?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  providerFacilitySearch?: InputMaybe<ProviderFacilitySearch>;
};

export type InvestigationResults = {
  __typename?: 'InvestigationResults';
  content: Array<Maybe<Investigation>>;
  total: Scalars['Int'];
};

export enum InvestigationStatus {
  Closed = 'CLOSED',
  Open = 'OPEN'
}

export type Jurisdiction = {
  __typename?: 'Jurisdiction';
  assigningAuthorityCd?: Maybe<Scalars['String']>;
  assigningAuthorityDescTxt?: Maybe<Scalars['String']>;
  codeDescTxt?: Maybe<Scalars['String']>;
  codeSeqNum?: Maybe<Scalars['Int']>;
  codeSetNm?: Maybe<Scalars['String']>;
  codeShortDescTxt?: Maybe<Scalars['String']>;
  codeSystemCd?: Maybe<Scalars['String']>;
  codeSystemDescTxt?: Maybe<Scalars['String']>;
  effectiveFromTime?: Maybe<Scalars['Date']>;
  effectiveToTime?: Maybe<Scalars['Date']>;
  exportInd?: Maybe<Scalars['String']>;
  id: Scalars['String'];
  indentLevelNbr?: Maybe<Scalars['Int']>;
  isModifiableInd?: Maybe<Scalars['String']>;
  nbsUid?: Maybe<Scalars['ID']>;
  parentIsCd?: Maybe<Scalars['String']>;
  sourceConceptId?: Maybe<Scalars['String']>;
  stateDomainCd?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['Date']>;
  typeCd: Scalars['String'];
};

export type LabReport = {
  __typename?: 'LabReport';
  actIds?: Maybe<Array<Maybe<ActId>>>;
  activityToTime?: Maybe<Scalars['Date']>;
  addTime?: Maybe<Scalars['Date']>;
  addUserId?: Maybe<Scalars['Int']>;
  associatedInvestigations?: Maybe<Array<Maybe<AssociatedInvestigation>>>;
  cdDescTxt?: Maybe<Scalars['String']>;
  classCd?: Maybe<Scalars['String']>;
  effectiveFromTime?: Maybe<Scalars['Date']>;
  electronicInd?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['String']>;
  jurisdictionCd?: Maybe<Scalars['Int']>;
  jurisdictionCodeDescTxt?: Maybe<Scalars['String']>;
  lastChange?: Maybe<Scalars['Date']>;
  lastChgUserId?: Maybe<Scalars['Int']>;
  localId?: Maybe<Scalars['String']>;
  materialParticipations?: Maybe<Array<Maybe<MaterialParticipation>>>;
  moodCd?: Maybe<Scalars['String']>;
  observationLastChgTime?: Maybe<Scalars['Date']>;
  observationUid?: Maybe<Scalars['Int']>;
  observations?: Maybe<Array<Maybe<Observation>>>;
  organizationParticipations?: Maybe<Array<Maybe<OrganizationParticipation>>>;
  personParticipations?: Maybe<Array<Maybe<PersonParticipation>>>;
  pregnantIndCd?: Maybe<Scalars['String']>;
  programAreaCd?: Maybe<Scalars['String']>;
  recordStatusCd?: Maybe<Scalars['String']>;
  rptToStateTime?: Maybe<Scalars['Date']>;
  versionCtrlNbr?: Maybe<Scalars['Int']>;
};

export type LabReportFilter = {
  codedResult?: InputMaybe<Scalars['String']>;
  createdBy?: InputMaybe<Scalars['ID']>;
  enteredBy?: InputMaybe<Array<InputMaybe<UserType>>>;
  entryMethods?: InputMaybe<Array<InputMaybe<EntryMethod>>>;
  eventDateSearch?: InputMaybe<LaboratoryEventDateSearch>;
  eventId?: InputMaybe<Scalars['String']>;
  eventIdType?: InputMaybe<LaboratoryEventIdType>;
  eventStatus?: InputMaybe<Array<InputMaybe<EventStatus>>>;
  jurisdictions?: InputMaybe<Array<InputMaybe<Scalars['ID']>>>;
  lastUpdatedBy?: InputMaybe<Scalars['ID']>;
  patientId?: InputMaybe<Scalars['Int']>;
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatus?: InputMaybe<Array<InputMaybe<LaboratoryReportStatus>>>;
  programAreas?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  providerSearch?: InputMaybe<LabReportProviderSearch>;
  resultedTest?: InputMaybe<Scalars['String']>;
};

export type LabReportProviderSearch = {
  providerId: Scalars['ID'];
  providerType: ProviderType;
};

export type LabReportResults = {
  __typename?: 'LabReportResults';
  content: Array<Maybe<LabReport>>;
  total: Scalars['Int'];
};

export type LabResult = {
  __typename?: 'LabResult';
  id?: Maybe<LabResultId>;
  labResultDescTxt?: Maybe<Scalars['String']>;
  nbsUid?: Maybe<Scalars['ID']>;
};

export type LabResultId = {
  __typename?: 'LabResultId';
  labResultCd?: Maybe<Scalars['String']>;
  laboratoryId?: Maybe<Scalars['String']>;
};

export type LabTest = {
  __typename?: 'LabTest';
  id?: Maybe<LabTestId>;
  labTestDescTxt?: Maybe<Scalars['String']>;
  organismResultTestInd?: Maybe<Scalars['String']>;
};

export type LabTestId = {
  __typename?: 'LabTestId';
  labTestCd?: Maybe<Scalars['String']>;
  laboratoryId?: Maybe<Scalars['String']>;
};

export type LaboratoryEventDateSearch = {
  eventDateType: LaboratoryReportEventDateType;
  from: Scalars['Date'];
  to: Scalars['Date'];
};

export enum LaboratoryEventIdType {
  AccessionNumber = 'ACCESSION_NUMBER',
  LabId = 'LAB_ID'
}

export enum LaboratoryReportEventDateType {
  DateOfReport = 'DATE_OF_REPORT',
  DateOfSpecimenCollection = 'DATE_OF_SPECIMEN_COLLECTION',
  DateReceivedByPublicHealth = 'DATE_RECEIVED_BY_PUBLIC_HEALTH',
  LabReportCreateDate = 'LAB_REPORT_CREATE_DATE',
  LastUpdateDate = 'LAST_UPDATE_DATE'
}

export enum LaboratoryReportStatus {
  Processed = 'PROCESSED',
  Unprocessed = 'UNPROCESSED'
}

export type LocalCodedResults = {
  __typename?: 'LocalCodedResults';
  content: Array<Maybe<LabResult>>;
  total: Scalars['Int'];
};

export type LocalLabTestResults = {
  __typename?: 'LocalLabTestResults';
  content: Array<Maybe<LabTest>>;
  total: Scalars['Int'];
};

export type Locator = {
  __typename?: 'Locator';
  censusBlockCd?: Maybe<Scalars['String']>;
  censusMinorCivilDivisionCd?: Maybe<Scalars['String']>;
  censusTrackCd?: Maybe<Scalars['String']>;
  censusTract?: Maybe<Scalars['String']>;
  cityCd?: Maybe<Scalars['String']>;
  cityDescTxt?: Maybe<Scalars['String']>;
  cntryCd?: Maybe<Scalars['String']>;
  cntryDescTxt?: Maybe<Scalars['String']>;
  cntyCd?: Maybe<Scalars['String']>;
  cntyDescTxt?: Maybe<Scalars['String']>;
  emailAddress?: Maybe<Scalars['String']>;
  extenstionTxt?: Maybe<Scalars['String']>;
  geocodeMatchInd?: Maybe<Scalars['String']>;
  msaCongressDistrictCd?: Maybe<Scalars['String']>;
  phoneNbrTxt?: Maybe<Scalars['String']>;
  regionDistrictCd?: Maybe<Scalars['String']>;
  stateCd?: Maybe<Scalars['String']>;
  streetAddr1?: Maybe<Scalars['String']>;
  streetAddr2?: Maybe<Scalars['String']>;
  urlAddress?: Maybe<Scalars['String']>;
  withinCityLimitsInd?: Maybe<Scalars['String']>;
  zipCd?: Maybe<Scalars['String']>;
};

export type LocatorParticipations = {
  __typename?: 'LocatorParticipations';
  classCd?: Maybe<Scalars['String']>;
  locator?: Maybe<Locator>;
};

export type LoincCode = {
  __typename?: 'LoincCode';
  componentName?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['String']>;
  methodType?: Maybe<Scalars['String']>;
  property?: Maybe<Scalars['String']>;
  relatedClassCd?: Maybe<Scalars['String']>;
  systemCd?: Maybe<Scalars['String']>;
};

export type LoincLabTestResults = {
  __typename?: 'LoincLabTestResults';
  content: Array<Maybe<LoincCode>>;
  total: Scalars['Int'];
};

export type MaterialParticipation = {
  __typename?: 'MaterialParticipation';
  actUid?: Maybe<Scalars['Int']>;
  cd?: Maybe<Scalars['String']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  entityId?: Maybe<Scalars['String']>;
  participationLastChangeTime?: Maybe<Scalars['Date']>;
  participationRecordStatus?: Maybe<Scalars['String']>;
  subjectClassCd?: Maybe<Scalars['String']>;
  typeCd?: Maybe<Scalars['String']>;
  typeDescTxt?: Maybe<Scalars['String']>;
};

export type Mutation = {
  __typename?: 'Mutation';
  createPatient: Scalars['String'];
  updatePatient?: Maybe<UpdateResult>;
};


export type MutationCreatePatientArgs = {
  patient: PersonInput;
};


export type MutationUpdatePatientArgs = {
  id: Scalars['ID'];
  patient: PersonInput;
};

export type NbsEntity = {
  __typename?: 'NBSEntity';
  entityLocatorParticipations?: Maybe<Array<Maybe<LocatorParticipations>>>;
};

export type Name = {
  firstName?: InputMaybe<Scalars['String']>;
  lastName?: InputMaybe<Scalars['String']>;
  middleName?: InputMaybe<Scalars['String']>;
  nameUseCd: NameUseCd;
  suffix?: InputMaybe<Suffix>;
};

export enum NameUseCd {
  /**  Alias Name */
  A = 'A',
  Ad = 'AD',
  /**  Adopted Name */
  Al = 'AL',
  /**  Mother's Name */
  Br = 'BR',
  /**  Legal */
  C = 'C',
  /**  Coded Pseudo */
  I = 'I',
  /**  Indigenous/Tribal */
  L = 'L',
  /**  License */
  M = 'M',
  /**  Maiden Name */
  Mo = 'MO',
  /**  Name at Birth */
  P = 'P',
  /**  Name of Partner/Spouse */
  R = 'R',
  /**  Artist/Stage Name */
  S = 'S',
  /**  Religious */
  U = 'U'
}

export type NamedByContact = {
  __typename?: 'NamedByContact';
  associatedWith?: Maybe<PatientContactInvestigation>;
  condition?: Maybe<Scalars['String']>;
  contact: NamedContact;
  contactRecord: Scalars['ID'];
  createdOn: Scalars['Date'];
  event: Scalars['String'];
  namedOn: Scalars['Date'];
};

export type NamedByPatient = {
  __typename?: 'NamedByPatient';
  condition?: Maybe<Scalars['String']>;
  contact: NamedContact;
  contactRecord: Scalars['ID'];
  createdOn: Scalars['Date'];
  disposition?: Maybe<Scalars['String']>;
  event: Scalars['String'];
  namedOn: Scalars['Date'];
  priority?: Maybe<Scalars['String']>;
};

export type NamedContact = {
  __typename?: 'NamedContact';
  id: Scalars['ID'];
  name: Scalars['String'];
};

export enum NotificationStatus {
  Approved = 'APPROVED',
  Completed = 'COMPLETED',
  MessageFailed = 'MESSAGE_FAILED',
  PendingApproval = 'PENDING_APPROVAL',
  Rejected = 'REJECTED'
}

export type NotificationStatuses = {
  includeUnassigned: Scalars['Boolean'];
  statusList: Array<NotificationStatus>;
};

export type Observation = {
  __typename?: 'Observation';
  altCd?: Maybe<Scalars['String']>;
  altCdSystemCd?: Maybe<Scalars['String']>;
  altDescTxt?: Maybe<Scalars['String']>;
  cd?: Maybe<Scalars['String']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  displayName?: Maybe<Scalars['String']>;
  domainCd?: Maybe<Scalars['String']>;
  ovcAltCdSystemCd?: Maybe<Scalars['String']>;
  ovcAltCode?: Maybe<Scalars['String']>;
  ovcAltDescTxt?: Maybe<Scalars['String']>;
  ovcCode?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
};

export enum Operator {
  After = 'AFTER',
  Before = 'BEFORE',
  Equal = 'EQUAL'
}

export type Organization = {
  __typename?: 'Organization';
  addReasonCd?: Maybe<Scalars['String']>;
  addTime?: Maybe<Scalars['Date']>;
  addUserId?: Maybe<Scalars['ID']>;
  cd?: Maybe<Scalars['String']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  cityCd?: Maybe<Scalars['String']>;
  cityDescTxt?: Maybe<Scalars['String']>;
  cntryCd?: Maybe<Scalars['String']>;
  cntyCd?: Maybe<Scalars['String']>;
  description?: Maybe<Scalars['String']>;
  displayNm?: Maybe<Scalars['String']>;
  durationAmt?: Maybe<Scalars['String']>;
  durationUnitCd?: Maybe<Scalars['String']>;
  edxInd?: Maybe<Scalars['String']>;
  electronicInd?: Maybe<Scalars['String']>;
  fromTime?: Maybe<Scalars['Date']>;
  id?: Maybe<Scalars['ID']>;
  lastChgReasonCd?: Maybe<Scalars['String']>;
  lastChgTime?: Maybe<Scalars['Date']>;
  lastChgUserId?: Maybe<Scalars['Int']>;
  localId?: Maybe<Scalars['String']>;
  phoneCntryCd?: Maybe<Scalars['String']>;
  phoneNbr?: Maybe<Scalars['String']>;
  recordStatusCd?: Maybe<RecordStatus>;
  recordStatusTime?: Maybe<Scalars['Date']>;
  standardIndustryClassCd?: Maybe<Scalars['String']>;
  standardIndustryDescTxt?: Maybe<Scalars['String']>;
  stateCd?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['Date']>;
  streetAddr1?: Maybe<Scalars['String']>;
  streetAddr2?: Maybe<Scalars['String']>;
  toTime?: Maybe<Scalars['Date']>;
  userAffiliationTxt?: Maybe<Scalars['String']>;
  versionCtrlNbr?: Maybe<Scalars['Int']>;
  zipCd?: Maybe<Scalars['String']>;
};

export type OrganizationFilter = {
  cityCd?: InputMaybe<Scalars['String']>;
  cityDescTxt?: InputMaybe<Scalars['String']>;
  displayNm?: InputMaybe<Scalars['String']>;
  id?: InputMaybe<Scalars['ID']>;
  stateCd?: InputMaybe<Scalars['String']>;
  streetAddr1?: InputMaybe<Scalars['String']>;
  streetAddr2?: InputMaybe<Scalars['String']>;
  zipCd?: InputMaybe<Scalars['String']>;
};

export type OrganizationParticipation = {
  __typename?: 'OrganizationParticipation';
  actUid?: Maybe<Scalars['Int']>;
  entityId?: Maybe<Scalars['Int']>;
  name?: Maybe<Scalars['String']>;
  organizationLastChangeTime?: Maybe<Scalars['Date']>;
  participationLastChangeTime?: Maybe<Scalars['Date']>;
  participationRecordStatus?: Maybe<Scalars['String']>;
  subjectClassCd?: Maybe<Scalars['String']>;
  typeCd?: Maybe<Scalars['String']>;
  typeDescTxt?: Maybe<Scalars['String']>;
};

export type OrganizationResults = {
  __typename?: 'OrganizationResults';
  content: Array<Maybe<Organization>>;
  total: Scalars['Int'];
};

export type Outbreak = {
  __typename?: 'Outbreak';
  codeShortDescTxt?: Maybe<Scalars['String']>;
  id: OutbreakId;
};

export type OutbreakId = {
  __typename?: 'OutbreakId';
  code: Scalars['String'];
  codeSetNm: Scalars['String'];
};

export type OutbreakResults = {
  __typename?: 'OutbreakResults';
  content: Array<Maybe<Outbreak>>;
  total: Scalars['Int'];
};

export type Page = {
  pageNumber: Scalars['Int'];
  pageSize: Scalars['Int'];
};

export type PatientContactInvestigation = {
  __typename?: 'PatientContactInvestigation';
  condition: Scalars['String'];
  id: Scalars['ID'];
  local: Scalars['String'];
};

export type PatientContacts = {
  __typename?: 'PatientContacts';
  namedByContact?: Maybe<Array<Maybe<NamedByContact>>>;
  namedByPatient?: Maybe<Array<Maybe<NamedByPatient>>>;
  patient: Scalars['ID'];
};

export type PatientIdentificationTypeResults = {
  __typename?: 'PatientIdentificationTypeResults';
  content: Array<Maybe<IdentificationType>>;
  total: Scalars['Int'];
};

export type PatientTreatment = {
  __typename?: 'PatientTreatment';
  associatedWith: PatientTreatmentInvestigation;
  createdOn: Scalars['Date'];
  description: Scalars['String'];
  event: Scalars['String'];
  provider?: Maybe<Scalars['String']>;
  treatedOn: Scalars['Date'];
  treatment: Scalars['ID'];
};

export type PatientTreatmentInvestigation = {
  __typename?: 'PatientTreatmentInvestigation';
  condition: Scalars['String'];
  id: Scalars['ID'];
  local: Scalars['String'];
};

export type Person = {
  __typename?: 'Person';
  addReasonCd?: Maybe<Scalars['String']>;
  addTime?: Maybe<Scalars['Date']>;
  addUserId?: Maybe<Scalars['ID']>;
  additionalGenderCd?: Maybe<Gender>;
  administrativeGenderCd?: Maybe<Gender>;
  adultsInHouseNbr?: Maybe<Scalars['Int']>;
  ageCalc?: Maybe<Scalars['Int']>;
  ageCalcTime?: Maybe<Scalars['Date']>;
  ageCalcUnitCd?: Maybe<Scalars['String']>;
  ageCategoryCd?: Maybe<Scalars['String']>;
  ageReported?: Maybe<Scalars['String']>;
  ageReportedTime?: Maybe<Scalars['Date']>;
  ageReportedUnitCd?: Maybe<Scalars['String']>;
  asOfDateAdmin?: Maybe<Scalars['Date']>;
  asOfDateEthnicity?: Maybe<Scalars['Date']>;
  asOfDateGeneral?: Maybe<Scalars['Date']>;
  asOfDateMorbidity?: Maybe<Scalars['Date']>;
  asOfDateSex?: Maybe<Scalars['Date']>;
  birthCityCd?: Maybe<Scalars['String']>;
  birthCityDescTxt?: Maybe<Scalars['String']>;
  birthCntryCd?: Maybe<Scalars['String']>;
  birthGenderCd?: Maybe<Gender>;
  birthOrderNbr?: Maybe<Scalars['Int']>;
  birthStateCd?: Maybe<Scalars['String']>;
  birthTime?: Maybe<Scalars['Date']>;
  birthTimeCalc?: Maybe<Scalars['Date']>;
  cd?: Maybe<Scalars['String']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  cellPhoneNbr?: Maybe<Scalars['String']>;
  childrenInHouseNbr?: Maybe<Scalars['Int']>;
  currSexCd?: Maybe<Scalars['String']>;
  deceasedIndCd?: Maybe<Scalars['String']>;
  deceasedTime?: Maybe<Scalars['Date']>;
  dedupMatchInd?: Maybe<Scalars['String']>;
  description?: Maybe<Scalars['String']>;
  dlNum?: Maybe<Scalars['String']>;
  dlStateCd?: Maybe<Scalars['String']>;
  educationLevelCd?: Maybe<Scalars['String']>;
  educationLevelDescTxt?: Maybe<Scalars['String']>;
  edxInd?: Maybe<Scalars['String']>;
  eharsId?: Maybe<Scalars['String']>;
  electronicInd?: Maybe<Scalars['String']>;
  entityIds?: Maybe<Array<Maybe<PersonIdentification>>>;
  ethnicGroupDescTxt?: Maybe<Scalars['String']>;
  ethnicGroupInd?: Maybe<Scalars['String']>;
  ethnicGroupSeqNbr?: Maybe<Scalars['Int']>;
  ethnicUnkReasonCd?: Maybe<Scalars['String']>;
  ethnicityGroupCd?: Maybe<Scalars['String']>;
  firstNm?: Maybe<Scalars['String']>;
  groupNbr?: Maybe<Scalars['Int']>;
  groupTime?: Maybe<Scalars['Date']>;
  hmCityCd?: Maybe<Scalars['String']>;
  hmCityDescTxt?: Maybe<Scalars['String']>;
  hmCntryCd?: Maybe<Scalars['String']>;
  hmCntyCd?: Maybe<Scalars['String']>;
  hmEmailAddr?: Maybe<Scalars['String']>;
  hmPhoneCntryCd?: Maybe<Scalars['String']>;
  hmPhoneNbr?: Maybe<Scalars['String']>;
  hmStateCd?: Maybe<Scalars['String']>;
  hmStreetAddr1?: Maybe<Scalars['String']>;
  hmStreetAddr2?: Maybe<Scalars['String']>;
  hmZipCd?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['ID']>;
  lastChgReasonCd?: Maybe<Scalars['String']>;
  lastChgTime?: Maybe<Scalars['Date']>;
  lastChgUserId?: Maybe<Scalars['ID']>;
  lastNm?: Maybe<Scalars['String']>;
  localId?: Maybe<Scalars['String']>;
  maritalStatusCd?: Maybe<Scalars['String']>;
  maritalStatusDescTxt?: Maybe<Scalars['String']>;
  medicaidNum?: Maybe<Scalars['String']>;
  middleNm?: Maybe<Scalars['String']>;
  mothersMaidenNm?: Maybe<Scalars['String']>;
  multipleBirthInd?: Maybe<Scalars['String']>;
  names?: Maybe<Array<Maybe<PersonName>>>;
  nbsEntity: NbsEntity;
  nmPrefix?: Maybe<Scalars['String']>;
  nmSuffix?: Maybe<Scalars['String']>;
  occupationCd?: Maybe<Scalars['String']>;
  personParentUid?: Maybe<PersonParentUid>;
  preferredGenderCd?: Maybe<Gender>;
  preferredNm?: Maybe<Scalars['String']>;
  primLangCd?: Maybe<Scalars['String']>;
  primLangDescTxt?: Maybe<Scalars['String']>;
  raceCategoryCd?: Maybe<Scalars['String']>;
  raceCd?: Maybe<Scalars['String']>;
  raceDescTxt?: Maybe<Scalars['String']>;
  raceSeqNbr?: Maybe<Scalars['Int']>;
  recordStatusCd?: Maybe<RecordStatus>;
  recordStatusTime?: Maybe<Scalars['Date']>;
  sexUnkReasonCd?: Maybe<Scalars['String']>;
  speaksEnglishCd?: Maybe<Scalars['String']>;
  ssn?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['Date']>;
  survivedIndCd?: Maybe<Scalars['String']>;
  userAffiliationTxt?: Maybe<Scalars['String']>;
  versionCtrlNbr?: Maybe<Scalars['Int']>;
  wkCityCd?: Maybe<Scalars['String']>;
  wkCityDescTxt?: Maybe<Scalars['String']>;
  wkCntryCd?: Maybe<Scalars['String']>;
  wkCntyCd?: Maybe<Scalars['String']>;
  wkEmailAddr?: Maybe<Scalars['String']>;
  wkPhoneCntryCd?: Maybe<Scalars['String']>;
  wkPhoneNbr?: Maybe<Scalars['String']>;
  wkStateCd?: Maybe<Scalars['String']>;
  wkStreetAddr1?: Maybe<Scalars['String']>;
  wkStreetAddr2?: Maybe<Scalars['String']>;
  wkZipCd?: Maybe<Scalars['String']>;
};

export type PersonFilter = {
  address?: InputMaybe<Scalars['String']>;
  city?: InputMaybe<Scalars['String']>;
  country?: InputMaybe<Scalars['String']>;
  dateOfBirth?: InputMaybe<Scalars['Date']>;
  dateOfBirthOperator?: InputMaybe<Operator>;
  deceased?: InputMaybe<Deceased>;
  email?: InputMaybe<Scalars['String']>;
  ethnicity?: InputMaybe<Scalars['String']>;
  firstName?: InputMaybe<Scalars['String']>;
  gender?: InputMaybe<Gender>;
  id?: InputMaybe<Scalars['ID']>;
  identification?: InputMaybe<Identification>;
  lastName?: InputMaybe<Scalars['String']>;
  mortalityStatus?: InputMaybe<Scalars['String']>;
  phoneNumber?: InputMaybe<Scalars['String']>;
  race?: InputMaybe<Scalars['String']>;
  recordStatus: Array<RecordStatus>;
  ssn?: InputMaybe<Scalars['String']>;
  state?: InputMaybe<Scalars['String']>;
  treatmentId?: InputMaybe<Scalars['String']>;
  vaccinationId?: InputMaybe<Scalars['String']>;
  zip?: InputMaybe<Scalars['String']>;
};

export type PersonIdentification = {
  __typename?: 'PersonIdentification';
  assigningAuthorityCd?: Maybe<Scalars['String']>;
  assigningAuthorityDescTxt?: Maybe<Scalars['String']>;
  rootExtensionTxt?: Maybe<Scalars['String']>;
  typeCd?: Maybe<Scalars['String']>;
  typeDescTxt?: Maybe<Scalars['String']>;
};

export type PersonInput = {
  DateOfBirth?: InputMaybe<Scalars['Date']>;
  addresses?: InputMaybe<Array<InputMaybe<PostalAddress>>>;
  asOf?: InputMaybe<Scalars['Date']>;
  birthGender?: InputMaybe<Gender>;
  comments?: InputMaybe<Scalars['String']>;
  currentGender?: InputMaybe<Gender>;
  deceased?: InputMaybe<Deceased>;
  deceasedTime?: InputMaybe<Scalars['Date']>;
  emailAddresses?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  ethnicityCode?: InputMaybe<Scalars['String']>;
  identifications?: InputMaybe<Array<InputMaybe<Identification>>>;
  maritalStatus?: InputMaybe<Scalars['String']>;
  names?: InputMaybe<Array<InputMaybe<Name>>>;
  phoneNumbers?: InputMaybe<Array<InputMaybe<PhoneNumber>>>;
  raceCodes?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  ssn?: InputMaybe<Scalars['String']>;
};

export type PersonName = {
  __typename?: 'PersonName';
  firstNm?: Maybe<Scalars['String']>;
  lastNm?: Maybe<Scalars['String']>;
  middleNm?: Maybe<Scalars['String']>;
  nmPrefix?: Maybe<Scalars['String']>;
  nmSuffix?: Maybe<Scalars['String']>;
};

export type PersonParticipation = {
  __typename?: 'PersonParticipation';
  actUid: Scalars['Int'];
  birthTime?: Maybe<Scalars['Date']>;
  currSexCd?: Maybe<Scalars['String']>;
  entityId: Scalars['Int'];
  firstName?: Maybe<Scalars['String']>;
  lastName?: Maybe<Scalars['String']>;
  localId?: Maybe<Scalars['String']>;
  participationLastChangeTime?: Maybe<Scalars['Date']>;
  participationRecordStatus?: Maybe<Scalars['String']>;
  personCd: Scalars['String'];
  personLastChangeTime?: Maybe<Scalars['Date']>;
  personParentUid?: Maybe<Scalars['Int']>;
  personRecordStatus: Scalars['String'];
  subjectClassCd?: Maybe<Scalars['String']>;
  typeCd?: Maybe<Scalars['String']>;
  typeDescTxt?: Maybe<Scalars['String']>;
};

export type PersonResults = {
  __typename?: 'PersonResults';
  content: Array<Person>;
  total: Scalars['Int'];
};

export type PhoneNumber = {
  extension?: InputMaybe<Scalars['String']>;
  number: Scalars['String'];
  phoneType: PhoneType;
};

export enum PhoneType {
  Cell = 'CELL',
  Home = 'HOME',
  Work = 'WORK'
}

export type Place = {
  __typename?: 'Place';
  addReasonCd?: Maybe<Scalars['String']>;
  addTime?: Maybe<Scalars['Date']>;
  addUserId?: Maybe<Scalars['Int']>;
  cd?: Maybe<Scalars['String']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  cityCd?: Maybe<Scalars['String']>;
  cityDescTxt?: Maybe<Scalars['String']>;
  cntryCd?: Maybe<Scalars['String']>;
  cntyCd?: Maybe<Scalars['String']>;
  description?: Maybe<Scalars['String']>;
  durationAmt?: Maybe<Scalars['String']>;
  durationUnitCd?: Maybe<Scalars['String']>;
  fromTime?: Maybe<Scalars['Date']>;
  id?: Maybe<Scalars['ID']>;
  lastChgReasonCd?: Maybe<Scalars['String']>;
  lastChgTime?: Maybe<Scalars['Date']>;
  lastChgUserId?: Maybe<Scalars['Int']>;
  localId?: Maybe<Scalars['String']>;
  nm?: Maybe<Scalars['String']>;
  phoneCntryCd?: Maybe<Scalars['String']>;
  phoneNbr?: Maybe<Scalars['String']>;
  recordStatusCd?: Maybe<Scalars['String']>;
  recordStatusTime?: Maybe<Scalars['Date']>;
  stateCd?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['Date']>;
  streetAddr1?: Maybe<Scalars['String']>;
  streetAddr2?: Maybe<Scalars['String']>;
  toTime?: Maybe<Scalars['Date']>;
  userAffiliationTxt?: Maybe<Scalars['String']>;
  versionCtrlNbr?: Maybe<Scalars['Int']>;
  zipCd?: Maybe<Scalars['String']>;
};

export type PlaceFilter = {
  cityCd?: InputMaybe<Scalars['String']>;
  cityDescTxt?: InputMaybe<Scalars['String']>;
  description?: InputMaybe<Scalars['String']>;
  id?: InputMaybe<Scalars['ID']>;
  nm?: InputMaybe<Scalars['String']>;
  stateCd?: InputMaybe<Scalars['String']>;
  streetAddr1?: InputMaybe<Scalars['String']>;
  streetAddr2?: InputMaybe<Scalars['String']>;
  zipCd?: InputMaybe<Scalars['String']>;
};

export type PostalAddress = {
  censusTract?: InputMaybe<Scalars['String']>;
  city?: InputMaybe<Scalars['String']>;
  countryCode?: InputMaybe<Scalars['String']>;
  countyCode?: InputMaybe<Scalars['String']>;
  stateCode?: InputMaybe<Scalars['String']>;
  streetAddress1?: InputMaybe<Scalars['String']>;
  streetAddress2?: InputMaybe<Scalars['String']>;
  zip?: InputMaybe<Scalars['String']>;
};

export enum PregnancyStatus {
  No = 'NO',
  Unknown = 'UNKNOWN',
  Yes = 'YES'
}

export enum ProcessingStatus {
  AwaitingInterview = 'AWAITING_INTERVIEW',
  ClosedCase = 'CLOSED_CASE',
  FieldFollowUp = 'FIELD_FOLLOW_UP',
  NoFollowUp = 'NO_FOLLOW_UP',
  OpenCase = 'OPEN_CASE',
  SurveillanceFollowUp = 'SURVEILLANCE_FOLLOW_UP'
}

export type ProcessingStatuses = {
  includeUnassigned: Scalars['Boolean'];
  statusList: Array<ProcessingStatus>;
};

export type ProgramAreaCode = {
  __typename?: 'ProgramAreaCode';
  codeSeq?: Maybe<Scalars['Int']>;
  codeSetNm?: Maybe<Scalars['String']>;
  id: Scalars['String'];
  nbsUid?: Maybe<Scalars['ID']>;
  progAreaDescTxt?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['Date']>;
};

export type ProviderFacilitySearch = {
  entityType: ReportingEntityType;
  id: Scalars['ID'];
};

export enum ProviderType {
  OrderingFacility = 'ORDERING_FACILITY',
  OrderingProvider = 'ORDERING_PROVIDER',
  ReportingFacility = 'REPORTING_FACILITY'
}

export type Query = {
  __typename?: 'Query';
  findAllConditionCodes: Array<Maybe<ConditionCode>>;
  findAllCountryCodes: Array<Maybe<CountryCode>>;
  findAllCountyCodesForState: Array<Maybe<CountyCode>>;
  findAllEthnicityValues: EthnicityResults;
  findAllJurisdictions: Array<Maybe<Jurisdiction>>;
  findAllOrganizations: OrganizationResults;
  findAllOutbreaks: OutbreakResults;
  findAllPatientIdentificationTypes: PatientIdentificationTypeResults;
  findAllPatients: PersonResults;
  findAllPlaces: Array<Maybe<Place>>;
  findAllProgramAreas: Array<Maybe<ProgramAreaCode>>;
  findAllRaceValues: RaceResults;
  findAllStateCodes: Array<Maybe<StateCode>>;
  findAllUsers: UserResults;
  findContactsForPatient?: Maybe<PatientContacts>;
  findDocumentsRequiringReviewForPatient: LabReportResults;
  findInvestigationsByFilter: InvestigationResults;
  findLabReportsByFilter: LabReportResults;
  findLocalCodedResults: LocalCodedResults;
  findLocalLabTest: LocalLabTestResults;
  findLoincLabTest: LoincLabTestResults;
  findOpenInvestigationsForPatient: InvestigationResults;
  findOrganizationById?: Maybe<Organization>;
  findOrganizationsByFilter: OrganizationResults;
  findPatientById?: Maybe<Person>;
  findPatientsByFilter: PersonResults;
  findPatientsByOrganizationFilter: PersonResults;
  findPlaceById?: Maybe<Place>;
  findPlacesByFilter: Array<Maybe<Place>>;
  findSnomedCodedResults: SnomedCodedResults;
  findTreatmentsForPatient?: Maybe<Array<Maybe<PatientTreatment>>>;
};


export type QueryFindAllConditionCodesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllCountryCodesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllCountyCodesForStateArgs = {
  page?: InputMaybe<Page>;
  stateCode: Scalars['String'];
};


export type QueryFindAllEthnicityValuesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllJurisdictionsArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllOrganizationsArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllOutbreaksArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllPatientIdentificationTypesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllPatientsArgs = {
  page?: InputMaybe<SortablePage>;
};


export type QueryFindAllPlacesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllProgramAreasArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllRaceValuesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllStateCodesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllUsersArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindContactsForPatientArgs = {
  patient: Scalars['ID'];
};


export type QueryFindDocumentsRequiringReviewForPatientArgs = {
  page?: InputMaybe<Page>;
  patientId: Scalars['Int'];
};


export type QueryFindInvestigationsByFilterArgs = {
  filter: InvestigationFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindLabReportsByFilterArgs = {
  filter: LabReportFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindLocalCodedResultsArgs = {
  page?: InputMaybe<Page>;
  searchText: Scalars['String'];
};


export type QueryFindLocalLabTestArgs = {
  page?: InputMaybe<Page>;
  searchText: Scalars['String'];
};


export type QueryFindLoincLabTestArgs = {
  page?: InputMaybe<Page>;
  searchText: Scalars['String'];
};


export type QueryFindOpenInvestigationsForPatientArgs = {
  page?: InputMaybe<Page>;
  patientId: Scalars['Int'];
};


export type QueryFindOrganizationByIdArgs = {
  id: Scalars['ID'];
};


export type QueryFindOrganizationsByFilterArgs = {
  filter: OrganizationFilter;
  page?: InputMaybe<Page>;
};


export type QueryFindPatientByIdArgs = {
  id: Scalars['ID'];
};


export type QueryFindPatientsByFilterArgs = {
  filter: PersonFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindPatientsByOrganizationFilterArgs = {
  filter: OrganizationFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindPlaceByIdArgs = {
  id: Scalars['ID'];
};


export type QueryFindPlacesByFilterArgs = {
  filter: PlaceFilter;
  page?: InputMaybe<Page>;
};


export type QueryFindSnomedCodedResultsArgs = {
  page?: InputMaybe<Page>;
  searchText: Scalars['String'];
};


export type QueryFindTreatmentsForPatientArgs = {
  patient: Scalars['ID'];
};

export type Race = {
  __typename?: 'Race';
  codeDescTxt: Scalars['String'];
  id: RaceId;
};

export type RaceId = {
  __typename?: 'RaceId';
  code: Scalars['String'];
};

export type RaceResults = {
  __typename?: 'RaceResults';
  content: Array<Maybe<Race>>;
  total: Scalars['Int'];
};

export enum RecordStatus {
  Active = 'ACTIVE',
  Inactive = 'INACTIVE',
  LogDel = 'LOG_DEL',
  Superceded = 'SUPERCEDED'
}

export enum ReportingEntityType {
  Facility = 'FACILITY',
  Provider = 'PROVIDER'
}

export type SnomedCode = {
  __typename?: 'SnomedCode';
  id?: Maybe<Scalars['String']>;
  snomedDescTxt?: Maybe<Scalars['String']>;
};

export type SnomedCodedResults = {
  __typename?: 'SnomedCodedResults';
  content: Array<Maybe<SnomedCode>>;
  total: Scalars['Int'];
};

export enum SortDirection {
  Asc = 'ASC',
  Desc = 'DESC'
}

export enum SortField {
  BirthTime = 'birthTime',
  LastNm = 'lastNm'
}

export type SortablePage = {
  pageNumber?: InputMaybe<Scalars['Int']>;
  pageSize?: InputMaybe<Scalars['Int']>;
  sortDirection?: InputMaybe<SortDirection>;
  sortField?: InputMaybe<SortField>;
};

export type StateCode = {
  __typename?: 'StateCode';
  assigningAuthorityCd?: Maybe<Scalars['String']>;
  assigningAuthorityDescTxt?: Maybe<Scalars['String']>;
  codeDescTxt?: Maybe<Scalars['String']>;
  codeSetNm?: Maybe<Scalars['String']>;
  codeSystemCd?: Maybe<Scalars['String']>;
  codeSystemDescTxt?: Maybe<Scalars['String']>;
  effectiveFromTime?: Maybe<Scalars['Date']>;
  effectiveToTime?: Maybe<Scalars['Date']>;
  excludedTxt?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['String']>;
  indentLevelNbr?: Maybe<Scalars['Int']>;
  isModifiableInd?: Maybe<Scalars['String']>;
  keyInfoTxt?: Maybe<Scalars['String']>;
  nbsUid?: Maybe<Scalars['Int']>;
  parentIsCd?: Maybe<Scalars['String']>;
  seqNum?: Maybe<Scalars['Int']>;
  sourceConceptId?: Maybe<Scalars['String']>;
  stateNm?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['Date']>;
};

export enum Suffix {
  Esq = 'ESQ',
  Ii = 'II',
  Iii = 'III',
  Iv = 'IV',
  Jr = 'JR',
  Sr = 'SR',
  V = 'V'
}

export type UpdateResult = {
  __typename?: 'UpdateResult';
  requestId?: Maybe<Scalars['String']>;
  updatedPerson?: Maybe<Person>;
};

export type User = {
  __typename?: 'User';
  nedssEntryId: Scalars['ID'];
  recordStatusCd?: Maybe<RecordStatus>;
  userFirstNm: Scalars['String'];
  userId: Scalars['String'];
  userLastNm: Scalars['String'];
};

export type UserResults = {
  __typename?: 'UserResults';
  content: Array<Maybe<User>>;
  total: Scalars['Int'];
};

export enum UserType {
  External = 'EXTERNAL',
  Internal = 'INTERNAL'
}

export type PersonParentUid = {
  __typename?: 'personParentUid';
  id?: Maybe<Scalars['ID']>;
};

export type CreatePatientMutationVariables = Exact<{
  patient: PersonInput;
}>;


export type CreatePatientMutation = { __typename?: 'Mutation', createPatient: string };

export type UpdatePatientMutationVariables = Exact<{
  id: Scalars['ID'];
  patient: PersonInput;
}>;


export type UpdatePatientMutation = { __typename?: 'Mutation', updatePatient?: { __typename?: 'UpdateResult', requestId?: string | null, updatedPerson?: { __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null } | null } | null };

export type FindAllConditionCodesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllConditionCodesQuery = { __typename?: 'Query', findAllConditionCodes: Array<{ __typename?: 'ConditionCode', id: string, conditionDescTxt?: string | null } | null> };

export type FindAllCountryCodesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllCountryCodesQuery = { __typename?: 'Query', findAllCountryCodes: Array<{ __typename?: 'CountryCode', id?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, codeDescTxt?: string | null, codeShortDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, excludedTxt?: string | null, keyInfoTxt?: string | null, indentLevelNbr?: number | null, isModifiableInd?: string | null, parentIsCd?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, seqNum?: number | null, nbsUid?: number | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null } | null> };

export type FindAllCountyCodesForStateQueryVariables = Exact<{
  stateCode: Scalars['String'];
  page?: InputMaybe<Page>;
}>;


export type FindAllCountyCodesForStateQuery = { __typename?: 'Query', findAllCountyCodesForState: Array<{ __typename?: 'CountyCode', id: string, codeDescTxt?: string | null, codeShortDescTxt?: string | null } | null> };

export type FindAllEthnicityValuesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllEthnicityValuesQuery = { __typename?: 'Query', findAllEthnicityValues: { __typename?: 'EthnicityResults', total: number, content: Array<{ __typename?: 'Ethnicity', codeDescTxt: string, id: { __typename?: 'EthnicityId', code: string } } | null> } };

export type FindAllJurisdictionsQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllJurisdictionsQuery = { __typename?: 'Query', findAllJurisdictions: Array<{ __typename?: 'Jurisdiction', id: string, typeCd: string, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, codeDescTxt?: string | null, codeShortDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, indentLevelNbr?: number | null, isModifiableInd?: string | null, parentIsCd?: string | null, stateDomainCd?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, codeSeqNum?: number | null, nbsUid?: string | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null, exportInd?: string | null } | null> };

export type FindAllOrganizationsQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllOrganizationsQuery = { __typename?: 'Query', findAllOrganizations: { __typename?: 'OrganizationResults', total: number, content: Array<{ __typename?: 'Organization', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, standardIndustryClassCd?: string | null, standardIndustryDescTxt?: string | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, displayNm?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, zipCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null, electronicInd?: string | null, edxInd?: string | null } | null> } };

export type FindAllOutbreaksQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllOutbreaksQuery = { __typename?: 'Query', findAllOutbreaks: { __typename?: 'OutbreakResults', total: number, content: Array<{ __typename?: 'Outbreak', codeShortDescTxt?: string | null, id: { __typename?: 'OutbreakId', codeSetNm: string, code: string } } | null> } };

export type FindAllPatientIdentificationTypesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllPatientIdentificationTypesQuery = { __typename?: 'Query', findAllPatientIdentificationTypes: { __typename?: 'PatientIdentificationTypeResults', total: number, content: Array<{ __typename?: 'IdentificationType', codeDescTxt: string, id: { __typename?: 'IdentificationTypeId', code: string } } | null> } };

export type FindAllPatientsQueryVariables = Exact<{
  page?: InputMaybe<SortablePage>;
}>;


export type FindAllPatientsQuery = { __typename?: 'Query', findAllPatients: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null }> } };

export type FindAllPlacesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllPlacesQuery = { __typename?: 'Query', findAllPlaces: Array<{ __typename?: 'Place', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: number | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, nm?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, zipCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null } | null> };

export type FindAllProgramAreasQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllProgramAreasQuery = { __typename?: 'Query', findAllProgramAreas: Array<{ __typename?: 'ProgramAreaCode', id: string, progAreaDescTxt?: string | null, nbsUid?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, codeSeq?: number | null } | null> };

export type FindAllRaceValuesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllRaceValuesQuery = { __typename?: 'Query', findAllRaceValues: { __typename?: 'RaceResults', total: number, content: Array<{ __typename?: 'Race', codeDescTxt: string, id: { __typename?: 'RaceId', code: string } } | null> } };

export type FindAllStateCodesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllStateCodesQuery = { __typename?: 'Query', findAllStateCodes: Array<{ __typename?: 'StateCode', id?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, stateNm?: string | null, codeDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, excludedTxt?: string | null, indentLevelNbr?: number | null, isModifiableInd?: string | null, keyInfoTxt?: string | null, parentIsCd?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, seqNum?: number | null, nbsUid?: number | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null } | null> };

export type FindAllUsersQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllUsersQuery = { __typename?: 'Query', findAllUsers: { __typename?: 'UserResults', total: number, content: Array<{ __typename?: 'User', nedssEntryId: string, userId: string, userFirstNm: string, userLastNm: string, recordStatusCd?: RecordStatus | null } | null> } };

export type FindContactsForPatientQueryVariables = Exact<{
  patient: Scalars['ID'];
}>;


export type FindContactsForPatientQuery = { __typename?: 'Query', findContactsForPatient?: { __typename?: 'PatientContacts', patient: string, namedByPatient?: Array<{ __typename?: 'NamedByPatient', contactRecord: string, createdOn: any, condition?: string | null, namedOn: any, priority?: string | null, disposition?: string | null, event: string, contact: { __typename?: 'NamedContact', id: string, name: string } } | null> | null, namedByContact?: Array<{ __typename?: 'NamedByContact', contactRecord: string, createdOn: any, namedOn: any, condition?: string | null, event: string, contact: { __typename?: 'NamedContact', id: string, name: string }, associatedWith?: { __typename?: 'PatientContactInvestigation', id: string, local: string, condition: string } | null } | null> | null } | null };

export type FindDocumentsRequiringReviewForPatientQueryVariables = Exact<{
  patientId: Scalars['Int'];
  page?: InputMaybe<Page>;
}>;


export type FindDocumentsRequiringReviewForPatientQuery = { __typename?: 'Query', findDocumentsRequiringReviewForPatient: { __typename?: 'LabReportResults', total: number, content: Array<{ __typename?: 'LabReport', id?: string | null, observationUid?: number | null, lastChange?: any | null, classCd?: string | null, moodCd?: string | null, observationLastChgTime?: any | null, cdDescTxt?: string | null, recordStatusCd?: string | null, programAreaCd?: string | null, jurisdictionCd?: number | null, jurisdictionCodeDescTxt?: string | null, pregnantIndCd?: string | null, localId?: string | null, activityToTime?: any | null, effectiveFromTime?: any | null, rptToStateTime?: any | null, addTime?: any | null, electronicInd?: string | null, versionCtrlNbr?: number | null, addUserId?: number | null, lastChgUserId?: number | null, personParticipations?: Array<{ __typename?: 'PersonParticipation', actUid: number, localId?: string | null, typeCd?: string | null, entityId: number, subjectClassCd?: string | null, participationRecordStatus?: string | null, typeDescTxt?: string | null, participationLastChangeTime?: any | null, firstName?: string | null, lastName?: string | null, birthTime?: any | null, currSexCd?: string | null, personCd: string, personParentUid?: number | null, personRecordStatus: string, personLastChangeTime?: any | null } | null> | null, organizationParticipations?: Array<{ __typename?: 'OrganizationParticipation', actUid?: number | null, typeCd?: string | null, entityId?: number | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, name?: string | null, organizationLastChangeTime?: any | null } | null> | null, materialParticipations?: Array<{ __typename?: 'MaterialParticipation', actUid?: number | null, typeCd?: string | null, entityId?: string | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, cd?: string | null, cdDescTxt?: string | null } | null> | null, observations?: Array<{ __typename?: 'Observation', cd?: string | null, cdDescTxt?: string | null, domainCd?: string | null, statusCd?: string | null, altCd?: string | null, altDescTxt?: string | null, altCdSystemCd?: string | null, displayName?: string | null, ovcCode?: string | null, ovcAltCode?: string | null, ovcAltDescTxt?: string | null, ovcAltCdSystemCd?: string | null } | null> | null, actIds?: Array<{ __typename?: 'ActId', id?: number | null, recordStatus?: string | null, actIdSeq?: number | null, rootExtensionTxt?: string | null, typeCd?: string | null, lastChangeTime?: any | null } | null> | null, associatedInvestigations?: Array<{ __typename?: 'AssociatedInvestigation', publicHealthCaseUid?: number | null, cdDescTxt?: string | null, localId?: string | null, lastChgTime?: any | null, actRelationshipLastChgTime?: any | null } | null> | null } | null> } };

export type FindInvestigationsByFilterQueryVariables = Exact<{
  filter: InvestigationFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindInvestigationsByFilterQuery = { __typename?: 'Query', findInvestigationsByFilter: { __typename?: 'InvestigationResults', total: number, content: Array<{ __typename?: 'Investigation', id?: string | null, recordStatus?: string | null, lastChangeTime?: any | null, publicHealthCaseUid?: number | null, caseClassCd?: string | null, outbreakName?: string | null, caseTypeCd?: string | null, cdDescTxt?: string | null, progAreaCd?: string | null, jurisdictionCd?: number | null, jurisdictionCodeDescTxt?: string | null, pregnantIndCd?: string | null, localId?: string | null, rptFormCmpltTime?: any | null, activityToTime?: any | null, activityFromTime?: any | null, addTime?: any | null, publicHealthCaseLastChgTime?: any | null, addUserId?: number | null, lastChangeUserId?: number | null, currProcessStateCd?: string | null, investigationStatusCd?: string | null, moodCd?: string | null, notificationLocalId?: string | null, notificationAddTime?: any | null, notificationRecordStatusCd?: string | null, notificationLastChgTime?: any | null, personParticipations?: Array<{ __typename?: 'PersonParticipation', actUid: number, localId?: string | null, typeCd?: string | null, entityId: number, subjectClassCd?: string | null, participationRecordStatus?: string | null, typeDescTxt?: string | null, participationLastChangeTime?: any | null, firstName?: string | null, lastName?: string | null, birthTime?: any | null, currSexCd?: string | null, personCd: string, personParentUid?: number | null, personRecordStatus: string, personLastChangeTime?: any | null } | null> | null, organizationParticipations?: Array<{ __typename?: 'OrganizationParticipation', actUid?: number | null, typeCd?: string | null, entityId?: number | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, name?: string | null, organizationLastChangeTime?: any | null } | null> | null, actIds?: Array<{ __typename?: 'ActId', id?: number | null, recordStatus?: string | null, actIdSeq?: number | null, rootExtensionTxt?: string | null, typeCd?: string | null, lastChangeTime?: any | null } | null> | null } | null> } };

export type FindLabReportsByFilterQueryVariables = Exact<{
  filter: LabReportFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindLabReportsByFilterQuery = { __typename?: 'Query', findLabReportsByFilter: { __typename?: 'LabReportResults', total: number, content: Array<{ __typename?: 'LabReport', id?: string | null, observationUid?: number | null, lastChange?: any | null, classCd?: string | null, moodCd?: string | null, observationLastChgTime?: any | null, cdDescTxt?: string | null, recordStatusCd?: string | null, programAreaCd?: string | null, jurisdictionCd?: number | null, jurisdictionCodeDescTxt?: string | null, pregnantIndCd?: string | null, localId?: string | null, activityToTime?: any | null, effectiveFromTime?: any | null, rptToStateTime?: any | null, addTime?: any | null, electronicInd?: string | null, versionCtrlNbr?: number | null, addUserId?: number | null, lastChgUserId?: number | null, personParticipations?: Array<{ __typename?: 'PersonParticipation', actUid: number, localId?: string | null, typeCd?: string | null, entityId: number, subjectClassCd?: string | null, participationRecordStatus?: string | null, typeDescTxt?: string | null, participationLastChangeTime?: any | null, firstName?: string | null, lastName?: string | null, birthTime?: any | null, currSexCd?: string | null, personCd: string, personParentUid?: number | null, personRecordStatus: string, personLastChangeTime?: any | null } | null> | null, organizationParticipations?: Array<{ __typename?: 'OrganizationParticipation', actUid?: number | null, typeCd?: string | null, entityId?: number | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, name?: string | null, organizationLastChangeTime?: any | null } | null> | null, materialParticipations?: Array<{ __typename?: 'MaterialParticipation', actUid?: number | null, typeCd?: string | null, entityId?: string | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, cd?: string | null, cdDescTxt?: string | null } | null> | null, observations?: Array<{ __typename?: 'Observation', cd?: string | null, cdDescTxt?: string | null, domainCd?: string | null, statusCd?: string | null, altCd?: string | null, altDescTxt?: string | null, altCdSystemCd?: string | null, displayName?: string | null, ovcCode?: string | null, ovcAltCode?: string | null, ovcAltDescTxt?: string | null, ovcAltCdSystemCd?: string | null } | null> | null, actIds?: Array<{ __typename?: 'ActId', id?: number | null, recordStatus?: string | null, actIdSeq?: number | null, rootExtensionTxt?: string | null, typeCd?: string | null, lastChangeTime?: any | null } | null> | null, associatedInvestigations?: Array<{ __typename?: 'AssociatedInvestigation', publicHealthCaseUid?: number | null, cdDescTxt?: string | null, localId?: string | null, lastChgTime?: any | null, actRelationshipLastChgTime?: any | null } | null> | null } | null> } };

export type FindLocalCodedResultsQueryVariables = Exact<{
  searchText: Scalars['String'];
  page?: InputMaybe<Page>;
}>;


export type FindLocalCodedResultsQuery = { __typename?: 'Query', findLocalCodedResults: { __typename?: 'LocalCodedResults', total: number, content: Array<{ __typename?: 'LabResult', nbsUid?: string | null, labResultDescTxt?: string | null, id?: { __typename?: 'LabResultId', labResultCd?: string | null, laboratoryId?: string | null } | null } | null> } };

export type FindLocalLabTestQueryVariables = Exact<{
  searchText: Scalars['String'];
  page?: InputMaybe<Page>;
}>;


export type FindLocalLabTestQuery = { __typename?: 'Query', findLocalLabTest: { __typename?: 'LocalLabTestResults', total: number, content: Array<{ __typename?: 'LabTest', labTestDescTxt?: string | null, organismResultTestInd?: string | null, id?: { __typename?: 'LabTestId', labTestCd?: string | null, laboratoryId?: string | null } | null } | null> } };

export type FindLoincLabTestQueryVariables = Exact<{
  searchText: Scalars['String'];
  page?: InputMaybe<Page>;
}>;


export type FindLoincLabTestQuery = { __typename?: 'Query', findLoincLabTest: { __typename?: 'LoincLabTestResults', total: number, content: Array<{ __typename?: 'LoincCode', id?: string | null, componentName?: string | null, methodType?: string | null, systemCd?: string | null, property?: string | null, relatedClassCd?: string | null } | null> } };

export type FindOpenInvestigationsForPatientQueryVariables = Exact<{
  patientId: Scalars['Int'];
  page?: InputMaybe<Page>;
}>;


export type FindOpenInvestigationsForPatientQuery = { __typename?: 'Query', findOpenInvestigationsForPatient: { __typename?: 'InvestigationResults', total: number, content: Array<{ __typename?: 'Investigation', id?: string | null, recordStatus?: string | null, lastChangeTime?: any | null, publicHealthCaseUid?: number | null, caseClassCd?: string | null, outbreakName?: string | null, caseTypeCd?: string | null, cdDescTxt?: string | null, progAreaCd?: string | null, jurisdictionCd?: number | null, jurisdictionCodeDescTxt?: string | null, pregnantIndCd?: string | null, localId?: string | null, rptFormCmpltTime?: any | null, activityToTime?: any | null, activityFromTime?: any | null, addTime?: any | null, publicHealthCaseLastChgTime?: any | null, addUserId?: number | null, lastChangeUserId?: number | null, currProcessStateCd?: string | null, investigationStatusCd?: string | null, moodCd?: string | null, notificationLocalId?: string | null, notificationAddTime?: any | null, notificationRecordStatusCd?: string | null, notificationLastChgTime?: any | null, personParticipations?: Array<{ __typename?: 'PersonParticipation', actUid: number, localId?: string | null, typeCd?: string | null, entityId: number, subjectClassCd?: string | null, participationRecordStatus?: string | null, typeDescTxt?: string | null, participationLastChangeTime?: any | null, firstName?: string | null, lastName?: string | null, birthTime?: any | null, currSexCd?: string | null, personCd: string, personParentUid?: number | null, personRecordStatus: string, personLastChangeTime?: any | null } | null> | null, organizationParticipations?: Array<{ __typename?: 'OrganizationParticipation', actUid?: number | null, typeCd?: string | null, entityId?: number | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, name?: string | null, organizationLastChangeTime?: any | null } | null> | null, actIds?: Array<{ __typename?: 'ActId', id?: number | null, recordStatus?: string | null, actIdSeq?: number | null, rootExtensionTxt?: string | null, typeCd?: string | null, lastChangeTime?: any | null } | null> | null } | null> } };

export type FindOrganizationByIdQueryVariables = Exact<{
  id: Scalars['ID'];
}>;


export type FindOrganizationByIdQuery = { __typename?: 'Query', findOrganizationById?: { __typename?: 'Organization', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, standardIndustryClassCd?: string | null, standardIndustryDescTxt?: string | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, displayNm?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, zipCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null, electronicInd?: string | null, edxInd?: string | null } | null };

export type FindOrganizationsByFilterQueryVariables = Exact<{
  filter: OrganizationFilter;
  page?: InputMaybe<Page>;
}>;


export type FindOrganizationsByFilterQuery = { __typename?: 'Query', findOrganizationsByFilter: { __typename?: 'OrganizationResults', total: number, content: Array<{ __typename?: 'Organization', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, standardIndustryClassCd?: string | null, standardIndustryDescTxt?: string | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, displayNm?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, zipCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null, electronicInd?: string | null, edxInd?: string | null } | null> } };

export type FindPatientByIdQueryVariables = Exact<{
  id: Scalars['ID'];
}>;


export type FindPatientByIdQuery = { __typename?: 'Query', findPatientById?: { __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null } | null };

export type FindPatientsByFilterQueryVariables = Exact<{
  filter: PersonFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindPatientsByFilterQuery = { __typename?: 'Query', findPatientsByFilter: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null }> } };

export type FindPatientsByOrganizationFilterQueryVariables = Exact<{
  filter: OrganizationFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindPatientsByOrganizationFilterQuery = { __typename?: 'Query', findPatientsByOrganizationFilter: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null }> } };

export type FindPlaceByIdQueryVariables = Exact<{
  id: Scalars['ID'];
}>;


export type FindPlaceByIdQuery = { __typename?: 'Query', findPlaceById?: { __typename?: 'Place', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: number | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, nm?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, zipCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null } | null };

export type FindPlacesByFilterQueryVariables = Exact<{
  filter: PlaceFilter;
  page?: InputMaybe<Page>;
}>;


export type FindPlacesByFilterQuery = { __typename?: 'Query', findPlacesByFilter: Array<{ __typename?: 'Place', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: number | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, nm?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, zipCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null } | null> };

export type FindSnomedCodedResultsQueryVariables = Exact<{
  searchText: Scalars['String'];
  page?: InputMaybe<Page>;
}>;


export type FindSnomedCodedResultsQuery = { __typename?: 'Query', findSnomedCodedResults: { __typename?: 'SnomedCodedResults', total: number, content: Array<{ __typename?: 'SnomedCode', id?: string | null, snomedDescTxt?: string | null } | null> } };

export type FindTreatmentsForPatientQueryVariables = Exact<{
  patient: Scalars['ID'];
}>;


export type FindTreatmentsForPatientQuery = { __typename?: 'Query', findTreatmentsForPatient?: Array<{ __typename?: 'PatientTreatment', treatment: string, createdOn: any, provider?: string | null, treatedOn: any, description: string, event: string, associatedWith: { __typename?: 'PatientTreatmentInvestigation', id: string, local: string, condition: string } } | null> | null };


export const CreatePatientDocument = gql`
    mutation createPatient($patient: PersonInput!) {
  createPatient(patient: $patient)
}
    `;
export type CreatePatientMutationFn = Apollo.MutationFunction<CreatePatientMutation, CreatePatientMutationVariables>;

/**
 * __useCreatePatientMutation__
 *
 * To run a mutation, you first call `useCreatePatientMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useCreatePatientMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [createPatientMutation, { data, loading, error }] = useCreatePatientMutation({
 *   variables: {
 *      patient: // value for 'patient'
 *   },
 * });
 */
export function useCreatePatientMutation(baseOptions?: Apollo.MutationHookOptions<CreatePatientMutation, CreatePatientMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<CreatePatientMutation, CreatePatientMutationVariables>(CreatePatientDocument, options);
      }
export type CreatePatientMutationHookResult = ReturnType<typeof useCreatePatientMutation>;
export type CreatePatientMutationResult = Apollo.MutationResult<CreatePatientMutation>;
export type CreatePatientMutationOptions = Apollo.BaseMutationOptions<CreatePatientMutation, CreatePatientMutationVariables>;
export const UpdatePatientDocument = gql`
    mutation updatePatient($id: ID!, $patient: PersonInput!) {
  updatePatient(id: $id, patient: $patient) {
    updatedPerson {
      id
      nbsEntity {
        entityLocatorParticipations {
          classCd
          locator {
            emailAddress
            extenstionTxt
            phoneNbrTxt
            urlAddress
            censusBlockCd
            censusMinorCivilDivisionCd
            censusTrackCd
            cityCd
            cityDescTxt
            cntryCd
            cntryDescTxt
            cntyCd
            cntyDescTxt
            msaCongressDistrictCd
            regionDistrictCd
            stateCd
            streetAddr1
            streetAddr2
            zipCd
            geocodeMatchInd
            withinCityLimitsInd
            censusTract
          }
        }
      }
      entityIds {
        typeDescTxt
        typeCd
        rootExtensionTxt
        assigningAuthorityCd
        assigningAuthorityDescTxt
      }
      names {
        firstNm
        middleNm
        lastNm
        nmSuffix
        nmPrefix
      }
      addReasonCd
      addTime
      addUserId
      administrativeGenderCd
      ageCalc
      ageCalcTime
      ageCalcUnitCd
      ageCategoryCd
      ageReported
      ageReportedTime
      ageReportedUnitCd
      birthGenderCd
      birthOrderNbr
      birthTime
      birthTimeCalc
      cd
      cdDescTxt
      currSexCd
      deceasedIndCd
      deceasedTime
      description
      educationLevelCd
      educationLevelDescTxt
      ethnicGroupInd
      lastChgReasonCd
      lastChgTime
      lastChgUserId
      localId
      maritalStatusCd
      maritalStatusDescTxt
      mothersMaidenNm
      multipleBirthInd
      occupationCd
      preferredGenderCd
      primLangCd
      primLangDescTxt
      recordStatusCd
      recordStatusTime
      statusCd
      statusTime
      survivedIndCd
      userAffiliationTxt
      firstNm
      lastNm
      middleNm
      nmPrefix
      nmSuffix
      preferredNm
      hmStreetAddr1
      hmStreetAddr2
      hmCityCd
      hmCityDescTxt
      hmStateCd
      hmZipCd
      hmCntyCd
      hmCntryCd
      hmPhoneNbr
      hmPhoneCntryCd
      hmEmailAddr
      cellPhoneNbr
      wkStreetAddr1
      wkStreetAddr2
      wkCityCd
      wkCityDescTxt
      wkStateCd
      wkZipCd
      wkCntyCd
      wkCntryCd
      wkPhoneNbr
      wkPhoneCntryCd
      wkEmailAddr
      ssn
      medicaidNum
      dlNum
      dlStateCd
      raceCd
      raceSeqNbr
      raceCategoryCd
      ethnicityGroupCd
      ethnicGroupSeqNbr
      adultsInHouseNbr
      childrenInHouseNbr
      birthCityCd
      birthCityDescTxt
      birthCntryCd
      birthStateCd
      raceDescTxt
      ethnicGroupDescTxt
      versionCtrlNbr
      asOfDateAdmin
      asOfDateEthnicity
      asOfDateGeneral
      asOfDateMorbidity
      asOfDateSex
      electronicInd
      personParentUid {
        id
      }
      dedupMatchInd
      groupNbr
      groupTime
      edxInd
      speaksEnglishCd
      additionalGenderCd
      eharsId
      ethnicUnkReasonCd
      sexUnkReasonCd
    }
    requestId
  }
}
    `;
export type UpdatePatientMutationFn = Apollo.MutationFunction<UpdatePatientMutation, UpdatePatientMutationVariables>;

/**
 * __useUpdatePatientMutation__
 *
 * To run a mutation, you first call `useUpdatePatientMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientMutation, { data, loading, error }] = useUpdatePatientMutation({
 *   variables: {
 *      id: // value for 'id'
 *      patient: // value for 'patient'
 *   },
 * });
 */
export function useUpdatePatientMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientMutation, UpdatePatientMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientMutation, UpdatePatientMutationVariables>(UpdatePatientDocument, options);
      }
export type UpdatePatientMutationHookResult = ReturnType<typeof useUpdatePatientMutation>;
export type UpdatePatientMutationResult = Apollo.MutationResult<UpdatePatientMutation>;
export type UpdatePatientMutationOptions = Apollo.BaseMutationOptions<UpdatePatientMutation, UpdatePatientMutationVariables>;
export const FindAllConditionCodesDocument = gql`
    query findAllConditionCodes($page: Page) {
  findAllConditionCodes(page: $page) {
    id
    conditionDescTxt
  }
}
    `;

/**
 * __useFindAllConditionCodesQuery__
 *
 * To run a query within a React component, call `useFindAllConditionCodesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllConditionCodesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllConditionCodesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllConditionCodesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllConditionCodesQuery, FindAllConditionCodesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllConditionCodesQuery, FindAllConditionCodesQueryVariables>(FindAllConditionCodesDocument, options);
      }
export function useFindAllConditionCodesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllConditionCodesQuery, FindAllConditionCodesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllConditionCodesQuery, FindAllConditionCodesQueryVariables>(FindAllConditionCodesDocument, options);
        }
export type FindAllConditionCodesQueryHookResult = ReturnType<typeof useFindAllConditionCodesQuery>;
export type FindAllConditionCodesLazyQueryHookResult = ReturnType<typeof useFindAllConditionCodesLazyQuery>;
export type FindAllConditionCodesQueryResult = Apollo.QueryResult<FindAllConditionCodesQuery, FindAllConditionCodesQueryVariables>;
export const FindAllCountryCodesDocument = gql`
    query findAllCountryCodes($page: Page) {
  findAllCountryCodes(page: $page) {
    id
    assigningAuthorityCd
    assigningAuthorityDescTxt
    codeDescTxt
    codeShortDescTxt
    effectiveFromTime
    effectiveToTime
    excludedTxt
    keyInfoTxt
    indentLevelNbr
    isModifiableInd
    parentIsCd
    statusCd
    statusTime
    codeSetNm
    seqNum
    nbsUid
    sourceConceptId
    codeSystemCd
    codeSystemDescTxt
  }
}
    `;

/**
 * __useFindAllCountryCodesQuery__
 *
 * To run a query within a React component, call `useFindAllCountryCodesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllCountryCodesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllCountryCodesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllCountryCodesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllCountryCodesQuery, FindAllCountryCodesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllCountryCodesQuery, FindAllCountryCodesQueryVariables>(FindAllCountryCodesDocument, options);
      }
export function useFindAllCountryCodesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllCountryCodesQuery, FindAllCountryCodesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllCountryCodesQuery, FindAllCountryCodesQueryVariables>(FindAllCountryCodesDocument, options);
        }
export type FindAllCountryCodesQueryHookResult = ReturnType<typeof useFindAllCountryCodesQuery>;
export type FindAllCountryCodesLazyQueryHookResult = ReturnType<typeof useFindAllCountryCodesLazyQuery>;
export type FindAllCountryCodesQueryResult = Apollo.QueryResult<FindAllCountryCodesQuery, FindAllCountryCodesQueryVariables>;
export const FindAllCountyCodesForStateDocument = gql`
    query findAllCountyCodesForState($stateCode: String!, $page: Page) {
  findAllCountyCodesForState(stateCode: $stateCode, page: $page) {
    id
    codeDescTxt
    codeShortDescTxt
  }
}
    `;

/**
 * __useFindAllCountyCodesForStateQuery__
 *
 * To run a query within a React component, call `useFindAllCountyCodesForStateQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllCountyCodesForStateQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllCountyCodesForStateQuery({
 *   variables: {
 *      stateCode: // value for 'stateCode'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllCountyCodesForStateQuery(baseOptions: Apollo.QueryHookOptions<FindAllCountyCodesForStateQuery, FindAllCountyCodesForStateQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllCountyCodesForStateQuery, FindAllCountyCodesForStateQueryVariables>(FindAllCountyCodesForStateDocument, options);
      }
export function useFindAllCountyCodesForStateLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllCountyCodesForStateQuery, FindAllCountyCodesForStateQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllCountyCodesForStateQuery, FindAllCountyCodesForStateQueryVariables>(FindAllCountyCodesForStateDocument, options);
        }
export type FindAllCountyCodesForStateQueryHookResult = ReturnType<typeof useFindAllCountyCodesForStateQuery>;
export type FindAllCountyCodesForStateLazyQueryHookResult = ReturnType<typeof useFindAllCountyCodesForStateLazyQuery>;
export type FindAllCountyCodesForStateQueryResult = Apollo.QueryResult<FindAllCountyCodesForStateQuery, FindAllCountyCodesForStateQueryVariables>;
export const FindAllEthnicityValuesDocument = gql`
    query findAllEthnicityValues($page: Page) {
  findAllEthnicityValues(page: $page) {
    content {
      id {
        code
      }
      codeDescTxt
    }
    total
  }
}
    `;

/**
 * __useFindAllEthnicityValuesQuery__
 *
 * To run a query within a React component, call `useFindAllEthnicityValuesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllEthnicityValuesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllEthnicityValuesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllEthnicityValuesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllEthnicityValuesQuery, FindAllEthnicityValuesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllEthnicityValuesQuery, FindAllEthnicityValuesQueryVariables>(FindAllEthnicityValuesDocument, options);
      }
export function useFindAllEthnicityValuesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllEthnicityValuesQuery, FindAllEthnicityValuesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllEthnicityValuesQuery, FindAllEthnicityValuesQueryVariables>(FindAllEthnicityValuesDocument, options);
        }
export type FindAllEthnicityValuesQueryHookResult = ReturnType<typeof useFindAllEthnicityValuesQuery>;
export type FindAllEthnicityValuesLazyQueryHookResult = ReturnType<typeof useFindAllEthnicityValuesLazyQuery>;
export type FindAllEthnicityValuesQueryResult = Apollo.QueryResult<FindAllEthnicityValuesQuery, FindAllEthnicityValuesQueryVariables>;
export const FindAllJurisdictionsDocument = gql`
    query findAllJurisdictions($page: Page) {
  findAllJurisdictions(page: $page) {
    id
    typeCd
    assigningAuthorityCd
    assigningAuthorityDescTxt
    codeDescTxt
    codeShortDescTxt
    effectiveFromTime
    effectiveToTime
    indentLevelNbr
    isModifiableInd
    parentIsCd
    stateDomainCd
    statusCd
    statusTime
    codeSetNm
    codeSeqNum
    nbsUid
    sourceConceptId
    codeSystemCd
    codeSystemDescTxt
    exportInd
  }
}
    `;

/**
 * __useFindAllJurisdictionsQuery__
 *
 * To run a query within a React component, call `useFindAllJurisdictionsQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllJurisdictionsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllJurisdictionsQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllJurisdictionsQuery(baseOptions?: Apollo.QueryHookOptions<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>(FindAllJurisdictionsDocument, options);
      }
export function useFindAllJurisdictionsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>(FindAllJurisdictionsDocument, options);
        }
export type FindAllJurisdictionsQueryHookResult = ReturnType<typeof useFindAllJurisdictionsQuery>;
export type FindAllJurisdictionsLazyQueryHookResult = ReturnType<typeof useFindAllJurisdictionsLazyQuery>;
export type FindAllJurisdictionsQueryResult = Apollo.QueryResult<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>;
export const FindAllOrganizationsDocument = gql`
    query findAllOrganizations($page: Page) {
  findAllOrganizations(page: $page) {
    content {
      id
      addReasonCd
      addTime
      addUserId
      cd
      cdDescTxt
      description
      durationAmt
      durationUnitCd
      fromTime
      lastChgReasonCd
      lastChgTime
      lastChgUserId
      localId
      recordStatusCd
      recordStatusTime
      standardIndustryClassCd
      standardIndustryDescTxt
      statusCd
      statusTime
      toTime
      userAffiliationTxt
      displayNm
      streetAddr1
      streetAddr2
      cityCd
      cityDescTxt
      stateCd
      cntyCd
      cntryCd
      zipCd
      phoneNbr
      phoneCntryCd
      versionCtrlNbr
      electronicInd
      edxInd
    }
    total
  }
}
    `;

/**
 * __useFindAllOrganizationsQuery__
 *
 * To run a query within a React component, call `useFindAllOrganizationsQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllOrganizationsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllOrganizationsQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllOrganizationsQuery(baseOptions?: Apollo.QueryHookOptions<FindAllOrganizationsQuery, FindAllOrganizationsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllOrganizationsQuery, FindAllOrganizationsQueryVariables>(FindAllOrganizationsDocument, options);
      }
export function useFindAllOrganizationsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllOrganizationsQuery, FindAllOrganizationsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllOrganizationsQuery, FindAllOrganizationsQueryVariables>(FindAllOrganizationsDocument, options);
        }
export type FindAllOrganizationsQueryHookResult = ReturnType<typeof useFindAllOrganizationsQuery>;
export type FindAllOrganizationsLazyQueryHookResult = ReturnType<typeof useFindAllOrganizationsLazyQuery>;
export type FindAllOrganizationsQueryResult = Apollo.QueryResult<FindAllOrganizationsQuery, FindAllOrganizationsQueryVariables>;
export const FindAllOutbreaksDocument = gql`
    query findAllOutbreaks($page: Page) {
  findAllOutbreaks(page: $page) {
    content {
      id {
        codeSetNm
        code
      }
      codeShortDescTxt
    }
    total
  }
}
    `;

/**
 * __useFindAllOutbreaksQuery__
 *
 * To run a query within a React component, call `useFindAllOutbreaksQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllOutbreaksQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllOutbreaksQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllOutbreaksQuery(baseOptions?: Apollo.QueryHookOptions<FindAllOutbreaksQuery, FindAllOutbreaksQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllOutbreaksQuery, FindAllOutbreaksQueryVariables>(FindAllOutbreaksDocument, options);
      }
export function useFindAllOutbreaksLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllOutbreaksQuery, FindAllOutbreaksQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllOutbreaksQuery, FindAllOutbreaksQueryVariables>(FindAllOutbreaksDocument, options);
        }
export type FindAllOutbreaksQueryHookResult = ReturnType<typeof useFindAllOutbreaksQuery>;
export type FindAllOutbreaksLazyQueryHookResult = ReturnType<typeof useFindAllOutbreaksLazyQuery>;
export type FindAllOutbreaksQueryResult = Apollo.QueryResult<FindAllOutbreaksQuery, FindAllOutbreaksQueryVariables>;
export const FindAllPatientIdentificationTypesDocument = gql`
    query findAllPatientIdentificationTypes($page: Page) {
  findAllPatientIdentificationTypes(page: $page) {
    content {
      id {
        code
      }
      codeDescTxt
    }
    total
  }
}
    `;

/**
 * __useFindAllPatientIdentificationTypesQuery__
 *
 * To run a query within a React component, call `useFindAllPatientIdentificationTypesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllPatientIdentificationTypesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllPatientIdentificationTypesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllPatientIdentificationTypesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllPatientIdentificationTypesQuery, FindAllPatientIdentificationTypesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllPatientIdentificationTypesQuery, FindAllPatientIdentificationTypesQueryVariables>(FindAllPatientIdentificationTypesDocument, options);
      }
export function useFindAllPatientIdentificationTypesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllPatientIdentificationTypesQuery, FindAllPatientIdentificationTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllPatientIdentificationTypesQuery, FindAllPatientIdentificationTypesQueryVariables>(FindAllPatientIdentificationTypesDocument, options);
        }
export type FindAllPatientIdentificationTypesQueryHookResult = ReturnType<typeof useFindAllPatientIdentificationTypesQuery>;
export type FindAllPatientIdentificationTypesLazyQueryHookResult = ReturnType<typeof useFindAllPatientIdentificationTypesLazyQuery>;
export type FindAllPatientIdentificationTypesQueryResult = Apollo.QueryResult<FindAllPatientIdentificationTypesQuery, FindAllPatientIdentificationTypesQueryVariables>;
export const FindAllPatientsDocument = gql`
    query findAllPatients($page: SortablePage) {
  findAllPatients(page: $page) {
    content {
      id
      nbsEntity {
        entityLocatorParticipations {
          classCd
          locator {
            emailAddress
            extenstionTxt
            phoneNbrTxt
            urlAddress
            censusBlockCd
            censusMinorCivilDivisionCd
            censusTrackCd
            cityCd
            cityDescTxt
            cntryCd
            cntryDescTxt
            cntyCd
            cntyDescTxt
            msaCongressDistrictCd
            regionDistrictCd
            stateCd
            streetAddr1
            streetAddr2
            zipCd
            geocodeMatchInd
            withinCityLimitsInd
            censusTract
          }
        }
      }
      entityIds {
        typeDescTxt
        typeCd
        rootExtensionTxt
        assigningAuthorityCd
        assigningAuthorityDescTxt
      }
      names {
        firstNm
        middleNm
        lastNm
        nmSuffix
        nmPrefix
      }
      addReasonCd
      addTime
      addUserId
      administrativeGenderCd
      ageCalc
      ageCalcTime
      ageCalcUnitCd
      ageCategoryCd
      ageReported
      ageReportedTime
      ageReportedUnitCd
      birthGenderCd
      birthOrderNbr
      birthTime
      birthTimeCalc
      cd
      cdDescTxt
      currSexCd
      deceasedIndCd
      deceasedTime
      description
      educationLevelCd
      educationLevelDescTxt
      ethnicGroupInd
      lastChgReasonCd
      lastChgTime
      lastChgUserId
      localId
      maritalStatusCd
      maritalStatusDescTxt
      mothersMaidenNm
      multipleBirthInd
      occupationCd
      preferredGenderCd
      primLangCd
      primLangDescTxt
      recordStatusCd
      recordStatusTime
      statusCd
      statusTime
      survivedIndCd
      userAffiliationTxt
      firstNm
      lastNm
      middleNm
      nmPrefix
      nmSuffix
      preferredNm
      hmStreetAddr1
      hmStreetAddr2
      hmCityCd
      hmCityDescTxt
      hmStateCd
      hmZipCd
      hmCntyCd
      hmCntryCd
      hmPhoneNbr
      hmPhoneCntryCd
      hmEmailAddr
      cellPhoneNbr
      wkStreetAddr1
      wkStreetAddr2
      wkCityCd
      wkCityDescTxt
      wkStateCd
      wkZipCd
      wkCntyCd
      wkCntryCd
      wkPhoneNbr
      wkPhoneCntryCd
      wkEmailAddr
      ssn
      medicaidNum
      dlNum
      dlStateCd
      raceCd
      raceSeqNbr
      raceCategoryCd
      ethnicityGroupCd
      ethnicGroupSeqNbr
      adultsInHouseNbr
      childrenInHouseNbr
      birthCityCd
      birthCityDescTxt
      birthCntryCd
      birthStateCd
      raceDescTxt
      ethnicGroupDescTxt
      versionCtrlNbr
      asOfDateAdmin
      asOfDateEthnicity
      asOfDateGeneral
      asOfDateMorbidity
      asOfDateSex
      electronicInd
      personParentUid {
        id
      }
      dedupMatchInd
      groupNbr
      groupTime
      edxInd
      speaksEnglishCd
      additionalGenderCd
      eharsId
      ethnicUnkReasonCd
      sexUnkReasonCd
    }
    total
  }
}
    `;

/**
 * __useFindAllPatientsQuery__
 *
 * To run a query within a React component, call `useFindAllPatientsQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllPatientsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllPatientsQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllPatientsQuery(baseOptions?: Apollo.QueryHookOptions<FindAllPatientsQuery, FindAllPatientsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllPatientsQuery, FindAllPatientsQueryVariables>(FindAllPatientsDocument, options);
      }
export function useFindAllPatientsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllPatientsQuery, FindAllPatientsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllPatientsQuery, FindAllPatientsQueryVariables>(FindAllPatientsDocument, options);
        }
export type FindAllPatientsQueryHookResult = ReturnType<typeof useFindAllPatientsQuery>;
export type FindAllPatientsLazyQueryHookResult = ReturnType<typeof useFindAllPatientsLazyQuery>;
export type FindAllPatientsQueryResult = Apollo.QueryResult<FindAllPatientsQuery, FindAllPatientsQueryVariables>;
export const FindAllPlacesDocument = gql`
    query findAllPlaces($page: Page) {
  findAllPlaces(page: $page) {
    id
    addReasonCd
    addTime
    addUserId
    cd
    cdDescTxt
    description
    durationAmt
    durationUnitCd
    fromTime
    lastChgReasonCd
    lastChgTime
    lastChgUserId
    localId
    nm
    recordStatusCd
    recordStatusTime
    statusCd
    statusTime
    toTime
    userAffiliationTxt
    streetAddr1
    streetAddr2
    cityCd
    cityDescTxt
    stateCd
    zipCd
    cntyCd
    cntryCd
    phoneNbr
    phoneCntryCd
    versionCtrlNbr
  }
}
    `;

/**
 * __useFindAllPlacesQuery__
 *
 * To run a query within a React component, call `useFindAllPlacesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllPlacesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllPlacesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllPlacesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllPlacesQuery, FindAllPlacesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllPlacesQuery, FindAllPlacesQueryVariables>(FindAllPlacesDocument, options);
      }
export function useFindAllPlacesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllPlacesQuery, FindAllPlacesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllPlacesQuery, FindAllPlacesQueryVariables>(FindAllPlacesDocument, options);
        }
export type FindAllPlacesQueryHookResult = ReturnType<typeof useFindAllPlacesQuery>;
export type FindAllPlacesLazyQueryHookResult = ReturnType<typeof useFindAllPlacesLazyQuery>;
export type FindAllPlacesQueryResult = Apollo.QueryResult<FindAllPlacesQuery, FindAllPlacesQueryVariables>;
export const FindAllProgramAreasDocument = gql`
    query findAllProgramAreas($page: Page) {
  findAllProgramAreas(page: $page) {
    id
    progAreaDescTxt
    nbsUid
    statusCd
    statusTime
    codeSetNm
    codeSeq
  }
}
    `;

/**
 * __useFindAllProgramAreasQuery__
 *
 * To run a query within a React component, call `useFindAllProgramAreasQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllProgramAreasQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllProgramAreasQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllProgramAreasQuery(baseOptions?: Apollo.QueryHookOptions<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>(FindAllProgramAreasDocument, options);
      }
export function useFindAllProgramAreasLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>(FindAllProgramAreasDocument, options);
        }
export type FindAllProgramAreasQueryHookResult = ReturnType<typeof useFindAllProgramAreasQuery>;
export type FindAllProgramAreasLazyQueryHookResult = ReturnType<typeof useFindAllProgramAreasLazyQuery>;
export type FindAllProgramAreasQueryResult = Apollo.QueryResult<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>;
export const FindAllRaceValuesDocument = gql`
    query findAllRaceValues($page: Page) {
  findAllRaceValues(page: $page) {
    content {
      id {
        code
      }
      codeDescTxt
    }
    total
  }
}
    `;

/**
 * __useFindAllRaceValuesQuery__
 *
 * To run a query within a React component, call `useFindAllRaceValuesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllRaceValuesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllRaceValuesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllRaceValuesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllRaceValuesQuery, FindAllRaceValuesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllRaceValuesQuery, FindAllRaceValuesQueryVariables>(FindAllRaceValuesDocument, options);
      }
export function useFindAllRaceValuesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllRaceValuesQuery, FindAllRaceValuesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllRaceValuesQuery, FindAllRaceValuesQueryVariables>(FindAllRaceValuesDocument, options);
        }
export type FindAllRaceValuesQueryHookResult = ReturnType<typeof useFindAllRaceValuesQuery>;
export type FindAllRaceValuesLazyQueryHookResult = ReturnType<typeof useFindAllRaceValuesLazyQuery>;
export type FindAllRaceValuesQueryResult = Apollo.QueryResult<FindAllRaceValuesQuery, FindAllRaceValuesQueryVariables>;
export const FindAllStateCodesDocument = gql`
    query findAllStateCodes($page: Page) {
  findAllStateCodes(page: $page) {
    id
    assigningAuthorityCd
    assigningAuthorityDescTxt
    stateNm
    codeDescTxt
    effectiveFromTime
    effectiveToTime
    excludedTxt
    indentLevelNbr
    isModifiableInd
    keyInfoTxt
    parentIsCd
    statusCd
    statusTime
    codeSetNm
    seqNum
    nbsUid
    sourceConceptId
    codeSystemCd
    codeSystemDescTxt
  }
}
    `;

/**
 * __useFindAllStateCodesQuery__
 *
 * To run a query within a React component, call `useFindAllStateCodesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllStateCodesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllStateCodesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllStateCodesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllStateCodesQuery, FindAllStateCodesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllStateCodesQuery, FindAllStateCodesQueryVariables>(FindAllStateCodesDocument, options);
      }
export function useFindAllStateCodesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllStateCodesQuery, FindAllStateCodesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllStateCodesQuery, FindAllStateCodesQueryVariables>(FindAllStateCodesDocument, options);
        }
export type FindAllStateCodesQueryHookResult = ReturnType<typeof useFindAllStateCodesQuery>;
export type FindAllStateCodesLazyQueryHookResult = ReturnType<typeof useFindAllStateCodesLazyQuery>;
export type FindAllStateCodesQueryResult = Apollo.QueryResult<FindAllStateCodesQuery, FindAllStateCodesQueryVariables>;
export const FindAllUsersDocument = gql`
    query findAllUsers($page: Page) {
  findAllUsers(page: $page) {
    content {
      nedssEntryId
      userId
      userFirstNm
      userLastNm
      recordStatusCd
    }
    total
  }
}
    `;

/**
 * __useFindAllUsersQuery__
 *
 * To run a query within a React component, call `useFindAllUsersQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllUsersQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllUsersQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllUsersQuery(baseOptions?: Apollo.QueryHookOptions<FindAllUsersQuery, FindAllUsersQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllUsersQuery, FindAllUsersQueryVariables>(FindAllUsersDocument, options);
      }
export function useFindAllUsersLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllUsersQuery, FindAllUsersQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllUsersQuery, FindAllUsersQueryVariables>(FindAllUsersDocument, options);
        }
export type FindAllUsersQueryHookResult = ReturnType<typeof useFindAllUsersQuery>;
export type FindAllUsersLazyQueryHookResult = ReturnType<typeof useFindAllUsersLazyQuery>;
export type FindAllUsersQueryResult = Apollo.QueryResult<FindAllUsersQuery, FindAllUsersQueryVariables>;
export const FindContactsForPatientDocument = gql`
    query findContactsForPatient($patient: ID!) {
  findContactsForPatient(patient: $patient) {
    patient
    namedByPatient {
      contactRecord
      createdOn
      condition
      contact {
        id
        name
      }
      namedOn
      priority
      disposition
      event
    }
    namedByContact {
      contactRecord
      createdOn
      contact {
        id
        name
      }
      namedOn
      condition
      event
      associatedWith {
        id
        local
        condition
      }
    }
  }
}
    `;

/**
 * __useFindContactsForPatientQuery__
 *
 * To run a query within a React component, call `useFindContactsForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindContactsForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindContactsForPatientQuery({
 *   variables: {
 *      patient: // value for 'patient'
 *   },
 * });
 */
export function useFindContactsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindContactsForPatientQuery, FindContactsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindContactsForPatientQuery, FindContactsForPatientQueryVariables>(FindContactsForPatientDocument, options);
      }
export function useFindContactsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindContactsForPatientQuery, FindContactsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindContactsForPatientQuery, FindContactsForPatientQueryVariables>(FindContactsForPatientDocument, options);
        }
export type FindContactsForPatientQueryHookResult = ReturnType<typeof useFindContactsForPatientQuery>;
export type FindContactsForPatientLazyQueryHookResult = ReturnType<typeof useFindContactsForPatientLazyQuery>;
export type FindContactsForPatientQueryResult = Apollo.QueryResult<FindContactsForPatientQuery, FindContactsForPatientQueryVariables>;
export const FindDocumentsRequiringReviewForPatientDocument = gql`
    query findDocumentsRequiringReviewForPatient($patientId: Int!, $page: Page) {
  findDocumentsRequiringReviewForPatient(patientId: $patientId, page: $page) {
    content {
      id
      observationUid
      lastChange
      classCd
      moodCd
      observationLastChgTime
      cdDescTxt
      recordStatusCd
      programAreaCd
      jurisdictionCd
      jurisdictionCodeDescTxt
      pregnantIndCd
      localId
      activityToTime
      effectiveFromTime
      rptToStateTime
      addTime
      electronicInd
      versionCtrlNbr
      addUserId
      lastChgUserId
      personParticipations {
        actUid
        localId
        typeCd
        entityId
        subjectClassCd
        participationRecordStatus
        typeDescTxt
        participationLastChangeTime
        firstName
        lastName
        birthTime
        currSexCd
        personCd
        personParentUid
        personRecordStatus
        personLastChangeTime
      }
      organizationParticipations {
        actUid
        typeCd
        entityId
        subjectClassCd
        typeDescTxt
        participationRecordStatus
        participationLastChangeTime
        name
        organizationLastChangeTime
      }
      materialParticipations {
        actUid
        typeCd
        entityId
        subjectClassCd
        typeDescTxt
        participationRecordStatus
        participationLastChangeTime
        cd
        cdDescTxt
      }
      observations {
        cd
        cdDescTxt
        domainCd
        statusCd
        altCd
        altDescTxt
        altCdSystemCd
        displayName
        ovcCode
        ovcAltCode
        ovcAltDescTxt
        ovcAltCdSystemCd
      }
      actIds {
        id
        recordStatus
        actIdSeq
        rootExtensionTxt
        typeCd
        lastChangeTime
      }
      associatedInvestigations {
        publicHealthCaseUid
        cdDescTxt
        localId
        lastChgTime
        actRelationshipLastChgTime
      }
    }
    total
  }
}
    `;

/**
 * __useFindDocumentsRequiringReviewForPatientQuery__
 *
 * To run a query within a React component, call `useFindDocumentsRequiringReviewForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindDocumentsRequiringReviewForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindDocumentsRequiringReviewForPatientQuery({
 *   variables: {
 *      patientId: // value for 'patientId'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindDocumentsRequiringReviewForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>(FindDocumentsRequiringReviewForPatientDocument, options);
      }
export function useFindDocumentsRequiringReviewForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>(FindDocumentsRequiringReviewForPatientDocument, options);
        }
export type FindDocumentsRequiringReviewForPatientQueryHookResult = ReturnType<typeof useFindDocumentsRequiringReviewForPatientQuery>;
export type FindDocumentsRequiringReviewForPatientLazyQueryHookResult = ReturnType<typeof useFindDocumentsRequiringReviewForPatientLazyQuery>;
export type FindDocumentsRequiringReviewForPatientQueryResult = Apollo.QueryResult<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>;
export const FindInvestigationsByFilterDocument = gql`
    query findInvestigationsByFilter($filter: InvestigationFilter!, $page: SortablePage) {
  findInvestigationsByFilter(filter: $filter, page: $page) {
    content {
      id
      recordStatus
      lastChangeTime
      publicHealthCaseUid
      caseClassCd
      outbreakName
      caseTypeCd
      cdDescTxt
      progAreaCd
      jurisdictionCd
      jurisdictionCodeDescTxt
      pregnantIndCd
      localId
      rptFormCmpltTime
      activityToTime
      activityFromTime
      addTime
      publicHealthCaseLastChgTime
      addUserId
      lastChangeUserId
      currProcessStateCd
      investigationStatusCd
      moodCd
      notificationLocalId
      notificationAddTime
      notificationRecordStatusCd
      notificationLastChgTime
      personParticipations {
        actUid
        localId
        typeCd
        entityId
        subjectClassCd
        participationRecordStatus
        typeDescTxt
        participationLastChangeTime
        firstName
        lastName
        birthTime
        currSexCd
        personCd
        personParentUid
        personRecordStatus
        personLastChangeTime
      }
      organizationParticipations {
        actUid
        typeCd
        entityId
        subjectClassCd
        typeDescTxt
        participationRecordStatus
        participationLastChangeTime
        name
        organizationLastChangeTime
      }
      actIds {
        id
        recordStatus
        actIdSeq
        rootExtensionTxt
        typeCd
        lastChangeTime
      }
    }
    total
  }
}
    `;

/**
 * __useFindInvestigationsByFilterQuery__
 *
 * To run a query within a React component, call `useFindInvestigationsByFilterQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindInvestigationsByFilterQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindInvestigationsByFilterQuery({
 *   variables: {
 *      filter: // value for 'filter'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindInvestigationsByFilterQuery(baseOptions: Apollo.QueryHookOptions<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>(FindInvestigationsByFilterDocument, options);
      }
export function useFindInvestigationsByFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>(FindInvestigationsByFilterDocument, options);
        }
export type FindInvestigationsByFilterQueryHookResult = ReturnType<typeof useFindInvestigationsByFilterQuery>;
export type FindInvestigationsByFilterLazyQueryHookResult = ReturnType<typeof useFindInvestigationsByFilterLazyQuery>;
export type FindInvestigationsByFilterQueryResult = Apollo.QueryResult<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>;
export const FindLabReportsByFilterDocument = gql`
    query findLabReportsByFilter($filter: LabReportFilter!, $page: SortablePage) {
  findLabReportsByFilter(filter: $filter, page: $page) {
    content {
      id
      observationUid
      lastChange
      classCd
      moodCd
      observationLastChgTime
      cdDescTxt
      recordStatusCd
      programAreaCd
      jurisdictionCd
      jurisdictionCodeDescTxt
      pregnantIndCd
      localId
      activityToTime
      effectiveFromTime
      rptToStateTime
      addTime
      electronicInd
      versionCtrlNbr
      addUserId
      lastChgUserId
      personParticipations {
        actUid
        localId
        typeCd
        entityId
        subjectClassCd
        participationRecordStatus
        typeDescTxt
        participationLastChangeTime
        firstName
        lastName
        birthTime
        currSexCd
        personCd
        personParentUid
        personRecordStatus
        personLastChangeTime
      }
      organizationParticipations {
        actUid
        typeCd
        entityId
        subjectClassCd
        typeDescTxt
        participationRecordStatus
        participationLastChangeTime
        name
        organizationLastChangeTime
      }
      materialParticipations {
        actUid
        typeCd
        entityId
        subjectClassCd
        typeDescTxt
        participationRecordStatus
        participationLastChangeTime
        cd
        cdDescTxt
      }
      observations {
        cd
        cdDescTxt
        domainCd
        statusCd
        altCd
        altDescTxt
        altCdSystemCd
        displayName
        ovcCode
        ovcAltCode
        ovcAltDescTxt
        ovcAltCdSystemCd
      }
      actIds {
        id
        recordStatus
        actIdSeq
        rootExtensionTxt
        typeCd
        lastChangeTime
      }
      associatedInvestigations {
        publicHealthCaseUid
        cdDescTxt
        localId
        lastChgTime
        actRelationshipLastChgTime
      }
    }
    total
  }
}
    `;

/**
 * __useFindLabReportsByFilterQuery__
 *
 * To run a query within a React component, call `useFindLabReportsByFilterQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindLabReportsByFilterQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindLabReportsByFilterQuery({
 *   variables: {
 *      filter: // value for 'filter'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindLabReportsByFilterQuery(baseOptions: Apollo.QueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>(FindLabReportsByFilterDocument, options);
      }
export function useFindLabReportsByFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>(FindLabReportsByFilterDocument, options);
        }
export type FindLabReportsByFilterQueryHookResult = ReturnType<typeof useFindLabReportsByFilterQuery>;
export type FindLabReportsByFilterLazyQueryHookResult = ReturnType<typeof useFindLabReportsByFilterLazyQuery>;
export type FindLabReportsByFilterQueryResult = Apollo.QueryResult<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>;
export const FindLocalCodedResultsDocument = gql`
    query findLocalCodedResults($searchText: String!, $page: Page) {
  findLocalCodedResults(searchText: $searchText, page: $page) {
    content {
      id {
        labResultCd
        laboratoryId
      }
      nbsUid
      labResultDescTxt
    }
    total
  }
}
    `;

/**
 * __useFindLocalCodedResultsQuery__
 *
 * To run a query within a React component, call `useFindLocalCodedResultsQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindLocalCodedResultsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindLocalCodedResultsQuery({
 *   variables: {
 *      searchText: // value for 'searchText'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindLocalCodedResultsQuery(baseOptions: Apollo.QueryHookOptions<FindLocalCodedResultsQuery, FindLocalCodedResultsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindLocalCodedResultsQuery, FindLocalCodedResultsQueryVariables>(FindLocalCodedResultsDocument, options);
      }
export function useFindLocalCodedResultsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindLocalCodedResultsQuery, FindLocalCodedResultsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindLocalCodedResultsQuery, FindLocalCodedResultsQueryVariables>(FindLocalCodedResultsDocument, options);
        }
export type FindLocalCodedResultsQueryHookResult = ReturnType<typeof useFindLocalCodedResultsQuery>;
export type FindLocalCodedResultsLazyQueryHookResult = ReturnType<typeof useFindLocalCodedResultsLazyQuery>;
export type FindLocalCodedResultsQueryResult = Apollo.QueryResult<FindLocalCodedResultsQuery, FindLocalCodedResultsQueryVariables>;
export const FindLocalLabTestDocument = gql`
    query findLocalLabTest($searchText: String!, $page: Page) {
  findLocalLabTest(searchText: $searchText, page: $page) {
    content {
      id {
        labTestCd
        laboratoryId
      }
      labTestDescTxt
      organismResultTestInd
    }
    total
  }
}
    `;

/**
 * __useFindLocalLabTestQuery__
 *
 * To run a query within a React component, call `useFindLocalLabTestQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindLocalLabTestQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindLocalLabTestQuery({
 *   variables: {
 *      searchText: // value for 'searchText'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindLocalLabTestQuery(baseOptions: Apollo.QueryHookOptions<FindLocalLabTestQuery, FindLocalLabTestQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindLocalLabTestQuery, FindLocalLabTestQueryVariables>(FindLocalLabTestDocument, options);
      }
export function useFindLocalLabTestLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindLocalLabTestQuery, FindLocalLabTestQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindLocalLabTestQuery, FindLocalLabTestQueryVariables>(FindLocalLabTestDocument, options);
        }
export type FindLocalLabTestQueryHookResult = ReturnType<typeof useFindLocalLabTestQuery>;
export type FindLocalLabTestLazyQueryHookResult = ReturnType<typeof useFindLocalLabTestLazyQuery>;
export type FindLocalLabTestQueryResult = Apollo.QueryResult<FindLocalLabTestQuery, FindLocalLabTestQueryVariables>;
export const FindLoincLabTestDocument = gql`
    query findLoincLabTest($searchText: String!, $page: Page) {
  findLoincLabTest(searchText: $searchText, page: $page) {
    content {
      id
      componentName
      methodType
      systemCd
      property
      relatedClassCd
    }
    total
  }
}
    `;

/**
 * __useFindLoincLabTestQuery__
 *
 * To run a query within a React component, call `useFindLoincLabTestQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindLoincLabTestQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindLoincLabTestQuery({
 *   variables: {
 *      searchText: // value for 'searchText'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindLoincLabTestQuery(baseOptions: Apollo.QueryHookOptions<FindLoincLabTestQuery, FindLoincLabTestQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindLoincLabTestQuery, FindLoincLabTestQueryVariables>(FindLoincLabTestDocument, options);
      }
export function useFindLoincLabTestLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindLoincLabTestQuery, FindLoincLabTestQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindLoincLabTestQuery, FindLoincLabTestQueryVariables>(FindLoincLabTestDocument, options);
        }
export type FindLoincLabTestQueryHookResult = ReturnType<typeof useFindLoincLabTestQuery>;
export type FindLoincLabTestLazyQueryHookResult = ReturnType<typeof useFindLoincLabTestLazyQuery>;
export type FindLoincLabTestQueryResult = Apollo.QueryResult<FindLoincLabTestQuery, FindLoincLabTestQueryVariables>;
export const FindOpenInvestigationsForPatientDocument = gql`
    query findOpenInvestigationsForPatient($patientId: Int!, $page: Page) {
  findOpenInvestigationsForPatient(patientId: $patientId, page: $page) {
    content {
      id
      recordStatus
      lastChangeTime
      publicHealthCaseUid
      caseClassCd
      outbreakName
      caseTypeCd
      cdDescTxt
      progAreaCd
      jurisdictionCd
      jurisdictionCodeDescTxt
      pregnantIndCd
      localId
      rptFormCmpltTime
      activityToTime
      activityFromTime
      addTime
      publicHealthCaseLastChgTime
      addUserId
      lastChangeUserId
      currProcessStateCd
      investigationStatusCd
      moodCd
      notificationLocalId
      notificationAddTime
      notificationRecordStatusCd
      notificationLastChgTime
      personParticipations {
        actUid
        localId
        typeCd
        entityId
        subjectClassCd
        participationRecordStatus
        typeDescTxt
        participationLastChangeTime
        firstName
        lastName
        birthTime
        currSexCd
        personCd
        personParentUid
        personRecordStatus
        personLastChangeTime
      }
      organizationParticipations {
        actUid
        typeCd
        entityId
        subjectClassCd
        typeDescTxt
        participationRecordStatus
        participationLastChangeTime
        name
        organizationLastChangeTime
      }
      actIds {
        id
        recordStatus
        actIdSeq
        rootExtensionTxt
        typeCd
        lastChangeTime
      }
    }
    total
  }
}
    `;

/**
 * __useFindOpenInvestigationsForPatientQuery__
 *
 * To run a query within a React component, call `useFindOpenInvestigationsForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindOpenInvestigationsForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindOpenInvestigationsForPatientQuery({
 *   variables: {
 *      patientId: // value for 'patientId'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindOpenInvestigationsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindOpenInvestigationsForPatientQuery, FindOpenInvestigationsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindOpenInvestigationsForPatientQuery, FindOpenInvestigationsForPatientQueryVariables>(FindOpenInvestigationsForPatientDocument, options);
      }
export function useFindOpenInvestigationsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindOpenInvestigationsForPatientQuery, FindOpenInvestigationsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindOpenInvestigationsForPatientQuery, FindOpenInvestigationsForPatientQueryVariables>(FindOpenInvestigationsForPatientDocument, options);
        }
export type FindOpenInvestigationsForPatientQueryHookResult = ReturnType<typeof useFindOpenInvestigationsForPatientQuery>;
export type FindOpenInvestigationsForPatientLazyQueryHookResult = ReturnType<typeof useFindOpenInvestigationsForPatientLazyQuery>;
export type FindOpenInvestigationsForPatientQueryResult = Apollo.QueryResult<FindOpenInvestigationsForPatientQuery, FindOpenInvestigationsForPatientQueryVariables>;
export const FindOrganizationByIdDocument = gql`
    query findOrganizationById($id: ID!) {
  findOrganizationById(id: $id) {
    id
    addReasonCd
    addTime
    addUserId
    cd
    cdDescTxt
    description
    durationAmt
    durationUnitCd
    fromTime
    lastChgReasonCd
    lastChgTime
    lastChgUserId
    localId
    recordStatusCd
    recordStatusTime
    standardIndustryClassCd
    standardIndustryDescTxt
    statusCd
    statusTime
    toTime
    userAffiliationTxt
    displayNm
    streetAddr1
    streetAddr2
    cityCd
    cityDescTxt
    stateCd
    cntyCd
    cntryCd
    zipCd
    phoneNbr
    phoneCntryCd
    versionCtrlNbr
    electronicInd
    edxInd
  }
}
    `;

/**
 * __useFindOrganizationByIdQuery__
 *
 * To run a query within a React component, call `useFindOrganizationByIdQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindOrganizationByIdQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindOrganizationByIdQuery({
 *   variables: {
 *      id: // value for 'id'
 *   },
 * });
 */
export function useFindOrganizationByIdQuery(baseOptions: Apollo.QueryHookOptions<FindOrganizationByIdQuery, FindOrganizationByIdQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindOrganizationByIdQuery, FindOrganizationByIdQueryVariables>(FindOrganizationByIdDocument, options);
      }
export function useFindOrganizationByIdLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindOrganizationByIdQuery, FindOrganizationByIdQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindOrganizationByIdQuery, FindOrganizationByIdQueryVariables>(FindOrganizationByIdDocument, options);
        }
export type FindOrganizationByIdQueryHookResult = ReturnType<typeof useFindOrganizationByIdQuery>;
export type FindOrganizationByIdLazyQueryHookResult = ReturnType<typeof useFindOrganizationByIdLazyQuery>;
export type FindOrganizationByIdQueryResult = Apollo.QueryResult<FindOrganizationByIdQuery, FindOrganizationByIdQueryVariables>;
export const FindOrganizationsByFilterDocument = gql`
    query findOrganizationsByFilter($filter: OrganizationFilter!, $page: Page) {
  findOrganizationsByFilter(filter: $filter, page: $page) {
    content {
      id
      addReasonCd
      addTime
      addUserId
      cd
      cdDescTxt
      description
      durationAmt
      durationUnitCd
      fromTime
      lastChgReasonCd
      lastChgTime
      lastChgUserId
      localId
      recordStatusCd
      recordStatusTime
      standardIndustryClassCd
      standardIndustryDescTxt
      statusCd
      statusTime
      toTime
      userAffiliationTxt
      displayNm
      streetAddr1
      streetAddr2
      cityCd
      cityDescTxt
      stateCd
      cntyCd
      cntryCd
      zipCd
      phoneNbr
      phoneCntryCd
      versionCtrlNbr
      electronicInd
      edxInd
    }
    total
  }
}
    `;

/**
 * __useFindOrganizationsByFilterQuery__
 *
 * To run a query within a React component, call `useFindOrganizationsByFilterQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindOrganizationsByFilterQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindOrganizationsByFilterQuery({
 *   variables: {
 *      filter: // value for 'filter'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindOrganizationsByFilterQuery(baseOptions: Apollo.QueryHookOptions<FindOrganizationsByFilterQuery, FindOrganizationsByFilterQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindOrganizationsByFilterQuery, FindOrganizationsByFilterQueryVariables>(FindOrganizationsByFilterDocument, options);
      }
export function useFindOrganizationsByFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindOrganizationsByFilterQuery, FindOrganizationsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindOrganizationsByFilterQuery, FindOrganizationsByFilterQueryVariables>(FindOrganizationsByFilterDocument, options);
        }
export type FindOrganizationsByFilterQueryHookResult = ReturnType<typeof useFindOrganizationsByFilterQuery>;
export type FindOrganizationsByFilterLazyQueryHookResult = ReturnType<typeof useFindOrganizationsByFilterLazyQuery>;
export type FindOrganizationsByFilterQueryResult = Apollo.QueryResult<FindOrganizationsByFilterQuery, FindOrganizationsByFilterQueryVariables>;
export const FindPatientByIdDocument = gql`
    query findPatientById($id: ID!) {
  findPatientById(id: $id) {
    id
    nbsEntity {
      entityLocatorParticipations {
        classCd
        locator {
          emailAddress
          extenstionTxt
          phoneNbrTxt
          urlAddress
          censusBlockCd
          censusMinorCivilDivisionCd
          censusTrackCd
          cityCd
          cityDescTxt
          cntryCd
          cntryDescTxt
          cntyCd
          cntyDescTxt
          msaCongressDistrictCd
          regionDistrictCd
          stateCd
          streetAddr1
          streetAddr2
          zipCd
          geocodeMatchInd
          withinCityLimitsInd
          censusTract
        }
      }
    }
    entityIds {
      typeDescTxt
      typeCd
      rootExtensionTxt
      assigningAuthorityCd
      assigningAuthorityDescTxt
    }
    names {
      firstNm
      middleNm
      lastNm
      nmSuffix
      nmPrefix
    }
    addReasonCd
    addTime
    addUserId
    administrativeGenderCd
    ageCalc
    ageCalcTime
    ageCalcUnitCd
    ageCategoryCd
    ageReported
    ageReportedTime
    ageReportedUnitCd
    birthGenderCd
    birthOrderNbr
    birthTime
    birthTimeCalc
    cd
    cdDescTxt
    currSexCd
    deceasedIndCd
    deceasedTime
    description
    educationLevelCd
    educationLevelDescTxt
    ethnicGroupInd
    lastChgReasonCd
    lastChgTime
    lastChgUserId
    localId
    maritalStatusCd
    maritalStatusDescTxt
    mothersMaidenNm
    multipleBirthInd
    occupationCd
    preferredGenderCd
    primLangCd
    primLangDescTxt
    recordStatusCd
    recordStatusTime
    statusCd
    statusTime
    survivedIndCd
    userAffiliationTxt
    firstNm
    lastNm
    middleNm
    nmPrefix
    nmSuffix
    preferredNm
    hmStreetAddr1
    hmStreetAddr2
    hmCityCd
    hmCityDescTxt
    hmStateCd
    hmZipCd
    hmCntyCd
    hmCntryCd
    hmPhoneNbr
    hmPhoneCntryCd
    hmEmailAddr
    cellPhoneNbr
    wkStreetAddr1
    wkStreetAddr2
    wkCityCd
    wkCityDescTxt
    wkStateCd
    wkZipCd
    wkCntyCd
    wkCntryCd
    wkPhoneNbr
    wkPhoneCntryCd
    wkEmailAddr
    ssn
    medicaidNum
    dlNum
    dlStateCd
    raceCd
    raceSeqNbr
    raceCategoryCd
    ethnicityGroupCd
    ethnicGroupSeqNbr
    adultsInHouseNbr
    childrenInHouseNbr
    birthCityCd
    birthCityDescTxt
    birthCntryCd
    birthStateCd
    raceDescTxt
    ethnicGroupDescTxt
    versionCtrlNbr
    asOfDateAdmin
    asOfDateEthnicity
    asOfDateGeneral
    asOfDateMorbidity
    asOfDateSex
    electronicInd
    personParentUid {
      id
    }
    dedupMatchInd
    groupNbr
    groupTime
    edxInd
    speaksEnglishCd
    additionalGenderCd
    eharsId
    ethnicUnkReasonCd
    sexUnkReasonCd
  }
}
    `;

/**
 * __useFindPatientByIdQuery__
 *
 * To run a query within a React component, call `useFindPatientByIdQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPatientByIdQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPatientByIdQuery({
 *   variables: {
 *      id: // value for 'id'
 *   },
 * });
 */
export function useFindPatientByIdQuery(baseOptions: Apollo.QueryHookOptions<FindPatientByIdQuery, FindPatientByIdQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientByIdQuery, FindPatientByIdQueryVariables>(FindPatientByIdDocument, options);
      }
export function useFindPatientByIdLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientByIdQuery, FindPatientByIdQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientByIdQuery, FindPatientByIdQueryVariables>(FindPatientByIdDocument, options);
        }
export type FindPatientByIdQueryHookResult = ReturnType<typeof useFindPatientByIdQuery>;
export type FindPatientByIdLazyQueryHookResult = ReturnType<typeof useFindPatientByIdLazyQuery>;
export type FindPatientByIdQueryResult = Apollo.QueryResult<FindPatientByIdQuery, FindPatientByIdQueryVariables>;
export const FindPatientsByFilterDocument = gql`
    query findPatientsByFilter($filter: PersonFilter!, $page: SortablePage) {
  findPatientsByFilter(filter: $filter, page: $page) {
    content {
      id
      nbsEntity {
        entityLocatorParticipations {
          classCd
          locator {
            emailAddress
            extenstionTxt
            phoneNbrTxt
            urlAddress
            censusBlockCd
            censusMinorCivilDivisionCd
            censusTrackCd
            cityCd
            cityDescTxt
            cntryCd
            cntryDescTxt
            cntyCd
            cntyDescTxt
            msaCongressDistrictCd
            regionDistrictCd
            stateCd
            streetAddr1
            streetAddr2
            zipCd
            geocodeMatchInd
            withinCityLimitsInd
            censusTract
          }
        }
      }
      entityIds {
        typeDescTxt
        typeCd
        rootExtensionTxt
        assigningAuthorityCd
        assigningAuthorityDescTxt
      }
      names {
        firstNm
        middleNm
        lastNm
        nmSuffix
        nmPrefix
      }
      addReasonCd
      addTime
      addUserId
      administrativeGenderCd
      ageCalc
      ageCalcTime
      ageCalcUnitCd
      ageCategoryCd
      ageReported
      ageReportedTime
      ageReportedUnitCd
      birthGenderCd
      birthOrderNbr
      birthTime
      birthTimeCalc
      cd
      cdDescTxt
      currSexCd
      deceasedIndCd
      deceasedTime
      description
      educationLevelCd
      educationLevelDescTxt
      ethnicGroupInd
      lastChgReasonCd
      lastChgTime
      lastChgUserId
      localId
      maritalStatusCd
      maritalStatusDescTxt
      mothersMaidenNm
      multipleBirthInd
      occupationCd
      preferredGenderCd
      primLangCd
      primLangDescTxt
      recordStatusCd
      recordStatusTime
      statusCd
      statusTime
      survivedIndCd
      userAffiliationTxt
      firstNm
      lastNm
      middleNm
      nmPrefix
      nmSuffix
      preferredNm
      hmStreetAddr1
      hmStreetAddr2
      hmCityCd
      hmCityDescTxt
      hmStateCd
      hmZipCd
      hmCntyCd
      hmCntryCd
      hmPhoneNbr
      hmPhoneCntryCd
      hmEmailAddr
      cellPhoneNbr
      wkStreetAddr1
      wkStreetAddr2
      wkCityCd
      wkCityDescTxt
      wkStateCd
      wkZipCd
      wkCntyCd
      wkCntryCd
      wkPhoneNbr
      wkPhoneCntryCd
      wkEmailAddr
      ssn
      medicaidNum
      dlNum
      dlStateCd
      raceCd
      raceSeqNbr
      raceCategoryCd
      ethnicityGroupCd
      ethnicGroupSeqNbr
      adultsInHouseNbr
      childrenInHouseNbr
      birthCityCd
      birthCityDescTxt
      birthCntryCd
      birthStateCd
      raceDescTxt
      ethnicGroupDescTxt
      versionCtrlNbr
      asOfDateAdmin
      asOfDateEthnicity
      asOfDateGeneral
      asOfDateMorbidity
      asOfDateSex
      electronicInd
      personParentUid {
        id
      }
      dedupMatchInd
      groupNbr
      groupTime
      edxInd
      speaksEnglishCd
      additionalGenderCd
      eharsId
      ethnicUnkReasonCd
      sexUnkReasonCd
    }
    total
  }
}
    `;

/**
 * __useFindPatientsByFilterQuery__
 *
 * To run a query within a React component, call `useFindPatientsByFilterQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPatientsByFilterQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPatientsByFilterQuery({
 *   variables: {
 *      filter: // value for 'filter'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindPatientsByFilterQuery(baseOptions: Apollo.QueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(FindPatientsByFilterDocument, options);
      }
export function useFindPatientsByFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(FindPatientsByFilterDocument, options);
        }
export type FindPatientsByFilterQueryHookResult = ReturnType<typeof useFindPatientsByFilterQuery>;
export type FindPatientsByFilterLazyQueryHookResult = ReturnType<typeof useFindPatientsByFilterLazyQuery>;
export type FindPatientsByFilterQueryResult = Apollo.QueryResult<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>;
export const FindPatientsByOrganizationFilterDocument = gql`
    query findPatientsByOrganizationFilter($filter: OrganizationFilter!, $page: SortablePage) {
  findPatientsByOrganizationFilter(filter: $filter, page: $page) {
    content {
      id
      nbsEntity {
        entityLocatorParticipations {
          classCd
          locator {
            emailAddress
            extenstionTxt
            phoneNbrTxt
            urlAddress
            censusBlockCd
            censusMinorCivilDivisionCd
            censusTrackCd
            cityCd
            cityDescTxt
            cntryCd
            cntryDescTxt
            cntyCd
            cntyDescTxt
            msaCongressDistrictCd
            regionDistrictCd
            stateCd
            streetAddr1
            streetAddr2
            zipCd
            geocodeMatchInd
            withinCityLimitsInd
            censusTract
          }
        }
      }
      entityIds {
        typeDescTxt
        typeCd
        rootExtensionTxt
        assigningAuthorityCd
        assigningAuthorityDescTxt
      }
      names {
        firstNm
        middleNm
        lastNm
        nmSuffix
        nmPrefix
      }
      addReasonCd
      addTime
      addUserId
      administrativeGenderCd
      ageCalc
      ageCalcTime
      ageCalcUnitCd
      ageCategoryCd
      ageReported
      ageReportedTime
      ageReportedUnitCd
      birthGenderCd
      birthOrderNbr
      birthTime
      birthTimeCalc
      cd
      cdDescTxt
      currSexCd
      deceasedIndCd
      deceasedTime
      description
      educationLevelCd
      educationLevelDescTxt
      ethnicGroupInd
      lastChgReasonCd
      lastChgTime
      lastChgUserId
      localId
      maritalStatusCd
      maritalStatusDescTxt
      mothersMaidenNm
      multipleBirthInd
      occupationCd
      preferredGenderCd
      primLangCd
      primLangDescTxt
      recordStatusCd
      recordStatusTime
      statusCd
      statusTime
      survivedIndCd
      userAffiliationTxt
      firstNm
      lastNm
      middleNm
      nmPrefix
      nmSuffix
      preferredNm
      hmStreetAddr1
      hmStreetAddr2
      hmCityCd
      hmCityDescTxt
      hmStateCd
      hmZipCd
      hmCntyCd
      hmCntryCd
      hmPhoneNbr
      hmPhoneCntryCd
      hmEmailAddr
      cellPhoneNbr
      wkStreetAddr1
      wkStreetAddr2
      wkCityCd
      wkCityDescTxt
      wkStateCd
      wkZipCd
      wkCntyCd
      wkCntryCd
      wkPhoneNbr
      wkPhoneCntryCd
      wkEmailAddr
      ssn
      medicaidNum
      dlNum
      dlStateCd
      raceCd
      raceSeqNbr
      raceCategoryCd
      ethnicityGroupCd
      ethnicGroupSeqNbr
      adultsInHouseNbr
      childrenInHouseNbr
      birthCityCd
      birthCityDescTxt
      birthCntryCd
      birthStateCd
      raceDescTxt
      ethnicGroupDescTxt
      versionCtrlNbr
      asOfDateAdmin
      asOfDateEthnicity
      asOfDateGeneral
      asOfDateMorbidity
      asOfDateSex
      electronicInd
      personParentUid {
        id
      }
      dedupMatchInd
      groupNbr
      groupTime
      edxInd
      speaksEnglishCd
      additionalGenderCd
      eharsId
      ethnicUnkReasonCd
      sexUnkReasonCd
    }
    total
  }
}
    `;

/**
 * __useFindPatientsByOrganizationFilterQuery__
 *
 * To run a query within a React component, call `useFindPatientsByOrganizationFilterQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPatientsByOrganizationFilterQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPatientsByOrganizationFilterQuery({
 *   variables: {
 *      filter: // value for 'filter'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindPatientsByOrganizationFilterQuery(baseOptions: Apollo.QueryHookOptions<FindPatientsByOrganizationFilterQuery, FindPatientsByOrganizationFilterQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientsByOrganizationFilterQuery, FindPatientsByOrganizationFilterQueryVariables>(FindPatientsByOrganizationFilterDocument, options);
      }
export function useFindPatientsByOrganizationFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientsByOrganizationFilterQuery, FindPatientsByOrganizationFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientsByOrganizationFilterQuery, FindPatientsByOrganizationFilterQueryVariables>(FindPatientsByOrganizationFilterDocument, options);
        }
export type FindPatientsByOrganizationFilterQueryHookResult = ReturnType<typeof useFindPatientsByOrganizationFilterQuery>;
export type FindPatientsByOrganizationFilterLazyQueryHookResult = ReturnType<typeof useFindPatientsByOrganizationFilterLazyQuery>;
export type FindPatientsByOrganizationFilterQueryResult = Apollo.QueryResult<FindPatientsByOrganizationFilterQuery, FindPatientsByOrganizationFilterQueryVariables>;
export const FindPlaceByIdDocument = gql`
    query findPlaceById($id: ID!) {
  findPlaceById(id: $id) {
    id
    addReasonCd
    addTime
    addUserId
    cd
    cdDescTxt
    description
    durationAmt
    durationUnitCd
    fromTime
    lastChgReasonCd
    lastChgTime
    lastChgUserId
    localId
    nm
    recordStatusCd
    recordStatusTime
    statusCd
    statusTime
    toTime
    userAffiliationTxt
    streetAddr1
    streetAddr2
    cityCd
    cityDescTxt
    stateCd
    zipCd
    cntyCd
    cntryCd
    phoneNbr
    phoneCntryCd
    versionCtrlNbr
  }
}
    `;

/**
 * __useFindPlaceByIdQuery__
 *
 * To run a query within a React component, call `useFindPlaceByIdQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPlaceByIdQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPlaceByIdQuery({
 *   variables: {
 *      id: // value for 'id'
 *   },
 * });
 */
export function useFindPlaceByIdQuery(baseOptions: Apollo.QueryHookOptions<FindPlaceByIdQuery, FindPlaceByIdQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPlaceByIdQuery, FindPlaceByIdQueryVariables>(FindPlaceByIdDocument, options);
      }
export function useFindPlaceByIdLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPlaceByIdQuery, FindPlaceByIdQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPlaceByIdQuery, FindPlaceByIdQueryVariables>(FindPlaceByIdDocument, options);
        }
export type FindPlaceByIdQueryHookResult = ReturnType<typeof useFindPlaceByIdQuery>;
export type FindPlaceByIdLazyQueryHookResult = ReturnType<typeof useFindPlaceByIdLazyQuery>;
export type FindPlaceByIdQueryResult = Apollo.QueryResult<FindPlaceByIdQuery, FindPlaceByIdQueryVariables>;
export const FindPlacesByFilterDocument = gql`
    query findPlacesByFilter($filter: PlaceFilter!, $page: Page) {
  findPlacesByFilter(filter: $filter, page: $page) {
    id
    addReasonCd
    addTime
    addUserId
    cd
    cdDescTxt
    description
    durationAmt
    durationUnitCd
    fromTime
    lastChgReasonCd
    lastChgTime
    lastChgUserId
    localId
    nm
    recordStatusCd
    recordStatusTime
    statusCd
    statusTime
    toTime
    userAffiliationTxt
    streetAddr1
    streetAddr2
    cityCd
    cityDescTxt
    stateCd
    zipCd
    cntyCd
    cntryCd
    phoneNbr
    phoneCntryCd
    versionCtrlNbr
  }
}
    `;

/**
 * __useFindPlacesByFilterQuery__
 *
 * To run a query within a React component, call `useFindPlacesByFilterQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPlacesByFilterQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPlacesByFilterQuery({
 *   variables: {
 *      filter: // value for 'filter'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindPlacesByFilterQuery(baseOptions: Apollo.QueryHookOptions<FindPlacesByFilterQuery, FindPlacesByFilterQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPlacesByFilterQuery, FindPlacesByFilterQueryVariables>(FindPlacesByFilterDocument, options);
      }
export function useFindPlacesByFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPlacesByFilterQuery, FindPlacesByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPlacesByFilterQuery, FindPlacesByFilterQueryVariables>(FindPlacesByFilterDocument, options);
        }
export type FindPlacesByFilterQueryHookResult = ReturnType<typeof useFindPlacesByFilterQuery>;
export type FindPlacesByFilterLazyQueryHookResult = ReturnType<typeof useFindPlacesByFilterLazyQuery>;
export type FindPlacesByFilterQueryResult = Apollo.QueryResult<FindPlacesByFilterQuery, FindPlacesByFilterQueryVariables>;
export const FindSnomedCodedResultsDocument = gql`
    query findSnomedCodedResults($searchText: String!, $page: Page) {
  findSnomedCodedResults(searchText: $searchText, page: $page) {
    content {
      id
      snomedDescTxt
    }
    total
  }
}
    `;

/**
 * __useFindSnomedCodedResultsQuery__
 *
 * To run a query within a React component, call `useFindSnomedCodedResultsQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindSnomedCodedResultsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindSnomedCodedResultsQuery({
 *   variables: {
 *      searchText: // value for 'searchText'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindSnomedCodedResultsQuery(baseOptions: Apollo.QueryHookOptions<FindSnomedCodedResultsQuery, FindSnomedCodedResultsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindSnomedCodedResultsQuery, FindSnomedCodedResultsQueryVariables>(FindSnomedCodedResultsDocument, options);
      }
export function useFindSnomedCodedResultsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindSnomedCodedResultsQuery, FindSnomedCodedResultsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindSnomedCodedResultsQuery, FindSnomedCodedResultsQueryVariables>(FindSnomedCodedResultsDocument, options);
        }
export type FindSnomedCodedResultsQueryHookResult = ReturnType<typeof useFindSnomedCodedResultsQuery>;
export type FindSnomedCodedResultsLazyQueryHookResult = ReturnType<typeof useFindSnomedCodedResultsLazyQuery>;
export type FindSnomedCodedResultsQueryResult = Apollo.QueryResult<FindSnomedCodedResultsQuery, FindSnomedCodedResultsQueryVariables>;
export const FindTreatmentsForPatientDocument = gql`
    query findTreatmentsForPatient($patient: ID!) {
  findTreatmentsForPatient(patient: $patient) {
    treatment
    createdOn
    provider
    treatedOn
    description
    event
    associatedWith {
      id
      local
      condition
    }
  }
}
    `;

/**
 * __useFindTreatmentsForPatientQuery__
 *
 * To run a query within a React component, call `useFindTreatmentsForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindTreatmentsForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindTreatmentsForPatientQuery({
 *   variables: {
 *      patient: // value for 'patient'
 *   },
 * });
 */
export function useFindTreatmentsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>(FindTreatmentsForPatientDocument, options);
      }
export function useFindTreatmentsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>(FindTreatmentsForPatientDocument, options);
        }
export type FindTreatmentsForPatientQueryHookResult = ReturnType<typeof useFindTreatmentsForPatientQuery>;
export type FindTreatmentsForPatientLazyQueryHookResult = ReturnType<typeof useFindTreatmentsForPatientLazyQuery>;
export type FindTreatmentsForPatientQueryResult = Apollo.QueryResult<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>;