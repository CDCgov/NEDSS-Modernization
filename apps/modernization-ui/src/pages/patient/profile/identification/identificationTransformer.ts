import { PatientIdentification } from 'generated/graphql/schema';
import { PatientIdentificationResult } from './useFindPatientProfileIdentifications';
import { asLocalDate } from 'date';
import { Identification } from './identification';

const internalized = (content: PatientIdentification): Identification | null => {
    return (
        content && {
            ...content,
            asOf: content.asOf && asLocalDate(content.asOf)
        }
    );
};

export const transform = (result: PatientIdentificationResult['findPatientProfile'] | null): Identification[] => {
    if (result) {
        debugger
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
