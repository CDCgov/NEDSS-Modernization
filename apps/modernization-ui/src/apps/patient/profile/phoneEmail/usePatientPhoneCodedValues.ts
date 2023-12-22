import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query names {
        phoneTypes {
            value
            name
        }
        phoneUses {
            value
            name
        }
    }
`;

type Variables = {};

type Result = {
    __typename?: 'Query';
    phoneTypes: Array<CodedValue>;
    phoneUses: Array<CodedValue>;
};

export function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initialCoded = {
    types: [],
    uses: []
};

type PatientPhoneCodedValues = {
    types: CodedValue[];
    uses: CodedValue[];
};

const usePatientPhoneCodedValues = () => {
    const [coded, setCoded] = useState<PatientPhoneCodedValues>(initialCoded);

    const handleComplete = (data: Result) => {
        setCoded({
            types: data.phoneTypes,
            uses: data.phoneUses
        });
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { usePatientPhoneCodedValues };
export type { PatientPhoneCodedValues };
