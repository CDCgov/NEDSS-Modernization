import { Button, Grid, Label } from '@trussworks/react-uswds';
import { Controller, useFieldArray, useFormContext, useWatch } from 'react-hook-form';
import FormCard from 'components/FormCard/FormCard';
import { validatePhoneNumber } from 'validation/phone';
import { Input } from 'components/FormInputs/Input';
import { PhoneNumberInput } from 'components/FormInputs/PhoneNumberInput/PhoneNumberInput';
import { maxLengthRule } from 'validation/entry';
import { SelectInput } from 'components/FormInputs/SelectInput';
import styles from './contactFields.module.scss';
import { useEffect } from 'react';

type Props = {
    id: string;
    title: string;
};

type Type = {
    name: string;
    value: string;
    type: string;
};

const classification: Type[] = [
    { name: 'Home phone', value: 'H', type: 'PH' },
    { name: 'Work phone', value: 'WP', type: 'PH' },
    { name: 'Cell phone', value: 'MC', type: 'CP' }
];

export default function ContactFields({ id, title }: Props) {
    const { control, setValue } = useFormContext();

    const { fields: phoneNumberFields, append: phoneNumberAppend } = useFieldArray({
        control,
        name: 'phoneNumbers'
    });
    const { fields: emailFields, append: emailFieldAppend } = useFieldArray({
        control,
        name: 'emailAddresses'
    });

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
                                    placeholder="333-444-555"
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
                                    placeholder="333-444-555"
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
                                    placeholder="1234"
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
                            rules={{
                                validate: {
                                    properNumber: validatePhoneNumber
                                },
                                ...maxLengthRule(20)
                            }}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <PhoneNumberInput
                                    placeholder="333-444-555"
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
                {phoneNumberFields.map((item: any, index: number) => (
                    <Grid row gap={2} key={item.id}>
                        <div className={styles.phone}>
                            <Label htmlFor="Phone">Phone</Label>
                            <div className={styles.fields}>
                                <Grid col={5}>
                                    <Controller
                                        control={control}
                                        name={`phoneNumbers[${index}].use`}
                                        rules={{ required: { value: true, message: 'Type is required.' } }}
                                        render={({ field: { onChange, value }, fieldState: { error } }) => (
                                            <SelectInput
                                                defaultValue={value ? value : 'H'}
                                                onChange={onChange}
                                                htmlFor={'use'}
                                                options={classification}
                                                error={error?.message}
                                                required
                                            />
                                        )}
                                    />
                                </Grid>
                                <Grid col={7}>
                                    <Controller
                                        control={control}
                                        name={`phoneNumbers[${index}].number`}
                                        rules={{
                                            validate: {
                                                properNumber: validatePhoneNumber
                                            },
                                            ...maxLengthRule(20)
                                        }}
                                        render={({
                                            field: { onChange, onBlur, value, name },
                                            fieldState: { error }
                                        }) => (
                                            <PhoneNumberInput
                                                placeholder="333-444-555"
                                                onChange={onChange}
                                                onBlur={onBlur}
                                                defaultValue={value}
                                                id={name}
                                                error={error?.message}
                                                mask="___-___-____"
                                                pattern="\d{3}-\d{3}-\d{4}"
                                            />
                                        )}
                                    />
                                </Grid>
                            </div>
                        </div>
                    </Grid>
                ))}
                <Grid row>
                    <Grid col={12}>
                        <Button
                            type={'button'}
                            onClick={() =>
                                phoneNumberAppend({
                                    number: null,
                                    use: 'H',
                                    type: 'PH'
                                })
                            }
                            className="text-bold"
                            unstyled
                            style={{ margin: '1.125rem 0 0 0', padding: '0' }}>
                            + Add another phone number
                        </Button>
                    </Grid>
                </Grid>
                {emailFields.map((item: any, index: number) => (
                    <Grid row key={item.id}>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name={`emailAddresses[${index}].email`}
                                rules={{
                                    pattern: {
                                        value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                                        message: 'Please enter a valid email address (example: youremail@website.com)'
                                    },
                                    ...maxLengthRule(100)
                                }}
                                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                    <Input
                                        placeholder="jdoe@gmail.com"
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
                ))}
                <Grid row>
                    <Grid col={12}>
                        <Button
                            type={'button'}
                            onClick={() =>
                                emailFieldAppend({
                                    email: null
                                })
                            }
                            className="text-bold"
                            unstyled
                            style={{ margin: '1.125rem 0 0 0', padding: '0' }}>
                            + Add another email address
                        </Button>
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
