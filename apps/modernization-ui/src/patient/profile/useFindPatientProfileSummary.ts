import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { FindPatientProfileQuery, FindPatientProfileQueryVariables } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($asOf: DateTime, $patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            local
            shortId
            version
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
                race
                phone {
                    use
                    number
                }
                email {
                    use
                    address
                }
                address {
                    street
                    city
                    state
                    zipcode
                    country
                }
            }
        }
    }
`;

export function useFindPatientProfileSummary(
    baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(Query, options);
}
