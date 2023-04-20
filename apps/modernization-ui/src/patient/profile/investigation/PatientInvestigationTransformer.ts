import { Investigation } from './PatientInvestigation';
import { FindInvestigationsForPatientQuery } from 'generated/graphql/schema';

type Result = FindInvestigationsForPatientQuery['findInvestigationsForPatient'];

type Content = {
    __typename?: 'PatientInvestigation';
    investigation: string;
    startedOn?: any | null;
    condition: string;
    status: string;
    caseStatus?: string | null;
    jurisdiction: string;
    event: string;
    coInfection?: string | null;
    notification?: string | null;
    investigator?: string | null;
} | null;

const internalized = (content: Content): Investigation | null => {
    return (
        content && {
            ...content,
            startedOn: content.startedOn && new Date(content.startedOn)
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
