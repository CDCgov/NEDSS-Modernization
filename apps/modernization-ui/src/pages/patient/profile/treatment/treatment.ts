import { PatientTreatmentInvestigation } from 'generated/graphql/schema';

export enum Column {
    DateCreated = 'Date created',
    Provider = 'Provider',
    TreatmentDate = 'Treatment date',
    Treatment = 'Treatment',
    AssociatedWith = 'Associated with',
    EventNumber = 'Event #'
}

export type Treatment = {
    associatedWith: PatientTreatmentInvestigation;
    createdOn: Date;
    description: string | null;
    event: string | null;
    provider?: string | null;
    treatedOn: Date;
    treatment: string | null;
};
