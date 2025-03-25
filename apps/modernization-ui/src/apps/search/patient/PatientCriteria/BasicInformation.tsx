import { Controller, useFormContext } from 'react-hook-form';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { DateCriteriaField, validateDateCriteria } from 'design-system/date/criteria';
import { OperatorInput } from 'design-system/input/operator';
import { SingleSelect } from 'design-system/select';
import { EntryFieldsProps } from 'design-system/entry';
import { Input } from 'components/FormInputs/Input';
import { validNameRule } from 'validation/entry';
import { genders } from 'options/gender';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry, statusOptions } from 'apps/search/patient/criteria';
import { Permitted } from 'libs/permission';
import { onlyPatientIdKeys } from '../onlyPatientIdKeys';

export const BasicInformation = ({ sizing, orientation }: EntryFieldsProps) => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    return (
        <SearchCriteria>
            <Controller
                control={control}
                name="name.last"
                rules={validNameRule}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <OperatorInput
                        id={name}
                        value={value}
                        label="Last name"
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                        onChange={onChange}
                    />
                )}
            />
            <Controller
                control={control}
                name="name.first"
                rules={validNameRule}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <OperatorInput
                        id={name}
                        value={value}
                        label="First name"
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                        onChange={onChange}
                    />
                )}
            />
            <Controller
                control={control}
                name="bornOn"
                rules={{ validate: (value) => !value || validateDateCriteria('Date of birth')(value) }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <DateCriteriaField
                        id={name}
                        label="Date of birth"
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="gender"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        value={value}
                        onChange={onChange}
                        name={name}
                        label="Current sex"
                        id={name}
                        options={genders}
                        sizing={sizing}
                        orientation={orientation}
                    />
                )}
            />
            <Controller
                control={control}
                name="id"
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="Patient ID(s)"
                        helperText="Separate IDs by commas, semicolons, or spaces"
                        name={name}
                        htmlFor={name}
                        id={name}
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                        onKeyDown={onlyPatientIdKeys}
                    />
                )}
            />
            <Permitted permission="FINDINACTIVE-PATIENT">
                <Controller
                    control={control}
                    name="status"
                    rules={{
                        required: { value: true, message: 'At least one status is required' }
                    }}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <CheckboxGroup
                            name={name}
                            label={'Include records that are'}
                            sizing={sizing}
                            orientation={orientation}
                            required
                            options={statusOptions}
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            error={error?.message}
                        />
                    )}
                />
            </Permitted>
        </SearchCriteria>
    );
};
