export type Name = {
    prefix: string | null;
    first: string | null;
    middle: string | null;
    last: string | null;
    suffix: string | null;
};

export type Phone = {
    use: string;
    number: string;
};

export type Email = {
    use: string;
    address: string;
};

export type PatientSummaryIdentification = {
    type: string;
    value: string;
};

export type Address = {
    street: string | null;
    city: string | null;
    state: string | null;
    zipcode: string | null;
    country: string | null;
};

export type PatientSummary = {
    legalName: Name | null;
    birthday: string | null;
    age: number | null;
    gender: string | null;
    ethnicity: string | null;
    race: string | null;
    phone: Phone[];
    email: Email[];
    identification: PatientSummaryIdentification[];
    address: Address | null;
};
