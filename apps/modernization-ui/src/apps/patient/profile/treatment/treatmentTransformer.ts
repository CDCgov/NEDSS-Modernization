import { FindTreatmentsForPatientQuery, PatientTreatment } from 'generated/graphql/schema';
import { asLocalDate } from 'date';
import { Treatment } from './treatment';

const internalized = (content: PatientTreatment): Treatment | null => {
    return (
        content && {
            ...content,
            createdOn: content.createdOn && asLocalDate(content.createdOn)
        }
    );
};

export const transform = (result: FindTreatmentsForPatientQuery['findTreatmentsForPatient']): Treatment[] => {
    if (result && result.content) {
        return result.content.reduce((existing: Treatment[], next: PatientTreatment | null) => {
            if (next) {
                const doc = internalized(next);
                if (doc) {
                    existing.push(doc);
                }
            }

            return existing;
        }, []);
    }
    return [];
};
