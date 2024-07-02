import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { FindDistinctCodedResultsQuery, useFindDistinctCodedResultsLazyQuery } from 'generated/graphql/schema';
import { Selectable } from 'options/selectable';

const CodedResultsAutocomplete = ({ id, label, onChange, ...rest }: AutocompleteSingleProps) => {
    const [getCodedResults] = useFindDistinctCodedResultsLazyQuery();

    const labTestToComboOption = (test: any): Selectable => ({
        name: test.name,
        value: test.name,
        label: test.name
    });

    const onCompleteCodedResults = (response: FindDistinctCodedResultsQuery) => {
        const resultedTests = response.findDistinctCodedResults.map(labTestToComboOption) || [];
        return resultedTests;
    };

    const resolver = async (criteria: string): Promise<Selectable[]> => {
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
    };

    return <Autocomplete resolver={resolver} onChange={onChange} id={id} label={label} {...rest} />;
};

export { CodedResultsAutocomplete };
