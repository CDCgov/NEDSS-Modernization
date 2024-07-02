import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { InvestigationEventIdType, PregnancyStatus, ReportingEntityType } from 'generated/graphql/schema';
import { formatInterfaceString } from 'utils/util';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { AutocompleteMulti, Autocomplete } from '../../../design-system/autocomplete';
import { ConditionOptionsService, UserOptionsService } from 'generated';
import { MultiSelect } from 'design-system/select';
import { fetchProgramAreaOptions } from 'apps/page-builder/services/programAreaAPI';
import { ProgramArea } from 'apps/page-builder/generated';
import { useEffect, useState } from 'react';
import { Selectable, asValue } from 'options';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';

type Props = {
    form: UseFormReturn<InvestigationFilterEntry>;
};

const GeneralSearchFields = ({ form }: Props) => {
    const watch = useWatch({ control: form.control });
    const [programAreaOptions, setProgramAreaOptions] = useState<ProgramArea[] | void>();

    const handleChangeConditions = (e: Selectable[]) => {
        form.setValue('conditions', e);
    };

    const condtionsResolver = (criteria: string, limit?: number) =>
        ConditionOptionsService.complete({
            criteria: criteria,
            limit: limit
        }).then((response) => response);

    const usersResolver = (criteria: string, limit?: number) => {
        return UserOptionsService.userAutocomplete({ criteria, limit }).then((response) => response);
    };

    useEffect(() => {
        fetchProgramAreaOptions().then((response) => setProgramAreaOptions(response));
    }, []);

    return (
        <>
            <AutocompleteMulti
                id="conditions-autocomplete"
                name="conditions"
                label="Conditions"
                resolver={condtionsResolver}
                onChange={handleChangeConditions}
            />
            {programAreaOptions?.length ? (
                <Controller
                    control={form.control}
                    name="programAreas"
                    render={({ field: { name } }) => (
                        <MultiSelect
                            data-testid="programArea"
                            id="programArea"
                            label="Program area"
                            name={name}
                            options={programAreaOptions?.map((p) => {
                                return {
                                    label: p.display ?? '',
                                    name: p.display ?? '',
                                    value: p.value ?? ''
                                };
                            })}
                        />
                    )}
                />
            ) : null}
            <MultiSelect
                data-testid="jurisdictions"
                id="jurisdiction"
                label="Jurisdiction"
                name="jurisdiction"
                onChange={() => 'void'}
                options={[
                    {
                        label: 'Jurisdiction',
                        name: 'coming soon',
                        value: 'comingsoon'
                    }
                ]}
            />
            <Controller
                control={form.control}
                name="pregnancyStatus"
                render={({ field: { name, value } }) => (
                    <SelectInput
                        data-testid="pregnancyStatus"
                        name={name}
                        id="pregnancyStatus"
                        label="Pregnancy test"
                        onChange={() => form.setValue('pregnancyStatus', value)}
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
                name="identification.type"
                render={({ field: { value, name } }) => (
                    <SelectInput
                        name={name}
                        defaultValue={asValue(value)}
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
            <Controller
                control={form.control}
                name={'eventDate.type'}
                render={({ field: { value, name } }) => (
                    <SelectInput
                        data-testid={name}
                        name={name}
                        value={asValue(value)}
                        id="eventDateType"
                        label="Event date type"
                        options={Object.values(InvestigationEventIdType).map((event) => {
                            return {
                                label: formatInterfaceString(event),
                                name: formatInterfaceString(event),
                                value: event
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
                                label="From"
                                required
                                name={name}
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
                name="createdBy"
                render={({ field: { name, onBlur } }) => (
                    <Autocomplete
                        data-testid="createdBy"
                        name={name}
                        label="Event creeated by"
                        id={'createdBy'}
                        onBlur={onBlur}
                        resolver={usersResolver}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="updatedBy"
                render={({ field: { name, onBlur } }) => (
                    <Autocomplete
                        name={name}
                        label="Event updated by"
                        id={'updatedBy'}
                        resolver={usersResolver}
                        onBlur={onBlur}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="reportingFacility"
                render={({ field: { name } }) => (
                    <SelectInput
                        id={name}
                        data-testid={name}
                        name="reportingFacility"
                        label="Event provider/facility type"
                        options={Object.values(ReportingEntityType).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type
                            };
                        })}
                    />
                )}
            />
        </>
    );
};

export default GeneralSearchFields;
