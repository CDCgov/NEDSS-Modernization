import { ComboBox, ComboBoxOption, Label } from '@trussworks/react-uswds';
import {
    CodedResult,
    LabReportFilter,
    ResultedTest,
    useFindDistinctCodedResultsLazyQuery,
    useFindDistinctResultedTestLazyQuery
} from 'generated/graphql/schema';
import debounce from 'lodash.debounce';
import { useState } from 'react';
import { Controller, UseFormReturn } from 'react-hook-form';

type LabReportCriteriaProps = {
    form: UseFormReturn<LabReportFilter>;
};
export const LabReportCriteria = ({ form }: LabReportCriteriaProps) => {
    const [getLocalResultedTests] = useFindDistinctResultedTestLazyQuery();
    const [getCodedResultedTests] = useFindDistinctCodedResultsLazyQuery();
    const [codedResults, setCodedResults] = useState<{ label: string; value: string }[]>([]);
    const [resultData, setResultsData] = useState<{ label: string; value: string }[]>([]);

    const removeDuplicates = (items: any) => {
        const newArray: any = [];
        const uniqueObject: any = {};
        for (const i in items) {
            if (items[i]['value']) {
                uniqueObject[items[i]['value']] = items[i];
            }
        }
        for (const i in uniqueObject) {
            if (uniqueObject[i]) {
                newArray.push(uniqueObject[i]);
            }
        }
        return newArray;
    };

    const debouncedCodedSearchResults = debounce(async (criteria: string) => {
        getCodedResultedTests({ variables: { searchText: criteria, loinc: false } }).then((response) => {
            const codedResults = response.data?.findDistinctCodedResults.map(codedResultToComboOption) || [];
            setCodedResults(removeDuplicates(codedResults));
        });
    }, 300);

    const codedResultToComboOption = (codedResult: CodedResult): ComboBoxOption => {
        return { label: codedResult.name, value: codedResult.name };
    };

    const debounceResultedTestSearch = debounce(async (criteria: string) => {
        getLocalResultedTests({ variables: { searchText: criteria, loinc: false } }).then((response) => {
            const resultedTests = response.data?.findDistinctResultedTest.map(labTestToComboOption) || [];
            setResultsData(removeDuplicates(resultedTests));
        });
    }, 300);

    const labTestToComboOption = (resultedTest: ResultedTest): ComboBoxOption => {
        return { label: resultedTest.name, value: resultedTest.name };
    };

    return (
        <>
            <Label htmlFor={'resultedTest'}>Resulted test</Label>
            <Controller
                control={form.control}
                name={'resultedTest'}
                render={({ field: { onChange, value } }) => (
                    <ComboBox
                        key={value}
                        id="resultedTest"
                        name="resultedTest"
                        options={resultData || []}
                        defaultValue={value as string | undefined}
                        onChange={(e) => {
                            debounceResultedTestSearch(e ?? '');
                            onChange(e);
                        }}
                    />
                )}
            />

            <Label htmlFor={'codedResult'}>Coded result/organism</Label>
            <Controller
                control={form.control}
                name={'codedResult'}
                render={({ field: { onChange, value } }) => (
                    <ComboBox
                        key={value}
                        id="codedResult"
                        name="codedResult"
                        options={codedResults || []}
                        defaultValue={value as string | undefined}
                        onChange={(e) => {
                            debouncedCodedSearchResults(e ?? '');
                            onChange(e);
                        }}
                        inputProps={{ onChange: (e) => debouncedCodedSearchResults(e.target.value) }}
                    />
                )}
            />
        </>
    );
};
