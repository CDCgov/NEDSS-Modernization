import { useEffect, useMemo } from 'react';
import { EntryWrapper } from 'components/Entry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { calculateAge } from 'date';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { BirthAndGenderEntry } from './BirthAndGenderEntry';
import { usePatientSexBirthCodedValues } from './usePatientSexBirthCodedValues';
import { useCountryOptions, useCountyOptions, useStateOptions } from 'options/location';

const UNKNOWN_GENDER = 'U';
const ENTRY_FIELD_PLACEHOLDER = '';

export const BirthAndGenderEntryFields = () => {
    const { control, setValue } = useFormContext<BirthAndGenderEntry>();
    const currentBirthday = useWatch({ control, name: 'birth.bornOn' });
    const age = useMemo(() => calculateAge(currentBirthday), [currentBirthday]);
    const selectedCurrentGender = useWatch({ control, name: 'gender.current' });
    const selectedState = useWatch({ control, name: 'birth.state' });
    const selectedMultipleBirth = useWatch({ control, name: 'birth.multipleBirth' });

    const coded = usePatientSexBirthCodedValues();

    const countries = useCountryOptions();
    const states = useStateOptions();
    const counties = useCountyOptions(selectedState);

    useEffect(() => {
        if (!selectedState) {
            setValue('birth.county', '');
        }
    }, [selectedState]);

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
                name="birth.bornOn"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <DatePickerInput
                        label="Date of birth:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        disableFutureDates
                    />
                )}
            />
            <EntryWrapper label="Current age:" htmlFor="currentAge" orientation="horizontal">
                <div>{age}</div>
            </EntryWrapper>
            <Controller
                control={control}
                name="gender.current"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Current sex:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.genders}
                    />
                )}
            />
            <Controller
                control={control}
                name="gender.unknownReason"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Unknown reason:"
                        orientation="horizontal"
                        disabled={selectedCurrentGender !== UNKNOWN_GENDER}
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.genderUnknownReasons}
                    />
                )}
            />
            <Controller
                control={control}
                name="gender.preferred"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Transgender information:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.preferredGenders}
                    />
                )}
            />
            <Controller
                control={control}
                name="gender.additional"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <Input
                        label="Additional gender:"
                        orientation="horizontal"
                        placeholder={ENTRY_FIELD_PLACEHOLDER}
                        onChange={onChange}
                        onBlur={onBlur}
                        type="text"
                        id={name}
                        name={name}
                        htmlFor={name}
                        defaultValue={value}
                    />
                )}
            />
            <Controller
                control={control}
                name="birth.gender"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Birth sex:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.genders}
                    />
                )}
            />
            <Controller
                control={control}
                name="birth.multipleBirth"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Multiple birth:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={coded.multipleBirth}
                    />
                )}
            />
            {selectedMultipleBirth === 'Y' && (
                <Controller
                    control={control}
                    name="birth.birthOrder"
                    rules={{ min: { value: 0, message: 'Must be a positive number' } }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            label="Birth order:"
                            orientation="horizontal"
                            placeholder={ENTRY_FIELD_PLACEHOLDER}
                            onChange={onChange}
                            onBlur={onBlur}
                            type="text"
                            defaultValue={value ? value.toString() : ''}
                            id={name}
                            htmlFor={name}
                            name={name}
                            mask="_____"
                            pattern="\d{5}"
                            error={error?.message}
                        />
                    )}
                />
            )}
            <Controller
                control={control}
                name="birth.city"
                rules={maxLengthRule(100)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Birth city:"
                        orientation="horizontal"
                        placeholder={ENTRY_FIELD_PLACEHOLDER}
                        onChange={onChange}
                        onBlur={onBlur}
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
                name="birth.state"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Birth state:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={states}
                    />
                )}
            />
            <Controller
                control={control}
                name="birth.county"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Birth county:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={counties}
                    />
                )}
            />

            <Controller
                control={control}
                name="birth.country"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SelectInput
                        label="Birth country:"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        htmlFor={name}
                        options={countries}
                    />
                )}
            />
        </section>
    );
};
