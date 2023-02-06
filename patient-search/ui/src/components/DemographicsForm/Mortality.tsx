import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, useForm } from 'react-hook-form';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Input } from '../FormInputs/Input';
import { SelectInput } from '../FormInputs/SelectInput';
import { stateList } from '../../constant/states';

export const MortalityForm = ({ setMortalityForm }: any) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = (data: any) => {
        console.log(data);
        setMortalityForm();
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
                    Is the patient deceased:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="deceased"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="deceased"
                                id="deceased"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Date of death:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="dod"
                        render={({ field: { onChange, value } }) => (
                            <DatePickerInput defaultValue={value} onChange={onChange} name="dod" htmlFor={'dod'} />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    City of death:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="city"
                        render={({ field: { onChange, value } }) => (
                            <Input onChange={onChange} type="text" defaultValue={value} htmlFor="city" id="city" />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    State of death:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="state"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'state'}
                                options={stateList}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    County of death:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="county"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'county'}
                                options={stateList}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Country of death:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="country"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'country'}
                                options={stateList}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" outline onClick={setMortalityForm}>
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
