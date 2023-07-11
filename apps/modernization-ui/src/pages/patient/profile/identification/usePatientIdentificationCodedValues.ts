import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query identification {
        identificationTypes {
            value
            name
        }
        assigningAuthorities {
            value
            name
        }
    }
`;

type Variables = {};

type Result = {
    __typename?: 'Query';
    identificationTypes: Array<CodedValue>;
    assigningAuthorities: Array<CodedValue>;
};

export function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initialCoded = {
    types: [],
    authorities: []
};

type PatientIdentificationCodedValues = {
    types: CodedValue[];
    authorities: CodedValue[];
};

const usePatientIdentificationCodedValues = () => {
    const [coded, setCoded] = useState<PatientIdentificationCodedValues>(initialCoded);

    const handleComplete = (data: Result) => {
        setCoded({
            types: data.identificationTypes,
            authorities: data.assigningAuthorities
        });
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { usePatientIdentificationCodedValues };
export type { PatientIdentificationCodedValues };
