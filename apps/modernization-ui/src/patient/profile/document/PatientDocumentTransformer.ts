import { FindDocumentsForPatientQuery } from 'generated/graphql/schema';
import { Document } from './PatientDocuments';

type Result = FindDocumentsForPatientQuery['findDocumentsForPatient'];

type Content = {
    __typename?: 'PatientDocument';
    document: string;
    receivedOn: any;
    type: string;
    sendingFacility: string;
    reportedOn: any;
    condition?: string | null;
    event: string;
    associatedWith?: {
        __typename?: 'PatientDocumentInvestigation';
        id: string;
        local: string;
    } | null;
} | null;

const internalized = (content: Content): Document | null =>
    content && {
        ...content,
        receivedOn: new Date(content.receivedOn),
        reportedOn: new Date(content.reportedOn)
    };

export const transform = (result: Result): Document[] => {
    if (result) {
        return result.content.reduce((existing: Document[], next: Content | null) => {
            if (next) {
                const doc = internalized(next);
                if (doc) {
                    return [...existing, doc];
                }
            }

            return existing;
        }, []);
    }
    return [];
};
