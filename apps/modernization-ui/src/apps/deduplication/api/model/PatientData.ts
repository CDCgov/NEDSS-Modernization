export type PatientData = {
    personUid: string;
    adminComments: AdminComments;
    names: PatientName[];
    addresses: PatientAddress[];
};

export type AdminComments = {
    date: string;
    comment: string;
};

export type PatientName = {
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

export type PatientAddress = {
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
