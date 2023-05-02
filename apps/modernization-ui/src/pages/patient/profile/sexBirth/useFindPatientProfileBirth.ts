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
            birth {
                patient
                id
                version
                asOf
                bornOn
                age
                multipleBirth {
                    id
                    description
                }
                city
                state {
                    id
                    description
                }
                country {
                    id
                    description
                }
            }
            gender {
                patient
                id
                version
                asOf
                birth {
                    id
                    description
                }
                current {
                    id
                    description
                }
                unknownReason {
                    id
                    description
                }
                preferred {
                    id
                    description
                }
                additional
            }
        }
    }
`;

export function useFindPatientProfileBirth(
    baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(Query, options);
}
