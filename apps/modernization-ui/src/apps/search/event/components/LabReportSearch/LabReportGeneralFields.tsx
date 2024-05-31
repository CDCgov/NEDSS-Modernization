import { Checkbox, ErrorMessage, Label } from '@trussworks/react-uswds';
import { UserAutocomplete } from 'options/autocompete/UserAutocomplete';
import { ProviderAutocomplete } from 'options/autocompete/ProviderAutocomplete';
import { FacilityAutocomplete } from 'options/autocompete/FacilityAutocomplete';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import {
    EntryMethod,
    EventStatus,
    LabReportFilter,
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

type LabReportGeneralFieldProps = {
    form: UseFormReturn<LabReportFilter>;
};
export const LabReportGeneralFields = ({ form }: LabReportGeneralFieldProps) => {
    const watch = useWatch({ control: form.control });
    let entryMethodArr: string[] = [];
    let enteredByArr: string[] = [];
    let eventStatusArr: string[] = [];
    let processingStatusArr: string[] = [];

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
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
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
                                            name: p.id ?? '',
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
                        onChange={onChange}
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
                        entryMethodArr = (value as string[] | undefined) || [];
                        return (
                            <div className="grid-row">
                                {Object.values(EntryMethod).map((status, index) => (
                                    <Checkbox
                                        checked={value?.includes(status)}
                                        key={index}
                                        id={status}
                                        name={status}
                                        label={formatInterfaceString(status.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                entryMethodArr.push(status);
                                                onChange(entryMethodArr);
                                            } else {
                                                const index = entryMethodArr.indexOf(status);
                                                if (index > -1) {
                                                    entryMethodArr.splice(index, 1);
                                                }
                                                onChange(entryMethodArr);
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
                        enteredByArr = (value as string[] | undefined) || [];
                        return (
                            <div className="grid-row">
                                {Object.values(UserType).map((item, index) => (
                                    <Checkbox
                                        checked={value?.includes(item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                enteredByArr.push(item);
                                                onChange(enteredByArr);
                                            } else {
                                                const index = enteredByArr.indexOf(item);
                                                if (index > -1) {
                                                    enteredByArr.splice(index, 1);
                                                }
                                                onChange(enteredByArr);
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
                                eventStatusArr = (value as string[] | undefined) || [];
                                return (
                                    <Checkbox
                                        checked={value?.includes(item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                eventStatusArr.push(item);
                                                onChange(eventStatusArr);
                                            } else {
                                                const index = eventStatusArr.indexOf(item);
                                                if (index > -1) {
                                                    eventStatusArr.splice(index, 1);
                                                }
                                                onChange(eventStatusArr);
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
                        processingStatusArr = (value as string[] | undefined) || [];
                        return (
                            <div className="grid-row">
                                {Object.values(LaboratoryReportStatus).map((item, index) => (
                                    <Checkbox
                                        checked={value?.includes(item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                processingStatusArr.push(item);
                                                onChange(processingStatusArr);
                                            } else {
                                                const index = processingStatusArr.indexOf(item);
                                                if (index > -1) {
                                                    processingStatusArr.splice(index, 1);
                                                }
                                                onChange(processingStatusArr);
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
                                placeholder=""
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
