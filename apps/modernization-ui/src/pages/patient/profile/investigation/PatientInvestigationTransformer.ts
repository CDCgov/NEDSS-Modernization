import { asLocalDate } from 'date';
import { Investigation } from './PatientInvestigation';
import { FindInvestigationsForPatientQuery, PatientInvestigation } from 'generated/graphql/schema';

type Result = FindInvestigationsForPatientQuery['findInvestigationsForPatient'];

type Content = PatientInvestigation;

const internalized = (content: Content): Investigation | null => {
    return (
        content && {
            ...content,
            startedOn: content.startedOn && asLocalDate(content.startedOn)
        }
    );
};

export const transform = (result: Result): Investigation[] => {
    if (result) {
        return result.content.reduce((existing: Investigation[], next: Content | null) => {
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
