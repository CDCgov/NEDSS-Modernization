import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { orNull, maybeNumber } from 'utils';
import { maxLengthRule } from 'validation/entry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Input } from 'components/FormInputs/Input';
import { usePatientProfilePermissions } from 'apps/patient/profile/permission';
import { usePatientGeneralCodedValues } from 'apps/patient/profile/generalInfo/usePatientGeneralCodedValues';

type Props = {
    entry?: GeneralInformationEntry | null;
    onChanged?: (updated: GeneralInformationEntry) => void;
    onCancel?: () => void;
};

export type GeneralInformationEntry = {
    asOf: string | null;
    maritalStatus: string | null;
    maternalMaidenName: string | null;
    adultsInHouse: number | null;
    childrenInHouse: number | null;
    occupation: string | null;
    educationLevel: string | null;
    primaryLanguage: string | null;
    speaksEnglish: string | null;
    stateHIVCase: string | null;
};

export const GeneralPatientInformationForm = ({ entry, onChanged = () => {}, onCancel = () => {} }: Props) => {
    const { hivAccess } = usePatientProfilePermissions();

    const {
        handleSubmit,
        control,
        formState: { isValid }
    } = useForm({ mode: 'onBlur' });

    const onSubmit = (entered: FieldValues) => {
        onChanged({
            asOf: entered?.asOf,
            maritalStatus: orNull(entered?.maritalStatus),
            maternalMaidenName: entered?.maternalMaidenName,
            adultsInHouse: maybeNumber(entered?.adultsInHouse),
            childrenInHouse: maybeNumber(entered?.childrenInHouse),
            occupation: orNull(entered?.occupation),
            educationLevel: orNull(entered?.educationLevel),
            primaryLanguage: orNull(entered?.primaryLanguage),
            speaksEnglish: orNull(entered?.speaksEnglish),
            stateHIVCase: entered?.stateHIVCase
        });
    };

    const coded = usePatientGeneralCodedValues();

    return (
        <>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold required">
                    As of:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="asOf"
                        defaultValue={entry?.asOf}
                        rules={{ required: { value: true, message: 'As of date is required.' } }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                name={name}
                                disableFutureDates
                                errorMessage={error?.message}
                                required
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold">
                    Marital status:
                </Grid>
                <Grid col={6}>
                    <Controller
                        defaultValue={entry?.maritalStatus}
                        control={control}
                        name="maritalStatus"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'maritalStatus'}
                                options={coded.maritalStatuses}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold">
                    Mother's maiden name:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="maternalMaidenName"
                        rules={maxLengthRule(50)}
                        defaultValue={entry?.maternalMaidenName}
                        render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                            <Input
                                placeholder="No Data"
                                onBlur={onBlur}
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="maternalMaidenName"
                                id="maternalMaidenName"
                                error={error?.message}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold">
                    Number of adults in residence:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="adultsInHouse"
                        defaultValue={entry?.adultsInHouse}
                        rules={{ min: { value: 0, message: 'Must be greater than 0' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <Input
                                placeholder="No Data"
                                onBlur={onBlur}
                                onChange={onChange}
                                type="number"
                                defaultValue={value}
                                htmlFor="adultsInHouse"
                                id="adultsInHouse"
                                error={error?.message}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold">
                    Number of children in residence:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry?.childrenInHouse}
                        name="childrenInHouse"
                        rules={{ min: { value: 0, message: 'Must be greater than 0' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <Input
                                placeholder="No Data"
                                onBlur={onBlur}
                                onChange={onChange}
                                type="number"
                                defaultValue={value}
                                htmlFor="childrenInHouse"
                                id="childrenInHouse"
                                error={error?.message}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold">
                    Primary occupation:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="occupation"
                        defaultValue={entry?.occupation}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'occupation'}
                                options={coded.primaryOccupations}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold">
                    Highest level of education:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="educationLevel"
                        defaultValue={entry?.educationLevel}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'educationLevel'}
                                options={coded.educationLevels}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold">
                    Primary language:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        defaultValue={entry?.primaryLanguage}
                        name="primaryLanguage"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'primaryLanguage'}
                                options={coded.primaryLanguages}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                <Grid col={6} className="margin-top-1 text-bold">
                    Speak english:
                </Grid>
                <Grid col={6}>
                    <Controller
                        defaultValue={entry?.speaksEnglish}
                        control={control}
                        name="speaksEnglish"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'speaksEnglish'}
                                options={coded.speaksEnglish}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            {hivAccess && (
                <Grid row className="flex-justify flex-align-center padding-2 border-bottom border-base-lighter">
                    <Grid col={6} className="margin-top-1 text-bold">
                        State HIV case ID:
                    </Grid>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="stateHIVCase"
                            rules={maxLengthRule(20)}
                            defaultValue={entry?.stateHIVCase}
                            render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                <Input
                                    placeholder="No Data"
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    type="text"
                                    defaultValue={value}
                                    htmlFor="stateHIVCase"
                                    id="stateHIVCase"
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
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
