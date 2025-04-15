import { DataElements } from './DataElement';
import { MatchingAttribute } from './Pass';

export const DataElementToMatchingAttribute: Record<keyof DataElements, MatchingAttribute> = {
    firstName: MatchingAttribute.FIRST_NAME,
    lastName: MatchingAttribute.LAST_NAME,
    suffix: MatchingAttribute.SUFFIX,
    dateOfBirth: MatchingAttribute.BIRTHDATE,
    sex: MatchingAttribute.SEX,
    race: MatchingAttribute.RACE,
    socialSecurity: MatchingAttribute.SOCIAL_SECURITY,
    address: MatchingAttribute.ADDRESS,
    city: MatchingAttribute.CITY,
    state: MatchingAttribute.STATE,
    zip: MatchingAttribute.ZIP,
    county: MatchingAttribute.COUNTY,
    telephone: MatchingAttribute.PHONE,
    email: MatchingAttribute.EMAIL,
    accountNumber: MatchingAttribute.ACCOUNT_NUMBER,
    driversLicenseNumber: MatchingAttribute.DRIVERS_LICENSE_NUMBER,
    medicaidNumber: MatchingAttribute.MEDICAID_NUMBER,
    medicalRecordNumber: MatchingAttribute.MEDICAL_RECORD_NUMBER,
    medicareNumber: MatchingAttribute.MEDICARE_NUMBER,
    nationalUniqueIdentifier: MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER,
    patientExternalIdentifier: MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER,
    patientInternalIdentifier: MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER,
    personNumber: MatchingAttribute.PERSON_NUMBER,
    visaPassport: MatchingAttribute.VISA_PASSPORT,
    wicIdentifier: MatchingAttribute.WIC_IDENTIFIER
};
