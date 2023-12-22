import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query names {
        addressTypes {
            value
            name
        }
        addressUses {
            value
            name
        }
    }
`;

type Variables = {};

type Result = {
    __typename?: 'Query';
    addressTypes: Array<CodedValue>;
    addressUses: Array<CodedValue>;
};

export function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initialCoded = {
    types: [],
    uses: []
};

type PatientAddressCodedValues = {
    types: CodedValue[];
    uses: CodedValue[];
};

const usePatientAddressCodedValues = () => {
    const [coded, setCoded] = useState<PatientAddressCodedValues>(initialCoded);

    const handleComplete = (data: Result) => {
        setCoded({
            types: data.addressTypes,
            uses: data.addressUses
        });
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { usePatientAddressCodedValues };
export type { PatientAddressCodedValues };
