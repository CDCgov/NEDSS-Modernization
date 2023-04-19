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
  DateTime: any;
};

export type ActId = {
  __typename?: 'ActId';
  actIdSeq?: Maybe<Scalars['Int']>;
  id?: Maybe<Scalars['Int']>;
  lastChangeTime?: Maybe<Scalars['DateTime']>;
  recordStatus?: Maybe<Scalars['String']>;
  rootExtensionTxt?: Maybe<Scalars['String']>;
  typeCd?: Maybe<Scalars['String']>;
};

export type AssociatedInvestigation = {
  __typename?: 'AssociatedInvestigation';
  actRelationshipLastChgTime?: Maybe<Scalars['DateTime']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  lastChgTime?: Maybe<Scalars['DateTime']>;
  localId?: Maybe<Scalars['String']>;
  publicHealthCaseUid?: Maybe<Scalars['Int']>;
};

export enum CaseStatus {
  Confirmed = 'CONFIRMED',
  NotACase = 'NOT_A_CASE',
  Probable = 'PROBABLE',
  Suspect = 'SUSPECT',
  Unassigned = 'UNASSIGNED',
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

export type ContactsNamedByPatientResults = {
  __typename?: 'ContactsNamedByPatientResults';
  content: Array<Maybe<NamedByPatient>>;
  number: Scalars['Int'];
  total: Scalars['Int'];
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
  effectiveFromTime?: Maybe<Scalars['DateTime']>;
  effectiveToTime?: Maybe<Scalars['DateTime']>;
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
  statusTime?: Maybe<Scalars['DateTime']>;
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

export type GeneralInfoInput = {
  adultsInHouseNumber?: InputMaybe<Scalars['Int']>;
  asOf?: InputMaybe<Scalars['DateTime']>;
  childrenInHouseNumber?: InputMaybe<Scalars['Int']>;
  educationLevelCode?: InputMaybe<Scalars['String']>;
  eharsId?: InputMaybe<Scalars['String']>;
  maritalStatus?: InputMaybe<Scalars['String']>;
  mothersMaidenName?: InputMaybe<Scalars['String']>;
  occupationCode?: InputMaybe<Scalars['String']>;
  patientId: Scalars['ID'];
  primaryLanguageCode?: InputMaybe<Scalars['String']>;
  speaksEnglishCode?: InputMaybe<Scalars['String']>;
};

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
  activityFromTime?: Maybe<Scalars['DateTime']>;
  activityToTime?: Maybe<Scalars['DateTime']>;
  addTime?: Maybe<Scalars['DateTime']>;
  addUserId?: Maybe<Scalars['Int']>;
  caseClassCd?: Maybe<Scalars['String']>;
  caseTypeCd?: Maybe<Scalars['String']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  currProcessStateCd?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['ID']>;
  investigationStatusCd?: Maybe<Scalars['String']>;
  jurisdictionCd?: Maybe<Scalars['Int']>;
  jurisdictionCodeDescTxt?: Maybe<Scalars['String']>;
  lastChangeTime?: Maybe<Scalars['DateTime']>;
  lastChangeUserId?: Maybe<Scalars['Int']>;
  localId?: Maybe<Scalars['String']>;
  moodCd?: Maybe<Scalars['String']>;
  notificationAddTime?: Maybe<Scalars['DateTime']>;
  notificationLastChgTime?: Maybe<Scalars['DateTime']>;
  notificationLocalId?: Maybe<Scalars['String']>;
  notificationRecordStatusCd?: Maybe<Scalars['String']>;
  organizationParticipations?: Maybe<Array<Maybe<OrganizationParticipation>>>;
  outbreakName?: Maybe<Scalars['String']>;
  personParticipations?: Maybe<Array<Maybe<PersonParticipation>>>;
  pregnantIndCd?: Maybe<Scalars['String']>;
  progAreaCd?: Maybe<Scalars['String']>;
  publicHealthCaseLastChgTime?: Maybe<Scalars['DateTime']>;
  publicHealthCaseUid?: Maybe<Scalars['Int']>;
  recordStatus?: Maybe<Scalars['String']>;
  rptFormCmpltTime?: Maybe<Scalars['DateTime']>;
};

export type InvestigationEventDateSearch = {
  eventDateType: InvestigationEventDateType;
  from: Scalars['DateTime'];
  to: Scalars['DateTime'];
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
  effectiveFromTime?: Maybe<Scalars['DateTime']>;
  effectiveToTime?: Maybe<Scalars['DateTime']>;
  exportInd?: Maybe<Scalars['String']>;
  id: Scalars['String'];
  indentLevelNbr?: Maybe<Scalars['Int']>;
  isModifiableInd?: Maybe<Scalars['String']>;
  nbsUid?: Maybe<Scalars['ID']>;
  parentIsCd?: Maybe<Scalars['String']>;
  sourceConceptId?: Maybe<Scalars['String']>;
  stateDomainCd?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['DateTime']>;
  typeCd: Scalars['String'];
};

export type LabReport = {
  __typename?: 'LabReport';
  actIds?: Maybe<Array<Maybe<ActId>>>;
  activityToTime?: Maybe<Scalars['DateTime']>;
  addTime?: Maybe<Scalars['DateTime']>;
  addUserId?: Maybe<Scalars['Int']>;
  associatedInvestigations?: Maybe<Array<Maybe<AssociatedInvestigation>>>;
  cdDescTxt?: Maybe<Scalars['String']>;
  classCd?: Maybe<Scalars['String']>;
  effectiveFromTime?: Maybe<Scalars['DateTime']>;
  electronicInd?: Maybe<Scalars['String']>;
  id?: Maybe<Scalars['String']>;
  jurisdictionCd?: Maybe<Scalars['Int']>;
  jurisdictionCodeDescTxt?: Maybe<Scalars['String']>;
  lastChange?: Maybe<Scalars['DateTime']>;
  lastChgUserId?: Maybe<Scalars['Int']>;
  localId?: Maybe<Scalars['String']>;
  materialParticipations?: Maybe<Array<Maybe<MaterialParticipation>>>;
  moodCd?: Maybe<Scalars['String']>;
  observationLastChgTime?: Maybe<Scalars['DateTime']>;
  observationUid?: Maybe<Scalars['Int']>;
  observations?: Maybe<Array<Maybe<Observation>>>;
  organizationParticipations?: Maybe<Array<Maybe<OrganizationParticipation>>>;
  personParticipations?: Maybe<Array<Maybe<PersonParticipation>>>;
  pregnantIndCd?: Maybe<Scalars['String']>;
  programAreaCd?: Maybe<Scalars['String']>;
  recordStatusCd?: Maybe<Scalars['String']>;
  rptToStateTime?: Maybe<Scalars['DateTime']>;
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
  from: Scalars['DateTime'];
  to: Scalars['DateTime'];
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
  participationLastChangeTime?: Maybe<Scalars['DateTime']>;
  participationRecordStatus?: Maybe<Scalars['String']>;
  subjectClassCd?: Maybe<Scalars['String']>;
  typeCd?: Maybe<Scalars['String']>;
  typeDescTxt?: Maybe<Scalars['String']>;
};

export type MortalityInput = {
  asOf?: InputMaybe<Scalars['DateTime']>;
  cityOfDeath?: InputMaybe<Scalars['String']>;
  countryOfDeath?: InputMaybe<Scalars['String']>;
  countyOfDeath?: InputMaybe<Scalars['String']>;
  deceased?: InputMaybe<Deceased>;
  deceasedTime?: InputMaybe<Scalars['DateTime']>;
  patientId: Scalars['ID'];
  stateOfDeath?: InputMaybe<Scalars['String']>;
};

export type Mutation = {
  __typename?: 'Mutation';
  createPatient: PatientEventResponse;
  deletePatient: PatientEventResponse;
  updateMortality: PatientEventResponse;
  updatePatientGeneralInfo: PatientEventResponse;
  updatePatientSexBirth: PatientEventResponse;
};


export type MutationCreatePatientArgs = {
  patient: PersonInput;
};


export type MutationDeletePatientArgs = {
  patientId: Scalars['ID'];
};


export type MutationUpdateMortalityArgs = {
  input: MortalityInput;
};


export type MutationUpdatePatientGeneralInfoArgs = {
  input: GeneralInfoInput;
};


export type MutationUpdatePatientSexBirthArgs = {
  input: UpdateSexAndBirthInput;
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
  createdOn: Scalars['DateTime'];
  event: Scalars['String'];
  namedOn: Scalars['DateTime'];
};

export type NamedByPatient = {
  __typename?: 'NamedByPatient';
  associatedWith?: Maybe<PatientContactInvestigation>;
  condition?: Maybe<Scalars['String']>;
  contact: NamedContact;
  contactRecord: Scalars['ID'];
  createdOn: Scalars['DateTime'];
  disposition?: Maybe<Scalars['String']>;
  event: Scalars['String'];
  namedOn: Scalars['DateTime'];
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
  Rejected = 'REJECTED',
  Unassigned = 'UNASSIGNED'
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
  addTime?: Maybe<Scalars['DateTime']>;
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
  fromTime?: Maybe<Scalars['DateTime']>;
  id?: Maybe<Scalars['ID']>;
  lastChgReasonCd?: Maybe<Scalars['String']>;
  lastChgTime?: Maybe<Scalars['DateTime']>;
  lastChgUserId?: Maybe<Scalars['Int']>;
  localId?: Maybe<Scalars['String']>;
  phoneCntryCd?: Maybe<Scalars['String']>;
  phoneNbr?: Maybe<Scalars['String']>;
  recordStatusCd?: Maybe<RecordStatus>;
  recordStatusTime?: Maybe<Scalars['DateTime']>;
  standardIndustryClassCd?: Maybe<Scalars['String']>;
  standardIndustryDescTxt?: Maybe<Scalars['String']>;
  stateCd?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['DateTime']>;
  streetAddr1?: Maybe<Scalars['String']>;
  streetAddr2?: Maybe<Scalars['String']>;
  toTime?: Maybe<Scalars['DateTime']>;
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
  organizationLastChangeTime?: Maybe<Scalars['DateTime']>;
  participationLastChangeTime?: Maybe<Scalars['DateTime']>;
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

export type PatientAddress = {
  __typename?: 'PatientAddress';
  address1?: Maybe<Scalars['String']>;
  address2?: Maybe<Scalars['String']>;
  asOf: Scalars['DateTime'];
  censusTract?: Maybe<Scalars['String']>;
  city?: Maybe<Scalars['String']>;
  comment?: Maybe<Scalars['String']>;
  country?: Maybe<PatientCodedValue>;
  county?: Maybe<PatientCodedValue>;
  id: Scalars['ID'];
  patient: Scalars['Int'];
  state?: Maybe<PatientCodedValue>;
  type?: Maybe<PatientCodedValue>;
  use?: Maybe<PatientCodedValue>;
  version: Scalars['Int'];
  zipcode?: Maybe<Scalars['String']>;
};

export type PatientAddressResults = {
  __typename?: 'PatientAddressResults';
  content: Array<Maybe<PatientAddress>>;
  number: Scalars['Int'];
  size: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientAdministrative = {
  __typename?: 'PatientAdministrative';
  asOf: Scalars['DateTime'];
  comment?: Maybe<Scalars['String']>;
  id: Scalars['ID'];
  patient: Scalars['ID'];
  version: Scalars['Int'];
};

export type PatientAdministrativeResults = {
  __typename?: 'PatientAdministrativeResults';
  content: Array<Maybe<PatientAdministrative>>;
  number: Scalars['Int'];
  size: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientBirth = {
  __typename?: 'PatientBirth';
  age?: Maybe<Scalars['Int']>;
  asOf: Scalars['DateTime'];
  bornOn?: Maybe<Scalars['Date']>;
  city?: Maybe<Scalars['String']>;
  country?: Maybe<PatientCodedValue>;
  id: Scalars['ID'];
  multipleBirth?: Maybe<PatientCodedValue>;
  patient: Scalars['Int'];
  state?: Maybe<PatientCodedValue>;
  version: Scalars['Int'];
};

export type PatientCodedValue = {
  __typename?: 'PatientCodedValue';
  description: Scalars['String'];
  id: Scalars['String'];
};

export type PatientContactInvestigation = {
  __typename?: 'PatientContactInvestigation';
  condition: Scalars['String'];
  id: Scalars['ID'];
  local: Scalars['String'];
};

export type PatientDocument = {
  __typename?: 'PatientDocument';
  associatedWith?: Maybe<PatientDocumentInvestigation>;
  condition?: Maybe<Scalars['String']>;
  document: Scalars['ID'];
  event: Scalars['String'];
  receivedOn: Scalars['DateTime'];
  reportedOn: Scalars['DateTime'];
  sendingFacility: Scalars['String'];
  type: Scalars['String'];
};

export type PatientDocumentInvestigation = {
  __typename?: 'PatientDocumentInvestigation';
  id: Scalars['ID'];
  local: Scalars['String'];
};

export type PatientDocumentResults = {
  __typename?: 'PatientDocumentResults';
  content: Array<Maybe<PatientDocument>>;
  number: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientEthnicity = {
  __typename?: 'PatientEthnicity';
  asOf: Scalars['DateTime'];
  detailed: Array<Maybe<PatientCodedValue>>;
  ethnicGroup: PatientCodedValue;
  id: Scalars['ID'];
  patient: Scalars['Int'];
  unknownReason?: Maybe<PatientCodedValue>;
  version: Scalars['Int'];
};

export type PatientEventResponse = {
  __typename?: 'PatientEventResponse';
  patientId: Scalars['ID'];
  requestId: Scalars['String'];
};

export type PatientGender = {
  __typename?: 'PatientGender';
  additional?: Maybe<Scalars['String']>;
  asOf: Scalars['DateTime'];
  birth?: Maybe<PatientCodedValue>;
  current?: Maybe<PatientCodedValue>;
  id: Scalars['ID'];
  patient: Scalars['Int'];
  preferred?: Maybe<PatientCodedValue>;
  unknownReason?: Maybe<PatientCodedValue>;
  version: Scalars['Int'];
};

export type PatientGeneral = {
  __typename?: 'PatientGeneral';
  adultsInHouse?: Maybe<Scalars['Int']>;
  asOf: Scalars['DateTime'];
  childrenInHouse?: Maybe<Scalars['Int']>;
  educationLevel?: Maybe<PatientCodedValue>;
  id: Scalars['ID'];
  maritalStatus?: Maybe<PatientCodedValue>;
  maternalMaidenName?: Maybe<Scalars['String']>;
  occupation?: Maybe<PatientCodedValue>;
  patient: Scalars['Int'];
  primaryLanguage?: Maybe<PatientCodedValue>;
  speaksEnglish?: Maybe<PatientCodedValue>;
  stateHIVCase?: Maybe<Scalars['String']>;
  version: Scalars['Int'];
};

export type PatientIdentification = {
  __typename?: 'PatientIdentification';
  asOf: Scalars['DateTime'];
  authority?: Maybe<PatientCodedValue>;
  id: Scalars['ID'];
  patient: Scalars['Int'];
  sequence: Scalars['Int'];
  type: PatientCodedValue;
  value?: Maybe<Scalars['String']>;
  version: Scalars['Int'];
};

export type PatientIdentificationResults = {
  __typename?: 'PatientIdentificationResults';
  content: Array<Maybe<PatientIdentification>>;
  number: Scalars['Int'];
  size: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientIdentificationTypeResults = {
  __typename?: 'PatientIdentificationTypeResults';
  content: Array<Maybe<IdentificationType>>;
  total: Scalars['Int'];
};

export type PatientLegalName = {
  __typename?: 'PatientLegalName';
  first?: Maybe<Scalars['String']>;
  last?: Maybe<Scalars['String']>;
  middle?: Maybe<Scalars['String']>;
  prefix?: Maybe<Scalars['String']>;
  suffix?: Maybe<Scalars['String']>;
};

export type PatientMorbidity = {
  __typename?: 'PatientMorbidity';
  associatedWith?: Maybe<PatientMorbidityInvestigation>;
  condition: Scalars['String'];
  event: Scalars['String'];
  jurisdiction: Scalars['String'];
  labResults: Array<Maybe<PatientMorbidityLabResult>>;
  morbidity: Scalars['ID'];
  provider?: Maybe<Scalars['String']>;
  receivedOn: Scalars['DateTime'];
  reportedOn: Scalars['DateTime'];
  treatments: Array<Maybe<Scalars['String']>>;
};

export type PatientMorbidityInvestigation = {
  __typename?: 'PatientMorbidityInvestigation';
  condition: Scalars['String'];
  id: Scalars['ID'];
  local: Scalars['String'];
};

export type PatientMorbidityLabResult = {
  __typename?: 'PatientMorbidityLabResult';
  codedResult?: Maybe<Scalars['String']>;
  labTest: Scalars['String'];
  numericResult?: Maybe<Scalars['String']>;
  status?: Maybe<Scalars['String']>;
  textResult?: Maybe<Scalars['String']>;
};

export type PatientMorbidityResults = {
  __typename?: 'PatientMorbidityResults';
  content: Array<Maybe<PatientMorbidity>>;
  number: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientMortality = {
  __typename?: 'PatientMortality';
  asOf: Scalars['DateTime'];
  city?: Maybe<Scalars['String']>;
  country?: Maybe<PatientCodedValue>;
  deceased?: Maybe<PatientCodedValue>;
  deceasedOn?: Maybe<Scalars['Date']>;
  id: Scalars['ID'];
  patient: Scalars['Int'];
  state?: Maybe<PatientCodedValue>;
  version: Scalars['Int'];
};

export type PatientName = {
  __typename?: 'PatientName';
  asOf: Scalars['DateTime'];
  degree?: Maybe<PatientCodedValue>;
  first?: Maybe<Scalars['String']>;
  last?: Maybe<Scalars['String']>;
  middle?: Maybe<Scalars['String']>;
  patient: Scalars['ID'];
  prefix?: Maybe<PatientCodedValue>;
  secondLast?: Maybe<Scalars['String']>;
  secondMiddle?: Maybe<Scalars['String']>;
  sequence: Scalars['Int'];
  suffix?: Maybe<PatientCodedValue>;
  use: PatientCodedValue;
  version: Scalars['Int'];
};

export type PatientNameResults = {
  __typename?: 'PatientNameResults';
  content: Array<Maybe<PatientName>>;
  number: Scalars['Int'];
  size: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientNamedByContactResults = {
  __typename?: 'PatientNamedByContactResults';
  content: Array<Maybe<NamedByPatient>>;
  number: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientPhone = {
  __typename?: 'PatientPhone';
  asOf: Scalars['DateTime'];
  comment?: Maybe<Scalars['String']>;
  countryCode?: Maybe<Scalars['String']>;
  email?: Maybe<Scalars['String']>;
  extension?: Maybe<Scalars['String']>;
  id: Scalars['ID'];
  number?: Maybe<Scalars['String']>;
  patient: Scalars['Int'];
  type?: Maybe<PatientCodedValue>;
  url?: Maybe<Scalars['String']>;
  use?: Maybe<PatientCodedValue>;
  version: Scalars['Int'];
};

export type PatientPhoneResults = {
  __typename?: 'PatientPhoneResults';
  content: Array<Maybe<PatientPhone>>;
  number: Scalars['Int'];
  size: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientProfile = {
  __typename?: 'PatientProfile';
  addresses?: Maybe<PatientAddressResults>;
  administrative?: Maybe<PatientAdministrativeResults>;
  birth?: Maybe<PatientBirth>;
  ethnicity?: Maybe<PatientEthnicity>;
  gender?: Maybe<PatientGender>;
  general?: Maybe<PatientGeneral>;
  id: Scalars['ID'];
  identification?: Maybe<PatientIdentificationResults>;
  local: Scalars['String'];
  mortality?: Maybe<PatientMortality>;
  names?: Maybe<PatientNameResults>;
  phones?: Maybe<PatientPhoneResults>;
  races?: Maybe<PatientRaceResults>;
  shortId?: Maybe<Scalars['Int']>;
  summary?: Maybe<PatientSummary>;
  version: Scalars['Int'];
};


export type PatientProfileAddressesArgs = {
  page?: InputMaybe<Page>;
};


export type PatientProfileAdministrativeArgs = {
  page?: InputMaybe<Page>;
};


export type PatientProfileIdentificationArgs = {
  page?: InputMaybe<Page>;
};


export type PatientProfileNamesArgs = {
  page?: InputMaybe<Page>;
};


export type PatientProfilePhonesArgs = {
  page?: InputMaybe<Page>;
};


export type PatientProfileRacesArgs = {
  page?: InputMaybe<Page>;
};


export type PatientProfileSummaryArgs = {
  asOf?: InputMaybe<Scalars['DateTime']>;
};

export type PatientRace = {
  __typename?: 'PatientRace';
  asOf: Scalars['DateTime'];
  category: PatientCodedValue;
  detailed?: Maybe<Array<Maybe<PatientCodedValue>>>;
  id: Scalars['ID'];
  patient: Scalars['Int'];
  version: Scalars['Int'];
};

export type PatientRaceResults = {
  __typename?: 'PatientRaceResults';
  content: Array<Maybe<PatientRace>>;
  number: Scalars['Int'];
  size: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientSummary = {
  __typename?: 'PatientSummary';
  address?: Maybe<PatientSummaryAddress>;
  age?: Maybe<Scalars['Int']>;
  birthday?: Maybe<Scalars['Date']>;
  email?: Maybe<Array<Maybe<PatientSummaryEmail>>>;
  ethnicity?: Maybe<Scalars['String']>;
  gender?: Maybe<Scalars['String']>;
  legalName?: Maybe<PatientLegalName>;
  phone?: Maybe<Array<Maybe<PatientSummaryPhone>>>;
  race?: Maybe<Scalars['String']>;
};

export type PatientSummaryAddress = {
  __typename?: 'PatientSummaryAddress';
  city?: Maybe<Scalars['String']>;
  country?: Maybe<Scalars['String']>;
  state?: Maybe<Scalars['String']>;
  street?: Maybe<Scalars['String']>;
  zipcode?: Maybe<Scalars['String']>;
};

export type PatientSummaryEmail = {
  __typename?: 'PatientSummaryEmail';
  address?: Maybe<Scalars['String']>;
  use?: Maybe<Scalars['String']>;
};

export type PatientSummaryPhone = {
  __typename?: 'PatientSummaryPhone';
  number?: Maybe<Scalars['String']>;
  use?: Maybe<Scalars['String']>;
};

export type PatientTreatment = {
  __typename?: 'PatientTreatment';
  associatedWith: PatientTreatmentInvestigation;
  createdOn: Scalars['DateTime'];
  description: Scalars['String'];
  event: Scalars['String'];
  provider?: Maybe<Scalars['String']>;
  treatedOn: Scalars['DateTime'];
  treatment: Scalars['ID'];
};

export type PatientTreatmentInvestigation = {
  __typename?: 'PatientTreatmentInvestigation';
  condition: Scalars['String'];
  id: Scalars['ID'];
  local: Scalars['String'];
};

export type PatientTreatmentResults = {
  __typename?: 'PatientTreatmentResults';
  content: Array<Maybe<PatientTreatment>>;
  number: Scalars['Int'];
  total: Scalars['Int'];
};

export type PatientVaccination = {
  __typename?: 'PatientVaccination';
  administered: Scalars['String'];
  administeredOn: Scalars['DateTime'];
  associatedWith?: Maybe<PatientVaccinationInvestigation>;
  createdOn: Scalars['DateTime'];
  event: Scalars['String'];
  provider?: Maybe<Scalars['String']>;
  vaccination: Scalars['ID'];
};

export type PatientVaccinationInvestigation = {
  __typename?: 'PatientVaccinationInvestigation';
  condition: Scalars['String'];
  id: Scalars['ID'];
  local: Scalars['String'];
};

export type PatientVaccinationResults = {
  __typename?: 'PatientVaccinationResults';
  content: Array<Maybe<PatientVaccination>>;
  number: Scalars['Int'];
  total: Scalars['Int'];
};

export type Person = {
  __typename?: 'Person';
  addReasonCd?: Maybe<Scalars['String']>;
  addTime?: Maybe<Scalars['DateTime']>;
  addUserId?: Maybe<Scalars['ID']>;
  additionalGenderCd?: Maybe<Scalars['String']>;
  administrativeGenderCd?: Maybe<Scalars['String']>;
  adultsInHouseNbr?: Maybe<Scalars['Int']>;
  ageCalc?: Maybe<Scalars['Int']>;
  ageCalcTime?: Maybe<Scalars['DateTime']>;
  ageCalcUnitCd?: Maybe<Scalars['String']>;
  ageCategoryCd?: Maybe<Scalars['String']>;
  ageReported?: Maybe<Scalars['String']>;
  ageReportedTime?: Maybe<Scalars['DateTime']>;
  ageReportedUnitCd?: Maybe<Scalars['String']>;
  asOfDateAdmin?: Maybe<Scalars['DateTime']>;
  asOfDateEthnicity?: Maybe<Scalars['DateTime']>;
  asOfDateGeneral?: Maybe<Scalars['DateTime']>;
  asOfDateMorbidity?: Maybe<Scalars['DateTime']>;
  asOfDateSex?: Maybe<Scalars['DateTime']>;
  birthCityCd?: Maybe<Scalars['String']>;
  birthCityDescTxt?: Maybe<Scalars['String']>;
  birthCntryCd?: Maybe<Scalars['String']>;
  birthGenderCd?: Maybe<Gender>;
  birthOrderNbr?: Maybe<Scalars['Int']>;
  birthStateCd?: Maybe<Scalars['String']>;
  birthTime?: Maybe<Scalars['DateTime']>;
  birthTimeCalc?: Maybe<Scalars['DateTime']>;
  cd?: Maybe<Scalars['String']>;
  cdDescTxt?: Maybe<Scalars['String']>;
  cellPhoneNbr?: Maybe<Scalars['String']>;
  childrenInHouseNbr?: Maybe<Scalars['Int']>;
  currSexCd?: Maybe<Scalars['String']>;
  deceasedIndCd?: Maybe<Scalars['String']>;
  deceasedTime?: Maybe<Scalars['DateTime']>;
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
  groupTime?: Maybe<Scalars['DateTime']>;
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
  lastChgTime?: Maybe<Scalars['DateTime']>;
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
  preferredGenderCd?: Maybe<Scalars['String']>;
  preferredNm?: Maybe<Scalars['String']>;
  primLangCd?: Maybe<Scalars['String']>;
  primLangDescTxt?: Maybe<Scalars['String']>;
  raceCategoryCd?: Maybe<Scalars['String']>;
  raceCd?: Maybe<Scalars['String']>;
  raceDescTxt?: Maybe<Scalars['String']>;
  raceSeqNbr?: Maybe<Scalars['Int']>;
  races?: Maybe<Array<Maybe<PersonRace>>>;
  recordStatusCd?: Maybe<RecordStatus>;
  recordStatusTime?: Maybe<Scalars['DateTime']>;
  sexUnkReasonCd?: Maybe<Scalars['String']>;
  shortId?: Maybe<Scalars['Int']>;
  speaksEnglishCd?: Maybe<Scalars['String']>;
  ssn?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['DateTime']>;
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
  adultNbrInHouse?: InputMaybe<Scalars['Int']>;
  asOf?: InputMaybe<Scalars['DateTime']>;
  birthGender?: InputMaybe<Gender>;
  childrenNbrinHouse?: InputMaybe<Scalars['Int']>;
  comments?: InputMaybe<Scalars['String']>;
  currentGender?: InputMaybe<Gender>;
  deceased?: InputMaybe<Deceased>;
  deceasedTime?: InputMaybe<Scalars['DateTime']>;
  emailAddresses?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  ethnicityCode?: InputMaybe<Scalars['String']>;
  hIVCaseId?: InputMaybe<Scalars['String']>;
  highestEducationLvl?: InputMaybe<Scalars['String']>;
  identifications?: InputMaybe<Array<InputMaybe<Identification>>>;
  maritalStatus?: InputMaybe<Scalars['String']>;
  mothersMaidenName?: InputMaybe<Scalars['String']>;
  names?: InputMaybe<Array<InputMaybe<Name>>>;
  phoneNumbers?: InputMaybe<Array<InputMaybe<PhoneNumber>>>;
  primaryLang?: InputMaybe<Scalars['String']>;
  primaryOccupation?: InputMaybe<Scalars['String']>;
  raceCodes?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  speaksEnglish?: InputMaybe<Scalars['String']>;
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
  birthTime?: Maybe<Scalars['DateTime']>;
  currSexCd?: Maybe<Scalars['String']>;
  entityId: Scalars['Int'];
  firstName?: Maybe<Scalars['String']>;
  lastName?: Maybe<Scalars['String']>;
  localId?: Maybe<Scalars['String']>;
  participationLastChangeTime?: Maybe<Scalars['DateTime']>;
  participationRecordStatus?: Maybe<Scalars['String']>;
  personCd: Scalars['String'];
  personLastChangeTime?: Maybe<Scalars['DateTime']>;
  personParentUid?: Maybe<Scalars['Int']>;
  personRecordStatus: Scalars['String'];
  subjectClassCd?: Maybe<Scalars['String']>;
  typeCd?: Maybe<Scalars['String']>;
  typeDescTxt?: Maybe<Scalars['String']>;
};

export type PersonRace = {
  __typename?: 'PersonRace';
  raceCd?: Maybe<Scalars['String']>;
  recordStatusCd?: Maybe<Scalars['String']>;
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
  addTime?: Maybe<Scalars['DateTime']>;
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
  fromTime?: Maybe<Scalars['DateTime']>;
  id?: Maybe<Scalars['ID']>;
  lastChgReasonCd?: Maybe<Scalars['String']>;
  lastChgTime?: Maybe<Scalars['DateTime']>;
  lastChgUserId?: Maybe<Scalars['Int']>;
  localId?: Maybe<Scalars['String']>;
  nm?: Maybe<Scalars['String']>;
  phoneCntryCd?: Maybe<Scalars['String']>;
  phoneNbr?: Maybe<Scalars['String']>;
  recordStatusCd?: Maybe<Scalars['String']>;
  recordStatusTime?: Maybe<Scalars['DateTime']>;
  stateCd?: Maybe<Scalars['String']>;
  statusCd?: Maybe<Scalars['String']>;
  statusTime?: Maybe<Scalars['DateTime']>;
  streetAddr1?: Maybe<Scalars['String']>;
  streetAddr2?: Maybe<Scalars['String']>;
  toTime?: Maybe<Scalars['DateTime']>;
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
  SurveillanceFollowUp = 'SURVEILLANCE_FOLLOW_UP',
  Unassigned = 'UNASSIGNED'
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
  statusTime?: Maybe<Scalars['DateTime']>;
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
  findContactsNamedByPatient?: Maybe<ContactsNamedByPatientResults>;
  findDocumentsForPatient?: Maybe<PatientDocumentResults>;
  findDocumentsRequiringReviewForPatient: LabReportResults;
  findInvestigationsByFilter: InvestigationResults;
  findLabReportsByFilter: LabReportResults;
  findLocalCodedResults: LocalCodedResults;
  findLocalLabTest: LocalLabTestResults;
  findLoincLabTest: LoincLabTestResults;
  findMorbidityReportsForPatient?: Maybe<PatientMorbidityResults>;
  findOpenInvestigationsForPatient: InvestigationResults;
  findOrganizationById?: Maybe<Organization>;
  findOrganizationsByFilter: OrganizationResults;
  findPatientById?: Maybe<Person>;
  findPatientNamedByContact?: Maybe<PatientNamedByContactResults>;
  findPatientProfile?: Maybe<PatientProfile>;
  findPatientsByFilter: PersonResults;
  findPatientsByOrganizationFilter: PersonResults;
  findPlaceById?: Maybe<Place>;
  findPlacesByFilter: Array<Maybe<Place>>;
  findSnomedCodedResults: SnomedCodedResults;
  findTreatmentsForPatient?: Maybe<PatientTreatmentResults>;
  findVaccinationsForPatient?: Maybe<PatientVaccinationResults>;
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


export type QueryFindContactsNamedByPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID'];
};


export type QueryFindDocumentsForPatientArgs = {
  page?: InputMaybe<Page>;
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


export type QueryFindMorbidityReportsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID'];
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


export type QueryFindPatientNamedByContactArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID'];
};


export type QueryFindPatientProfileArgs = {
  patient?: InputMaybe<Scalars['ID']>;
  shortId?: InputMaybe<Scalars['Int']>;
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
  page?: InputMaybe<Page>;
  patient: Scalars['ID'];
};


export type QueryFindVaccinationsForPatientArgs = {
  page?: InputMaybe<Page>;
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
  effectiveFromTime?: Maybe<Scalars['DateTime']>;
  effectiveToTime?: Maybe<Scalars['DateTime']>;
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
  statusTime?: Maybe<Scalars['DateTime']>;
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

export type UpdateSexAndBirthInput = {
  additionalGender?: InputMaybe<Scalars['String']>;
  ageReportedTime?: InputMaybe<Scalars['DateTime']>;
  asOf?: InputMaybe<Scalars['DateTime']>;
  birthCity?: InputMaybe<Scalars['String']>;
  birthCntry?: InputMaybe<Scalars['String']>;
  birthGender?: InputMaybe<Gender>;
  birthOrderNbr?: InputMaybe<Scalars['Int']>;
  birthState?: InputMaybe<Scalars['String']>;
  currentAge?: InputMaybe<Scalars['String']>;
  currentGender?: InputMaybe<Gender>;
  dateOfBirth?: InputMaybe<Scalars['Date']>;
  multipleBirth?: InputMaybe<Scalars['String']>;
  patientId: Scalars['ID'];
  sexUnknown?: InputMaybe<Scalars['String']>;
  transGenderInfo?: InputMaybe<Scalars['String']>;
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


export type CreatePatientMutation = { __typename?: 'Mutation', createPatient: { __typename?: 'PatientEventResponse', requestId: string, patientId: string } };

export type DeletePatientMutationVariables = Exact<{
  patientId: Scalars['ID'];
}>;


export type DeletePatientMutation = { __typename?: 'Mutation', deletePatient: { __typename?: 'PatientEventResponse', requestId: string, patientId: string } };

export type UpdateMortalityMutationVariables = Exact<{
  input: MortalityInput;
}>;


export type UpdateMortalityMutation = { __typename?: 'Mutation', updateMortality: { __typename?: 'PatientEventResponse', requestId: string, patientId: string } };

export type UpdatePatientGeneralInfoMutationVariables = Exact<{
  input: GeneralInfoInput;
}>;


export type UpdatePatientGeneralInfoMutation = { __typename?: 'Mutation', updatePatientGeneralInfo: { __typename?: 'PatientEventResponse', requestId: string, patientId: string } };

export type UpdatePatientSexBirthMutationVariables = Exact<{
  input: UpdateSexAndBirthInput;
}>;


export type UpdatePatientSexBirthMutation = { __typename?: 'Mutation', updatePatientSexBirth: { __typename?: 'PatientEventResponse', requestId: string, patientId: string } };

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


export type FindAllPatientsQuery = { __typename?: 'Query', findAllPatients: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', shortId?: number | null, id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: string | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: string | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: string | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, races?: Array<{ __typename?: 'PersonRace', raceCd?: string | null, recordStatusCd?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null }> } };

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

export type FindContactsNamedByPatientQueryVariables = Exact<{
  patient: Scalars['ID'];
  page?: InputMaybe<Page>;
}>;


export type FindContactsNamedByPatientQuery = { __typename?: 'Query', findContactsNamedByPatient?: { __typename?: 'ContactsNamedByPatientResults', total: number, number: number, content: Array<{ __typename?: 'NamedByPatient', contactRecord: string, createdOn: any, condition?: string | null, namedOn: any, priority?: string | null, disposition?: string | null, event: string, contact: { __typename?: 'NamedContact', id: string, name: string }, associatedWith?: { __typename?: 'PatientContactInvestigation', id: string, local: string, condition: string } | null } | null> } | null };

export type FindDocumentsForPatientQueryVariables = Exact<{
  patient: Scalars['ID'];
  page?: InputMaybe<Page>;
}>;


export type FindDocumentsForPatientQuery = { __typename?: 'Query', findDocumentsForPatient?: { __typename?: 'PatientDocumentResults', total: number, number: number, content: Array<{ __typename?: 'PatientDocument', document: string, receivedOn: any, type: string, sendingFacility: string, reportedOn: any, condition?: string | null, event: string, associatedWith?: { __typename?: 'PatientDocumentInvestigation', id: string, local: string } | null } | null> } | null };

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

export type FindMorbidityReportsForPatientQueryVariables = Exact<{
  patient: Scalars['ID'];
  page?: InputMaybe<Page>;
}>;


export type FindMorbidityReportsForPatientQuery = { __typename?: 'Query', findMorbidityReportsForPatient?: { __typename?: 'PatientMorbidityResults', total: number, number: number, content: Array<{ __typename?: 'PatientMorbidity', morbidity: string, receivedOn: any, provider?: string | null, reportedOn: any, condition: string, jurisdiction: string, event: string, treatments: Array<string | null>, associatedWith?: { __typename?: 'PatientMorbidityInvestigation', id: string, local: string, condition: string } | null, labResults: Array<{ __typename?: 'PatientMorbidityLabResult', labTest: string, status?: string | null, codedResult?: string | null, numericResult?: string | null, textResult?: string | null } | null> } | null> } | null };

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


export type FindPatientByIdQuery = { __typename?: 'Query', findPatientById?: { __typename?: 'Person', shortId?: number | null, id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: string | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: string | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: string | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, races?: Array<{ __typename?: 'PersonRace', raceCd?: string | null, recordStatusCd?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null } | null };

export type FindPatientNamedByContactQueryVariables = Exact<{
  patient: Scalars['ID'];
  page?: InputMaybe<Page>;
}>;


export type FindPatientNamedByContactQuery = { __typename?: 'Query', findPatientNamedByContact?: { __typename?: 'PatientNamedByContactResults', total: number, number: number, content: Array<{ __typename?: 'NamedByPatient', contactRecord: string, createdOn: any, condition?: string | null, namedOn: any, priority?: string | null, disposition?: string | null, event: string, contact: { __typename?: 'NamedContact', id: string, name: string }, associatedWith?: { __typename?: 'PatientContactInvestigation', id: string, local: string, condition: string } | null } | null> } | null };

export type FindPatientProfileQueryVariables = Exact<{
  asOf?: InputMaybe<Scalars['DateTime']>;
  page?: InputMaybe<Page>;
  page1?: InputMaybe<Page>;
  page2?: InputMaybe<Page>;
  page3?: InputMaybe<Page>;
  page4?: InputMaybe<Page>;
  page5?: InputMaybe<Page>;
  patient?: InputMaybe<Scalars['ID']>;
  shortId?: InputMaybe<Scalars['Int']>;
}>;


export type FindPatientProfileQuery = { __typename?: 'Query', findPatientProfile?: { __typename?: 'PatientProfile', id: string, local: string, shortId?: number | null, version: number, summary?: { __typename?: 'PatientSummary', birthday?: any | null, age?: number | null, gender?: string | null, ethnicity?: string | null, race?: string | null, legalName?: { __typename?: 'PatientLegalName', prefix?: string | null, first?: string | null, middle?: string | null, last?: string | null, suffix?: string | null } | null, phone?: Array<{ __typename?: 'PatientSummaryPhone', use?: string | null, number?: string | null } | null> | null, email?: Array<{ __typename?: 'PatientSummaryEmail', use?: string | null, address?: string | null } | null> | null, address?: { __typename?: 'PatientSummaryAddress', street?: string | null, city?: string | null, state?: string | null, zipcode?: string | null, country?: string | null } | null } | null, names?: { __typename?: 'PatientNameResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientName', patient: string, version: number, asOf: any, sequence: number, first?: string | null, middle?: string | null, secondMiddle?: string | null, last?: string | null, secondLast?: string | null, use: { __typename?: 'PatientCodedValue', id: string, description: string }, prefix?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, suffix?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, degree?: { __typename?: 'PatientCodedValue', id: string, description: string } | null } | null> } | null, administrative?: { __typename?: 'PatientAdministrativeResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientAdministrative', patient: string, id: string, version: number, asOf: any, comment?: string | null } | null> } | null, addresses?: { __typename?: 'PatientAddressResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientAddress', patient: number, id: string, version: number, asOf: any, address1?: string | null, address2?: string | null, city?: string | null, zipcode?: string | null, censusTract?: string | null, comment?: string | null, type?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, county?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, state?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, country?: { __typename?: 'PatientCodedValue', id: string, description: string } | null } | null> } | null, phones?: { __typename?: 'PatientPhoneResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientPhone', patient: number, id: string, version: number, asOf: any, countryCode?: string | null, number?: string | null, extension?: string | null, email?: string | null, url?: string | null, comment?: string | null } | null> } | null, identification?: { __typename?: 'PatientIdentificationResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientIdentification', patient: number, id: string, sequence: number, version: number, asOf: any, value?: string | null, authority?: { __typename?: 'PatientCodedValue', id: string, description: string } | null } | null> } | null, races?: { __typename?: 'PatientRaceResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientRace', patient: number, id: string, version: number, asOf: any, category: { __typename?: 'PatientCodedValue', id: string, description: string }, detailed?: Array<{ __typename?: 'PatientCodedValue', id: string, description: string } | null> | null } | null> } | null, birth?: { __typename?: 'PatientBirth', patient: number, id: string, version: number, asOf: any, bornOn?: any | null, age?: number | null, city?: string | null, multipleBirth?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, state?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, country?: { __typename?: 'PatientCodedValue', id: string, description: string } | null } | null, gender?: { __typename?: 'PatientGender', patient: number, id: string, version: number, asOf: any, additional?: string | null, birth?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, current?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, unknownReason?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, preferred?: { __typename?: 'PatientCodedValue', id: string, description: string } | null } | null, mortality?: { __typename?: 'PatientMortality', patient: number, id: string, version: number, asOf: any, deceasedOn?: any | null, city?: string | null, deceased?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, state?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, country?: { __typename?: 'PatientCodedValue', id: string, description: string } | null } | null, general?: { __typename?: 'PatientGeneral', patient: number, id: string, version: number, asOf: any, maternalMaidenName?: string | null, adultsInHouse?: number | null, childrenInHouse?: number | null, stateHIVCase?: string | null, maritalStatus?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, occupation?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, educationLevel?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, primaryLanguage?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, speaksEnglish?: { __typename?: 'PatientCodedValue', id: string, description: string } | null } | null, ethnicity?: { __typename?: 'PatientEthnicity', patient: number, id: string, version: number, asOf: any, ethnicGroup: { __typename?: 'PatientCodedValue', id: string, description: string }, unknownReason?: { __typename?: 'PatientCodedValue', id: string, description: string } | null, detailed: Array<{ __typename?: 'PatientCodedValue', id: string, description: string } | null> } | null } | null };

export type FindPatientsByFilterQueryVariables = Exact<{
  filter: PersonFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindPatientsByFilterQuery = { __typename?: 'Query', findPatientsByFilter: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', shortId?: number | null, id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: string | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: string | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: string | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, races?: Array<{ __typename?: 'PersonRace', raceCd?: string | null, recordStatusCd?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null }> } };

export type FindPatientsByOrganizationFilterQueryVariables = Exact<{
  filter: OrganizationFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindPatientsByOrganizationFilterQuery = { __typename?: 'Query', findPatientsByOrganizationFilter: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', shortId?: number | null, id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: string | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: string | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: string | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, nbsEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, typeCd?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, races?: Array<{ __typename?: 'PersonRace', raceCd?: string | null, recordStatusCd?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null, personParentUid?: { __typename?: 'personParentUid', id?: string | null } | null }> } };

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
  page?: InputMaybe<Page>;
}>;


export type FindTreatmentsForPatientQuery = { __typename?: 'Query', findTreatmentsForPatient?: { __typename?: 'PatientTreatmentResults', total: number, number: number, content: Array<{ __typename?: 'PatientTreatment', treatment: string, createdOn: any, provider?: string | null, treatedOn: any, description: string, event: string, associatedWith: { __typename?: 'PatientTreatmentInvestigation', id: string, local: string, condition: string } } | null> } | null };

export type FindVaccinationsForPatientQueryVariables = Exact<{
  patient: Scalars['ID'];
  page?: InputMaybe<Page>;
}>;


export type FindVaccinationsForPatientQuery = { __typename?: 'Query', findVaccinationsForPatient?: { __typename?: 'PatientVaccinationResults', total: number, number: number, content: Array<{ __typename?: 'PatientVaccination', vaccination: string, createdOn: any, provider?: string | null, administeredOn: any, administered: string, event: string, associatedWith?: { __typename?: 'PatientVaccinationInvestigation', id: string, local: string, condition: string } | null } | null> } | null };


export const CreatePatientDocument = gql`
    mutation createPatient($patient: PersonInput!) {
  createPatient(patient: $patient) {
    requestId
    patientId
  }
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
export const DeletePatientDocument = gql`
    mutation deletePatient($patientId: ID!) {
  deletePatient(patientId: $patientId) {
    requestId
    patientId
  }
}
    `;
export type DeletePatientMutationFn = Apollo.MutationFunction<DeletePatientMutation, DeletePatientMutationVariables>;

/**
 * __useDeletePatientMutation__
 *
 * To run a mutation, you first call `useDeletePatientMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeletePatientMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deletePatientMutation, { data, loading, error }] = useDeletePatientMutation({
 *   variables: {
 *      patientId: // value for 'patientId'
 *   },
 * });
 */
export function useDeletePatientMutation(baseOptions?: Apollo.MutationHookOptions<DeletePatientMutation, DeletePatientMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<DeletePatientMutation, DeletePatientMutationVariables>(DeletePatientDocument, options);
      }
export type DeletePatientMutationHookResult = ReturnType<typeof useDeletePatientMutation>;
export type DeletePatientMutationResult = Apollo.MutationResult<DeletePatientMutation>;
export type DeletePatientMutationOptions = Apollo.BaseMutationOptions<DeletePatientMutation, DeletePatientMutationVariables>;
export const UpdateMortalityDocument = gql`
    mutation updateMortality($input: MortalityInput!) {
  updateMortality(input: $input) {
    requestId
    patientId
  }
}
    `;
export type UpdateMortalityMutationFn = Apollo.MutationFunction<UpdateMortalityMutation, UpdateMortalityMutationVariables>;

/**
 * __useUpdateMortalityMutation__
 *
 * To run a mutation, you first call `useUpdateMortalityMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdateMortalityMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updateMortalityMutation, { data, loading, error }] = useUpdateMortalityMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdateMortalityMutation(baseOptions?: Apollo.MutationHookOptions<UpdateMortalityMutation, UpdateMortalityMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdateMortalityMutation, UpdateMortalityMutationVariables>(UpdateMortalityDocument, options);
      }
export type UpdateMortalityMutationHookResult = ReturnType<typeof useUpdateMortalityMutation>;
export type UpdateMortalityMutationResult = Apollo.MutationResult<UpdateMortalityMutation>;
export type UpdateMortalityMutationOptions = Apollo.BaseMutationOptions<UpdateMortalityMutation, UpdateMortalityMutationVariables>;
export const UpdatePatientGeneralInfoDocument = gql`
    mutation updatePatientGeneralInfo($input: GeneralInfoInput!) {
  updatePatientGeneralInfo(input: $input) {
    requestId
    patientId
  }
}
    `;
export type UpdatePatientGeneralInfoMutationFn = Apollo.MutationFunction<UpdatePatientGeneralInfoMutation, UpdatePatientGeneralInfoMutationVariables>;

/**
 * __useUpdatePatientGeneralInfoMutation__
 *
 * To run a mutation, you first call `useUpdatePatientGeneralInfoMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientGeneralInfoMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientGeneralInfoMutation, { data, loading, error }] = useUpdatePatientGeneralInfoMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientGeneralInfoMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientGeneralInfoMutation, UpdatePatientGeneralInfoMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientGeneralInfoMutation, UpdatePatientGeneralInfoMutationVariables>(UpdatePatientGeneralInfoDocument, options);
      }
export type UpdatePatientGeneralInfoMutationHookResult = ReturnType<typeof useUpdatePatientGeneralInfoMutation>;
export type UpdatePatientGeneralInfoMutationResult = Apollo.MutationResult<UpdatePatientGeneralInfoMutation>;
export type UpdatePatientGeneralInfoMutationOptions = Apollo.BaseMutationOptions<UpdatePatientGeneralInfoMutation, UpdatePatientGeneralInfoMutationVariables>;
export const UpdatePatientSexBirthDocument = gql`
    mutation updatePatientSexBirth($input: UpdateSexAndBirthInput!) {
  updatePatientSexBirth(input: $input) {
    requestId
    patientId
  }
}
    `;
export type UpdatePatientSexBirthMutationFn = Apollo.MutationFunction<UpdatePatientSexBirthMutation, UpdatePatientSexBirthMutationVariables>;

/**
 * __useUpdatePatientSexBirthMutation__
 *
 * To run a mutation, you first call `useUpdatePatientSexBirthMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientSexBirthMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientSexBirthMutation, { data, loading, error }] = useUpdatePatientSexBirthMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientSexBirthMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientSexBirthMutation, UpdatePatientSexBirthMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientSexBirthMutation, UpdatePatientSexBirthMutationVariables>(UpdatePatientSexBirthDocument, options);
      }
export type UpdatePatientSexBirthMutationHookResult = ReturnType<typeof useUpdatePatientSexBirthMutation>;
export type UpdatePatientSexBirthMutationResult = Apollo.MutationResult<UpdatePatientSexBirthMutation>;
export type UpdatePatientSexBirthMutationOptions = Apollo.BaseMutationOptions<UpdatePatientSexBirthMutation, UpdatePatientSexBirthMutationVariables>;
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
      shortId
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
      races {
        raceCd
        recordStatusCd
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
export const FindContactsNamedByPatientDocument = gql`
    query findContactsNamedByPatient($patient: ID!, $page: Page) {
  findContactsNamedByPatient(patient: $patient, page: $page) {
    content {
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
      associatedWith {
        id
        local
        condition
      }
    }
    total
    number
  }
}
    `;

/**
 * __useFindContactsNamedByPatientQuery__
 *
 * To run a query within a React component, call `useFindContactsNamedByPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindContactsNamedByPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindContactsNamedByPatientQuery({
 *   variables: {
 *      patient: // value for 'patient'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindContactsNamedByPatientQuery(baseOptions: Apollo.QueryHookOptions<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>(FindContactsNamedByPatientDocument, options);
      }
export function useFindContactsNamedByPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>(FindContactsNamedByPatientDocument, options);
        }
export type FindContactsNamedByPatientQueryHookResult = ReturnType<typeof useFindContactsNamedByPatientQuery>;
export type FindContactsNamedByPatientLazyQueryHookResult = ReturnType<typeof useFindContactsNamedByPatientLazyQuery>;
export type FindContactsNamedByPatientQueryResult = Apollo.QueryResult<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>;
export const FindDocumentsForPatientDocument = gql`
    query findDocumentsForPatient($patient: ID!, $page: Page) {
  findDocumentsForPatient(patient: $patient, page: $page) {
    content {
      document
      receivedOn
      type
      sendingFacility
      reportedOn
      condition
      event
      associatedWith {
        id
        local
      }
    }
    total
    number
  }
}
    `;

/**
 * __useFindDocumentsForPatientQuery__
 *
 * To run a query within a React component, call `useFindDocumentsForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindDocumentsForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindDocumentsForPatientQuery({
 *   variables: {
 *      patient: // value for 'patient'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindDocumentsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>(FindDocumentsForPatientDocument, options);
      }
export function useFindDocumentsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>(FindDocumentsForPatientDocument, options);
        }
export type FindDocumentsForPatientQueryHookResult = ReturnType<typeof useFindDocumentsForPatientQuery>;
export type FindDocumentsForPatientLazyQueryHookResult = ReturnType<typeof useFindDocumentsForPatientLazyQuery>;
export type FindDocumentsForPatientQueryResult = Apollo.QueryResult<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>;
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
export const FindMorbidityReportsForPatientDocument = gql`
    query findMorbidityReportsForPatient($patient: ID!, $page: Page) {
  findMorbidityReportsForPatient(patient: $patient, page: $page) {
    content {
      morbidity
      receivedOn
      provider
      reportedOn
      condition
      jurisdiction
      event
      associatedWith {
        id
        local
        condition
      }
      treatments
      labResults {
        labTest
        status
        codedResult
        numericResult
        textResult
      }
    }
    total
    number
  }
}
    `;

/**
 * __useFindMorbidityReportsForPatientQuery__
 *
 * To run a query within a React component, call `useFindMorbidityReportsForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindMorbidityReportsForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindMorbidityReportsForPatientQuery({
 *   variables: {
 *      patient: // value for 'patient'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindMorbidityReportsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>(FindMorbidityReportsForPatientDocument, options);
      }
export function useFindMorbidityReportsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>(FindMorbidityReportsForPatientDocument, options);
        }
export type FindMorbidityReportsForPatientQueryHookResult = ReturnType<typeof useFindMorbidityReportsForPatientQuery>;
export type FindMorbidityReportsForPatientLazyQueryHookResult = ReturnType<typeof useFindMorbidityReportsForPatientLazyQuery>;
export type FindMorbidityReportsForPatientQueryResult = Apollo.QueryResult<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>;
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
    shortId
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
    races {
      raceCd
      recordStatusCd
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
export const FindPatientNamedByContactDocument = gql`
    query findPatientNamedByContact($patient: ID!, $page: Page) {
  findPatientNamedByContact(patient: $patient, page: $page) {
    content {
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
      associatedWith {
        id
        local
        condition
      }
    }
    total
    number
  }
}
    `;

/**
 * __useFindPatientNamedByContactQuery__
 *
 * To run a query within a React component, call `useFindPatientNamedByContactQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPatientNamedByContactQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPatientNamedByContactQuery({
 *   variables: {
 *      patient: // value for 'patient'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindPatientNamedByContactQuery(baseOptions: Apollo.QueryHookOptions<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>(FindPatientNamedByContactDocument, options);
      }
export function useFindPatientNamedByContactLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>(FindPatientNamedByContactDocument, options);
        }
export type FindPatientNamedByContactQueryHookResult = ReturnType<typeof useFindPatientNamedByContactQuery>;
export type FindPatientNamedByContactLazyQueryHookResult = ReturnType<typeof useFindPatientNamedByContactLazyQuery>;
export type FindPatientNamedByContactQueryResult = Apollo.QueryResult<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>;
export const FindPatientProfileDocument = gql`
    query findPatientProfile($asOf: DateTime, $page: Page, $page1: Page, $page2: Page, $page3: Page, $page4: Page, $page5: Page, $patient: ID, $shortId: Int) {
  findPatientProfile(patient: $patient, shortId: $shortId) {
    id
    local
    shortId
    version
    summary(asOf: $asOf) {
      legalName {
        prefix
        first
        middle
        last
        suffix
      }
      birthday
      age
      gender
      ethnicity
      race
      phone {
        use
        number
      }
      email {
        use
        address
      }
      address {
        street
        city
        state
        zipcode
        country
      }
    }
    names(page: $page) {
      content {
        patient
        version
        asOf
        sequence
        use {
          id
          description
        }
        prefix {
          id
          description
        }
        first
        middle
        secondMiddle
        last
        secondLast
        suffix {
          id
          description
        }
        degree {
          id
          description
        }
      }
      total
      number
      size
    }
    administrative(page: $page1) {
      content {
        patient
        id
        version
        asOf
        comment
      }
      total
      number
      size
    }
    addresses(page: $page2) {
      content {
        patient
        id
        version
        asOf
        type {
          id
          description
        }
        address1
        address2
        city
        county {
          id
          description
        }
        state {
          id
          description
        }
        zipcode
        country {
          id
          description
        }
        censusTract
        comment
      }
      total
      number
      size
    }
    phones(page: $page3) {
      content {
        patient
        id
        version
        asOf
        countryCode
        number
        extension
        email
        url
        comment
      }
      total
      number
      size
    }
    identification(page: $page4) {
      content {
        patient
        id
        sequence
        version
        asOf
        authority {
          id
          description
        }
        value
      }
      total
      number
      size
    }
    races(page: $page5) {
      content {
        patient
        id
        version
        asOf
        category {
          id
          description
        }
        detailed {
          id
          description
        }
      }
      total
      number
      size
    }
    birth {
      patient
      id
      version
      asOf
      bornOn
      age
      multipleBirth {
        id
        description
      }
      city
      state {
        id
        description
      }
      country {
        id
        description
      }
    }
    gender {
      patient
      id
      version
      asOf
      birth {
        id
        description
      }
      current {
        id
        description
      }
      unknownReason {
        id
        description
      }
      preferred {
        id
        description
      }
      additional
    }
    mortality {
      patient
      id
      version
      asOf
      deceased {
        id
        description
      }
      deceasedOn
      city
      state {
        id
        description
      }
      country {
        id
        description
      }
    }
    general {
      patient
      id
      version
      asOf
      maritalStatus {
        id
        description
      }
      maternalMaidenName
      adultsInHouse
      childrenInHouse
      occupation {
        id
        description
      }
      educationLevel {
        id
        description
      }
      primaryLanguage {
        id
        description
      }
      speaksEnglish {
        id
        description
      }
      stateHIVCase
    }
    ethnicity {
      patient
      id
      version
      asOf
      ethnicGroup {
        id
        description
      }
      unknownReason {
        id
        description
      }
      detailed {
        id
        description
      }
    }
  }
}
    `;

/**
 * __useFindPatientProfileQuery__
 *
 * To run a query within a React component, call `useFindPatientProfileQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPatientProfileQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPatientProfileQuery({
 *   variables: {
 *      asOf: // value for 'asOf'
 *      page: // value for 'page'
 *      page1: // value for 'page1'
 *      page2: // value for 'page2'
 *      page3: // value for 'page3'
 *      page4: // value for 'page4'
 *      page5: // value for 'page5'
 *      patient: // value for 'patient'
 *      shortId: // value for 'shortId'
 *   },
 * });
 */
export function useFindPatientProfileQuery(baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(FindPatientProfileDocument, options);
      }
export function useFindPatientProfileLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(FindPatientProfileDocument, options);
        }
export type FindPatientProfileQueryHookResult = ReturnType<typeof useFindPatientProfileQuery>;
export type FindPatientProfileLazyQueryHookResult = ReturnType<typeof useFindPatientProfileLazyQuery>;
export type FindPatientProfileQueryResult = Apollo.QueryResult<FindPatientProfileQuery, FindPatientProfileQueryVariables>;
export const FindPatientsByFilterDocument = gql`
    query findPatientsByFilter($filter: PersonFilter!, $page: SortablePage) {
  findPatientsByFilter(filter: $filter, page: $page) {
    content {
      shortId
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
      races {
        raceCd
        recordStatusCd
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
      shortId
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
      races {
        raceCd
        recordStatusCd
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
    query findTreatmentsForPatient($patient: ID!, $page: Page) {
  findTreatmentsForPatient(patient: $patient, page: $page) {
    content {
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
    total
    number
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
 *      page: // value for 'page'
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
export const FindVaccinationsForPatientDocument = gql`
    query findVaccinationsForPatient($patient: ID!, $page: Page) {
  findVaccinationsForPatient(patient: $patient, page: $page) {
    content {
      vaccination
      createdOn
      provider
      administeredOn
      administered
      event
      associatedWith {
        id
        local
        condition
      }
    }
    total
    number
  }
}
    `;

/**
 * __useFindVaccinationsForPatientQuery__
 *
 * To run a query within a React component, call `useFindVaccinationsForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindVaccinationsForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindVaccinationsForPatientQuery({
 *   variables: {
 *      patient: // value for 'patient'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindVaccinationsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>(FindVaccinationsForPatientDocument, options);
      }
export function useFindVaccinationsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>(FindVaccinationsForPatientDocument, options);
        }
export type FindVaccinationsForPatientQueryHookResult = ReturnType<typeof useFindVaccinationsForPatientQuery>;
export type FindVaccinationsForPatientLazyQueryHookResult = ReturnType<typeof useFindVaccinationsForPatientLazyQuery>;
export type FindVaccinationsForPatientQueryResult = Apollo.QueryResult<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>;