import { gql } from '@apollo/client';
import * as Apollo from '@apollo/client';
import { GroupedCodedValue } from 'coded';
import { useEffect, useState } from 'react';

const Query = gql`
    query counties($state: String) {
        counties(state: $state) {
            value
            name
            group
        }
    }
`;

type Variables = { state?: string | undefined };

type Result = {
    counties: GroupedCodedValue[];
};

const useCodedValueQuery = (baseOptions?: Apollo.QueryHookOptions<Result, Variables>) =>
    Apollo.useLazyQuery<Result, Variables>(Query, { ...baseOptions });

const initial = {
    counties: []
};

type CountiesCodedValues = {
    counties: GroupedCodedValue[];
};

const useCountyCodedValues = (state?: string | undefined | null) => {
    const [coded, setCoded] = useState<CountiesCodedValues>(initial);

    const [getCodedValues] = useCodedValueQuery({ onCompleted: setCoded });

    useEffect(() => {
        if (state) {
            getCodedValues({
                variables: {
                    state: state
                }
            });
        } else {
            setCoded(initial);
        }
    }, [state]);

    return coded;
};

export { useCountyCodedValues };
export type { CountiesCodedValues };
