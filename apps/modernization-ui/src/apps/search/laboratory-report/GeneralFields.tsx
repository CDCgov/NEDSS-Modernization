import { Checkbox, ErrorMessage, Label } from '@trussworks/react-uswds';
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
import { MultiSelect, SinlgeSelect } from 'design-system/select';
import { Selectable } from 'options';

type LabReportGeneralFieldProps = {
    form: UseFormReturn<LabReportFilterEntry>;
};
export const GeneralFields = ({ form }: LabReportGeneralFieldProps) => {
    const watch = useWatch({ control: form.control });
    let entryMethodArr: Selectable[] = [];
    let enteredByArr: Selectable[] = [];
    let eventStatusArr: Selectable[] = [];
    let processingStatusArr: Selectable[] = [];

    const handleEventDateTypeChange = (
        e: ChangeEvent<HTMLSelectElement>,
        onChange: (event: ChangeEvent<HTMLSelectElement>) => void
    ): void => {
        // Clear date fields if date type is deselected
        if (e.target.value === '') {
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

    const updateCheckboxes = (arr: Selectable[], value: string) => {
        const updatedArr = entryMethodArr.filter((item) => item.value !== value);
        return updatedArr;
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
                    <SinlgeSelect
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
                    <SelectInput
                        name={name}
                        value={value as string | undefined}
                        onChange={(e) => handleEventDateTypeChange(e, onChange)}
                        label="Event date type"
                        htmlFor={name}
                        dataTestid={name}
                        options={Object.values(LaboratoryReportEventDateType).map((type) => {
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

            <Label htmlFor={'entryMethod'}>
                Entry method
                <Controller
                    control={form.control}
                    name="entryMethods"
                    render={({ field: { onChange, value } }) => {
                        entryMethodArr = value || [];
                        return (
                            <div className="grid-row">
                                {Object.values(EntryMethod).map((status, index) => (
                                    <Checkbox
                                        checked={value?.some((item) => item.name === status)}
                                        key={index}
                                        id={status}
                                        name={status}
                                        label={formatInterfaceString(status.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                entryMethodArr.push({ name: status, value: status, label: status });
                                                onChange(entryMethodArr);
                                            } else {
                                                onChange(updateCheckboxes(entryMethodArr, status));
                                            }
                                        }}
                                        className="checkbox-input"
                                    />
                                ))}
                            </div>
                        );
                    }}
                />
            </Label>

            <Label htmlFor={'enteredBy'}>
                Entered by
                <Controller
                    control={form.control}
                    name="enteredBy"
                    render={({ field: { onChange, value } }) => {
                        enteredByArr = value || [];
                        return (
                            <div className="grid-row">
                                {Object.values(UserType).map((item, index) => (
                                    <Checkbox
                                        checked={value?.some((enteredBy) => enteredBy.name === item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                enteredByArr.push({ name: item, value: item, label: item });
                                                onChange(enteredByArr);
                                            } else {
                                                onChange(updateCheckboxes(enteredByArr, item));
                                            }
                                        }}
                                        className="checkbox-input"
                                    />
                                ))}
                            </div>
                        );
                    }}
                />
            </Label>

            <Label htmlFor={'eventStatus'}>
                Event status
                <Controller
                    control={form.control}
                    name="eventStatus"
                    render={({ field: { onChange, value } }) => (
                        <div className="grid-row">
                            {Object.values(EventStatus).map((item, index) => {
                                eventStatusArr = value || [];
                                return (
                                    <Checkbox
                                        checked={value?.some((eventStatus) => eventStatus.name === item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                eventStatusArr.push({ name: item, value: item, label: item });
                                                onChange(eventStatusArr);
                                            } else {
                                                onChange(updateCheckboxes(eventStatusArr, item));
                                            }
                                        }}
                                        className="checkbox-input"
                                    />
                                );
                            })}
                        </div>
                    )}
                />
            </Label>

            <Label htmlFor={'processingStatus'}>
                Processing status
                <Controller
                    control={form.control}
                    name="processingStatus"
                    render={({ field: { onChange, value } }) => {
                        processingStatusArr = value || [];
                        return (
                            <div className="grid-row">
                                {Object.values(LaboratoryReportStatus).map((item, index) => (
                                    <Checkbox
                                        checked={value?.some((processingStatus) => processingStatus.name === item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                processingStatusArr.push({ name: item, value: item, label: item });
                                                onChange(processingStatusArr);
                                            } else {
                                                onChange(updateCheckboxes(processingStatusArr, item));
                                            }
                                        }}
                                        className="checkbox-input"
                                    />
                                ))}
                            </div>
                        );
                    }}
                />
            </Label>

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
