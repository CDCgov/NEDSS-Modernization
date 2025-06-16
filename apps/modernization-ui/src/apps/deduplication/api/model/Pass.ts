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
    threshold?: number;
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
    PHONE = 'PHONE',
    EMAIL = 'EMAIL',
    IDENTIFIER = 'IDENTIFIER',
    ACCOUNT_NUMBER = 'IDENTIFIER:AN',
    DRIVERS_LICENSE_NUMBER = 'IDENTIFIER:DL',
    MEDICAID_NUMBER = 'IDENTIFIER:MA',
    MEDICAL_RECORD_NUMBER = 'IDENTIFIER:MR',
    MEDICARE_NUMBER = 'IDENTIFIER:MC',
    NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER = 'IDENTIFIER:NI',
    PATIENT_EXTERNAL_IDENTIFIER = 'IDENTIFIER:PT',
    PATIENT_INTERNAL_IDENTIFIER = 'IDENTIFIER:PI',
    PERSON_NUMBER = 'IDENTIFIER:PN',
    SOCIAL_SECURITY = 'IDENTIFIER:SS',
    VISA_PASSPORT = 'IDENTIFIER:VS',
    WIC_IDENTIFIER = 'IDENTIFIER:WC'
}

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
