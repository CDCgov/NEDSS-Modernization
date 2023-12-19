import { asLocalDate } from 'date';
import { LabReport } from './LabReport';
import { FindLabReportsForPatientQuery, PatientLabReport } from 'generated/graphql/schema';

type Result = FindLabReportsForPatientQuery['findLabReportsForPatient'];

type Content = PatientLabReport;

const internalized = (content: Content): LabReport | null => {
    return (
        content && {
            ...content,
            addTime: content.addTime && asLocalDate(content.addTime)
        }
    );
};

export const transform = (result: Result): LabReport[] => {
    if (result) {
        return result.reduce((existing: LabReport[], next: Content | null) => {
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
