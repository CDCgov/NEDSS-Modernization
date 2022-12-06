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

export enum Deceased {
  N = 'N',
  Unk = 'UNK',
  Y = 'Y'
}

export enum EntryMethod {
  Electronic = 'ELECTRONIC',
  Manual = 'MANUAL'
}

export enum Ethnicity {
  HispanicOrLatino = 'HISPANIC_OR_LATINO',
  NotHispanicOrLatino = 'NOT_HISPANIC_OR_LATINO',
  Unknown = 'UNKNOWN'
}

export type EventFilter = {
  eventType: EventType;
  investigationFilter?: InputMaybe<InvestigationFilter>;
  laboratoryReportFilter?: InputMaybe<LaboratoryReportFilter>;
};

export enum EventStatus {
  New = 'NEW',
  Update = 'UPDATE'
}

export enum EventType {
  Investigation = 'INVESTIGATION',
  LaboratoryReport = 'LABORATORY_REPORT'
}

export enum Gender {
  F = 'F',
  M = 'M',
  U = 'U'
}

export type Identification = {
  identificationNumber: Scalars['String'];
  identificationType: IdentificationType;
};

export enum IdentificationType {
  AccountNumber = 'ACCOUNT_NUMBER',
  AlternatePersonNumber = 'ALTERNATE_PERSON_NUMBER',
  ChipIdentificationNumber = 'CHIP_IDENTIFICATION_NUMBER',
  DriversLicenseNumber = 'DRIVERS_LICENSE_NUMBER',
  ImmunizationRegistryId = 'IMMUNIZATION_REGISTRY_ID',
  MedicaidNumber = 'MEDICAID_NUMBER',
  MedicalRecordNumber = 'MEDICAL_RECORD_NUMBER',
  MedicareNumber = 'MEDICARE_NUMBER',
  MothersIdentifier = 'MOTHERS_IDENTIFIER',
  NationalUniqueIndividualIdentifier = 'NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER',
  Other = 'OTHER',
  PartnerServicesPatientNumber = 'PARTNER_SERVICES_PATIENT_NUMBER',
  PatientExternalIdentifier = 'PATIENT_EXTERNAL_IDENTIFIER',
  PatientInternalIdentifier = 'PATIENT_INTERNAL_IDENTIFIER',
  PersonNumber = 'PERSON_NUMBER',
  PrisonIdentificationNumber = 'PRISON_IDENTIFICATION_NUMBER',
  RyanWhiteIdentifier = 'RYAN_WHITE_IDENTIFIER',
  SocialSecurity = 'SOCIAL_SECURITY',
  VisaPassport = 'VISA_PASSPORT',
  WicIdentifier = 'WIC_IDENTIFIER'
}

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
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatuses?: InputMaybe<ProcessingStatuses>;
  programAreas?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  providerFacilitySearch?: InputMaybe<ProviderFacilitySearch>;
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

export type LabReportProviderSearch = {
  providerId: Scalars['ID'];
  providerType: ProviderType;
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

export type LaboratoryReportFilter = {
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
  pregnancyStatus?: InputMaybe<PregnancyStatus>;
  processingStatus?: InputMaybe<Array<InputMaybe<LaboratoryReportStatus>>>;
  programAreas?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  providerSearch?: InputMaybe<LabReportProviderSearch>;
  resultedTest?: InputMaybe<Scalars['String']>;
};

export enum LaboratoryReportStatus {
  Processed = 'PROCESSED',
  Unprocessed = 'UNPROCESSED'
}

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

export type Mutation = {
  __typename?: 'Mutation';
  createPatient: Person;
};


export type MutationCreatePatientArgs = {
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
  suffix?: InputMaybe<Suffix>;
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
  recordStatusCd?: Maybe<Scalars['String']>;
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

export type Page = {
  pageNumber?: InputMaybe<Scalars['Int']>;
  pageSize?: InputMaybe<Scalars['Int']>;
};

export type Person = {
  __typename?: 'Person';
  NBSEntity: NbsEntity;
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
  nmPrefix?: Maybe<Scalars['String']>;
  nmSuffix?: Maybe<Scalars['String']>;
  occupationCd?: Maybe<Scalars['String']>;
  personParentUid?: Maybe<Scalars['ID']>;
  preferredGenderCd?: Maybe<Gender>;
  preferredNm?: Maybe<Scalars['String']>;
  primLangCd?: Maybe<Scalars['String']>;
  primLangDescTxt?: Maybe<Scalars['String']>;
  raceCategoryCd?: Maybe<Scalars['String']>;
  raceCd?: Maybe<Scalars['String']>;
  raceDescTxt?: Maybe<Scalars['String']>;
  raceSeqNbr?: Maybe<Scalars['Int']>;
  recordStatusCd?: Maybe<Scalars['String']>;
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
  ethnicity?: InputMaybe<Ethnicity>;
  firstName?: InputMaybe<Scalars['String']>;
  gender?: InputMaybe<Gender>;
  id?: InputMaybe<Scalars['ID']>;
  identification?: InputMaybe<Identification>;
  lastName?: InputMaybe<Scalars['String']>;
  mortalityStatus?: InputMaybe<Scalars['String']>;
  phoneNumber?: InputMaybe<Scalars['String']>;
  race?: InputMaybe<Race>;
  recordStatus?: InputMaybe<RecordStatus>;
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
  typeDescTxt?: Maybe<Scalars['String']>;
};

export type PersonInput = {
  DateOfBirth?: InputMaybe<Scalars['Date']>;
  addresses?: InputMaybe<Array<InputMaybe<PostalAddress>>>;
  birthGender?: InputMaybe<Gender>;
  currentGender?: InputMaybe<Gender>;
  deceased?: InputMaybe<Deceased>;
  emailAddresses?: InputMaybe<Array<InputMaybe<Scalars['String']>>>;
  ethnicity?: InputMaybe<Ethnicity>;
  name?: InputMaybe<Name>;
  phoneNumbers?: InputMaybe<Array<InputMaybe<PhoneNumber>>>;
  race?: InputMaybe<Race>;
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

export type PersonPage = {
  pageNumber?: InputMaybe<Scalars['Int']>;
  pageSize?: InputMaybe<Scalars['Int']>;
  sortDirection?: InputMaybe<SortDirection>;
  sortField?: InputMaybe<PersonSortField>;
};

export type PersonResults = {
  __typename?: 'PersonResults';
  content: Array<Maybe<Person>>;
  total: Scalars['Int'];
};

export enum PersonSortField {
  AddTime = 'addTime',
  BirthTime = 'birthTime',
  LastNm = 'lastNm'
}

export type PhoneNumber = {
  extension?: InputMaybe<Scalars['String']>;
  phoneNumber: Scalars['String'];
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
  findAllJurisdictions: Array<Maybe<Jurisdiction>>;
  findAllOrganizations: Array<Maybe<Organization>>;
  findAllPatients: PersonResults;
  findAllPlaces: Array<Maybe<Place>>;
  findAllProgramAreas: Array<Maybe<ProgramAreaCode>>;
  findAllStateCodes: Array<Maybe<StateCode>>;
  findOrganizationById?: Maybe<Organization>;
  findOrganizationsByFilter: Array<Maybe<Organization>>;
  findPatientById?: Maybe<Person>;
  findPatientsByEvent: PersonResults;
  findPatientsByFilter: PersonResults;
  findPatientsByOrganizationFilter: PersonResults;
  findPlaceById?: Maybe<Place>;
  findPlacesByFilter: Array<Maybe<Place>>;
};


export type QueryFindAllConditionCodesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllCountryCodesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllJurisdictionsArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllOrganizationsArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllPatientsArgs = {
  page?: InputMaybe<PersonPage>;
};


export type QueryFindAllPlacesArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllProgramAreasArgs = {
  page?: InputMaybe<Page>;
};


export type QueryFindAllStateCodesArgs = {
  page?: InputMaybe<Page>;
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


export type QueryFindPatientsByEventArgs = {
  filter: EventFilter;
  page?: InputMaybe<PersonPage>;
};


export type QueryFindPatientsByFilterArgs = {
  filter: PersonFilter;
  page?: InputMaybe<PersonPage>;
};


export type QueryFindPatientsByOrganizationFilterArgs = {
  filter: OrganizationFilter;
  page?: InputMaybe<PersonPage>;
};


export type QueryFindPlaceByIdArgs = {
  id: Scalars['ID'];
};


export type QueryFindPlacesByFilterArgs = {
  filter: PlaceFilter;
  page?: InputMaybe<Page>;
};

export enum Race {
  AfricanAmerican = 'AFRICAN_AMERICAN',
  AmericanIndianOrAlaskanNative = 'AMERICAN_INDIAN_OR_ALASKAN_NATIVE',
  Asian = 'ASIAN',
  NativeHawaiianOrPacificIslander = 'NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER',
  NotAsked = 'NOT_ASKED',
  OtherRace = 'OTHER_RACE',
  RefusedToAnswer = 'REFUSED_TO_ANSWER',
  Unknown = 'UNKNOWN',
  White = 'WHITE'
}

export enum RecordStatus {
  Active = 'ACTIVE',
  LogDel = 'LOG_DEL'
}

export enum ReportingEntityType {
  Facility = 'FACILITY',
  Provider = 'PROVIDER'
}

export enum SortDirection {
  Asc = 'ASC',
  Desc = 'DESC'
}

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

export enum UserType {
  External = 'EXTERNAL',
  Internal = 'INTERNAL'
}

export type CreatePatientMutationVariables = Exact<{
  patient: PersonInput;
}>;


export type CreatePatientMutation = { __typename?: 'Mutation', createPatient: { __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, personParentUid?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, NBSEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null } };

export type FindAllConditionCodesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllConditionCodesQuery = { __typename?: 'Query', findAllConditionCodes: Array<{ __typename?: 'ConditionCode', id: string, conditionDescTxt?: string | null } | null> };

export type FindAllCountryCodesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllCountryCodesQuery = { __typename?: 'Query', findAllCountryCodes: Array<{ __typename?: 'CountryCode', id?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, codeDescTxt?: string | null, codeShortDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, excludedTxt?: string | null, keyInfoTxt?: string | null, indentLevelNbr?: number | null, isModifiableInd?: string | null, parentIsCd?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, seqNum?: number | null, nbsUid?: number | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null } | null> };

export type FindAllJurisdictionsQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllJurisdictionsQuery = { __typename?: 'Query', findAllJurisdictions: Array<{ __typename?: 'Jurisdiction', id: string, typeCd: string, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, codeDescTxt?: string | null, codeShortDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, indentLevelNbr?: number | null, isModifiableInd?: string | null, parentIsCd?: string | null, stateDomainCd?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, codeSeqNum?: number | null, nbsUid?: string | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null, exportInd?: string | null } | null> };

export type FindAllOrganizationsQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllOrganizationsQuery = { __typename?: 'Query', findAllOrganizations: Array<{ __typename?: 'Organization', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, standardIndustryClassCd?: string | null, standardIndustryDescTxt?: string | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, displayNm?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, zipCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null, electronicInd?: string | null, edxInd?: string | null } | null> };

export type FindAllPatientsQueryVariables = Exact<{
  page?: InputMaybe<PersonPage>;
}>;


export type FindAllPatientsQuery = { __typename?: 'Query', findAllPatients: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, personParentUid?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, NBSEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null } | null> } };

export type FindAllPlacesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllPlacesQuery = { __typename?: 'Query', findAllPlaces: Array<{ __typename?: 'Place', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: number | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, nm?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, zipCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null } | null> };

export type FindAllProgramAreasQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllProgramAreasQuery = { __typename?: 'Query', findAllProgramAreas: Array<{ __typename?: 'ProgramAreaCode', id: string, progAreaDescTxt?: string | null, nbsUid?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, codeSeq?: number | null } | null> };

export type FindAllStateCodesQueryVariables = Exact<{
  page?: InputMaybe<Page>;
}>;


export type FindAllStateCodesQuery = { __typename?: 'Query', findAllStateCodes: Array<{ __typename?: 'StateCode', id?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null, stateNm?: string | null, codeDescTxt?: string | null, effectiveFromTime?: any | null, effectiveToTime?: any | null, excludedTxt?: string | null, indentLevelNbr?: number | null, isModifiableInd?: string | null, keyInfoTxt?: string | null, parentIsCd?: string | null, statusCd?: string | null, statusTime?: any | null, codeSetNm?: string | null, seqNum?: number | null, nbsUid?: number | null, sourceConceptId?: string | null, codeSystemCd?: string | null, codeSystemDescTxt?: string | null } | null> };

export type FindOrganizationByIdQueryVariables = Exact<{
  id: Scalars['ID'];
}>;


export type FindOrganizationByIdQuery = { __typename?: 'Query', findOrganizationById?: { __typename?: 'Organization', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, standardIndustryClassCd?: string | null, standardIndustryDescTxt?: string | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, displayNm?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, zipCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null, electronicInd?: string | null, edxInd?: string | null } | null };

export type FindOrganizationsByFilterQueryVariables = Exact<{
  filter: OrganizationFilter;
  page?: InputMaybe<Page>;
}>;


export type FindOrganizationsByFilterQuery = { __typename?: 'Query', findOrganizationsByFilter: Array<{ __typename?: 'Organization', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, standardIndustryClassCd?: string | null, standardIndustryDescTxt?: string | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, displayNm?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, zipCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null, electronicInd?: string | null, edxInd?: string | null } | null> };

export type FindPatientByIdQueryVariables = Exact<{
  id: Scalars['ID'];
}>;


export type FindPatientByIdQuery = { __typename?: 'Query', findPatientById?: { __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, personParentUid?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, NBSEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null } | null };

export type FindPatientsByEventQueryVariables = Exact<{
  filter: EventFilter;
  page?: InputMaybe<PersonPage>;
}>;


export type FindPatientsByEventQuery = { __typename?: 'Query', findPatientsByEvent: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, personParentUid?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, NBSEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null } | null> } };

export type FindPatientsByFilterQueryVariables = Exact<{
  filter: PersonFilter;
  page?: InputMaybe<PersonPage>;
}>;


export type FindPatientsByFilterQuery = { __typename?: 'Query', findPatientsByFilter: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, personParentUid?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, NBSEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null } | null> } };

export type FindPatientsByOrganizationFilterQueryVariables = Exact<{
  filter: OrganizationFilter;
  page?: InputMaybe<PersonPage>;
}>;


export type FindPatientsByOrganizationFilterQuery = { __typename?: 'Query', findPatientsByOrganizationFilter: { __typename?: 'PersonResults', total: number, content: Array<{ __typename?: 'Person', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: string | null, administrativeGenderCd?: Gender | null, ageCalc?: number | null, ageCalcTime?: any | null, ageCalcUnitCd?: string | null, ageCategoryCd?: string | null, ageReported?: string | null, ageReportedTime?: any | null, ageReportedUnitCd?: string | null, birthGenderCd?: Gender | null, birthOrderNbr?: number | null, birthTime?: any | null, birthTimeCalc?: any | null, cd?: string | null, cdDescTxt?: string | null, currSexCd?: string | null, deceasedIndCd?: string | null, deceasedTime?: any | null, description?: string | null, educationLevelCd?: string | null, educationLevelDescTxt?: string | null, ethnicGroupInd?: string | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: string | null, localId?: string | null, maritalStatusCd?: string | null, maritalStatusDescTxt?: string | null, mothersMaidenNm?: string | null, multipleBirthInd?: string | null, occupationCd?: string | null, preferredGenderCd?: Gender | null, primLangCd?: string | null, primLangDescTxt?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, survivedIndCd?: string | null, userAffiliationTxt?: string | null, firstNm?: string | null, lastNm?: string | null, middleNm?: string | null, nmPrefix?: string | null, nmSuffix?: string | null, preferredNm?: string | null, hmStreetAddr1?: string | null, hmStreetAddr2?: string | null, hmCityCd?: string | null, hmCityDescTxt?: string | null, hmStateCd?: string | null, hmZipCd?: string | null, hmCntyCd?: string | null, hmCntryCd?: string | null, hmPhoneNbr?: string | null, hmPhoneCntryCd?: string | null, hmEmailAddr?: string | null, cellPhoneNbr?: string | null, wkStreetAddr1?: string | null, wkStreetAddr2?: string | null, wkCityCd?: string | null, wkCityDescTxt?: string | null, wkStateCd?: string | null, wkZipCd?: string | null, wkCntyCd?: string | null, wkCntryCd?: string | null, wkPhoneNbr?: string | null, wkPhoneCntryCd?: string | null, wkEmailAddr?: string | null, ssn?: string | null, medicaidNum?: string | null, dlNum?: string | null, dlStateCd?: string | null, raceCd?: string | null, raceSeqNbr?: number | null, raceCategoryCd?: string | null, ethnicityGroupCd?: string | null, ethnicGroupSeqNbr?: number | null, adultsInHouseNbr?: number | null, childrenInHouseNbr?: number | null, birthCityCd?: string | null, birthCityDescTxt?: string | null, birthCntryCd?: string | null, birthStateCd?: string | null, raceDescTxt?: string | null, ethnicGroupDescTxt?: string | null, versionCtrlNbr?: number | null, asOfDateAdmin?: any | null, asOfDateEthnicity?: any | null, asOfDateGeneral?: any | null, asOfDateMorbidity?: any | null, asOfDateSex?: any | null, electronicInd?: string | null, personParentUid?: string | null, dedupMatchInd?: string | null, groupNbr?: number | null, groupTime?: any | null, edxInd?: string | null, speaksEnglishCd?: string | null, additionalGenderCd?: Gender | null, eharsId?: string | null, ethnicUnkReasonCd?: string | null, sexUnkReasonCd?: string | null, NBSEntity: { __typename?: 'NBSEntity', entityLocatorParticipations?: Array<{ __typename?: 'LocatorParticipations', classCd?: string | null, locator?: { __typename?: 'Locator', emailAddress?: string | null, extenstionTxt?: string | null, phoneNbrTxt?: string | null, urlAddress?: string | null, censusBlockCd?: string | null, censusMinorCivilDivisionCd?: string | null, censusTrackCd?: string | null, cityCd?: string | null, cityDescTxt?: string | null, cntryCd?: string | null, cntryDescTxt?: string | null, cntyCd?: string | null, cntyDescTxt?: string | null, msaCongressDistrictCd?: string | null, regionDistrictCd?: string | null, stateCd?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, zipCd?: string | null, geocodeMatchInd?: string | null, withinCityLimitsInd?: string | null, censusTract?: string | null } | null } | null> | null }, entityIds?: Array<{ __typename?: 'PersonIdentification', typeDescTxt?: string | null, rootExtensionTxt?: string | null, assigningAuthorityCd?: string | null, assigningAuthorityDescTxt?: string | null } | null> | null, names?: Array<{ __typename?: 'PersonName', firstNm?: string | null, middleNm?: string | null, lastNm?: string | null, nmSuffix?: string | null, nmPrefix?: string | null } | null> | null } | null> } };

export type FindPlaceByIdQueryVariables = Exact<{
  id: Scalars['ID'];
}>;


export type FindPlaceByIdQuery = { __typename?: 'Query', findPlaceById?: { __typename?: 'Place', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: number | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, nm?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, zipCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null } | null };

export type FindPlacesByFilterQueryVariables = Exact<{
  filter: PlaceFilter;
  page?: InputMaybe<Page>;
}>;


export type FindPlacesByFilterQuery = { __typename?: 'Query', findPlacesByFilter: Array<{ __typename?: 'Place', id?: string | null, addReasonCd?: string | null, addTime?: any | null, addUserId?: number | null, cd?: string | null, cdDescTxt?: string | null, description?: string | null, durationAmt?: string | null, durationUnitCd?: string | null, fromTime?: any | null, lastChgReasonCd?: string | null, lastChgTime?: any | null, lastChgUserId?: number | null, localId?: string | null, nm?: string | null, recordStatusCd?: string | null, recordStatusTime?: any | null, statusCd?: string | null, statusTime?: any | null, toTime?: any | null, userAffiliationTxt?: string | null, streetAddr1?: string | null, streetAddr2?: string | null, cityCd?: string | null, cityDescTxt?: string | null, stateCd?: string | null, zipCd?: string | null, cntyCd?: string | null, cntryCd?: string | null, phoneNbr?: string | null, phoneCntryCd?: string | null, versionCtrlNbr?: number | null } | null> };


export const CreatePatientDocument = gql`
    mutation createPatient($patient: PersonInput!) {
  createPatient(patient: $patient) {
    id
    NBSEntity {
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
    personParentUid
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
export const FindAllPatientsDocument = gql`
    query findAllPatients($page: PersonPage) {
  findAllPatients(page: $page) {
    content {
      id
      NBSEntity {
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
      personParentUid
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
    NBSEntity {
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
    personParentUid
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
export const FindPatientsByEventDocument = gql`
    query findPatientsByEvent($filter: EventFilter!, $page: PersonPage) {
  findPatientsByEvent(filter: $filter, page: $page) {
    content {
      id
      NBSEntity {
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
      personParentUid
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
 * __useFindPatientsByEventQuery__
 *
 * To run a query within a React component, call `useFindPatientsByEventQuery` and pass it any options that fit your needs.
 * When your component renders, `useFindPatientsByEventQuery` returns an object from Apollo Client that contains loading, error, and data properties
 * you can use to render your UI.
 *
 * @param baseOptions options that will be passed into the query, supported options are listed on: https://www.apollographql.com/docs/react/api/react-hooks/#options;
 *
 * @example
 * const { data, loading, error } = useFindPatientsByEventQuery({
 *   variables: {
 *      filter: // value for 'filter'
 *      page: // value for 'page'
 *   },
 * });
 */
export function useFindPatientsByEventQuery(baseOptions: Apollo.QueryHookOptions<FindPatientsByEventQuery, FindPatientsByEventQueryVariables>) {
        const options = {...defaultOptions, ...baseOptions}
        return Apollo.useQuery<FindPatientsByEventQuery, FindPatientsByEventQueryVariables>(FindPatientsByEventDocument, options);
      }
export function useFindPatientsByEventLazyQuery(baseOptions?: Apollo.LazyQueryHookOptions<FindPatientsByEventQuery, FindPatientsByEventQueryVariables>) {
          const options = {...defaultOptions, ...baseOptions}
          return Apollo.useLazyQuery<FindPatientsByEventQuery, FindPatientsByEventQueryVariables>(FindPatientsByEventDocument, options);
        }
export type FindPatientsByEventQueryHookResult = ReturnType<typeof useFindPatientsByEventQuery>;
export type FindPatientsByEventLazyQueryHookResult = ReturnType<typeof useFindPatientsByEventLazyQuery>;
export type FindPatientsByEventQueryResult = Apollo.QueryResult<FindPatientsByEventQuery, FindPatientsByEventQueryVariables>;
export const FindPatientsByFilterDocument = gql`
    query findPatientsByFilter($filter: PersonFilter!, $page: PersonPage) {
  findPatientsByFilter(filter: $filter, page: $page) {
    content {
      id
      NBSEntity {
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
      personParentUid
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
    query findPatientsByOrganizationFilter($filter: OrganizationFilter!, $page: PersonPage) {
  findPatientsByOrganizationFilter(filter: $filter, page: $page) {
    content {
      id
      NBSEntity {
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
      personParentUid
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