import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { GroupedCodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query detailedRaces($category: String) {
        detailedRaces(category: $category) {
            value
            name
            group
        }
    }
`;

type Variables = { category?: string | undefined };

type Result = {
    detailedRaces: GroupedCodedValue[];
};

const useCodedValueQuery = (baseOptions?: Apollo.QueryHookOptions<Result, Variables>) =>
    Apollo.useLazyQuery<Result, Variables>(Query, { ...baseOptions });

const useDetailedRaceCodedValues = (category?: string | undefined) => {
    const [coded, setCoded] = useState<GroupedCodedValue[]>([]);

    const handleComplete = (result: Result) => setCoded(result.detailedRaces);

    const [getCodedValues] = useCodedValueQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (category) {
            getCodedValues({
                variables: {
                    category: category
                }
            });
        } else {
            setCoded([]);
        }
    }, [category]);

    return coded;
};

export { useDetailedRaceCodedValues };
