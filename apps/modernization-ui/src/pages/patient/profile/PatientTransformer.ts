import { FindPatientProfileQuery } from 'generated/graphql/schema';
import { Patient } from './Patient';

type Result = FindPatientProfileQuery['findPatientProfile'];

export const transform = (result: Result): Patient | undefined => {
    if (result) {
        const { id, local, version, shortId } = result;
        return {
            id,
            local,
            version,
            shortId
        };
    }
};
