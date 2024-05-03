import { ErrorMessage } from '@trussworks/react-uswds';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import {
    InvestigationEventDateType,
    InvestigationEventIdType,
    InvestigationFilter,
    PregnancyStatus,
    ReportingEntityType
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { ChangeEvent, ReactElement } from 'react';
import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { formatInterfaceString } from 'utils/util';
import { UserAutocomplete } from 'options/autocompete/UserAutocomplete';
import { ProviderAutocomplete } from 'options/autocompete/ProviderAutocomplete';
import { FacilityAutocomplete } from 'options/autocompete/FacilityAutocomplete';

type InvestigationGeneralAccordionProps = {
    form: UseFormReturn<InvestigationFilter>;
};
export const InvestigationGeneralFields = ({ form }: InvestigationGeneralAccordionProps): ReactElement => {
    const watch = useWatch({ control: form.control });

    const handleEventDateTypeChange = (
        e: ChangeEvent<HTMLSelectElement>,
        onChange: (event: ChangeEvent<HTMLSelectElement>) => void
    ): void => {
        // Clear date fields if date type is deselected
        if (!e.target.value) {
            form.setValue('eventDate', { type: undefined } as any, { shouldDirty: true, shouldValidate: true });
            return;
        }
        onChange(e);
    };

    const handleEventIdTypeChange = (
        e: ChangeEvent<HTMLSelectElement>,
        onChange: (event: ChangeEvent<HTMLSelectElement>) => void
    ): void => {
        // Clear event id field on deselect
        if (!e.target.value) {
            form.setValue('eventId', { investigationEventType: undefined } as any, {
                shouldDirty: true,
                shouldValidate: true
            });
            return;
        }
        onChange(e);
    };

    const handleChangeWithUndefinedDefaultValue = (
        name: String,
        e: ChangeEvent<HTMLSelectElement>,
        onChange: (event: ChangeEvent<HTMLSelectElement>) => void
    ): void => {
        // Clear event id field on deselect
        if (!e.target.value) {
            form.setValue(name as any, undefined as any, {
                shouldDirty: true,
                shouldValidate: true
            });
            return;
        }
        onChange(e);
    };

    const handleFacilityTypeChange = (
        e: ChangeEvent<HTMLSelectElement>,
        onChange: (event: ChangeEvent<HTMLSelectElement>) => void
    ): void => {
        // Clear event id field on deselect
        if (!e.target.value) {
            form.setValue('providerFacilitySearch', { entityType: undefined } as any, {
                shouldDirty: true,
                shouldValidate: true
            });
            return;
        }
        onChange(e);
    };

    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <Controller
                            control={form.control}
                            name="conditions"
                            render={({ field: { onChange, name, value } }) => (
                                <MultiSelectInput
                                    id="conditionInput"
                                    label="Condition"
                                    onChange={onChange}
                                    value={value as string[] | undefined}
                                    name={name}
                                    options={searchCriteria.conditions.map((c) => {
                                        return {
                                            name: c.conditionDescTxt ?? '',
                                            value: c.id
                                        };
                                    })}
                                />
                            )}
                        />
                        <Controller
                            control={form.control}
                            name="programAreas"
                            render={({ field: { onChange, name, value } }) => (
                                <MultiSelectInput
                                    label="Program area"
                                    onChange={onChange}
                                    value={value as string[] | undefined}
                                    name={name}
                                    options={searchCriteria.programAreas.map((p) => {
                                        return {
                                            name: p.id,
                                            value: p.id
                                        };
                                    })}
                                />
                            )}
                        />
                        <Controller
                            control={form.control}
                            name="jurisdictions"
                            render={({ field: { onChange, name, value } }) => (
                                <MultiSelectInput
                                    label="Jurisdiction"
                                    onChange={onChange}
                                    value={value as string[] | undefined}
                                    name={name}
                                    options={searchCriteria.jurisdictions.map((j) => {
                                        return {
                                            name: j.codeDescTxt ?? '',
                                            value: j.id
                                        };
                                    })}
                                />
                            )}
                        />
                    </>
                )}
            </SearchCriteriaContext.Consumer>
            <Controller
                control={form.control}
                name="pregnancyStatus"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        name={name}
                        value={value as string | undefined}
                        onChange={(e) => handleChangeWithUndefinedDefaultValue(name, e, onChange)}
                        label="Pregnancy test"
                        htmlFor={name}
                        dataTestid={name}
                        options={[
                            { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes },
                            { name: PregnancyStatus.No, value: PregnancyStatus.No },
                            { name: PregnancyStatus.Unknown, value: PregnancyStatus.Unknown }
                        ]}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="eventId.investigationEventType"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        name={name}
                        value={value as string | undefined}
                        onChange={(e) => handleEventIdTypeChange(e, onChange)}
                        label="Event id type"
                        dataTestid={name}
                        htmlFor={name}
                        options={Object.values(InvestigationEventIdType).map((event) => {
                            return {
                                name: formatInterfaceString(event),
                                value: event
                            };
                        })}
                    />
                )}
            />
            {watch.eventId?.investigationEventType ? (
                <Controller
                    control={form.control}
                    name="eventId.id"
                    rules={{
                        required: { value: true, message: 'Event Id is required' }
                    }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <>
                            <Input
                                onChange={onChange}
                                onBlur={onBlur}
                                data-testid={name}
                                label="Event id"
                                defaultValue={value}
                                type="text"
                                htmlFor={name}
                                id={name}
                                required
                            />
                            {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                        </>
                    )}
                />
            ) : null}

            <Controller
                control={form.control}
                name="eventDate.type"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        name={name}
                        value={value as string | undefined}
                        onChange={(e) => handleEventDateTypeChange(e, onChange)}
                        label="Event date type"
                        htmlFor={name}
                        dataTestid={name}
                        options={Object.values(InvestigationEventDateType).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type
                            };
                        })}
                    />
                )}
            />

            {watch.eventDate?.type ? (
                <>
                    <Controller
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
                                htmlFor={'from'}
                                label="From"
                                required
                                id={name}
                                errorMessage={error?.message}
                            />
                        )}
                    />

                    <Controller
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
                                htmlFor={'to'}
                                id={name}
                                label="To"
                                required
                                errorMessage={error?.message}
                            />
                        )}
                    />
                </>
            ) : null}

            <>
                <Controller
                    control={form.control}
                    name="createdBy"
                    render={({ field: { onChange } }) => (
                        <UserAutocomplete id="createdBy" label="Event created by user" onChange={onChange} />
                    )}
                />

                <Controller
                    control={form.control}
                    name="lastUpdatedBy"
                    render={({ field: { onChange } }) => (
                        <UserAutocomplete id="lastUpdatedBy" onChange={onChange} label="Event updated by user" />
                    )}
                />
            </>

            <Controller
                control={form.control}
                name="providerFacilitySearch.entityType"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        name={name}
                        value={value as string | undefined}
                        onChange={(e) => handleFacilityTypeChange(e, onChange)}
                        label="Event provider/facility type"
                        htmlFor={name}
                        dataTestid={name}
                        options={Object.values(ReportingEntityType).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type
                            };
                        })}
                    />
                )}
            />

            {watch.providerFacilitySearch?.entityType == 'PROVIDER' && (
                <Controller
                    control={form.control}
                    name="providerFacilitySearch.id"
                    rules={{
                        required: { value: true, message: `Provider is required` }
                    }}
                    render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                        <>
                            <ProviderAutocomplete
                                id={name}
                                label="Event provider type"
                                placeholder=""
                                onChange={onChange}
                                required={true}
                                onBlur={onBlur}
                            />
                            {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                        </>
                    )}
                />
            )}

            {watch.providerFacilitySearch?.entityType == 'FACILITY' && (
                <Controller
                    control={form.control}
                    name="providerFacilitySearch.id"
                    rules={{
                        required: { value: true, message: `Facility is required` }
                    }}
                    render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                        <>
                            <FacilityAutocomplete
                                id={name}
                                label="Event facility type"
                                placeholder=""
                                onChange={onChange}
                                required={true}
                                onBlur={onBlur}
                            />
                            {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                        </>
                    )}
                />
            )}
        </>
    );
};
