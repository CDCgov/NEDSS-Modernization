import { PatientName } from 'generated/graphql/schema';
import { PatientNameResult } from './useFindPatientProfileNames';
import { Name } from './NameEntry';

const internalized = (content: PatientName): Name | null => {
    return (
        content && {
            ...content,
            asOf: content.asOf && new Date(content.asOf)
        }
    );
};

export const transform = (result: PatientNameResult['findPatientProfile'] | null): Name[] => {
    if (result) {
        return result.names.content.reduce((existing: Name[], next: PatientName | null) => {
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
