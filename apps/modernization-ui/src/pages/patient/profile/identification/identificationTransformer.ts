import { PatientIdentification } from 'generated/graphql/schema';
import { PatientIdentificationResult } from './useFindPatientProfileIdentifications';
import { asLocalDate } from 'date';

const internalized = (content: PatientIdentification): PatientIdentification | null => {
    return (
        content && {
            ...content,
            asOf: content.asOf && asLocalDate(content.asOf)
        }
    );
};

export const transform = (result: PatientIdentificationResult['findPatientProfile']): PatientIdentification[] => {
    if (result) {
        return result.identification.content.reduce(
            (existing: PatientIdentification[], next: PatientIdentification | null) => {
                if (next) {
                    const doc = internalized(next);
                    if (doc) {
                        return [...existing, doc];
                    }
                }

                return existing;
            },
            []
        );
    }
    return [];
};
