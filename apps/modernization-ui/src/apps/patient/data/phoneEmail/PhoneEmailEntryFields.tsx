import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule, validateRequiredRule, validEmailRule } from 'validation/entry';
import { validatePhoneNumber } from 'validation/phone';
import { EntryFieldsProps } from 'design-system/entry';
import { usePatientPhoneCodedValues } from 'apps/patient/profile/phoneEmail/usePatientPhoneCodedValues';
import { PhoneEmailEntry } from './entry';

const AS_OF_DATE_LABEL = 'Phone & email as of';
const TYPE_LABEL = 'Type';
const USE_LABEL = 'Use';
const COMMENTS_LABEL = 'Phone & email comments';

type PhoneEmailEntryFieldsProps = EntryFieldsProps;

export const PhoneEmailEntryFields = ({ orientation = 'horizontal' }: PhoneEmailEntryFieldsProps) => {
    const { control } = useFormContext<PhoneEmailEntry>();
    const coded = usePatientPhoneCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validateRequiredRule(AS_OF_DATE_LABEL), ...validDateRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={`phone-${name}`}
                        label={AS_OF_DATE_LABEL}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ ...validateRequiredRule(TYPE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={TYPE_LABEL}
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        value={value}
                        id={`phone-${name}`}
                        name={`phone-${name}`}
                        options={coded.types}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="use"
                rules={{ ...validateRequiredRule(USE_LABEL) }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={USE_LABEL}
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        value={value}
                        id={`phone-${name}`}
                        name={`phone-${name}`}
                        options={coded.uses}
                        error={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="countryCode"
                rules={{
                    pattern: {
                        value: /^\+?\d{1,3}$/,
                        message: 'A country code should be 1 to 3 digits'
                    }
                }}
                render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => {
                    return (
                        <Input
                            label="Country code"
                            orientation={orientation}
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            type="tel"
                            mask="___"
                            pattern="^\+?\d{1,3}$"
                            htmlFor={name}
                            id={name}
                            name={name}
                            error={error?.message}
                        />
                    );
                }}
            />
            <Controller
                control={control}
                name="phoneNumber"
                rules={{
                    validate: {
                        properNumber: (value) => validatePhoneNumber(value ?? '')
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Phone number"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        htmlFor={name}
                        id={name}
                        name={name}
                        mask="___-___-____"
                        pattern="\d{3}-\d{3}-\d{4}"
                        error={
                            error &&
                            'Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).'
                        }
                    />
                )}
            />
            <Controller
                control={control}
                name="extension"
                rules={{
                    pattern: {
                        value: /^\+?\d{1,4}$/,
                        message: 'A Extension should be 1 to 4 digits'
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Extension"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        mask="____"
                        pattern="^\+?\d{1,4}$"
                        htmlFor={name}
                        id={name}
                        name={name}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="email"
                rules={{
                    ...validEmailRule(100)
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Email"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        htmlFor={name}
                        id={name}
                        name={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="url"
                rules={maxLengthRule(100, 'URL')}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="URL"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        htmlFor={name}
                        id={name}
                        name={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="comment"
                rules={maxLengthRule(2000, COMMENTS_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={COMMENTS_LABEL}
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        type="text"
                        name={`phone-${name}`}
                        htmlFor={`phone-${name}`}
                        id={`phone-${name}`}
                        error={error?.message}
                        multiline
                    />
                )}
            />
        </section>
    );
};
