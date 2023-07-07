import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { PatientBirth, PatientGender } from 'generated/graphql/schema';

export const Query = gql`
    query findPatientProfile($patient: ID!) {
        findPatientProfile(patient: $patient) {
            id
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
                birthOrder
                city
                state {
                    id
                    description
                }
                country {
                    id
                    description
                }
                county {
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

type PatientProfileBirthAndGenderVariables = {
    patient: string;
};

type PatientProfileBirthAndGenderResult = {
    __typename?: 'Query';
    findPatientProfile?: {
        __typename?: 'PatientProfile';
        id: string;
        birth: PatientBirth;
        gender: PatientGender;
    };
};

export function useFindPatientProfileBirth(
    baseOptions?: Apollo.QueryHookOptions<PatientProfileBirthAndGenderResult, PatientProfileBirthAndGenderVariables>
) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientProfileBirthAndGenderResult, PatientProfileBirthAndGenderVariables>(
        Query,
        options
    );
}

export type { PatientProfileBirthAndGenderVariables, PatientProfileBirthAndGenderResult };
