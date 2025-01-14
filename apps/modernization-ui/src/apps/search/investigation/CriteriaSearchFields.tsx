import { Controller, useFormContext } from 'react-hook-form';
import {
    InvestigationFilterEntry,
    caseStatusOptions,
    investigationStatusOptions,
    notificationStatusOptions,
    processingStatusOptions
} from './InvestigationFormTypes';
import { SingleSelect, MultiSelect } from 'design-system/select';
import { ConceptMultiSelect } from 'options/concepts/ConceptMultiSelect';
import { SearchCriteria } from 'apps/search/criteria';

const CriteriaSearchFields = () => {
    const form = useFormContext<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>();

    return (
        <SearchCriteria>
            <Controller
                control={form.control}
                name="investigationStatus"
                render={({ field: { onChange, name, value } }) => (
                    <SingleSelect
                        id={name}
                        name={name}
                        value={value}
                        onChange={onChange}
                        label="Investigation status"
                        sizing="compact"
                        options={investigationStatusOptions}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="outbreaks"
                render={({ field: { name, onChange, value } }) => (
                    <ConceptMultiSelect
                        id={name}
                        name={name}
                        value={value}
                        onChange={onChange}
                        valueSet="OUTBREAK_NM"
                        label="Outbreak name"
                        sizing="compact"
                    />
                )}
            />

            <Controller
                control={form.control}
                name="caseStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        id={name}
                        name={name}
                        label="Case status"
                        sizing="compact"
                        value={value}
                        onChange={onChange}
                        options={caseStatusOptions}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="processingStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        id={name}
                        name={name}
                        value={value}
                        label="Current processing status"
                        sizing="compact"
                        onChange={onChange}
                        options={processingStatusOptions}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="notificationStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        id={name}
                        name={name}
                        value={value}
                        label="Notification status"
                        sizing="compact"
                        onChange={onChange}
                        options={notificationStatusOptions}
                    />
                )}
            />
        </SearchCriteria>
    );
};

export default CriteriaSearchFields;
