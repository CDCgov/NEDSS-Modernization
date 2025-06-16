import { DataElements } from './DataElement';
import { BlockingAttribute, MatchingAttribute } from './Pass';

const MatchingAttributeLabels: Record<MatchingAttribute, { label: string; isActive: (d: DataElements) => boolean }> = {
    [MatchingAttribute.FIRST_NAME]: {
        label: 'First name',
        isActive: (d: DataElements) => d.firstName?.active ?? false
    },
    [MatchingAttribute.LAST_NAME]: {
        label: 'Last name',
        isActive: (d: DataElements) => d.lastName?.active ?? false
    },
    [MatchingAttribute.SUFFIX]: { label: 'Suffix', isActive: (d: DataElements) => d.suffix?.active ?? false },
    [MatchingAttribute.BIRTHDATE]: {
        label: 'Date of birth',
        isActive: (d: DataElements) => d.dateOfBirth?.active ?? false
    },
    [MatchingAttribute.SEX]: { label: 'Sex', isActive: (d: DataElements) => d.sex?.active ?? false },
    [MatchingAttribute.RACE]: { label: 'Race', isActive: (d: DataElements) => d.race?.active ?? false },
    [MatchingAttribute.ADDRESS]: { label: 'Address', isActive: (d: DataElements) => d.address?.active ?? false },
    [MatchingAttribute.CITY]: { label: 'City', isActive: (d: DataElements) => d.city?.active ?? false },
    [MatchingAttribute.STATE]: { label: 'State', isActive: (d: DataElements) => d.state?.active ?? false },
    [MatchingAttribute.ZIP]: { label: 'Zip', isActive: (d: DataElements) => d.zip?.active ?? false },
    [MatchingAttribute.COUNTY]: { label: 'County', isActive: (d: DataElements) => d.county?.active ?? false },
    [MatchingAttribute.PHONE]: { label: 'Phone', isActive: (d: DataElements) => d.telephone?.active ?? false },
    [MatchingAttribute.EMAIL]: { label: 'Email', isActive: (d: DataElements) => d.email?.active ?? false },
    [MatchingAttribute.IDENTIFIER]: {
        label: 'Identifier',
        isActive: (d: DataElements) => d.identifier?.active ?? false
    },
    [MatchingAttribute.SOCIAL_SECURITY]: {
        label: 'Social security number',
        isActive: (d: DataElements) => d.socialSecurity?.active ?? false
    },
    [MatchingAttribute.DRIVERS_LICENSE_NUMBER]: {
        label: "Driver's license",
        isActive: (d: DataElements) => d.driversLicenseNumber?.active ?? false
    },
    [MatchingAttribute.MEDICAID_NUMBER]: {
        label: 'Medicaid number',
        isActive: (d: DataElements) => d.medicaidNumber?.active ?? false
    },
    [MatchingAttribute.MEDICAL_RECORD_NUMBER]: {
        label: 'Medical record number',
        isActive: (d: DataElements) => d.medicalRecordNumber?.active ?? false
    },
    [MatchingAttribute.MEDICARE_NUMBER]: {
        label: 'Medicare number',
        isActive: (d: DataElements) => d.medicareNumber?.active ?? false
    },
    [MatchingAttribute.ACCOUNT_NUMBER]: {
        label: 'Account number',
        isActive: (d: DataElements) => d.accountNumber?.active ?? false
    },
    [MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER]: {
        label: 'National unique individual identifier',
        isActive: (d: DataElements) => d.nationalUniqueIdentifier?.active ?? false
    },
    [MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER]: {
        label: 'Patient external identifier',
        isActive: (d: DataElements) => d.patientExternalIdentifier?.active ?? false
    },
    [MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER]: {
        label: 'Patient internal identifier',
        isActive: (d: DataElements) => d.patientInternalIdentifier?.active ?? false
    },
    [MatchingAttribute.PERSON_NUMBER]: {
        label: 'Person number',
        isActive: (d: DataElements) => d.personNumber?.active ?? false
    },
    [MatchingAttribute.VISA_PASSPORT]: {
        label: 'VISA / Passport number',
        isActive: (d: DataElements) => d.visaPassport?.active ?? false
    },
    [MatchingAttribute.WIC_IDENTIFIER]: {
        label: 'WIC identifier',
        isActive: (d: DataElements) => d.wicIdentifier?.active ?? false
    }
};

const MatchingAttributeLabelsList = Array.from(Object.entries(MatchingAttributeLabels)) as [
    MatchingAttribute,
    { label: string; isActive: (d: DataElements) => boolean }
][];

const BlockingAttributeLabels: Record<
    BlockingAttribute,
    { label: string; description: string; isActive: (d: DataElements) => boolean }
> = {
    [BlockingAttribute.FIRST_NAME]: {
        label: 'First name',
        description: "The first 4 characters of the person's first name.",
        isActive: (d: DataElements) => d.firstName?.active ?? false
    },
    [BlockingAttribute.LAST_NAME]: {
        label: 'Last name',
        description: "The first 4 characters of the person's last name.",
        isActive: (d: DataElements) => d.lastName?.active ?? false
    },
    [BlockingAttribute.BIRTHDATE]: {
        label: 'Date of birth',
        description: "The person's birthdate in the format YYYY-MM-DD.",
        isActive: (d: DataElements) => d.dateOfBirth?.active ?? false
    },
    [BlockingAttribute.SEX]: {
        label: 'Sex',
        description: "The person's sex in the format of M or F.",
        isActive: (d: DataElements) => d.sex?.active ?? false
    },
    [BlockingAttribute.ADDRESS]: {
        label: 'Street address 1',
        description: "The first 4 characters of the person's address.",
        isActive: (d: DataElements) => d.address?.active ?? false
    },
    [BlockingAttribute.ZIP]: {
        label: 'Zip',
        description: "The person's 5 digit zip code.",
        isActive: (d: DataElements) => d.zip?.active ?? false
    },
    [BlockingAttribute.EMAIL]: {
        label: 'Email',
        description: "The first 4 characters of the person's email address.",
        isActive: (d: DataElements) => d.email?.active ?? false
    },
    [BlockingAttribute.PHONE]: {
        label: 'Phone',
        description: "The first 4 digits of the person's phone number.",
        isActive: (d: DataElements) => d.telephone?.active ?? false
    },
    [BlockingAttribute.IDENTIFIER]: {
        label: 'Identifier',
        description: "Any of the person's identifiers.",
        isActive: (d: DataElements) => d.identifier?.active ?? false
    }
};

const BlockingAttributeLabelsList = Array.from(Object.entries(BlockingAttributeLabels)) as [
    BlockingAttribute,
    { label: string; description: string; isActive: (d: DataElements) => boolean }
][];

export { BlockingAttributeLabels, BlockingAttributeLabelsList, MatchingAttributeLabels, MatchingAttributeLabelsList };
