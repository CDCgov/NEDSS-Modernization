import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { Grid } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule, validEmailRule } from 'validation/entry';
import { PhoneNumberInputField, validPhoneNumberRule } from 'libs/demographics/contact';

const HOME_PHONE_LABEL = 'Home phone';
const WORK_PHONE_LABEL = 'Work phone';
const CELL_PHONE_LABEL = 'Cell phone';

type Props = {
    id: string;
    title: string;
};

export default function ContactFields({ id, title }: Props) {
    const { control, setValue } = useFormContext();

    const phoneFields = useWatch({ control: control, name: 'phoneNumbers' });

    useEffect(() => {
        if (phoneFields) {
            phoneFields.forEach((number: any, i: number) => {
                if (number.use === 'MC') {
                    setValue(`phoneNumbers.${i}.type`, 'CP');
                } else {
                    setValue(`phoneNumbers.${i}.type`, 'PH');
                }
            });
        }
    }, [JSON.stringify(phoneFields)]);

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
                                    mask="________"
                                    pattern="^\+?\d{1,8}$"
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
                    <Controller
                        control={control}
                        name={`emailAddress`}
                        rules={{
                            ...validEmailRule(100)
                        }}
                        render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                            <Input
                                onChange={onChange}
                                onBlur={onBlur}
                                type="text"
                                label="Email"
                                defaultValue={value}
                                htmlFor={name}
                                id={name}
                                error={error?.message}
                            />
                        )}
                    />
                </Grid>
            </Grid>
        </FormCard>
    );
}
