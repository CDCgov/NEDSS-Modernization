import { ReactNode } from 'react';
import { FormProvider, useForm } from 'react-hook-form';

import { LabReportFilterEntry, initial } from './labReportFormTypes';

const LabratorySearchCriteriaFormWrapper = ({ children }: { children: ReactNode }) => {
    const form = useForm<LabReportFilterEntry, Partial<LabReportFilterEntry>>({
        defaultValues: initial,
        mode: 'onBlur'
    });

    return <FormProvider {...form}>{children}</FormProvider>;
};

export { LabratorySearchCriteriaFormWrapper };
