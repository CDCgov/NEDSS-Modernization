import { EntryFieldsProps } from 'design-system/entry';
import { Controller, useFormContext } from 'react-hook-form';
import { validDateRule, DatePickerInput } from 'design-system/date';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { SingleSelect } from 'design-system/select';
import { TextInputField } from 'design-system/input';
import { TextAreaField } from 'design-system/input/text/TextAreaField';
import { MaskedTextInputField } from 'design-system/input/text';
import { EmailField, maybeValidateEmail, PhoneNumberInputField, validPhoneNumberRule } from 'libs/demographics/contact';
import { Verification } from 'libs/verification';
import { labels, PhoneEmailDemographic } from '../phoneEmails';
import { PhoneEmailOptions } from './usePhoneEmailOptions';

type PhoneEmailDemographicFieldsProps = { options: PhoneEmailOptions } & EntryFieldsProps;

const PhoneEmailDemographicFields = ({
    orientation = 'horizontal',
    sizing = 'medium',
    options
}: PhoneEmailDemographicFieldsProps) => {
    const { control } = useFormContext<PhoneEmailDemographic>();

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
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={labels.use}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={name}
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
                name="countryCode"
                rules={{
                    pattern: {
                        value: /^\+?\d{1,20}$/,
                        message: 'A Country code should be 1 to 20 digits.'
                    }
                }}
                render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                    <MaskedTextInputField
                        id={name}
                        label={labels.countryCode}
                        type="tel"
                        mask="____________________"
                        pattern="^\+?\d{1,20}$"
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
                name="phoneNumber"
                rules={validPhoneNumberRule(labels.phoneNumber)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <PhoneNumberInputField
                        id={name}
                        label={labels.phoneNumber}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        orientation={orientation}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="extension"
                rules={{
                    pattern: {
                        value: /^\+?\d{1,20}$/,
                        message: 'An Extension should be 1 to 20 digits.'
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <MaskedTextInputField
                        id={name}
                        label={labels.extension}
                        mask="____________________"
                        pattern="^\+?\d{1,20}$"
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        orientation={orientation}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />

            <Controller
                control={control}
                name="email"
                rules={maxLengthRule(100, labels.email)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Verification
                        control={control}
                        name={name}
                        constraint={maybeValidateEmail(labels.email)}
                        render={({ verify, violation }) => (
                            <EmailField
                                id={name}
                                label={labels.email}
                                onBlur={() => {
                                    verify();
                                    onBlur();
                                }}
                                onChange={onChange}
                                value={value}
                                orientation={orientation}
                                error={error?.message}
                                warning={violation}
                                sizing={sizing}
                            />
                        )}
                    />
                )}
            />
            <Controller
                control={control}
                name="url"
                rules={maxLengthRule(100, labels.url)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        id={name}
                        label={labels.url}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        orientation={orientation}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="comment"
                rules={{ ...maxLengthRule(2000, labels.comment) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextAreaField
                        label={labels.comment}
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
        </>
    );
};

export { PhoneEmailDemographicFields };
