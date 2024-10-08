import { Controller, useFormContext } from 'react-hook-form';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { SingleSelect } from 'design-system/select';
import { Toggle } from 'design-system/toggle/Toggle';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry, statusOptions } from 'apps/search/patient/criteria';
import { validNameRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { genders } from 'options/gender';

export const BasicInformation = () => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    return (
        <SearchCriteria>
            <Controller
                control={control}
                name="lastName"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        onBlur={onBlur}
                        onChange={onChange}
                        type="text"
                        label="Last name"
                        name={name}
                        defaultValue={value}
                        htmlFor={name}
                        id={name}
                        sizing="compact"
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="firstName"
                rules={validNameRule}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="First name"
                        name={name}
                        htmlFor={name}
                        id={name}
                        sizing="compact"
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="includeSimilar"
                render={({ field: { onChange, value, name } }) => (
                    <Toggle
                        value={value}
                        label={'Include results that sound similar'}
                        name={name}
                        onChange={onChange}
                        sizing="compact"
                    />
                )}
            />
            <Controller
                control={control}
                name="dateOfBirth"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <DatePickerInput
                        name={name}
                        label="Date of birth"
                        defaultValue={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        sizing="compact"
                        disableFutureDates
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
                        sizing="compact"
                    />
                )}
            />
            <Controller
                control={control}
                name="id"
                render={({ field: { onChange, value, name } }) => (
                    <Input
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="Patient ID"
                        name={name}
                        htmlFor={name}
                        id={name}
                        sizing="compact"
                    />
                )}
            />
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
                        sizing="compact"
                        requried
                        options={statusOptions}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                    />
                )}
            />
        </SearchCriteria>
    );
};
