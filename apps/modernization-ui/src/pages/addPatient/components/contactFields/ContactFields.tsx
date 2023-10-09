import { Button, Grid } from '@trussworks/react-uswds';
import { Controller, useFieldArray, useFormContext } from 'react-hook-form';
import FormCard from 'components/FormCard/FormCard';
import { validatePhoneNumber } from 'validation/phone';
import { Input } from 'components/FormInputs/Input';
import { PhoneNumberInput } from 'components/FormInputs/PhoneNumberInput/PhoneNumberInput';
import { maxLengthRule } from 'validation/entry';
import { InitalEntryType } from 'pages/addPatient/AddPatient';

type Props = {
    id: string;
    title: string;
    initialEntry?: InitalEntryType;
};

export default function ContactFields({ id, title, initialEntry }: Props) {
    const { control } = useFormContext();

    const { fields: phoneNumberFields, append: phoneNumberAppend } = useFieldArray({
        control,
        name: 'phoneNumbers'
    });

    const { fields: emailFields, append: emailFieldAppend } = useFieldArray({
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
                            rules={{
                                validate: {
                                    properNumber: validatePhoneNumber
                                },
                                ...maxLengthRule(20)
                            }}
                            render={({ field: { onChange, onBlur }, fieldState: { error } }) => (
                                <PhoneNumberInput
                                    placeholder="333-444-555"
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    label="Home phone"
                                    value={initialEntry?.homePhone}
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
                    <Grid col={2}>
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
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                {phoneNumberFields.map((item: any, index: number) => (
                    <Grid row key={item.id}>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name={`phoneNumbers[${index}].number`}
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
                ))}
                <Grid row>
                    <Grid col={12}>
                        <Button
                            type={'button'}
                            onClick={() =>
                                phoneNumberAppend({
                                    number: null,
                                    type: 'CP',
                                    use: 'MC'
                                })
                            }
                            className="text-bold"
                            unstyled
                            style={{ margin: '0', padding: '0' }}>
                            + Add another phone number
                        </Button>
                    </Grid>
                </Grid>
                {emailFields.map((item: any, index: number) => (
                    <Grid row key={item.id}>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                defaultValue={initialEntry?.emailAddresses?.[index].email}
                                name={`emailAddresses[${index}].email`}
                                rules={{
                                    pattern: {
                                        value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                                        message: 'Please enter a valid email address (example: youremail@website.com)'
                                    },
                                    ...maxLengthRule(100)
                                }}
                                render={({ field: { onChange, onBlur, name }, fieldState: { error } }) => (
                                    <Input
                                        placeholder="jdoe@gmail.com"
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        type="text"
                                        label="Email"
                                        defaultValue={initialEntry?.emailAddresses?.[index].email}
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
                            style={{ margin: '0', padding: '0' }}>
                            + Add another email address
                        </Button>
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
