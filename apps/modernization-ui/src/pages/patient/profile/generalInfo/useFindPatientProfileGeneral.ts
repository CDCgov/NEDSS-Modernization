import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { FindPatientProfileQuery, FindPatientProfileQueryVariables } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            local
            shortId
            version
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
                stateHIVCase
            }
        }
    }
`;

export function useFindPatientProfileGeneral(
    baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(Query, options);
}
