import { FindContactsNamedByPatientQuery, FindPatientNamedByContactQuery } from 'generated/graphql/schema';
import { Tracing, Condition } from './PatientContacts';
import { mapNonNull } from 'utils/mapNonNull';

type NamedByPatientResult = FindContactsNamedByPatientQuery['findContactsNamedByPatient'];
type NamedByContactResult = FindPatientNamedByContactQuery['findPatientNamedByContact'];

type Result = NamedByContactResult | NamedByPatientResult;

type TracedCondition = { __typename?: 'TracedCondition'; id?: string | null; description?: string | null };

type AssociatedWith = {
    __typename?: 'PatientContactInvestigation';
    id: string;
    local: string;
    condition: string;
};

type NamedContact = {
    __typename?: 'NamedContact';
    id: string;
    name: string;
};

type Content = {
    __typename?: 'NamedByPatient';
    contactRecord: string;
    createdOn: any;
    condition: TracedCondition;
    namedOn: any;
    priority?: string | null;
    disposition?: string | null;
    event: string;
    contact: NamedContact;
    associatedWith?: AssociatedWith | null;
} | null;

const orNull = (value: string | undefined | null): string | null => (value && value) || null;

const ensureCondition = (condition: TracedCondition): Condition => ({
    id: orNull(condition.id),
    description: orNull(condition.description)
});

const internalized = (content: Content): Tracing | null =>
    content && {
        ...content,
        createdOn: new Date(content.createdOn),
        namedOn: new Date(content.namedOn),
        condition: (content.condition && ensureCondition(content.condition)) || null
    };

export const transform = (result: Result): Tracing[] => mapNonNull(internalized, result?.content);
