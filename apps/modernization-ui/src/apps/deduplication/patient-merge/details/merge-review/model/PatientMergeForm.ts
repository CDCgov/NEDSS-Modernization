export type PatientMergeForm = {
    survivingRecord: string; // the person_uid to be marked as surviving record
    adminComments: string; // corresponds to id of person to pull comments from
    names: NameId[];
    addresses: AddressId[];
    phoneEmails: PhoneEmailId[];
    identifications: IdentificationId[];
    races: RaceId[];
    ethnicity: string; // the id of the person to pull ethnicity data from
    sexAndBirth: SexAndBirthValues;
    mortality: MortalityValues;
    generalInfo: GeneralInfoValues;
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

export type IdentificationId = {
    personUid: string;
    sequence: string;
};

export type RaceId = {
    personUid: string;
    raceCode: string;
};

// all fields are the person_uid of the patient to pull values from
export type SexAndBirthValues = {
    asOf?: string;
    dateOfBirth?: string;
    currentSex?: string;
    transgenderInfo?: string;
    additionalGender?: string;
    birthGender?: string;
    multipleBirth?: string;
    birthCity?: string;
    birthState?: string;
    birthCountry?: string;
};

export type MortalityValues = {
    asOf?: string;
    deceased?: string;
    dateOfDeath?: string;
    deathCity?: string;
    deathState?: string;
    deathCountry?: string;
};

export type GeneralInfoValues = {
    asOf: string;
    maritalStatus: string;
    mothersMaidenName: string;
    numberOfAdultsInResidence: string;
    numberOfChildrenInResidence: string;
    primaryOccupation: string;
    educationLevel: string;
    primaryLanguage: string;
    speaksEnglish: string;
    stateHivCaseId: string;
};
