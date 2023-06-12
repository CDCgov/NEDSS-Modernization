import { Grid } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { Suffix } from '../../../../generated/graphql/schema';
import { Controller } from 'react-hook-form';
import { Input } from '../../../../components/FormInputs/Input';

export default function NameFields({ id, title, control }: { id?: string; title?: string; control?: any }) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="lastName"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Last"
                                    defaultValue={value}
                                    htmlFor="lastName"
                                    id="lastName"
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="firstName"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    defaultValue={value}
                                    htmlFor="firstName"
                                    id="firstName"
                                    label="First"
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="middleName"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    defaultValue={value}
                                    htmlFor="middleName"
                                    id="middleName"
                                    label="Middle"
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="suffix"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name="suffix"
                                    htmlFor={'suffix'}
                                    label="Suffix"
                                    options={Object.values(Suffix).map((suffix) => {
                                        return {
                                            name: suffix,
                                            value: suffix
                                        };
                                    })}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
