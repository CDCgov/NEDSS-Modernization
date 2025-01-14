import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { PatientGeneral } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            general {
                patient
                id
                version
                asOf
                maritalStatus {
                    id
                    description
                }
                maternalMaidenName
                adultsInHouse
                childrenInHouse
                occupation {
                    id
                    description
                }
                educationLevel {
                    id
                    description
                }
                primaryLanguage {
                    id
                    description
                }
                speaksEnglish {
                    id
                    description
                }
                stateHIVCase {
                    __typename
                    ... on Allowed {
                        value
                    }
                    ... on Restricted {
                        reason
                    }
                }
            }
        }
    }
`;

type PatientProfileGeneralVariables = {
    patient: string;
};

type PatientProfileGeneralResult = {
    __typename?: 'Query';
    findPatientProfile?: {
        __typename?: 'PatientProfile';
        id: string;
        general: PatientGeneral;
    };
};

export function useFindPatientProfileGeneral(
    baseOptions?: Apollo.QueryHookOptions<PatientProfileGeneralResult, PatientProfileGeneralVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientProfileGeneralResult, PatientProfileGeneralVariables>(Query, options);
}

export type { PatientProfileGeneralVariables, PatientProfileGeneralResult };
