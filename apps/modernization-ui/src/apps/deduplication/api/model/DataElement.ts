type DataElement = {
    active?: boolean;
    oddsRatio?: number;
    logOdds?: number;
    threshold?: number;
};

type DataElements = {
    // Personal Information
    firstName?: DataElement;
    lastName?: DataElement;
    dateOfBirth?: DataElement;
    sex?: DataElement;
    race?: DataElement;
    suffix?: DataElement;
    // Address Details
    address?: DataElement;
    city?: DataElement;
    state?: DataElement;
    zip?: DataElement;
    county?: DataElement;
    telephone?: DataElement;
    email?: DataElement;
    // Identification Details
    accountNumber?: DataElement;
    driversLicenseNumber?: DataElement;
    medicaidNumber?: DataElement;
    medicalRecordNumber?: DataElement;
    medicareNumber?: DataElement;
    nationalUniqueIdentifier?: DataElement;
    patientExternalIdentifier?: DataElement;
    patientInternalIdentifier?: DataElement;
    personNumber?: DataElement;
    socialSecurity?: DataElement;
    visaPassport?: DataElement;
    wicIdentifier?: DataElement;
};

export type { DataElements, DataElement };
