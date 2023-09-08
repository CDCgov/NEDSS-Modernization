import { Controller } from 'react-hook-form';
import { Grid } from '@trussworks/react-uswds';
import { CodedValue } from 'coded';
import FormCard from 'components/FormCard/FormCard';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Input } from 'components/FormInputs/Input';

type CodedValues = {
    suffixes: CodedValue[];
};

type Props = { id: string; title: string; control: any; coded: CodedValues };

export default function NameFields({ id, title, control, coded }: Props) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="lastName"
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Last"
                                    value={value}
                                    htmlFor={name}
                                    id={name}
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
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    value={value}
                                    htmlFor={name}
                                    id={name}
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
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    value={value}
                                    htmlFor={name}
                                    id={name}
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
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    defaultValue={value}
                                    name={name}
                                    htmlFor={name}
                                    label="Suffix"
                                    options={coded.suffixes}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
