import { PatientIdentification } from 'generated/graphql/schema';
import { PatientIdentificationResult } from './useFindPatientProfileIdentifications';
import { Identification } from './identification';

const internalized = (content: PatientIdentification): Identification | null => {
    return (
        content && {
            ...content,
            asOf: content.asOf && new Date(content.asOf)
        }
    );
};

export const transform = (result: PatientIdentificationResult['findPatientProfile'] | null): Identification[] => {
    if (result) {
        return result.identification.content.reduce(
            (existing: Identification[], next: PatientIdentification | null) => {
                if (next) {
                    const doc = internalized(next);
                    if (doc) {
                        existing.push(doc);
                    }
                }

                return existing;
            },
            []
        );
    }
    return [];
};
