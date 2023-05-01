import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { FindPatientProfileQuery, FindPatientProfileQueryVariables } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($page2: Page, $patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            local
            shortId
            version
            addresses(page: $page2) {
                content {
                    patient
                    id
                    version
                    asOf
                    type {
                        id
                        description
                    }
                    address1
                    address2
                    city
                    county {
                        id
                        description
                    }
                    state {
                        id
                        description
                    }
                    zipcode
                    country {
                        id
                        description
                    }
                    censusTract
                    comment
                }
                total
                number
                size
            }
        }
    }
`;

export function useFindPatientProfileAddresses(
    baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(Query, options);
}
