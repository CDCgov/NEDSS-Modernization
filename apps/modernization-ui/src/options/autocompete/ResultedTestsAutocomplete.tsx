import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { FindDistinctResultedTestQuery, useFindDistinctResultedTestLazyQuery } from 'generated/graphql/schema';
import { Selectable } from 'options/selectable';

const ResultedTestsAutocomplete = ({ id, label, onChange, ...rest }: AutocompleteSingleProps) => {
    const [getLocalResultedTests] = useFindDistinctResultedTestLazyQuery();

    const labTestToComboOption = (test: any): Selectable => ({
        name: test.name,
        value: test.name,
        label: test.name
    });

    const onCompleteResultedTests = (response: FindDistinctResultedTestQuery) => {
        const resultedTests = response.findDistinctResultedTest.map(labTestToComboOption) || [];
        return resultedTests;
    };

    const resolver = async (criteria: string): Promise<Selectable[]> => {
        try {
            const response = await getLocalResultedTests({
                variables: {
                    searchText: criteria
                }
            });

            if (response.data) {
                return onCompleteResultedTests(response.data);
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

export { ResultedTestsAutocomplete };
