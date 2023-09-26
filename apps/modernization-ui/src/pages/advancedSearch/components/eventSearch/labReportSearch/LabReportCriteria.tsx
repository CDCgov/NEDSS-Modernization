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
import { FocusEvent, ReactNode, useRef, useState } from 'react';
import { Controller, UseFormReturn } from 'react-hook-form';
import { Suggestions } from 'suggestion/Suggestions';

type LabReportCriteriaFieldsProps = {
    form: UseFormReturn<LabReportFilter>;
    resultedTestOptions: { label: string; value: string }[];
    codedResultOptions: { label: string; value: string }[];
    onResultedTestSearch: (search: string) => void;
    onCodedResultSearch: (search: string) => void;
};
export const LabReportCriteriaFields = ({
    form,
    resultedTestOptions,
    codedResultOptions,
    onResultedTestSearch,
    onCodedResultSearch
}: LabReportCriteriaFieldsProps) => {
    const resultedTestRef = useRef<HTMLInputElement>(null);
    const codedResultRef = useRef<HTMLInputElement>(null);
    const renderSuggestion = (suggestion: { label: string; value: string }): ReactNode => {
        return <>{suggestion.label}</>;
    };

    const onResultedTestChange = (value: string, onChange: any) => {
        onResultedTestSearch(value);
        if (value === '') {
            // resets to undefined instead of empty string
            form.resetField('resultedTest');
        } else {
            onChange(value);
        }
    };
    const onCodedResultChange = (value: string, onChange: any) => {
        onCodedResultSearch(value);
        if (value === '') {
            form.resetField('codedResult');
        } else {
            onChange(value);
        }
    };
    // Lists are not populated on load, await user focus to initialize
    const onResultedTestFocus = (event: FocusEvent<HTMLInputElement>) => {
        if (resultedTestOptions.length === 0) {
            onResultedTestSearch(event.target.value);
        }
    };
    const onCodedResultFocus = (event: FocusEvent<HTMLInputElement>) => {
        if (codedResultOptions.length === 0) {
            onCodedResultSearch(event.target.value);
        }
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
                                data-testid={name}
                                type="text"
                                textInputRef={resultedTestRef}
                                defaultValue={value}
                                autoComplete="off"
                                onFocus={onResultedTestFocus}
                                onChange={(e: any) => {
                                    onResultedTestChange(e.target.value, onChange);
                                }}
                            />
                            {document.activeElement === resultedTestRef.current ? (
                                <Suggestions
                                    id={`${name}-suggestions`}
                                    suggestions={resultedTestOptions}
                                    renderSuggestion={renderSuggestion}
                                    onSelection={(e) => onChange(e.value)}
                                />
                            ) : null}
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
                                data-testid={name}
                                type="text"
                                textInputRef={codedResultRef}
                                defaultValue={value}
                                autoComplete="off"
                                onFocus={onCodedResultFocus}
                                onChange={(e: any) => {
                                    onCodedResultChange(e.target.value, onChange);
                                }}
                            />
                            {document.activeElement === codedResultRef.current ? (
                                <Suggestions
                                    id={`${name}-suggestions`}
                                    suggestions={codedResultOptions}
                                    renderSuggestion={renderSuggestion}
                                    onSelection={(e) => onChange(e.value)}
                                />
                            ) : null}
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
                onResultedTestSearch={debounceResultedTestSearch}
                onCodedResultSearch={debouncedCodedSearchResults}
            />
        </div>
    );
};
