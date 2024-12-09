import { useEffect, useMemo } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { usePatientSexBirthCodedValues } from 'apps/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { Input } from 'components/FormInputs/Input';
import { displayAgeAsOfToday } from 'date/displayAge';
import { useCountyCodedValues } from 'location';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { BirthEntry, SexEntry } from 'apps/patient/data/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { ValueView } from 'design-system/data-display/ValueView';

const UNKNOWN_GENDER = 'U';

const AS_OF_DATE_LABEL = 'Sex & birth information as of';
const BORN_ON_LABEL = 'Date of birth';
const BIRTH_CITY_LABEL = 'Birth city';

type SexAndBirthEntryFieldsProps = EntryFieldsProps;

export const SexAndBirthEntryFields = ({ orientation = 'horizontal' }: SexAndBirthEntryFieldsProps) => {
    const { control, setValue } = useFormContext<{ birthAndSex: BirthEntry & SexEntry }>();
    const currentBirthday = useWatch({ control, name: 'birthAndSex.bornOn' });
    const selectedCurrentGender = useWatch({ control, name: 'birthAndSex.current' });
    const selectedState = useWatch({ control, name: 'birthAndSex.state' });
    const selectedMultipleBirth = useWatch({ control, name: 'birthAndSex.multiple' });
    const age = useMemo(() => displayAgeAsOfToday(currentBirthday), [currentBirthday]);

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
                rules={{ ...validateRequiredRule(AS_OF_DATE_LABEL), ...validDateRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Sex & birth information as of"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="birthAndSex.bornOn"
                rules={validDateRule(BORN_ON_LABEL)}
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <DatePickerInput
                        label={BORN_ON_LABEL}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                    />
                )}
            />
            <ValueView title="Current age" value={age} />
            <Controller
                control={control}
                name="birthAndSex.current"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Current sex"
                        orientation={orientation}
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
                    shouldUnregister
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <SingleSelect
                            label="Unknown reason"
                            orientation={orientation}
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
                        orientation={orientation}
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
                rules={maxLengthRule(20)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Additional gender"
                        orientation={orientation}
                        placeholder="No Data"
                        onChange={onChange}
                        onBlur={onBlur}
                        type="text"
                        id={name}
                        name={name}
                        htmlFor={name}
                        defaultValue={value}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="birthAndSex.sex"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Birth sex"
                        orientation={orientation}
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
                        orientation={orientation}
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
                    shouldUnregister
                    rules={{ min: { value: 0, message: 'Must be a positive number' } }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            label="Birth order"
                            orientation={orientation}
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
                rules={maxLengthRule(100, BIRTH_CITY_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={BIRTH_CITY_LABEL}
                        orientation={orientation}
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
                        orientation={orientation}
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
                        orientation={orientation}
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
                        orientation={orientation}
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
