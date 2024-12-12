import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { EntryFieldsProps } from 'design-system/entry';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { Verification } from 'libs/verification';
import { EmailField, validateEmail, PhoneNumberInputField, validPhoneNumberRule } from 'libs/demographics/contact';
import { MaskedTextInputField } from 'design-system/input/text';
import { Input } from 'components/FormInputs/Input';
import { PhoneEmailEntry } from 'apps/patient/data';
import { usePatientPhoneCodedValues } from 'apps/patient/profile/phoneEmail/usePatientPhoneCodedValues';
import { TextAreaField } from 'design-system/input/text/TextAreaField';

const AS_OF_DATE_LABEL = 'Phone & email as of';
const TYPE_LABEL = 'Type';
const USE_LABEL = 'Use';
const PHONE_NUMBER_LABEL = 'Phone number';
const EMAIL_LABEL = 'Email';
const URL_LABEL = 'URL';
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
                        message: 'A Country code should be 1 to 3 digits'
                    }
                }}
                render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                    <MaskedTextInputField
                        id={name}
                        label="Country code"
                        type="tel"
                        mask="___"
                        pattern="^\+?\d{1,3}$"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        orientation={orientation}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="phoneNumber"
                rules={validPhoneNumberRule(PHONE_NUMBER_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <PhoneNumberInputField
                        id={name}
                        label={PHONE_NUMBER_LABEL}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        orientation={orientation}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="extension"
                rules={{
                    pattern: {
                        value: /^\+?\d{1,20}$/,
                        message: 'An Extension should be 1 to 20 digits'
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <MaskedTextInputField
                        id={name}
                        label="Extension"
                        mask="____________________"
                        pattern="^\+?\d{1,20}$"
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        orientation={orientation}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="email"
                rules={maxLengthRule(100, EMAIL_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Verification
                        constraint={validateEmail(EMAIL_LABEL)}
                        render={({ verify, violation }) => (
                            <EmailField
                                id={name}
                                label={EMAIL_LABEL}
                                onBlur={() => {
                                    verify(value);
                                    onBlur();
                                }}
                                onChange={onChange}
                                value={value}
                                orientation={orientation}
                                error={error?.message}
                                warnings={violation}
                            />
                        )}
                    />
                )}
            />
            <Controller
                control={control}
                name="url"
                rules={maxLengthRule(100, URL_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={URL_LABEL}
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
                    <TextAreaField
                        label={COMMENTS_LABEL}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        name={`phone-${name}`}
                        id={`phone-${name}`}
                        error={error?.message}
                    />
                )}
            />
        </section>
    );
};
