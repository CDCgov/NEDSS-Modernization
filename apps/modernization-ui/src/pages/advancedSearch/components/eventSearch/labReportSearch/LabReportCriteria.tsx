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

type LabReportCriteriaFieldsProps = {
    form: UseFormReturn<LabReportFilter>;
    resultedTestOptions: { label: string; value: string }[];
    codedResultOptions: { label: string; value: string }[];
    resultedTestSearch: (search: string) => void;
    codedResultSearch: (search: string) => void;
};
export const LabReportCriteriaFields = ({
    form,
    resultedTestOptions,
    codedResultOptions,
    resultedTestSearch,
    codedResultSearch
}: LabReportCriteriaFieldsProps) => {
    return (
        <>
            <Label htmlFor={'resultedTest'}>Resulted test</Label>
            {resultedTestOptions ? (
                <Controller
                    control={form.control}
                    name={'resultedTest'}
                    render={({ field: { onChange, value, name } }) => (
                        <ComboBox
                            key={value}
                            id="resultedTest"
                            name="resultedTest"
                            options={resultedTestOptions}
                            defaultValue={value ?? undefined}
                            data-testid={name}
                            onChange={(e) => {
                                onChange(e);
                                resultedTestSearch(e ?? '');
                            }}
                        />
                    )}
                />
            ) : null}

            <Label htmlFor={'codedResult'}>Coded result/organism</Label>
            {codedResultOptions ? (
                <Controller
                    control={form.control}
                    name={'codedResult'}
                    render={({ field: { onChange, value } }) => (
                        <ComboBox
                            key={value}
                            id="codedResult"
                            name="codedResult"
                            options={codedResultOptions}
                            defaultValue={value ?? undefined}
                            onChange={(e) => {
                                onChange(e);
                                codedResultSearch(e ?? '');
                            }}
                        />
                    )}
                />
            ) : null}
        </>
    );
};

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
            <LabReportCriteriaFields
                form={form}
                codedResultOptions={codedResults ?? []}
                resultedTestOptions={resultData ?? []}
                resultedTestSearch={debounceResultedTestSearch}
                codedResultSearch={debouncedCodedSearchResults}
            />
        </div>
    );
};
