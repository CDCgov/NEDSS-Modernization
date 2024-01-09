import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { Page, PatientIdentificationResults } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($patient: ID!, $page: Page!) {
        findPatientProfile(patient: $patient) {
            id
            identification(page: $page) {
                content {
                    patient
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

type Variables = {
    patient: string;
    page: Page;
};

type PatientIdentificationResult = {
    findPatientProfile: {
        identification: PatientIdentificationResults;
    };
};

export function useFindPatientProfileIdentifications(
    baseOptions?: Apollo.QueryHookOptions<PatientIdentificationResult, Variables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientIdentificationResult, Variables>(Query, options);
}

export type { PatientIdentificationResult };
