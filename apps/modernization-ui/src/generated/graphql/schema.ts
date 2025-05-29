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

export type AdministrativeInput = {
  asOf: Scalars['Date']['input'];
  comment?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
};

export type Allowed = {
  __typename?: 'Allowed';
  value?: Maybe<Scalars['String']['output']>;
};

export type AssociatedInvestigation = {
  __typename?: 'AssociatedInvestigation';
  cdDescTxt: Scalars['String']['output'];
  localId: Scalars['String']['output'];
};

export type AssociatedInvestigation2 = {
  __typename?: 'AssociatedInvestigation2';
  cdDescTxt: Scalars['String']['output'];
  localId: Scalars['String']['output'];
  publicHealthCaseUid: Scalars['Int']['output'];
};

export enum CaseStatus {
  Confirmed = 'CONFIRMED',
  NotACase = 'NOT_A_CASE',
  Probable = 'PROBABLE',
  Suspect = 'SUSPECT',
  Unassigned = 'UNASSIGNED',
  Unknown = 'UNKNOWN'
}

export type CodedResult = {
  __typename?: 'CodedResult';
  name: Scalars['String']['output'];
};

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

export type DeletePatientAddressInput = {
  id: Scalars['Int']['input'];
  patient: Scalars['Int']['input'];
};

export type DeletePatientIdentificationInput = {
  patient: Scalars['Int']['input'];
  sequence: Scalars['Int']['input'];
};

export type DeletePatientNameInput = {
  patient: Scalars['Int']['input'];
  sequence: Scalars['Int']['input'];
};

export type DeletePatientPhoneInput = {
  id: Scalars['Int']['input'];
  patient: Scalars['Int']['input'];
};

export type DeletePatientRace = {
  category: Scalars['String']['input'];
  patient: Scalars['Int']['input'];
};

export type Description = {
  __typename?: 'Description';
  title?: Maybe<Scalars['String']['output']>;
  value?: Maybe<Scalars['String']['output']>;
};

export type DocumentRequiringReview = {
  __typename?: 'DocumentRequiringReview';
  dateReceived: Scalars['DateTime']['output'];
  descriptions: Array<Maybe<Description>>;
  eventDate?: Maybe<Scalars['DateTime']['output']>;
  facilityProviders: FacilityProviders;
  id: Scalars['ID']['output'];
  isElectronic: Scalars['Boolean']['output'];
  localId: Scalars['String']['output'];
  type: Scalars['String']['output'];
};

export enum DocumentRequiringReviewSortableField {
  DateReceived = 'dateReceived',
  EventDate = 'eventDate',
  LocalId = 'localId',
  Type = 'type'
}

export type DocumentRequiringReviewSortablePage = {
  pageNumber?: InputMaybe<Scalars['Int']['input']>;
  pageSize?: InputMaybe<Scalars['Int']['input']>;
  sortDirection?: InputMaybe<SortDirection>;
  sortField?: InputMaybe<DocumentRequiringReviewSortableField>;
};

export enum EntryMethod {
  Electronic = 'ELECTRONIC',
  Manual = 'MANUAL'
}

export type EthnicityInput = {
  asOf: Scalars['Date']['input'];
  detailed?: InputMaybe<Array<Scalars['String']['input']>>;
  ethnicGroup?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['String']['input'];
  unknownReason?: InputMaybe<Scalars['String']['input']>;
};

export type EventId = {
  id: Scalars['String']['input'];
  investigationEventType: InvestigationEventIdType;
};

export enum EventStatus {
  New = 'NEW',
  Update = 'UPDATE'
}

export type FacilityProviders = {
  __typename?: 'FacilityProviders';
  orderingProvider?: Maybe<OrderingProvider>;
  reportingFacility?: Maybe<ReportingFacility>;
  sendingFacility?: Maybe<SendingFacility>;
};

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

export type GeneralInfoInput = {
  adultsInHouse?: InputMaybe<Scalars['Int']['input']>;
  asOf: Scalars['Date']['input'];
  childrenInHouse?: InputMaybe<Scalars['Int']['input']>;
  educationLevel?: InputMaybe<Scalars['String']['input']>;
  maritalStatus?: InputMaybe<Scalars['String']['input']>;
  maternalMaidenName?: InputMaybe<Scalars['String']['input']>;
  occupation?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  primaryLanguage?: InputMaybe<Scalars['String']['input']>;
  speaksEnglish?: InputMaybe<Scalars['String']['input']>;
  stateHIVCase?: InputMaybe<Scalars['String']['input']>;
};

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

export type MaterialParticipation2 = {
  __typename?: 'MaterialParticipation2';
  actUid: Scalars['Int']['output'];
  cd: Scalars['String']['output'];
  cdDescTxt: Scalars['String']['output'];
  entityId?: Maybe<Scalars['String']['output']>;
  participationLastChangeTime?: Maybe<Scalars['DateTime']['output']>;
  participationRecordStatus?: Maybe<Scalars['String']['output']>;
  subjectClassCd: Scalars['String']['output'];
  typeCd: Scalars['String']['output'];
  typeDescTxt?: Maybe<Scalars['String']['output']>;
};

export type MortalityInput = {
  asOf: Scalars['Date']['input'];
  city?: InputMaybe<Scalars['String']['input']>;
  country?: InputMaybe<Scalars['String']['input']>;
  county?: InputMaybe<Scalars['String']['input']>;
  deceased?: InputMaybe<Scalars['String']['input']>;
  deceasedOn?: InputMaybe<Scalars['Date']['input']>;
  patient: Scalars['Int']['input'];
  state?: InputMaybe<Scalars['String']['input']>;
};

export type Mutation = {
  __typename?: 'Mutation';
  addPatientAddress: PatientAddressChangeResult;
  addPatientIdentification: PatientIdentificationChangeResult;
  addPatientName: PatientNameChangeResult;
  addPatientPhone: PatientPhoneChangeResult;
  addPatientRace: PatientRaceAddResult;
  deletePatientAddress: PatientAddressChangeResult;
  deletePatientIdentification: PatientIdentificationChangeResult;
  deletePatientName: PatientNameChangeResult;
  deletePatientPhone: PatientPhoneChangeResult;
  deletePatientRace: PatientRaceChangeSuccessful;
  updateEthnicity: PatientEthnicityChangeResult;
  updatePatientAddress: PatientAddressChangeResult;
  updatePatientBirthAndGender: PatientBirthAndGenderChangeResult;
  updatePatientGeneralInfo: PatientGeneralChangeResult;
  updatePatientIdentification: PatientIdentificationChangeResult;
  updatePatientMortality: PatientMortalityChangeResult;
  updatePatientName: PatientNameChangeResult;
  updatePatientPhone: PatientPhoneChangeResult;
  updatePatientRace: PatientRaceChangeSuccessful;
};


export type MutationAddPatientAddressArgs = {
  input: NewPatientAddressInput;
};


export type MutationAddPatientIdentificationArgs = {
  input: NewPatientIdentificationInput;
};


export type MutationAddPatientNameArgs = {
  input: NewPatientNameInput;
};


export type MutationAddPatientPhoneArgs = {
  input: NewPatientPhoneInput;
};


export type MutationAddPatientRaceArgs = {
  input: RaceInput;
};


export type MutationDeletePatientAddressArgs = {
  input?: InputMaybe<DeletePatientAddressInput>;
};


export type MutationDeletePatientIdentificationArgs = {
  input?: InputMaybe<DeletePatientIdentificationInput>;
};


export type MutationDeletePatientNameArgs = {
  input: DeletePatientNameInput;
};


export type MutationDeletePatientPhoneArgs = {
  input?: InputMaybe<DeletePatientPhoneInput>;
};


export type MutationDeletePatientRaceArgs = {
  input: DeletePatientRace;
};


export type MutationUpdateEthnicityArgs = {
  input: EthnicityInput;
};


export type MutationUpdatePatientAddressArgs = {
  input: UpdatePatientAddressInput;
};


export type MutationUpdatePatientBirthAndGenderArgs = {
  input: UpdateBirthAndGenderInput;
};


export type MutationUpdatePatientGeneralInfoArgs = {
  input: GeneralInfoInput;
};


export type MutationUpdatePatientIdentificationArgs = {
  input: UpdatePatientIdentificationInput;
};


export type MutationUpdatePatientMortalityArgs = {
  input: MortalityInput;
};


export type MutationUpdatePatientNameArgs = {
  input: UpdatePatientNameInput;
};


export type MutationUpdatePatientPhoneArgs = {
  input: UpdatePatientPhoneInput;
};


export type MutationUpdatePatientRaceArgs = {
  input: RaceInput;
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

export type NewPatientAddressInput = {
  address1?: InputMaybe<Scalars['String']['input']>;
  address2?: InputMaybe<Scalars['String']['input']>;
  asOf: Scalars['Date']['input'];
  censusTract?: InputMaybe<Scalars['String']['input']>;
  city?: InputMaybe<Scalars['String']['input']>;
  comment?: InputMaybe<Scalars['String']['input']>;
  country?: InputMaybe<Scalars['String']['input']>;
  county?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  state?: InputMaybe<Scalars['String']['input']>;
  type: Scalars['String']['input'];
  use: Scalars['String']['input'];
  zipcode?: InputMaybe<Scalars['String']['input']>;
};

export type NewPatientIdentificationInput = {
  asOf: Scalars['Date']['input'];
  authority?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  type: Scalars['String']['input'];
  value: Scalars['String']['input'];
};

export type NewPatientNameInput = {
  asOf: Scalars['Date']['input'];
  degree?: InputMaybe<Scalars['String']['input']>;
  first?: InputMaybe<Scalars['String']['input']>;
  last?: InputMaybe<Scalars['String']['input']>;
  middle?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  prefix?: InputMaybe<Scalars['String']['input']>;
  secondLast?: InputMaybe<Scalars['String']['input']>;
  secondMiddle?: InputMaybe<Scalars['String']['input']>;
  suffix?: InputMaybe<Scalars['String']['input']>;
  type: Scalars['String']['input'];
};

export type NewPatientPhoneInput = {
  asOf: Scalars['Date']['input'];
  comment?: InputMaybe<Scalars['String']['input']>;
  countryCode?: InputMaybe<Scalars['String']['input']>;
  email?: InputMaybe<Scalars['String']['input']>;
  extension?: InputMaybe<Scalars['String']['input']>;
  number?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  type: Scalars['String']['input'];
  url?: InputMaybe<Scalars['String']['input']>;
  use: Scalars['String']['input'];
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

export type Observation2 = {
  __typename?: 'Observation2';
  cdDescTxt: Scalars['String']['output'];
  displayName?: Maybe<Scalars['String']['output']>;
  domainCd: Scalars['String']['output'];
};

export enum Operator {
  After = 'AFTER',
  Before = 'BEFORE',
  Equal = 'EQUAL'
}

export type OrderingProvider = {
  __typename?: 'OrderingProvider';
  name?: Maybe<Scalars['String']['output']>;
};

export type OrganizationParticipation2 = {
  __typename?: 'OrganizationParticipation2';
  name?: Maybe<Scalars['String']['output']>;
  typeCd?: Maybe<Scalars['String']['output']>;
};

export type Page = {
  pageNumber: Scalars['Int']['input'];
  pageSize: Scalars['Int']['input'];
};

export type PatientAddress = {
  __typename?: 'PatientAddress';
  address1?: Maybe<Scalars['String']['output']>;
  address2?: Maybe<Scalars['String']['output']>;
  asOf: Scalars['Date']['output'];
  censusTract?: Maybe<Scalars['String']['output']>;
  city?: Maybe<Scalars['String']['output']>;
  comment?: Maybe<Scalars['String']['output']>;
  country?: Maybe<PatientCountry>;
  county?: Maybe<PatientCounty>;
  id: Scalars['ID']['output'];
  patient: Scalars['Int']['output'];
  state?: Maybe<PatientState>;
  type: PatientAddressType;
  use: PatientAddressUse;
  version: Scalars['Int']['output'];
  zipcode?: Maybe<Scalars['String']['output']>;
};

export type PatientAddressChangeResult = {
  __typename?: 'PatientAddressChangeResult';
  id: Scalars['Int']['output'];
  patient: Scalars['Int']['output'];
};

export type PatientAddressResults = {
  __typename?: 'PatientAddressResults';
  content: Array<PatientAddress>;
  number: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientAddressType = {
  __typename?: 'PatientAddressType';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientAddressUse = {
  __typename?: 'PatientAddressUse';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientAdministrativeChangeResult = {
  __typename?: 'PatientAdministrativeChangeResult';
  patient: Scalars['Int']['output'];
};

export type PatientBirth = {
  __typename?: 'PatientBirth';
  age?: Maybe<Scalars['Int']['output']>;
  asOf: Scalars['Date']['output'];
  birthOrder?: Maybe<Scalars['Int']['output']>;
  bornOn?: Maybe<Scalars['Date']['output']>;
  city?: Maybe<Scalars['String']['output']>;
  country?: Maybe<PatientCountry>;
  county?: Maybe<PatientCounty>;
  id: Scalars['ID']['output'];
  multipleBirth?: Maybe<PatientIndicatorCodedValue>;
  patient: Scalars['Int']['output'];
  state?: Maybe<PatientState>;
  version: Scalars['Int']['output'];
};

export type PatientBirthAndGenderChangeResult = {
  __typename?: 'PatientBirthAndGenderChangeResult';
  patient: Scalars['Int']['output'];
};

export type PatientContactInvestigation = {
  __typename?: 'PatientContactInvestigation';
  condition: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  local: Scalars['String']['output'];
};

export type PatientCountry = {
  __typename?: 'PatientCountry';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientCounty = {
  __typename?: 'PatientCounty';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientDetailedEthnicity = {
  __typename?: 'PatientDetailedEthnicity';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
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

export type PatientDocumentRequiringReviewResults = {
  __typename?: 'PatientDocumentRequiringReviewResults';
  content: Array<Maybe<DocumentRequiringReview>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientDocumentResults = {
  __typename?: 'PatientDocumentResults';
  content: Array<Maybe<PatientDocument>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientEducationLevel = {
  __typename?: 'PatientEducationLevel';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientEthnicGroup = {
  __typename?: 'PatientEthnicGroup';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientEthnicity = {
  __typename?: 'PatientEthnicity';
  asOf: Scalars['Date']['output'];
  detailed: Array<PatientDetailedEthnicity>;
  ethnicGroup: PatientEthnicGroup;
  id: Scalars['ID']['output'];
  patient: Scalars['Int']['output'];
  unknownReason?: Maybe<PatientEthnicityUnknownReason>;
  version: Scalars['Int']['output'];
};

export type PatientEthnicityChangeResult = {
  __typename?: 'PatientEthnicityChangeResult';
  patient: Scalars['String']['output'];
};

export type PatientEthnicityUnknownReason = {
  __typename?: 'PatientEthnicityUnknownReason';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientGender = {
  __typename?: 'PatientGender';
  additional?: Maybe<Scalars['String']['output']>;
  asOf: Scalars['Date']['output'];
  birth?: Maybe<PatientGenderCodedValue>;
  current?: Maybe<PatientGenderCodedValue>;
  id: Scalars['ID']['output'];
  patient: Scalars['Int']['output'];
  preferred?: Maybe<PatientPreferredGender>;
  unknownReason?: Maybe<PatientGenderUnknownReason>;
  version: Scalars['Int']['output'];
};

export type PatientGenderCodedValue = {
  __typename?: 'PatientGenderCodedValue';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientGenderUnknownReason = {
  __typename?: 'PatientGenderUnknownReason';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientGeneral = {
  __typename?: 'PatientGeneral';
  adultsInHouse?: Maybe<Scalars['Int']['output']>;
  asOf: Scalars['Date']['output'];
  childrenInHouse?: Maybe<Scalars['Int']['output']>;
  educationLevel?: Maybe<PatientEducationLevel>;
  id: Scalars['ID']['output'];
  maritalStatus?: Maybe<PatientMaritalStatus>;
  maternalMaidenName?: Maybe<Scalars['String']['output']>;
  occupation?: Maybe<PatientOccupation>;
  patient: Scalars['Int']['output'];
  primaryLanguage?: Maybe<PatientPrimaryLanguage>;
  speaksEnglish?: Maybe<PatientIndicatorCodedValue>;
  stateHIVCase?: Maybe<Sensitive>;
  version: Scalars['Int']['output'];
};

export type PatientGeneralChangeResult = {
  __typename?: 'PatientGeneralChangeResult';
  patient: Scalars['Int']['output'];
};

export type PatientIdentification = {
  __typename?: 'PatientIdentification';
  asOf: Scalars['Date']['output'];
  authority?: Maybe<PatientIdentificationAuthority>;
  patient: Scalars['Int']['output'];
  sequence: Scalars['Int']['output'];
  type: PatientIdentificationType;
  value?: Maybe<Scalars['String']['output']>;
  version: Scalars['Int']['output'];
};

export type PatientIdentificationAuthority = {
  __typename?: 'PatientIdentificationAuthority';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientIdentificationChangeResult = {
  __typename?: 'PatientIdentificationChangeResult';
  patient: Scalars['Int']['output'];
  sequence: Scalars['Int']['output'];
};

export type PatientIdentificationResults = {
  __typename?: 'PatientIdentificationResults';
  content: Array<PatientIdentification>;
  number: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientIdentificationType = {
  __typename?: 'PatientIdentificationType';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientIndicatorCodedValue = {
  __typename?: 'PatientIndicatorCodedValue';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientInvestigation = {
  __typename?: 'PatientInvestigation';
  caseStatus?: Maybe<Scalars['String']['output']>;
  coInfection?: Maybe<Scalars['String']['output']>;
  comparable: Scalars['Boolean']['output'];
  condition: Scalars['String']['output'];
  event: Scalars['String']['output'];
  investigation: Scalars['ID']['output'];
  investigator?: Maybe<Scalars['String']['output']>;
  jurisdiction: Scalars['String']['output'];
  notification?: Maybe<Scalars['String']['output']>;
  startedOn?: Maybe<Scalars['Date']['output']>;
  status: Scalars['String']['output'];
};

export type PatientInvestigationResults = {
  __typename?: 'PatientInvestigationResults';
  content: Array<Maybe<PatientInvestigation>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientLabReport = {
  __typename?: 'PatientLabReport';
  addTime: Scalars['DateTime']['output'];
  associatedInvestigations: Array<AssociatedInvestigation2>;
  effectiveFromTime?: Maybe<Scalars['DateTime']['output']>;
  electronicInd?: Maybe<Scalars['String']['output']>;
  id: Scalars['String']['output'];
  jurisdictionCodeDescTxt: Scalars['String']['output'];
  localId: Scalars['String']['output'];
  observationUid: Scalars['Int']['output'];
  observations: Array<Observation2>;
  organizationParticipations: Array<OrganizationParticipation2>;
  personParticipations: Array<PersonParticipation2>;
  programAreaCd: Scalars['String']['output'];
};

export type PatientLabReportFilter = {
  codedResult?: InputMaybe<Scalars['String']['input']>;
  createdBy?: InputMaybe<Scalars['ID']['input']>;
  enteredBy?: InputMaybe<Array<InputMaybe<UserType>>>;
  entryMethods?: InputMaybe<Array<InputMaybe<EntryMethod>>>;
  eventDate?: InputMaybe<LaboratoryEventDateSearch>;
  eventId?: InputMaybe<LabReportEventId>;
  eventStatus?: InputMaybe<Array<InputMaybe<EventStatus>>>;
  jurisdictions?: InputMaybe<Array<InputMaybe<Scalars['ID']['input']>>>;
  lastUpdatedBy?: InputMaybe<Scalars['ID']['input']>;
  orderingLabId?: InputMaybe<Scalars['ID']['input']>;
  orderingProviderId?: InputMaybe<Scalars['ID']['input']>;
  patientId?: InputMaybe<Scalars['Int']['input']>;
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatus?: InputMaybe<Array<InputMaybe<LaboratoryReportStatus>>>;
  programAreas?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  providerSearch?: InputMaybe<LabReportProviderSearch>;
  reportingLabId?: InputMaybe<Scalars['ID']['input']>;
  resultedTest?: InputMaybe<Scalars['String']['input']>;
};

export type PatientLabReportResults = {
  __typename?: 'PatientLabReportResults';
  content: Array<Maybe<PatientLabReport>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientLegalName = {
  __typename?: 'PatientLegalName';
  first?: Maybe<Scalars['String']['output']>;
  last?: Maybe<Scalars['String']['output']>;
  middle?: Maybe<Scalars['String']['output']>;
  prefix?: Maybe<Scalars['String']['output']>;
  suffix?: Maybe<Scalars['String']['output']>;
};

export type PatientMaritalStatus = {
  __typename?: 'PatientMaritalStatus';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientMorbidity = {
  __typename?: 'PatientMorbidity';
  associatedWith?: Maybe<PatientMorbidityInvestigation>;
  condition: Scalars['String']['output'];
  event: Scalars['String']['output'];
  jurisdiction: Scalars['String']['output'];
  labResults: Array<Maybe<PatientMorbidityLabResult>>;
  morbidity: Scalars['ID']['output'];
  provider?: Maybe<Scalars['String']['output']>;
  receivedOn: Scalars['DateTime']['output'];
  reportedOn: Scalars['DateTime']['output'];
  treatments: Array<Maybe<Scalars['String']['output']>>;
};

export type PatientMorbidityInvestigation = {
  __typename?: 'PatientMorbidityInvestigation';
  condition: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  local: Scalars['String']['output'];
};

export type PatientMorbidityLabResult = {
  __typename?: 'PatientMorbidityLabResult';
  codedResult?: Maybe<Scalars['String']['output']>;
  labTest: Scalars['String']['output'];
  numericResult?: Maybe<Scalars['String']['output']>;
  status?: Maybe<Scalars['String']['output']>;
  textResult?: Maybe<Scalars['String']['output']>;
};

export type PatientMorbidityResults = {
  __typename?: 'PatientMorbidityResults';
  content: Array<Maybe<PatientMorbidity>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientMortality = {
  __typename?: 'PatientMortality';
  asOf: Scalars['Date']['output'];
  city?: Maybe<Scalars['String']['output']>;
  country?: Maybe<PatientCountry>;
  county?: Maybe<PatientCounty>;
  deceased?: Maybe<PatientIndicatorCodedValue>;
  deceasedOn?: Maybe<Scalars['Date']['output']>;
  id: Scalars['ID']['output'];
  patient: Scalars['Int']['output'];
  state?: Maybe<PatientState>;
  version: Scalars['Int']['output'];
};

export type PatientMortalityChangeResult = {
  __typename?: 'PatientMortalityChangeResult';
  patient: Scalars['Int']['output'];
};

export type PatientName = {
  __typename?: 'PatientName';
  asOf: Scalars['Date']['output'];
  degree?: Maybe<PatientNameDegree>;
  first?: Maybe<Scalars['String']['output']>;
  last?: Maybe<Scalars['String']['output']>;
  middle?: Maybe<Scalars['String']['output']>;
  patient: Scalars['Int']['output'];
  prefix?: Maybe<PatientNamePrefix>;
  secondLast?: Maybe<Scalars['String']['output']>;
  secondMiddle?: Maybe<Scalars['String']['output']>;
  sequence: Scalars['Int']['output'];
  suffix?: Maybe<PatientNameSuffix>;
  use: PatientNameUse;
  version: Scalars['Int']['output'];
};

export type PatientNameChangeResult = {
  __typename?: 'PatientNameChangeResult';
  patient: Scalars['Int']['output'];
  sequence: Scalars['Int']['output'];
};

export type PatientNameCriteria = {
  first?: InputMaybe<TextCriteria>;
  last?: InputMaybe<TextCriteria>;
};

export type PatientNameDegree = {
  __typename?: 'PatientNameDegree';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientNamePrefix = {
  __typename?: 'PatientNamePrefix';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientNameResults = {
  __typename?: 'PatientNameResults';
  content: Array<PatientName>;
  number: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientNameSuffix = {
  __typename?: 'PatientNameSuffix';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientNameUse = {
  __typename?: 'PatientNameUse';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientNamedByContactResults = {
  __typename?: 'PatientNamedByContactResults';
  content: Array<Maybe<NamedByPatient>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientOccupation = {
  __typename?: 'PatientOccupation';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientPhone = {
  __typename?: 'PatientPhone';
  asOf: Scalars['Date']['output'];
  comment?: Maybe<Scalars['String']['output']>;
  countryCode?: Maybe<Scalars['String']['output']>;
  email?: Maybe<Scalars['String']['output']>;
  extension?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  number?: Maybe<Scalars['String']['output']>;
  patient: Scalars['Int']['output'];
  type?: Maybe<PatientPhoneType>;
  url?: Maybe<Scalars['String']['output']>;
  use?: Maybe<PatientPhoneUse>;
  version: Scalars['Int']['output'];
};

export type PatientPhoneChangeResult = {
  __typename?: 'PatientPhoneChangeResult';
  id: Scalars['Int']['output'];
  patient: Scalars['Int']['output'];
};

export type PatientPhoneResults = {
  __typename?: 'PatientPhoneResults';
  content: Array<PatientPhone>;
  number: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientPhoneType = {
  __typename?: 'PatientPhoneType';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientPhoneUse = {
  __typename?: 'PatientPhoneUse';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientPreferredGender = {
  __typename?: 'PatientPreferredGender';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientPrimaryLanguage = {
  __typename?: 'PatientPrimaryLanguage';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientProfile = {
  __typename?: 'PatientProfile';
  addresses: PatientAddressResults;
  birth?: Maybe<PatientBirth>;
  deletable: Scalars['Boolean']['output'];
  ethnicity?: Maybe<PatientEthnicity>;
  gender?: Maybe<PatientGender>;
  general?: Maybe<PatientGeneral>;
  id: Scalars['ID']['output'];
  identification: PatientIdentificationResults;
  local: Scalars['String']['output'];
  mortality?: Maybe<PatientMortality>;
  names: PatientNameResults;
  phones: PatientPhoneResults;
  races: PatientRaceResults;
  shortId: Scalars['Int']['output'];
  status: Scalars['String']['output'];
  summary?: Maybe<PatientSummary>;
  version: Scalars['Int']['output'];
};


export type PatientProfileAddressesArgs = {
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
  asOf?: InputMaybe<Scalars['Date']['input']>;
};

export type PatientRace = {
  __typename?: 'PatientRace';
  asOf: Scalars['Date']['output'];
  category: PatientRaceCategory;
  detailed: Array<PatientRaceDetail>;
  id: Scalars['Int']['output'];
  patient: Scalars['Int']['output'];
  version: Scalars['Int']['output'];
};

export type PatientRaceAddResult = PatientRaceChangeFailureExistingCategory | PatientRaceChangeSuccessful;

export type PatientRaceCategory = {
  __typename?: 'PatientRaceCategory';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientRaceChangeFailureExistingCategory = {
  __typename?: 'PatientRaceChangeFailureExistingCategory';
  category: Scalars['String']['output'];
  patient: Scalars['Int']['output'];
};

export type PatientRaceChangeSuccessful = {
  __typename?: 'PatientRaceChangeSuccessful';
  patient: Scalars['Int']['output'];
};

export type PatientRaceDetail = {
  __typename?: 'PatientRaceDetail';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientRaceResults = {
  __typename?: 'PatientRaceResults';
  content: Array<PatientRace>;
  number: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
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

export type PatientState = {
  __typename?: 'PatientState';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientSummary = {
  __typename?: 'PatientSummary';
  address: Array<PatientSummaryAddress>;
  age?: Maybe<Scalars['Int']['output']>;
  birthday?: Maybe<Scalars['Date']['output']>;
  email?: Maybe<Array<PatientSummaryEmail>>;
  ethnicity?: Maybe<Scalars['String']['output']>;
  gender?: Maybe<Scalars['String']['output']>;
  home?: Maybe<PatientSummaryAddress>;
  identification?: Maybe<Array<PatientSummaryIdentification>>;
  legalName?: Maybe<PatientLegalName>;
  phone?: Maybe<Array<PatientSummaryPhone>>;
  races?: Maybe<Array<Scalars['String']['output']>>;
};

export type PatientSummaryAddress = {
  __typename?: 'PatientSummaryAddress';
  address?: Maybe<Scalars['String']['output']>;
  address2?: Maybe<Scalars['String']['output']>;
  city?: Maybe<Scalars['String']['output']>;
  country?: Maybe<Scalars['String']['output']>;
  state?: Maybe<Scalars['String']['output']>;
  use: Scalars['String']['output'];
  zipcode?: Maybe<Scalars['String']['output']>;
};

export type PatientSummaryEmail = {
  __typename?: 'PatientSummaryEmail';
  address?: Maybe<Scalars['String']['output']>;
  use?: Maybe<Scalars['String']['output']>;
};

export type PatientSummaryIdentification = {
  __typename?: 'PatientSummaryIdentification';
  type: Scalars['String']['output'];
  value: Scalars['String']['output'];
};

export type PatientSummaryPhone = {
  __typename?: 'PatientSummaryPhone';
  number?: Maybe<Scalars['String']['output']>;
  use?: Maybe<Scalars['String']['output']>;
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

export type PatientUpdateResponse = {
  __typename?: 'PatientUpdateResponse';
  patientId: Scalars['ID']['output'];
};

export type PatientVaccination = {
  __typename?: 'PatientVaccination';
  administered: Scalars['String']['output'];
  administeredOn?: Maybe<Scalars['DateTime']['output']>;
  associatedWith?: Maybe<PatientVaccinationInvestigation>;
  createdOn: Scalars['DateTime']['output'];
  event: Scalars['String']['output'];
  provider?: Maybe<Scalars['String']['output']>;
  vaccination: Scalars['ID']['output'];
};

export type PatientVaccinationInvestigation = {
  __typename?: 'PatientVaccinationInvestigation';
  condition: Scalars['String']['output'];
  id: Scalars['ID']['output'];
  local: Scalars['String']['output'];
};

export type PatientVaccinationResults = {
  __typename?: 'PatientVaccinationResults';
  content: Array<Maybe<PatientVaccination>>;
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

export type PersonParticipation2 = {
  __typename?: 'PersonParticipation2';
  firstName?: Maybe<Scalars['String']['output']>;
  lastName?: Maybe<Scalars['String']['output']>;
  personCd: Scalars['String']['output'];
  typeCd: Scalars['String']['output'];
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
  findDistinctCodedResults: Array<CodedResult>;
  findDistinctResultedTest: Array<ResultedTest>;
  findDocumentsForPatient?: Maybe<PatientDocumentResults>;
  findDocumentsRequiringReviewForPatient: PatientDocumentRequiringReviewResults;
  findInvestigationsByFilter: InvestigationResults;
  findInvestigationsForPatient?: Maybe<PatientInvestigationResults>;
  findLabReportsByFilter: LabReportResults;
  findLabReportsForPatient?: Maybe<PatientLabReportResults>;
  findMorbidityReportsForPatient?: Maybe<PatientMorbidityResults>;
  findPatientNamedByContact?: Maybe<PatientNamedByContactResults>;
  findPatientProfile?: Maybe<PatientProfile>;
  findPatientsByFilter: PatientSearchResults;
  findTreatmentsForPatient?: Maybe<PatientTreatmentResults>;
  findVaccinationsForPatient?: Maybe<PatientVaccinationResults>;
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


export type QueryFindDistinctCodedResultsArgs = {
  searchText: Scalars['String']['input'];
};


export type QueryFindDistinctResultedTestArgs = {
  searchText: Scalars['String']['input'];
};


export type QueryFindDocumentsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindDocumentsRequiringReviewForPatientArgs = {
  page?: InputMaybe<DocumentRequiringReviewSortablePage>;
  patient: Scalars['Int']['input'];
};


export type QueryFindInvestigationsByFilterArgs = {
  filter: InvestigationFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindInvestigationsForPatientArgs = {
  openOnly?: InputMaybe<Scalars['Boolean']['input']>;
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindLabReportsByFilterArgs = {
  filter: LabReportFilter;
  page?: InputMaybe<SortablePage>;
};


export type QueryFindLabReportsForPatientArgs = {
  page?: InputMaybe<Page>;
  personUid: Scalars['Int']['input'];
};


export type QueryFindMorbidityReportsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindPatientNamedByContactArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindPatientProfileArgs = {
  patient?: InputMaybe<Scalars['ID']['input']>;
  shortId?: InputMaybe<Scalars['Int']['input']>;
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


export type QueryFindVaccinationsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};

export type RaceInput = {
  asOf?: InputMaybe<Scalars['Date']['input']>;
  category: Scalars['String']['input'];
  detailed?: InputMaybe<Array<Scalars['String']['input']>>;
  patient: Scalars['Int']['input'];
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

export type ReportingFacility = {
  __typename?: 'ReportingFacility';
  name?: Maybe<Scalars['String']['output']>;
};

export type Restricted = {
  __typename?: 'Restricted';
  reason: Scalars['String']['output'];
};

export type ResultedTest = {
  __typename?: 'ResultedTest';
  name: Scalars['String']['output'];
};

export type SendingFacility = {
  __typename?: 'SendingFacility';
  name?: Maybe<Scalars['String']['output']>;
};

export type Sensitive = Allowed | Restricted;

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

export enum Suffix {
  Esq = 'ESQ',
  Ii = 'II',
  Iii = 'III',
  Iv = 'IV',
  Jr = 'JR',
  Sr = 'SR',
  V = 'V'
}

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

export type UpdateBirthAndGenderInput = {
  asOf: Scalars['Date']['input'];
  birth?: InputMaybe<UpdateBirthInput>;
  gender?: InputMaybe<UpdateGenderInput>;
  patient: Scalars['ID']['input'];
};

export type UpdateBirthInput = {
  birthOrder?: InputMaybe<Scalars['Int']['input']>;
  bornOn?: InputMaybe<Scalars['Date']['input']>;
  city?: InputMaybe<Scalars['String']['input']>;
  country?: InputMaybe<Scalars['String']['input']>;
  county?: InputMaybe<Scalars['String']['input']>;
  gender?: InputMaybe<Scalars['String']['input']>;
  multipleBirth?: InputMaybe<Scalars['String']['input']>;
  state?: InputMaybe<Scalars['String']['input']>;
};

export type UpdateGenderInput = {
  additional?: InputMaybe<Scalars['String']['input']>;
  current?: InputMaybe<Scalars['String']['input']>;
  preferred?: InputMaybe<Scalars['String']['input']>;
  unknownReason?: InputMaybe<Scalars['String']['input']>;
};

export type UpdatePatientAddressInput = {
  address1?: InputMaybe<Scalars['String']['input']>;
  address2?: InputMaybe<Scalars['String']['input']>;
  asOf: Scalars['Date']['input'];
  censusTract?: InputMaybe<Scalars['String']['input']>;
  city?: InputMaybe<Scalars['String']['input']>;
  comment?: InputMaybe<Scalars['String']['input']>;
  country?: InputMaybe<Scalars['String']['input']>;
  county?: InputMaybe<Scalars['String']['input']>;
  id: Scalars['Int']['input'];
  patient: Scalars['Int']['input'];
  state?: InputMaybe<Scalars['String']['input']>;
  type: Scalars['String']['input'];
  use: Scalars['String']['input'];
  zipcode?: InputMaybe<Scalars['String']['input']>;
};

export type UpdatePatientIdentificationInput = {
  asOf: Scalars['Date']['input'];
  authority?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  sequence: Scalars['Int']['input'];
  type: Scalars['String']['input'];
  value: Scalars['String']['input'];
};

export type UpdatePatientNameInput = {
  asOf: Scalars['Date']['input'];
  degree?: InputMaybe<Scalars['String']['input']>;
  first?: InputMaybe<Scalars['String']['input']>;
  last?: InputMaybe<Scalars['String']['input']>;
  middle?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  prefix?: InputMaybe<Scalars['String']['input']>;
  secondLast?: InputMaybe<Scalars['String']['input']>;
  secondMiddle?: InputMaybe<Scalars['String']['input']>;
  sequence: Scalars['Int']['input'];
  suffix?: InputMaybe<Scalars['String']['input']>;
  type: Scalars['String']['input'];
};

export type UpdatePatientPhoneInput = {
  asOf: Scalars['Date']['input'];
  comment?: InputMaybe<Scalars['String']['input']>;
  countryCode?: InputMaybe<Scalars['String']['input']>;
  email?: InputMaybe<Scalars['String']['input']>;
  extension?: InputMaybe<Scalars['String']['input']>;
  id: Scalars['Int']['input'];
  number?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  type: Scalars['String']['input'];
  url?: InputMaybe<Scalars['String']['input']>;
  use: Scalars['String']['input'];
};

export enum UserType {
  External = 'EXTERNAL',
  Internal = 'INTERNAL'
}

export type AddPatientAddressMutationVariables = Exact<{
  input: NewPatientAddressInput;
}>;


export type AddPatientAddressMutation = { __typename?: 'Mutation', addPatientAddress: { __typename?: 'PatientAddressChangeResult', patient: number, id: number } };

export type AddPatientIdentificationMutationVariables = Exact<{
  input: NewPatientIdentificationInput;
}>;


export type AddPatientIdentificationMutation = { __typename?: 'Mutation', addPatientIdentification: { __typename?: 'PatientIdentificationChangeResult', patient: number, sequence: number } };

export type AddPatientNameMutationVariables = Exact<{
  input: NewPatientNameInput;
}>;


export type AddPatientNameMutation = { __typename?: 'Mutation', addPatientName: { __typename?: 'PatientNameChangeResult', patient: number, sequence: number } };

export type AddPatientPhoneMutationVariables = Exact<{
  input: NewPatientPhoneInput;
}>;


export type AddPatientPhoneMutation = { __typename?: 'Mutation', addPatientPhone: { __typename?: 'PatientPhoneChangeResult', patient: number, id: number } };

export type AddPatientRaceMutationVariables = Exact<{
  input: RaceInput;
}>;


export type AddPatientRaceMutation = { __typename?: 'Mutation', addPatientRace: { __typename: 'PatientRaceChangeFailureExistingCategory', patient: number, category: string } | { __typename: 'PatientRaceChangeSuccessful', patient: number } };

export type DeletePatientAddressMutationVariables = Exact<{
  input?: InputMaybe<DeletePatientAddressInput>;
}>;


export type DeletePatientAddressMutation = { __typename?: 'Mutation', deletePatientAddress: { __typename?: 'PatientAddressChangeResult', patient: number, id: number } };

export type DeletePatientIdentificationMutationVariables = Exact<{
  input?: InputMaybe<DeletePatientIdentificationInput>;
}>;


export type DeletePatientIdentificationMutation = { __typename?: 'Mutation', deletePatientIdentification: { __typename?: 'PatientIdentificationChangeResult', patient: number, sequence: number } };

export type DeletePatientNameMutationVariables = Exact<{
  input: DeletePatientNameInput;
}>;


export type DeletePatientNameMutation = { __typename?: 'Mutation', deletePatientName: { __typename?: 'PatientNameChangeResult', patient: number, sequence: number } };

export type DeletePatientPhoneMutationVariables = Exact<{
  input?: InputMaybe<DeletePatientPhoneInput>;
}>;


export type DeletePatientPhoneMutation = { __typename?: 'Mutation', deletePatientPhone: { __typename?: 'PatientPhoneChangeResult', patient: number, id: number } };

export type DeletePatientRaceMutationVariables = Exact<{
  input: DeletePatientRace;
}>;


export type DeletePatientRaceMutation = { __typename?: 'Mutation', deletePatientRace: { __typename?: 'PatientRaceChangeSuccessful', patient: number } };

export type UpdateEthnicityMutationVariables = Exact<{
  input: EthnicityInput;
}>;


export type UpdateEthnicityMutation = { __typename?: 'Mutation', updateEthnicity: { __typename?: 'PatientEthnicityChangeResult', patient: string } };

export type UpdatePatientAddressMutationVariables = Exact<{
  input: UpdatePatientAddressInput;
}>;


export type UpdatePatientAddressMutation = { __typename?: 'Mutation', updatePatientAddress: { __typename?: 'PatientAddressChangeResult', patient: number, id: number } };

export type UpdatePatientBirthAndGenderMutationVariables = Exact<{
  input: UpdateBirthAndGenderInput;
}>;


export type UpdatePatientBirthAndGenderMutation = { __typename?: 'Mutation', updatePatientBirthAndGender: { __typename?: 'PatientBirthAndGenderChangeResult', patient: number } };

export type UpdatePatientGeneralInfoMutationVariables = Exact<{
  input: GeneralInfoInput;
}>;


export type UpdatePatientGeneralInfoMutation = { __typename?: 'Mutation', updatePatientGeneralInfo: { __typename?: 'PatientGeneralChangeResult', patient: number } };

export type UpdatePatientIdentificationMutationVariables = Exact<{
  input: UpdatePatientIdentificationInput;
}>;


export type UpdatePatientIdentificationMutation = { __typename?: 'Mutation', updatePatientIdentification: { __typename?: 'PatientIdentificationChangeResult', patient: number, sequence: number } };

export type UpdatePatientMortalityMutationVariables = Exact<{
  input: MortalityInput;
}>;


export type UpdatePatientMortalityMutation = { __typename?: 'Mutation', updatePatientMortality: { __typename?: 'PatientMortalityChangeResult', patient: number } };

export type UpdatePatientNameMutationVariables = Exact<{
  input: UpdatePatientNameInput;
}>;


export type UpdatePatientNameMutation = { __typename?: 'Mutation', updatePatientName: { __typename?: 'PatientNameChangeResult', patient: number, sequence: number } };

export type UpdatePatientPhoneMutationVariables = Exact<{
  input: UpdatePatientPhoneInput;
}>;


export type UpdatePatientPhoneMutation = { __typename?: 'Mutation', updatePatientPhone: { __typename?: 'PatientPhoneChangeResult', patient: number, id: number } };

export type UpdatePatientRaceMutationVariables = Exact<{
  input: RaceInput;
}>;


export type UpdatePatientRaceMutation = { __typename?: 'Mutation', updatePatientRace: { __typename?: 'PatientRaceChangeSuccessful', patient: number } };

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

export type FindDistinctCodedResultsQueryVariables = Exact<{
  searchText: Scalars['String']['input'];
}>;


export type FindDistinctCodedResultsQuery = { __typename?: 'Query', findDistinctCodedResults: Array<{ __typename?: 'CodedResult', name: string }> };

export type FindDistinctResultedTestQueryVariables = Exact<{
  searchText: Scalars['String']['input'];
}>;


export type FindDistinctResultedTestQuery = { __typename?: 'Query', findDistinctResultedTest: Array<{ __typename?: 'ResultedTest', name: string }> };

export type FindDocumentsForPatientQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindDocumentsForPatientQuery = { __typename?: 'Query', findDocumentsForPatient?: { __typename?: 'PatientDocumentResults', total: number, number: number, content: Array<{ __typename?: 'PatientDocument', document: string, receivedOn: any, type: string, sendingFacility: string, reportedOn: any, condition?: string | null, event: string, associatedWith?: { __typename?: 'PatientDocumentInvestigation', id: string, local: string } | null } | null> } | null };

export type FindDocumentsRequiringReviewForPatientQueryVariables = Exact<{
  patient: Scalars['Int']['input'];
  page?: InputMaybe<DocumentRequiringReviewSortablePage>;
}>;


export type FindDocumentsRequiringReviewForPatientQuery = { __typename?: 'Query', findDocumentsRequiringReviewForPatient: { __typename?: 'PatientDocumentRequiringReviewResults', total: number, number: number, content: Array<{ __typename?: 'DocumentRequiringReview', id: string, localId: string, type: string, dateReceived: any, eventDate?: any | null, isElectronic: boolean, facilityProviders: { __typename?: 'FacilityProviders', reportingFacility?: { __typename?: 'ReportingFacility', name?: string | null } | null, orderingProvider?: { __typename?: 'OrderingProvider', name?: string | null } | null, sendingFacility?: { __typename?: 'SendingFacility', name?: string | null } | null }, descriptions: Array<{ __typename?: 'Description', title?: string | null, value?: string | null } | null> } | null> } };

export type FindInvestigationsByFilterQueryVariables = Exact<{
  filter: InvestigationFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindInvestigationsByFilterQuery = { __typename?: 'Query', findInvestigationsByFilter: { __typename?: 'InvestigationResults', total: number, page: number, size: number, content: Array<{ __typename?: 'Investigation', relevance: number, id?: string | null, cdDescTxt?: string | null, jurisdictionCodeDescTxt?: string | null, localId?: string | null, addTime?: any | null, startedOn?: any | null, investigationStatusCd?: string | null, notificationRecordStatusCd?: string | null, investigatorLastName?: string | null, personParticipations: Array<{ __typename?: 'InvestigationPersonParticipation', birthTime?: any | null, currSexCd?: string | null, typeCd: string, firstName?: string | null, lastName?: string | null, personCd: string, personParentUid?: number | null, shortId?: number | null }> }> } };

export type FindInvestigationsForPatientQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
  openOnly?: InputMaybe<Scalars['Boolean']['input']>;
}>;


export type FindInvestigationsForPatientQuery = { __typename?: 'Query', findInvestigationsForPatient?: { __typename?: 'PatientInvestigationResults', total: number, number: number, content: Array<{ __typename?: 'PatientInvestigation', investigation: string, startedOn?: any | null, condition: string, status: string, caseStatus?: string | null, jurisdiction: string, event: string, coInfection?: string | null, notification?: string | null, investigator?: string | null, comparable: boolean } | null> } | null };

export type FindLabReportsByFilterQueryVariables = Exact<{
  filter: LabReportFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindLabReportsByFilterQuery = { __typename?: 'Query', findLabReportsByFilter: { __typename?: 'LabReportResults', total: number, page: number, size: number, content: Array<{ __typename?: 'LabReport', relevance: number, id: string, jurisdictionCd: number, localId: string, addTime: any, personParticipations: Array<{ __typename?: 'LabReportPersonParticipation', birthTime?: any | null, currSexCd?: string | null, typeCd?: string | null, firstName?: string | null, lastName?: string | null, personCd: string, personParentUid?: number | null, shortId?: number | null }>, organizationParticipations: Array<{ __typename?: 'LabReportOrganizationParticipation', typeCd: string, name: string }>, observations: Array<{ __typename?: 'Observation', cdDescTxt?: string | null, statusCd?: string | null, altCd?: string | null, displayName?: string | null }>, associatedInvestigations: Array<{ __typename?: 'AssociatedInvestigation', cdDescTxt: string, localId: string }>, tests: Array<{ __typename?: 'LabTestSummary', name?: string | null, status?: string | null, coded?: string | null, numeric?: number | null, high?: string | null, low?: string | null, unit?: string | null }> }> } };

export type FindLabReportsForPatientQueryVariables = Exact<{
  personUid: Scalars['Int']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindLabReportsForPatientQuery = { __typename?: 'Query', findLabReportsForPatient?: { __typename?: 'PatientLabReportResults', total: number, number: number, content: Array<{ __typename?: 'PatientLabReport', id: string, observationUid: number, addTime: any, effectiveFromTime?: any | null, programAreaCd: string, jurisdictionCodeDescTxt: string, localId: string, electronicInd?: string | null, associatedInvestigations: Array<{ __typename?: 'AssociatedInvestigation2', publicHealthCaseUid: number, cdDescTxt: string, localId: string }>, personParticipations: Array<{ __typename?: 'PersonParticipation2', typeCd: string, personCd: string, firstName?: string | null, lastName?: string | null }>, organizationParticipations: Array<{ __typename?: 'OrganizationParticipation2', typeCd?: string | null, name?: string | null }>, observations: Array<{ __typename?: 'Observation2', domainCd: string, cdDescTxt: string, displayName?: string | null }> } | null> } | null };

export type FindMorbidityReportsForPatientQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindMorbidityReportsForPatientQuery = { __typename?: 'Query', findMorbidityReportsForPatient?: { __typename?: 'PatientMorbidityResults', total: number, number: number, content: Array<{ __typename?: 'PatientMorbidity', morbidity: string, receivedOn: any, provider?: string | null, reportedOn: any, condition: string, jurisdiction: string, event: string, treatments: Array<string | null>, associatedWith?: { __typename?: 'PatientMorbidityInvestigation', id: string, local: string, condition: string } | null, labResults: Array<{ __typename?: 'PatientMorbidityLabResult', labTest: string, status?: string | null, codedResult?: string | null, numericResult?: string | null, textResult?: string | null } | null> } | null> } | null };

export type FindPatientNamedByContactQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindPatientNamedByContactQuery = { __typename?: 'Query', findPatientNamedByContact?: { __typename?: 'PatientNamedByContactResults', total: number, number: number, content: Array<{ __typename?: 'NamedByPatient', contactRecord: string, createdOn: any, namedOn: any, priority?: string | null, disposition?: string | null, event: string, condition: { __typename?: 'TracedCondition', id?: string | null, description?: string | null }, contact: { __typename?: 'NamedContact', id: string, name: string }, associatedWith?: { __typename?: 'PatientContactInvestigation', id: string, local: string, condition: string } | null } | null> } | null };

export type FindPatientProfileQueryVariables = Exact<{
  asOf?: InputMaybe<Scalars['Date']['input']>;
  page?: InputMaybe<Page>;
  page1?: InputMaybe<Page>;
  page2?: InputMaybe<Page>;
  page3?: InputMaybe<Page>;
  page4?: InputMaybe<Page>;
  patient?: InputMaybe<Scalars['ID']['input']>;
  shortId?: InputMaybe<Scalars['Int']['input']>;
}>;


export type FindPatientProfileQuery = { __typename?: 'Query', findPatientProfile?: { __typename?: 'PatientProfile', id: string, local: string, shortId: number, version: number, status: string, deletable: boolean, summary?: { __typename?: 'PatientSummary', birthday?: any | null, age?: number | null, gender?: string | null, ethnicity?: string | null, races?: Array<string> | null, legalName?: { __typename?: 'PatientLegalName', prefix?: string | null, first?: string | null, middle?: string | null, last?: string | null, suffix?: string | null } | null, home?: { __typename?: 'PatientSummaryAddress', use: string, address?: string | null, address2?: string | null, city?: string | null, state?: string | null, zipcode?: string | null, country?: string | null } | null, address: Array<{ __typename?: 'PatientSummaryAddress', use: string, address?: string | null, address2?: string | null, city?: string | null, state?: string | null, zipcode?: string | null, country?: string | null }>, identification?: Array<{ __typename?: 'PatientSummaryIdentification', type: string, value: string }> | null, phone?: Array<{ __typename?: 'PatientSummaryPhone', use?: string | null, number?: string | null }> | null, email?: Array<{ __typename?: 'PatientSummaryEmail', use?: string | null, address?: string | null }> | null } | null, names: { __typename?: 'PatientNameResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientName', patient: number, version: number, asOf: any, sequence: number, first?: string | null, middle?: string | null, secondMiddle?: string | null, last?: string | null, secondLast?: string | null, use: { __typename?: 'PatientNameUse', id: string, description: string }, prefix?: { __typename?: 'PatientNamePrefix', id: string, description: string } | null, suffix?: { __typename?: 'PatientNameSuffix', id: string, description: string } | null, degree?: { __typename?: 'PatientNameDegree', id: string, description: string } | null }> }, addresses: { __typename?: 'PatientAddressResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientAddress', patient: number, id: string, version: number, asOf: any, address1?: string | null, address2?: string | null, city?: string | null, zipcode?: string | null, censusTract?: string | null, comment?: string | null, type: { __typename?: 'PatientAddressType', id: string, description: string }, county?: { __typename?: 'PatientCounty', id: string, description: string } | null, state?: { __typename?: 'PatientState', id: string, description: string } | null, country?: { __typename?: 'PatientCountry', id: string, description: string } | null }> }, phones: { __typename?: 'PatientPhoneResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientPhone', patient: number, id: string, version: number, asOf: any, countryCode?: string | null, number?: string | null, extension?: string | null, email?: string | null, url?: string | null, comment?: string | null }> }, identification: { __typename?: 'PatientIdentificationResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientIdentification', patient: number, sequence: number, version: number, asOf: any, value?: string | null, authority?: { __typename?: 'PatientIdentificationAuthority', id: string, description: string } | null }> }, races: { __typename?: 'PatientRaceResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientRace', patient: number, id: number, version: number, asOf: any, category: { __typename?: 'PatientRaceCategory', id: string, description: string }, detailed: Array<{ __typename?: 'PatientRaceDetail', id: string, description: string }> }> }, birth?: { __typename?: 'PatientBirth', patient: number, id: string, version: number, asOf: any, bornOn?: any | null, age?: number | null, birthOrder?: number | null, city?: string | null, multipleBirth?: { __typename?: 'PatientIndicatorCodedValue', id: string, description: string } | null, state?: { __typename?: 'PatientState', id: string, description: string } | null, county?: { __typename?: 'PatientCounty', id: string, description: string } | null, country?: { __typename?: 'PatientCountry', id: string, description: string } | null } | null, gender?: { __typename?: 'PatientGender', patient: number, id: string, version: number, asOf: any, additional?: string | null, birth?: { __typename?: 'PatientGenderCodedValue', id: string, description: string } | null, current?: { __typename?: 'PatientGenderCodedValue', id: string, description: string } | null, unknownReason?: { __typename?: 'PatientGenderUnknownReason', id: string, description: string } | null, preferred?: { __typename?: 'PatientPreferredGender', id: string, description: string } | null } | null, mortality?: { __typename?: 'PatientMortality', patient: number, id: string, version: number, asOf: any, deceasedOn?: any | null, city?: string | null, deceased?: { __typename?: 'PatientIndicatorCodedValue', id: string, description: string } | null, state?: { __typename?: 'PatientState', id: string, description: string } | null, county?: { __typename?: 'PatientCounty', id: string, description: string } | null, country?: { __typename?: 'PatientCountry', id: string, description: string } | null } | null, general?: { __typename?: 'PatientGeneral', patient: number, id: string, version: number, asOf: any, maternalMaidenName?: string | null, adultsInHouse?: number | null, childrenInHouse?: number | null, maritalStatus?: { __typename?: 'PatientMaritalStatus', id: string, description: string } | null, occupation?: { __typename?: 'PatientOccupation', id: string, description: string } | null, educationLevel?: { __typename?: 'PatientEducationLevel', id: string, description: string } | null, primaryLanguage?: { __typename?: 'PatientPrimaryLanguage', id: string, description: string } | null, speaksEnglish?: { __typename?: 'PatientIndicatorCodedValue', id: string, description: string } | null, stateHIVCase?: { __typename: 'Allowed', value?: string | null } | { __typename: 'Restricted', reason: string } | null } | null, ethnicity?: { __typename?: 'PatientEthnicity', patient: number, id: string, version: number, asOf: any, ethnicGroup: { __typename?: 'PatientEthnicGroup', id: string, description: string }, unknownReason?: { __typename?: 'PatientEthnicityUnknownReason', id: string, description: string } | null, detailed: Array<{ __typename?: 'PatientDetailedEthnicity', id: string, description: string }> } | null } | null };

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

export type FindVaccinationsForPatientQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindVaccinationsForPatientQuery = { __typename?: 'Query', findVaccinationsForPatient?: { __typename?: 'PatientVaccinationResults', total: number, number: number, content: Array<{ __typename?: 'PatientVaccination', vaccination: string, createdOn: any, provider?: string | null, administeredOn?: any | null, administered: string, event: string, associatedWith?: { __typename?: 'PatientVaccinationInvestigation', id: string, local: string, condition: string } | null } | null> } | null };


export const AddPatientAddressDocument = gql`
    mutation addPatientAddress($input: NewPatientAddressInput!) {
  addPatientAddress(input: $input) {
    patient
    id
  }
}
    `;
export type AddPatientAddressMutationFn = Apollo.MutationFunction<AddPatientAddressMutation, AddPatientAddressMutationVariables>;

/**
 * __useAddPatientAddressMutation__
 *
 * To run a mutation, you first call `useAddPatientAddressMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useAddPatientAddressMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [addPatientAddressMutation, { data, loading, error }] = useAddPatientAddressMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useAddPatientAddressMutation(baseOptions?: Apollo.MutationHookOptions<AddPatientAddressMutation, AddPatientAddressMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<AddPatientAddressMutation, AddPatientAddressMutationVariables>(AddPatientAddressDocument, options);
      }
export type AddPatientAddressMutationHookResult = ReturnType<typeof useAddPatientAddressMutation>;
export type AddPatientAddressMutationResult = Apollo.MutationResult<AddPatientAddressMutation>;
export type AddPatientAddressMutationOptions = Apollo.BaseMutationOptions<AddPatientAddressMutation, AddPatientAddressMutationVariables>;
export const AddPatientIdentificationDocument = gql`
    mutation addPatientIdentification($input: NewPatientIdentificationInput!) {
  addPatientIdentification(input: $input) {
    patient
    sequence
  }
}
    `;
export type AddPatientIdentificationMutationFn = Apollo.MutationFunction<AddPatientIdentificationMutation, AddPatientIdentificationMutationVariables>;

/**
 * __useAddPatientIdentificationMutation__
 *
 * To run a mutation, you first call `useAddPatientIdentificationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useAddPatientIdentificationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [addPatientIdentificationMutation, { data, loading, error }] = useAddPatientIdentificationMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useAddPatientIdentificationMutation(baseOptions?: Apollo.MutationHookOptions<AddPatientIdentificationMutation, AddPatientIdentificationMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<AddPatientIdentificationMutation, AddPatientIdentificationMutationVariables>(AddPatientIdentificationDocument, options);
      }
export type AddPatientIdentificationMutationHookResult = ReturnType<typeof useAddPatientIdentificationMutation>;
export type AddPatientIdentificationMutationResult = Apollo.MutationResult<AddPatientIdentificationMutation>;
export type AddPatientIdentificationMutationOptions = Apollo.BaseMutationOptions<AddPatientIdentificationMutation, AddPatientIdentificationMutationVariables>;
export const AddPatientNameDocument = gql`
    mutation addPatientName($input: NewPatientNameInput!) {
  addPatientName(input: $input) {
    patient
    sequence
  }
}
    `;
export type AddPatientNameMutationFn = Apollo.MutationFunction<AddPatientNameMutation, AddPatientNameMutationVariables>;

/**
 * __useAddPatientNameMutation__
 *
 * To run a mutation, you first call `useAddPatientNameMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useAddPatientNameMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [addPatientNameMutation, { data, loading, error }] = useAddPatientNameMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useAddPatientNameMutation(baseOptions?: Apollo.MutationHookOptions<AddPatientNameMutation, AddPatientNameMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<AddPatientNameMutation, AddPatientNameMutationVariables>(AddPatientNameDocument, options);
      }
export type AddPatientNameMutationHookResult = ReturnType<typeof useAddPatientNameMutation>;
export type AddPatientNameMutationResult = Apollo.MutationResult<AddPatientNameMutation>;
export type AddPatientNameMutationOptions = Apollo.BaseMutationOptions<AddPatientNameMutation, AddPatientNameMutationVariables>;
export const AddPatientPhoneDocument = gql`
    mutation addPatientPhone($input: NewPatientPhoneInput!) {
  addPatientPhone(input: $input) {
    patient
    id
  }
}
    `;
export type AddPatientPhoneMutationFn = Apollo.MutationFunction<AddPatientPhoneMutation, AddPatientPhoneMutationVariables>;

/**
 * __useAddPatientPhoneMutation__
 *
 * To run a mutation, you first call `useAddPatientPhoneMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useAddPatientPhoneMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [addPatientPhoneMutation, { data, loading, error }] = useAddPatientPhoneMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useAddPatientPhoneMutation(baseOptions?: Apollo.MutationHookOptions<AddPatientPhoneMutation, AddPatientPhoneMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<AddPatientPhoneMutation, AddPatientPhoneMutationVariables>(AddPatientPhoneDocument, options);
      }
export type AddPatientPhoneMutationHookResult = ReturnType<typeof useAddPatientPhoneMutation>;
export type AddPatientPhoneMutationResult = Apollo.MutationResult<AddPatientPhoneMutation>;
export type AddPatientPhoneMutationOptions = Apollo.BaseMutationOptions<AddPatientPhoneMutation, AddPatientPhoneMutationVariables>;
export const AddPatientRaceDocument = gql`
    mutation addPatientRace($input: RaceInput!) {
  addPatientRace(input: $input) {
    __typename
    ... on PatientRaceChangeSuccessful {
      patient
    }
    ... on PatientRaceChangeFailureExistingCategory {
      patient
      category
    }
  }
}
    `;
export type AddPatientRaceMutationFn = Apollo.MutationFunction<AddPatientRaceMutation, AddPatientRaceMutationVariables>;

/**
 * __useAddPatientRaceMutation__
 *
 * To run a mutation, you first call `useAddPatientRaceMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useAddPatientRaceMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [addPatientRaceMutation, { data, loading, error }] = useAddPatientRaceMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useAddPatientRaceMutation(baseOptions?: Apollo.MutationHookOptions<AddPatientRaceMutation, AddPatientRaceMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<AddPatientRaceMutation, AddPatientRaceMutationVariables>(AddPatientRaceDocument, options);
      }
export type AddPatientRaceMutationHookResult = ReturnType<typeof useAddPatientRaceMutation>;
export type AddPatientRaceMutationResult = Apollo.MutationResult<AddPatientRaceMutation>;
export type AddPatientRaceMutationOptions = Apollo.BaseMutationOptions<AddPatientRaceMutation, AddPatientRaceMutationVariables>;
export const DeletePatientAddressDocument = gql`
    mutation deletePatientAddress($input: DeletePatientAddressInput) {
  deletePatientAddress(input: $input) {
    patient
    id
  }
}
    `;
export type DeletePatientAddressMutationFn = Apollo.MutationFunction<DeletePatientAddressMutation, DeletePatientAddressMutationVariables>;

/**
 * __useDeletePatientAddressMutation__
 *
 * To run a mutation, you first call `useDeletePatientAddressMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeletePatientAddressMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deletePatientAddressMutation, { data, loading, error }] = useDeletePatientAddressMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useDeletePatientAddressMutation(baseOptions?: Apollo.MutationHookOptions<DeletePatientAddressMutation, DeletePatientAddressMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<DeletePatientAddressMutation, DeletePatientAddressMutationVariables>(DeletePatientAddressDocument, options);
      }
export type DeletePatientAddressMutationHookResult = ReturnType<typeof useDeletePatientAddressMutation>;
export type DeletePatientAddressMutationResult = Apollo.MutationResult<DeletePatientAddressMutation>;
export type DeletePatientAddressMutationOptions = Apollo.BaseMutationOptions<DeletePatientAddressMutation, DeletePatientAddressMutationVariables>;
export const DeletePatientIdentificationDocument = gql`
    mutation deletePatientIdentification($input: DeletePatientIdentificationInput) {
  deletePatientIdentification(input: $input) {
    patient
    sequence
  }
}
    `;
export type DeletePatientIdentificationMutationFn = Apollo.MutationFunction<DeletePatientIdentificationMutation, DeletePatientIdentificationMutationVariables>;

/**
 * __useDeletePatientIdentificationMutation__
 *
 * To run a mutation, you first call `useDeletePatientIdentificationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeletePatientIdentificationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deletePatientIdentificationMutation, { data, loading, error }] = useDeletePatientIdentificationMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useDeletePatientIdentificationMutation(baseOptions?: Apollo.MutationHookOptions<DeletePatientIdentificationMutation, DeletePatientIdentificationMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<DeletePatientIdentificationMutation, DeletePatientIdentificationMutationVariables>(DeletePatientIdentificationDocument, options);
      }
export type DeletePatientIdentificationMutationHookResult = ReturnType<typeof useDeletePatientIdentificationMutation>;
export type DeletePatientIdentificationMutationResult = Apollo.MutationResult<DeletePatientIdentificationMutation>;
export type DeletePatientIdentificationMutationOptions = Apollo.BaseMutationOptions<DeletePatientIdentificationMutation, DeletePatientIdentificationMutationVariables>;
export const DeletePatientNameDocument = gql`
    mutation deletePatientName($input: DeletePatientNameInput!) {
  deletePatientName(input: $input) {
    patient
    sequence
  }
}
    `;
export type DeletePatientNameMutationFn = Apollo.MutationFunction<DeletePatientNameMutation, DeletePatientNameMutationVariables>;

/**
 * __useDeletePatientNameMutation__
 *
 * To run a mutation, you first call `useDeletePatientNameMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeletePatientNameMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deletePatientNameMutation, { data, loading, error }] = useDeletePatientNameMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useDeletePatientNameMutation(baseOptions?: Apollo.MutationHookOptions<DeletePatientNameMutation, DeletePatientNameMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<DeletePatientNameMutation, DeletePatientNameMutationVariables>(DeletePatientNameDocument, options);
      }
export type DeletePatientNameMutationHookResult = ReturnType<typeof useDeletePatientNameMutation>;
export type DeletePatientNameMutationResult = Apollo.MutationResult<DeletePatientNameMutation>;
export type DeletePatientNameMutationOptions = Apollo.BaseMutationOptions<DeletePatientNameMutation, DeletePatientNameMutationVariables>;
export const DeletePatientPhoneDocument = gql`
    mutation deletePatientPhone($input: DeletePatientPhoneInput) {
  deletePatientPhone(input: $input) {
    patient
    id
  }
}
    `;
export type DeletePatientPhoneMutationFn = Apollo.MutationFunction<DeletePatientPhoneMutation, DeletePatientPhoneMutationVariables>;

/**
 * __useDeletePatientPhoneMutation__
 *
 * To run a mutation, you first call `useDeletePatientPhoneMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeletePatientPhoneMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deletePatientPhoneMutation, { data, loading, error }] = useDeletePatientPhoneMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useDeletePatientPhoneMutation(baseOptions?: Apollo.MutationHookOptions<DeletePatientPhoneMutation, DeletePatientPhoneMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<DeletePatientPhoneMutation, DeletePatientPhoneMutationVariables>(DeletePatientPhoneDocument, options);
      }
export type DeletePatientPhoneMutationHookResult = ReturnType<typeof useDeletePatientPhoneMutation>;
export type DeletePatientPhoneMutationResult = Apollo.MutationResult<DeletePatientPhoneMutation>;
export type DeletePatientPhoneMutationOptions = Apollo.BaseMutationOptions<DeletePatientPhoneMutation, DeletePatientPhoneMutationVariables>;
export const DeletePatientRaceDocument = gql`
    mutation deletePatientRace($input: DeletePatientRace!) {
  deletePatientRace(input: $input) {
    patient
  }
}
    `;
export type DeletePatientRaceMutationFn = Apollo.MutationFunction<DeletePatientRaceMutation, DeletePatientRaceMutationVariables>;

/**
 * __useDeletePatientRaceMutation__
 *
 * To run a mutation, you first call `useDeletePatientRaceMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeletePatientRaceMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deletePatientRaceMutation, { data, loading, error }] = useDeletePatientRaceMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useDeletePatientRaceMutation(baseOptions?: Apollo.MutationHookOptions<DeletePatientRaceMutation, DeletePatientRaceMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<DeletePatientRaceMutation, DeletePatientRaceMutationVariables>(DeletePatientRaceDocument, options);
      }
export type DeletePatientRaceMutationHookResult = ReturnType<typeof useDeletePatientRaceMutation>;
export type DeletePatientRaceMutationResult = Apollo.MutationResult<DeletePatientRaceMutation>;
export type DeletePatientRaceMutationOptions = Apollo.BaseMutationOptions<DeletePatientRaceMutation, DeletePatientRaceMutationVariables>;
export const UpdateEthnicityDocument = gql`
    mutation updateEthnicity($input: EthnicityInput!) {
  updateEthnicity(input: $input) {
    patient
  }
}
    `;
export type UpdateEthnicityMutationFn = Apollo.MutationFunction<UpdateEthnicityMutation, UpdateEthnicityMutationVariables>;

/**
 * __useUpdateEthnicityMutation__
 *
 * To run a mutation, you first call `useUpdateEthnicityMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdateEthnicityMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updateEthnicityMutation, { data, loading, error }] = useUpdateEthnicityMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdateEthnicityMutation(baseOptions?: Apollo.MutationHookOptions<UpdateEthnicityMutation, UpdateEthnicityMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdateEthnicityMutation, UpdateEthnicityMutationVariables>(UpdateEthnicityDocument, options);
      }
export type UpdateEthnicityMutationHookResult = ReturnType<typeof useUpdateEthnicityMutation>;
export type UpdateEthnicityMutationResult = Apollo.MutationResult<UpdateEthnicityMutation>;
export type UpdateEthnicityMutationOptions = Apollo.BaseMutationOptions<UpdateEthnicityMutation, UpdateEthnicityMutationVariables>;
export const UpdatePatientAddressDocument = gql`
    mutation updatePatientAddress($input: UpdatePatientAddressInput!) {
  updatePatientAddress(input: $input) {
    patient
    id
  }
}
    `;
export type UpdatePatientAddressMutationFn = Apollo.MutationFunction<UpdatePatientAddressMutation, UpdatePatientAddressMutationVariables>;

/**
 * __useUpdatePatientAddressMutation__
 *
 * To run a mutation, you first call `useUpdatePatientAddressMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientAddressMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientAddressMutation, { data, loading, error }] = useUpdatePatientAddressMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientAddressMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientAddressMutation, UpdatePatientAddressMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientAddressMutation, UpdatePatientAddressMutationVariables>(UpdatePatientAddressDocument, options);
      }
export type UpdatePatientAddressMutationHookResult = ReturnType<typeof useUpdatePatientAddressMutation>;
export type UpdatePatientAddressMutationResult = Apollo.MutationResult<UpdatePatientAddressMutation>;
export type UpdatePatientAddressMutationOptions = Apollo.BaseMutationOptions<UpdatePatientAddressMutation, UpdatePatientAddressMutationVariables>;
export const UpdatePatientBirthAndGenderDocument = gql`
    mutation updatePatientBirthAndGender($input: UpdateBirthAndGenderInput!) {
  updatePatientBirthAndGender(input: $input) {
    patient
  }
}
    `;
export type UpdatePatientBirthAndGenderMutationFn = Apollo.MutationFunction<UpdatePatientBirthAndGenderMutation, UpdatePatientBirthAndGenderMutationVariables>;

/**
 * __useUpdatePatientBirthAndGenderMutation__
 *
 * To run a mutation, you first call `useUpdatePatientBirthAndGenderMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientBirthAndGenderMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientBirthAndGenderMutation, { data, loading, error }] = useUpdatePatientBirthAndGenderMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientBirthAndGenderMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientBirthAndGenderMutation, UpdatePatientBirthAndGenderMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientBirthAndGenderMutation, UpdatePatientBirthAndGenderMutationVariables>(UpdatePatientBirthAndGenderDocument, options);
      }
export type UpdatePatientBirthAndGenderMutationHookResult = ReturnType<typeof useUpdatePatientBirthAndGenderMutation>;
export type UpdatePatientBirthAndGenderMutationResult = Apollo.MutationResult<UpdatePatientBirthAndGenderMutation>;
export type UpdatePatientBirthAndGenderMutationOptions = Apollo.BaseMutationOptions<UpdatePatientBirthAndGenderMutation, UpdatePatientBirthAndGenderMutationVariables>;
export const UpdatePatientGeneralInfoDocument = gql`
    mutation updatePatientGeneralInfo($input: GeneralInfoInput!) {
  updatePatientGeneralInfo(input: $input) {
    patient
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
export const UpdatePatientIdentificationDocument = gql`
    mutation updatePatientIdentification($input: UpdatePatientIdentificationInput!) {
  updatePatientIdentification(input: $input) {
    patient
    sequence
  }
}
    `;
export type UpdatePatientIdentificationMutationFn = Apollo.MutationFunction<UpdatePatientIdentificationMutation, UpdatePatientIdentificationMutationVariables>;

/**
 * __useUpdatePatientIdentificationMutation__
 *
 * To run a mutation, you first call `useUpdatePatientIdentificationMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientIdentificationMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientIdentificationMutation, { data, loading, error }] = useUpdatePatientIdentificationMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientIdentificationMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientIdentificationMutation, UpdatePatientIdentificationMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientIdentificationMutation, UpdatePatientIdentificationMutationVariables>(UpdatePatientIdentificationDocument, options);
      }
export type UpdatePatientIdentificationMutationHookResult = ReturnType<typeof useUpdatePatientIdentificationMutation>;
export type UpdatePatientIdentificationMutationResult = Apollo.MutationResult<UpdatePatientIdentificationMutation>;
export type UpdatePatientIdentificationMutationOptions = Apollo.BaseMutationOptions<UpdatePatientIdentificationMutation, UpdatePatientIdentificationMutationVariables>;
export const UpdatePatientMortalityDocument = gql`
    mutation updatePatientMortality($input: MortalityInput!) {
  updatePatientMortality(input: $input) {
    patient
  }
}
    `;
export type UpdatePatientMortalityMutationFn = Apollo.MutationFunction<UpdatePatientMortalityMutation, UpdatePatientMortalityMutationVariables>;

/**
 * __useUpdatePatientMortalityMutation__
 *
 * To run a mutation, you first call `useUpdatePatientMortalityMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientMortalityMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientMortalityMutation, { data, loading, error }] = useUpdatePatientMortalityMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientMortalityMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientMortalityMutation, UpdatePatientMortalityMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientMortalityMutation, UpdatePatientMortalityMutationVariables>(UpdatePatientMortalityDocument, options);
      }
export type UpdatePatientMortalityMutationHookResult = ReturnType<typeof useUpdatePatientMortalityMutation>;
export type UpdatePatientMortalityMutationResult = Apollo.MutationResult<UpdatePatientMortalityMutation>;
export type UpdatePatientMortalityMutationOptions = Apollo.BaseMutationOptions<UpdatePatientMortalityMutation, UpdatePatientMortalityMutationVariables>;
export const UpdatePatientNameDocument = gql`
    mutation updatePatientName($input: UpdatePatientNameInput!) {
  updatePatientName(input: $input) {
    patient
    sequence
  }
}
    `;
export type UpdatePatientNameMutationFn = Apollo.MutationFunction<UpdatePatientNameMutation, UpdatePatientNameMutationVariables>;

/**
 * __useUpdatePatientNameMutation__
 *
 * To run a mutation, you first call `useUpdatePatientNameMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientNameMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientNameMutation, { data, loading, error }] = useUpdatePatientNameMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientNameMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientNameMutation, UpdatePatientNameMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientNameMutation, UpdatePatientNameMutationVariables>(UpdatePatientNameDocument, options);
      }
export type UpdatePatientNameMutationHookResult = ReturnType<typeof useUpdatePatientNameMutation>;
export type UpdatePatientNameMutationResult = Apollo.MutationResult<UpdatePatientNameMutation>;
export type UpdatePatientNameMutationOptions = Apollo.BaseMutationOptions<UpdatePatientNameMutation, UpdatePatientNameMutationVariables>;
export const UpdatePatientPhoneDocument = gql`
    mutation updatePatientPhone($input: UpdatePatientPhoneInput!) {
  updatePatientPhone(input: $input) {
    patient
    id
  }
}
    `;
export type UpdatePatientPhoneMutationFn = Apollo.MutationFunction<UpdatePatientPhoneMutation, UpdatePatientPhoneMutationVariables>;

/**
 * __useUpdatePatientPhoneMutation__
 *
 * To run a mutation, you first call `useUpdatePatientPhoneMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientPhoneMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientPhoneMutation, { data, loading, error }] = useUpdatePatientPhoneMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientPhoneMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientPhoneMutation, UpdatePatientPhoneMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientPhoneMutation, UpdatePatientPhoneMutationVariables>(UpdatePatientPhoneDocument, options);
      }
export type UpdatePatientPhoneMutationHookResult = ReturnType<typeof useUpdatePatientPhoneMutation>;
export type UpdatePatientPhoneMutationResult = Apollo.MutationResult<UpdatePatientPhoneMutation>;
export type UpdatePatientPhoneMutationOptions = Apollo.BaseMutationOptions<UpdatePatientPhoneMutation, UpdatePatientPhoneMutationVariables>;
export const UpdatePatientRaceDocument = gql`
    mutation updatePatientRace($input: RaceInput!) {
  updatePatientRace(input: $input) {
    patient
  }
}
    `;
export type UpdatePatientRaceMutationFn = Apollo.MutationFunction<UpdatePatientRaceMutation, UpdatePatientRaceMutationVariables>;

/**
 * __useUpdatePatientRaceMutation__
 *
 * To run a mutation, you first call `useUpdatePatientRaceMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientRaceMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientRaceMutation, { data, loading, error }] = useUpdatePatientRaceMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientRaceMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientRaceMutation, UpdatePatientRaceMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientRaceMutation, UpdatePatientRaceMutationVariables>(UpdatePatientRaceDocument, options);
      }
export type UpdatePatientRaceMutationHookResult = ReturnType<typeof useUpdatePatientRaceMutation>;
export type UpdatePatientRaceMutationResult = Apollo.MutationResult<UpdatePatientRaceMutation>;
export type UpdatePatientRaceMutationOptions = Apollo.BaseMutationOptions<UpdatePatientRaceMutation, UpdatePatientRaceMutationVariables>;
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
export const FindDistinctCodedResultsDocument = gql`
    query findDistinctCodedResults($searchText: String!) {
  findDistinctCodedResults(searchText: $searchText) {
    name
  }
}
    `;

/**
 * __useFindDistinctCodedResultsQuery__
 *
 * To run a query within a React component, call `useFindDistinctCodedResultsQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindDistinctCodedResultsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindDistinctCodedResultsQuery({
 *   variables: {
 *      searchText: // value for 'searchText'
 *   },
 * });
 */
export function useFindDistinctCodedResultsQuery(baseOptions: Apollo.QueryHookOptions<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables> & ({ variables: FindDistinctCodedResultsQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>(FindDistinctCodedResultsDocument, options);
      }
export function useFindDistinctCodedResultsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>(FindDistinctCodedResultsDocument, options);
        }
export function useFindDistinctCodedResultsSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>(FindDistinctCodedResultsDocument, options);
        }
export type FindDistinctCodedResultsQueryHookResult = ReturnType<typeof useFindDistinctCodedResultsQuery>;
export type FindDistinctCodedResultsLazyQueryHookResult = ReturnType<typeof useFindDistinctCodedResultsLazyQuery>;
export type FindDistinctCodedResultsSuspenseQueryHookResult = ReturnType<typeof useFindDistinctCodedResultsSuspenseQuery>;
export type FindDistinctCodedResultsQueryResult = Apollo.QueryResult<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>;
export const FindDistinctResultedTestDocument = gql`
    query findDistinctResultedTest($searchText: String!) {
  findDistinctResultedTest(searchText: $searchText) {
    name
  }
}
    `;

/**
 * __useFindDistinctResultedTestQuery__
 *
 * To run a query within a React component, call `useFindDistinctResultedTestQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindDistinctResultedTestQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindDistinctResultedTestQuery({
 *   variables: {
 *      searchText: // value for 'searchText'
 *   },
 * });
 */
export function useFindDistinctResultedTestQuery(baseOptions: Apollo.QueryHookOptions<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables> & ({ variables: FindDistinctResultedTestQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>(FindDistinctResultedTestDocument, options);
      }
export function useFindDistinctResultedTestLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>(FindDistinctResultedTestDocument, options);
        }
export function useFindDistinctResultedTestSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>(FindDistinctResultedTestDocument, options);
        }
export type FindDistinctResultedTestQueryHookResult = ReturnType<typeof useFindDistinctResultedTestQuery>;
export type FindDistinctResultedTestLazyQueryHookResult = ReturnType<typeof useFindDistinctResultedTestLazyQuery>;
export type FindDistinctResultedTestSuspenseQueryHookResult = ReturnType<typeof useFindDistinctResultedTestSuspenseQuery>;
export type FindDistinctResultedTestQueryResult = Apollo.QueryResult<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>;
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
export const FindDocumentsRequiringReviewForPatientDocument = gql`
    query findDocumentsRequiringReviewForPatient($patient: Int!, $page: DocumentRequiringReviewSortablePage) {
  findDocumentsRequiringReviewForPatient(patient: $patient, page: $page) {
    content {
      id
      localId
      type
      dateReceived
      eventDate
      isElectronic
      facilityProviders {
        reportingFacility {
          name
        }
        orderingProvider {
          name
        }
        sendingFacility {
          name
        }
      }
      descriptions {
        title
        value
      }
    }
    total
    number
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
 *      patient: // value for 'patient'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindDocumentsRequiringReviewForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables> & ({ variables: FindDocumentsRequiringReviewForPatientQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>(FindDocumentsRequiringReviewForPatientDocument, options);
      }
export function useFindDocumentsRequiringReviewForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>(FindDocumentsRequiringReviewForPatientDocument, options);
        }
export function useFindDocumentsRequiringReviewForPatientSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>(FindDocumentsRequiringReviewForPatientDocument, options);
        }
export type FindDocumentsRequiringReviewForPatientQueryHookResult = ReturnType<typeof useFindDocumentsRequiringReviewForPatientQuery>;
export type FindDocumentsRequiringReviewForPatientLazyQueryHookResult = ReturnType<typeof useFindDocumentsRequiringReviewForPatientLazyQuery>;
export type FindDocumentsRequiringReviewForPatientSuspenseQueryHookResult = ReturnType<typeof useFindDocumentsRequiringReviewForPatientSuspenseQuery>;
export type FindDocumentsRequiringReviewForPatientQueryResult = Apollo.QueryResult<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>;
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
export const FindInvestigationsForPatientDocument = gql`
    query findInvestigationsForPatient($patient: ID!, $page: Page, $openOnly: Boolean) {
  findInvestigationsForPatient(
    patient: $patient
    page: $page
    openOnly: $openOnly
  ) {
    content {
      investigation
      startedOn
      condition
      status
      caseStatus
      jurisdiction
      event
      coInfection
      notification
      investigator
      comparable
    }
    total
    number
  }
}
    `;

/**
 * __useFindInvestigationsForPatientQuery__
 *
 * To run a query within a React component, call `useFindInvestigationsForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindInvestigationsForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindInvestigationsForPatientQuery({
 *   variables: {
 *      patient: // value for 'patient'
 *      page: // value for 'page'
 *      openOnly: // value for 'openOnly'
 *   },
 * });
 */
export function useFindInvestigationsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables> & ({ variables: FindInvestigationsForPatientQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>(FindInvestigationsForPatientDocument, options);
      }
export function useFindInvestigationsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>(FindInvestigationsForPatientDocument, options);
        }
export function useFindInvestigationsForPatientSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>(FindInvestigationsForPatientDocument, options);
        }
export type FindInvestigationsForPatientQueryHookResult = ReturnType<typeof useFindInvestigationsForPatientQuery>;
export type FindInvestigationsForPatientLazyQueryHookResult = ReturnType<typeof useFindInvestigationsForPatientLazyQuery>;
export type FindInvestigationsForPatientSuspenseQueryHookResult = ReturnType<typeof useFindInvestigationsForPatientSuspenseQuery>;
export type FindInvestigationsForPatientQueryResult = Apollo.QueryResult<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>;
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
export const FindLabReportsForPatientDocument = gql`
    query findLabReportsForPatient($personUid: Int!, $page: Page) {
  findLabReportsForPatient(personUid: $personUid, page: $page) {
    content {
      id
      observationUid
      addTime
      effectiveFromTime
      programAreaCd
      jurisdictionCodeDescTxt
      localId
      electronicInd
      associatedInvestigations {
        publicHealthCaseUid
        cdDescTxt
        localId
      }
      personParticipations {
        typeCd
        personCd
        firstName
        lastName
      }
      organizationParticipations {
        typeCd
        name
      }
      observations {
        domainCd
        cdDescTxt
        displayName
      }
    }
    total
    number
  }
}
    `;

/**
 * __useFindLabReportsForPatientQuery__
 *
 * To run a query within a React component, call `useFindLabReportsForPatientQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindLabReportsForPatientQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindLabReportsForPatientQuery({
 *   variables: {
 *      personUid: // value for 'personUid'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindLabReportsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindLabReportsForPatientQuery, FindLabReportsForPatientQueryVariables> & ({ variables: FindLabReportsForPatientQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindLabReportsForPatientQuery, FindLabReportsForPatientQueryVariables>(FindLabReportsForPatientDocument, options);
      }
export function useFindLabReportsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindLabReportsForPatientQuery, FindLabReportsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindLabReportsForPatientQuery, FindLabReportsForPatientQueryVariables>(FindLabReportsForPatientDocument, options);
        }
export function useFindLabReportsForPatientSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindLabReportsForPatientQuery, FindLabReportsForPatientQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindLabReportsForPatientQuery, FindLabReportsForPatientQueryVariables>(FindLabReportsForPatientDocument, options);
        }
export type FindLabReportsForPatientQueryHookResult = ReturnType<typeof useFindLabReportsForPatientQuery>;
export type FindLabReportsForPatientLazyQueryHookResult = ReturnType<typeof useFindLabReportsForPatientLazyQuery>;
export type FindLabReportsForPatientSuspenseQueryHookResult = ReturnType<typeof useFindLabReportsForPatientSuspenseQuery>;
export type FindLabReportsForPatientQueryResult = Apollo.QueryResult<FindLabReportsForPatientQuery, FindLabReportsForPatientQueryVariables>;
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
export function useFindMorbidityReportsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables> & ({ variables: FindMorbidityReportsForPatientQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>(FindMorbidityReportsForPatientDocument, options);
      }
export function useFindMorbidityReportsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>(FindMorbidityReportsForPatientDocument, options);
        }
export function useFindMorbidityReportsForPatientSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>(FindMorbidityReportsForPatientDocument, options);
        }
export type FindMorbidityReportsForPatientQueryHookResult = ReturnType<typeof useFindMorbidityReportsForPatientQuery>;
export type FindMorbidityReportsForPatientLazyQueryHookResult = ReturnType<typeof useFindMorbidityReportsForPatientLazyQuery>;
export type FindMorbidityReportsForPatientSuspenseQueryHookResult = ReturnType<typeof useFindMorbidityReportsForPatientSuspenseQuery>;
export type FindMorbidityReportsForPatientQueryResult = Apollo.QueryResult<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>;
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
export const FindPatientProfileDocument = gql`
    query findPatientProfile($asOf: Date, $page: Page, $page1: Page, $page2: Page, $page3: Page, $page4: Page, $patient: ID, $shortId: Int) {
  findPatientProfile(patient: $patient, shortId: $shortId) {
    id
    local
    shortId
    version
    status
    deletable
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
      races
      home {
        use
        address
        address2
        city
        state
        zipcode
        country
      }
      address {
        use
        address
        address2
        city
        state
        zipcode
        country
      }
      identification {
        type
        value
      }
      phone {
        use
        number
      }
      email {
        use
        address
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
    addresses(page: $page1) {
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
    phones(page: $page2) {
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
    identification(page: $page3) {
      content {
        patient
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
    races(page: $page4) {
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
      birthOrder
      city
      state {
        id
        description
      }
      county {
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
      county {
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
      stateHIVCase {
        __typename
        ... on Allowed {
          value
        }
        ... on Restricted {
          reason
        }
      }
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
export function useFindPatientProfileSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(FindPatientProfileDocument, options);
        }
export type FindPatientProfileQueryHookResult = ReturnType<typeof useFindPatientProfileQuery>;
export type FindPatientProfileLazyQueryHookResult = ReturnType<typeof useFindPatientProfileLazyQuery>;
export type FindPatientProfileSuspenseQueryHookResult = ReturnType<typeof useFindPatientProfileSuspenseQuery>;
export type FindPatientProfileQueryResult = Apollo.QueryResult<FindPatientProfileQuery, FindPatientProfileQueryVariables>;
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
export function useFindVaccinationsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables> & ({ variables: FindVaccinationsForPatientQueryVariables; skip?: boolean; } | { skip: boolean; }) ) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>(FindVaccinationsForPatientDocument, options);
      }
export function useFindVaccinationsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>(FindVaccinationsForPatientDocument, options);
        }
export function useFindVaccinationsForPatientSuspenseQuery(baseOptions?: Apollo.SkipToken | Apollo.SuspenseQueryHookOptions<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>) {
          const options = baseOptions === Apollo.skipToken ? baseOptions : {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>(FindVaccinationsForPatientDocument, options);
        }
export type FindVaccinationsForPatientQueryHookResult = ReturnType<typeof useFindVaccinationsForPatientQuery>;
export type FindVaccinationsForPatientLazyQueryHookResult = ReturnType<typeof useFindVaccinationsForPatientLazyQuery>;
export type FindVaccinationsForPatientSuspenseQueryHookResult = ReturnType<typeof useFindVaccinationsForPatientSuspenseQuery>;
export type FindVaccinationsForPatientQueryResult = Apollo.QueryResult<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>;