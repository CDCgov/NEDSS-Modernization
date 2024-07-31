import { DocumentRequiringReview, FindDocumentsRequiringReviewForPatientQuery } from 'generated/graphql/schema';
import { DocumentReview } from './ReviewDocuments';

type Result = FindDocumentsRequiringReviewForPatientQuery['findDocumentsRequiringReviewForPatient'];

type Content = DocumentRequiringReview;

const internalized = (content: Content): DocumentReview | null =>
    content && {
        ...content,
        dateReceived: new Date(content.dateReceived),
        eventDate: new Date(content.eventDate)
    };

export const transform = (result: Result): DocumentReview[] => {
    if (result) {
        return result.content.reduce((existing: DocumentReview[], next: Content | null) => {
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
