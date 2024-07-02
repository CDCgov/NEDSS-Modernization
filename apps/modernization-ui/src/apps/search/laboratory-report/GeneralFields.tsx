import { ErrorMessage } from '@trussworks/react-uswds';
import { UserAutocomplete } from 'options/autocompete/UserAutocomplete';
import { ProviderAutocomplete } from 'options/autocompete/ProviderAutocomplete';
import { FacilityAutocomplete } from 'options/autocompete/FacilityAutocomplete';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import {
    EntryMethod,
    EventStatus,
    LaboratoryEventIdType,
    LaboratoryReportEventDateType,
    LaboratoryReportStatus,
    PregnancyStatus,
    ProviderType,
    UserType
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { ChangeEvent } from 'react';
import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { formatInterfaceString } from 'utils/util';
import { LabReportFilterEntry } from './labReportFormTypes';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { Selectable } from 'options';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';

type LabReportGeneralFieldProps = {
    form: UseFormReturn<LabReportFilterEntry>;
};
export const GeneralFields = ({ form }: LabReportGeneralFieldProps) => {
    const watch = useWatch({ control: form.control });

    const handleEventDateTypeChange = (
        e: Selectable | undefined,
        onChange: (event: Selectable | undefined) => void
    ): void => {
        // Clear date fields if date type is deselected
        if (e?.value === '') {
            form.resetField('eventDate.from');
            form.resetField('eventDate.to');
        }
        onChange(e);
    };

    const handleEventIdTypeChange = (
        e: ChangeEvent<HTMLSelectElement>,
        onChange: (event: ChangeEvent<HTMLSelectElement>) => void
    ): void => {
        // Clear event id field on deselect
        if (e.target.value === '') {
            form.resetField('eventId.labEventId');
        }
        onChange(e);
    };

    const handleFacilityTypeChange = (
        e: ChangeEvent<HTMLSelectElement>,
        onChange: (event: ChangeEvent<HTMLSelectElement>) => void
    ): void => {
        // Clear event id field on deselect
        if (e.target.value === '') {
            form.resetField('providerSearch.providerId');
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
                            name="programAreas"
                            render={({ field: { onChange, name, value } }) => (
                                <MultiSelect
                                    label="Program area"
                                    onChange={onChange}
                                    value={value}
                                    name={name}
                                    options={searchCriteria.programAreas.map((p) => {
                                        return {
                                            name: p.id ?? '',
                                            value: p.id,
                                            label: p.id
                                        };
                                    })}
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
                                    onChange={onChange}
                                    value={value}
                                    name={name}
                                    options={searchCriteria.jurisdictions.map((p) => {
                                        return {
                                            name: p.codeDescTxt ?? '',
                                            value: p.id,
                                            label: p.codeDescTxt ?? ''
                                        };
                                    })}
                                    id={name}
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
                    <SingleSelect
                        name={name}
                        value={value}
                        onChange={onChange}
                        label="Pregnancy test"
                        options={[
                            { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes, label: PregnancyStatus.Yes },
                            { name: PregnancyStatus.No, value: PregnancyStatus.No, label: PregnancyStatus.No },
                            {
                                name: PregnancyStatus.Unknown,
                                value: PregnancyStatus.Unknown,
                                label: PregnancyStatus.Unknown
                            }
                        ]}
                        id={name}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="eventId.labEventType"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        name={name}
                        value={(value as string) ?? undefined}
                        onChange={(e) => handleEventIdTypeChange(e, onChange)}
                        label="Event id type"
                        htmlFor={name}
                        dataTestid={name}
                        options={Object.values(LaboratoryEventIdType).map((event) => {
                            return {
                                name: formatInterfaceString(event),
                                value: event
                            };
                        })}
                    />
                )}
            />

            {watch.eventId?.labEventType ? (
                <Controller
                    control={form.control}
                    name="eventId.labEventId"
                    rules={{
                        required: { value: true, message: 'Event Id is required' }
                    }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <>
                            <Input
                                onChange={onChange}
                                onBlur={onBlur}
                                label="Event id"
                                defaultValue={value}
                                type="text"
                                htmlFor={name}
                                id={name}
                                data-testid={name}
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
                    <SingleSelect
                        id={name}
                        name={name}
                        value={value}
                        onChange={(e) => handleEventDateTypeChange(e, onChange)}
                        label="Event date type"
                        options={Object.values(LaboratoryReportEventDateType).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type,
                                label: formatInterfaceString(type)
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
                                required
                                errorMessage={error?.message}
                            />
                        )}
                    />
                </>
            ) : null}

            <Controller
                control={form.control}
                name="entryMethods"
                render={({ field: { onChange, value, name } }) => (
                    <div className="grid-row">
                        <CheckboxGroup
                            name={name}
                            className="padding-0"
                            label="Entry method"
                            options={Object.values(EntryMethod).map((item) => {
                                return {
                                    name: item,
                                    label: item,
                                    value: item
                                };
                            })}
                            value={value}
                            onChange={onChange}
                        />
                    </div>
                )}
            />

            <Controller
                control={form.control}
                name="enteredBy"
                render={({ field: { onChange, value, name } }) => (
                    <div className="grid-row">
                        <CheckboxGroup
                            name={name}
                            className="padding-0"
                            label="Entered by"
                            options={Object.values(UserType).map((item) => {
                                return {
                                    name: item,
                                    label: item,
                                    value: item
                                };
                            })}
                            value={value}
                            onChange={onChange}
                        />
                    </div>
                )}
            />

            <Controller
                control={form.control}
                name="eventStatus"
                render={({ field: { onChange, value, name } }) => (
                    <div className="grid-row">
                        <CheckboxGroup
                            name={name}
                            className="padding-0"
                            label="Event status"
                            options={Object.values(EventStatus).map((item) => {
                                return {
                                    name: item,
                                    label: item,
                                    value: item
                                };
                            })}
                            value={value}
                            onChange={onChange}
                        />
                    </div>
                )}
            />

            <Controller
                control={form.control}
                name="processingStatus"
                render={({ field: { onChange, value, name } }) => (
                    <div className="grid-row">
                        <CheckboxGroup
                            name={name}
                            className="padding-0"
                            label="Processing status"
                            options={Object.values(LaboratoryReportStatus).map((item) => {
                                return {
                                    name: item,
                                    label: item,
                                    value: item
                                };
                            })}
                            value={value}
                            onChange={onChange}
                        />
                    </div>
                )}
            />

            <Controller
                control={form.control}
                name="createdBy"
                render={({ field: { onChange, name } }) => (
                    <UserAutocomplete id={name} label="Event created by user" onChange={onChange} />
                )}
            />
            <Controller
                control={form.control}
                name="lastUpdatedBy"
                render={({ field: { onChange, name } }) => (
                    <UserAutocomplete id={name} onChange={onChange} label="Event updated by user" />
                )}
            />
            <Controller
                control={form.control}
                name="providerSearch.providerType"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        name={name}
                        value={value as string | undefined}
                        onChange={(e) => handleFacilityTypeChange(e, onChange)}
                        label="Event provider/facility type"
                        htmlFor={name}
                        dataTestid={name}
                        options={Object.values(ProviderType).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type
                            };
                        })}
                    />
                )}
            />

            {watch.providerSearch?.providerType == 'ORDERING_FACILITY' && (
                <Controller
                    control={form.control}
                    name="providerSearch.providerId"
                    rules={{
                        required: { value: true, message: `Ordering facility is required` }
                    }}
                    render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                        <>
                            <FacilityAutocomplete
                                id={name}
                                label="Event ordering facility"
                                required={true}
                                onChange={onChange}
                                onBlur={onBlur}
                            />
                            {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                        </>
                    )}
                />
            )}

            {watch.providerSearch?.providerType == 'ORDERING_PROVIDER' && (
                <Controller
                    control={form.control}
                    name="providerSearch.providerId"
                    rules={{
                        required: { value: true, message: `Ordering provider is required` }
                    }}
                    render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                        <>
                            <ProviderAutocomplete
                                id={name}
                                label="Event ordering provider"
                                required={true}
                                placeholder=""
                                onChange={onChange}
                                onBlur={onBlur}
                            />
                            {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                        </>
                    )}
                />
            )}

            {watch.providerSearch?.providerType == 'REPORTING_FACILITY' && (
                <Controller
                    control={form.control}
                    name="providerSearch.providerId"
                    rules={{
                        required: { value: true, message: `Facility is required` }
                    }}
                    render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                        <>
                            <FacilityAutocomplete
                                id={name}
                                label="Event reporting facility"
                                required={true}
                                placeholder=""
                                onChange={onChange}
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
