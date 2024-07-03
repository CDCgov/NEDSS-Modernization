import { handleChangeToDefaultValue } from 'forms/event';
import { UseFormReturn, Controller } from 'react-hook-form';
import {
    InvestigationFilterEntry,
    caseStatusOptions,
    investigationStatusOptions,
    notificationStatusOptions,
    processingStatusOptions
} from './InvestigationFormTypes';
import { SingleSelect, MultiSelect } from 'design-system/select';
import { useContext } from 'react';
import { InvestigationFormContext } from './InvestigationSearch';

const CriteriaSearchFields = () => {
    const form = useContext(InvestigationFormContext);

    return (
        <>
            <Controller
                control={form.control}
                name="investigationStatus"
                render={({ field: { onChange, name } }) => (
                    <SingleSelect
                        data-testid={name}
                        name={name}
                        onChange={onChange}
                        label="Investigation status"
                        options={investigationStatusOptions}
                        id={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="outbreaks"
                render={({ field: { name } }) => (
                    <SingleSelect
                        name={name}
                        label="Outbreak names"
                        options={[
                            {
                                name: 'Coming soon',
                                value: '',
                                label: 'coming soon'
                            }
                        ]}
                        id={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="caseStatuses"
                render={({ field: { onChange, name } }) => (
                    <MultiSelect
                        data-testid={'caseStatuses'}
                        label="Case status"
                        onChange={onChange}
                        name={name}
                        options={caseStatusOptions}
                        id={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="processingStatuses"
                render={({ field: { onChange, name } }) => (
                    <MultiSelect
                        label="Current processing status"
                        onChange={onChange}
                        name={name}
                        id={name}
                        options={processingStatusOptions}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="notificationStatuses"
                render={({ field: { onChange, name } }) => (
                    <MultiSelect
                        label="Notification status"
                        onChange={onChange}
                        name={name}
                        id={name}
                        options={notificationStatusOptions}
                    />
                )}
            />
        </>
    );
};

export default CriteriaSearchFields;
