type DataElement = {
    active: boolean;
    m: number;
    u: number;
    logOdds: number;
    threshold: number;
};

type DataElements = {
    belongingnessRatio: number;
    firstName: DataElement;
    lastName: DataElement;
    suffix: DataElement;
    birthDate: DataElement;
    mrn: DataElement;
    ssn: DataElement;
    sex: DataElement;
    gender: DataElement;
    race: DataElement;
    address: DataElement;
    city: DataElement;
    state: DataElement;
    zip: DataElement;
    county: DataElement;
    telephone: DataElement;
};

export type { DataElement, DataElements };
