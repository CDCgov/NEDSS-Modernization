import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, useForm } from 'react-hook-form';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Input } from '../FormInputs/Input';
import { SelectInput } from '../FormInputs/SelectInput';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { usePatientSexBirthCodedValues } from 'pages/patient/profile/sexBirth/usePatientSexBirthCodedValues';

export const SexBirthForm = ({ setSexBirthForm }: any) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const coded = usePatientSexBirthCodedValues();

    const onSubmit = () => {
        setSexBirthForm();
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
                    Date of birth:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="dob"
                        render={({ field: { onChange, value } }) => (
                            <DatePickerInput defaultValue={value} onChange={onChange} name="dob" htmlFor={'dob'} />
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
                        name="gender"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="gender"
                                htmlFor={'gender'}
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
                        name="gender"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="gender"
                                htmlFor={'gender'}
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
                        name="mBirth"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                defaultValue={value}
                                onChange={onChange}
                                name="mBirth"
                                htmlFor={'mBirth'}
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
                        name="bOrder"
                        render={({ field: { onChange, value } }) => (
                            <Input
                                placeholder="No data"
                                onChange={onChange}
                                type="text"
                                defaultValue={value}
                                htmlFor="bOrder"
                                id="bOrder"
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
                    <SearchCriteriaContext.Consumer>
                        {({ searchCriteria }) => (
                            <Controller
                                control={control}
                                name="bState"
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        defaultValue={value}
                                        onChange={onChange}
                                        htmlFor={'bState'}
                                        options={searchCriteria.states.map((state) => {
                                            return {
                                                value: state?.id!,
                                                name: state?.codeDescTxt!
                                            };
                                        })}
                                    />
                                )}
                            />
                        )}
                    </SearchCriteriaContext.Consumer>
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
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'bCounty'} options={[]} />
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
                        name="bCountry"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'bCountry'} options={[]} />
                        )}
                    />
                </Grid>
            </Grid>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" outline onClick={setSexBirthForm}>
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
