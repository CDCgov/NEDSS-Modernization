import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { EntryFieldsProps } from 'design-system/entry';
import { UserAutocomplete } from 'options/autocompete/UserAutocomplete';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { ProviderAutocomplete } from 'options/autocompete/ProviderAutocomplete';
import { FacilityAutocomplete } from 'options/autocompete/FacilityAutocomplete';
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

export const GeneralFields = ({ sizing = 'medium' }: EntryFieldsProps) => {
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                            sizing={sizing}
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
                        sizing={sizing}
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
                                sizing={sizing}
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
                                sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
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
                        sizing={sizing}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                    />
                )}
            />
        </SearchCriteria>
    );
};
