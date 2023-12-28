import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { Page, PatientAddressResults } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($page: Page, $patient: ID!) {
        findPatientProfile(patient: $patient) {
            id
            addresses(page: $page) {
                content {
                    patient
                    id
                    version
                    asOf
                    type {
                        id
                        description
                    }
                    use {
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

type PatientProfileAddressesVariables = {
    patient: string;
    page?: Page;
};

type PatientProfileAddressesResult = {
    __typename?: 'Query';
    findPatientProfile?: {
        __typename?: 'PatientProfile';
        id: string;
        addresses: PatientAddressResults;
    };
};

export function useFindPatientProfileAddresses(
    baseOptions?: Apollo.QueryHookOptions<PatientProfileAddressesResult, PatientProfileAddressesVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientProfileAddressesResult, PatientProfileAddressesVariables>(Query, options);
}

export type { PatientProfileAddressesVariables, PatientProfileAddressesResult };
