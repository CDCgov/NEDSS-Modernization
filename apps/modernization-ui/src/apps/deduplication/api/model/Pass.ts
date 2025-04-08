export type Pass = {
    id?: number;
    name: string;
    description?: string;
    active: boolean;
    blockingCriteria: BlockingAttribute[];
    matchingCriteria: MatchingAttributeEntry[];
    lowerBound?: number;
    upperBound?: number;
};

export type MatchingAttributeEntry = {
    attribute: MatchingAttribute;
    method: MatchMethod;
};

export enum MatchingAttribute {
    FIRST_NAME = 'FIRST_NAME',
    LAST_NAME = 'LAST_NAME',
    SUFFIX = 'SUFFIX',
    BIRTHDATE = 'BIRTHDATE',
    SEX = 'SEX',
    RACE = 'RACE',
    ADDRESS = 'ADDRESS',
    CITY = 'CITY',
    STATE = 'STATE',
    ZIP = 'ZIP',
    COUNTY = 'COUNTY',
    TELECOM = 'TELECOM',
    PHONE = 'PHONE',
    EMAIL = 'EMAIL',
    IDENTIFIER = 'IDENTIFIER',
    ACCOUNT_NUMBER = 'IDENTIFIER:AN',
    DRIVERS_LICENSE_NUMBER = 'IDENTIFIER:DL',
    MEDICAID_NUMBER = 'IDENTIFIER:MA',
    MEDICAL_RECORD_NUMBER = 'IDENTIFIER:MR',
    NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER = 'IDENTIFIER:NI',
    PATIENT_EXTERNAL_IDENTIFIER = 'IDENTIFIER:PT',
    PATIENT_INTERNAL_IDENTIFIER = 'IDENTIFIER:PI',
    PERSON_NUMBER = 'IDENTIFIER:PN',
    SOCIAL_SECURITY = 'IDENTIFIER:SS',
    VISA_PASSPORT = 'IDENTIFIER:VS',
    WIC_IDENTIFIER = 'IDENTIFIER:WC'
}

export const matchingAttributeLabelMap: Map<MatchingAttribute, string> = new Map([
    [MatchingAttribute.FIRST_NAME, 'First name'],
    [MatchingAttribute.LAST_NAME, 'Last name'],
    [MatchingAttribute.SUFFIX, 'Suffix'],
    [MatchingAttribute.BIRTHDATE, 'Date of birth'],
    [MatchingAttribute.SEX, 'Sex'],
    [MatchingAttribute.RACE, 'Race'],
    [MatchingAttribute.ADDRESS, 'Address'],
    [MatchingAttribute.CITY, 'City'],
    [MatchingAttribute.STATE, 'State'],
    [MatchingAttribute.ZIP, 'Zip'],
    [MatchingAttribute.COUNTY, 'County'],
    [MatchingAttribute.PHONE, 'Phone'],
    [MatchingAttribute.EMAIL, 'Email'],
    [MatchingAttribute.SOCIAL_SECURITY, 'Social security number'],
    [MatchingAttribute.DRIVERS_LICENSE_NUMBER, "Driver's license"],
    [MatchingAttribute.MEDICAID_NUMBER, 'Medicaid number'],
    [MatchingAttribute.MEDICAL_RECORD_NUMBER, 'Medical record number'],
    [MatchingAttribute.ACCOUNT_NUMBER, 'Account number'],
    [MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER, 'National unique idividual identifier'],
    [MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER, 'Patient external identifier'],
    [MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER, 'Patient internal identifier'],
    [MatchingAttribute.PERSON_NUMBER, 'Person number'],
    [MatchingAttribute.VISA_PASSPORT, 'VISA / Passport number'],
    [MatchingAttribute.WIC_IDENTIFIER, 'WIC Identifier']
]);

export enum MatchMethod {
    NONE = '',
    EXACT = 'EXACT',
    JAROWINKLER = 'JAROWINKLER'
}

export const matchMethodLabelMap = new Map<MatchMethod, string>([
    [MatchMethod.EXACT, 'Exact'],
    [MatchMethod.JAROWINKLER, 'JaroWinkler']
]);

export enum BlockingAttribute {
    FIRST_NAME = 'FIRST_NAME',
    LAST_NAME = 'LAST_NAME',
    BIRTHDATE = 'BIRTHDATE',
    SEX = 'SEX',
    ADDRESS = 'ADDRESS',
    ZIP = 'ZIP',
    EMAIL = 'EMAIL',
    PHONE = 'PHONE',
    IDENTIFIER = 'IDENTIFIER'
}

export const blockingAttributeLabelMap = new Map<BlockingAttribute, { label: string; description: string }>([
    [
        BlockingAttribute.FIRST_NAME,
        { label: 'First name', description: "The first 4 characters of the patient's first name" }
    ],
    [
        BlockingAttribute.LAST_NAME,
        { label: 'Last name', description: "The first 4 characters of the patient's last name" }
    ],
    [
        BlockingAttribute.BIRTHDATE,
        { label: 'Date of birth', description: "The person's birthdate in the format YYYY-MM-DD." }
    ],
    [BlockingAttribute.SEX, { label: 'Sex', description: "The person's sex in the format of M or F." }],
    [
        BlockingAttribute.ADDRESS,
        { label: 'Street address 1', description: "The first 4 characters of the person's address." }
    ],
    [BlockingAttribute.ZIP, { label: 'Zip', description: "The person's 5 digit zip code." }],
    [BlockingAttribute.EMAIL, { label: 'Email', description: "The first 4 characters of the person's email address." }],
    [BlockingAttribute.PHONE, { label: 'Phone', description: "The first 4 digits of the person's phone number." }],
    [
        BlockingAttribute.IDENTIFIER,
        {
            label: 'Identifier',
            description:
                'An identifier for the patient. Matching on this will check if any identifier value/authority/type combination matches.'
        }
    ]
]);
