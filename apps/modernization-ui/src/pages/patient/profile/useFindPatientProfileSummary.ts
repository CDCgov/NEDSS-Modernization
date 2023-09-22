import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';

export const Query = gql`
    query findPatientProfile($asOf: DateTime, $patient: ID, $shortId: Int) {
        findPatientProfile(patient: $patient, shortId: $shortId) {
            id
            local
            shortId
            version
            deletable
            status
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
                identification {
                    type
                    value
                }
            }
        }
    }
`;

type PatientLegalName = {
    __typename: 'PatientLegalName';
    prefix: string | null;
    first: string | null;
    middle: string | null;
    last: string | null;
    suffix: string | null;
};

type PatientSummaryAddress = {
    __typename: 'PatientSummaryAddress';
    street: string | null;
    city: string | null;
    state: string | null;
    zipcode: string | null;
    country: string | null;
};

type PatientSummaryPhone = { __typename: 'PatientSummaryPhone'; use: string; number: string };

type PatientSummaryEmail = { __typename: 'PatientSummaryEmail'; use: string; address: string };

type PatientSummaryIdentification = {
    __typename: 'PatientSummaryIdentification';
    type: string;
    value: string;
};

type PatientSummary = {
    __typename: 'PatientSummary';
    birthday: any | null;
    age: number | null;
    gender: string | null;
    ethnicity: string | null;
    race: string | null;
    legalName: PatientLegalName | null;
    phone: PatientSummaryPhone[];
    email: PatientSummaryEmail[];
    identification: PatientSummaryIdentification[];
    address: PatientSummaryAddress | null;
};

type WithPatientSummary = {
    summary: PatientSummary;
};

type PatientProfile = {
    __typename: 'PatientProfile';
    id: string;
    local: string;
    shortId: number;
    version: number;
    status: string;
    deletable: boolean;
};

type PatientProfileResult = {
    __typename: 'Query';
    findPatientProfile?: (PatientProfile & WithPatientSummary) | null | undefined;
};

type Variables = {
    asOf?: Date;
    patient?: string;
    shortId?: number;
};

function useFindPatientProfileSummary(baseOptions?: Apollo.QueryHookOptions<PatientProfileResult, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<PatientProfileResult, Variables>(Query, options);
}

export { useFindPatientProfileSummary };
export type { PatientProfileResult };
