import { Controller, useFormContext } from 'react-hook-form';
import { Grid } from '@trussworks/react-uswds';
import { CodedValue } from 'coded';
import FormCard from 'components/FormCard/FormCard';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Input } from 'components/FormInputs/Input';
import { validNameRule } from 'validation/entry';
import { InitalEntryType } from 'pages/addPatient/AddPatient';

type CodedValues = {
    suffixes: CodedValue[];
};

type Props = { id: string; title: string; coded: CodedValues; initalEntry?: InitalEntryType };

const NameFields = ({ id, title, coded, initalEntry }: Props) => {
    const { control } = useFormContext();
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="lastName"
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                                <Input
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    type="text"
                                    label="Last"
                                    defaultValue={initalEntry?.lastName}
                                    htmlFor={name}
                                    id={name}
                                    name={name}
                                    error={error?.message}
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
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                                <Input
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    type="text"
                                    label="First"
                                    defaultValue={initalEntry?.firstName}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
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
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    type="text"
                                    label="Middle"
                                    defaultValue={value}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
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
};

export { NameFields };
