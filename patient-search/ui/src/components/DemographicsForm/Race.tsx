import { Controller, useForm } from 'react-hook-form';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { SelectInput } from '../FormInputs/SelectInput';
import { Race } from '../../generated/graphql/schema';
import { formatInterfaceString } from '../../utils/util';
import { Input } from '../FormInputs/Input';

export const RaceForm = ({ setRaceForm }: any) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = (data: any) => {
        console.log(data);
        setRaceForm();
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
                        name="race"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'race'}
                                options={Object.values(Race).map((race) => {
                                    return {
                                        name: formatInterfaceString(race),
                                        value: race
                                    };
                                })}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Detailed race:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="detailedRace"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="detailedRace"
                                id="detailedRace"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" outline onClick={setRaceForm}>
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
