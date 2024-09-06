import { Controller, useFormContext } from 'react-hook-form';
import { LabReportFilterEntry } from 'apps/search/laboratory-report/labReportFormTypes';
import { CodedResultsAutocomplete } from 'options/autocompete/CodedResultsAutocomplete';
import { SearchCriteria } from 'apps/search/criteria';
import { ResultedTestsAutocomplete } from 'options/autocompete/ResultedTestsAutocomplete';

export const CriteriaFields = () => {
    const form = useFormContext<LabReportFilterEntry, Partial<LabReportFilterEntry>>();

    return (
        <SearchCriteria>
            <Controller
                control={form.control}
                name={'resultedTest'}
                render={({ field: { onChange, name, value } }) => (
                    <ResultedTestsAutocomplete
                        value={value}
                        id={name}
                        label="Resulted test"
                        sizing="compact"
                        onChange={onChange}
                    />
                )}
            />

            <Controller
                control={form.control}
                name={'codedResult'}
                render={({ field: { onChange, name, value } }) => (
                    <CodedResultsAutocomplete
                        value={value}
                        id={name}
                        label="Coded result/organism"
                        sizing="compact"
                        onChange={onChange}
                    />
                )}
            />
        </SearchCriteria>
    );
};
