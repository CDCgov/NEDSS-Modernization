import { usePatientSexBirthCodedValues } from 'apps/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { asAgeDisplay } from 'date/asAgeDisplay';
import { DataDisplay } from 'design-system/data-display/DataDisplay';
import { SingleSelect } from 'design-system/select';
import { useCountyCodedValues } from 'location';
import { useEffect, useMemo } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { BirthEntry, SexEntry } from '../entry';

const UNKNOWN_GENDER = 'U';
export const SexAndBirthEntryFields = () => {
    const { control, setValue } = useFormContext<{ birthAndSex: BirthEntry & SexEntry }>();
    const currentBirthday = useWatch({ control, name: 'birthAndSex.bornOn' });
    const selectedCurrentGender = useWatch({ control, name: 'birthAndSex.current' });
    const selectedState = useWatch({ control, name: 'birthAndSex.state' });
    const selectedMultipleBirth = useWatch({ control, name: 'birthAndSex.multiple' });
    const age = useMemo(() => asAgeDisplay(currentBirthday), [currentBirthday]);

    const coded = usePatientSexBirthCodedValues();
    const { counties } = useCountyCodedValues(selectedState?.value);

    useEffect(() => {
        if (!selectedState) {
            setValue('birthAndSex.county', undefined);
        }
    }, [selectedState]);

    useEffect(() => {
        if (UNKNOWN_GENDER !== selectedCurrentGender?.value) {
            setValue('birthAndSex.unknownReason', undefined);
        }
    }, [selectedCurrentGender]);

    return (
        <section>
            <Controller
                control={control}
                name="birthAndSex.asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Sex & birth information as of"
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
                name="birthAndSex.bornOn"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <DatePickerInput
                        label="Date of birth"
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        disableFutureDates
                    />
                )}
            />
            <DataDisplay title="Current age" value={age} />
            <Controller
                control={control}
                name="birthAndSex.current"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Current sex"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.genders}
                    />
                )}
            />
            {selectedCurrentGender?.value === UNKNOWN_GENDER && (
                <Controller
                    control={control}
                    name="birthAndSex.unknownReason"
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <SingleSelect
                            label="Unknown reason"
                            orientation="horizontal"
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            options={coded.genderUnknownReasons}
                        />
                    )}
                />
            )}

            <Controller
                control={control}
                name="birthAndSex.transgenderInformation"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Transgender information"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.preferredGenders}
                    />
                )}
            />
            <Controller
                control={control}
                name="birthAndSex.additionalGender"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <Input
                        label="Additional gender"
                        orientation="horizontal"
                        placeholder="No Data"
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
                name="birthAndSex.sex"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Birth sex"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.genders}
                    />
                )}
            />
            <Controller
                control={control}
                name="birthAndSex.multiple"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Multiple birth"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.multipleBirth}
                    />
                )}
            />
            {selectedMultipleBirth?.value === 'Y' && (
                <Controller
                    control={control}
                    name="birthAndSex.order"
                    rules={{ min: { value: 0, message: 'Must be a positive number' } }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            label="Birth order"
                            orientation="horizontal"
                            placeholder="No Data"
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
                name="birthAndSex.city"
                rules={maxLengthRule(100)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Birth city"
                        orientation="horizontal"
                        placeholder="No Data"
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
                name="birthAndSex.state"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Birth state"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.states.all}
                    />
                )}
            />
            <Controller
                control={control}
                name="birthAndSex.county"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Birth county"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={counties}
                    />
                )}
            />

            <Controller
                control={control}
                name="birthAndSex.country"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Birth country"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.countries}
                    />
                )}
            />
        </section>
    );
};
