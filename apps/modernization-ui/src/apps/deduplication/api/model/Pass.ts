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
    DATE_OF_BIRTH = 'DATE_OF_BIRTH',
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
    ACCOUNT_NUMBER = 'ACCOUNT_NUMBER',
    DRIVERS_LICENSE_NUMBER = 'DRIVERS_LICENSE_NUMBER',
    MEDICAID_NUMBER = 'MEDICAID_NUMBER',
    MEDICAL_RECORD_NUMBER = 'MEDICAL_RECORD_NUMBER',
    NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER = 'NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER',
    PATIENT_EXTERNAL_IDENTIFIER = 'PATIENT_EXTERNAL_IDENTIFIER',
    PATIENT_INTERNAL_IDENTIFIER = 'PATIENT_INTERNAL_IDENTIFIER',
    PERSON_NUMBER = 'PERSON_NUMBER',
    SOCIAL_SECURITY = 'SOCIAL_SECURITY',
    VISA_PASSPORT = 'VISA/PASSPORT',
    WIC_IDENTIFIER = 'WIC_IDENTIFIER'
}

export enum MatchMethod {
    NONE = '',
    EXACT = 'EXACT',
    JAROWINKLER = 'JAROWINKLER'
}

export enum BlockingAttribute {
    FIRST_NAME = 'FIRST_NAME',
    LAST_NAME = 'LAST_NAME',
    DATE_OF_BIRTH = 'DATE_OF_BIRTH',
    SEX = 'SEX',
    STREET_ADDRESS = 'STREET_ADDRESS',
    ZIP = 'ZIP',
    EMAIL = 'EMAIL',
    PHONE = 'PHONE',
    IDENTIFIER = 'IDENTIFIER'
}
