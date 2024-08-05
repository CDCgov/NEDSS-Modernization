import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { FacilityAutocomplete } from 'options/autocompete/FacilityAutocomplete';
import { ProviderAutocomplete } from 'options/autocompete/ProviderAutocomplete';
import { UserAutocomplete } from 'options/autocompete/UserAutocomplete';
import { useConditionOptions } from 'options/condition';
import { useProgramAreaOptions } from 'options/program-areas';
import { useJurisdictionOptions } from 'options/jurisdictions';
import { SearchCriteria } from 'apps/search/criteria';

import {
    InvestigationFilterEntry,
    dateTypeOptions,
    investigationEventTypeOptions,
    pregnancyStatusOptions
} from './InvestigationFormTypes';

const GeneralSearchFields = () => {
    const { all: jurisdictions } = useJurisdictionOptions();
    const { all: programAreas } = useProgramAreaOptions();
    const { options: conditions } = useConditionOptions();

    const form = useFormContext<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>();
    const watch = useWatch({ control: form.control });

    const selectedIdentificationType = useWatch({ control: form.control, name: 'identification.type' });
    const selectedEventType = useWatch({ control: form.control, name: 'eventDate.type' });

    return (
        <SearchCriteria>
            <Controller
                name="conditions"
                control={form.control}
                render={({ field: { name, onChange, value } }) => (
                    <MultiSelect
                        id={name}
                        label="Conditions"
                        sizing="compact"
                        value={value}
                        onChange={onChange}
                        name={name}
                        options={conditions}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="programAreas"
                render={({ field: { name, onChange, value } }) => (
                    <MultiSelect
                        id={name}
                        label="Program area"
                        sizing="compact"
                        onChange={onChange}
                        value={value}
                        name={name}
                        options={programAreas}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="jurisdictions"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        label="Jurisdiction"
                        sizing="compact"
                        onChange={onChange}
                        value={value}
                        name={name}
                        options={jurisdictions}
                        id={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="pregnancyStatus"
                render={({ field: { name, onChange, value } }) => (
                    <SingleSelect
                        name={name}
                        value={value}
                        id="pregnancyStatus"
                        label="Pregnancy status"
                        sizing="compact"
                        onChange={onChange}
                        options={pregnancyStatusOptions}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="identification.type"
                render={({ field: { name, value, onChange } }) => (
                    <SingleSelect
                        label="Event ID type"
                        id={name}
                        sizing="compact"
                        value={value}
                        onChange={onChange}
                        options={investigationEventTypeOptions}
                    />
                )}
            />

            {selectedIdentificationType && (
                <Controller
                    control={form.control}
                    name="identification.value"
                    rules={{
                        required: { value: true, message: 'Event Id is required' }
                    }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            onBlur={onBlur}
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="Event ID"
                            name={name}
                            htmlFor={name}
                            id={name}
                            sizing="compact"
                            error={error?.message}
                            required
                        />
                    )}
                />
            )}

            <Controller
                control={form.control}
                name={'eventDate.type'}
                render={({ field: { name, value, onChange } }) => (
                    <SingleSelect
                        name={name}
                        value={value}
                        onChange={onChange}
                        id={name}
                        label="Event date type"
                        sizing="compact"
                        options={dateTypeOptions}
                    />
                )}
            />

            {selectedEventType && (
                <>
                    <Controller
                        shouldUnregister
                        control={form.control}
                        name="eventDate.from"
                        rules={{
                            required: { value: true, message: 'From date is required' }
                        }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <DatePickerInput
                                disabled={!watch.eventDate?.type}
                                defaultValue={value}
                                onBlur={onBlur}
                                onChange={onChange}
                                label="From"
                                sizing="compact"
                                required
                                name={name}
                                errorMessage={error?.message}
                            />
                        )}
                    />

                    <Controller
                        shouldUnregister
                        control={form.control}
                        name="eventDate.to"
                        rules={{
                            required: { value: true, message: 'To date is required' }
                        }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <DatePickerInput
                                disabled={!watch.eventDate?.type}
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                name={name}
                                label="To"
                                sizing="compact"
                                required
                                errorMessage={error?.message}
                            />
                        )}
                    />
                </>
            )}

            <Controller
                control={form.control}
                name="createdBy"
                render={({ field: { name, value, onChange } }) => (
                    <UserAutocomplete
                        id={name}
                        label="Event created by"
                        sizing="compact"
                        onChange={onChange}
                        value={value}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="updatedBy"
                render={({ field: { name, value, onChange } }) => (
                    <UserAutocomplete
                        id={name}
                        label="Event updated by"
                        sizing="compact"
                        onChange={onChange}
                        value={value}
                    />
                )}
            />

            <Controller
                shouldUnregister
                control={form.control}
                name="reportingProviderId"
                render={({ field: { onBlur, onChange, name, value } }) => (
                    <>
                        <ProviderAutocomplete
                            id={name}
                            label="Reporting provider"
                            sizing="compact"
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                        />
                    </>
                )}
            />

            <Controller
                shouldUnregister
                control={form.control}
                name="reportingFacilityId"
                render={({ field: { onBlur, onChange, name, value } }) => (
                    <>
                        <FacilityAutocomplete
                            id={name}
                            label="Reporting Facility"
                            sizing="compact"
                            value={value}
                            onChange={(e) => onChange(e)}
                            onBlur={onBlur}
                        />
                    </>
                )}
            />
        </SearchCriteria>
    );
};

export default GeneralSearchFields;
