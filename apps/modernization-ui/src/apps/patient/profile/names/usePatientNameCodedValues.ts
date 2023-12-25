import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query names {
        nameTypes {
            value
            name
        }
        prefixes {
            value
            name
        }
        suffixes {
            value
            name
        }
        degrees {
            value
            name
        }
    }
`;

type Variables = {};

type Result = {
    __typename?: 'Query';
    nameTypes: Array<CodedValue>;
    prefixes: Array<CodedValue>;
    suffixes: Array<CodedValue>;
    degrees: Array<CodedValue>;
};

export function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initialCoded = {
    types: [],
    prefixes: [],
    suffixes: [],
    degrees: []
};

type PatientNameCodedValues = {
    types: CodedValue[];
    prefixes: CodedValue[];
    suffixes: CodedValue[];
    degrees: CodedValue[];
};

const usePatientNameCodedValues = () => {
    const [coded, setCoded] = useState<PatientNameCodedValues>(initialCoded);

    const handleComplete = (data: Result) => {
        setCoded({
            types: data.nameTypes,
            prefixes: data.prefixes,
            degrees: data.degrees,
            suffixes: data.suffixes
        });
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { usePatientNameCodedValues };
export type { PatientNameCodedValues };
