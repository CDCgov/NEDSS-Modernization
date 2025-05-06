import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { PatientSummary } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($asOf: Date, $patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            local
            shortId
            version
            deletable
            status
            summary(asOf: $asOf) {
                legalName {
                    prefix
                    first
                    middle
                    last
                    suffix
                }
                birthday
                age
                gender
                ethnicity
                races
                phone {
                    use
                    number
                }
                email {
                    use
                    address
                }
                home {
                    use
                    address
                    address2
                    city
                    state
                    zipcode
                    country
                }
                address {
                    use
                    address
                    address2
                    city
                    state
                    zipcode
                    country
                }
                identification {
                    type
                    value
                }
            }
        }
    }
`;

type WithPatientSummary = {
    summary: PatientSummary;
};

type PatientProfile = {
    __typename: 'PatientProfile';
    id: string;
    local: string;
    shortId: number;
    version: number;
    status: string;
    deletable: boolean;
};

type PatientProfileResult = {
    __typename: 'Query';
    findPatientProfile?: (PatientProfile & WithPatientSummary) | null | undefined;
};

type Variables = {
    asOf?: Date;
    patient?: string;
    shortId?: number;
};

function useFindPatientProfileSummary(baseOptions?: Apollo.QueryHookOptions<PatientProfileResult, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientProfileResult, Variables>(Query, options);
}

export { useFindPatientProfileSummary };
export type { PatientProfileResult };
