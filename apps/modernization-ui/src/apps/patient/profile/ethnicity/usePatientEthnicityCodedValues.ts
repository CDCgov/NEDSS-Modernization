import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query ethnicityValues {
        ethnicGroups {
            value
            name
        }
        ethnicityUnknownReasons {
            value
            name
        }
        detailedEthnicities {
            value
            name
        }
    }
`;

type Variables = { [key: string]: never };

type Result = {
    __typename?: 'Query';
    ethnicGroups: Array<CodedValue>;
    ethnicityUnknownReasons: Array<CodedValue>;
    detailedEthnicities: Array<CodedValue>;
};

export function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initialCoded = {
    ethnicGroups: [],
    ethnicityUnknownReasons: [],
    detailedEthnicities: []
};

type PatientEthnicityCodedValue = {
    ethnicGroups: CodedValue[];
    ethnicityUnknownReasons: CodedValue[];
    detailedEthnicities: CodedValue[];
};

const usePatientEthnicityCodedValues = () => {
    const [coded, setCoded] = useState<PatientEthnicityCodedValue>(initialCoded);

    const handleComplete = (data: Result) => {
        setCoded({
            ...data
        });
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { usePatientEthnicityCodedValues };
export type { PatientEthnicityCodedValue };
