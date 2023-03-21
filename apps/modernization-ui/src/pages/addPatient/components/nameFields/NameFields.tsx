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
                            name="last-name"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Last"
                                    defaultValue={value}
                                    htmlFor="last-name"
                                    id="last-name"
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="first-name"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    defaultValue={value}
                                    htmlFor="first-name"
                                    id="first-name"
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
                            name="middle-name"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    defaultValue={value}
                                    htmlFor="middle-name"
                                    id="middle-name"
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
