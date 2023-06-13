import { Controller, useForm } from 'react-hook-form';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { SelectInput } from '../FormInputs/SelectInput';
import { BaseSyntheticEvent, useState } from 'react';
import { usePatientEthnicityCodedValues } from 'pages/patient/profile/ethnicity';

export const EthnicityForm = ({ setEthnicityForm }: any) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const coded = usePatientEthnicityCodedValues();

    const onSubmit = () => {
        setEthnicityForm();
    };

    const [selectedEthinicity, setSelectedEthinicity] = useState<'2135-2' | '2186-5' | 'UNK' | 'NOT-TO-ANS' | ''>('');

    const handleOnChange = (fn?: (...event: any[]) => void) => (changed: BaseSyntheticEvent) => {
        setSelectedEthinicity(changed?.currentTarget?.value);
        fn && fn(changed);
    };

    return (
        <>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1 label-text">
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
                    Ethnicity:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="ethnicity"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                dataTestid="ethnicity"
                                name="ethnicity"
                                defaultValue={value}
                                onChange={handleOnChange(onChange)}
                                htmlFor={'ethnicity'}
                                options={coded.ethnicGroups}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            {selectedEthinicity === '2135-2' && (
                <Grid row className="flex-justify flex-align-center padding-2">
                    <Grid col={6} className="margin-top-1">
                        Spanish origin:
                    </Grid>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="spanish"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'spanish'}
                                    options={coded.detailedEthnicities}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            )}
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Reason unknown:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="reason"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'reason'}
                                options={coded.ethnicityUnknownReasons}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button
                        type="button"
                        className="margin-top-0"
                        data-testid="cancel-btn"
                        outline
                        onClick={setEthnicityForm}>
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
