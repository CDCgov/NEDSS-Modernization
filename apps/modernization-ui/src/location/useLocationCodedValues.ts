import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query locations {
        countries {
            value
            name
        }
        states {
            value
            name
            abbreviation
        }
    }
`;

type Variables = { [key: string]: never };

type StateCodedValue = CodedValue & { abbreviation: string };

type Result = {
    __typename?: 'Query';
    countries: Array<CodedValue>;
    states: Array<StateCodedValue>;
};

function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const initial = {
    countries: [],
    states: []
};

type LocationCodedValues = {
    countries: CodedValue[];
    states: StateCodedValue[];
};

const useLocationCodedValues = () => {
    const [coded, setCoded] = useState<LocationCodedValues>(initial);

    const [getCodedValues] = useCodedValueQuery({ onCompleted: setCoded });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { useLocationCodedValues };
export type { LocationCodedValues, StateCodedValue };
