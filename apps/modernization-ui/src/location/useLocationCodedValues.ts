import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue } from 'coded';
import { useEffect, useState } from 'react';
import { useCountyCodedValues } from './useCountyCodedValues';

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
    states: {
        all: [],
        byAbbreviation: () => null,
        byValue: () => null
    },
    counties: {
        byState: (state?: string) => useCountyCodedValues(state).counties
    }
};

type StateCodedValues = {
    all: StateCodedValue[];
    byAbbreviation: (_abbreviation: string) => StateCodedValue | null;
    byValue: (_value?: string | null) => StateCodedValue | null;
};

type CountyCodedValues = {
    byState: (state?: string) => CodedValue[];
};

type LocationCodedValues = {
    countries: CodedValue[];
    states: StateCodedValues;
    counties: CountyCodedValues;
};

const useLocationCodedValues = () => {
    const [coded, setCoded] = useState<LocationCodedValues>(initial);

    const handleCompleted = (values: Result) => {
        setCoded({
            ...initial,
            countries: values.countries,
            states: {
                all: values.states,
                byAbbreviation: (abbreviation: string) =>
                    values.states.find((state) => state.abbreviation === abbreviation) ?? null,
                byValue: (value?: string | null) => values.states.find((state) => state.value === value) ?? null
            }
        });
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleCompleted });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { useLocationCodedValues, initial };
export type { LocationCodedValues, StateCodedValue, StateCodedValues, CountyCodedValues };
