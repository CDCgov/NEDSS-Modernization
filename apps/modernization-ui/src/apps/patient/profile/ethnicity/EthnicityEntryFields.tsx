import React from 'react';
import { usePatientEthnicityCodedValues } from './usePatientEthnicityCodedValues';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { EthnicityEntry } from './EthnicityEntry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';

const UNKNOWN = 'UNK';
const HISPANIC = '2135-2';

export const EthnicityEntryFields = () => {
    const { control } = useFormContext<EthnicityEntry>();
    const coded = usePatientEthnicityCodedValues();

    const selectedEthinicity = useWatch({ control, name: 'ethnicGroup' });
    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="As of:"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="ethnicGroup"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Ethnicity:"
                        orientation="horizontal"
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        defaultValue={value}
                        options={coded.ethnicGroups}
                    />
                )}
            />

            {selectedEthinicity === HISPANIC && (
                <Controller
                    control={control}
                    name="detailed"
                    shouldUnregister
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <MultiSelectInput
                            label="Spanish origin:"
                            orientation="horizontal"
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            value={value}
                            options={coded.detailedEthnicities}
                        />
                    )}
                />
            )}
            {selectedEthinicity === UNKNOWN && (
                <Controller
                    control={control}
                    name="unknownReason"
                    shouldUnregister
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <SelectInput
                            label="Reason unknown:"
                            orientation="horizontal"
                            defaultValue={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            htmlFor={'unknownReason'}
                            options={coded.ethnicityUnknownReasons}
                        />
                    )}
                />
            )}
        </section>
    );
};
