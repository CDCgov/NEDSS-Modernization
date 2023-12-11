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
};

export type ActId = {
  __typename?: 'ActId';
  actIdSeq?: Maybe<Scalars['Int']['output']>;
  id?: Maybe<Scalars['Int']['output']>;
  lastChangeTime?: Maybe<Scalars['DateTime']['output']>;
  recordStatus?: Maybe<Scalars['String']['output']>;
  rootExtensionTxt?: Maybe<Scalars['String']['output']>;
  typeCd?: Maybe<Scalars['String']['output']>;
};

export type AddressType = {
  __typename?: 'AddressType';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type AddressTypeResults = {
  __typename?: 'AddressTypeResults';
  content: Array<AddressType>;
  total: Scalars['Int']['output'];
};

export type AddressUse = {
  __typename?: 'AddressUse';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type AddressUseResults = {
  __typename?: 'AddressUseResults';
  content: Array<AddressUse>;
  total: Scalars['Int']['output'];
};

export type AdministrativeInput = {
  asOf?: InputMaybe<Scalars['DateTime']['input']>;
  comment?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
};

export type AssigningAuthor = {
  __typename?: 'AssigningAuthor';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type AssigningAuthorResults = {
  __typename?: 'AssigningAuthorResults';
  content: Array<AssigningAuthor>;
  total: Scalars['Int']['output'];
};

export type AssociatedInvestigation = {
  __typename?: 'AssociatedInvestigation';
  actRelationshipLastChgTime?: Maybe<Scalars['DateTime']['output']>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  lastChgTime?: Maybe<Scalars['DateTime']['output']>;
  localId?: Maybe<Scalars['String']['output']>;
  publicHealthCaseUid?: Maybe<Scalars['Int']['output']>;
};

export enum CaseStatus {
  Confirmed = 'CONFIRMED',
  NotACase = 'NOT_A_CASE',
  Probable = 'PROBABLE',
  Suspect = 'SUSPECT',
  Unassigned = 'UNASSIGNED',
  Unknown = 'UNKNOWN'
}

export type CodeValueGeneralId = {
  __typename?: 'CodeValueGeneralId';
  code: Scalars['ID']['output'];
  codeSetNm: Scalars['String']['output'];
};

export type CodedResult = {
  __typename?: 'CodedResult';
  name: Scalars['String']['output'];
};

export type CodedValue = {
  __typename?: 'CodedValue';
  name: Scalars['String']['output'];
  value: Scalars['String']['output'];
};

export type ConditionCode = {
  __typename?: 'ConditionCode';
  conditionDescTxt?: Maybe<Scalars['String']['output']>;
  id: Scalars['String']['output'];
};

export type ContactsNamedByPatientResults = {
  __typename?: 'ContactsNamedByPatientResults';
  content: Array<Maybe<NamedByPatient>>;
  number: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export enum Deceased {
  N = 'N',
  Unk = 'UNK',
  Y = 'Y'
}

export type Degree = {
  __typename?: 'Degree';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type DegreeResults = {
  __typename?: 'DegreeResults';
  content: Array<Degree>;
  total: Scalars['Int']['output'];
};

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

export type Ethnicity = {
  __typename?: 'Ethnicity';
  codeDescTxt: Scalars['String']['output'];
  id: EthnicityId;
};

export type EthnicityId = {
  __typename?: 'EthnicityId';
  code: Scalars['String']['output'];
};

export type EthnicityInput = {
  asOf: Scalars['DateTime']['input'];
  detailed?: InputMaybe<Array<Scalars['String']['input']>>;
  ethnicGroup?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['String']['input'];
  unknownReason?: InputMaybe<Scalars['String']['input']>;
};

export type EthnicityResults = {
  __typename?: 'EthnicityResults';
  content: Array<Maybe<Ethnicity>>;
  total: Scalars['Int']['output'];
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

export enum Gender {
  F = 'F',
  M = 'M',
  U = 'U'
}

export type GeneralInfoInput = {
  adultsInHouse?: InputMaybe<Scalars['Int']['input']>;
  asOf: Scalars['DateTime']['input'];
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

export type GroupedCodedValue = {
  __typename?: 'GroupedCodedValue';
  group: Scalars['String']['output'];
  name: Scalars['String']['output'];
  value: Scalars['String']['output'];
};

export type IdentificationCriteria = {
  assigningAuthority?: InputMaybe<Scalars['String']['input']>;
  identificationNumber?: InputMaybe<Scalars['String']['input']>;
  identificationType?: InputMaybe<Scalars['String']['input']>;
};

export type IdentificationType = {
  __typename?: 'IdentificationType';
  codeDescTxt: Scalars['String']['output'];
  id: IdentificationTypeId;
};

export type IdentificationTypeId = {
  __typename?: 'IdentificationTypeId';
  code: Scalars['String']['output'];
};

export type IdentificationTypes = {
  __typename?: 'IdentificationTypes';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type IdentificationTypesResults = {
  __typename?: 'IdentificationTypesResults';
  content: Array<IdentificationTypes>;
  total: Scalars['Int']['output'];
};

export type Investigation = {
  __typename?: 'Investigation';
  actIds?: Maybe<Array<Maybe<ActId>>>;
  activityFromTime?: Maybe<Scalars['DateTime']['output']>;
  activityToTime?: Maybe<Scalars['DateTime']['output']>;
  addTime?: Maybe<Scalars['DateTime']['output']>;
  addUserId?: Maybe<Scalars['Int']['output']>;
  caseClassCd?: Maybe<Scalars['String']['output']>;
  caseTypeCd?: Maybe<Scalars['String']['output']>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  currProcessStateCd?: Maybe<Scalars['String']['output']>;
  id?: Maybe<Scalars['ID']['output']>;
  investigationStatusCd?: Maybe<Scalars['String']['output']>;
  jurisdictionCd?: Maybe<Scalars['Int']['output']>;
  jurisdictionCodeDescTxt?: Maybe<Scalars['String']['output']>;
  lastChangeTime?: Maybe<Scalars['DateTime']['output']>;
  lastChangeUserId?: Maybe<Scalars['Int']['output']>;
  localId?: Maybe<Scalars['String']['output']>;
  moodCd?: Maybe<Scalars['String']['output']>;
  notificationAddTime?: Maybe<Scalars['DateTime']['output']>;
  notificationLastChgTime?: Maybe<Scalars['DateTime']['output']>;
  notificationLocalId?: Maybe<Scalars['String']['output']>;
  notificationRecordStatusCd?: Maybe<Scalars['String']['output']>;
  organizationParticipations?: Maybe<Array<Maybe<OrganizationParticipation>>>;
  outbreakName?: Maybe<Scalars['String']['output']>;
  personParticipations?: Maybe<Array<Maybe<PersonParticipation>>>;
  pregnantIndCd?: Maybe<Scalars['String']['output']>;
  progAreaCd?: Maybe<Scalars['String']['output']>;
  publicHealthCaseLastChgTime?: Maybe<Scalars['DateTime']['output']>;
  publicHealthCaseUid?: Maybe<Scalars['Int']['output']>;
  recordStatus?: Maybe<Scalars['String']['output']>;
  rptFormCmpltTime?: Maybe<Scalars['DateTime']['output']>;
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
  conditions?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  createdBy?: InputMaybe<Scalars['String']['input']>;
  eventDate?: InputMaybe<InvestigationEventDateSearch>;
  eventId?: InputMaybe<EventId>;
  investigationStatus?: InputMaybe<InvestigationStatus>;
  investigatorId?: InputMaybe<Scalars['ID']['input']>;
  jurisdictions?: InputMaybe<Array<InputMaybe<Scalars['ID']['input']>>>;
  lastUpdatedBy?: InputMaybe<Scalars['String']['input']>;
  notificationStatuses?: InputMaybe<Array<InputMaybe<NotificationStatus>>>;
  outbreakNames?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  patientId?: InputMaybe<Scalars['Int']['input']>;
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatuses?: InputMaybe<Array<InputMaybe<ProcessingStatus>>>;
  programAreas?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  providerFacilitySearch?: InputMaybe<ProviderFacilitySearch>;
};

export type InvestigationResults = {
  __typename?: 'InvestigationResults';
  content: Array<Maybe<Investigation>>;
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

export type KeyValuePair = {
  __typename?: 'KeyValuePair';
  key: Scalars['String']['output'];
  value: Scalars['String']['output'];
};

export type KeyValuePairResults = {
  __typename?: 'KeyValuePairResults';
  content: Array<KeyValuePair>;
  total: Scalars['Int']['output'];
};

export type LabReport = {
  __typename?: 'LabReport';
  actIds?: Maybe<Array<Maybe<ActId>>>;
  activityToTime?: Maybe<Scalars['DateTime']['output']>;
  addTime?: Maybe<Scalars['DateTime']['output']>;
  addUserId?: Maybe<Scalars['Int']['output']>;
  associatedInvestigations?: Maybe<Array<Maybe<AssociatedInvestigation>>>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  classCd?: Maybe<Scalars['String']['output']>;
  effectiveFromTime?: Maybe<Scalars['DateTime']['output']>;
  electronicInd?: Maybe<Scalars['String']['output']>;
  id?: Maybe<Scalars['String']['output']>;
  jurisdictionCd?: Maybe<Scalars['Int']['output']>;
  jurisdictionCodeDescTxt?: Maybe<Scalars['String']['output']>;
  lastChange?: Maybe<Scalars['DateTime']['output']>;
  lastChgUserId?: Maybe<Scalars['Int']['output']>;
  localId?: Maybe<Scalars['String']['output']>;
  materialParticipations?: Maybe<Array<Maybe<MaterialParticipation>>>;
  moodCd?: Maybe<Scalars['String']['output']>;
  observationLastChgTime?: Maybe<Scalars['DateTime']['output']>;
  observationUid?: Maybe<Scalars['Int']['output']>;
  observations?: Maybe<Array<Maybe<Observation>>>;
  organizationParticipations?: Maybe<Array<Maybe<OrganizationParticipation>>>;
  personParticipations?: Maybe<Array<Maybe<PersonParticipation>>>;
  pregnantIndCd?: Maybe<Scalars['String']['output']>;
  programAreaCd?: Maybe<Scalars['String']['output']>;
  recordStatusCd?: Maybe<Scalars['String']['output']>;
  rptToStateTime?: Maybe<Scalars['DateTime']['output']>;
  versionCtrlNbr?: Maybe<Scalars['Int']['output']>;
};

export type LabReportEventId = {
  labEventId: Scalars['String']['input'];
  labEventType: LaboratoryEventIdType;
};

export type LabReportFilter = {
  codedResult?: InputMaybe<Scalars['String']['input']>;
  createdBy?: InputMaybe<Scalars['ID']['input']>;
  enteredBy?: InputMaybe<Array<InputMaybe<UserType>>>;
  entryMethods?: InputMaybe<Array<InputMaybe<EntryMethod>>>;
  eventDate?: InputMaybe<LaboratoryEventDateSearch>;
  eventId?: InputMaybe<LabReportEventId>;
  eventStatus?: InputMaybe<Array<InputMaybe<EventStatus>>>;
  jurisdictions?: InputMaybe<Array<InputMaybe<Scalars['ID']['input']>>>;
  lastUpdatedBy?: InputMaybe<Scalars['ID']['input']>;
  patientId?: InputMaybe<Scalars['Int']['input']>;
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatus?: InputMaybe<Array<InputMaybe<LaboratoryReportStatus>>>;
  programAreas?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  providerSearch?: InputMaybe<LabReportProviderSearch>;
  resultedTest?: InputMaybe<Scalars['String']['input']>;
};

export type LabReportProviderSearch = {
  providerId: Scalars['ID']['input'];
  providerType: ProviderType;
};

export type LabReportResults = {
  __typename?: 'LabReportResults';
  content: Array<Maybe<LabReport>>;
  total: Scalars['Int']['output'];
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

export type MaterialParticipation = {
  __typename?: 'MaterialParticipation';
  actUid?: Maybe<Scalars['Int']['output']>;
  cd?: Maybe<Scalars['String']['output']>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  entityId?: Maybe<Scalars['String']['output']>;
  participationLastChangeTime?: Maybe<Scalars['DateTime']['output']>;
  participationRecordStatus?: Maybe<Scalars['String']['output']>;
  subjectClassCd?: Maybe<Scalars['String']['output']>;
  typeCd?: Maybe<Scalars['String']['output']>;
  typeDescTxt?: Maybe<Scalars['String']['output']>;
};

export type MortalityInput = {
  asOf: Scalars['DateTime']['input'];
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
  addPatientRace: PatientRaceChangeResult;
  createPatient: PatientCreatedResponse;
  deletePatient: PatientDeleteResult;
  deletePatientAddress: PatientAddressChangeResult;
  deletePatientEmail: PatientUpdateResponse;
  deletePatientIdentification: PatientIdentificationChangeResult;
  deletePatientName: PatientNameChangeResult;
  deletePatientPhone: PatientPhoneChangeResult;
  deletePatientRace: PatientRaceChangeResult;
  updateEthnicity: PatientEthnicityChangeResult;
  updatePatientAddress: PatientAddressChangeResult;
  updatePatientAdministrative: PatientAdministrativeChangeResult;
  updatePatientBirthAndGender: PatientBirthAndGenderChangeResult;
  updatePatientGeneralInfo: PatientGeneralChangeResult;
  updatePatientIdentification: PatientIdentificationChangeResult;
  updatePatientMortality: PatientMortalityChangeResult;
  updatePatientName: PatientNameChangeResult;
  updatePatientPhone: PatientPhoneChangeResult;
  updatePatientRace: PatientRaceChangeResult;
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


export type MutationCreatePatientArgs = {
  patient: PersonInput;
};


export type MutationDeletePatientArgs = {
  patient: Scalars['ID']['input'];
};


export type MutationDeletePatientAddressArgs = {
  input?: InputMaybe<DeletePatientAddressInput>;
};


export type MutationDeletePatientEmailArgs = {
  patientId: Scalars['Int']['input'];
  personSeqNum: Scalars['Int']['input'];
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


export type MutationUpdatePatientAdministrativeArgs = {
  input: AdministrativeInput;
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

export type NaicsIndustryCode = {
  __typename?: 'NaicsIndustryCode';
  assigningAuthorityCd?: Maybe<Scalars['String']['output']>;
  assigningAuthorityDescTxt?: Maybe<Scalars['String']['output']>;
  codeDescTxt?: Maybe<Scalars['String']['output']>;
  codeSetNm?: Maybe<Scalars['String']['output']>;
  codeShortDescTxt?: Maybe<Scalars['String']['output']>;
  effectiveFromTime?: Maybe<Scalars['DateTime']['output']>;
  effectiveToTime?: Maybe<Scalars['DateTime']['output']>;
  id: Scalars['ID']['output'];
  indentLevelNbr?: Maybe<Scalars['Int']['output']>;
  isModifiableInd?: Maybe<Scalars['String']['output']>;
  keyInfoTxt?: Maybe<Scalars['String']['output']>;
  nbsUid?: Maybe<Scalars['Int']['output']>;
  parentIsCd?: Maybe<Scalars['String']['output']>;
  seqNum?: Maybe<Scalars['Int']['output']>;
  sourceConceptId?: Maybe<Scalars['String']['output']>;
  statusCd?: Maybe<Scalars['String']['output']>;
  statusTime?: Maybe<Scalars['String']['output']>;
};

export type NamePrefix = {
  __typename?: 'NamePrefix';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type NamePrefixResults = {
  __typename?: 'NamePrefixResults';
  content: Array<NamePrefix>;
  total: Scalars['Int']['output'];
};

export type NameType = {
  __typename?: 'NameType';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type NameTypeResults = {
  __typename?: 'NameTypeResults';
  content: Array<NameType>;
  total: Scalars['Int']['output'];
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

export type NewPatientAddress = {
  censusTract?: InputMaybe<Scalars['String']['input']>;
  city?: InputMaybe<Scalars['String']['input']>;
  country?: InputMaybe<Scalars['String']['input']>;
  county?: InputMaybe<Scalars['String']['input']>;
  state?: InputMaybe<Scalars['String']['input']>;
  streetAddress1?: InputMaybe<Scalars['String']['input']>;
  streetAddress2?: InputMaybe<Scalars['String']['input']>;
  zip?: InputMaybe<Scalars['String']['input']>;
};

export type NewPatientAddressInput = {
  address1?: InputMaybe<Scalars['String']['input']>;
  address2?: InputMaybe<Scalars['String']['input']>;
  asOf: Scalars['DateTime']['input'];
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

export type NewPatientIdentification = {
  authority?: InputMaybe<Scalars['String']['input']>;
  type: Scalars['String']['input'];
  value: Scalars['String']['input'];
};

export type NewPatientIdentificationInput = {
  asOf: Scalars['DateTime']['input'];
  authority?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  type: Scalars['String']['input'];
  value: Scalars['String']['input'];
};

export type NewPatientName = {
  first?: InputMaybe<Scalars['String']['input']>;
  last?: InputMaybe<Scalars['String']['input']>;
  middle?: InputMaybe<Scalars['String']['input']>;
  suffix?: InputMaybe<Suffix>;
  use: NameUseCd;
};

export type NewPatientNameInput = {
  asOf?: InputMaybe<Scalars['DateTime']['input']>;
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
  asOf: Scalars['DateTime']['input'];
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

export type NewPatientPhoneNumber = {
  extension?: InputMaybe<Scalars['String']['input']>;
  number: Scalars['String']['input'];
  type: Scalars['String']['input'];
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
  altCdSystemCd?: Maybe<Scalars['String']['output']>;
  altDescTxt?: Maybe<Scalars['String']['output']>;
  cd?: Maybe<Scalars['String']['output']>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  displayName?: Maybe<Scalars['String']['output']>;
  domainCd?: Maybe<Scalars['String']['output']>;
  ovcAltCdSystemCd?: Maybe<Scalars['String']['output']>;
  ovcAltCode?: Maybe<Scalars['String']['output']>;
  ovcAltDescTxt?: Maybe<Scalars['String']['output']>;
  ovcCode?: Maybe<Scalars['String']['output']>;
  statusCd?: Maybe<Scalars['String']['output']>;
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

export type Organization = {
  __typename?: 'Organization';
  addReasonCd?: Maybe<Scalars['String']['output']>;
  addTime?: Maybe<Scalars['DateTime']['output']>;
  addUserId?: Maybe<Scalars['ID']['output']>;
  cd?: Maybe<Scalars['String']['output']>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  cityCd?: Maybe<Scalars['String']['output']>;
  cityDescTxt?: Maybe<Scalars['String']['output']>;
  cntryCd?: Maybe<Scalars['String']['output']>;
  cntyCd?: Maybe<Scalars['String']['output']>;
  description?: Maybe<Scalars['String']['output']>;
  displayNm?: Maybe<Scalars['String']['output']>;
  durationAmt?: Maybe<Scalars['String']['output']>;
  durationUnitCd?: Maybe<Scalars['String']['output']>;
  edxInd?: Maybe<Scalars['String']['output']>;
  electronicInd?: Maybe<Scalars['String']['output']>;
  fromTime?: Maybe<Scalars['DateTime']['output']>;
  id?: Maybe<Scalars['ID']['output']>;
  lastChgReasonCd?: Maybe<Scalars['String']['output']>;
  lastChgTime?: Maybe<Scalars['DateTime']['output']>;
  lastChgUserId?: Maybe<Scalars['Int']['output']>;
  localId?: Maybe<Scalars['String']['output']>;
  phoneCntryCd?: Maybe<Scalars['String']['output']>;
  phoneNbr?: Maybe<Scalars['String']['output']>;
  recordStatusCd?: Maybe<RecordStatus>;
  recordStatusTime?: Maybe<Scalars['DateTime']['output']>;
  standardIndustryClassCd?: Maybe<Scalars['String']['output']>;
  standardIndustryDescTxt?: Maybe<Scalars['String']['output']>;
  stateCd?: Maybe<Scalars['String']['output']>;
  statusCd?: Maybe<Scalars['String']['output']>;
  statusTime?: Maybe<Scalars['DateTime']['output']>;
  streetAddr1?: Maybe<Scalars['String']['output']>;
  streetAddr2?: Maybe<Scalars['String']['output']>;
  toTime?: Maybe<Scalars['DateTime']['output']>;
  userAffiliationTxt?: Maybe<Scalars['String']['output']>;
  versionCtrlNbr?: Maybe<Scalars['Int']['output']>;
  zipCd?: Maybe<Scalars['String']['output']>;
};

export type OrganizationFilter = {
  cityCd?: InputMaybe<Scalars['String']['input']>;
  cityDescTxt?: InputMaybe<Scalars['String']['input']>;
  displayNm?: InputMaybe<Scalars['String']['input']>;
  id?: InputMaybe<Scalars['ID']['input']>;
  stateCd?: InputMaybe<Scalars['String']['input']>;
  streetAddr1?: InputMaybe<Scalars['String']['input']>;
  streetAddr2?: InputMaybe<Scalars['String']['input']>;
  zipCd?: InputMaybe<Scalars['String']['input']>;
};

export type OrganizationParticipation = {
  __typename?: 'OrganizationParticipation';
  actUid?: Maybe<Scalars['Int']['output']>;
  entityId?: Maybe<Scalars['Int']['output']>;
  name?: Maybe<Scalars['String']['output']>;
  organizationLastChangeTime?: Maybe<Scalars['DateTime']['output']>;
  participationLastChangeTime?: Maybe<Scalars['DateTime']['output']>;
  participationRecordStatus?: Maybe<Scalars['String']['output']>;
  subjectClassCd?: Maybe<Scalars['String']['output']>;
  typeCd?: Maybe<Scalars['String']['output']>;
  typeDescTxt?: Maybe<Scalars['String']['output']>;
};

export type OrganizationResults = {
  __typename?: 'OrganizationResults';
  content: Array<Maybe<Organization>>;
  total: Scalars['Int']['output'];
};

export type Outbreak = {
  __typename?: 'Outbreak';
  codeShortDescTxt?: Maybe<Scalars['String']['output']>;
  id: OutbreakId;
};

export type OutbreakId = {
  __typename?: 'OutbreakId';
  code: Scalars['String']['output'];
  codeSetNm: Scalars['String']['output'];
};

export type OutbreakResults = {
  __typename?: 'OutbreakResults';
  content: Array<Maybe<Outbreak>>;
  total: Scalars['Int']['output'];
};

export type Page = {
  pageNumber: Scalars['Int']['input'];
  pageSize: Scalars['Int']['input'];
};

export type PatientAddress = {
  __typename?: 'PatientAddress';
  address1?: Maybe<Scalars['String']['output']>;
  address2?: Maybe<Scalars['String']['output']>;
  asOf: Scalars['DateTime']['output'];
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

export type PatientAdministrative = {
  __typename?: 'PatientAdministrative';
  asOf: Scalars['DateTime']['output'];
  comment?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  patient: Scalars['ID']['output'];
  version: Scalars['Int']['output'];
};

export type PatientAdministrativeChangeResult = {
  __typename?: 'PatientAdministrativeChangeResult';
  patient: Scalars['Int']['output'];
};

export type PatientAdministrativeResults = {
  __typename?: 'PatientAdministrativeResults';
  content: Array<PatientAdministrative>;
  number: Scalars['Int']['output'];
  size: Scalars['Int']['output'];
  total: Scalars['Int']['output'];
};

export type PatientBirth = {
  __typename?: 'PatientBirth';
  age?: Maybe<Scalars['Int']['output']>;
  asOf: Scalars['DateTime']['output'];
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

export type PatientCreatedResponse = {
  __typename?: 'PatientCreatedResponse';
  id: Scalars['Int']['output'];
  shortId: Scalars['Int']['output'];
};

export type PatientDeleteFailed = {
  __typename?: 'PatientDeleteFailed';
  patient: Scalars['Int']['output'];
  reason: Scalars['String']['output'];
};

export type PatientDeleteResult = PatientDeleteFailed | PatientDeleteSuccessful;

export type PatientDeleteSuccessful = {
  __typename?: 'PatientDeleteSuccessful';
  patient: Scalars['Int']['output'];
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
  asOf: Scalars['DateTime']['output'];
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
  asOf: Scalars['DateTime']['output'];
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
  asOf: Scalars['DateTime']['output'];
  childrenInHouse?: Maybe<Scalars['Int']['output']>;
  educationLevel?: Maybe<PatientEducationLevel>;
  id: Scalars['ID']['output'];
  maritalStatus?: Maybe<PatientMaritalStatus>;
  maternalMaidenName?: Maybe<Scalars['String']['output']>;
  occupation?: Maybe<PatientOccupation>;
  patient: Scalars['Int']['output'];
  primaryLanguage?: Maybe<PatientPrimaryLanguage>;
  speaksEnglish?: Maybe<PatientIndicatorCodedValue>;
  stateHIVCase?: Maybe<Scalars['String']['output']>;
  version: Scalars['Int']['output'];
};

export type PatientGeneralChangeResult = {
  __typename?: 'PatientGeneralChangeResult';
  patient: Scalars['Int']['output'];
};

export type PatientIdentification = {
  __typename?: 'PatientIdentification';
  asOf: Scalars['DateTime']['output'];
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

export type PatientIdentificationTypeResults = {
  __typename?: 'PatientIdentificationTypeResults';
  content: Array<Maybe<IdentificationType>>;
  total: Scalars['Int']['output'];
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
  asOf: Scalars['DateTime']['output'];
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
  asOf: Scalars['DateTime']['output'];
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
  asOf: Scalars['DateTime']['output'];
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
  administrative: PatientAdministrativeResults;
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
  asOf?: InputMaybe<Scalars['DateTime']['input']>;
};

export type PatientRace = {
  __typename?: 'PatientRace';
  asOf: Scalars['DateTime']['output'];
  category: PatientRaceCategory;
  detailed: Array<PatientRaceDetail>;
  id: Scalars['Int']['output'];
  patient: Scalars['Int']['output'];
  version: Scalars['Int']['output'];
};

export type PatientRaceCategory = {
  __typename?: 'PatientRaceCategory';
  description: Scalars['String']['output'];
  id: Scalars['String']['output'];
};

export type PatientRaceChangeResult = {
  __typename?: 'PatientRaceChangeResult';
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
  state?: Maybe<Scalars['String']['output']>;
  use: Scalars['String']['output'];
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
};

export type PatientSearchResults = {
  __typename?: 'PatientSearchResults';
  content: Array<PatientSearchResult>;
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
  address?: InputMaybe<Scalars['String']['input']>;
  city?: InputMaybe<Scalars['String']['input']>;
  country?: InputMaybe<Scalars['String']['input']>;
  dateOfBirth?: InputMaybe<Scalars['Date']['input']>;
  dateOfBirthOperator?: InputMaybe<Operator>;
  deceased?: InputMaybe<Deceased>;
  email?: InputMaybe<Scalars['String']['input']>;
  ethnicity?: InputMaybe<Scalars['String']['input']>;
  firstName?: InputMaybe<Scalars['String']['input']>;
  gender?: InputMaybe<Scalars['String']['input']>;
  id?: InputMaybe<Scalars['ID']['input']>;
  identification?: InputMaybe<IdentificationCriteria>;
  lastName?: InputMaybe<Scalars['String']['input']>;
  mortalityStatus?: InputMaybe<Scalars['String']['input']>;
  phoneNumber?: InputMaybe<Scalars['String']['input']>;
  race?: InputMaybe<Scalars['String']['input']>;
  recordStatus: Array<RecordStatus>;
  state?: InputMaybe<Scalars['String']['input']>;
  treatmentId?: InputMaybe<Scalars['String']['input']>;
  vaccinationId?: InputMaybe<Scalars['String']['input']>;
  zip?: InputMaybe<Scalars['String']['input']>;
};

export type PersonInput = {
  addresses?: InputMaybe<Array<NewPatientAddress>>;
  asOf?: InputMaybe<Scalars['DateTime']['input']>;
  birthGender?: InputMaybe<Gender>;
  comments?: InputMaybe<Scalars['String']['input']>;
  currentGender?: InputMaybe<Gender>;
  dateOfBirth?: InputMaybe<Scalars['Date']['input']>;
  deceased?: InputMaybe<Deceased>;
  deceasedTime?: InputMaybe<Scalars['DateTime']['input']>;
  emailAddresses?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  ethnicity?: InputMaybe<Scalars['String']['input']>;
  identifications?: InputMaybe<Array<NewPatientIdentification>>;
  maritalStatus?: InputMaybe<Scalars['String']['input']>;
  names?: InputMaybe<Array<InputMaybe<NewPatientName>>>;
  phoneNumbers?: InputMaybe<Array<NewPatientPhoneNumber>>;
  races?: InputMaybe<Array<InputMaybe<Scalars['String']['input']>>>;
  stateHIVCase?: InputMaybe<Scalars['String']['input']>;
};

export type PersonParticipation = {
  __typename?: 'PersonParticipation';
  actUid: Scalars['Int']['output'];
  birthTime?: Maybe<Scalars['DateTime']['output']>;
  currSexCd?: Maybe<Scalars['String']['output']>;
  entityId: Scalars['Int']['output'];
  firstName?: Maybe<Scalars['String']['output']>;
  lastName?: Maybe<Scalars['String']['output']>;
  localId?: Maybe<Scalars['String']['output']>;
  participationLastChangeTime?: Maybe<Scalars['DateTime']['output']>;
  participationRecordStatus?: Maybe<Scalars['String']['output']>;
  personCd: Scalars['String']['output'];
  personLastChangeTime?: Maybe<Scalars['DateTime']['output']>;
  personParentUid?: Maybe<Scalars['Int']['output']>;
  personRecordStatus: Scalars['String']['output'];
  shortId?: Maybe<Scalars['Int']['output']>;
  subjectClassCd?: Maybe<Scalars['String']['output']>;
  typeCd?: Maybe<Scalars['String']['output']>;
  typeDescTxt?: Maybe<Scalars['String']['output']>;
};

export type PhoneAndEmailType = {
  __typename?: 'PhoneAndEmailType';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type PhoneAndEmailTypeResults = {
  __typename?: 'PhoneAndEmailTypeResults';
  content: Array<PhoneAndEmailType>;
  total: Scalars['Int']['output'];
};

export type PhoneAndEmailUse = {
  __typename?: 'PhoneAndEmailUse';
  codeShortDescTxt: Scalars['String']['output'];
  id: CodeValueGeneralId;
};

export type PhoneAndEmailUseResults = {
  __typename?: 'PhoneAndEmailUseResults';
  content: Array<PhoneAndEmailUse>;
  total: Scalars['Int']['output'];
};

export type Place = {
  __typename?: 'Place';
  addReasonCd?: Maybe<Scalars['String']['output']>;
  addTime?: Maybe<Scalars['DateTime']['output']>;
  addUserId?: Maybe<Scalars['Int']['output']>;
  cd?: Maybe<Scalars['String']['output']>;
  cdDescTxt?: Maybe<Scalars['String']['output']>;
  cityCd?: Maybe<Scalars['String']['output']>;
  cityDescTxt?: Maybe<Scalars['String']['output']>;
  cntryCd?: Maybe<Scalars['String']['output']>;
  cntyCd?: Maybe<Scalars['String']['output']>;
  description?: Maybe<Scalars['String']['output']>;
  durationAmt?: Maybe<Scalars['String']['output']>;
  durationUnitCd?: Maybe<Scalars['String']['output']>;
  fromTime?: Maybe<Scalars['DateTime']['output']>;
  id?: Maybe<Scalars['ID']['output']>;
  lastChgReasonCd?: Maybe<Scalars['String']['output']>;
  lastChgTime?: Maybe<Scalars['DateTime']['output']>;
  lastChgUserId?: Maybe<Scalars['Int']['output']>;
  localId?: Maybe<Scalars['String']['output']>;
  nm?: Maybe<Scalars['String']['output']>;
  phoneCntryCd?: Maybe<Scalars['String']['output']>;
  phoneNbr?: Maybe<Scalars['String']['output']>;
  recordStatusCd?: Maybe<Scalars['String']['output']>;
  recordStatusTime?: Maybe<Scalars['DateTime']['output']>;
  stateCd?: Maybe<Scalars['String']['output']>;
  statusCd?: Maybe<Scalars['String']['output']>;
  statusTime?: Maybe<Scalars['DateTime']['output']>;
  streetAddr1?: Maybe<Scalars['String']['output']>;
  streetAddr2?: Maybe<Scalars['String']['output']>;
  toTime?: Maybe<Scalars['DateTime']['output']>;
  userAffiliationTxt?: Maybe<Scalars['String']['output']>;
  versionCtrlNbr?: Maybe<Scalars['Int']['output']>;
  zipCd?: Maybe<Scalars['String']['output']>;
};

export type PlaceFilter = {
  cityCd?: InputMaybe<Scalars['String']['input']>;
  cityDescTxt?: InputMaybe<Scalars['String']['input']>;
  description?: InputMaybe<Scalars['String']['input']>;
  id?: InputMaybe<Scalars['ID']['input']>;
  nm?: InputMaybe<Scalars['String']['input']>;
  stateCd?: InputMaybe<Scalars['String']['input']>;
  streetAddr1?: InputMaybe<Scalars['String']['input']>;
  streetAddr2?: InputMaybe<Scalars['String']['input']>;
  zipCd?: InputMaybe<Scalars['String']['input']>;
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
  addressTypes: Array<CodedValue>;
  addressUses: Array<CodedValue>;
  assigningAuthorities: Array<CodedValue>;
  counties: Array<GroupedCodedValue>;
  countries: Array<CodedValue>;
  degrees: Array<CodedValue>;
  detailedEthnicities: Array<CodedValue>;
  detailedRaces: Array<GroupedCodedValue>;
  educationLevels: Array<CodedValue>;
  ethnicGroups: Array<CodedValue>;
  ethnicityUnknownReasons: Array<CodedValue>;
  findAllAddressTypes: AddressTypeResults;
  findAllAddressUses: AddressUseResults;
  findAllAssigningAuthorities: AssigningAuthorResults;
  findAllConditionCodes: Array<Maybe<ConditionCode>>;
  findAllDegrees: DegreeResults;
  findAllEthnicityValues: EthnicityResults;
  findAllIdentificationTypes: IdentificationTypesResults;
  findAllJurisdictions: Array<Maybe<Jurisdiction>>;
  findAllNamePrefixes: NamePrefixResults;
  findAllNameTypes: NameTypeResults;
  findAllOrganizations: OrganizationResults;
  findAllOutbreaks: OutbreakResults;
  findAllPatientIdentificationTypes: PatientIdentificationTypeResults;
  findAllPhoneAndEmailType: PhoneAndEmailTypeResults;
  findAllPhoneAndEmailUse: PhoneAndEmailUseResults;
  findAllPlaces: Array<Maybe<Place>>;
  findAllProgramAreas: Array<Maybe<ProgramAreaCode>>;
  findAllRaceValues: RaceResults;
  findAllStateCountyCodeValues: Array<StateCountyCodeValue>;
  findAllUsers: UserResults;
  findContactsNamedByPatient?: Maybe<ContactsNamedByPatientResults>;
  findDistinctCodedResults: Array<CodedResult>;
  findDistinctResultedTest: Array<ResultedTest>;
  findDocumentsForPatient?: Maybe<PatientDocumentResults>;
  findDocumentsRequiringReviewForPatient: PatientDocumentRequiringReviewResults;
  findInvestigationsByFilter: InvestigationResults;
  findInvestigationsForPatient?: Maybe<PatientInvestigationResults>;
  findLabReportsByFilter: LabReportResults;
  findMorbidityReportsForPatient?: Maybe<PatientMorbidityResults>;
  findNameSuffixes: KeyValuePairResults;
  findOrganizationById?: Maybe<Organization>;
  findOrganizationsByFilter: OrganizationResults;
  findPatientNamedByContact?: Maybe<PatientNamedByContactResults>;
  findPatientProfile?: Maybe<PatientProfile>;
  findPatientsByFilter: PatientSearchResults;
  findPlaceById?: Maybe<Place>;
  findPlacesByFilter: Array<Maybe<Place>>;
  findSnomedCodedResults: SnomedCodedResults;
  findTreatmentsForPatient?: Maybe<PatientTreatmentResults>;
  findVaccinationsForPatient?: Maybe<PatientVaccinationResults>;
  genderUnknownReasons: Array<CodedValue>;
  genders: Array<CodedValue>;
  identificationTypes: Array<CodedValue>;
  maritalStatuses: Array<CodedValue>;
  nameTypes: Array<CodedValue>;
  phoneTypes: Array<CodedValue>;
  phoneUses: Array<CodedValue>;
  preferredGenders: Array<CodedValue>;
  prefixes: Array<CodedValue>;
  primaryLanguages: Array<CodedValue>;
  primaryOccupations: Array<CodedValue>;
  raceCategories: Array<CodedValue>;
  states: Array<StateCodedValue>;
  suffixes: Array<CodedValue>;
};


export type QueryCountiesArgs = {
  state?: InputMaybe<Scalars['String']['input']>;
};


export type QueryDetailedRacesArgs = {
  category?: InputMaybe<Scalars['String']['input']>;
};


export type QueryFindAllAddressTypesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllAddressUsesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllAssigningAuthoritiesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllConditionCodesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllDegreesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllEthnicityValuesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllIdentificationTypesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllJurisdictionsArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllNamePrefixesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllNameTypesArgs = {
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


export type QueryFindAllPhoneAndEmailTypeArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllPhoneAndEmailUseArgs = {
  page?: InputMaybe<Page>;
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


export type QueryFindAllStateCountyCodeValuesArgs = {
  page?: InputMaybe<Page>;
  stateCode?: InputMaybe<Scalars['String']['input']>;
};


export type QueryFindAllUsersArgs = {
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


export type QueryFindMorbidityReportsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindNameSuffixesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindOrganizationByIdArgs = {
  id: Scalars['ID']['input'];
};


export type QueryFindOrganizationsByFilterArgs = {
  filter: OrganizationFilter;
  page?: InputMaybe<Page>;
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
};


export type QueryFindPlaceByIdArgs = {
  id: Scalars['ID']['input'];
};


export type QueryFindPlacesByFilterArgs = {
  filter: PlaceFilter;
  page?: InputMaybe<Page>;
};


export type QueryFindSnomedCodedResultsArgs = {
  page?: InputMaybe<Page>;
  searchText: Scalars['String']['input'];
};


export type QueryFindTreatmentsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};


export type QueryFindVaccinationsForPatientArgs = {
  page?: InputMaybe<Page>;
  patient: Scalars['ID']['input'];
};

export type Race = {
  __typename?: 'Race';
  codeDescTxt: Scalars['String']['output'];
  id: RaceId;
};

export type RaceId = {
  __typename?: 'RaceId';
  code: Scalars['String']['output'];
};

export type RaceInput = {
  asOf?: InputMaybe<Scalars['DateTime']['input']>;
  category: Scalars['String']['input'];
  detailed?: InputMaybe<Array<Scalars['String']['input']>>;
  patient: Scalars['Int']['input'];
};

export type RaceResults = {
  __typename?: 'RaceResults';
  content: Array<Maybe<Race>>;
  total: Scalars['Int']['output'];
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

export type ResultedTest = {
  __typename?: 'ResultedTest';
  name: Scalars['String']['output'];
};

export type SendingFacility = {
  __typename?: 'SendingFacility';
  name?: Maybe<Scalars['String']['output']>;
};

export type SnomedCode = {
  __typename?: 'SnomedCode';
  id?: Maybe<Scalars['String']['output']>;
  snomedDescTxt?: Maybe<Scalars['String']['output']>;
};

export type SnomedCodedResults = {
  __typename?: 'SnomedCodedResults';
  content: Array<Maybe<SnomedCode>>;
  total: Scalars['Int']['output'];
};

export enum SortDirection {
  Asc = 'ASC',
  Desc = 'DESC'
}

export enum SortField {
  BirthTime = 'birthTime',
  LastNm = 'lastNm',
  Relevance = 'relevance'
}

export type SortablePage = {
  pageNumber?: InputMaybe<Scalars['Int']['input']>;
  pageSize?: InputMaybe<Scalars['Int']['input']>;
  sortDirection?: InputMaybe<SortDirection>;
  sortField?: InputMaybe<SortField>;
};

export type StateCodedValue = {
  __typename?: 'StateCodedValue';
  abbreviation: Scalars['String']['output'];
  name: Scalars['String']['output'];
  value: Scalars['ID']['output'];
};

export type StateCountyCodeValue = {
  __typename?: 'StateCountyCodeValue';
  assigningAuthorityCd?: Maybe<Scalars['String']['output']>;
  assigningAuthorityDescTxt?: Maybe<Scalars['String']['output']>;
  codeDescTxt?: Maybe<Scalars['String']['output']>;
  codeSetNm?: Maybe<Scalars['String']['output']>;
  codeShortDescTxt?: Maybe<Scalars['String']['output']>;
  codeSystemCd?: Maybe<Scalars['String']['output']>;
  codeSystemDescTxt?: Maybe<Scalars['String']['output']>;
  effectiveFromTime?: Maybe<Scalars['DateTime']['output']>;
  effectiveToTime?: Maybe<Scalars['DateTime']['output']>;
  excludedTxt?: Maybe<Scalars['String']['output']>;
  id: Scalars['ID']['output'];
  indentLevelNbr?: Maybe<Scalars['Int']['output']>;
  isModifiableInd?: Maybe<Scalars['Int']['output']>;
  nbsUid?: Maybe<Scalars['Int']['output']>;
  parentIsCd?: Maybe<Scalars['Int']['output']>;
  seqNum?: Maybe<Scalars['Int']['output']>;
  sourceConceptId?: Maybe<Scalars['String']['output']>;
  statusCd?: Maybe<Scalars['Int']['output']>;
  statusTime?: Maybe<Scalars['DateTime']['output']>;
};

export type StateCountyCodeValueResults = {
  __typename?: 'StateCountyCodeValueResults';
  content: Array<StateCountyCodeValue>;
  total: Scalars['Int']['output'];
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

export type TracedCondition = {
  __typename?: 'TracedCondition';
  description?: Maybe<Scalars['String']['output']>;
  id?: Maybe<Scalars['String']['output']>;
};

export type UpdateBirthAndGenderInput = {
  asOf: Scalars['DateTime']['input'];
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
  asOf: Scalars['DateTime']['input'];
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
  asOf: Scalars['DateTime']['input'];
  authority?: InputMaybe<Scalars['String']['input']>;
  patient: Scalars['Int']['input'];
  sequence: Scalars['Int']['input'];
  type: Scalars['String']['input'];
  value: Scalars['String']['input'];
};

export type UpdatePatientNameInput = {
  asOf?: InputMaybe<Scalars['DateTime']['input']>;
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
  asOf: Scalars['DateTime']['input'];
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

export type User = {
  __typename?: 'User';
  nedssEntryId: Scalars['ID']['output'];
  recordStatusCd?: Maybe<RecordStatus>;
  userFirstNm: Scalars['String']['output'];
  userId: Scalars['String']['output'];
  userLastNm: Scalars['String']['output'];
};

export type UserResults = {
  __typename?: 'UserResults';
  content: Array<Maybe<User>>;
  total: Scalars['Int']['output'];
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


export type AddPatientRaceMutation = { __typename?: 'Mutation', addPatientRace: { __typename?: 'PatientRaceChangeResult', patient: number } };

export type CreatePatientMutationVariables = Exact<{
  patient: PersonInput;
}>;


export type CreatePatientMutation = { __typename?: 'Mutation', createPatient: { __typename?: 'PatientCreatedResponse', id: number, shortId: number } };

export type DeletePatientMutationVariables = Exact<{
  patient: Scalars['ID']['input'];
}>;


export type DeletePatientMutation = { __typename?: 'Mutation', deletePatient: { __typename: 'PatientDeleteFailed', patient: number, reason: string } | { __typename: 'PatientDeleteSuccessful', patient: number } };

export type DeletePatientAddressMutationVariables = Exact<{
  input?: InputMaybe<DeletePatientAddressInput>;
}>;


export type DeletePatientAddressMutation = { __typename?: 'Mutation', deletePatientAddress: { __typename?: 'PatientAddressChangeResult', patient: number, id: number } };

export type DeletePatientEmailMutationVariables = Exact<{
  patientId: Scalars['Int']['input'];
  personSeqNum: Scalars['Int']['input'];
}>;


export type DeletePatientEmailMutation = { __typename?: 'Mutation', deletePatientEmail: { __typename?: 'PatientUpdateResponse', patientId: string } };

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


export type DeletePatientRaceMutation = { __typename?: 'Mutation', deletePatientRace: { __typename?: 'PatientRaceChangeResult', patient: number } };

export type UpdateEthnicityMutationVariables = Exact<{
  input: EthnicityInput;
}>;


export type UpdateEthnicityMutation = { __typename?: 'Mutation', updateEthnicity: { __typename?: 'PatientEthnicityChangeResult', patient: string } };

export type UpdatePatientAddressMutationVariables = Exact<{
  input: UpdatePatientAddressInput;
}>;


export type UpdatePatientAddressMutation = { __typename?: 'Mutation', updatePatientAddress: { __typename?: 'PatientAddressChangeResult', patient: number, id: number } };

export type UpdatePatientAdministrativeMutationVariables = Exact<{
  input: AdministrativeInput;
}>;


export type UpdatePatientAdministrativeMutation = { __typename?: 'Mutation', updatePatientAdministrative: { __typename?: 'PatientAdministrativeChangeResult', patient: number } };

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


export type UpdatePatientRaceMutation = { __typename?: 'Mutation', updatePatientRace: { __typename?: 'PatientRaceChangeResult', patient: number } };

export type AddressTypesQueryVariables = Exact<{ [key: string]: never; }>;


export type AddressTypesQuery = { __typename?: 'Query', addressTypes: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type AddressUsesQueryVariables = Exact<{ [key: string]: never; }>;


export type AddressUsesQuery = { __typename?: 'Query', addressUses: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type AssigningAuthoritiesQueryVariables = Exact<{ [key: string]: never; }>;


export type AssigningAuthoritiesQuery = { __typename?: 'Query', assigningAuthorities: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type CountiesQueryVariables = Exact<{
  state?: InputMaybe<Scalars['String']['input']>;
}>;


export type CountiesQuery = { __typename?: 'Query', counties: Array<{ __typename?: 'GroupedCodedValue', value: string, name: string, group: string }> };

export type CountriesQueryVariables = Exact<{ [key: string]: never; }>;


export type CountriesQuery = { __typename?: 'Query', countries: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type DegreesQueryVariables = Exact<{ [key: string]: never; }>;


export type DegreesQuery = { __typename?: 'Query', degrees: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type DetailedEthnicitiesQueryVariables = Exact<{ [key: string]: never; }>;


export type DetailedEthnicitiesQuery = { __typename?: 'Query', detailedEthnicities: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type DetailedRacesQueryVariables = Exact<{
  category?: InputMaybe<Scalars['String']['input']>;
}>;


export type DetailedRacesQuery = { __typename?: 'Query', detailedRaces: Array<{ __typename?: 'GroupedCodedValue', value: string, name: string, group: string }> };

export type EducationLevelsQueryVariables = Exact<{ [key: string]: never; }>;


export type EducationLevelsQuery = { __typename?: 'Query', educationLevels: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type EthnicGroupsQueryVariables = Exact<{ [key: string]: never; }>;


export type EthnicGroupsQuery = { __typename?: 'Query', ethnicGroups: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type EthnicityUnknownReasonsQueryVariables = Exact<{ [key: string]: never; }>;


export type EthnicityUnknownReasonsQuery = { __typename?: 'Query', ethnicityUnknownReasons: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type FindAllAddressTypesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllAddressTypesQuery = { __typename?: 'Query', findAllAddressTypes: { __typename?: 'AddressTypeResults', total: number, content: Array<{ __typename?: 'AddressType', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

export type FindAllAddressUsesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllAddressUsesQuery = { __typename?: 'Query', findAllAddressUses: { __typename?: 'AddressUseResults', total: number, content: Array<{ __typename?: 'AddressUse', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

export type FindAllAssigningAuthoritiesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllAssigningAuthoritiesQuery = { __typename?: 'Query', findAllAssigningAuthorities: { __typename?: 'AssigningAuthorResults', total: number, content: Array<{ __typename?: 'AssigningAuthor', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

export type FindAllConditionCodesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllConditionCodesQuery = { __typename?: 'Query', findAllConditionCodes: Array<{ __typename?: 'ConditionCode', id: string, conditionDescTxt?: string | null } | null> };

export type FindAllDegreesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllDegreesQuery = { __typename?: 'Query', findAllDegrees: { __typename?: 'DegreeResults', total: number, content: Array<{ __typename?: 'Degree', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

export type FindAllEthnicityValuesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllEthnicityValuesQuery = { __typename?: 'Query', findAllEthnicityValues: { __typename?: 'EthnicityResults', total: number, content: Array<{ __typename?: 'Ethnicity', codeDescTxt: string, id: { __typename?: 'EthnicityId', code: string } } | null> } };

export type FindAllIdentificationTypesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllIdentificationTypesQuery = { __typename?: 'Query', findAllIdentificationTypes: { __typename?: 'IdentificationTypesResults', total: number, content: Array<{ __typename?: 'IdentificationTypes', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

export type FindAllJurisdictionsQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllJurisdictionsQuery = { __typename?: 'Query', findAllJurisdictions: Array<{ __typename?: 'Jurisdiction', id: string, typeCd: string, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, codeDescTxt?: string | null, codeShortDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, indentLevelNbr?: number | null, isModifiableInd?: string | null, parentIsCd?: string | null, stateDomainCd?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, codeSeqNum?: number | null, nbsUid?: string | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null, exportInd?: string | null } | null> };

export type FindAllNamePrefixesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllNamePrefixesQuery = { __typename?: 'Query', findAllNamePrefixes: { __typename?: 'NamePrefixResults', total: number, content: Array<{ __typename?: 'NamePrefix', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

export type FindAllNameTypesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllNameTypesQuery = { __typename?: 'Query', findAllNameTypes: { __typename?: 'NameTypeResults', total: number, content: Array<{ __typename?: 'NameType', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

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

export type FindAllPhoneAndEmailTypeQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllPhoneAndEmailTypeQuery = { __typename?: 'Query', findAllPhoneAndEmailType: { __typename?: 'PhoneAndEmailTypeResults', total: number, content: Array<{ __typename?: 'PhoneAndEmailType', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

export type FindAllPhoneAndEmailUseQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllPhoneAndEmailUseQuery = { __typename?: 'Query', findAllPhoneAndEmailUse: { __typename?: 'PhoneAndEmailUseResults', total: number, content: Array<{ __typename?: 'PhoneAndEmailUse', codeShortDescTxt: string, id: { __typename?: 'CodeValueGeneralId', codeSetNm: string, code: string } }> } };

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

export type FindAllStateCountyCodeValuesQueryVariables = Exact<{
  stateCode?: InputMaybe<Scalars['String']['input']>;
  page?: InputMaybe<Page>;
}>;


export type FindAllStateCountyCodeValuesQuery = { __typename?: 'Query', findAllStateCountyCodeValues: Array<{ __typename?: 'StateCountyCodeValue', id: string, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, codeDescTxt?: string | null, codeShortDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, excludedTxt?: string | null, indentLevelNbr?: number | null, isModifiableInd?: number | null, parentIsCd?: number | null, statusCd?: number | null, statusTime?: any | null, codeSetNm?: string | null, seqNum?: number | null, nbsUid?: number | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null }> };

export type FindAllUsersQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllUsersQuery = { __typename?: 'Query', findAllUsers: { __typename?: 'UserResults', total: number, content: Array<{ __typename?: 'User', nedssEntryId: string, userId: string, userFirstNm: string, userLastNm: string, recordStatusCd?: RecordStatus | null } | null> } };

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


export type FindInvestigationsByFilterQuery = { __typename?: 'Query', findInvestigationsByFilter: { __typename?: 'InvestigationResults', total: number, content: Array<{ __typename?: 'Investigation', id?: string | null, recordStatus?: string | null, lastChangeTime?: any | null, publicHealthCaseUid?: number | null, caseClassCd?: string | null, outbreakName?: string | null, caseTypeCd?: string | null, cdDescTxt?: string | null, progAreaCd?: string | null, jurisdictionCd?: number | null, jurisdictionCodeDescTxt?: string | null, pregnantIndCd?: string | null, localId?: string | null, rptFormCmpltTime?: any | null, activityToTime?: any | null, activityFromTime?: any | null, addTime?: any | null, publicHealthCaseLastChgTime?: any | null, addUserId?: number | null, lastChangeUserId?: number | null, currProcessStateCd?: string | null, investigationStatusCd?: string | null, moodCd?: string | null, notificationLocalId?: string | null, notificationAddTime?: any | null, notificationRecordStatusCd?: string | null, notificationLastChgTime?: any | null, personParticipations?: Array<{ __typename?: 'PersonParticipation', actUid: number, localId?: string | null, typeCd?: string | null, entityId: number, subjectClassCd?: string | null, participationRecordStatus?: string | null, typeDescTxt?: string | null, participationLastChangeTime?: any | null, firstName?: string | null, lastName?: string | null, birthTime?: any | null, currSexCd?: string | null, personCd: string, personParentUid?: number | null, personRecordStatus: string, personLastChangeTime?: any | null, shortId?: number | null } | null> | null, organizationParticipations?: Array<{ __typename?: 'OrganizationParticipation', actUid?: number | null, typeCd?: string | null, entityId?: number | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, name?: string | null, organizationLastChangeTime?: any | null } | null> | null, actIds?: Array<{ __typename?: 'ActId', id?: number | null, recordStatus?: string | null, actIdSeq?: number | null, rootExtensionTxt?: string | null, typeCd?: string | null, lastChangeTime?: any | null } | null> | null } | null> } };

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


export type FindLabReportsByFilterQuery = { __typename?: 'Query', findLabReportsByFilter: { __typename?: 'LabReportResults', total: number, content: Array<{ __typename?: 'LabReport', id?: string | null, observationUid?: number | null, lastChange?: any | null, classCd?: string | null, moodCd?: string | null, observationLastChgTime?: any | null, cdDescTxt?: string | null, recordStatusCd?: string | null, programAreaCd?: string | null, jurisdictionCd?: number | null, jurisdictionCodeDescTxt?: string | null, pregnantIndCd?: string | null, localId?: string | null, activityToTime?: any | null, effectiveFromTime?: any | null, rptToStateTime?: any | null, addTime?: any | null, electronicInd?: string | null, versionCtrlNbr?: number | null, addUserId?: number | null, lastChgUserId?: number | null, personParticipations?: Array<{ __typename?: 'PersonParticipation', actUid: number, localId?: string | null, typeCd?: string | null, entityId: number, subjectClassCd?: string | null, participationRecordStatus?: string | null, typeDescTxt?: string | null, participationLastChangeTime?: any | null, firstName?: string | null, lastName?: string | null, birthTime?: any | null, currSexCd?: string | null, personCd: string, personParentUid?: number | null, personRecordStatus: string, personLastChangeTime?: any | null, shortId?: number | null } | null> | null, organizationParticipations?: Array<{ __typename?: 'OrganizationParticipation', actUid?: number | null, typeCd?: string | null, entityId?: number | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, name?: string | null, organizationLastChangeTime?: any | null } | null> | null, materialParticipations?: Array<{ __typename?: 'MaterialParticipation', actUid?: number | null, typeCd?: string | null, entityId?: string | null, subjectClassCd?: string | null, typeDescTxt?: string | null, participationRecordStatus?: string | null, participationLastChangeTime?: any | null, cd?: string | null, cdDescTxt?: string | null } | null> | null, observations?: Array<{ __typename?: 'Observation', cd?: string | null, cdDescTxt?: string | null, domainCd?: string | null, statusCd?: string | null, altCd?: string | null, altDescTxt?: string | null, altCdSystemCd?: string | null, displayName?: string | null, ovcCode?: string | null, ovcAltCode?: string | null, ovcAltDescTxt?: string | null, ovcAltCdSystemCd?: string | null } | null> | null, actIds?: Array<{ __typename?: 'ActId', id?: number | null, recordStatus?: string | null, actIdSeq?: number | null, rootExtensionTxt?: string | null, typeCd?: string | null, lastChangeTime?: any | null } | null> | null, associatedInvestigations?: Array<{ __typename?: 'AssociatedInvestigation', publicHealthCaseUid?: number | null, cdDescTxt?: string | null, localId?: string | null, lastChgTime?: any | null, actRelationshipLastChgTime?: any | null } | null> | null } | null> } };

export type FindMorbidityReportsForPatientQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindMorbidityReportsForPatientQuery = { __typename?: 'Query', findMorbidityReportsForPatient?: { __typename?: 'PatientMorbidityResults', total: number, number: number, content: Array<{ __typename?: 'PatientMorbidity', morbidity: string, receivedOn: any, provider?: string | null, reportedOn: any, condition: string, jurisdiction: string, event: string, treatments: Array<string | null>, associatedWith?: { __typename?: 'PatientMorbidityInvestigation', id: string, local: string, condition: string } | null, labResults: Array<{ __typename?: 'PatientMorbidityLabResult', labTest: string, status?: string | null, codedResult?: string | null, numericResult?: string | null, textResult?: string | null } | null> } | null> } | null };

export type FindNameSuffixesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindNameSuffixesQuery = { __typename?: 'Query', findNameSuffixes: { __typename?: 'KeyValuePairResults', total: number, content: Array<{ __typename?: 'KeyValuePair', key: string, value: string }> } };

export type FindOrganizationByIdQueryVariables = Exact<{
  id: Scalars['ID']['input'];
}>;


export type FindOrganizationByIdQuery = { __typename?: 'Query', findOrganizationById?: { __typename?: 'Organization', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, standardIndustryClassCd?: string | null, standardIndustryDescTxt?: string | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, displayNm?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, zipCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null, electronicInd?: string | null, edxInd?: string | null } | null };

export type FindOrganizationsByFilterQueryVariables = Exact<{
  filter: OrganizationFilter;
  page?: InputMaybe<Page>;
}>;


export type FindOrganizationsByFilterQuery = { __typename?: 'Query', findOrganizationsByFilter: { __typename?: 'OrganizationResults', total: number, content: Array<{ __typename?: 'Organization', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, recordStatusCd?: RecordStatus | null, recordStatusTime?: any | null, standardIndustryClassCd?: string | null, standardIndustryDescTxt?: string | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, displayNm?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, zipCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null, electronicInd?: string | null, edxInd?: string | null } | null> } };

export type FindPatientNamedByContactQueryVariables = Exact<{
  patient: Scalars['ID']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindPatientNamedByContactQuery = { __typename?: 'Query', findPatientNamedByContact?: { __typename?: 'PatientNamedByContactResults', total: number, number: number, content: Array<{ __typename?: 'NamedByPatient', contactRecord: string, createdOn: any, namedOn: any, priority?: string | null, disposition?: string | null, event: string, condition: { __typename?: 'TracedCondition', id?: string | null, description?: string | null }, contact: { __typename?: 'NamedContact', id: string, name: string }, associatedWith?: { __typename?: 'PatientContactInvestigation', id: string, local: string, condition: string } | null } | null> } | null };

export type FindPatientProfileQueryVariables = Exact<{
  asOf?: InputMaybe<Scalars['DateTime']['input']>;
  page?: InputMaybe<Page>;
  page1?: InputMaybe<Page>;
  page2?: InputMaybe<Page>;
  page3?: InputMaybe<Page>;
  page4?: InputMaybe<Page>;
  page5?: InputMaybe<Page>;
  patient?: InputMaybe<Scalars['ID']['input']>;
  shortId?: InputMaybe<Scalars['Int']['input']>;
}>;


export type FindPatientProfileQuery = { __typename?: 'Query', findPatientProfile?: { __typename?: 'PatientProfile', id: string, local: string, shortId: number, version: number, status: string, deletable: boolean, summary?: { __typename?: 'PatientSummary', birthday?: any | null, age?: number | null, gender?: string | null, ethnicity?: string | null, races?: Array<string> | null, legalName?: { __typename?: 'PatientLegalName', prefix?: string | null, first?: string | null, middle?: string | null, last?: string | null, suffix?: string | null } | null, home?: { __typename?: 'PatientSummaryAddress', use: string, address?: string | null, address2?: string | null, city?: string | null, state?: string | null, zipcode?: string | null, country?: string | null } | null, address: Array<{ __typename?: 'PatientSummaryAddress', use: string, address?: string | null, address2?: string | null, city?: string | null, state?: string | null, zipcode?: string | null, country?: string | null }>, identification?: Array<{ __typename?: 'PatientSummaryIdentification', type: string, value: string }> | null, phone?: Array<{ __typename?: 'PatientSummaryPhone', use?: string | null, number?: string | null }> | null, email?: Array<{ __typename?: 'PatientSummaryEmail', use?: string | null, address?: string | null }> | null } | null, names: { __typename?: 'PatientNameResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientName', patient: number, version: number, asOf: any, sequence: number, first?: string | null, middle?: string | null, secondMiddle?: string | null, last?: string | null, secondLast?: string | null, use: { __typename?: 'PatientNameUse', id: string, description: string }, prefix?: { __typename?: 'PatientNamePrefix', id: string, description: string } | null, suffix?: { __typename?: 'PatientNameSuffix', id: string, description: string } | null, degree?: { __typename?: 'PatientNameDegree', id: string, description: string } | null }> }, administrative: { __typename?: 'PatientAdministrativeResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientAdministrative', patient: string, id: string, version: number, asOf: any, comment?: string | null }> }, addresses: { __typename?: 'PatientAddressResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientAddress', patient: number, id: string, version: number, asOf: any, address1?: string | null, address2?: string | null, city?: string | null, zipcode?: string | null, censusTract?: string | null, comment?: string | null, type: { __typename?: 'PatientAddressType', id: string, description: string }, county?: { __typename?: 'PatientCounty', id: string, description: string } | null, state?: { __typename?: 'PatientState', id: string, description: string } | null, country?: { __typename?: 'PatientCountry', id: string, description: string } | null }> }, phones: { __typename?: 'PatientPhoneResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientPhone', patient: number, id: string, version: number, asOf: any, countryCode?: string | null, number?: string | null, extension?: string | null, email?: string | null, url?: string | null, comment?: string | null }> }, identification: { __typename?: 'PatientIdentificationResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientIdentification', patient: number, sequence: number, version: number, asOf: any, value?: string | null, authority?: { __typename?: 'PatientIdentificationAuthority', id: string, description: string } | null }> }, races: { __typename?: 'PatientRaceResults', total: number, number: number, size: number, content: Array<{ __typename?: 'PatientRace', patient: number, id: number, version: number, asOf: any, category: { __typename?: 'PatientRaceCategory', id: string, description: string }, detailed: Array<{ __typename?: 'PatientRaceDetail', id: string, description: string }> }> }, birth?: { __typename?: 'PatientBirth', patient: number, id: string, version: number, asOf: any, bornOn?: any | null, age?: number | null, birthOrder?: number | null, city?: string | null, multipleBirth?: { __typename?: 'PatientIndicatorCodedValue', id: string, description: string } | null, state?: { __typename?: 'PatientState', id: string, description: string } | null, county?: { __typename?: 'PatientCounty', id: string, description: string } | null, country?: { __typename?: 'PatientCountry', id: string, description: string } | null } | null, gender?: { __typename?: 'PatientGender', patient: number, id: string, version: number, asOf: any, additional?: string | null, birth?: { __typename?: 'PatientGenderCodedValue', id: string, description: string } | null, current?: { __typename?: 'PatientGenderCodedValue', id: string, description: string } | null, unknownReason?: { __typename?: 'PatientGenderUnknownReason', id: string, description: string } | null, preferred?: { __typename?: 'PatientPreferredGender', id: string, description: string } | null } | null, mortality?: { __typename?: 'PatientMortality', patient: number, id: string, version: number, asOf: any, deceasedOn?: any | null, city?: string | null, deceased?: { __typename?: 'PatientIndicatorCodedValue', id: string, description: string } | null, state?: { __typename?: 'PatientState', id: string, description: string } | null, county?: { __typename?: 'PatientCounty', id: string, description: string } | null, country?: { __typename?: 'PatientCountry', id: string, description: string } | null } | null, general?: { __typename?: 'PatientGeneral', patient: number, id: string, version: number, asOf: any, maternalMaidenName?: string | null, adultsInHouse?: number | null, childrenInHouse?: number | null, stateHIVCase?: string | null, maritalStatus?: { __typename?: 'PatientMaritalStatus', id: string, description: string } | null, occupation?: { __typename?: 'PatientOccupation', id: string, description: string } | null, educationLevel?: { __typename?: 'PatientEducationLevel', id: string, description: string } | null, primaryLanguage?: { __typename?: 'PatientPrimaryLanguage', id: string, description: string } | null, speaksEnglish?: { __typename?: 'PatientIndicatorCodedValue', id: string, description: string } | null } | null, ethnicity?: { __typename?: 'PatientEthnicity', patient: number, id: string, version: number, asOf: any, ethnicGroup: { __typename?: 'PatientEthnicGroup', id: string, description: string }, unknownReason?: { __typename?: 'PatientEthnicityUnknownReason', id: string, description: string } | null, detailed: Array<{ __typename?: 'PatientDetailedEthnicity', id: string, description: string }> } | null } | null };

export type FindPatientsByFilterQueryVariables = Exact<{
  filter: PersonFilter;
  page?: InputMaybe<SortablePage>;
}>;


export type FindPatientsByFilterQuery = { __typename?: 'Query', findPatientsByFilter: { __typename?: 'PatientSearchResults', total: number, content: Array<{ __typename?: 'PatientSearchResult', patient: number, birthday?: any | null, age?: number | null, gender?: string | null, status: string, shortId: number, phones: Array<string>, emails: Array<string>, legalName?: { __typename?: 'PatientSearchResultName', first?: string | null, middle?: string | null, last?: string | null, suffix?: string | null } | null, names: Array<{ __typename?: 'PatientSearchResultName', first?: string | null, middle?: string | null, last?: string | null, suffix?: string | null }>, identification: Array<{ __typename?: 'PatientSearchResultIdentification', type: string, value: string }>, addresses: Array<{ __typename?: 'PatientSearchResultAddress', use: string, address?: string | null, address2?: string | null, city?: string | null, state?: string | null, zipcode?: string | null }> }> } };

export type FindPlaceByIdQueryVariables = Exact<{
  id: Scalars['ID']['input'];
}>;


export type FindPlaceByIdQuery = { __typename?: 'Query', findPlaceById?: { __typename?: 'Place', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: number | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, nm?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, zipCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null } | null };

export type FindPlacesByFilterQueryVariables = Exact<{
  filter: PlaceFilter;
  page?: InputMaybe<Page>;
}>;


export type FindPlacesByFilterQuery = { __typename?: 'Query', findPlacesByFilter: Array<{ __typename?: 'Place', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: number | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, nm?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, zipCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null } | null> };

export type FindSnomedCodedResultsQueryVariables = Exact<{
  searchText: Scalars['String']['input'];
  page?: InputMaybe<Page>;
}>;


export type FindSnomedCodedResultsQuery = { __typename?: 'Query', findSnomedCodedResults: { __typename?: 'SnomedCodedResults', total: number, content: Array<{ __typename?: 'SnomedCode', id?: string | null, snomedDescTxt?: string | null } | null> } };

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

export type GenderUnknownReasonsQueryVariables = Exact<{ [key: string]: never; }>;


export type GenderUnknownReasonsQuery = { __typename?: 'Query', genderUnknownReasons: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type GendersQueryVariables = Exact<{ [key: string]: never; }>;


export type GendersQuery = { __typename?: 'Query', genders: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type IdentificationTypesQueryVariables = Exact<{ [key: string]: never; }>;


export type IdentificationTypesQuery = { __typename?: 'Query', identificationTypes: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type MaritalStatusesQueryVariables = Exact<{ [key: string]: never; }>;


export type MaritalStatusesQuery = { __typename?: 'Query', maritalStatuses: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type NameTypesQueryVariables = Exact<{ [key: string]: never; }>;


export type NameTypesQuery = { __typename?: 'Query', nameTypes: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type PhoneTypesQueryVariables = Exact<{ [key: string]: never; }>;


export type PhoneTypesQuery = { __typename?: 'Query', phoneTypes: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type PhoneUsesQueryVariables = Exact<{ [key: string]: never; }>;


export type PhoneUsesQuery = { __typename?: 'Query', phoneUses: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type PreferredGendersQueryVariables = Exact<{ [key: string]: never; }>;


export type PreferredGendersQuery = { __typename?: 'Query', preferredGenders: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type PrefixesQueryVariables = Exact<{ [key: string]: never; }>;


export type PrefixesQuery = { __typename?: 'Query', prefixes: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type PrimaryLanguagesQueryVariables = Exact<{ [key: string]: never; }>;


export type PrimaryLanguagesQuery = { __typename?: 'Query', primaryLanguages: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type PrimaryOccupationsQueryVariables = Exact<{ [key: string]: never; }>;


export type PrimaryOccupationsQuery = { __typename?: 'Query', primaryOccupations: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type RaceCategoriesQueryVariables = Exact<{ [key: string]: never; }>;


export type RaceCategoriesQuery = { __typename?: 'Query', raceCategories: Array<{ __typename?: 'CodedValue', value: string, name: string }> };

export type StatesQueryVariables = Exact<{ [key: string]: never; }>;


export type StatesQuery = { __typename?: 'Query', states: Array<{ __typename?: 'StateCodedValue', value: string, name: string, abbreviation: string }> };

export type SuffixesQueryVariables = Exact<{ [key: string]: never; }>;


export type SuffixesQuery = { __typename?: 'Query', suffixes: Array<{ __typename?: 'CodedValue', value: string, name: string }> };


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
    patient
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
export const CreatePatientDocument = gql`
    mutation createPatient($patient: PersonInput!) {
  createPatient(patient: $patient) {
    id
    shortId
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
    mutation deletePatient($patient: ID!) {
  deletePatient(patient: $patient) {
    __typename
    ... on PatientDeleteSuccessful {
      patient
    }
    ... on PatientDeleteFailed {
      patient
      reason
    }
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
 *      patient: // value for 'patient'
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
export const DeletePatientEmailDocument = gql`
    mutation deletePatientEmail($patientId: Int!, $personSeqNum: Int!) {
  deletePatientEmail(patientId: $patientId, personSeqNum: $personSeqNum) {
    patientId
  }
}
    `;
export type DeletePatientEmailMutationFn = Apollo.MutationFunction<DeletePatientEmailMutation, DeletePatientEmailMutationVariables>;

/**
 * __useDeletePatientEmailMutation__
 *
 * To run a mutation, you first call `useDeletePatientEmailMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useDeletePatientEmailMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [deletePatientEmailMutation, { data, loading, error }] = useDeletePatientEmailMutation({
 *   variables: {
 *      patientId: // value for 'patientId'
 *      personSeqNum: // value for 'personSeqNum'
 *   },
 * });
 */
export function useDeletePatientEmailMutation(baseOptions?: Apollo.MutationHookOptions<DeletePatientEmailMutation, DeletePatientEmailMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<DeletePatientEmailMutation, DeletePatientEmailMutationVariables>(DeletePatientEmailDocument, options);
      }
export type DeletePatientEmailMutationHookResult = ReturnType<typeof useDeletePatientEmailMutation>;
export type DeletePatientEmailMutationResult = Apollo.MutationResult<DeletePatientEmailMutation>;
export type DeletePatientEmailMutationOptions = Apollo.BaseMutationOptions<DeletePatientEmailMutation, DeletePatientEmailMutationVariables>;
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
export const UpdatePatientAdministrativeDocument = gql`
    mutation updatePatientAdministrative($input: AdministrativeInput!) {
  updatePatientAdministrative(input: $input) {
    patient
  }
}
    `;
export type UpdatePatientAdministrativeMutationFn = Apollo.MutationFunction<UpdatePatientAdministrativeMutation, UpdatePatientAdministrativeMutationVariables>;

/**
 * __useUpdatePatientAdministrativeMutation__
 *
 * To run a mutation, you first call `useUpdatePatientAdministrativeMutation` within a React component and pass it any options that fit your needs.
 * When your component renders, `useUpdatePatientAdministrativeMutation` returns a tuple that includes:
 * - A mutate function that you can call at any time to execute the mutation
 * - An object with fields that represent the current status of the mutation's execution
 *
 * @param baseOptions options that will be passed into the mutation, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options-2;
 *
 * @example
 * const [updatePatientAdministrativeMutation, { data, loading, error }] = useUpdatePatientAdministrativeMutation({
 *   variables: {
 *      input: // value for 'input'
 *   },
 * });
 */
export function useUpdatePatientAdministrativeMutation(baseOptions?: Apollo.MutationHookOptions<UpdatePatientAdministrativeMutation, UpdatePatientAdministrativeMutationVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useMutation<UpdatePatientAdministrativeMutation, UpdatePatientAdministrativeMutationVariables>(UpdatePatientAdministrativeDocument, options);
      }
export type UpdatePatientAdministrativeMutationHookResult = ReturnType<typeof useUpdatePatientAdministrativeMutation>;
export type UpdatePatientAdministrativeMutationResult = Apollo.MutationResult<UpdatePatientAdministrativeMutation>;
export type UpdatePatientAdministrativeMutationOptions = Apollo.BaseMutationOptions<UpdatePatientAdministrativeMutation, UpdatePatientAdministrativeMutationVariables>;
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
export const AddressTypesDocument = gql`
    query addressTypes {
  addressTypes {
    value
    name
  }
}
    `;

/**
 * __useAddressTypesQuery__
 *
 * To run a query within a React component, call `useAddressTypesQuery` and pass it any options that fit your needs.
 * When your component renders, `useAddressTypesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useAddressTypesQuery({
 *   variables: {
 *   },
 * });
 */
export function useAddressTypesQuery(baseOptions?: Apollo.QueryHookOptions<AddressTypesQuery, AddressTypesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<AddressTypesQuery, AddressTypesQueryVariables>(AddressTypesDocument, options);
      }
export function useAddressTypesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<AddressTypesQuery, AddressTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<AddressTypesQuery, AddressTypesQueryVariables>(AddressTypesDocument, options);
        }
export function useAddressTypesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<AddressTypesQuery, AddressTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<AddressTypesQuery, AddressTypesQueryVariables>(AddressTypesDocument, options);
        }
export type AddressTypesQueryHookResult = ReturnType<typeof useAddressTypesQuery>;
export type AddressTypesLazyQueryHookResult = ReturnType<typeof useAddressTypesLazyQuery>;
export type AddressTypesSuspenseQueryHookResult = ReturnType<typeof useAddressTypesSuspenseQuery>;
export type AddressTypesQueryResult = Apollo.QueryResult<AddressTypesQuery, AddressTypesQueryVariables>;
export const AddressUsesDocument = gql`
    query addressUses {
  addressUses {
    value
    name
  }
}
    `;

/**
 * __useAddressUsesQuery__
 *
 * To run a query within a React component, call `useAddressUsesQuery` and pass it any options that fit your needs.
 * When your component renders, `useAddressUsesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useAddressUsesQuery({
 *   variables: {
 *   },
 * });
 */
export function useAddressUsesQuery(baseOptions?: Apollo.QueryHookOptions<AddressUsesQuery, AddressUsesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<AddressUsesQuery, AddressUsesQueryVariables>(AddressUsesDocument, options);
      }
export function useAddressUsesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<AddressUsesQuery, AddressUsesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<AddressUsesQuery, AddressUsesQueryVariables>(AddressUsesDocument, options);
        }
export function useAddressUsesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<AddressUsesQuery, AddressUsesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<AddressUsesQuery, AddressUsesQueryVariables>(AddressUsesDocument, options);
        }
export type AddressUsesQueryHookResult = ReturnType<typeof useAddressUsesQuery>;
export type AddressUsesLazyQueryHookResult = ReturnType<typeof useAddressUsesLazyQuery>;
export type AddressUsesSuspenseQueryHookResult = ReturnType<typeof useAddressUsesSuspenseQuery>;
export type AddressUsesQueryResult = Apollo.QueryResult<AddressUsesQuery, AddressUsesQueryVariables>;
export const AssigningAuthoritiesDocument = gql`
    query assigningAuthorities {
  assigningAuthorities {
    value
    name
  }
}
    `;

/**
 * __useAssigningAuthoritiesQuery__
 *
 * To run a query within a React component, call `useAssigningAuthoritiesQuery` and pass it any options that fit your needs.
 * When your component renders, `useAssigningAuthoritiesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useAssigningAuthoritiesQuery({
 *   variables: {
 *   },
 * });
 */
export function useAssigningAuthoritiesQuery(baseOptions?: Apollo.QueryHookOptions<AssigningAuthoritiesQuery, AssigningAuthoritiesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<AssigningAuthoritiesQuery, AssigningAuthoritiesQueryVariables>(AssigningAuthoritiesDocument, options);
      }
export function useAssigningAuthoritiesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<AssigningAuthoritiesQuery, AssigningAuthoritiesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<AssigningAuthoritiesQuery, AssigningAuthoritiesQueryVariables>(AssigningAuthoritiesDocument, options);
        }
export function useAssigningAuthoritiesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<AssigningAuthoritiesQuery, AssigningAuthoritiesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<AssigningAuthoritiesQuery, AssigningAuthoritiesQueryVariables>(AssigningAuthoritiesDocument, options);
        }
export type AssigningAuthoritiesQueryHookResult = ReturnType<typeof useAssigningAuthoritiesQuery>;
export type AssigningAuthoritiesLazyQueryHookResult = ReturnType<typeof useAssigningAuthoritiesLazyQuery>;
export type AssigningAuthoritiesSuspenseQueryHookResult = ReturnType<typeof useAssigningAuthoritiesSuspenseQuery>;
export type AssigningAuthoritiesQueryResult = Apollo.QueryResult<AssigningAuthoritiesQuery, AssigningAuthoritiesQueryVariables>;
export const CountiesDocument = gql`
    query counties($state: String) {
  counties(state: $state) {
    value
    name
    group
  }
}
    `;

/**
 * __useCountiesQuery__
 *
 * To run a query within a React component, call `useCountiesQuery` and pass it any options that fit your needs.
 * When your component renders, `useCountiesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useCountiesQuery({
 *   variables: {
 *      state: // value for 'state'
 *   },
 * });
 */
export function useCountiesQuery(baseOptions?: Apollo.QueryHookOptions<CountiesQuery, CountiesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<CountiesQuery, CountiesQueryVariables>(CountiesDocument, options);
      }
export function useCountiesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<CountiesQuery, CountiesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<CountiesQuery, CountiesQueryVariables>(CountiesDocument, options);
        }
export function useCountiesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<CountiesQuery, CountiesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<CountiesQuery, CountiesQueryVariables>(CountiesDocument, options);
        }
export type CountiesQueryHookResult = ReturnType<typeof useCountiesQuery>;
export type CountiesLazyQueryHookResult = ReturnType<typeof useCountiesLazyQuery>;
export type CountiesSuspenseQueryHookResult = ReturnType<typeof useCountiesSuspenseQuery>;
export type CountiesQueryResult = Apollo.QueryResult<CountiesQuery, CountiesQueryVariables>;
export const CountriesDocument = gql`
    query countries {
  countries {
    value
    name
  }
}
    `;

/**
 * __useCountriesQuery__
 *
 * To run a query within a React component, call `useCountriesQuery` and pass it any options that fit your needs.
 * When your component renders, `useCountriesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useCountriesQuery({
 *   variables: {
 *   },
 * });
 */
export function useCountriesQuery(baseOptions?: Apollo.QueryHookOptions<CountriesQuery, CountriesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<CountriesQuery, CountriesQueryVariables>(CountriesDocument, options);
      }
export function useCountriesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<CountriesQuery, CountriesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<CountriesQuery, CountriesQueryVariables>(CountriesDocument, options);
        }
export function useCountriesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<CountriesQuery, CountriesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<CountriesQuery, CountriesQueryVariables>(CountriesDocument, options);
        }
export type CountriesQueryHookResult = ReturnType<typeof useCountriesQuery>;
export type CountriesLazyQueryHookResult = ReturnType<typeof useCountriesLazyQuery>;
export type CountriesSuspenseQueryHookResult = ReturnType<typeof useCountriesSuspenseQuery>;
export type CountriesQueryResult = Apollo.QueryResult<CountriesQuery, CountriesQueryVariables>;
export const DegreesDocument = gql`
    query degrees {
  degrees {
    value
    name
  }
}
    `;

/**
 * __useDegreesQuery__
 *
 * To run a query within a React component, call `useDegreesQuery` and pass it any options that fit your needs.
 * When your component renders, `useDegreesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useDegreesQuery({
 *   variables: {
 *   },
 * });
 */
export function useDegreesQuery(baseOptions?: Apollo.QueryHookOptions<DegreesQuery, DegreesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<DegreesQuery, DegreesQueryVariables>(DegreesDocument, options);
      }
export function useDegreesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<DegreesQuery, DegreesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<DegreesQuery, DegreesQueryVariables>(DegreesDocument, options);
        }
export function useDegreesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<DegreesQuery, DegreesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<DegreesQuery, DegreesQueryVariables>(DegreesDocument, options);
        }
export type DegreesQueryHookResult = ReturnType<typeof useDegreesQuery>;
export type DegreesLazyQueryHookResult = ReturnType<typeof useDegreesLazyQuery>;
export type DegreesSuspenseQueryHookResult = ReturnType<typeof useDegreesSuspenseQuery>;
export type DegreesQueryResult = Apollo.QueryResult<DegreesQuery, DegreesQueryVariables>;
export const DetailedEthnicitiesDocument = gql`
    query detailedEthnicities {
  detailedEthnicities {
    value
    name
  }
}
    `;

/**
 * __useDetailedEthnicitiesQuery__
 *
 * To run a query within a React component, call `useDetailedEthnicitiesQuery` and pass it any options that fit your needs.
 * When your component renders, `useDetailedEthnicitiesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useDetailedEthnicitiesQuery({
 *   variables: {
 *   },
 * });
 */
export function useDetailedEthnicitiesQuery(baseOptions?: Apollo.QueryHookOptions<DetailedEthnicitiesQuery, DetailedEthnicitiesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<DetailedEthnicitiesQuery, DetailedEthnicitiesQueryVariables>(DetailedEthnicitiesDocument, options);
      }
export function useDetailedEthnicitiesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<DetailedEthnicitiesQuery, DetailedEthnicitiesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<DetailedEthnicitiesQuery, DetailedEthnicitiesQueryVariables>(DetailedEthnicitiesDocument, options);
        }
export function useDetailedEthnicitiesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<DetailedEthnicitiesQuery, DetailedEthnicitiesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<DetailedEthnicitiesQuery, DetailedEthnicitiesQueryVariables>(DetailedEthnicitiesDocument, options);
        }
export type DetailedEthnicitiesQueryHookResult = ReturnType<typeof useDetailedEthnicitiesQuery>;
export type DetailedEthnicitiesLazyQueryHookResult = ReturnType<typeof useDetailedEthnicitiesLazyQuery>;
export type DetailedEthnicitiesSuspenseQueryHookResult = ReturnType<typeof useDetailedEthnicitiesSuspenseQuery>;
export type DetailedEthnicitiesQueryResult = Apollo.QueryResult<DetailedEthnicitiesQuery, DetailedEthnicitiesQueryVariables>;
export const DetailedRacesDocument = gql`
    query detailedRaces($category: String) {
  detailedRaces(category: $category) {
    value
    name
    group
  }
}
    `;

/**
 * __useDetailedRacesQuery__
 *
 * To run a query within a React component, call `useDetailedRacesQuery` and pass it any options that fit your needs.
 * When your component renders, `useDetailedRacesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useDetailedRacesQuery({
 *   variables: {
 *      category: // value for 'category'
 *   },
 * });
 */
export function useDetailedRacesQuery(baseOptions?: Apollo.QueryHookOptions<DetailedRacesQuery, DetailedRacesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<DetailedRacesQuery, DetailedRacesQueryVariables>(DetailedRacesDocument, options);
      }
export function useDetailedRacesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<DetailedRacesQuery, DetailedRacesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<DetailedRacesQuery, DetailedRacesQueryVariables>(DetailedRacesDocument, options);
        }
export function useDetailedRacesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<DetailedRacesQuery, DetailedRacesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<DetailedRacesQuery, DetailedRacesQueryVariables>(DetailedRacesDocument, options);
        }
export type DetailedRacesQueryHookResult = ReturnType<typeof useDetailedRacesQuery>;
export type DetailedRacesLazyQueryHookResult = ReturnType<typeof useDetailedRacesLazyQuery>;
export type DetailedRacesSuspenseQueryHookResult = ReturnType<typeof useDetailedRacesSuspenseQuery>;
export type DetailedRacesQueryResult = Apollo.QueryResult<DetailedRacesQuery, DetailedRacesQueryVariables>;
export const EducationLevelsDocument = gql`
    query educationLevels {
  educationLevels {
    value
    name
  }
}
    `;

/**
 * __useEducationLevelsQuery__
 *
 * To run a query within a React component, call `useEducationLevelsQuery` and pass it any options that fit your needs.
 * When your component renders, `useEducationLevelsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useEducationLevelsQuery({
 *   variables: {
 *   },
 * });
 */
export function useEducationLevelsQuery(baseOptions?: Apollo.QueryHookOptions<EducationLevelsQuery, EducationLevelsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<EducationLevelsQuery, EducationLevelsQueryVariables>(EducationLevelsDocument, options);
      }
export function useEducationLevelsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<EducationLevelsQuery, EducationLevelsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<EducationLevelsQuery, EducationLevelsQueryVariables>(EducationLevelsDocument, options);
        }
export function useEducationLevelsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<EducationLevelsQuery, EducationLevelsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<EducationLevelsQuery, EducationLevelsQueryVariables>(EducationLevelsDocument, options);
        }
export type EducationLevelsQueryHookResult = ReturnType<typeof useEducationLevelsQuery>;
export type EducationLevelsLazyQueryHookResult = ReturnType<typeof useEducationLevelsLazyQuery>;
export type EducationLevelsSuspenseQueryHookResult = ReturnType<typeof useEducationLevelsSuspenseQuery>;
export type EducationLevelsQueryResult = Apollo.QueryResult<EducationLevelsQuery, EducationLevelsQueryVariables>;
export const EthnicGroupsDocument = gql`
    query ethnicGroups {
  ethnicGroups {
    value
    name
  }
}
    `;

/**
 * __useEthnicGroupsQuery__
 *
 * To run a query within a React component, call `useEthnicGroupsQuery` and pass it any options that fit your needs.
 * When your component renders, `useEthnicGroupsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useEthnicGroupsQuery({
 *   variables: {
 *   },
 * });
 */
export function useEthnicGroupsQuery(baseOptions?: Apollo.QueryHookOptions<EthnicGroupsQuery, EthnicGroupsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<EthnicGroupsQuery, EthnicGroupsQueryVariables>(EthnicGroupsDocument, options);
      }
export function useEthnicGroupsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<EthnicGroupsQuery, EthnicGroupsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<EthnicGroupsQuery, EthnicGroupsQueryVariables>(EthnicGroupsDocument, options);
        }
export function useEthnicGroupsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<EthnicGroupsQuery, EthnicGroupsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<EthnicGroupsQuery, EthnicGroupsQueryVariables>(EthnicGroupsDocument, options);
        }
export type EthnicGroupsQueryHookResult = ReturnType<typeof useEthnicGroupsQuery>;
export type EthnicGroupsLazyQueryHookResult = ReturnType<typeof useEthnicGroupsLazyQuery>;
export type EthnicGroupsSuspenseQueryHookResult = ReturnType<typeof useEthnicGroupsSuspenseQuery>;
export type EthnicGroupsQueryResult = Apollo.QueryResult<EthnicGroupsQuery, EthnicGroupsQueryVariables>;
export const EthnicityUnknownReasonsDocument = gql`
    query ethnicityUnknownReasons {
  ethnicityUnknownReasons {
    value
    name
  }
}
    `;

/**
 * __useEthnicityUnknownReasonsQuery__
 *
 * To run a query within a React component, call `useEthnicityUnknownReasonsQuery` and pass it any options that fit your needs.
 * When your component renders, `useEthnicityUnknownReasonsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useEthnicityUnknownReasonsQuery({
 *   variables: {
 *   },
 * });
 */
export function useEthnicityUnknownReasonsQuery(baseOptions?: Apollo.QueryHookOptions<EthnicityUnknownReasonsQuery, EthnicityUnknownReasonsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<EthnicityUnknownReasonsQuery, EthnicityUnknownReasonsQueryVariables>(EthnicityUnknownReasonsDocument, options);
      }
export function useEthnicityUnknownReasonsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<EthnicityUnknownReasonsQuery, EthnicityUnknownReasonsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<EthnicityUnknownReasonsQuery, EthnicityUnknownReasonsQueryVariables>(EthnicityUnknownReasonsDocument, options);
        }
export function useEthnicityUnknownReasonsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<EthnicityUnknownReasonsQuery, EthnicityUnknownReasonsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<EthnicityUnknownReasonsQuery, EthnicityUnknownReasonsQueryVariables>(EthnicityUnknownReasonsDocument, options);
        }
export type EthnicityUnknownReasonsQueryHookResult = ReturnType<typeof useEthnicityUnknownReasonsQuery>;
export type EthnicityUnknownReasonsLazyQueryHookResult = ReturnType<typeof useEthnicityUnknownReasonsLazyQuery>;
export type EthnicityUnknownReasonsSuspenseQueryHookResult = ReturnType<typeof useEthnicityUnknownReasonsSuspenseQuery>;
export type EthnicityUnknownReasonsQueryResult = Apollo.QueryResult<EthnicityUnknownReasonsQuery, EthnicityUnknownReasonsQueryVariables>;
export const FindAllAddressTypesDocument = gql`
    query findAllAddressTypes($page: Page) {
  findAllAddressTypes(page: $page) {
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
 * __useFindAllAddressTypesQuery__
 *
 * To run a query within a React component, call `useFindAllAddressTypesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllAddressTypesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllAddressTypesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllAddressTypesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllAddressTypesQuery, FindAllAddressTypesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllAddressTypesQuery, FindAllAddressTypesQueryVariables>(FindAllAddressTypesDocument, options);
      }
export function useFindAllAddressTypesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllAddressTypesQuery, FindAllAddressTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllAddressTypesQuery, FindAllAddressTypesQueryVariables>(FindAllAddressTypesDocument, options);
        }
export function useFindAllAddressTypesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllAddressTypesQuery, FindAllAddressTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllAddressTypesQuery, FindAllAddressTypesQueryVariables>(FindAllAddressTypesDocument, options);
        }
export type FindAllAddressTypesQueryHookResult = ReturnType<typeof useFindAllAddressTypesQuery>;
export type FindAllAddressTypesLazyQueryHookResult = ReturnType<typeof useFindAllAddressTypesLazyQuery>;
export type FindAllAddressTypesSuspenseQueryHookResult = ReturnType<typeof useFindAllAddressTypesSuspenseQuery>;
export type FindAllAddressTypesQueryResult = Apollo.QueryResult<FindAllAddressTypesQuery, FindAllAddressTypesQueryVariables>;
export const FindAllAddressUsesDocument = gql`
    query findAllAddressUses($page: Page) {
  findAllAddressUses(page: $page) {
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
 * __useFindAllAddressUsesQuery__
 *
 * To run a query within a React component, call `useFindAllAddressUsesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllAddressUsesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllAddressUsesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllAddressUsesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllAddressUsesQuery, FindAllAddressUsesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllAddressUsesQuery, FindAllAddressUsesQueryVariables>(FindAllAddressUsesDocument, options);
      }
export function useFindAllAddressUsesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllAddressUsesQuery, FindAllAddressUsesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllAddressUsesQuery, FindAllAddressUsesQueryVariables>(FindAllAddressUsesDocument, options);
        }
export function useFindAllAddressUsesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllAddressUsesQuery, FindAllAddressUsesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllAddressUsesQuery, FindAllAddressUsesQueryVariables>(FindAllAddressUsesDocument, options);
        }
export type FindAllAddressUsesQueryHookResult = ReturnType<typeof useFindAllAddressUsesQuery>;
export type FindAllAddressUsesLazyQueryHookResult = ReturnType<typeof useFindAllAddressUsesLazyQuery>;
export type FindAllAddressUsesSuspenseQueryHookResult = ReturnType<typeof useFindAllAddressUsesSuspenseQuery>;
export type FindAllAddressUsesQueryResult = Apollo.QueryResult<FindAllAddressUsesQuery, FindAllAddressUsesQueryVariables>;
export const FindAllAssigningAuthoritiesDocument = gql`
    query findAllAssigningAuthorities($page: Page) {
  findAllAssigningAuthorities(page: $page) {
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
 * __useFindAllAssigningAuthoritiesQuery__
 *
 * To run a query within a React component, call `useFindAllAssigningAuthoritiesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllAssigningAuthoritiesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllAssigningAuthoritiesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllAssigningAuthoritiesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllAssigningAuthoritiesQuery, FindAllAssigningAuthoritiesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllAssigningAuthoritiesQuery, FindAllAssigningAuthoritiesQueryVariables>(FindAllAssigningAuthoritiesDocument, options);
      }
export function useFindAllAssigningAuthoritiesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllAssigningAuthoritiesQuery, FindAllAssigningAuthoritiesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllAssigningAuthoritiesQuery, FindAllAssigningAuthoritiesQueryVariables>(FindAllAssigningAuthoritiesDocument, options);
        }
export function useFindAllAssigningAuthoritiesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllAssigningAuthoritiesQuery, FindAllAssigningAuthoritiesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllAssigningAuthoritiesQuery, FindAllAssigningAuthoritiesQueryVariables>(FindAllAssigningAuthoritiesDocument, options);
        }
export type FindAllAssigningAuthoritiesQueryHookResult = ReturnType<typeof useFindAllAssigningAuthoritiesQuery>;
export type FindAllAssigningAuthoritiesLazyQueryHookResult = ReturnType<typeof useFindAllAssigningAuthoritiesLazyQuery>;
export type FindAllAssigningAuthoritiesSuspenseQueryHookResult = ReturnType<typeof useFindAllAssigningAuthoritiesSuspenseQuery>;
export type FindAllAssigningAuthoritiesQueryResult = Apollo.QueryResult<FindAllAssigningAuthoritiesQuery, FindAllAssigningAuthoritiesQueryVariables>;
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
export function useFindAllConditionCodesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllConditionCodesQuery, FindAllConditionCodesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllConditionCodesQuery, FindAllConditionCodesQueryVariables>(FindAllConditionCodesDocument, options);
        }
export type FindAllConditionCodesQueryHookResult = ReturnType<typeof useFindAllConditionCodesQuery>;
export type FindAllConditionCodesLazyQueryHookResult = ReturnType<typeof useFindAllConditionCodesLazyQuery>;
export type FindAllConditionCodesSuspenseQueryHookResult = ReturnType<typeof useFindAllConditionCodesSuspenseQuery>;
export type FindAllConditionCodesQueryResult = Apollo.QueryResult<FindAllConditionCodesQuery, FindAllConditionCodesQueryVariables>;
export const FindAllDegreesDocument = gql`
    query findAllDegrees($page: Page) {
  findAllDegrees(page: $page) {
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
 * __useFindAllDegreesQuery__
 *
 * To run a query within a React component, call `useFindAllDegreesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllDegreesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllDegreesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllDegreesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllDegreesQuery, FindAllDegreesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllDegreesQuery, FindAllDegreesQueryVariables>(FindAllDegreesDocument, options);
      }
export function useFindAllDegreesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllDegreesQuery, FindAllDegreesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllDegreesQuery, FindAllDegreesQueryVariables>(FindAllDegreesDocument, options);
        }
export function useFindAllDegreesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllDegreesQuery, FindAllDegreesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllDegreesQuery, FindAllDegreesQueryVariables>(FindAllDegreesDocument, options);
        }
export type FindAllDegreesQueryHookResult = ReturnType<typeof useFindAllDegreesQuery>;
export type FindAllDegreesLazyQueryHookResult = ReturnType<typeof useFindAllDegreesLazyQuery>;
export type FindAllDegreesSuspenseQueryHookResult = ReturnType<typeof useFindAllDegreesSuspenseQuery>;
export type FindAllDegreesQueryResult = Apollo.QueryResult<FindAllDegreesQuery, FindAllDegreesQueryVariables>;
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
export function useFindAllEthnicityValuesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllEthnicityValuesQuery, FindAllEthnicityValuesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllEthnicityValuesQuery, FindAllEthnicityValuesQueryVariables>(FindAllEthnicityValuesDocument, options);
        }
export type FindAllEthnicityValuesQueryHookResult = ReturnType<typeof useFindAllEthnicityValuesQuery>;
export type FindAllEthnicityValuesLazyQueryHookResult = ReturnType<typeof useFindAllEthnicityValuesLazyQuery>;
export type FindAllEthnicityValuesSuspenseQueryHookResult = ReturnType<typeof useFindAllEthnicityValuesSuspenseQuery>;
export type FindAllEthnicityValuesQueryResult = Apollo.QueryResult<FindAllEthnicityValuesQuery, FindAllEthnicityValuesQueryVariables>;
export const FindAllIdentificationTypesDocument = gql`
    query findAllIdentificationTypes($page: Page) {
  findAllIdentificationTypes(page: $page) {
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
 * __useFindAllIdentificationTypesQuery__
 *
 * To run a query within a React component, call `useFindAllIdentificationTypesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllIdentificationTypesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllIdentificationTypesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllIdentificationTypesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllIdentificationTypesQuery, FindAllIdentificationTypesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllIdentificationTypesQuery, FindAllIdentificationTypesQueryVariables>(FindAllIdentificationTypesDocument, options);
      }
export function useFindAllIdentificationTypesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllIdentificationTypesQuery, FindAllIdentificationTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllIdentificationTypesQuery, FindAllIdentificationTypesQueryVariables>(FindAllIdentificationTypesDocument, options);
        }
export function useFindAllIdentificationTypesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllIdentificationTypesQuery, FindAllIdentificationTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllIdentificationTypesQuery, FindAllIdentificationTypesQueryVariables>(FindAllIdentificationTypesDocument, options);
        }
export type FindAllIdentificationTypesQueryHookResult = ReturnType<typeof useFindAllIdentificationTypesQuery>;
export type FindAllIdentificationTypesLazyQueryHookResult = ReturnType<typeof useFindAllIdentificationTypesLazyQuery>;
export type FindAllIdentificationTypesSuspenseQueryHookResult = ReturnType<typeof useFindAllIdentificationTypesSuspenseQuery>;
export type FindAllIdentificationTypesQueryResult = Apollo.QueryResult<FindAllIdentificationTypesQuery, FindAllIdentificationTypesQueryVariables>;
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
export function useFindAllJurisdictionsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>(FindAllJurisdictionsDocument, options);
        }
export type FindAllJurisdictionsQueryHookResult = ReturnType<typeof useFindAllJurisdictionsQuery>;
export type FindAllJurisdictionsLazyQueryHookResult = ReturnType<typeof useFindAllJurisdictionsLazyQuery>;
export type FindAllJurisdictionsSuspenseQueryHookResult = ReturnType<typeof useFindAllJurisdictionsSuspenseQuery>;
export type FindAllJurisdictionsQueryResult = Apollo.QueryResult<FindAllJurisdictionsQuery, FindAllJurisdictionsQueryVariables>;
export const FindAllNamePrefixesDocument = gql`
    query findAllNamePrefixes($page: Page) {
  findAllNamePrefixes(page: $page) {
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
 * __useFindAllNamePrefixesQuery__
 *
 * To run a query within a React component, call `useFindAllNamePrefixesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllNamePrefixesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllNamePrefixesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllNamePrefixesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllNamePrefixesQuery, FindAllNamePrefixesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllNamePrefixesQuery, FindAllNamePrefixesQueryVariables>(FindAllNamePrefixesDocument, options);
      }
export function useFindAllNamePrefixesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllNamePrefixesQuery, FindAllNamePrefixesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllNamePrefixesQuery, FindAllNamePrefixesQueryVariables>(FindAllNamePrefixesDocument, options);
        }
export function useFindAllNamePrefixesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllNamePrefixesQuery, FindAllNamePrefixesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllNamePrefixesQuery, FindAllNamePrefixesQueryVariables>(FindAllNamePrefixesDocument, options);
        }
export type FindAllNamePrefixesQueryHookResult = ReturnType<typeof useFindAllNamePrefixesQuery>;
export type FindAllNamePrefixesLazyQueryHookResult = ReturnType<typeof useFindAllNamePrefixesLazyQuery>;
export type FindAllNamePrefixesSuspenseQueryHookResult = ReturnType<typeof useFindAllNamePrefixesSuspenseQuery>;
export type FindAllNamePrefixesQueryResult = Apollo.QueryResult<FindAllNamePrefixesQuery, FindAllNamePrefixesQueryVariables>;
export const FindAllNameTypesDocument = gql`
    query findAllNameTypes($page: Page) {
  findAllNameTypes(page: $page) {
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
 * __useFindAllNameTypesQuery__
 *
 * To run a query within a React component, call `useFindAllNameTypesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllNameTypesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllNameTypesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllNameTypesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllNameTypesQuery, FindAllNameTypesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllNameTypesQuery, FindAllNameTypesQueryVariables>(FindAllNameTypesDocument, options);
      }
export function useFindAllNameTypesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllNameTypesQuery, FindAllNameTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllNameTypesQuery, FindAllNameTypesQueryVariables>(FindAllNameTypesDocument, options);
        }
export function useFindAllNameTypesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllNameTypesQuery, FindAllNameTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllNameTypesQuery, FindAllNameTypesQueryVariables>(FindAllNameTypesDocument, options);
        }
export type FindAllNameTypesQueryHookResult = ReturnType<typeof useFindAllNameTypesQuery>;
export type FindAllNameTypesLazyQueryHookResult = ReturnType<typeof useFindAllNameTypesLazyQuery>;
export type FindAllNameTypesSuspenseQueryHookResult = ReturnType<typeof useFindAllNameTypesSuspenseQuery>;
export type FindAllNameTypesQueryResult = Apollo.QueryResult<FindAllNameTypesQuery, FindAllNameTypesQueryVariables>;
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
export function useFindAllOrganizationsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllOrganizationsQuery, FindAllOrganizationsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllOrganizationsQuery, FindAllOrganizationsQueryVariables>(FindAllOrganizationsDocument, options);
        }
export type FindAllOrganizationsQueryHookResult = ReturnType<typeof useFindAllOrganizationsQuery>;
export type FindAllOrganizationsLazyQueryHookResult = ReturnType<typeof useFindAllOrganizationsLazyQuery>;
export type FindAllOrganizationsSuspenseQueryHookResult = ReturnType<typeof useFindAllOrganizationsSuspenseQuery>;
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
export function useFindAllOutbreaksSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllOutbreaksQuery, FindAllOutbreaksQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllOutbreaksQuery, FindAllOutbreaksQueryVariables>(FindAllOutbreaksDocument, options);
        }
export type FindAllOutbreaksQueryHookResult = ReturnType<typeof useFindAllOutbreaksQuery>;
export type FindAllOutbreaksLazyQueryHookResult = ReturnType<typeof useFindAllOutbreaksLazyQuery>;
export type FindAllOutbreaksSuspenseQueryHookResult = ReturnType<typeof useFindAllOutbreaksSuspenseQuery>;
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
export function useFindAllPatientIdentificationTypesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllPatientIdentificationTypesQuery, FindAllPatientIdentificationTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllPatientIdentificationTypesQuery, FindAllPatientIdentificationTypesQueryVariables>(FindAllPatientIdentificationTypesDocument, options);
        }
export type FindAllPatientIdentificationTypesQueryHookResult = ReturnType<typeof useFindAllPatientIdentificationTypesQuery>;
export type FindAllPatientIdentificationTypesLazyQueryHookResult = ReturnType<typeof useFindAllPatientIdentificationTypesLazyQuery>;
export type FindAllPatientIdentificationTypesSuspenseQueryHookResult = ReturnType<typeof useFindAllPatientIdentificationTypesSuspenseQuery>;
export type FindAllPatientIdentificationTypesQueryResult = Apollo.QueryResult<FindAllPatientIdentificationTypesQuery, FindAllPatientIdentificationTypesQueryVariables>;
export const FindAllPhoneAndEmailTypeDocument = gql`
    query findAllPhoneAndEmailType($page: Page) {
  findAllPhoneAndEmailType(page: $page) {
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
 * __useFindAllPhoneAndEmailTypeQuery__
 *
 * To run a query within a React component, call `useFindAllPhoneAndEmailTypeQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllPhoneAndEmailTypeQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllPhoneAndEmailTypeQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllPhoneAndEmailTypeQuery(baseOptions?: Apollo.QueryHookOptions<FindAllPhoneAndEmailTypeQuery, FindAllPhoneAndEmailTypeQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllPhoneAndEmailTypeQuery, FindAllPhoneAndEmailTypeQueryVariables>(FindAllPhoneAndEmailTypeDocument, options);
      }
export function useFindAllPhoneAndEmailTypeLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllPhoneAndEmailTypeQuery, FindAllPhoneAndEmailTypeQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllPhoneAndEmailTypeQuery, FindAllPhoneAndEmailTypeQueryVariables>(FindAllPhoneAndEmailTypeDocument, options);
        }
export function useFindAllPhoneAndEmailTypeSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllPhoneAndEmailTypeQuery, FindAllPhoneAndEmailTypeQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllPhoneAndEmailTypeQuery, FindAllPhoneAndEmailTypeQueryVariables>(FindAllPhoneAndEmailTypeDocument, options);
        }
export type FindAllPhoneAndEmailTypeQueryHookResult = ReturnType<typeof useFindAllPhoneAndEmailTypeQuery>;
export type FindAllPhoneAndEmailTypeLazyQueryHookResult = ReturnType<typeof useFindAllPhoneAndEmailTypeLazyQuery>;
export type FindAllPhoneAndEmailTypeSuspenseQueryHookResult = ReturnType<typeof useFindAllPhoneAndEmailTypeSuspenseQuery>;
export type FindAllPhoneAndEmailTypeQueryResult = Apollo.QueryResult<FindAllPhoneAndEmailTypeQuery, FindAllPhoneAndEmailTypeQueryVariables>;
export const FindAllPhoneAndEmailUseDocument = gql`
    query findAllPhoneAndEmailUse($page: Page) {
  findAllPhoneAndEmailUse(page: $page) {
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
 * __useFindAllPhoneAndEmailUseQuery__
 *
 * To run a query within a React component, call `useFindAllPhoneAndEmailUseQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllPhoneAndEmailUseQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllPhoneAndEmailUseQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllPhoneAndEmailUseQuery(baseOptions?: Apollo.QueryHookOptions<FindAllPhoneAndEmailUseQuery, FindAllPhoneAndEmailUseQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllPhoneAndEmailUseQuery, FindAllPhoneAndEmailUseQueryVariables>(FindAllPhoneAndEmailUseDocument, options);
      }
export function useFindAllPhoneAndEmailUseLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllPhoneAndEmailUseQuery, FindAllPhoneAndEmailUseQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllPhoneAndEmailUseQuery, FindAllPhoneAndEmailUseQueryVariables>(FindAllPhoneAndEmailUseDocument, options);
        }
export function useFindAllPhoneAndEmailUseSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllPhoneAndEmailUseQuery, FindAllPhoneAndEmailUseQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllPhoneAndEmailUseQuery, FindAllPhoneAndEmailUseQueryVariables>(FindAllPhoneAndEmailUseDocument, options);
        }
export type FindAllPhoneAndEmailUseQueryHookResult = ReturnType<typeof useFindAllPhoneAndEmailUseQuery>;
export type FindAllPhoneAndEmailUseLazyQueryHookResult = ReturnType<typeof useFindAllPhoneAndEmailUseLazyQuery>;
export type FindAllPhoneAndEmailUseSuspenseQueryHookResult = ReturnType<typeof useFindAllPhoneAndEmailUseSuspenseQuery>;
export type FindAllPhoneAndEmailUseQueryResult = Apollo.QueryResult<FindAllPhoneAndEmailUseQuery, FindAllPhoneAndEmailUseQueryVariables>;
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
export function useFindAllPlacesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllPlacesQuery, FindAllPlacesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllPlacesQuery, FindAllPlacesQueryVariables>(FindAllPlacesDocument, options);
        }
export type FindAllPlacesQueryHookResult = ReturnType<typeof useFindAllPlacesQuery>;
export type FindAllPlacesLazyQueryHookResult = ReturnType<typeof useFindAllPlacesLazyQuery>;
export type FindAllPlacesSuspenseQueryHookResult = ReturnType<typeof useFindAllPlacesSuspenseQuery>;
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
export function useFindAllProgramAreasSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllProgramAreasQuery, FindAllProgramAreasQueryVariables>(FindAllProgramAreasDocument, options);
        }
export type FindAllProgramAreasQueryHookResult = ReturnType<typeof useFindAllProgramAreasQuery>;
export type FindAllProgramAreasLazyQueryHookResult = ReturnType<typeof useFindAllProgramAreasLazyQuery>;
export type FindAllProgramAreasSuspenseQueryHookResult = ReturnType<typeof useFindAllProgramAreasSuspenseQuery>;
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
export function useFindAllRaceValuesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllRaceValuesQuery, FindAllRaceValuesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllRaceValuesQuery, FindAllRaceValuesQueryVariables>(FindAllRaceValuesDocument, options);
        }
export type FindAllRaceValuesQueryHookResult = ReturnType<typeof useFindAllRaceValuesQuery>;
export type FindAllRaceValuesLazyQueryHookResult = ReturnType<typeof useFindAllRaceValuesLazyQuery>;
export type FindAllRaceValuesSuspenseQueryHookResult = ReturnType<typeof useFindAllRaceValuesSuspenseQuery>;
export type FindAllRaceValuesQueryResult = Apollo.QueryResult<FindAllRaceValuesQuery, FindAllRaceValuesQueryVariables>;
export const FindAllStateCountyCodeValuesDocument = gql`
    query findAllStateCountyCodeValues($stateCode: String, $page: Page) {
  findAllStateCountyCodeValues(stateCode: $stateCode, page: $page) {
    id
    assigningAuthorityCd
    assigningAuthorityDescTxt
    codeDescTxt
    codeShortDescTxt
    effectiveFromTime
    effectiveToTime
    excludedTxt
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
 * __useFindAllStateCountyCodeValuesQuery__
 *
 * To run a query within a React component, call `useFindAllStateCountyCodeValuesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindAllStateCountyCodeValuesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindAllStateCountyCodeValuesQuery({
 *   variables: {
 *      stateCode: // value for 'stateCode'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindAllStateCountyCodeValuesQuery(baseOptions?: Apollo.QueryHookOptions<FindAllStateCountyCodeValuesQuery, FindAllStateCountyCodeValuesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindAllStateCountyCodeValuesQuery, FindAllStateCountyCodeValuesQueryVariables>(FindAllStateCountyCodeValuesDocument, options);
      }
export function useFindAllStateCountyCodeValuesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindAllStateCountyCodeValuesQuery, FindAllStateCountyCodeValuesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindAllStateCountyCodeValuesQuery, FindAllStateCountyCodeValuesQueryVariables>(FindAllStateCountyCodeValuesDocument, options);
        }
export function useFindAllStateCountyCodeValuesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllStateCountyCodeValuesQuery, FindAllStateCountyCodeValuesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllStateCountyCodeValuesQuery, FindAllStateCountyCodeValuesQueryVariables>(FindAllStateCountyCodeValuesDocument, options);
        }
export type FindAllStateCountyCodeValuesQueryHookResult = ReturnType<typeof useFindAllStateCountyCodeValuesQuery>;
export type FindAllStateCountyCodeValuesLazyQueryHookResult = ReturnType<typeof useFindAllStateCountyCodeValuesLazyQuery>;
export type FindAllStateCountyCodeValuesSuspenseQueryHookResult = ReturnType<typeof useFindAllStateCountyCodeValuesSuspenseQuery>;
export type FindAllStateCountyCodeValuesQueryResult = Apollo.QueryResult<FindAllStateCountyCodeValuesQuery, FindAllStateCountyCodeValuesQueryVariables>;
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
export function useFindAllUsersSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindAllUsersQuery, FindAllUsersQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindAllUsersQuery, FindAllUsersQueryVariables>(FindAllUsersDocument, options);
        }
export type FindAllUsersQueryHookResult = ReturnType<typeof useFindAllUsersQuery>;
export type FindAllUsersLazyQueryHookResult = ReturnType<typeof useFindAllUsersLazyQuery>;
export type FindAllUsersSuspenseQueryHookResult = ReturnType<typeof useFindAllUsersSuspenseQuery>;
export type FindAllUsersQueryResult = Apollo.QueryResult<FindAllUsersQuery, FindAllUsersQueryVariables>;
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
export function useFindContactsNamedByPatientQuery(baseOptions: Apollo.QueryHookOptions<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>(FindContactsNamedByPatientDocument, options);
      }
export function useFindContactsNamedByPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>(FindContactsNamedByPatientDocument, options);
        }
export function useFindContactsNamedByPatientSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindContactsNamedByPatientQuery, FindContactsNamedByPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
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
export function useFindDistinctCodedResultsQuery(baseOptions: Apollo.QueryHookOptions<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>(FindDistinctCodedResultsDocument, options);
      }
export function useFindDistinctCodedResultsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>(FindDistinctCodedResultsDocument, options);
        }
export function useFindDistinctCodedResultsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindDistinctCodedResultsQuery, FindDistinctCodedResultsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
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
export function useFindDistinctResultedTestQuery(baseOptions: Apollo.QueryHookOptions<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>(FindDistinctResultedTestDocument, options);
      }
export function useFindDistinctResultedTestLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>(FindDistinctResultedTestDocument, options);
        }
export function useFindDistinctResultedTestSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindDistinctResultedTestQuery, FindDistinctResultedTestQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
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
export function useFindDocumentsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>(FindDocumentsForPatientDocument, options);
      }
export function useFindDocumentsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>(FindDocumentsForPatientDocument, options);
        }
export function useFindDocumentsForPatientSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindDocumentsForPatientQuery, FindDocumentsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
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
export function useFindDocumentsRequiringReviewForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>(FindDocumentsRequiringReviewForPatientDocument, options);
      }
export function useFindDocumentsRequiringReviewForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>(FindDocumentsRequiringReviewForPatientDocument, options);
        }
export function useFindDocumentsRequiringReviewForPatientSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindDocumentsRequiringReviewForPatientQuery, FindDocumentsRequiringReviewForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
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
        shortId
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
export function useFindInvestigationsByFilterSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindInvestigationsByFilterQuery, FindInvestigationsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
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
export function useFindInvestigationsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>(FindInvestigationsForPatientDocument, options);
      }
export function useFindInvestigationsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>(FindInvestigationsForPatientDocument, options);
        }
export function useFindInvestigationsForPatientSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindInvestigationsForPatientQuery, FindInvestigationsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
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
        shortId
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
export function useFindLabReportsByFilterSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>(FindLabReportsByFilterDocument, options);
        }
export type FindLabReportsByFilterQueryHookResult = ReturnType<typeof useFindLabReportsByFilterQuery>;
export type FindLabReportsByFilterLazyQueryHookResult = ReturnType<typeof useFindLabReportsByFilterLazyQuery>;
export type FindLabReportsByFilterSuspenseQueryHookResult = ReturnType<typeof useFindLabReportsByFilterSuspenseQuery>;
export type FindLabReportsByFilterQueryResult = Apollo.QueryResult<FindLabReportsByFilterQuery, FindLabReportsByFilterQueryVariables>;
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
export function useFindMorbidityReportsForPatientSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>(FindMorbidityReportsForPatientDocument, options);
        }
export type FindMorbidityReportsForPatientQueryHookResult = ReturnType<typeof useFindMorbidityReportsForPatientQuery>;
export type FindMorbidityReportsForPatientLazyQueryHookResult = ReturnType<typeof useFindMorbidityReportsForPatientLazyQuery>;
export type FindMorbidityReportsForPatientSuspenseQueryHookResult = ReturnType<typeof useFindMorbidityReportsForPatientSuspenseQuery>;
export type FindMorbidityReportsForPatientQueryResult = Apollo.QueryResult<FindMorbidityReportsForPatientQuery, FindMorbidityReportsForPatientQueryVariables>;
export const FindNameSuffixesDocument = gql`
    query findNameSuffixes($page: Page) {
  findNameSuffixes(page: $page) {
    content {
      key
      value
    }
    total
  }
}
    `;

/**
 * __useFindNameSuffixesQuery__
 *
 * To run a query within a React component, call `useFindNameSuffixesQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindNameSuffixesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindNameSuffixesQuery({
 *   variables: {
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindNameSuffixesQuery(baseOptions?: Apollo.QueryHookOptions<FindNameSuffixesQuery, FindNameSuffixesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindNameSuffixesQuery, FindNameSuffixesQueryVariables>(FindNameSuffixesDocument, options);
      }
export function useFindNameSuffixesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindNameSuffixesQuery, FindNameSuffixesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindNameSuffixesQuery, FindNameSuffixesQueryVariables>(FindNameSuffixesDocument, options);
        }
export function useFindNameSuffixesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindNameSuffixesQuery, FindNameSuffixesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindNameSuffixesQuery, FindNameSuffixesQueryVariables>(FindNameSuffixesDocument, options);
        }
export type FindNameSuffixesQueryHookResult = ReturnType<typeof useFindNameSuffixesQuery>;
export type FindNameSuffixesLazyQueryHookResult = ReturnType<typeof useFindNameSuffixesLazyQuery>;
export type FindNameSuffixesSuspenseQueryHookResult = ReturnType<typeof useFindNameSuffixesSuspenseQuery>;
export type FindNameSuffixesQueryResult = Apollo.QueryResult<FindNameSuffixesQuery, FindNameSuffixesQueryVariables>;
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
export function useFindOrganizationByIdSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindOrganizationByIdQuery, FindOrganizationByIdQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindOrganizationByIdQuery, FindOrganizationByIdQueryVariables>(FindOrganizationByIdDocument, options);
        }
export type FindOrganizationByIdQueryHookResult = ReturnType<typeof useFindOrganizationByIdQuery>;
export type FindOrganizationByIdLazyQueryHookResult = ReturnType<typeof useFindOrganizationByIdLazyQuery>;
export type FindOrganizationByIdSuspenseQueryHookResult = ReturnType<typeof useFindOrganizationByIdSuspenseQuery>;
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
export function useFindOrganizationsByFilterSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindOrganizationsByFilterQuery, FindOrganizationsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindOrganizationsByFilterQuery, FindOrganizationsByFilterQueryVariables>(FindOrganizationsByFilterDocument, options);
        }
export type FindOrganizationsByFilterQueryHookResult = ReturnType<typeof useFindOrganizationsByFilterQuery>;
export type FindOrganizationsByFilterLazyQueryHookResult = ReturnType<typeof useFindOrganizationsByFilterLazyQuery>;
export type FindOrganizationsByFilterSuspenseQueryHookResult = ReturnType<typeof useFindOrganizationsByFilterSuspenseQuery>;
export type FindOrganizationsByFilterQueryResult = Apollo.QueryResult<FindOrganizationsByFilterQuery, FindOrganizationsByFilterQueryVariables>;
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
export function useFindPatientNamedByContactQuery(baseOptions: Apollo.QueryHookOptions<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>(FindPatientNamedByContactDocument, options);
      }
export function useFindPatientNamedByContactLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>(FindPatientNamedByContactDocument, options);
        }
export function useFindPatientNamedByContactSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>(FindPatientNamedByContactDocument, options);
        }
export type FindPatientNamedByContactQueryHookResult = ReturnType<typeof useFindPatientNamedByContactQuery>;
export type FindPatientNamedByContactLazyQueryHookResult = ReturnType<typeof useFindPatientNamedByContactLazyQuery>;
export type FindPatientNamedByContactSuspenseQueryHookResult = ReturnType<typeof useFindPatientNamedByContactSuspenseQuery>;
export type FindPatientNamedByContactQueryResult = Apollo.QueryResult<FindPatientNamedByContactQuery, FindPatientNamedByContactQueryVariables>;
export const FindPatientProfileDocument = gql`
    query findPatientProfile($asOf: DateTime, $page: Page, $page1: Page, $page2: Page, $page3: Page, $page4: Page, $page5: Page, $patient: ID, $shortId: Int) {
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
export function useFindPatientProfileSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(FindPatientProfileDocument, options);
        }
export type FindPatientProfileQueryHookResult = ReturnType<typeof useFindPatientProfileQuery>;
export type FindPatientProfileLazyQueryHookResult = ReturnType<typeof useFindPatientProfileLazyQuery>;
export type FindPatientProfileSuspenseQueryHookResult = ReturnType<typeof useFindPatientProfileSuspenseQuery>;
export type FindPatientProfileQueryResult = Apollo.QueryResult<FindPatientProfileQuery, FindPatientProfileQueryVariables>;
export const FindPatientsByFilterDocument = gql`
    query findPatientsByFilter($filter: PersonFilter!, $page: SortablePage) {
  findPatientsByFilter(filter: $filter, page: $page) {
    content {
      patient
      birthday
      age
      gender
      status
      shortId
      legalName {
        first
        middle
        last
        suffix
      }
      names {
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
        use
        address
        address2
        city
        state
        zipcode
      }
      phones
      emails
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
export function useFindPatientsByFilterSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>(FindPatientsByFilterDocument, options);
        }
export type FindPatientsByFilterQueryHookResult = ReturnType<typeof useFindPatientsByFilterQuery>;
export type FindPatientsByFilterLazyQueryHookResult = ReturnType<typeof useFindPatientsByFilterLazyQuery>;
export type FindPatientsByFilterSuspenseQueryHookResult = ReturnType<typeof useFindPatientsByFilterSuspenseQuery>;
export type FindPatientsByFilterQueryResult = Apollo.QueryResult<FindPatientsByFilterQuery, FindPatientsByFilterQueryVariables>;
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
export function useFindPlaceByIdSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindPlaceByIdQuery, FindPlaceByIdQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPlaceByIdQuery, FindPlaceByIdQueryVariables>(FindPlaceByIdDocument, options);
        }
export type FindPlaceByIdQueryHookResult = ReturnType<typeof useFindPlaceByIdQuery>;
export type FindPlaceByIdLazyQueryHookResult = ReturnType<typeof useFindPlaceByIdLazyQuery>;
export type FindPlaceByIdSuspenseQueryHookResult = ReturnType<typeof useFindPlaceByIdSuspenseQuery>;
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
export function useFindPlacesByFilterSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindPlacesByFilterQuery, FindPlacesByFilterQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindPlacesByFilterQuery, FindPlacesByFilterQueryVariables>(FindPlacesByFilterDocument, options);
        }
export type FindPlacesByFilterQueryHookResult = ReturnType<typeof useFindPlacesByFilterQuery>;
export type FindPlacesByFilterLazyQueryHookResult = ReturnType<typeof useFindPlacesByFilterLazyQuery>;
export type FindPlacesByFilterSuspenseQueryHookResult = ReturnType<typeof useFindPlacesByFilterSuspenseQuery>;
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
export function useFindSnomedCodedResultsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindSnomedCodedResultsQuery, FindSnomedCodedResultsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindSnomedCodedResultsQuery, FindSnomedCodedResultsQueryVariables>(FindSnomedCodedResultsDocument, options);
        }
export type FindSnomedCodedResultsQueryHookResult = ReturnType<typeof useFindSnomedCodedResultsQuery>;
export type FindSnomedCodedResultsLazyQueryHookResult = ReturnType<typeof useFindSnomedCodedResultsLazyQuery>;
export type FindSnomedCodedResultsSuspenseQueryHookResult = ReturnType<typeof useFindSnomedCodedResultsSuspenseQuery>;
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
export function useFindTreatmentsForPatientSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindTreatmentsForPatientQuery, FindTreatmentsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
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
export function useFindVaccinationsForPatientQuery(baseOptions: Apollo.QueryHookOptions<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>(FindVaccinationsForPatientDocument, options);
      }
export function useFindVaccinationsForPatientLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>(FindVaccinationsForPatientDocument, options);
        }
export function useFindVaccinationsForPatientSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>(FindVaccinationsForPatientDocument, options);
        }
export type FindVaccinationsForPatientQueryHookResult = ReturnType<typeof useFindVaccinationsForPatientQuery>;
export type FindVaccinationsForPatientLazyQueryHookResult = ReturnType<typeof useFindVaccinationsForPatientLazyQuery>;
export type FindVaccinationsForPatientSuspenseQueryHookResult = ReturnType<typeof useFindVaccinationsForPatientSuspenseQuery>;
export type FindVaccinationsForPatientQueryResult = Apollo.QueryResult<FindVaccinationsForPatientQuery, FindVaccinationsForPatientQueryVariables>;
export const GenderUnknownReasonsDocument = gql`
    query genderUnknownReasons {
  genderUnknownReasons {
    value
    name
  }
}
    `;

/**
 * __useGenderUnknownReasonsQuery__
 *
 * To run a query within a React component, call `useGenderUnknownReasonsQuery` and pass it any options that fit your needs.
 * When your component renders, `useGenderUnknownReasonsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useGenderUnknownReasonsQuery({
 *   variables: {
 *   },
 * });
 */
export function useGenderUnknownReasonsQuery(baseOptions?: Apollo.QueryHookOptions<GenderUnknownReasonsQuery, GenderUnknownReasonsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<GenderUnknownReasonsQuery, GenderUnknownReasonsQueryVariables>(GenderUnknownReasonsDocument, options);
      }
export function useGenderUnknownReasonsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<GenderUnknownReasonsQuery, GenderUnknownReasonsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<GenderUnknownReasonsQuery, GenderUnknownReasonsQueryVariables>(GenderUnknownReasonsDocument, options);
        }
export function useGenderUnknownReasonsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<GenderUnknownReasonsQuery, GenderUnknownReasonsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<GenderUnknownReasonsQuery, GenderUnknownReasonsQueryVariables>(GenderUnknownReasonsDocument, options);
        }
export type GenderUnknownReasonsQueryHookResult = ReturnType<typeof useGenderUnknownReasonsQuery>;
export type GenderUnknownReasonsLazyQueryHookResult = ReturnType<typeof useGenderUnknownReasonsLazyQuery>;
export type GenderUnknownReasonsSuspenseQueryHookResult = ReturnType<typeof useGenderUnknownReasonsSuspenseQuery>;
export type GenderUnknownReasonsQueryResult = Apollo.QueryResult<GenderUnknownReasonsQuery, GenderUnknownReasonsQueryVariables>;
export const GendersDocument = gql`
    query genders {
  genders {
    value
    name
  }
}
    `;

/**
 * __useGendersQuery__
 *
 * To run a query within a React component, call `useGendersQuery` and pass it any options that fit your needs.
 * When your component renders, `useGendersQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useGendersQuery({
 *   variables: {
 *   },
 * });
 */
export function useGendersQuery(baseOptions?: Apollo.QueryHookOptions<GendersQuery, GendersQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<GendersQuery, GendersQueryVariables>(GendersDocument, options);
      }
export function useGendersLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<GendersQuery, GendersQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<GendersQuery, GendersQueryVariables>(GendersDocument, options);
        }
export function useGendersSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<GendersQuery, GendersQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<GendersQuery, GendersQueryVariables>(GendersDocument, options);
        }
export type GendersQueryHookResult = ReturnType<typeof useGendersQuery>;
export type GendersLazyQueryHookResult = ReturnType<typeof useGendersLazyQuery>;
export type GendersSuspenseQueryHookResult = ReturnType<typeof useGendersSuspenseQuery>;
export type GendersQueryResult = Apollo.QueryResult<GendersQuery, GendersQueryVariables>;
export const IdentificationTypesDocument = gql`
    query identificationTypes {
  identificationTypes {
    value
    name
  }
}
    `;

/**
 * __useIdentificationTypesQuery__
 *
 * To run a query within a React component, call `useIdentificationTypesQuery` and pass it any options that fit your needs.
 * When your component renders, `useIdentificationTypesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useIdentificationTypesQuery({
 *   variables: {
 *   },
 * });
 */
export function useIdentificationTypesQuery(baseOptions?: Apollo.QueryHookOptions<IdentificationTypesQuery, IdentificationTypesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<IdentificationTypesQuery, IdentificationTypesQueryVariables>(IdentificationTypesDocument, options);
      }
export function useIdentificationTypesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<IdentificationTypesQuery, IdentificationTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<IdentificationTypesQuery, IdentificationTypesQueryVariables>(IdentificationTypesDocument, options);
        }
export function useIdentificationTypesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<IdentificationTypesQuery, IdentificationTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<IdentificationTypesQuery, IdentificationTypesQueryVariables>(IdentificationTypesDocument, options);
        }
export type IdentificationTypesQueryHookResult = ReturnType<typeof useIdentificationTypesQuery>;
export type IdentificationTypesLazyQueryHookResult = ReturnType<typeof useIdentificationTypesLazyQuery>;
export type IdentificationTypesSuspenseQueryHookResult = ReturnType<typeof useIdentificationTypesSuspenseQuery>;
export type IdentificationTypesQueryResult = Apollo.QueryResult<IdentificationTypesQuery, IdentificationTypesQueryVariables>;
export const MaritalStatusesDocument = gql`
    query maritalStatuses {
  maritalStatuses {
    value
    name
  }
}
    `;

/**
 * __useMaritalStatusesQuery__
 *
 * To run a query within a React component, call `useMaritalStatusesQuery` and pass it any options that fit your needs.
 * When your component renders, `useMaritalStatusesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useMaritalStatusesQuery({
 *   variables: {
 *   },
 * });
 */
export function useMaritalStatusesQuery(baseOptions?: Apollo.QueryHookOptions<MaritalStatusesQuery, MaritalStatusesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<MaritalStatusesQuery, MaritalStatusesQueryVariables>(MaritalStatusesDocument, options);
      }
export function useMaritalStatusesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<MaritalStatusesQuery, MaritalStatusesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<MaritalStatusesQuery, MaritalStatusesQueryVariables>(MaritalStatusesDocument, options);
        }
export function useMaritalStatusesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<MaritalStatusesQuery, MaritalStatusesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<MaritalStatusesQuery, MaritalStatusesQueryVariables>(MaritalStatusesDocument, options);
        }
export type MaritalStatusesQueryHookResult = ReturnType<typeof useMaritalStatusesQuery>;
export type MaritalStatusesLazyQueryHookResult = ReturnType<typeof useMaritalStatusesLazyQuery>;
export type MaritalStatusesSuspenseQueryHookResult = ReturnType<typeof useMaritalStatusesSuspenseQuery>;
export type MaritalStatusesQueryResult = Apollo.QueryResult<MaritalStatusesQuery, MaritalStatusesQueryVariables>;
export const NameTypesDocument = gql`
    query nameTypes {
  nameTypes {
    value
    name
  }
}
    `;

/**
 * __useNameTypesQuery__
 *
 * To run a query within a React component, call `useNameTypesQuery` and pass it any options that fit your needs.
 * When your component renders, `useNameTypesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useNameTypesQuery({
 *   variables: {
 *   },
 * });
 */
export function useNameTypesQuery(baseOptions?: Apollo.QueryHookOptions<NameTypesQuery, NameTypesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<NameTypesQuery, NameTypesQueryVariables>(NameTypesDocument, options);
      }
export function useNameTypesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<NameTypesQuery, NameTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<NameTypesQuery, NameTypesQueryVariables>(NameTypesDocument, options);
        }
export function useNameTypesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<NameTypesQuery, NameTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<NameTypesQuery, NameTypesQueryVariables>(NameTypesDocument, options);
        }
export type NameTypesQueryHookResult = ReturnType<typeof useNameTypesQuery>;
export type NameTypesLazyQueryHookResult = ReturnType<typeof useNameTypesLazyQuery>;
export type NameTypesSuspenseQueryHookResult = ReturnType<typeof useNameTypesSuspenseQuery>;
export type NameTypesQueryResult = Apollo.QueryResult<NameTypesQuery, NameTypesQueryVariables>;
export const PhoneTypesDocument = gql`
    query phoneTypes {
  phoneTypes {
    value
    name
  }
}
    `;

/**
 * __usePhoneTypesQuery__
 *
 * To run a query within a React component, call `usePhoneTypesQuery` and pass it any options that fit your needs.
 * When your component renders, `usePhoneTypesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = usePhoneTypesQuery({
 *   variables: {
 *   },
 * });
 */
export function usePhoneTypesQuery(baseOptions?: Apollo.QueryHookOptions<PhoneTypesQuery, PhoneTypesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<PhoneTypesQuery, PhoneTypesQueryVariables>(PhoneTypesDocument, options);
      }
export function usePhoneTypesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<PhoneTypesQuery, PhoneTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<PhoneTypesQuery, PhoneTypesQueryVariables>(PhoneTypesDocument, options);
        }
export function usePhoneTypesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<PhoneTypesQuery, PhoneTypesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<PhoneTypesQuery, PhoneTypesQueryVariables>(PhoneTypesDocument, options);
        }
export type PhoneTypesQueryHookResult = ReturnType<typeof usePhoneTypesQuery>;
export type PhoneTypesLazyQueryHookResult = ReturnType<typeof usePhoneTypesLazyQuery>;
export type PhoneTypesSuspenseQueryHookResult = ReturnType<typeof usePhoneTypesSuspenseQuery>;
export type PhoneTypesQueryResult = Apollo.QueryResult<PhoneTypesQuery, PhoneTypesQueryVariables>;
export const PhoneUsesDocument = gql`
    query phoneUses {
  phoneUses {
    value
    name
  }
}
    `;

/**
 * __usePhoneUsesQuery__
 *
 * To run a query within a React component, call `usePhoneUsesQuery` and pass it any options that fit your needs.
 * When your component renders, `usePhoneUsesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = usePhoneUsesQuery({
 *   variables: {
 *   },
 * });
 */
export function usePhoneUsesQuery(baseOptions?: Apollo.QueryHookOptions<PhoneUsesQuery, PhoneUsesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<PhoneUsesQuery, PhoneUsesQueryVariables>(PhoneUsesDocument, options);
      }
export function usePhoneUsesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<PhoneUsesQuery, PhoneUsesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<PhoneUsesQuery, PhoneUsesQueryVariables>(PhoneUsesDocument, options);
        }
export function usePhoneUsesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<PhoneUsesQuery, PhoneUsesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<PhoneUsesQuery, PhoneUsesQueryVariables>(PhoneUsesDocument, options);
        }
export type PhoneUsesQueryHookResult = ReturnType<typeof usePhoneUsesQuery>;
export type PhoneUsesLazyQueryHookResult = ReturnType<typeof usePhoneUsesLazyQuery>;
export type PhoneUsesSuspenseQueryHookResult = ReturnType<typeof usePhoneUsesSuspenseQuery>;
export type PhoneUsesQueryResult = Apollo.QueryResult<PhoneUsesQuery, PhoneUsesQueryVariables>;
export const PreferredGendersDocument = gql`
    query preferredGenders {
  preferredGenders {
    value
    name
  }
}
    `;

/**
 * __usePreferredGendersQuery__
 *
 * To run a query within a React component, call `usePreferredGendersQuery` and pass it any options that fit your needs.
 * When your component renders, `usePreferredGendersQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = usePreferredGendersQuery({
 *   variables: {
 *   },
 * });
 */
export function usePreferredGendersQuery(baseOptions?: Apollo.QueryHookOptions<PreferredGendersQuery, PreferredGendersQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<PreferredGendersQuery, PreferredGendersQueryVariables>(PreferredGendersDocument, options);
      }
export function usePreferredGendersLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<PreferredGendersQuery, PreferredGendersQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<PreferredGendersQuery, PreferredGendersQueryVariables>(PreferredGendersDocument, options);
        }
export function usePreferredGendersSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<PreferredGendersQuery, PreferredGendersQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<PreferredGendersQuery, PreferredGendersQueryVariables>(PreferredGendersDocument, options);
        }
export type PreferredGendersQueryHookResult = ReturnType<typeof usePreferredGendersQuery>;
export type PreferredGendersLazyQueryHookResult = ReturnType<typeof usePreferredGendersLazyQuery>;
export type PreferredGendersSuspenseQueryHookResult = ReturnType<typeof usePreferredGendersSuspenseQuery>;
export type PreferredGendersQueryResult = Apollo.QueryResult<PreferredGendersQuery, PreferredGendersQueryVariables>;
export const PrefixesDocument = gql`
    query prefixes {
  prefixes {
    value
    name
  }
}
    `;

/**
 * __usePrefixesQuery__
 *
 * To run a query within a React component, call `usePrefixesQuery` and pass it any options that fit your needs.
 * When your component renders, `usePrefixesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = usePrefixesQuery({
 *   variables: {
 *   },
 * });
 */
export function usePrefixesQuery(baseOptions?: Apollo.QueryHookOptions<PrefixesQuery, PrefixesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<PrefixesQuery, PrefixesQueryVariables>(PrefixesDocument, options);
      }
export function usePrefixesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<PrefixesQuery, PrefixesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<PrefixesQuery, PrefixesQueryVariables>(PrefixesDocument, options);
        }
export function usePrefixesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<PrefixesQuery, PrefixesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<PrefixesQuery, PrefixesQueryVariables>(PrefixesDocument, options);
        }
export type PrefixesQueryHookResult = ReturnType<typeof usePrefixesQuery>;
export type PrefixesLazyQueryHookResult = ReturnType<typeof usePrefixesLazyQuery>;
export type PrefixesSuspenseQueryHookResult = ReturnType<typeof usePrefixesSuspenseQuery>;
export type PrefixesQueryResult = Apollo.QueryResult<PrefixesQuery, PrefixesQueryVariables>;
export const PrimaryLanguagesDocument = gql`
    query primaryLanguages {
  primaryLanguages {
    value
    name
  }
}
    `;

/**
 * __usePrimaryLanguagesQuery__
 *
 * To run a query within a React component, call `usePrimaryLanguagesQuery` and pass it any options that fit your needs.
 * When your component renders, `usePrimaryLanguagesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = usePrimaryLanguagesQuery({
 *   variables: {
 *   },
 * });
 */
export function usePrimaryLanguagesQuery(baseOptions?: Apollo.QueryHookOptions<PrimaryLanguagesQuery, PrimaryLanguagesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<PrimaryLanguagesQuery, PrimaryLanguagesQueryVariables>(PrimaryLanguagesDocument, options);
      }
export function usePrimaryLanguagesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<PrimaryLanguagesQuery, PrimaryLanguagesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<PrimaryLanguagesQuery, PrimaryLanguagesQueryVariables>(PrimaryLanguagesDocument, options);
        }
export function usePrimaryLanguagesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<PrimaryLanguagesQuery, PrimaryLanguagesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<PrimaryLanguagesQuery, PrimaryLanguagesQueryVariables>(PrimaryLanguagesDocument, options);
        }
export type PrimaryLanguagesQueryHookResult = ReturnType<typeof usePrimaryLanguagesQuery>;
export type PrimaryLanguagesLazyQueryHookResult = ReturnType<typeof usePrimaryLanguagesLazyQuery>;
export type PrimaryLanguagesSuspenseQueryHookResult = ReturnType<typeof usePrimaryLanguagesSuspenseQuery>;
export type PrimaryLanguagesQueryResult = Apollo.QueryResult<PrimaryLanguagesQuery, PrimaryLanguagesQueryVariables>;
export const PrimaryOccupationsDocument = gql`
    query primaryOccupations {
  primaryOccupations {
    value
    name
  }
}
    `;

/**
 * __usePrimaryOccupationsQuery__
 *
 * To run a query within a React component, call `usePrimaryOccupationsQuery` and pass it any options that fit your needs.
 * When your component renders, `usePrimaryOccupationsQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = usePrimaryOccupationsQuery({
 *   variables: {
 *   },
 * });
 */
export function usePrimaryOccupationsQuery(baseOptions?: Apollo.QueryHookOptions<PrimaryOccupationsQuery, PrimaryOccupationsQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<PrimaryOccupationsQuery, PrimaryOccupationsQueryVariables>(PrimaryOccupationsDocument, options);
      }
export function usePrimaryOccupationsLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<PrimaryOccupationsQuery, PrimaryOccupationsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<PrimaryOccupationsQuery, PrimaryOccupationsQueryVariables>(PrimaryOccupationsDocument, options);
        }
export function usePrimaryOccupationsSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<PrimaryOccupationsQuery, PrimaryOccupationsQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<PrimaryOccupationsQuery, PrimaryOccupationsQueryVariables>(PrimaryOccupationsDocument, options);
        }
export type PrimaryOccupationsQueryHookResult = ReturnType<typeof usePrimaryOccupationsQuery>;
export type PrimaryOccupationsLazyQueryHookResult = ReturnType<typeof usePrimaryOccupationsLazyQuery>;
export type PrimaryOccupationsSuspenseQueryHookResult = ReturnType<typeof usePrimaryOccupationsSuspenseQuery>;
export type PrimaryOccupationsQueryResult = Apollo.QueryResult<PrimaryOccupationsQuery, PrimaryOccupationsQueryVariables>;
export const RaceCategoriesDocument = gql`
    query raceCategories {
  raceCategories {
    value
    name
  }
}
    `;

/**
 * __useRaceCategoriesQuery__
 *
 * To run a query within a React component, call `useRaceCategoriesQuery` and pass it any options that fit your needs.
 * When your component renders, `useRaceCategoriesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useRaceCategoriesQuery({
 *   variables: {
 *   },
 * });
 */
export function useRaceCategoriesQuery(baseOptions?: Apollo.QueryHookOptions<RaceCategoriesQuery, RaceCategoriesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<RaceCategoriesQuery, RaceCategoriesQueryVariables>(RaceCategoriesDocument, options);
      }
export function useRaceCategoriesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<RaceCategoriesQuery, RaceCategoriesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<RaceCategoriesQuery, RaceCategoriesQueryVariables>(RaceCategoriesDocument, options);
        }
export function useRaceCategoriesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<RaceCategoriesQuery, RaceCategoriesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<RaceCategoriesQuery, RaceCategoriesQueryVariables>(RaceCategoriesDocument, options);
        }
export type RaceCategoriesQueryHookResult = ReturnType<typeof useRaceCategoriesQuery>;
export type RaceCategoriesLazyQueryHookResult = ReturnType<typeof useRaceCategoriesLazyQuery>;
export type RaceCategoriesSuspenseQueryHookResult = ReturnType<typeof useRaceCategoriesSuspenseQuery>;
export type RaceCategoriesQueryResult = Apollo.QueryResult<RaceCategoriesQuery, RaceCategoriesQueryVariables>;
export const StatesDocument = gql`
    query states {
  states {
    value
    name
    abbreviation
  }
}
    `;

/**
 * __useStatesQuery__
 *
 * To run a query within a React component, call `useStatesQuery` and pass it any options that fit your needs.
 * When your component renders, `useStatesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useStatesQuery({
 *   variables: {
 *   },
 * });
 */
export function useStatesQuery(baseOptions?: Apollo.QueryHookOptions<StatesQuery, StatesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<StatesQuery, StatesQueryVariables>(StatesDocument, options);
      }
export function useStatesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<StatesQuery, StatesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<StatesQuery, StatesQueryVariables>(StatesDocument, options);
        }
export function useStatesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<StatesQuery, StatesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<StatesQuery, StatesQueryVariables>(StatesDocument, options);
        }
export type StatesQueryHookResult = ReturnType<typeof useStatesQuery>;
export type StatesLazyQueryHookResult = ReturnType<typeof useStatesLazyQuery>;
export type StatesSuspenseQueryHookResult = ReturnType<typeof useStatesSuspenseQuery>;
export type StatesQueryResult = Apollo.QueryResult<StatesQuery, StatesQueryVariables>;
export const SuffixesDocument = gql`
    query suffixes {
  suffixes {
    value
    name
  }
}
    `;

/**
 * __useSuffixesQuery__
 *
 * To run a query within a React component, call `useSuffixesQuery` and pass it any options that fit your needs.
 * When your component renders, `useSuffixesQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useSuffixesQuery({
 *   variables: {
 *   },
 * });
 */
export function useSuffixesQuery(baseOptions?: Apollo.QueryHookOptions<SuffixesQuery, SuffixesQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<SuffixesQuery, SuffixesQueryVariables>(SuffixesDocument, options);
      }
export function useSuffixesLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<SuffixesQuery, SuffixesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<SuffixesQuery, SuffixesQueryVariables>(SuffixesDocument, options);
        }
export function useSuffixesSuspenseQuery(baseOptions?: Apollo.SuspenseQueryHookOptions<SuffixesQuery, SuffixesQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useSuspenseQuery<SuffixesQuery, SuffixesQueryVariables>(SuffixesDocument, options);
        }
export type SuffixesQueryHookResult = ReturnType<typeof useSuffixesQuery>;
export type SuffixesLazyQueryHookResult = ReturnType<typeof useSuffixesLazyQuery>;
export type SuffixesSuspenseQueryHookResult = ReturnType<typeof useSuffixesSuspenseQuery>;
export type SuffixesQueryResult = Apollo.QueryResult<SuffixesQuery, SuffixesQueryVariables>;