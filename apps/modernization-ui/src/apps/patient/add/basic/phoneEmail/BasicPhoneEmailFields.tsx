import { Controller, useFormContext } from 'react-hook-form';
import { EntryFieldsProps } from 'design-system/entry';
import { maxLengthRule } from 'validation/entry';
import { Verification } from 'libs/verification';
import { EmailField, maybeValidateEmail, PhoneNumberInputField, validPhoneNumberRule } from 'libs/demographics/contact';
import { BasicPhoneEmail } from '../entry';
import { MaskedTextInputField } from 'design-system/input/text';

const HOME_PHONE_LABEL = 'Home phone';
const WORK_PHONE_LABEL = 'Work phone';
const CELL_PHONE_LABEL = 'Cell phone';
const EMAIL_LABEL = 'Email';

export const BasicPhoneEmailFields = ({
    orientation = 'horizontal',
    sizing = 'medium',
    groupName = ''
}: EntryFieldsProps & { groupName?: string }) => {
    const { control } = useFormContext<{ phoneEmail: BasicPhoneEmail }>();

    return (
        <section>
            <Controller
                control={control}
                name="phoneEmail.home"
                rules={validPhoneNumberRule(HOME_PHONE_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <PhoneNumberInputField
                        id={name}
                        label={HOME_PHONE_LABEL}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        orientation={orientation}
                        sizing={sizing}
                        error={error?.message}
                        aria-label={`${groupName} ${HOME_PHONE_LABEL}`}
                    />
                )}
            />
            <Controller
                control={control}
                name="phoneEmail.work.phone"
                rules={validPhoneNumberRule(WORK_PHONE_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <PhoneNumberInputField
                        id={name}
                        label={WORK_PHONE_LABEL}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                        aria-label={`${groupName} ${WORK_PHONE_LABEL}`}
                    />
                )}
            />

            <Controller
                control={control}
                name="phoneEmail.work.extension"
                rules={{
                    pattern: {
                        value: /^\+?\d{1,20}$/,
                        message: 'An Extension should be 1 to 20 digits.'
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <MaskedTextInputField
                        id={name}
                        label="Work phone extension"
                        mask="____________________"
                        pattern="^\+?\d{1,20}$"
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                        aria-label={`${groupName} Work phone extension`}
                    />
                )}
            />

            <Controller
                control={control}
                name="phoneEmail.cell"
                rules={validPhoneNumberRule(CELL_PHONE_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <PhoneNumberInputField
                        id={name}
                        label={CELL_PHONE_LABEL}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                        aria-label={`${groupName} ${CELL_PHONE_LABEL}`}
                    />
                )}
            />

            <Controller
                control={control}
                name="phoneEmail.email"
                rules={maxLengthRule(100, EMAIL_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Verification
                        control={control}
                        name={name}
                        constraint={maybeValidateEmail(EMAIL_LABEL)}
                        render={({ verify, violation }) => (
                            <EmailField
                                id={name}
                                label={EMAIL_LABEL}
                                onBlur={() => {
                                    verify();
                                    onBlur();
                                }}
                                onChange={onChange}
                                value={value}
                                sizing={sizing}
                                orientation={orientation}
                                error={error?.message}
                                warning={violation}
                                aria-label={`${groupName} ${EMAIL_LABEL}`}
                            />
                        )}
                    />
                )}
            />
        </section>
    );
};
