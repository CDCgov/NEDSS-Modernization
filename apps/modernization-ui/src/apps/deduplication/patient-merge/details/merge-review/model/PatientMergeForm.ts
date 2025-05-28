export type PatientMergeForm = {
    survivingRecord: string; // the person_uid to be marked as surviving record
    adminComments: string; // corresponds to id of person to pull comments from
    names: NameId[];
    addresses: AddressId[];
    phoneEmails: PhoneEmailId[];
};

export type NameId = {
    personUid: string;
    sequence: string;
};

export type AddressId = {
    locatorId: string;
};

export type PhoneEmailId = {
    locatorId: string;
};
