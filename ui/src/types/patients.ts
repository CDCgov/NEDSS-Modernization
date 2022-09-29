export type Page = {
    pageSize: number,
    pageNumber: number
}

export type PatientFilter = {
    page: Page;
    id: number;
    lastName: string;
    firstName: string;
    ssn: string;
    phoneNumber: string;
    DateOfBirth: Date;
    DateOfBirthOperator: Operator;
    gender: string;
    deceased: Deceased;
    address: string;
    city: string;
    state: string;
    country: string;
    zip: string;
    mortalityStatus: string;
    ethnicity: string;
    recordStatus: any;
}

export type Deceased = 'YES' | 'NO' | 'UNKNOWN';

export type Operator = 'EQUAL' | 'BEFORE' | 'AFTER';
