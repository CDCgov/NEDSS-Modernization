import { Controller, useFormContext } from 'react-hook-form';
import { EntryFieldsProps } from 'design-system/entry';
import { validEmailRule } from 'validation/entry';
import { PhoneNumberInputField, validPhoneNumberRule } from 'libs/demographics/contact';
import { BasicPhoneEmail } from '../entry';
import { MaskedTextInputField, TextInputField } from 'design-system/input/text';

const HOME_PHONE_LABEL = 'Home phone';
const WORK_PHONE_LABEL = 'Work phone';
const CELL_PHONE_LABEL = 'Cell phone';

type BasicPhoneEmailFieldsProps = EntryFieldsProps;

export const BasicPhoneEmailFields = ({ orientation = 'horizontal' }: BasicPhoneEmailFieldsProps) => {
    const { control } = useFormContext<BasicPhoneEmail>();

    return (
        <section>
            <Controller
                control={control}
                name="home"
                rules={validPhoneNumberRule(HOME_PHONE_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <PhoneNumberInputField
                        id={name}
                        sizing="compact"
                        label={HOME_PHONE_LABEL}
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
                name="work.phone"
                rules={validPhoneNumberRule(WORK_PHONE_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <PhoneNumberInputField
                        id={name}
                        sizing="compact"
                        label={WORK_PHONE_LABEL}
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
                name="work.extension"
                rules={{
                    pattern: {
                        value: /^\+?\d{1,20}$/,
                        message: 'An Extension should be 1 to 20 digits'
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <MaskedTextInputField
                        id={name}
                        label="Work phone extension"
                        mask="____________________"
                        pattern="^\+?\d{1,20}$"
                        value={value}
                        sizing="compact"
                        onBlur={onBlur}
                        onChange={onChange}
                        orientation={orientation}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="cell"
                rules={validPhoneNumberRule(CELL_PHONE_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <PhoneNumberInputField
                        id={name}
                        sizing="compact"
                        label={CELL_PHONE_LABEL}
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
                rules={{
                    ...validEmailRule(100)
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        sizing="compact"
                        label="Email"
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        id={name}
                        name={name}
                        error={error?.message}
                    />
                )}
            />
        </section>
    );
};
