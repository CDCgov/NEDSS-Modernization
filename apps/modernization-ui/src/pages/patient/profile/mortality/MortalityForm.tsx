import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { useCountyCodedValues, useLocationCodedValues } from 'location';
import { Indicator, indicators } from 'coded';
import { orNull } from 'utils';
import { maxLengthRule } from 'validation/entry';

type Props = {
    entry: MortalityEntry;
    onChanged: (updated: MortalityEntry) => void;
    onCancel: () => void;
};

export type MortalityEntry = {
    asOf: string | null;
    deceased: string | null;
    deceasedOn: string | null;
    city: string | null;
    state: string | null;
    county: string | null;
    country: string | null;
};

export const MortalityForm = ({ entry, onChanged, onCancel }: Props) => {
    const {
        handleSubmit,
        control,
        formState: { isValid }
    } = useForm();

    const selectedState = useWatch({ control, name: 'state', defaultValue: entry.state });
    const selectedDeceased = useWatch({ control, name: 'deceased', defaultValue: entry.deceased });

    const coded = useLocationCodedValues();

    const byState = useCountyCodedValues(selectedState);

    const onSubmit = (entered: FieldValues) => {
        onChanged({
            asOf: entered.asOf,
            deceased: entered.deceased,
            deceasedOn: entered.deceasedOn,
            city: entered.city,
            state: orNull(entered.state),
            county: orNull(entered.county),
            country: orNull(entered.country)
        });
    };

    return (
        <>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1 demographics-label required">
                    As of:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="asOf"
                        defaultValue={entry.asOf}
                        rules={{ required: { value: true, message: 'As of date is requried.' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                name="asOf"
                                disableFutureDates
                                htmlFor={'asOf'}
                                errorMessage={error?.message}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1 demographics-label">
                    Is the patient deceased:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry.deceased}
                        name="deceased"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'deceased'}
                                options={indicators}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            {selectedDeceased && selectedDeceased === Indicator.Yes && (
                <>
                    <Grid row className="flex-justify flex-align-center padding-2">
                        <Grid col={6} className="margin-top-1 demographics-label">
                            Date of death:
                        </Grid>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name="deceasedOn"
                                defaultValue={entry?.deceasedOn}
                                render={({ field: { onChange, value } }) => (
                                    <DatePickerInput
                                        defaultValue={value}
                                        onChange={onChange}
                                        name="deceasedOn"
                                        disableFutureDates
                                        htmlFor={'deceasedOn'}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row className="flex-justify flex-align-center padding-2">
                        <Grid col={6} className="margin-top-1 demographics-label">
                            City of death:
                        </Grid>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name="city"
                                defaultValue={entry.city}
                                rules={maxLengthRule(100)}
                                render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                    <Input
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        type="text"
                                        defaultValue={value}
                                        htmlFor="city"
                                        id="city"
                                        error={error?.message}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row className="flex-justify flex-align-center padding-2">
                        <Grid col={6} className="margin-top-1 demographics-label">
                            State of death:
                        </Grid>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                defaultValue={entry.state}
                                name="state"
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        defaultValue={value}
                                        onChange={onChange}
                                        htmlFor={'state'}
                                        options={coded.states.all}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row className="flex-justify flex-align-center padding-2">
                        <Grid col={6} className="margin-top-1 demographics-label">
                            County of death:
                        </Grid>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name="county"
                                defaultValue={entry.county}
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
                        <Grid col={6} className="margin-top-1 demographics-label">
                            Country of death:
                        </Grid>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                defaultValue={entry.country}
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
                </>
            )}
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" outline onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button
                        onClick={handleSubmit(onSubmit)}
                        type="submit"
                        className="padding-105 text-center margin-top-0"
                        disabled={!isValid}>
                        Save
                    </Button>
                </ButtonGroup>
            </div>
        </>
    );
};
