import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { useCountyCodedValues } from 'location';
import { usePatientSexBirthCodedValues } from 'pages/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { InputMaybe, Scalars } from 'generated/graphql/schema';

type Props = {
    entry?: SexAndEntry | null;
    onChanged?: (updated: SexAndEntry) => void;
    onCancel?: () => void;
};

export type SexAndEntry = {
    additionalGender?: InputMaybe<Scalars['String']>;
    asOf?: InputMaybe<Scalars['DateTime']>;
    birthCity?: InputMaybe<Scalars['String']>;
    birthCntry?: InputMaybe<Scalars['String']>;
    birthGender?: string | null;
    birthOrderNbr?: InputMaybe<Scalars['Int']>;
    birthState?: InputMaybe<Scalars['String']>;
    currentAge?: number | null;
    currentGender?: string | null;
    dateOfBirth?: InputMaybe<Scalars['Date']>;
    multipleBirth?: InputMaybe<Scalars['String']>;
    sexUnknown?: InputMaybe<Scalars['String']>;
    transGenderInfo?: InputMaybe<Scalars['String']>;
};

export const SexBirthForm = ({ entry, onChanged = () => {}, onCancel = () => {} }: Props) => {
    const { handleSubmit, control } = useForm();

    const selectedState = useWatch({ control, name: 'bState' });

    const coded = usePatientSexBirthCodedValues();

    const byState = useCountyCodedValues(selectedState);

    const onSubmit = (entered: FieldValues) => {
        onChanged({
            asOf: entered?.asOf,
            dateOfBirth: entered?.dateOfBirth,
            currentAge: entered?.age,
            currentGender: entered?.gender,
            sexUnknown: entered?.sexUnknown,
            transGenderInfo: entered?.transGenderInfo,
            additionalGender: entered?.addGender,
            birthGender: entered?.birthGender,
            multipleBirth: entered?.multipleBirth,
            birthOrderNbr: entered?.birthOrderNbr,
            birthCity: entered?.bCity,
            birthCntry: entered?.bCountry,
            birthState: entered?.bState
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
                    Date of birth:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="dateOfBirth"
                        defaultValue={entry?.dateOfBirth}
                        render={({ field: { onChange, value } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                onChange={onChange}
                                name="dateOfBirth"
                                htmlFor={'dateOfBirth'}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Current age:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="age"
                        defaultValue={entry?.currentAge}
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="age"
                                id="age"
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Current sex:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="gender"
                        defaultValue={entry?.currentGender}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="gender"
                                htmlFor={'gender'}
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
                        name="sexUnknown"
                        defaultValue={entry?.sexUnknown}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="sexUnknown"
                                htmlFor={'sexUnknown'}
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
                        defaultValue={entry?.transGenderInfo}
                        name="transGenderInfo"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="transGenderInfo"
                                htmlFor={'transGenderInfo'}
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
                        defaultValue={entry?.additionalGender}
                        name="addGender"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="addGender"
                                id="addGender"
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
                        defaultValue={entry?.birthGender}
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
                        defaultValue={entry?.multipleBirth}
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
                        defaultValue={entry?.birthOrderNbr}
                        name="birthOrderNbr"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="birthOrderNbr"
                                id="birthOrderNbr"
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
                        name="bCity"
                        defaultValue={entry?.birthCity}
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="bCity"
                                id="bCity"
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
                        defaultValue={entry?.birthState}
                        name="bState"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'bState'}
                                options={coded.states}
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
                        name="bCounty"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'bCounty'}
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
                        defaultValue={entry?.birthCntry}
                        name="bCountry"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'bCountry'}
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
