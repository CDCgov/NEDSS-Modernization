import { SearchLayout } from 'apps/search/layout';
import { FormAccordion } from './FormAccordion';
import { useForm } from 'react-hook-form';
import { LabReportFilterEntry } from './labReportFormTypes';
import { initialEntry } from './initiaEntry';

const LaboratoryReportSearch = () => {
    const labReportForm = useForm<LabReportFilterEntry>({
        defaultValues: initialEntry(),
        mode: 'onBlur'
    });

    return (
        <SearchLayout
            criteria={() => <FormAccordion form={labReportForm} />}
            resultsAsList={() => <div>result list</div>}
            resultsAsTable={() => <div>result table</div>}
            onSearch={() => {}}
            onClear={() => {}}
        />
    );
};

export { LaboratoryReportSearch };
