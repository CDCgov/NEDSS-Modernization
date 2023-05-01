import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { FindPatientProfileQuery, FindPatientProfileQueryVariables } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($page4: Page, $patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            local
            shortId
            version
            identification(page: $page4) {
                content {
                    patient
                    id
                    sequence
                    version
                    asOf
                    authority {
                        id
                        description
                    }
                    value
                    type {
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

export function useFindPatientProfileIdentifications(
    baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(Query, options);
}
