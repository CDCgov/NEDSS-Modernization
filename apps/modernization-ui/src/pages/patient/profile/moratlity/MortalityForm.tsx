import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { useCountyCodedValues } from 'location';
import { Deceased, InputMaybe, Scalars } from 'generated/graphql/schema';
import { usePatientSexBirthCodedValues } from '../sexBirth/usePatientSexBirthCodedValues';

type Props = {
    entry?: MortalityEntry | null;
    onChanged?: (updated: MortalityEntry) => void;
    onCancel?: () => void;
};

export type MortalityEntry = {
    asOf?: InputMaybe<Scalars['DateTime']>;
    deceased?: string | null;
    deceasedTime?: string | null;
    cityOfDeath?: string | null;
    stateOfDeath?: string | null;
    countyOfDeath?: string | null;
    countryOfDeath?: string | null;
};

export const MortalityForm = ({ entry, onChanged = () => {}, onCancel = () => {} }: Props) => {
    const { handleSubmit, control } = useForm();

    const selectedState = useWatch({ control, name: 'state' });

    const coded = usePatientSexBirthCodedValues();

    const byState = useCountyCodedValues(selectedState);

    const onSubmit = (entered: FieldValues) => {
        onChanged({
            asOf: entered?.asOf,
            deceased: entered?.deceased,
            deceasedTime: entered?.dod,
            cityOfDeath: entered?.city,
            stateOfDeath: entered?.state,
            countyOfDeath: entered?.county,
            countryOfDeath: entered?.country
        });
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
                        name="asOf"
                        defaultValue={entry?.asOf}
                        render={({ field: { onChange, value } }) => (
                            <DatePickerInput defaultValue={value} onChange={onChange} name="asOf" htmlFor={'asOf'} />
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
                        defaultValue={entry?.deceased}
                        name="deceased"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'deceased'}
                                options={Object.values(Deceased).map((deceased) => {
                                    return {
                                        name:
                                            deceased === Deceased.N
                                                ? 'No'
                                                : deceased === Deceased.Y
                                                ? 'Yes'
                                                : 'Unknown',
                                        value: deceased || ''
                                    };
                                })}
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
                        defaultValue={entry?.deceasedTime}
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
                        defaultValue={entry?.cityOfDeath}
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
                        defaultValue={entry?.stateOfDeath}
                        name="state"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'state'}
                                options={coded.states}
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
                        defaultValue={entry?.countyOfDeath}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'county'}
                                options={byState.counties}
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
                        defaultValue={entry?.countryOfDeath}
                        name="country"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'country'}
                                options={coded.countries}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" outline onClick={onCancel}>
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
