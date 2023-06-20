import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { Page, PatientPhoneResults } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($page: Page, $patient: ID!) {
        findPatientProfile(patient: $patient) {
            id
            phones(page: $page) {
                content {
                    patient
                    id
                    version
                    asOf
                    countryCode
                    number
                    extension
                    email
                    url
                    comment
                    type {
                        id
                        description
                    }
                    use {
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

type PatientProfilePhoneEmailVariables = {
    patient: string;
    page?: Page;
};

type PatientProfilePhoneEmailResult = {
    __typename?: 'Query';
    findPatientProfile?: {
        __typename?: 'PatientProfile';
        id: string;
        phones: PatientPhoneResults;
    };
};

export function useFindPatientProfilePhoneAndEmail(
    baseOptions?: Apollo.QueryHookOptions<PatientProfilePhoneEmailResult, PatientProfilePhoneEmailVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientProfilePhoneEmailResult, PatientProfilePhoneEmailVariables>(Query, options);
}

export type { PatientProfilePhoneEmailVariables, PatientProfilePhoneEmailResult };
