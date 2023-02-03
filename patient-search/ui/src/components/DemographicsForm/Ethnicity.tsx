import { Controller, useForm } from 'react-hook-form';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { SelectInput } from '../FormInputs/SelectInput';
import { Ethnicity } from '../../generated/graphql/schema';
import { formatInterfaceString } from '../../utils/util';

export const EthnicityForm = ({ setEthnicityForm }: any) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = (data: any) => {
        console.log(data);
        setEthnicityForm();
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
                    Race:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="ethnicity"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'ethnicity'}
                                options={Object.values(Ethnicity).map((ethnicity) => {
                                    return {
                                        name: formatInterfaceString(ethnicity),
                                        value: ethnicity
                                    };
                                })}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Spanish origin:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="spanish"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'spanish'} options={[]} />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Reason unknowm:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="reason"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'reason'} options={[]} />
                        )}
                    />
                </Grid>
            </Grid>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" outline onClick={setEthnicityForm}>
                        Go back
                    </Button>
                    <Button
                        onClick={handleSubmit(onSubmit)}
                        type="submit"
                        className="padding-105 text-center margin-top-0">
                        Add
                    </Button>
                </ButtonGroup>
            </div>
        </>
    );
};
