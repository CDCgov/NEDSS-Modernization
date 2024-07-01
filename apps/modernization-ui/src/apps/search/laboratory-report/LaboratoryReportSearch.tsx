import { SearchLayout } from 'apps/search/layout';
import { FormAccordion } from './FormAccordion';
import { useForm } from 'react-hook-form';
import { LabReportFilterEntry } from './labReportFormTypes';
import { initialEntry } from './initiaEntry';
import { SearchCriteriaProvider } from 'providers/SearchCriteriaContext';

const LaboratoryReportSearch = () => {
    const labReportForm = useForm<LabReportFilterEntry>({
        defaultValues: initialEntry(),
        mode: 'onBlur'
    });

    return (
        <SearchCriteriaProvider>
            <SearchLayout
                criteria={() => <FormAccordion form={labReportForm} />}
                resultsAsList={() => <div>result list</div>}
                resultsAsTable={() => <div>result table</div>}
                onSearch={() => {}}
                onClear={() => {}}
            />
        </SearchCriteriaProvider>
    );
};

export { LaboratoryReportSearch };
