type DataElement = {
    active?: boolean;
    m?: number;
    u?: number;
    logOdds?: number;
    threshold?: number;
};

type DataElements = {
    firstName?: DataElement;
    lastName?: DataElement;
    suffix?: DataElement;
    birthDate?: DataElement;
    sex?: DataElement;
    gender?: DataElement;
    race?: DataElement;
    address?: DataElement;
    city?: DataElement;
    state?: DataElement;
    zip?: DataElement;
    county?: DataElement;
    telephone?: DataElement;
    mrn?: DataElement;
    ssn?: DataElement;
    driversLicense?: DataElement;
};

export type { DataElements, DataElement };
