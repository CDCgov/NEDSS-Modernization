import { Button, Grid } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { Controller } from 'react-hook-form';
import { Input } from '../../../../components/FormInputs/Input';

export default function ContactFields({
    id,
    title,
    control,
    phoneNumberFields,
    emailFields,
    phoneNumberAppend,
    emailFieldAppend
}: {
    id?: string;
    title?: string;
    control?: any;
    phoneNumberFields: any;
    emailFields: any;
    phoneNumberAppend: any;
    emailFieldAppend: any;
}) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="homePhone"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    placeholder="333-444-555"
                                    onChange={onChange}
                                    type="tel"
                                    label="Home phone"
                                    defaultValue={value}
                                    htmlFor="homePhone"
                                    id="homePhone"
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
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    placeholder="333-444-555"
                                    onChange={onChange}
                                    type="tel"
                                    label="Work phone"
                                    defaultValue={value}
                                    htmlFor="workPhone"
                                    id="workPhone"
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
                                name={`phoneNumbers[${index}].cellPhone`}
                                render={({ field: { onChange, value } }) => (
                                    <Input
                                        inputMode="numeric"
                                        placeholder="333-444-555"
                                        onChange={onChange}
                                        type="tel"
                                        label="Cell phone"
                                        defaultValue={value}
                                        htmlFor={`phoneNumbers[${index}].cellPhone`}
                                        id={`phoneNumbers[${index}].cellPhone`}
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
                                name={`emailAddresses[${index}].email`}
                                render={({ field: { onChange, value } }) => (
                                    <Input
                                        placeholder="jdoe@gmail.com"
                                        onChange={onChange}
                                        type="text"
                                        label="Email"
                                        defaultValue={value}
                                        htmlFor={`emailAddresses[${index}].email`}
                                        id={`emailAddresses[${index}].email`}
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
