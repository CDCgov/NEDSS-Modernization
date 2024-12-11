import React from 'react';
import { usePatientProfilePermissions } from '../permission';
import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule } from 'validation/entry';
import { GeneralInformationEntry } from './GeneralInformationEntry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { usePatientGeneralCodedValues } from './usePatientGeneralCodedValues';

const ENTRY_FIELD_PLACEHOLDER = '';

export const GeneralInformationFields = () => {
    const { control } = useFormContext<GeneralInformationEntry>();
    const { hivAccess } = usePatientProfilePermissions();
    const coded = usePatientGeneralCodedValues();

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
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="maritalStatus"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Marital status:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.maritalStatuses}
                    />
                )}
            />
            <Controller
                control={control}
                name="maternalMaidenName"
                rules={maxLengthRule(50)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Mother's maiden name:"
                        orientation="horizontal"
                        placeholder={ENTRY_FIELD_PLACEHOLDER}
                        onBlur={onBlur}
                        onChange={onChange}
                        type="text"
                        defaultValue={value}
                        id={name}
                        name={name}
                        htmlFor={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="adultsInHouse"
                rules={{ min: { value: 0, message: 'Must be greater than 0' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Number of adults in residence:"
                        orientation="horizontal"
                        placeholder={ENTRY_FIELD_PLACEHOLDER}
                        onBlur={onBlur}
                        onChange={onChange}
                        type="number"
                        defaultValue={value !== null ? value.toString() : ''}
                        id={name}
                        name={name}
                        htmlFor={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="childrenInHouse"
                rules={{ min: { value: 0, message: 'Must be greater than 0' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Number of children in residence:"
                        orientation="horizontal"
                        placeholder={ENTRY_FIELD_PLACEHOLDER}
                        onBlur={onBlur}
                        onChange={onChange}
                        type="number"
                        defaultValue={value !== null ? value.toString() : ''}
                        id={name}
                        name={name}
                        htmlFor={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="occupation"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Primary occupation:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        htmlFor={'occupation'}
                        options={coded.primaryOccupations}
                        id={name}
                        name={name}
                    />
                )}
            />
            <Controller
                control={control}
                name="educationLevel"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Highest level of education:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.educationLevels}
                    />
                )}
            />
            <Controller
                control={control}
                name="primaryLanguage"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Primary language:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.primaryLanguages}
                    />
                )}
            />
            <Controller
                control={control}
                name="speaksEnglish"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Speaks english:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.speaksEnglish}
                    />
                )}
            />
            {hivAccess && (
                <Controller
                    control={control}
                    name="stateHIVCase"
                    rules={maxLengthRule(20)}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <Input
                            label="State HIV case ID:"
                            orientation="horizontal"
                            placeholder={ENTRY_FIELD_PLACEHOLDER}
                            onBlur={onBlur}
                            onChange={onChange}
                            type="text"
                            defaultValue={value}
                            htmlFor={name}
                            id={name}
                            name={name}
                            error={error?.message}
                        />
                    )}
                />
            )}
        </section>
    );
};
