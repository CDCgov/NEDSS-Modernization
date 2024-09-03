import React, { useCallback } from 'react';
import {
    AutoCompleteWithString,
    AutoCompleteWithStringProps
} from 'design-system/autocomplete/autoCompleteWithString/AutoCompleteWithString';
import { FindDistinctCodedResultsQuery, useFindDistinctCodedResultsLazyQuery } from 'generated/graphql/schema';
import { Selectable } from 'options/selectable';

const CodedResultsAutocomplete = (props: Omit<AutoCompleteWithStringProps, 'resolver'>) => {
    const [getCodedResults] = useFindDistinctCodedResultsLazyQuery();

    const labTestToComboOption = (test: any): Selectable => ({
        name: test.name,
        value: test.name,
        label: test.name
    });

    const onCompleteCodedResults = useCallback(
        (response: FindDistinctCodedResultsQuery) => {
            return response.findDistinctCodedResults.map(labTestToComboOption) || [];
        },
        [labTestToComboOption]
    );

    const resolver = useCallback(
        async (criteria: string): Promise<Selectable[]> => {
            try {
                const response = await getCodedResults({
                    variables: {
                        searchText: criteria
                    }
                });

                if (response.data) {
                    return onCompleteCodedResults(response.data);
                } else {
                    return [];
                }
            } catch (error) {
                console.error('Error fetching resulted tests:', error);
                return [];
            }
        },
        [getCodedResults, onCompleteCodedResults]
    );

    return <AutoCompleteWithString resolver={resolver} {...props} />;
};

export { CodedResultsAutocomplete };
