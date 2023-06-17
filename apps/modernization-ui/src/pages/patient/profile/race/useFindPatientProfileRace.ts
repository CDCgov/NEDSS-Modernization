import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { FindPatientProfileQuery, FindPatientProfileQueryVariables } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($page5: Page, $patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            local
            shortId
            version
            races(page: $page5) {
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

export function useFindPatientProfileRace(
    baseOptions?: Apollo.QueryHookOptions<FindPatientProfileQuery, FindPatientProfileQueryVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<FindPatientProfileQuery, FindPatientProfileQueryVariables>(Query, options);
}
