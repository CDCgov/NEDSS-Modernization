import { AssociatedInvestigation } from 'generated';
import { DisplayableName } from 'name';

type PatientFileContactsNamed = {
    condition: string;
    contacts: ContactNamed[];
};

type ContactNamed = {
    local: string;
    patient: number;
    name?: DisplayableName;
    createdOn?: Date;
    namedOn?: Date;
    priority?: string;
    disposition?: string;
    processingDecision?: string;
    associations?: AssociatedInvestigation[];
};

const initial = (): PatientFileContactsNamed[] => [
    {
        condition: 'Acanthoamoeba Disease',
        contacts: [
            {
                local: 'CAS100000000',
                patient: 1234567,
                name: { first: 'John', last: 'Smith' },
                createdOn: new Date('01/01/2000'),
                priority: 'Medium',
                disposition: 'Not Infected',
                processingDecision: 'decision'
            }
        ]
    }
];

export { initial };

export type { PatientFileContactsNamed, ContactNamed };
