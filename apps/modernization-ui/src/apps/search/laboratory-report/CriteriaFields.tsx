import { LabReportFilterEntry } from 'apps/search/laboratory-report/labReportFormTypes';
import { CodedResultsAutocomplete } from 'options/autocompete/CodedResultsAutocomplete';
import { ResultedTestsAutocomplete } from 'options/autocompete/ResultedTestsAutocomplete';
import { Controller, UseFormReturn } from 'react-hook-form';

type LabReportCriteriaFieldsProps = {
    form: UseFormReturn<LabReportFilterEntry>;
};
export const LabReportCriteriaFields = ({ form }: LabReportCriteriaFieldsProps) => {
    return (
        <>
            <Controller
                control={form.control}
                name={'resultedTest'}
                render={({ field: { onChange, name } }) => (
                    <ResultedTestsAutocomplete id={name} label="Resulted Test" onChange={onChange} />
                )}
            />

            <Controller
                control={form.control}
                name={'codedResult'}
                render={({ field: { onChange, name } }) => (
                    <CodedResultsAutocomplete id={name} label="Coded result/organism" onChange={onChange} />
                )}
            />
        </>
    );
};

type LabReportCriteriaProps = {
    form: UseFormReturn<LabReportFilterEntry>;
};
export const CriteriaFields = ({ form }: LabReportCriteriaProps) => {
    return (
        <div id="criteria">
            <LabReportCriteriaFields form={form} />
        </div>
    );
};
