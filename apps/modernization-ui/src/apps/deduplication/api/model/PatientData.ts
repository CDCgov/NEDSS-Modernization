export type PatientData = {
    personUid: string;
    adminComments: AdminComments;
    names: PatientName[];
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
