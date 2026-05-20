/** Internal type. DO NOT USE DIRECTLY. */
type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
/** Internal type. DO NOT USE DIRECTLY. */
export type Incremental<T> = T | { [P in keyof T]?: P extends ' $fragmentName' | '__typename' ? T[P] : never };
import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
const defaultOptions = {} as const;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: { input: string; output: string; }
  String: { input: string; output: string; }
  Boolean: { input: boolean; output: boolean; }
  Int: { input: number; output: number; }
  Float: { input: number; output: number; }
  Date: { input: unknown; output: unknown; }
  DateTime: { input: unknown; output: unknown; }
  Direction: { input: unknown; output: unknown; }
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

export type PatientNameCriteria = {
  first?: InputMaybe<TextCriteria>;
  last?: InputMaybe<TextCriteria>;
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
  findInvestigationsByFilter: InvestigationResults;
  findLabReportsByFilter: LabReportResults;
  findPatientsByFilter: PatientSearchResults;
};


export type QueryFindAllJurisdictionsArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllProgramAreasArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindInvestigationsByFilterArgs = {
  filter: InvestigationFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindLabReportsByFilterArgs = {
  filter: LabReportFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindPatientsByFilterArgs = {
  filter: PersonFilter;
  page?: InputMaybe<SortablePage>;
  share?: InputMaybe<Scalars['String']['input']>;
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

export enum UserType {
  External = 'EXTERNAL',
  Internal = 'INTERNAL'
}

export type CaseStatus =
  | 'CONFIRMED'
  | 'NOT_A_CASE'
  | 'PROBABLE'
  | 'SUSPECT'
  | 'UNASSIGNED'
  | 'UNKNOWN';

export type DateBetweenCriteria = {
  from?: unknown;
  to?: unknown;
};

export type DateCriteria = {
  between?: DateBetweenCriteria | null | undefined;
  equals?: DateEqualsCriteria | null | undefined;
};

export type DateEqualsCriteria = {
  day?: number | null | undefined;
  month?: number | null | undefined;
  year?: number | null | undefined;
};

export type Deceased =
  | 'N'
  | 'UNK'
  | 'Y';

export type EntryMethod =
  | 'ELECTRONIC'
  | 'MANUAL';

export type EventId = {
  id: string;
  investigationEventType: InvestigationEventIdType;
};

export type EventStatus =
  | 'NEW'
  | 'UPDATE';

export type Filter = {
  address?: string | null | undefined;
  ageOrDateOfBirth?: string | null | undefined;
  email?: string | null | undefined;
  id?: string | null | undefined;
  identification?: string | null | undefined;
  name?: string | null | undefined;
  phone?: string | null | undefined;
  sex?: string | null | undefined;
};

export type IdentificationCriteria = {
  assigningAuthority?: string | null | undefined;
  identificationNumber?: string | null | undefined;
  identificationType?: string | null | undefined;
};

export type InvestigationEventDateSearch = {
  from: unknown;
  to: unknown;
  type: InvestigationEventDateType;
};

export type InvestigationEventDateType =
  | 'DATE_OF_REPORT'
  | 'INVESTIGATION_CLOSED_DATE'
  | 'INVESTIGATION_CREATE_DATE'
  | 'INVESTIGATION_START_DATE'
  | 'LAST_UPDATE_DATE'
  | 'NOTIFICATION_CREATE_DATE';

export type InvestigationEventIdType =
  | 'ABCS_CASE_ID'
  | 'CITY_COUNTY_CASE_ID'
  | 'INVESTIGATION_ID'
  | 'NOTIFICATION_ID'
  | 'STATE_CASE_ID';

export type InvestigationFilter = {
  caseStatuses?: Array<CaseStatus> | null | undefined;
  conditions?: Array<string> | null | undefined;
  createdBy?: string | null | undefined;
  eventDate?: InvestigationEventDateSearch | null | undefined;
  eventId?: EventId | null | undefined;
  investigationStatus?: InvestigationStatus | null | undefined;
  investigatorId?: number | null | undefined;
  jurisdictions?: Array<number> | null | undefined;
  lastUpdatedBy?: string | null | undefined;
  notificationStatuses?: Array<NotificationStatus | null | undefined> | null | undefined;
  outbreakNames?: Array<string | null | undefined> | null | undefined;
  patientId?: number | null | undefined;
  pregnancyStatus?: PregnancyStatus | null | undefined;
  processingStatuses?: Array<ProcessingStatus | null | undefined> | null | undefined;
  programAreas?: Array<string> | null | undefined;
  providerFacilitySearch?: ProviderFacilitySearch | null | undefined;
  reportingFacilityId?: string | null | undefined;
  reportingProviderId?: string | null | undefined;
};

export type InvestigationStatus =
  | 'CLOSED'
  | 'OPEN';

export type LabReportEventId = {
  labEventId: string;
  labEventType: LaboratoryEventIdType;
};

export type LabReportFilter = {
  codedResult?: string | null | undefined;
  createdBy?: number | null | undefined;
  enteredBy?: Array<UserType> | null | undefined;
  entryMethods?: Array<EntryMethod> | null | undefined;
  eventDate?: LaboratoryEventDateSearch | null | undefined;
  eventId?: LabReportEventId | null | undefined;
  eventStatus?: Array<EventStatus> | null | undefined;
  jurisdictions?: Array<number> | null | undefined;
  lastUpdatedBy?: number | null | undefined;
  orderingLabId?: number | null | undefined;
  orderingProviderId?: number | null | undefined;
  patientId?: number | null | undefined;
  pregnancyStatus?: PregnancyStatus | null | undefined;
  processingStatus?: Array<LaboratoryReportStatus> | null | undefined;
  programAreas?: Array<string> | null | undefined;
  providerSearch?: LabReportProviderSearch | null | undefined;
  reportingLabId?: number | null | undefined;
  resultedTest?: string | null | undefined;
};

export type LabReportProviderSearch = {
  providerId: string | number;
  providerType: ProviderType;
};

export type LaboratoryEventDateSearch = {
  from: unknown;
  to: unknown;
  type: LaboratoryReportEventDateType;
};

export type LaboratoryEventIdType =
  | 'ACCESSION_NUMBER'
  | 'LAB_ID';

export type LaboratoryReportEventDateType =
  | 'DATE_OF_REPORT'
  | 'DATE_OF_SPECIMEN_COLLECTION'
  | 'DATE_RECEIVED_BY_PUBLIC_HEALTH'
  | 'LAB_REPORT_CREATE_DATE'
  | 'LAST_UPDATE_DATE';

export type LaboratoryReportStatus =
  | 'PROCESSED'
  | 'UNPROCESSED';

export type LocationCriteria = {
  city?: TextCriteria | null | undefined;
  street?: TextCriteria | null | undefined;
};

export type NotificationStatus =
  | 'APPROVED'
  | 'COMPLETED'
  | 'MESSAGE_FAILED'
  | 'PENDING_APPROVAL'
  | 'REJECTED'
  | 'UNASSIGNED';

export type Operator =
  | 'AFTER'
  | 'BEFORE'
  | 'EQUAL';

export type Page = {
  pageNumber: number;
  pageSize: number;
};

export type PatientNameCriteria = {
  first?: TextCriteria | null | undefined;
  last?: TextCriteria | null | undefined;
};

export type PersonFilter = {
  abcCase?: string | null | undefined;
  accessionNumber?: string | null | undefined;
  address?: string | null | undefined;
  bornOn?: DateCriteria | null | undefined;
  city?: string | null | undefined;
  cityCountyCase?: string | null | undefined;
  country?: string | null | undefined;
  dateOfBirth?: unknown;
  dateOfBirthOperator?: Operator | null | undefined;
  deceased?: Deceased | null | undefined;
  disableSoundex?: boolean | null | undefined;
  document?: string | null | undefined;
  email?: string | null | undefined;
  ethnicity?: string | null | undefined;
  filter?: Filter | null | undefined;
  firstName?: string | null | undefined;
  gender?: string | null | undefined;
  id?: string | null | undefined;
  identification?: IdentificationCriteria | null | undefined;
  investigation?: string | null | undefined;
  labReport?: string | null | undefined;
  lastName?: string | null | undefined;
  location?: LocationCriteria | null | undefined;
  morbidity?: string | null | undefined;
  mortalityStatus?: string | null | undefined;
  name?: PatientNameCriteria | null | undefined;
  notification?: string | null | undefined;
  phoneNumber?: string | null | undefined;
  race?: string | null | undefined;
  recordStatus: Array<RecordStatus>;
  state?: string | null | undefined;
  stateCase?: string | null | undefined;
  treatment?: string | null | undefined;
  vaccination?: string | null | undefined;
  zip?: string | null | undefined;
};

export type PregnancyStatus =
  | 'NO'
  | 'UNKNOWN'
  | 'YES';

export type ProcessingStatus =
  | 'AWAITING_INTERVIEW'
  | 'CLOSED_CASE'
  | 'FIELD_FOLLOW_UP'
  | 'NO_FOLLOW_UP'
  | 'OPEN_CASE'
  | 'SURVEILLANCE_FOLLOW_UP'
  | 'UNASSIGNED';

export type ProviderFacilitySearch = {
  entityType: ReportingEntityType;
  id: string | number;
};

export type ProviderType =
  | 'ORDERING_FACILITY'
  | 'ORDERING_PROVIDER'
  | 'REPORTING_FACILITY';

export type RecordStatus =
  | 'ACTIVE'
  | 'INACTIVE'
  | 'LOG_DEL'
  | 'SUPERCEDED';

export type ReportingEntityType =
  | 'FACILITY'
  | 'PROVIDER';

export type Sort = {
  direction: unknown;
  property: string;
};

export type SortDirection =
  | 'ASC'
  | 'DESC';

export type SortField =
  | 'address'
  | 'birthTime'
  | 'city'
  | 'condition'
  | 'country'
  | 'county'
  | 'email'
  | 'firstNm'
  | 'id'
  | 'identification'
  | 'investigationId'
  | 'investigator'
  | 'jurisdiction'
  | 'lastNm'
  | 'local_id'
  | 'notification'
  | 'phoneNumber'
  | 'relevance'
  | 'sex'
  | 'startDate'
  | 'state'
  | 'status'
  | 'zip';

export type SortablePage = {
  pageNumber?: number | null | undefined;
  pageSize?: number | null | undefined;
  sort?: Sort | null | undefined;
  sortDirection?: SortDirection | null | undefined;
  sortField?: SortField | null | undefined;
};

export type TextCriteria = {
  contains?: string | null | undefined;
  equals?: string | null | undefined;
  not?: string | null | undefined;
  soundsLike?: string | null | undefined;
  startsWith?: string | null | undefined;
};

export type UserType =
  | 'EXTERNAL'
  | 'INTERNAL';

export type FindAllJurisdictionsQueryVariables = Exact<{
  page?: Page | null | undefined;
}>;


export type FindAllJurisdictionsQuery = { findAllJurisdictions: Array<{ id: string, typeCd: string, assigningAuthorityCd: string | null, assigningAuthorityDescTxt: string | null, codeDescTxt: string | null, codeShortDescTxt: string | null, effectiveFromTime: unknown, effectiveToTime: unknown, indentLevelNbr: number | null, isModifiableInd: string | null, parentIsCd: string | null, stateDomainCd: string | null, statusCd: string | null, statusTime: unknown, codeSetNm: string | null, codeSeqNum: number | null, nbsUid: string | null, sourceConceptId: string | null, codeSystemCd: string | null, codeSystemDescTxt: string | null, exportInd: string | null } | null> };

export type FindAllProgramAreasQueryVariables = Exact<{
  page?: Page | null | undefined;
}>;


export type FindAllProgramAreasQuery = { findAllProgramAreas: Array<{ id: string, progAreaDescTxt: string | null, nbsUid: string | null, statusCd: string | null, statusTime: unknown, codeSetNm: string | null, codeSeq: number | null } | null> };

export type FindInvestigationsByFilterQueryVariables = Exact<{
  filter: InvestigationFilter;
  page?: SortablePage | null | undefined;
}>;


export type FindInvestigationsByFilterQuery = { findInvestigationsByFilter: { total: number, page: number, size: number, content: Array<{ relevance: number, id: string | null, cdDescTxt: string | null, jurisdictionCodeDescTxt: string | null, localId: string | null, addTime: unknown, startedOn: unknown, investigationStatusCd: string | null, notificationRecordStatusCd: string | null, investigatorLastName: string | null, personParticipations: Array<{ birthTime: unknown, currSexCd: string | null, typeCd: string, firstName: string | null, lastName: string | null, personCd: string, personParentUid: number | null, shortId: number | null }> }> } };

export type FindLabReportsByFilterQueryVariables = Exact<{
  filter: LabReportFilter;
  page?: SortablePage | null | undefined;
}>;


export type FindLabReportsByFilterQuery = { findLabReportsByFilter: { total: number, page: number, size: number, content: Array<{ relevance: number, id: string, jurisdictionCd: number, localId: string, addTime: unknown, personParticipations: Array<{ birthTime: unknown, currSexCd: string | null, typeCd: string | null, firstName: string | null, lastName: string | null, personCd: string, personParentUid: number | null, shortId: number | null }>, organizationParticipations: Array<{ typeCd: string, name: string }>, observations: Array<{ cdDescTxt: string | null, statusCd: string | null, altCd: string | null, displayName: string | null }>, associatedInvestigations: Array<{ cdDescTxt: string, localId: string }>, tests: Array<{ name: string | null, status: string | null, coded: string | null, numeric: number | null, high: string | null, low: string | null, unit: string | null }> }> } };

export type FindPatientsByFilterQueryVariables = Exact<{
  filter: PersonFilter;
  page?: SortablePage | null | undefined;
  share?: string | null | undefined;
}>;


export type FindPatientsByFilterQuery = { findPatientsByFilter: { total: number, page: number, size: number, content: Array<{ patient: number, birthday: unknown, age: number | null, gender: string | null, status: string, shortId: number, phones: Array<string>, emails: Array<string>, legalName: { type: string | null, first: string | null, middle: string | null, last: string | null, suffix: string | null } | null, names: Array<{ type: string | null, first: string | null, middle: string | null, last: string | null, suffix: string | null }>, identification: Array<{ type: string, value: string }>, addresses: Array<{ type: string | null, use: string | null, address: string | null, address2: string | null, city: string | null, county: string | null, state: string | null, zipcode: string | null }>, detailedPhones: Array<{ type: string, use: string, number: string | null }> }> } };


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
// @ts-ignore
export function useFindAllJurisdictionsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>): Apollo.UseSuspenseQueryResult<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>;
export function useFindAllJurisdictionsSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>): Apollo.UseSuspenseQueryResult<FindAllJurisdictionsQuery | undefined, FindAllJurisdictionsQueryVariables>;
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
// @ts-ignore
export function useFindAllProgramAreasSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>): Apollo.UseSuspenseQueryResult<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>;
export function useFindAllProgramAreasSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>): Apollo.UseSuspenseQueryResult<FindAllProgramAreasQuery | undefined, FindAllProgramAreasQueryVariables>;
export function useFindAllProgramAreasSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>(FindAllProgramAreasDocument, options);
        }
export type FindAllProgramAreasQueryHookResult = ReturnType<typeof useFindAllProgramAreasQuery>;
export type FindAllProgramAreasLazyQueryHookResult = ReturnType<typeof useFindAllProgramAreasLazyQuery>;
export type FindAllProgramAreasSuspenseQueryHookResult = ReturnType<typeof useFindAllProgramAreasSuspenseQuery>;
export type FindAllProgramAreasQueryResult = Apollo.QueryResult<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>;
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
// @ts-ignore
export function useFindInvestigationsByFilterSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>): Apollo.UseSuspenseQueryResult<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>;
export function useFindInvestigationsByFilterSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>): Apollo.UseSuspenseQueryResult<FindInvestigationsByFilterQuery | undefined, FindInvestigationsByFilterQueryVariables>;
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
// @ts-ignore
export function useFindLabReportsByFilterSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>): Apollo.UseSuspenseQueryResult<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>;
export function useFindLabReportsByFilterSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>): Apollo.UseSuspenseQueryResult<FindLabReportsByFilterQuery | undefined, FindLabReportsByFilterQueryVariables>;
export function useFindLabReportsByFilterSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>(FindLabReportsByFilterDocument, options);
        }
export type FindLabReportsByFilterQueryHookResult = ReturnType<typeof useFindLabReportsByFilterQuery>;
export type FindLabReportsByFilterLazyQueryHookResult = ReturnType<typeof useFindLabReportsByFilterLazyQuery>;
export type FindLabReportsByFilterSuspenseQueryHookResult = ReturnType<typeof useFindLabReportsByFilterSuspenseQuery>;
export type FindLabReportsByFilterQueryResult = Apollo.QueryResult<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>;
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
// @ts-ignore
export function useFindPatientsByFilterSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>): Apollo.UseSuspenseQueryResult<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>;
export function useFindPatientsByFilterSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>): Apollo.UseSuspenseQueryResult<FindPatientsByFilterQuery | undefined, FindPatientsByFilterQueryVariables>;
export function useFindPatientsByFilterSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(FindPatientsByFilterDocument, options);
        }
export type FindPatientsByFilterQueryHookResult = ReturnType<typeof useFindPatientsByFilterQuery>;
export type FindPatientsByFilterLazyQueryHookResult = ReturnType<typeof useFindPatientsByFilterLazyQuery>;
export type FindPatientsByFilterSuspenseQueryHookResult = ReturnType<typeof useFindPatientsByFilterSuspenseQuery>;
export type FindPatientsByFilterQueryResult = Apollo.QueryResult<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>;