import { PatientAdministrative } from 'generated/graphql/schema';
import { PatientProfileAdministrativeResult } from './useFindPatientProfileAdministrative';
import { asLocalDate } from 'date';
import { Administrative } from './administrative';

const internalized = (content: PatientAdministrative): Administrative | null => {
    return (
        content && {
            ...content,
            asOf: content.asOf && asLocalDate(content.asOf)
        }
    );
};

export const transform = (
    result: PatientProfileAdministrativeResult['findPatientProfile'] | null
): Administrative[] => {
    if (result) {
        return result.administrative.content.reduce(
            (existing: Administrative[], next: PatientAdministrative | null) => {
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
