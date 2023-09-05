import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { useCountyCodedValues } from 'location';
import { usePatientSexBirthCodedValues } from 'pages/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { maybeNumber, orNull } from 'utils';
import { calculateAge, externalizeDate, externalizeDateTime } from 'date';
import { useMemo } from 'react';

const UNKNOWN_GENDER = 'U';

type BirthEntry = {
    bornOn: string | null;
    gender: string | null;
    multipleBirth: string | null;
    birthOrder: number | null;
    city: string | null;
    state: string | null;
    county: string | null;
    country: string | null;
};

type GenderEntry = {
    current: string | null;
    unknownReason: string | null;
    preferred: string | null;
    additional: string | null;
};

export type BirthAndGenderEntry = {
    asOf: string | null;
    birth: BirthEntry;
    gender: GenderEntry;
};

type Props = {
    entry: BirthAndGenderEntry;
    onChanged: (updated: BirthAndGenderEntry) => void;
    onCancel: () => void;
};

export const SexBirthForm = ({ entry, onChanged, onCancel }: Props) => {
    const {
        handleSubmit,
        control,
        formState: { isValid }
    } = useForm({ mode: 'onBlur' });

    const currentBirthday = useWatch({ control, name: 'bornOn', defaultValue: entry.birth.bornOn });

    const age = useMemo(() => calculateAge(currentBirthday), [currentBirthday]);

    const selectedCurrentGender = useWatch({ control, name: 'currentGender', defaultValue: entry.gender.current });

    const selectedState = useWatch({ control, name: 'state', defaultValue: entry.birth.state });

    const coded = usePatientSexBirthCodedValues();

    const byState = useCountyCodedValues(selectedState);

    const onSubmit = (entered: FieldValues) => {
        onChanged({
            asOf: externalizeDateTime(entered.asOf),
            birth: {
                bornOn: externalizeDate(entered.bornOn),
                gender: orNull(entered.birthGender),
                multipleBirth: orNull(entered.multipleBirth),
                birthOrder: maybeNumber(entered.birthOrder),
                city: entered.city,
                state: orNull(entered.state),
                county: orNull(entered.county),
                country: orNull(entered.country)
            },
            gender: {
                current: orNull(entered.currentGender),
                unknownReason: orNull(entered.unknownGenderReason),
                preferred: orNull(entered.preferredGender),
                additional: entered.additionalGender
            }
        });
    };

    return (
        <>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1 required">
                    As of:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="asOf"
                        defaultValue={entry.asOf}
                        rules={{ required: { value: true, message: 'As of date is required.' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                name="asOf"
                                htmlFor={'asOf'}
                                disableFutureDates
                                errorMessage={error?.message}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Date of birth:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="bornOn"
                        defaultValue={entry.birth.bornOn}
                        render={({ field: { onChange, value } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                onChange={onChange}
                                name="bornOn"
                                disableFutureDates
                                htmlFor={'bornOn'}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Current age:
                </Grid>
                <Grid col={6}>{age}</Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Current sex:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="currentGender"
                        defaultValue={entry.gender.current}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="currentGender"
                                htmlFor={'currentGender'}
                                options={coded.genders}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Unknown reason:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="unknownGenderReason"
                        defaultValue={entry.gender.unknownReason}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                disabled={selectedCurrentGender !== UNKNOWN_GENDER}
                                defaultValue={value}
                                onChange={onChange}
                                name="unknownGenderReason"
                                htmlFor={'unknownGenderReason'}
                                options={coded.genderUnknownReasons}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Transgender information:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry.gender.preferred}
                        name="preferredGender"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="preferredGender"
                                htmlFor={'preferredGender'}
                                options={coded.preferredGenders}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Additional gender:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry.gender.additional}
                        name="additionalGender"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No Data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="additionalGender"
                                id="additionalGender"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Birth sex:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry.birth.gender}
                        name="birthGender"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="birthGender"
                                htmlFor={'birthGender'}
                                options={coded.genders}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Multiple birth:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="multipleBirth"
                        defaultValue={entry.birth.multipleBirth}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="multipleBirth"
                                htmlFor={'multipleBirth'}
                                options={coded.multipleBirth}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Birth order:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry.birth.birthOrder}
                        name="birthOrder"
                        rules={{ min: { value: 0, message: 'Must be a positive number' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <Input
                                placeholder="No Data"
                                onChange={onChange}
                                onBlur={onBlur}
                                type="number"
                                defaultValue={value}
                                htmlFor="birthOrder"
                                id="birthOrder"
                                error={error?.message}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Birth city:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="city"
                        defaultValue={entry.birth.city}
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No Data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="city"
                                id="city"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Birth state:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry.birth.state}
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
                <Grid col={6} className="margin-top-1">
                    Birth county:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="county"
                        defaultValue={entry.birth.county}
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
                    Birth country:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry.birth.country}
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
                        className="padding-105 text-center margin-top-0"
                        disabled={!isValid}>
                        Save
                    </Button>
                </ButtonGroup>
            </div>
        </>
    );
};
