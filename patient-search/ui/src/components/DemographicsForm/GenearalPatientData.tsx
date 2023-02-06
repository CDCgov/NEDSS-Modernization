import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, useForm } from 'react-hook-form';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Input } from '../FormInputs/Input';
import { SelectInput } from '../FormInputs/SelectInput';

export const GeneralPatientInformation = ({ setGeneralForm }: any) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = (data: any) => {
        console.log(data);
        setGeneralForm();
    };

    return (
        <>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    As of:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="nameAsOf"
                        render={({ field: { onChange, value } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                onChange={onChange}
                                name="nameAsOf"
                                htmlFor={'nameAsOf'}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Marital status:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="maritalStatus"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'maritalStatus'}
                                options={[]}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Mother's maiden name:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="motherName"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="motherName"
                                id="motherName"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Number of adults in residence:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="adults"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="adults"
                                id="adults"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Number of children in residence:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="children"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="children"
                                id="children"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Primary occupation:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="occupation"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'occupation'} options={[]} />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Highest level of education:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="education"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'education'} options={[]} />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Primary language:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="education"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'education'} options={[]} />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Speak english:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="education"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'education'} options={[]} />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    State HIV case ID:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="hiv"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="hiv"
                                id="hiv"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" outline onClick={setGeneralForm}>
                        Cancel
                    </Button>
                    <Button
                        onClick={handleSubmit(onSubmit)}
                        type="submit"
                        className="padding-105 text-center margin-top-0">
                        Save
                    </Button>
                </ButtonGroup>
            </div>
        </>
    );
};
