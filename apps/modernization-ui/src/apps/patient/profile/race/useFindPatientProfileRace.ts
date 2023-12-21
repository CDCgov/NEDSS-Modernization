import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { Page, PatientRaceResults } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($page: Page, $patient: ID!) {
        findPatientProfile(patient: $patient) {
            id
            local
            shortId
            version
            races(page: $page) {
                content {
                    patient
                    version
                    asOf
                    category {
                        id
                        description
                    }
                    detailed {
                        id
                        description
                    }
                }
                total
                number
                size
            }
        }
    }
`;

type PatientProfileRaceVariables = {
    patient: string;
    page?: Page;
};

type PatientProfileRaceResult = {
    __typename?: 'Query';
    findPatientProfile?: {
        __typename?: 'PatientProfile';
        id: string;
        races: PatientRaceResults;
    };
};

export function useFindPatientProfileRace(
    baseOptions?: Apollo.QueryHookOptions<PatientProfileRaceResult, PatientProfileRaceVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientProfileRaceResult, PatientProfileRaceVariables>(Query, options);
}

export type { PatientProfileRaceVariables, PatientProfileRaceResult };
