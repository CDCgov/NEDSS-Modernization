import { Controller, useFieldArray, useFormContext } from 'react-hook-form';
import { Grid } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule } from 'validation/entry';
import { Verification } from 'libs/verification';
import { EmailField, maybeValidateEmail, PhoneNumberInputField, validPhoneNumberRule } from 'libs/demographics/contact';

const HOME_PHONE_LABEL = 'Home phone';
const WORK_PHONE_LABEL = 'Work phone';
const CELL_PHONE_LABEL = 'Cell phone';
const EMAIL_LABEL = 'Email';

type Props = {
    id: string;
    title: string;
};

export default function ContactFields({ id, title }: Props) {
    const { control } = useFormContext();

    const { fields: emailFields } = useFieldArray({
        control,
        name: 'emailAddresses'
    });

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="homePhone"
                            rules={validPhoneNumberRule(HOME_PHONE_LABEL)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <PhoneNumberInputField
                                    id={name}
                                    label={HOME_PHONE_LABEL}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    value={value}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row gap={2}>
                    <Grid col={4}>
                        <Controller
                            control={control}
                            name="workPhone"
                            rules={validPhoneNumberRule(WORK_PHONE_LABEL)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <PhoneNumberInputField
                                    id={name}
                                    label={WORK_PHONE_LABEL}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    value={value}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid className="ext-field" col={2}>
                        <Controller
                            control={control}
                            name="extension"
                            rules={maxLengthRule(20)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Input
                                    inputMode="numeric"
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    type="tel"
                                    label="Ext"
                                    defaultValue={value}
                                    htmlFor={name}
                                    id={name}
                                    mask="____________________"
                                    pattern="^\+?\d{1,20}$"
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row gap={2}>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="cellPhone"
                            rules={validPhoneNumberRule(CELL_PHONE_LABEL)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <PhoneNumberInputField
                                    id={name}
                                    label={CELL_PHONE_LABEL}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    value={value}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid col={6}>
                    {emailFields.map((item: { id: string }, index: number) => (
                        <Controller
                            key={item.id}
                            control={control}
                            name={`emailAddresses[${index}].email`}
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
                                            error={error?.message}
                                            warning={violation}
                                        />
                                    )}
                                />
                            )}
                        />
                    ))}
                </Grid>
            </Grid>
        </FormCard>
    );
}
