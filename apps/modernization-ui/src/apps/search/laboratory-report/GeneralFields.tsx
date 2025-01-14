import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { UserAutocomplete } from 'options/autocompete/UserAutocomplete';
import { ProviderAutocomplete } from 'options/autocompete/ProviderAutocomplete';
import { FacilityAutocomplete } from 'options/autocompete/FacilityAutocomplete';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { useJurisdictionOptions } from 'options/jurisdictions';
import { useProgramAreaOptions } from 'options/program-areas';
import { SearchCriteria } from 'apps/search/criteria';
import {
    dateTypes,
    enteredByTypes,
    entryMethodTypes,
    eventStatusTypes,
    identificationTypes,
    LabReportFilterEntry,
    pregnancyStatus,
    processingStatusTypes
} from './labReportFormTypes';

export const GeneralFields = () => {
    const form = useFormContext<LabReportFilterEntry, Partial<LabReportFilterEntry>>();

    const { all: jurisdictions } = useJurisdictionOptions();
    const { all: programAreas } = useProgramAreaOptions();

    const selectedIdentificationType = useWatch({ control: form.control, name: 'identification.type' });
    const selectedDateType = useWatch({ control: form.control, name: 'eventDate.type' });

    return (
        <SearchCriteria>
            <Controller
                control={form.control}
                name="programAreas"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        label="Program area"
                        sizing="compact"
                        onChange={onChange}
                        value={value}
                        name={name}
                        options={programAreas}
                        id={name}
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
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        name={name}
                        value={value}
                        onChange={onChange}
                        label="Pregnancy status"
                        options={pregnancyStatus}
                        id={name}
                        sizing="compact"
                    />
                )}
            />

            <Controller
                control={form.control}
                name="identification.type"
                shouldUnregister
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        name={name}
                        value={value}
                        onChange={onChange}
                        label="Event ID type"
                        id={name}
                        sizing="compact"
                        options={identificationTypes}
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
                            onChange={onChange}
                            onBlur={onBlur}
                            label="Event ID"
                            defaultValue={value}
                            type="text"
                            htmlFor={name}
                            id={name}
                            sizing="compact"
                            required
                            error={error?.message}
                        />
                    )}
                />
            )}

            <Controller
                control={form.control}
                name="eventDate.type"
                shouldUnregister
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        id={name}
                        sizing="compact"
                        name={name}
                        value={value}
                        onChange={onChange}
                        label="Event date type"
                        options={dateTypes}
                    />
                )}
            />

            {selectedDateType && (
                <>
                    <Controller
                        control={form.control}
                        name="eventDate.from"
                        shouldUnregister
                        rules={{
                            required: { value: true, message: 'From date is required' }
                        }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <DatePickerInput
                                sizing="compact"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                name={name}
                                label="From"
                                required
                                errorMessage={error?.message}
                            />
                        )}
                    />

                    <Controller
                        control={form.control}
                        name="eventDate.to"
                        shouldUnregister
                        rules={{
                            required: { value: true, message: 'To date is required' }
                        }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <DatePickerInput
                                sizing="compact"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                name={name}
                                label="To"
                                required
                                errorMessage={error?.message}
                            />
                        )}
                    />
                </>
            )}

            <Controller
                control={form.control}
                name="entryMethods"
                render={({ field: { onChange, value, name } }) => (
                    <CheckboxGroup
                        name={name}
                        label="Entry method"
                        sizing="compact"
                        options={entryMethodTypes}
                        value={value}
                        onChange={onChange}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="enteredBy"
                render={({ field: { onChange, value, name } }) => (
                    <CheckboxGroup
                        name={name}
                        label="Entered by"
                        sizing="compact"
                        options={enteredByTypes}
                        value={value}
                        onChange={onChange}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="eventStatus"
                render={({ field: { onChange, value, name } }) => (
                    <CheckboxGroup
                        name={name}
                        label="Event status"
                        sizing="compact"
                        options={eventStatusTypes}
                        value={value}
                        onChange={onChange}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="processingStatus"
                render={({ field: { onChange, value, name } }) => (
                    <CheckboxGroup
                        name={name}
                        label="Processing status"
                        sizing="compact"
                        options={processingStatusTypes}
                        value={value}
                        onChange={onChange}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="createdBy"
                render={({ field: { onChange, name, value } }) => (
                    <UserAutocomplete
                        id={name}
                        value={value}
                        label="Event created by user"
                        sizing="compact"
                        onChange={onChange}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="updatedBy"
                render={({ field: { onChange, name, value } }) => (
                    <UserAutocomplete
                        id={name}
                        value={value}
                        sizing="compact"
                        onChange={onChange}
                        label="Event updated by user"
                    />
                )}
            />
            <Controller
                control={form.control}
                name="orderingFacility"
                shouldUnregister
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <FacilityAutocomplete
                        value={value}
                        id={name}
                        label="Event ordering facility"
                        sizing="compact"
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="orderingProvider"
                shouldUnregister
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <ProviderAutocomplete
                        id={name}
                        label="Event ordering provider"
                        sizing="compact"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="reportingFacility"
                shouldUnregister
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <FacilityAutocomplete
                        id={name}
                        value={value}
                        label="Event reporting facility"
                        sizing="compact"
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                    />
                )}
            />
        </SearchCriteria>
    );
};
