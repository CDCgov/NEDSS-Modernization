import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { Page, PatientAdministrativeResults } from 'generated/graphql/schema';

const Query = gql`
    query findPatientProfile($page: Page, $patient: ID!) {
        findPatientProfile(patient: $patient) {
            id
            administrative(page: $page) {
                content {
                    patient
                    id
                    version
                    asOf
                    comment
                }
                total
                number
                size
            }
        }
    }
`;

type PatientProfileAdministrativeVariables = {
    patient: string;
    page?: Page;
};

type PatientProfileAdministrativeResult = {
    __typename?: 'Query';
    findPatientProfile?: {
        __typename?: 'PatientProfile';
        id: string;
        administrative: PatientAdministrativeResults;
    };
};

export function useFindPatientProfileAdministrative(
    baseOptions?: Apollo.QueryHookOptions<PatientProfileAdministrativeResult, PatientProfileAdministrativeVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientProfileAdministrativeResult, PatientProfileAdministrativeVariables>(
        Query,
        options
    );
}

export type { PatientProfileAdministrativeResult, PatientProfileAdministrativeVariables };
