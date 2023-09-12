import { ComboBox, ComboBoxOption, Label } from '@trussworks/react-uswds';
import {
    CodedResult,
    LabReportFilter,
    ResultedTest,
    useFindDistinctCodedResultsLazyQuery,
    useFindDistinctResultedTestLazyQuery
} from 'generated/graphql/schema';
import debounce from 'lodash.debounce';
import { useEffect, useState } from 'react';
import { Controller, UseFormReturn } from 'react-hook-form';

type LabReportCriteriaProps = {
    form: UseFormReturn<LabReportFilter>;
};
export const LabReportCriteria = ({ form }: LabReportCriteriaProps) => {
    const [getLocalResultedTests] = useFindDistinctResultedTestLazyQuery();
    const [getCodedResultedTests] = useFindDistinctCodedResultsLazyQuery();
    const [codedResults, setCodedResults] = useState<{ label: string; value: string }[]>();
    const [resultData, setResultsData] = useState<{ label: string; value: string }[]>();

    // Initialize values for dropdowns
    useEffect(() => {
        debounceResultedTestSearch(form.getValues('resultedTest') ?? '');
        debouncedCodedSearchResults(form.getValues('codedResult') ?? '');
    }, []);

    const debouncedCodedSearchResults = debounce(async (criteria: string) => {
        getCodedResultedTests({ variables: { searchText: criteria, loinc: false } }).then((response) => {
            const codedResults = response.data?.findDistinctCodedResults.map(codedResultToComboOption) || [];
            setCodedResults(codedResults);
        });
    }, 300);

    const codedResultToComboOption = (codedResult: CodedResult): ComboBoxOption => {
        return { label: codedResult.name, value: codedResult.name };
    };

    const debounceResultedTestSearch = debounce(async (criteria: string) => {
        getLocalResultedTests({ variables: { searchText: criteria, loinc: false } }).then((response) => {
            const resultedTests = response.data?.findDistinctResultedTest.map(labTestToComboOption) || [];
            setResultsData(resultedTests);
        });
    }, 300);

    const labTestToComboOption = (resultedTest: ResultedTest): ComboBoxOption => {
        return { label: resultedTest.name, value: resultedTest.name };
    };

    return (
        <div id="criteria">
            <Label htmlFor={'resultedTest'}>Resulted test</Label>
            {resultData ? (
                <Controller
                    control={form.control}
                    name={'resultedTest'}
                    render={({ field: { onChange, value } }) => (
                        <>
                            Value: {value}
                            <ComboBox
                                key={value}
                                id="resultedTest"
                                name="resultedTest"
                                options={resultData}
                                defaultValue={value ?? undefined}
                                onChange={(e) => {
                                    debounceResultedTestSearch(e ?? '');
                                    onChange(e);
                                }}
                            />
                        </>
                    )}
                />
            ) : null}

            <Label htmlFor={'codedResult'}>Coded result/organism</Label>
            {codedResults ? (
                <Controller
                    control={form.control}
                    name={'codedResult'}
                    render={({ field: { onChange, value } }) => (
                        <>
                            Value: {value}
                            <ComboBox
                                key={value}
                                id="codedResult"
                                name="codedResult"
                                options={codedResults}
                                defaultValue={value ?? undefined}
                                onChange={(e) => {
                                    debouncedCodedSearchResults(e ?? '');
                                    onChange(e);
                                }}
                            />
                        </>
                    )}
                />
            ) : null}
        </div>
    );
};
