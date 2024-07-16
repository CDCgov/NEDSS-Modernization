import { Controller, useFormContext } from 'react-hook-form';
import {
    InvestigationFilterEntry,
    caseStatusOptions,
    investigationStatusOptions,
    notificationStatusOptions,
    processingStatusOptions
} from './InvestigationFormTypes';
import { SingleSelect, MultiSelect } from 'design-system/select';
import { ConceptAutocomplete } from 'options/autocompete/ConceptAutocomplete';

const CriteriaSearchFields = () => {
    const form = useFormContext<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>();

    return (
        <>
            <Controller
                control={form.control}
                name="investigationStatus"
                render={({ field: { onChange, name, value } }) => (
                    <SingleSelect
                        data-testid={name}
                        name={name}
                        value={value}
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
                render={({ field: { name, onBlur } }) => (
                    <ConceptAutocomplete valueSet={'OUTBREAK_NM'} id={name} label={name} onBlur={onBlur} />
                )}
            />

            <Controller
                control={form.control}
                name="caseStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        data-testid={'caseStatuses'}
                        label="Case status"
                        onChange={onChange}
                        name={name}
                        value={value}
                        options={caseStatusOptions}
                        id={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="processingStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        label="Current processing status"
                        onChange={onChange}
                        name={name}
                        value={value}
                        id={name}
                        options={processingStatusOptions}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="notificationStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        label="Notification status"
                        onChange={onChange}
                        name={name}
                        value={value}
                        id={name}
                        options={notificationStatusOptions}
                    />
                )}
            />
        </>
    );
};

export default CriteriaSearchFields;
