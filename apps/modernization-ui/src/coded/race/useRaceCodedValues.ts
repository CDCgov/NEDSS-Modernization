import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { CodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query raceValues {
        raceCategories {
            value
            name
        }
    }
`;

type Variables = { [key: string]: never };

type Result = {
    __typename?: 'Query';
    raceCategories: Array<CodedValue>;
};

export function useCodedValueQuery(baseOptions?: Apollo.QueryHookOptions<Result, Variables>) {
    const options = { ...baseOptions };
    return Apollo.useLazyQuery<Result, Variables>(Query, options);
}

const useRaceCodedValues = () => {
    const [coded, setCoded] = useState<CodedValue[]>([]);

    const handleComplete = (data: Result) => {
        const raceCategories = (data.raceCategories || []).filter((category) => category.value !== 'M');
        setCoded(raceCategories);
    };

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        getCodedValues();
    }, []);

    return coded;
};

export { useRaceCodedValues };
