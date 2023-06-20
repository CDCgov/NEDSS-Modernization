import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { InputMaybe, Scalars } from 'generated/graphql/schema';
import { usePatientGeneralCodedValues } from 'pages/patient/profile/generalInfo/usePatientGeneralCodedValues';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Input } from 'components/FormInputs/Input';

type Props = {
    entry?: GeneralInformationEntry | null;
    onChanged?: (updated: GeneralInformationEntry) => void;
    onCancel?: () => void;
};

export type GeneralInformationEntry = {
    asOf?: InputMaybe<Scalars['DateTime']>;
    maritalStatus?: string | null;
    maternalMaidenName?: string | null;
    adultsInHouse?: string | null;
    childrenInHouse?: string | null;
    occupation?: string | null;
    educationLevel?: string | null;
    primaryLanguage?: string | null;
    speaksEnglish?: string | null;
    stateHIVCase?: string | null;
};

export const GeneralPatientInformationForm = ({ entry, onChanged = () => {}, onCancel = () => {} }: Props) => {
    const { handleSubmit, control } = useForm();

    const onSubmit = (entered: FieldValues) => {
        onChanged({
            asOf: entered?.nameAsOf,
            maritalStatus: entered?.maritalStatus,
            maternalMaidenName: entered?.motherName,
            adultsInHouse: entered?.adults,
            childrenInHouse: entered?.children,
            occupation: entered?.occupation,
            educationLevel: entered?.education,
            primaryLanguage: entered?.prmLang,
            speaksEnglish: entered?.speakEng,
            stateHIVCase: entered?.hiv
        });
    };

    const coded = usePatientGeneralCodedValues();

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
                        defaultValue={entry?.asOf}
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
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Mother's maiden name:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="motherName"
                        defaultValue={entry?.maternalMaidenName}
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
                        defaultValue={entry?.adultsInHouse}
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
                        defaultValue={entry?.childrenInHouse}
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
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Highest level of education:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="education"
                        defaultValue={entry?.educationLevel}
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'education'}
                                options={coded.educationLevels}
                            />
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
                        defaultValue={entry?.primaryLanguage}
                        name="prmLang"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'prmLang'}
                                options={coded.primaryLanguages}
                            />
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
                        defaultValue={entry?.speaksEnglish}
                        control={control}
                        name="speakEng"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                htmlFor={'speakEng'}
                                options={coded.speaksEnglish}
                            />
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
                        defaultValue={entry?.stateHIVCase}
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
