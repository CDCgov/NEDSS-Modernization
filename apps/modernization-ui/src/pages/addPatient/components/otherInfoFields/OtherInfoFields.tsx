import { useMemo } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { calculateAge } from 'date';
import { CodedValue, Indicator } from 'coded';
import { Controller, useWatch } from 'react-hook-form';
import FormCard from 'components/FormCard/FormCard';
import { Input } from 'components/FormInputs/Input';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';

type CodedValues = {
    deceased: CodedValue[];
    genders: CodedValue[];
    maritalStatuses: CodedValue[];
};

type Props = { id: string; title: string; control: any; coded: CodedValues; errors: any };

export default function OtherInfoFields({ id, title, control, coded, errors }: Props) {
    const selectedDeceased = useWatch({ control, name: 'deceased' });

    const currentBirthday = useWatch({ control, name: 'dob' });
    const age = useMemo(() => calculateAge(currentBirthday), [currentBirthday]);

    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="dateOfBirth"
                            render={({ field: { onChange, value, name } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    label="Date of birth"
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row className="flex-justify flex-align-center padding-y-2">
                    <Grid col={2} className="margin-top-1">
                        Current age:
                    </Grid>
                    <Grid col={10} className="margin-top-1">
                        {age}
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="currentGender"
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    label="Current sex"
                                    options={coded.genders}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="birthGender"
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    label="Birth sex"
                                    options={coded.genders}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="deceased"
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    label="Is this patient deceased?"
                                    options={coded.deceased}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="deceasedTime"
                            render={({ field: { onChange, value, name } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    label="Date of death"
                                    disabled={selectedDeceased !== Indicator.Yes}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="maritalStatus"
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    label="Marital Status"
                                    options={coded.maritalStatuses}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="stateHIVCase"
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    label="State HIV case ID"
                                    onChange={onChange}
                                    type="text"
                                    defaultValue={value}
                                    htmlFor={name}
                                    id={name}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
