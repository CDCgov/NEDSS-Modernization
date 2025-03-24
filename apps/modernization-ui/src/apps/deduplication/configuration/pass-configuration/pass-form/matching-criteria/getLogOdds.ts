import { MatchingAttribute } from 'apps/deduplication/api/model/Pass';
import { DataElements } from 'apps/deduplication/data-elements/DataElement';

export const getLogOdds = (dataElements: DataElements, matchingAttribute?: MatchingAttribute) => {
    switch (matchingAttribute) {
        case MatchingAttribute.FIRST_NAME:
            return dataElements.firstName?.logOdds ?? 0;
        case MatchingAttribute.LAST_NAME:
            return dataElements.lastName?.logOdds ?? 0;
        case MatchingAttribute.SUFFIX:
            return dataElements.suffix?.logOdds ?? 0;
        case MatchingAttribute.BIRTHDATE:
            return dataElements.dateOfBirth?.logOdds ?? 0;
        case MatchingAttribute.SEX:
            return dataElements.sex?.logOdds ?? 0;
        case MatchingAttribute.RACE:
            return dataElements.race?.logOdds ?? 0;
        case MatchingAttribute.ADDRESS:
            return dataElements.address?.logOdds ?? 0;
        case MatchingAttribute.CITY:
            return dataElements.city?.logOdds ?? 0;
        case MatchingAttribute.STATE:
            return dataElements.state?.logOdds ?? 0;
        case MatchingAttribute.ZIP:
            return dataElements.zip?.logOdds ?? 0;
        case MatchingAttribute.COUNTY:
            return dataElements.county?.logOdds ?? 0;
        case MatchingAttribute.PHONE:
            return dataElements.telephone?.logOdds ?? 0;
        case MatchingAttribute.EMAIL:
            return dataElements.email?.logOdds ?? 0;
        case MatchingAttribute.ACCOUNT_NUMBER:
            return dataElements.accountNumber?.logOdds ?? 0;
        case MatchingAttribute.DRIVERS_LICENSE_NUMBER:
            return dataElements.driversLicenseNumber?.logOdds ?? 0;
        case MatchingAttribute.MEDICAID_NUMBER:
            return dataElements.medicaidNumber?.logOdds ?? 0;
        case MatchingAttribute.MEDICAL_RECORD_NUMBER:
            return dataElements.medicalRecordNumber?.logOdds ?? 0;
        case MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER:
            return dataElements.nationalUniqueIdentifier?.logOdds ?? 0;
        case MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER:
            return dataElements.patientExternalIdentifier?.logOdds ?? 0;
        case MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER:
            return dataElements.patientInternalIdentifier?.logOdds ?? 0;
        case MatchingAttribute.PERSON_NUMBER:
            return dataElements.personNumber?.logOdds ?? 0;
        case MatchingAttribute.SOCIAL_SECURITY:
            return dataElements.socialSecurity?.logOdds ?? 0;
        case MatchingAttribute.VISA_PASSPORT:
            return dataElements.visaPassport?.logOdds ?? 0;
        case MatchingAttribute.WIC_IDENTIFIER:
            return dataElements.wicIdentifier?.logOdds ?? 0;
        default:
            return 0;
    }
};
