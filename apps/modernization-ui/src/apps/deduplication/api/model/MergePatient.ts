export type MergePatient = {
    personUid: string;
    adminComments: AdminComments;
    names: MergeName[];
    addresses: MergeAddress[];
    phoneEmails: MergePhoneEmail[];
    identifications: MergeIdentification[];
    races: MergeRace[];
    ethnicity: MergeEthnicity;
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
