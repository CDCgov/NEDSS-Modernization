import { useEffect } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import {
    validZipCodeRule,
    ZipCodeInputField,
    CensusTractInputField,
    validCensusTractRule
} from 'libs/demographics/location';
import { EntryFieldsProps } from 'design-system/entry';
import { TextInputField } from 'design-system/input';
import { TextAreaField } from 'design-system/input/text/TextAreaField';
import { SingleSelect } from 'design-system/select';
import { AddressOptions } from './useAddressOptions';
import { AddressDemographic, labels } from '../address';
import { Selectable } from 'options';

type AddressDemographicFieldsProps = { options: AddressOptions; entry?: AddressDemographic } & EntryFieldsProps;

const AddressDemographicFields = ({
    orientation = 'horizontal',
    sizing = 'medium',
    options,
    entry
}: AddressDemographicFieldsProps) => {
    const { control, setValue } = useFormContext<AddressDemographic>();

    useEffect(() => {
        // on form initialization, load counties for selected state
        options.location.state(entry?.state);
    }, [entry?.state]);

    const handleStateChange = (state: Selectable | null) => {
        // when user selects a different state, clear selected county and load new county list
        setValue('county', null);
        options.location.state(state);
    };

    return (
        <>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validDateRule(labels.asOf), ...validateRequiredRule(labels.asOf) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={labels.asOf}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={name}
                        orientation={orientation}
                        error={error?.message}
                        required
                        sizing={sizing}
                        aria-description="This date defaults to today and can be changed if needed"
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ ...validateRequiredRule(labels.type) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={labels.type}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={options.types}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />

            <Controller
                control={control}
                name="use"
                rules={{ ...validateRequiredRule(labels.use) }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={labels.use}
                        orientation={orientation}
                        value={value}
                        id={name}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        options={options.uses}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="address1"
                rules={{ ...maxLengthRule(100, labels.address1) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.address1}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="address2"
                rules={{ ...maxLengthRule(100, labels.address2) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.address2}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="city"
                rules={{ ...maxLengthRule(100, labels.city) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.city}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        name={name}
                        id={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="state"
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={labels.state}
                        orientation={orientation}
                        value={value}
                        onChange={(v) => {
                            handleStateChange(v);
                            onChange(v);
                        }}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.location.states}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="zipcode"
                rules={validZipCodeRule(labels.zip)}
                render={({ field: { onChange, value, name, onBlur }, fieldState: { error } }) => (
                    <ZipCodeInputField
                        id={name}
                        label={labels.zip}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        orientation={orientation}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="county"
                render={({ field: { onBlur, onChange, value, name } }) => (
                    <SingleSelect
                        label={labels.county}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={options.location.counties}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="censusTract"
                rules={validCensusTractRule(labels.censusTract)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <CensusTractInputField
                        id={name}
                        label={labels.censusTract}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        orientation={orientation}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="country"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        label={labels.country}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        options={options.location.countries}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="comment"
                rules={{ ...maxLengthRule(2000, labels.comment) }}
                render={({ field: { onChange, value, name } }) => (
                    <TextAreaField
                        label={labels.comment}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        id={name}
                        name={name}
                        sizing={sizing}
                    />
                )}
            />
        </>
    );
};

export { AddressDemographicFields };
