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
    createdOn: Date;
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

const initial = (): PatientFileContacts[] => [
    {
        condition: 'Acanthoamoeba Disease',
        contacts: [
            {
                local: 'CAS100000000',
                identifier: 1234556,
                patient: 1234567,
                named: { patientId: 12345672134, first: 'John', last: 'Smith' },
                namedOn: new Date('01/01/2001'),
                createdOn: new Date('02/02/2000'),
                priority: 'Medium',
                disposition: 'Not Infected',
                processingDecision: 'decision'
            }
        ]
    },
    {
        condition: 'Corona virus',
        contacts: [
            {
                local: 'CAS1000022320000',
                identifier: 15346352,
                patient: 35685678,
                named: { patientId: 3453457463, first: 'Alex', last: 'Smith' },
                createdOn: new Date('02/02/2003'),
                namedOn: new Date('01/01/2001'),
                priority: 'High',
                disposition: 'Infected',
                processingDecision: 'decision',
                associated: {
                    condition: 'Noro virus',
                    id: 67387294,
                    local: 'CAS10001000GA01'
                }
            }
        ]
    }
];

export { initial };

export type { PatientFileContacts, PatientFileContact };
