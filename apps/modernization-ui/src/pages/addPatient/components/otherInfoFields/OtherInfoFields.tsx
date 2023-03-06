import { Grid } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { Controller } from 'react-hook-form';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { Gender } from '../../../../generated/graphql/schema';
import { Input } from '../../../../components/FormInputs/Input';

export default function OtherInfoFields({ id, title, control }: { id?: string; title?: string; control: any }) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="dob"
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="dob"
                                    htmlFor={'dob'}
                                    label="Date of birth"
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="gender"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="gender"
                                    htmlFor={'gender'}
                                    label="Current sex"
                                    options={[
                                        { name: 'Male', value: Gender.M },
                                        { name: 'Female', value: Gender.F },
                                        { name: 'Other', value: Gender.U }
                                    ]}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="birthSex"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="birthSex"
                                    htmlFor={'birthSex'}
                                    label="Birth sex"
                                    options={[
                                        { name: 'Male', value: Gender.M },
                                        { name: 'Female', value: Gender.F },
                                        { name: 'Other', value: Gender.U }
                                    ]}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="birthSex"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="birthSex"
                                    htmlFor={'birthSex'}
                                    label="Is this patient deceased?"
                                    options={[
                                        { name: 'Yes', value: 'Y' },
                                        { name: 'No', value: 'N' }
                                    ]}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="dod"
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="dod"
                                    htmlFor={'dod'}
                                    label="Date of death"
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
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="maritalStatus"
                                    htmlFor={'maritalStatus'}
                                    label="Marital Status"
                                    options={[
                                        {
                                            name: 'Annulled',
                                            value: 'A'
                                        },
                                        {
                                            name: 'Common Law',
                                            value: 'C'
                                        },
                                        {
                                            name: 'Divorced',
                                            value: 'D'
                                        },
                                        {
                                            name: 'Domestic partner',
                                            value: 'T'
                                        },
                                        {
                                            name: 'Interlocutory',
                                            value: 'I'
                                        },
                                        {
                                            name: 'Legally separated',
                                            value: 'L'
                                        },
                                        {
                                            name: 'Living Together',
                                            value: 'G'
                                        },
                                        {
                                            name: 'Married',
                                            value: 'M'
                                        },
                                        {
                                            name: 'Other',
                                            value: 'O'
                                        },
                                        {
                                            name: 'Polygamous',
                                            value: 'P'
                                        },
                                        {
                                            name: 'Refused to answer',
                                            value: 'R'
                                        },
                                        {
                                            name: 'Separated',
                                            value: 'E'
                                        },
                                        {
                                            name: 'Single, never married',
                                            value: 'S'
                                        },
                                        {
                                            name: 'Unknown',
                                            value: 'U'
                                        },
                                        {
                                            name: 'Unmarried',
                                            value: 'B'
                                        },
                                        {
                                            name: 'Unreported',
                                            value: 'F'
                                        },
                                        {
                                            name: 'Widowed',
                                            value: 'W'
                                        }
                                    ]}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="hivId"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    label="State HIV case ID"
                                    onChange={onChange}
                                    type="text"
                                    defaultValue={value}
                                    htmlFor="hivId"
                                    id="hivId"
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
