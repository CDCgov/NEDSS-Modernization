import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { FindPatientProfileQuery, FindPatientProfileQueryVariables } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($page: Page, $patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            local
            shortId
            version
            names(page: $page) {
                content {
                    patient
                    version
                    asOf
                    sequence
                    use {
                        id
                        description
                    }
                    prefix {
                        id
                        description
                    }
                    first
                    middle
                    secondMiddle
                    last
                    secondLast
                    suffix {
                        id
                        description
                    }
                    degree {
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

export function useFindPatientProfileNames(
    baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(Query, options);
}
