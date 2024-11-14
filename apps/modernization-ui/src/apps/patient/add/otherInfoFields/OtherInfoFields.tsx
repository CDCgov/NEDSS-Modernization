import { Grid } from '@trussworks/react-uswds';
import { CodedValue } from 'coded';
import FormCard from 'components/FormCard/FormCard';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { calculateAge } from 'date';
import { Deceased } from 'generated/graphql/schema';
import { useMemo } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { NewPatientEntry } from '../NewPatientEntry';

type CodedValues = {
    deceased: CodedValue[];
    genders: CodedValue[];
    maritalStatuses: CodedValue[];
};

type Props = { id: string; title: string; coded: CodedValues };

export default function OtherInfoFields({ id, title, coded }: Readonly<Props>) {
    const { control } = useFormContext<NewPatientEntry>();

    const selectedDeceased = useWatch({ control, name: 'deceased' });

    const currentBirthday = useWatch({ control, name: 'dateOfBirth' });
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
                                    disableFutureDates
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
                {selectedDeceased === Deceased.Y && (
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
                                        label="Date of death"
                                        disableFutureDates
                                        disabled={selectedDeceased !== Deceased.Y}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                )}
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
                                    label="Marital status"
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
                            rules={maxLengthRule(20)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Input
                                    label="State HIV case ID"
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    type="text"
                                    defaultValue={value}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
