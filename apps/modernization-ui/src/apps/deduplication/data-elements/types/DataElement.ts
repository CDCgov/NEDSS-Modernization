type DataElement = {
    active?: boolean;
    logOdds?: number;
    threshold?: number;
};

type DataElements = {
    dateOfBirth?: DataElement;
    currentSex?: DataElement;
    race?: DataElement;
    firstName?: DataElement;
    lastName?: DataElement;
    suffix?: DataElement;
    streetAddress1?: DataElement;
    city?: DataElement;
    state?: DataElement;
    zip?: DataElement;
    county?: DataElement;
    telecom?: DataElement;
    telephone?: DataElement;
    email?: DataElement;
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

type Field = {
    key: keyof DataElements;
    label: string;
};

// Define structure for grouping fields (Personal, Address, Identification)
type DataElementGroup = {
    title: string;
    fields: Field[];
};

export type { DataElements, DataElement, Field, DataElementGroup };
