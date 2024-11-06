import { Controller, useFormContext } from 'react-hook-form';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { SingleSelect } from 'design-system/select';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry, statusOptions } from 'apps/search/patient/criteria';
import { validNameRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';

import { DatePickerInput } from 'design-system/date/picker';
import { genders } from 'options/gender';
import { OperatorInput } from 'design-system/input/operator';

export const BasicInformation = () => {
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
                        sizing="compact"
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
                        sizing="compact"
                        error={error?.message}
                        onChange={onChange}
                    />
                )}
            />
            <Controller
                control={control}
                name="dateOfBirth"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <DatePickerInput
                        id={name}
                        name={name}
                        label="Date of birth"
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        sizing="compact"
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
                        required
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
