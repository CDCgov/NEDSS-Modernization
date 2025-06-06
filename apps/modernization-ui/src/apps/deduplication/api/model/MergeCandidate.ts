export type MergeCandidate = {
    personUid: string;
    personLocalId: string;
    adminComments: AdminComments;
    names: MergeName[];
    addresses: MergeAddress[];
    phoneEmails: MergePhoneEmail[];
    identifications: MergeIdentification[];
    races: MergeRace[];
    ethnicity: MergeEthnicity;
    sexAndBirth: MergeSexAndBirth;
    mortality: MergeMortality;
    general: MergeGeneralInfo;
    investigations: MergeInvestigation[];
};

export type AdminComments = {
    date: string;
    comment: string;
};

export type MergeName = {
    personUid: string;
    sequence: string;
    asOf: string;
    type: string;
    prefix?: string;
    last?: string;
    secondLast?: string;
    first?: string;
    middle?: string;
    secondMiddle?: string;
    suffix?: string;
    degree?: string;
};

export type MergeAddress = {
    id: string;
    asOf: string;
    type: string;
    use: string;
    address?: string;
    address2?: string;
    city?: string;
    state?: string;
    zipcode?: string;
    county?: string;
    censusTract?: string;
    country?: string;
    comments?: string;
};

export type MergePhoneEmail = {
    id: string;
    asOf: string;
    type: string;
    use: string;
    countryCode?: string;
    phoneNumber?: string;
    extension?: string;
    email?: string;
    url?: string;
    comments?: string;
};

export type MergeIdentification = {
    personUid: string;
    sequence: string;
    asOf: string;
    type: string;
    assigningAuthority?: string;
    value: string;
};

export type MergeRace = {
    personUid: string;
    raceCode: string;
    asOf: string;
    race: string;
    detailedRaces?: string;
};

export type MergeEthnicity = {
    asof?: string;
    asOf?: string;
    ethnicity?: string;
    reasonUnknown?: string;
    spanishOrigin?: string;
};

export type MergeSexAndBirth = {
    asOf?: string;
    dateOfBirth?: string;
    currentSex?: string;
    sexUnknown?: string;
    transgender?: string;
    additionalGender?: string;
    birthGender?: string;
    multipleBirth?: string;
    birthOrder?: string;
    birthCity?: string;
    birthState?: string;
    birthCounty?: string;
    birthCountry?: string;
};

export type MergeMortality = {
    asOf?: string;
    deceased?: string;
    dateOfDeath?: string;
    deathCity?: string;
    deathState?: string;
    deathCounty?: string;
    deathCountry?: string;
};

export type MergeGeneralInfo = {
    asOf?: string;
    maritalStatus?: string;
    mothersMaidenName?: string;
    numberOfAdultsInResidence?: string;
    numberOfChildrenInResidence?: string;
    primaryOccupation?: string;
    educationLevel?: string;
    primaryLanguage?: string;
    speaksEnglish?: string;
    stateHivCaseId?: string;
};

export type MergeInvestigation = {
    id: string;
    startDate?: string;
    condition: string;
};
