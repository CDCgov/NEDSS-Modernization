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
            mortality {
                patient
                id
                version
                asOf
                deceased {
                    id
                    description
                }
                deceasedOn
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
        }
    }
`;

export function useFindPatientProfileMortality(
    baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(Query, options);
}
