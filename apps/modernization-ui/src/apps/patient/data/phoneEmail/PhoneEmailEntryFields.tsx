import { usePatientPhoneCodedValues } from 'apps/patient/profile/phoneEmail/usePatientPhoneCodedValues';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SingleSelect } from 'design-system/select';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { validatePhoneNumber } from 'validation/phone';
import { PhoneEmailEntry } from '../entry';

export const PhoneEmailEntryFields = () => {
    const { control } = useFormContext<PhoneEmailEntry>();
    const coded = usePatientPhoneCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Phone & email as of"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        name={`phone-${name}`}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ required: { value: true, message: 'Type is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Type"
                        orientation="horizontal"
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
                rules={{ required: { value: true, message: 'Use is required.' } }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Use"
                        orientation="horizontal"
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
                            orientation="horizontal"
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            type="tel"
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
                        orientation="horizontal"
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
                        orientation="horizontal"
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        htmlFor={name}
                        id={name}
                        name={name}
                        error={error && 'Invalid extension'}
                    />
                )}
            />

            <Controller
                control={control}
                name="email"
                rules={{
                    pattern: {
                        value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                        message: 'Please enter a valid email address (example: youremail@website.com)'
                    },
                    ...maxLengthRule(100)
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Email"
                        orientation="horizontal"
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
                rules={maxLengthRule(100)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="URL"
                        orientation="horizontal"
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
                rules={maxLengthRule(2000)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Phone & email comments"
                        orientation="horizontal"
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
