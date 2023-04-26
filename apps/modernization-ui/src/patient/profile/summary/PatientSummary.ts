type Name = {
    prefix: string | null;
    first: string | null;
    middle: string | null;
    last: string | null;
    suffix: string | null;
};

type Phone = {
    use: string;
    number: string;
};

type Email = {
    use: string;
    address: string;
};

type Address = {
    street: string | null;
    city: string | null;
    state: string | null;
    zipcode: string | null;
    country: string | null;
};

export type PatientSummary = {
    patient: number;
    legalName: Name;
    birthday: string;
    age: number;
    gender: string | null;
    ethnicity: string | null;
    race: string | null;
    phone: Phone[];
    email: Email[];
    address: Address | null;
};
