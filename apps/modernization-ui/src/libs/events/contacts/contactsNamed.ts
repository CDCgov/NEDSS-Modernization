import { AssociatedInvestigation } from 'generated';

type PatientFileContacts = {
    condition: string;
    contacts: PatientFileContact[];
};

type PatientFileContact = {
    patient: number;
    identifier: number;
    local: string;
    processingDecision?: string;
    referralBasis?: string;
    createdOn?: Date;
    namedOn?: Date;
    named: NamedContact;
    priority?: string;
    disposition?: string;
    associated?: AssociatedInvestigation;
};

type NamedContact = {
    patientId: number;
    first?: string;
    middle?: string;
    last?: string;
    suffix?: string;
};

export type { PatientFileContacts, PatientFileContact };
