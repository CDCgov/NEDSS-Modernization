import { Grid } from '@trussworks/react-uswds';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import FormCard from 'components/FormCard/FormCard';
import { validatePhoneNumber } from 'validation/phone';
import { Input } from 'components/FormInputs/Input';
import { PhoneNumberInput } from 'components/FormInputs/PhoneNumberInput/PhoneNumberInput';
import { maxLengthRule } from 'validation/entry';
import { useEffect } from 'react';

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
                            rules={{
                                validate: {
                                    properNumber: validatePhoneNumber
                                },
                                ...maxLengthRule(20)
                            }}
                            render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                <PhoneNumberInput
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    label="Home phone"
                                    defaultValue={value}
                                    id="homePhone"
                                    error={error?.message}
                                    mask="___-___-____"
                                    pattern="\d{3}-\d{3}-\d{4}"
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
                            rules={{
                                validate: {
                                    properNumber: validatePhoneNumber
                                },
                                ...maxLengthRule(20)
                            }}
                            render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                <PhoneNumberInput
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    label="Work phone"
                                    defaultValue={value}
                                    id="workPhone"
                                    error={error?.message}
                                    mask="___-___-____"
                                    pattern="\d{3}-\d{3}-\d{4}"
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
                            rules={{
                                validate: {
                                    properNumber: validatePhoneNumber
                                },
                                ...maxLengthRule(20)
                            }}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <PhoneNumberInput
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    label="Cell phone"
                                    defaultValue={value}
                                    id={name}
                                    error={error?.message}
                                    mask="___-___-____"
                                    pattern="\d{3}-\d{3}-\d{4}"
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
                            pattern: {
                                value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                                message: 'Please enter a valid email address (example: youremail@website.com)'
                            },
                            ...maxLengthRule(100)
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
