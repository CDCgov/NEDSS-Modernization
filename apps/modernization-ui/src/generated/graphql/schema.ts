import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type MakeEmpty<T extends { [key: string]: unknown }, K extends keyof T> = { [_ in K]?: never };
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
const defaultOptions = {} as const;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
  Date: { input: any; output: any; }
  DateTime: { input: any; output: any; }
  Direction: { input: any; output: any; }
};

export type AssociatedInvestigation = {
  __typename?: 'AssociatedInvestigation';
  cdDescTxt: Scalars['String']['output'];
  localId: Scalars['String']['output'];
};

export enum CaseStatus {
  Confirmed = 'CONFIRMED',
  NotACase = 'NOT_A_CASE',
  Probable = 'PROBABLE',
  Suspect = 'SUSPECT',
  Unassigned = 'UNASSIGNED',
  Unknown = 'UNKNOWN'
}

export type ContactsNamedByPatientResults = {
  __typename?: 'ContactsNamedByPatientResults';
  content: Array<Maybe<NamedByPatient>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type DateBetweenCriteria = {
  from?: InputMaybe<Scalars['Date']['input']>;
  to?: InputMaybe<Scalars['Date']['input']>;
};

export type DateCriteria = {
  between?: InputMaybe<DateBetweenCriteria>;
  equals?: InputMaybe<DateEqualsCriteria>;
};

export type DateEqualsCriteria = {
  day?: InputMaybe<Scalars['Int']['input']>;
  month?: InputMaybe<Scalars['Int']['input']>;
  year?: InputMaybe<Scalars['Int']['input']>;
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

export type EventId = {
  id: Scalars['String']['input'];
  investigationEventType: InvestigationEventIdType;
};

export enum EventStatus {
  New = 'NEW',
  Update = 'UPDATE'
}

export type Filter = {
  address?: InputMaybe<Scalars['String']['input']>;
  ageOrDateOfBirth?: InputMaybe<Scalars['String']['input']>;
  email?: InputMaybe<Scalars['String']['input']>;
  id?: InputMaybe<Scalars['String']['input']>;
  identification?: InputMaybe<Scalars['String']['input']>;
  name?: InputMaybe<Scalars['String']['input']>;
  phone?: InputMaybe<Scalars['String']['input']>;
  sex?: InputMaybe<Scalars['String']['input']>;
};

export enum Gender {
  F = 'F',
  M = 'M',
  U = 'U'
}

export type IdentificationCriteria = {
  assigningAuthority?: InputMaybe<Scalars['String']['input']>;
  identificationNumber?: InputMaybe<Scalars['String']['input']>;
  identificationType?: InputMaybe<Scalars['String']['input']>;
};

export type Investigation = {
  __typename?: 'Investigation';
  addTime?: Maybe<Scalars['Date']['output']>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  id?: Maybe<Scalars['ID']['output']>;
  investigationStatusCd?: Maybe<Scalars['String']['output']>;
  investigatorLastName?: Maybe<Scalars['String']['output']>;
  jurisdictionCodeDescTxt?: Maybe<Scalars['String']['output']>;
  localId?: Maybe<Scalars['String']['output']>;
  notificationRecordStatusCd?: Maybe<Scalars['String']['output']>;
  personParticipations: Array<InvestigationPersonParticipation>;
  relevance: Scalars['Float']['output'];
  startedOn?: Maybe<Scalars['Date']['output']>;
};

export type InvestigationEventDateSearch = {
  from: Scalars['Date']['input'];
  to: Scalars['Date']['input'];
  type: InvestigationEventDateType;
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
  caseStatuses?: InputMaybe<Array<CaseStatus>>;
  conditions?: InputMaybe<Array<Scalars['String']['input']>>;
  createdBy?: InputMaybe<Scalars['String']['input']>;
  eventDate?: InputMaybe<InvestigationEventDateSearch>;
  eventId?: InputMaybe<EventId>;
  investigationStatus?: InputMaybe<InvestigationStatus>;
  investigatorId?: InputMaybe<Scalars['Int']['input']>;
  jurisdictions?: InputMaybe<Array<Scalars['Int']['input']>>;
  lastUpdatedBy?: InputMaybe<Scalars['String']['input']>;
  notificationStatuses?: InputMaybe<Array<InputMaybe<NotificationStatus>>>;
  outbreakNames?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  patientId?: InputMaybe<Scalars['Int']['input']>;
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatuses?: InputMaybe<Array<InputMaybe<ProcessingStatus>>>;
  programAreas?: InputMaybe<Array<Scalars['String']['input']>>;
  providerFacilitySearch?: InputMaybe<ProviderFacilitySearch>;
  reportingFacilityId?: InputMaybe<Scalars['String']['input']>;
  reportingProviderId?: InputMaybe<Scalars['String']['input']>;
};

export type InvestigationPersonParticipation = {
  __typename?: 'InvestigationPersonParticipation';
  birthTime?: Maybe<Scalars['Date']['output']>;
  currSexCd?: Maybe<Scalars['String']['output']>;
  firstName?: Maybe<Scalars['String']['output']>;
  lastName?: Maybe<Scalars['String']['output']>;
  personCd: Scalars['String']['output'];
  personParentUid?: Maybe<Scalars['Int']['output']>;
  shortId?: Maybe<Scalars['Int']['output']>;
  typeCd: Scalars['String']['output'];
};

export type InvestigationResults = {
  __typename?: 'InvestigationResults';
  content: Array<Investigation>;
  page: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export enum InvestigationStatus {
  Closed = 'CLOSED',
  Open = 'OPEN'
}

export type Jurisdiction = {
  __typename?: 'Jurisdiction';
  assigningAuthorityCd?: Maybe<Scalars['String']['output']>;
  assigningAuthorityDescTxt?: Maybe<Scalars['String']['output']>;
  codeDescTxt?: Maybe<Scalars['String']['output']>;
  codeSeqNum?: Maybe<Scalars['Int']['output']>;
  codeSetNm?: Maybe<Scalars['String']['output']>;
  codeShortDescTxt?: Maybe<Scalars['String']['output']>;
  codeSystemCd?: Maybe<Scalars['String']['output']>;
  codeSystemDescTxt?: Maybe<Scalars['String']['output']>;
  effectiveFromTime?: Maybe<Scalars['DateTime']['output']>;
  effectiveToTime?: Maybe<Scalars['DateTime']['output']>;
  exportInd?: Maybe<Scalars['String']['output']>;
  id: Scalars['String']['output'];
  indentLevelNbr?: Maybe<Scalars['Int']['output']>;
  isModifiableInd?: Maybe<Scalars['String']['output']>;
  nbsUid?: Maybe<Scalars['ID']['output']>;
  parentIsCd?: Maybe<Scalars['String']['output']>;
  sourceConceptId?: Maybe<Scalars['String']['output']>;
  stateDomainCd?: Maybe<Scalars['String']['output']>;
  statusCd?: Maybe<Scalars['String']['output']>;
  statusTime?: Maybe<Scalars['DateTime']['output']>;
  typeCd: Scalars['String']['output'];
};

export type LabReport = {
  __typename?: 'LabReport';
  addTime: Scalars['Date']['output'];
  associatedInvestigations: Array<AssociatedInvestigation>;
  id: Scalars['String']['output'];
  jurisdictionCd: Scalars['Int']['output'];
  localId: Scalars['String']['output'];
  observations: Array<Observation>;
  organizationParticipations: Array<LabReportOrganizationParticipation>;
  personParticipations: Array<LabReportPersonParticipation>;
  relevance: Scalars['Float']['output'];
  tests: Array<LabTestSummary>;
};

export type LabReportEventId = {
  labEventId: Scalars['String']['input'];
  labEventType: LaboratoryEventIdType;
};

export type LabReportFilter = {
  codedResult?: InputMaybe<Scalars['String']['input']>;
  createdBy?: InputMaybe<Scalars['Int']['input']>;
  enteredBy?: InputMaybe<Array<UserType>>;
  entryMethods?: InputMaybe<Array<EntryMethod>>;
  eventDate?: InputMaybe<LaboratoryEventDateSearch>;
  eventId?: InputMaybe<LabReportEventId>;
  eventStatus?: InputMaybe<Array<EventStatus>>;
  jurisdictions?: InputMaybe<Array<Scalars['Int']['input']>>;
  lastUpdatedBy?: InputMaybe<Scalars['Int']['input']>;
  orderingLabId?: InputMaybe<Scalars['Int']['input']>;
  orderingProviderId?: InputMaybe<Scalars['Int']['input']>;
  patientId?: InputMaybe<Scalars['Int']['input']>;
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatus?: InputMaybe<Array<LaboratoryReportStatus>>;
  programAreas?: InputMaybe<Array<Scalars['String']['input']>>;
  providerSearch?: InputMaybe<LabReportProviderSearch>;
  reportingLabId?: InputMaybe<Scalars['Int']['input']>;
  resultedTest?: InputMaybe<Scalars['String']['input']>;
};

export type LabReportOrganizationParticipation = {
  __typename?: 'LabReportOrganizationParticipation';
  name: Scalars['String']['output'];
  typeCd: Scalars['String']['output'];
};

export type LabReportPersonParticipation = {
  __typename?: 'LabReportPersonParticipation';
  birthTime?: Maybe<Scalars['Date']['output']>;
  currSexCd?: Maybe<Scalars['String']['output']>;
  firstName?: Maybe<Scalars['String']['output']>;
  lastName?: Maybe<Scalars['String']['output']>;
  personCd: Scalars['String']['output'];
  personParentUid?: Maybe<Scalars['Int']['output']>;
  shortId?: Maybe<Scalars['Int']['output']>;
  typeCd?: Maybe<Scalars['String']['output']>;
};

export type LabReportProviderSearch = {
  providerId: Scalars['ID']['input'];
  providerType: ProviderType;
};

export type LabReportResults = {
  __typename?: 'LabReportResults';
  content: Array<LabReport>;
  page: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type LabTestSummary = {
  __typename?: 'LabTestSummary';
  coded?: Maybe<Scalars['String']['output']>;
  high?: Maybe<Scalars['String']['output']>;
  low?: Maybe<Scalars['String']['output']>;
  name?: Maybe<Scalars['String']['output']>;
  numeric?: Maybe<Scalars['Float']['output']>;
  status?: Maybe<Scalars['String']['output']>;
  unit?: Maybe<Scalars['String']['output']>;
};

export type LaboratoryEventDateSearch = {
  from: Scalars['Date']['input'];
  to: Scalars['Date']['input'];
  type: LaboratoryReportEventDateType;
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

export type LocationCriteria = {
  city?: InputMaybe<TextCriteria>;
  street?: InputMaybe<TextCriteria>;
};

export type NamedByContact = {
  __typename?: 'NamedByContact';
  associatedWith?: Maybe<PatientContactInvestigation>;
  condition: TracedCondition;
  contact: NamedContact;
  contactRecord: Scalars['ID']['output'];
  createdOn: Scalars['DateTime']['output'];
  event: Scalars['String']['output'];
  namedOn: Scalars['DateTime']['output'];
};

export type NamedByPatient = {
  __typename?: 'NamedByPatient';
  associatedWith?: Maybe<PatientContactInvestigation>;
  condition: TracedCondition;
  contact: NamedContact;
  contactRecord: Scalars['ID']['output'];
  createdOn: Scalars['DateTime']['output'];
  disposition?: Maybe<Scalars['String']['output']>;
  event: Scalars['String']['output'];
  namedOn: Scalars['DateTime']['output'];
  priority?: Maybe<Scalars['String']['output']>;
};

export type NamedContact = {
  __typename?: 'NamedContact';
  id: Scalars['ID']['output'];
  name: Scalars['String']['output'];
};

export enum NotificationStatus {
  Approved = 'APPROVED',
  Completed = 'COMPLETED',
  MessageFailed = 'MESSAGE_FAILED',
  PendingApproval = 'PENDING_APPROVAL',
  Rejected = 'REJECTED',
  Unassigned = 'UNASSIGNED'
}

export type Observation = {
  __typename?: 'Observation';
  altCd?: Maybe<Scalars['String']['output']>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  displayName?: Maybe<Scalars['String']['output']>;
  statusCd?: Maybe<Scalars['String']['output']>;
};

export enum Operator {
  After = 'AFTER',
  Before = 'BEFORE',
  Equal = 'EQUAL'
}

export type Page = {
  pageNumber: Scalars['Int']['input'];
  pageSize: Scalars['Int']['input'];
};

export type PatientContactInvestigation = {
  __typename?: 'PatientContactInvestigation';
  condition: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  local: Scalars['String']['output'];
};

export type PatientDocument = {
  __typename?: 'PatientDocument';
  associatedWith?: Maybe<PatientDocumentInvestigation>;
  condition?: Maybe<Scalars['String']['output']>;
  document: Scalars['ID']['output'];
  event: Scalars['String']['output'];
  receivedOn: Scalars['DateTime']['output'];
  reportedOn: Scalars['DateTime']['output'];
  sendingFacility: Scalars['String']['output'];
  type: Scalars['String']['output'];
};

export type PatientDocumentInvestigation = {
  __typename?: 'PatientDocumentInvestigation';
  id: Scalars['ID']['output'];
  local: Scalars['String']['output'];
};

export type PatientDocumentResults = {
  __typename?: 'PatientDocumentResults';
  content: Array<Maybe<PatientDocument>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientNameCriteria = {
  first?: InputMaybe<TextCriteria>;
  last?: InputMaybe<TextCriteria>;
};

export type PatientNamedByContactResults = {
  __typename?: 'PatientNamedByContactResults';
  content: Array<Maybe<NamedByPatient>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientSearchResult = {
  __typename?: 'PatientSearchResult';
  addresses: Array<PatientSearchResultAddress>;
  age?: Maybe<Scalars['Int']['output']>;
  birthday?: Maybe<Scalars['Date']['output']>;
  detailedPhones: Array<PatientSearchResultPhone>;
  emails: Array<Scalars['String']['output']>;
  gender?: Maybe<Scalars['String']['output']>;
  identification: Array<PatientSearchResultIdentification>;
  legalName?: Maybe<PatientSearchResultName>;
  names: Array<PatientSearchResultName>;
  patient: Scalars['Int']['output'];
  phones: Array<Scalars['String']['output']>;
  shortId: Scalars['Int']['output'];
  status: Scalars['String']['output'];
};

export type PatientSearchResultAddress = {
  __typename?: 'PatientSearchResultAddress';
  address?: Maybe<Scalars['String']['output']>;
  address2?: Maybe<Scalars['String']['output']>;
  city?: Maybe<Scalars['String']['output']>;
  county?: Maybe<Scalars['String']['output']>;
  state?: Maybe<Scalars['String']['output']>;
  type?: Maybe<Scalars['String']['output']>;
  use?: Maybe<Scalars['String']['output']>;
  zipcode?: Maybe<Scalars['String']['output']>;
};

export type PatientSearchResultIdentification = {
  __typename?: 'PatientSearchResultIdentification';
  type: Scalars['String']['output'];
  value: Scalars['String']['output'];
};

export type PatientSearchResultName = {
  __typename?: 'PatientSearchResultName';
  first?: Maybe<Scalars['String']['output']>;
  last?: Maybe<Scalars['String']['output']>;
  middle?: Maybe<Scalars['String']['output']>;
  suffix?: Maybe<Scalars['String']['output']>;
  type?: Maybe<Scalars['String']['output']>;
};

export type PatientSearchResultPhone = {
  __typename?: 'PatientSearchResultPhone';
  number?: Maybe<Scalars['String']['output']>;
  type: Scalars['String']['output'];
  use: Scalars['String']['output'];
};

export type PatientSearchResults = {
  __typename?: 'PatientSearchResults';
  content: Array<PatientSearchResult>;
  page: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientTreatment = {
  __typename?: 'PatientTreatment';
  associatedWith: PatientTreatmentInvestigation;
  createdOn: Scalars['DateTime']['output'];
  description: Scalars['String']['output'];
  event: Scalars['String']['output'];
  provider?: Maybe<Scalars['String']['output']>;
  treatedOn: Scalars['DateTime']['output'];
  treatment: Scalars['ID']['output'];
};

export type PatientTreatmentInvestigation = {
  __typename?: 'PatientTreatmentInvestigation';
  condition: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  local: Scalars['String']['output'];
};

export type PatientTreatmentResults = {
  __typename?: 'PatientTreatmentResults';
  content: Array<Maybe<PatientTreatment>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PersonFilter = {
  abcCase?: InputMaybe<Scalars['String']['input']>;
  accessionNumber?: InputMaybe<Scalars['String']['input']>;
  address?: InputMaybe<Scalars['String']['input']>;
  bornOn?: InputMaybe<DateCriteria>;
  city?: InputMaybe<Scalars['String']['input']>;
  cityCountyCase?: InputMaybe<Scalars['String']['input']>;
  country?: InputMaybe<Scalars['String']['input']>;
  dateOfBirth?: InputMaybe<Scalars['Date']['input']>;
  dateOfBirthOperator?: InputMaybe<Operator>;
  deceased?: InputMaybe<Deceased>;
  disableSoundex?: InputMaybe<Scalars['Boolean']['input']>;
  document?: InputMaybe<Scalars['String']['input']>;
  email?: InputMaybe<Scalars['String']['input']>;
  ethnicity?: InputMaybe<Scalars['String']['input']>;
  filter?: InputMaybe<Filter>;
  firstName?: InputMaybe<Scalars['String']['input']>;
  gender?: InputMaybe<Scalars['String']['input']>;
  id?: InputMaybe<Scalars['String']['input']>;
  identification?: InputMaybe<IdentificationCriteria>;
  investigation?: InputMaybe<Scalars['String']['input']>;
  labReport?: InputMaybe<Scalars['String']['input']>;
  lastName?: InputMaybe<Scalars['String']['input']>;
  location?: InputMaybe<LocationCriteria>;
  morbidity?: InputMaybe<Scalars['String']['input']>;
  mortalityStatus?: InputMaybe<Scalars['String']['input']>;
  name?: InputMaybe<PatientNameCriteria>;
  notification?: InputMaybe<Scalars['String']['input']>;
  phoneNumber?: InputMaybe<Scalars['String']['input']>;
  race?: InputMaybe<Scalars['String']['input']>;
  recordStatus: Array<RecordStatus>;
  state?: InputMaybe<Scalars['String']['input']>;
  stateCase?: InputMaybe<Scalars['String']['input']>;
  treatment?: InputMaybe<Scalars['String']['input']>;
  vaccination?: InputMaybe<Scalars['String']['input']>;
  zip?: InputMaybe<Scalars['String']['input']>;
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

export type ProgramAreaCode = {
  __typename?: 'ProgramAreaCode';
  codeSeq?: Maybe<Scalars['Int']['output']>;
  codeSetNm?: Maybe<Scalars['String']['output']>;
  id: Scalars['String']['output'];
  nbsUid?: Maybe<Scalars['ID']['output']>;
  progAreaDescTxt?: Maybe<Scalars['String']['output']>;
  statusCd?: Maybe<Scalars['String']['output']>;
  statusTime?: Maybe<Scalars['DateTime']['output']>;
};

export type ProviderFacilitySearch = {
  entityType: ReportingEntityType;
  id: Scalars['ID']['input'];
};

export enum ProviderType {
  OrderingFacility = 'ORDERING_FACILITY',
  OrderingProvider = 'ORDERING_PROVIDER',
  ReportingFacility = 'REPORTING_FACILITY'
}

export type Query = {
  __typename?: 'Query';
  findAllJurisdictions: Array<Maybe<Jurisdiction>>;
  findAllProgramAreas: Array<Maybe<ProgramAreaCode>>;
  findContactsNamedByPatient?: Maybe<ContactsNamedByPatientResults>;
  findDocumentsForPatient?: Maybe<PatientDocumentResults>;
  findInvestigationsByFilter: InvestigationResults;
  findLabReportsByFilter: LabReportResults;
  findPatientNamedByContact?: Maybe<PatientNamedByContactResults>;
  findPatientsByFilter: PatientSearchResults;
  findTreatmentsForPatient?: Maybe<PatientTreatmentResults>;
};


export type QueryFindAllJurisdictionsArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllProgramAreasArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindContactsNamedByPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindDocumentsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindInvestigationsByFilterArgs = {
  filter: InvestigationFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindLabReportsByFilterArgs = {
  filter: LabReportFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindPatientNamedByContactArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindPatientsByFilterArgs = {
  filter: PersonFilter;
  page?: InputMaybe<SortablePage>;
  share?: InputMaybe<Scalars['String']['input']>;
};


export type QueryFindTreatmentsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
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

export type Sort = {
  direction: Scalars['Direction']['input'];
  property: Scalars['String']['input'];
};

export enum SortDirection {
  Asc = 'ASC',
  Desc = 'DESC'
}

export enum SortField {
  Address = 'address',
  BirthTime = 'birthTime',
  City = 'city',
  Condition = 'condition',
  Country = 'country',
  County = 'county',
  Email = 'email',
  FirstNm = 'firstNm',
  Id = 'id',
  Identification = 'identification',
  InvestigationId = 'investigationId',
  Investigator = 'investigator',
  Jurisdiction = 'jurisdiction',
  LastNm = 'lastNm',
  LocalId = 'local_id',
  Notification = 'notification',
  PhoneNumber = 'phoneNumber',
  Relevance = 'relevance',
  Sex = 'sex',
  StartDate = 'startDate',
  State = 'state',
  Status = 'status',
  Zip = 'zip'
}

export type SortablePage = {
  pageNumber?: InputMaybe<Scalars['Int']['input']>;
  pageSize?: InputMaybe<Scalars['Int']['input']>;
  sort?: InputMaybe<Sort>;
  sortDirection?: InputMaybe<SortDirection>;
  sortField?: InputMaybe<SortField>;
};

export type TextCriteria = {
  contains?: InputMaybe<Scalars['String']['input']>;
  equals?: InputMaybe<Scalars['String']['input']>;
  not?: InputMaybe<Scalars['String']['input']>;
  soundsLike?: InputMaybe<Scalars['String']['input']>;
  startsWith?: InputMaybe<Scalars['String']['input']>;
};

export type TracedCondition = {
  __typename?: 'TracedCondition';
  description?: Maybe<Scalars['String']['output']>;
  id?: Maybe<Scalars['String']['output']>;
};

export enum UserType {
  External = 'EXTERNAL',
  Internal = 'INTERNAL'
}

export type FindAllJurisdictionsQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllJurisdictionsQuery = { __typename?: 'Query', findAllJurisdictions: Array<{ __typename?: 'Jurisdiction', id: string, typeCd: string, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, codeDescTxt?: string | null, codeShortDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, indentLevelNbr?: number | null, isModifiableInd?: string | null, parentIsCd?: string | null, stateDomainCd?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, codeSeqNum?: number | null, nbsUid?: string | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null, exportInd?: string | null } | null> };

export type FindAllProgramAreasQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllProgramAreasQuery = { __typename?: 'Query', findAllProgramAreas: Array<{ __typename?: 'ProgramAreaCode', id: string, progAreaDescTxt?: string | null, nbsUid?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, codeSeq?: number | null } | null> };

export type FindContactsNamedByPatientQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindContactsNamedByPatientQuery = { __typename?: 'Query', findContactsNamedByPatient?: { __typename?: 'ContactsNamedByPatientResults', total: number, number: number, content: Array<{ __typename?: 'NamedByPatient', contactRecord: string, createdOn: any, namedOn: any, priority?: string | null, disposition?: string | null, event: string, condition: { __typename?: 'TracedCondition', id?: string | null, description?: string | null }, contact: { __typename?: 'NamedContact', id: string, name: string }, associatedWith?: { __typename?: 'PatientContactInvestigation', id: string, local: string, condition: string } | null } | null> } | null };

export type FindDocumentsForPatientQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindDocumentsForPatientQuery = { __typename?: 'Query', findDocumentsForPatient?: { __typename?: 'PatientDocumentResults', total: number, number: number, content: Array<{ __typename?: 'PatientDocument', document: string, receivedOn: any, type: string, sendingFacility: string, reportedOn: any, condition?: string | null, event: string, associatedWith?: { __typename?: 'PatientDocumentInvestigation', id: string, local: string } | null } | null> } | null };

export type FindInvestigationsByFilterQueryVariables = Exact<{
  filter: InvestigationFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindInvestigationsByFilterQuery = { __typename?: 'Query', findInvestigationsByFilter: { __typename?: 'InvestigationResults', total: number, page: number, size: number, content: Array<{ __typename?: 'Investigation', relevance: number, id?: string | null, cdDescTxt?: string | null, jurisdictionCodeDescTxt?: string | null, localId?: string | null, addTime?: any | null, startedOn?: any | null, investigationStatusCd?: string | null, notificationRecordStatusCd?: string | null, investigatorLastName?: string | null, personParticipations: Array<{ __typename?: 'InvestigationPersonParticipation', birthTime?: any | null, currSexCd?: string | null, typeCd: string, firstName?: string | null, lastName?: string | null, personCd: string, personParentUid?: number | null, shortId?: number | null }> }> } };

export type FindLabReportsByFilterQueryVariables = Exact<{
  filter: LabReportFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindLabReportsByFilterQuery = { __typename?: 'Query', findLabReportsByFilter: { __typename?: 'LabReportResults', total: number, page: number, size: number, content: Array<{ __typename?: 'LabReport', relevance: number, id: string, jurisdictionCd: number, localId: string, addTime: any, personParticipations: Array<{ __typename?: 'LabReportPersonParticipation', birthTime?: any | null, currSexCd?: string | null, typeCd?: string | null, firstName?: string | null, lastName?: string | null, personCd: string, personParentUid?: number | null, shortId?: number | null }>, organizationParticipations: Array<{ __typename?: 'LabReportOrganizationParticipation', typeCd: string, name: string }>, observations: Array<{ __typename?: 'Observation', cdDescTxt?: string | null, statusCd?: string | null, altCd?: string | null, displayName?: string | null }>, associatedInvestigations: Array<{ __typename?: 'AssociatedInvestigation', cdDescTxt: string, localId: string }>, tests: Array<{ __typename?: 'LabTestSummary', name?: string | null, status?: string | null, coded?: string | null, numeric?: number | null, high?: string | null, low?: string | null, unit?: string | null }> }> } };

export type FindPatientNamedByContactQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindPatientNamedByContactQuery = { __typename?: 'Query', findPatientNamedByContact?: { __typename?: 'PatientNamedByContactResults', total: number, number: number, content: Array<{ __typename?: 'NamedByPatient', contactRecord: string, createdOn: any, namedOn: any, priority?: string | null, disposition?: string | null, event: string, condition: { __typename?: 'TracedCondition', id?: string | null, description?: string | null }, contact: { __typename?: 'NamedContact', id: string, name: string }, associatedWith?: { __typename?: 'PatientContactInvestigation', id: string, local: string, condition: string } | null } | null> } | null };

export type FindPatientsByFilterQueryVariables = Exact<{
  filter: PersonFilter;
  page?: InputMaybe<SortablePage>;
  share?: InputMaybe<Scalars['String']['input']>;
}>;


export type FindPatientsByFilterQuery = { __typename?: 'Query', findPatientsByFilter: { __typename?: 'PatientSearchResults', total: number, page: number, size: number, content: Array<{ __typename?: 'PatientSearchResult', patient: number, birthday?: any | null, age?: number | null, gender?: string | null, status: string, shortId: number, phones: Array<string>, emails: Array<string>, legalName?: { __typename?: 'PatientSearchResultName', type?: string | null, first?: string | null, middle?: string | null, last?: string | null, suffix?: string | null } | null, names: Array<{ __typename?: 'PatientSearchResultName', type?: string | null, first?: string | null, middle?: string | null, last?: string | null, suffix?: string | null }>, identification: Array<{ __typename?: 'PatientSearchResultIdentification', type: string, value: string }>, addresses: Array<{ __typename?: 'PatientSearchResultAddress', type?: string | null, use?: string | null, address?: string | null, address2?: string | null, city?: string | null, county?: string | null, state?: string | null, zipcode?: string | null }>, detailedPhones: Array<{ __typename?: 'PatientSearchResultPhone', type: string, use: string, number?: string | null }> }> } };

export type FindTreatmentsForPatientQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindTreatmentsForPatientQuery = { __typename?: 'Query', findTreatmentsForPatient?: { __typename?: 'PatientTreatmentResults', total: number, number: number, content: Array<{ __typename?: 'PatientTreatment', treatment: string, createdOn: any, provider?: string | null, treatedOn: any, description: string, event: string, associatedWith: { __typename?: 'PatientTreatmentInvestigation', id: string, local: string, condition: string } } | null> } | null };


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
export function useFindAllJurisdictionsSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>(FindAllJurisdictionsDocument, options);
        }
export type FindAllJurisdictionsQueryHookResult = ReturnType<typeof useFindAllJurisdictionsQuery>;
export type FindAllJurisdictionsLazyQueryHookResult = ReturnType<typeof useFindAllJurisdictionsLazyQuery>;
export type FindAllJurisdictionsSuspenseQueryHookResult = ReturnType<typeof useFindAllJurisdictionsSuspenseQuery>;
export type FindAllJurisdictionsQueryResult = Apollo.QueryResult<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>;
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
export function useFindAllProgramAreasSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>(FindAllProgramAreasDocument, options);
        }
export type FindAllProgramAreasQueryHookResult = ReturnType<typeof useFindAllProgramAreasQuery>;
export type FindAllProgramAreasLazyQueryHookResult = ReturnType<typeof useFindAllProgramAreasLazyQuery>;
export type FindAllProgramAreasSuspenseQueryHookResult = ReturnType<typeof useFindAllProgramAreasSuspenseQuery>;
export type FindAllProgramAreasQueryResult = Apollo.QueryResult<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>;
export const FindContactsNamedByPatientDocument = gql`
    query findContactsNamedByPatient($patient: ID!, $page: Page) {
  findContactsNamedByPatient(patient: $patient, page: $page) {
    content {
      contactRecord
      createdOn
      condition {
        id
        description
      }
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
export function useFindContactsNamedByPatientQuery(baseOptions: Apollo.QueryHookOptions<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables> & ({ variables: FindContactsNamedByPatientQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>(FindContactsNamedByPatientDocument, options);
      }
export function useFindContactsNamedByPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>(FindContactsNamedByPatientDocument, options);
        }
export function useFindContactsNamedByPatientSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>(FindContactsNamedByPatientDocument, options);
        }
export type FindContactsNamedByPatientQueryHookResult = ReturnType<typeof useFindContactsNamedByPatientQuery>;
export type FindContactsNamedByPatientLazyQueryHookResult = ReturnType<typeof useFindContactsNamedByPatientLazyQuery>;
export type FindContactsNamedByPatientSuspenseQueryHookResult = ReturnType<typeof useFindContactsNamedByPatientSuspenseQuery>;
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
export function useFindDocumentsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables> & ({ variables: FindDocumentsForPatientQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>(FindDocumentsForPatientDocument, options);
      }
export function useFindDocumentsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>(FindDocumentsForPatientDocument, options);
        }
export function useFindDocumentsForPatientSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>(FindDocumentsForPatientDocument, options);
        }
export type FindDocumentsForPatientQueryHookResult = ReturnType<typeof useFindDocumentsForPatientQuery>;
export type FindDocumentsForPatientLazyQueryHookResult = ReturnType<typeof useFindDocumentsForPatientLazyQuery>;
export type FindDocumentsForPatientSuspenseQueryHookResult = ReturnType<typeof useFindDocumentsForPatientSuspenseQuery>;
export type FindDocumentsForPatientQueryResult = Apollo.QueryResult<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>;
export const FindInvestigationsByFilterDocument = gql`
    query findInvestigationsByFilter($filter: InvestigationFilter!, $page: SortablePage) {
  findInvestigationsByFilter(filter: $filter, page: $page) {
    content {
      relevance
      id
      cdDescTxt
      jurisdictionCodeDescTxt
      localId
      addTime
      startedOn
      investigationStatusCd
      notificationRecordStatusCd
      investigatorLastName
      personParticipations {
        birthTime
        currSexCd
        typeCd
        firstName
        lastName
        personCd
        personParentUid
        shortId
      }
    }
    total
    page
    size
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
export function useFindInvestigationsByFilterQuery(baseOptions: Apollo.QueryHookOptions<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables> & ({ variables: FindInvestigationsByFilterQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>(FindInvestigationsByFilterDocument, options);
      }
export function useFindInvestigationsByFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>(FindInvestigationsByFilterDocument, options);
        }
export function useFindInvestigationsByFilterSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>(FindInvestigationsByFilterDocument, options);
        }
export type FindInvestigationsByFilterQueryHookResult = ReturnType<typeof useFindInvestigationsByFilterQuery>;
export type FindInvestigationsByFilterLazyQueryHookResult = ReturnType<typeof useFindInvestigationsByFilterLazyQuery>;
export type FindInvestigationsByFilterSuspenseQueryHookResult = ReturnType<typeof useFindInvestigationsByFilterSuspenseQuery>;
export type FindInvestigationsByFilterQueryResult = Apollo.QueryResult<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>;
export const FindLabReportsByFilterDocument = gql`
    query findLabReportsByFilter($filter: LabReportFilter!, $page: SortablePage) {
  findLabReportsByFilter(filter: $filter, page: $page) {
    content {
      relevance
      id
      jurisdictionCd
      localId
      addTime
      personParticipations {
        birthTime
        currSexCd
        typeCd
        firstName
        lastName
        personCd
        personParentUid
        shortId
      }
      organizationParticipations {
        typeCd
        name
      }
      observations {
        cdDescTxt
        statusCd
        altCd
        displayName
      }
      associatedInvestigations {
        cdDescTxt
        localId
      }
      tests {
        name
        status
        coded
        numeric
        high
        low
        unit
      }
    }
    total
    page
    size
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
export function useFindLabReportsByFilterQuery(baseOptions: Apollo.QueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables> & ({ variables: FindLabReportsByFilterQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>(FindLabReportsByFilterDocument, options);
      }
export function useFindLabReportsByFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>(FindLabReportsByFilterDocument, options);
        }
export function useFindLabReportsByFilterSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>(FindLabReportsByFilterDocument, options);
        }
export type FindLabReportsByFilterQueryHookResult = ReturnType<typeof useFindLabReportsByFilterQuery>;
export type FindLabReportsByFilterLazyQueryHookResult = ReturnType<typeof useFindLabReportsByFilterLazyQuery>;
export type FindLabReportsByFilterSuspenseQueryHookResult = ReturnType<typeof useFindLabReportsByFilterSuspenseQuery>;
export type FindLabReportsByFilterQueryResult = Apollo.QueryResult<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>;
export const FindPatientNamedByContactDocument = gql`
    query findPatientNamedByContact($patient: ID!, $page: Page) {
  findPatientNamedByContact(patient: $patient, page: $page) {
    content {
      contactRecord
      createdOn
      condition {
        id
        description
      }
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
export function useFindPatientNamedByContactQuery(baseOptions: Apollo.QueryHookOptions<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables> & ({ variables: FindPatientNamedByContactQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>(FindPatientNamedByContactDocument, options);
      }
export function useFindPatientNamedByContactLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>(FindPatientNamedByContactDocument, options);
        }
export function useFindPatientNamedByContactSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>(FindPatientNamedByContactDocument, options);
        }
export type FindPatientNamedByContactQueryHookResult = ReturnType<typeof useFindPatientNamedByContactQuery>;
export type FindPatientNamedByContactLazyQueryHookResult = ReturnType<typeof useFindPatientNamedByContactLazyQuery>;
export type FindPatientNamedByContactSuspenseQueryHookResult = ReturnType<typeof useFindPatientNamedByContactSuspenseQuery>;
export type FindPatientNamedByContactQueryResult = Apollo.QueryResult<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>;
export const FindPatientsByFilterDocument = gql`
    query findPatientsByFilter($filter: PersonFilter!, $page: SortablePage, $share: String) {
  findPatientsByFilter(filter: $filter, page: $page, share: $share) {
    content {
      patient
      birthday
      age
      gender
      status
      shortId
      legalName {
        type
        first
        middle
        last
        suffix
      }
      names {
        type
        first
        middle
        last
        suffix
      }
      identification {
        type
        value
      }
      addresses {
        type
        use
        address
        address2
        city
        county
        state
        zipcode
      }
      phones
      emails
      detailedPhones {
        type
        use
        number
      }
    }
    total
    page
    size
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
 *      share: // value for 'share'
 *   },
 * });
 */
export function useFindPatientsByFilterQuery(baseOptions: Apollo.QueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables> & ({ variables: FindPatientsByFilterQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(FindPatientsByFilterDocument, options);
      }
export function useFindPatientsByFilterLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(FindPatientsByFilterDocument, options);
        }
export function useFindPatientsByFilterSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(FindPatientsByFilterDocument, options);
        }
export type FindPatientsByFilterQueryHookResult = ReturnType<typeof useFindPatientsByFilterQuery>;
export type FindPatientsByFilterLazyQueryHookResult = ReturnType<typeof useFindPatientsByFilterLazyQuery>;
export type FindPatientsByFilterSuspenseQueryHookResult = ReturnType<typeof useFindPatientsByFilterSuspenseQuery>;
export type FindPatientsByFilterQueryResult = Apollo.QueryResult<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>;
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
export function useFindTreatmentsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables> & ({ variables: FindTreatmentsForPatientQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>(FindTreatmentsForPatientDocument, options);
      }
export function useFindTreatmentsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>(FindTreatmentsForPatientDocument, options);
        }
export function useFindTreatmentsForPatientSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>(FindTreatmentsForPatientDocument, options);
        }
export type FindTreatmentsForPatientQueryHookResult = ReturnType<typeof useFindTreatmentsForPatientQuery>;
export type FindTreatmentsForPatientLazyQueryHookResult = ReturnType<typeof useFindTreatmentsForPatientLazyQuery>;
export type FindTreatmentsForPatientSuspenseQueryHookResult = ReturnType<typeof useFindTreatmentsForPatientSuspenseQuery>;
export type FindTreatmentsForPatientQueryResult = Apollo.QueryResult<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>;