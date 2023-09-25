import { ComboBoxOption, Label } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
import {
    CodedResult,
    FindDistinctCodedResultsQuery,
    FindDistinctResultedTestQuery,
    LabReportFilter,
    ResultedTest,
    useFindDistinctCodedResultsLazyQuery,
    useFindDistinctResultedTestLazyQuery
} from 'generated/graphql/schema';
import debounce from 'lodash.debounce';
import { ReactNode, useEffect, useState } from 'react';
import { Controller, UseFormReturn } from 'react-hook-form';
import { Suggestions } from 'suggestion/Suggestions';

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
    const renderSuggestion = (suggestion: { label: string; value: string }): ReactNode => {
        return <>{suggestion.label}</>;
    };

    return (
        <>
            <Label htmlFor={'resultedTest'}>Resulted test</Label>
            {resultedTestOptions ? (
                <Controller
                    control={form.control}
                    name={'resultedTest'}
                    render={({ field: { onChange, value, name } }) => (
                        <>
                            <Input
                                id={name}
                                htmlFor={name}
                                type="text"
                                defaultValue={value}
                                autoComplete="off"
                                onChange={(e: any) => {
                                    resultedTestSearch(e.target.value);
                                    onChange(e.target.value === '' ? undefined : e.target.value);
                                }}
                            />
                            <Suggestions
                                id={`${name}-suggestions`}
                                suggestions={resultedTestOptions}
                                renderSuggestion={renderSuggestion}
                                onSelection={(e) => onChange(e.value)}
                            />
                        </>
                    )}
                />
            ) : null}

            <Label htmlFor={'codedResult'}>Coded result/organism</Label>
            {codedResultOptions ? (
                <Controller
                    control={form.control}
                    name={'codedResult'}
                    render={({ field: { onChange, value, name } }) => (
                        <>
                            <Input
                                id={name}
                                htmlFor={name}
                                type="text"
                                defaultValue={value}
                                autoComplete="off"
                                onChange={(e: any) => {
                                    codedResultSearch(e.target.value);
                                    onChange(e.target.value === '' ? undefined : e.target.value);
                                }}
                            />
                            <Suggestions
                                id={`${name}-suggestions`}
                                suggestions={codedResultOptions}
                                renderSuggestion={renderSuggestion}
                                onSelection={(e) => onChange(e.value)}
                            />
                        </>
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
    const onCompleteResultedTests = (response: FindDistinctResultedTestQuery) => {
        const resultedTests = response.findDistinctResultedTest.map(labTestToComboOption) || [];
        setResultedTestOptions(resultedTests);
    };
    const onCompleteCodedResults = (response: FindDistinctCodedResultsQuery): void => {
        const codedResults = response.findDistinctCodedResults.map(codedResultToComboOption) || [];
        setCodedResultOptions(codedResults);
    };
    const [getLocalResultedTests] = useFindDistinctResultedTestLazyQuery({ onCompleted: onCompleteResultedTests });
    const [getCodedResultedTests] = useFindDistinctCodedResultsLazyQuery({ onCompleted: onCompleteCodedResults });
    const [codedResultOptions, setCodedResultOptions] = useState<{ label: string; value: string }[]>();
    const [resultedTestOptions, setResultedTestOptions] = useState<{ label: string; value: string }[]>();

    // Initialize values for dropdowns
    useEffect(() => {
        debounceResultedTestSearch(form.getValues('resultedTest') ?? '');
        debouncedCodedSearchResults(form.getValues('codedResult') ?? '');
    }, []);

    const debouncedCodedSearchResults = debounce(async (criteria: string) => {
        getCodedResultedTests({ variables: { searchText: criteria, snomed: false } });
    }, 300);

    const codedResultToComboOption = (codedResult: CodedResult): ComboBoxOption => {
        return { label: codedResult.name, value: codedResult.name };
    };

    const debounceResultedTestSearch = debounce(async (criteria: string) => {
        getLocalResultedTests({ variables: { searchText: criteria, loinc: false } });
    }, 300);

    const labTestToComboOption = (resultedTest: ResultedTest): ComboBoxOption => {
        return { label: resultedTest.name, value: resultedTest.name };
    };

    return (
        <div id="criteria">
            <LabReportCriteriaFields
                form={form}
                codedResultOptions={codedResultOptions ?? []}
                resultedTestOptions={resultedTestOptions ?? []}
                resultedTestSearch={debounceResultedTestSearch}
                codedResultSearch={debouncedCodedSearchResults}
            />
        </div>
    );
};
