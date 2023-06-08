import { Button, Grid } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { Controller } from 'react-hook-form';
import { Input } from '../../../../components/FormInputs/Input';
import { PhoneNumberInput } from 'components/FormInputs/PhoneNumberInput/PhoneNumberInput';
import { validatePhoneNumber } from 'validation/phone';

export default function ContactFields({
    id,
    title,
    control,
    phoneNumberFields,
    emailFields,
    phoneNumberAppend,
    emailFieldAppend,
    errors
}: {
    id?: string;
    title?: string;
    control?: any;
    phoneNumberFields: any;
    emailFields: any;
    phoneNumberAppend: any;
    emailFieldAppend: any;
    errors: any;
}) {
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
                                    properNumber: (value) => validatePhoneNumber(value)
                                }
                            }}
                            render={({ field: { onChange, value } }) => (
                                <PhoneNumberInput
                                    placeholder="333-444-555"
                                    onChange={onChange}
                                    label="Home phone"
                                    defaultValue={value}
                                    id="homePhone"
                                    error={errors && errors.homePhone && 'Invalid phone number'}
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
                                    properNumber: (value) => validatePhoneNumber(value)
                                }
                            }}
                            render={({ field: { onChange, value } }) => (
                                <PhoneNumberInput
                                    placeholder="333-444-555"
                                    onChange={onChange}
                                    label="Work phone"
                                    defaultValue={value}
                                    id="workPhone"
                                    error={errors && errors.workPhone && 'Invalid phone number'}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={2}>
                        <Controller
                            control={control}
                            name="ext"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    inputMode="numeric"
                                    placeholder="1234"
                                    onChange={onChange}
                                    type="tel"
                                    label="Ext"
                                    defaultValue={value}
                                    htmlFor="ext"
                                    id="ext"
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
                                name={`cellPhone_${index}`}
                                rules={{
                                    validate: {
                                        properNumber: (value) => validatePhoneNumber(value)
                                    }
                                }}
                                render={({ field: { onChange, value } }) => (
                                    <PhoneNumberInput
                                        placeholder="333-444-555"
                                        onChange={onChange}
                                        label="Cell phone"
                                        defaultValue={value}
                                        id={`cellPhone_${index}`}
                                        error={errors && errors[`cellPhone_${index}`] && 'Invalid phone number'}
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
                                    cellPhone: null
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
                                name={`emailAddresses_${index}`}
                                rules={{
                                    pattern: {
                                        value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                                        message: 'Invalid email'
                                    }
                                }}
                                render={({ field: { onChange, value } }) => (
                                    <Input
                                        placeholder="jdoe@gmail.com"
                                        onChange={onChange}
                                        type="text"
                                        label="Email"
                                        defaultValue={value}
                                        htmlFor={`emailAddresses_${index}`}
                                        id={`emailAddresses_${index}`}
                                        error={
                                            errors &&
                                            errors[`emailAddresses_${index}`] &&
                                            errors[`emailAddresses_${index}`].message
                                        }
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
