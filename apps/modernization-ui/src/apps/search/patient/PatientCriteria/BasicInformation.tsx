import { Controller, useFormContext } from 'react-hook-form';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { SingleSelect } from 'design-system/select';
import { Toggle } from 'design-system/toggle/Toggle';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry, statusOptions } from 'apps/search/patient/criteria';
import { validNameRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';
import { genders } from 'options/gender';
import { OperatorInput } from 'design-system/input/operator';
import { DateEntryCriteria } from 'design-system/date/criteria/DateEntryCriteria';
import { validateDateEntry } from 'design-system/date/validateDateEntry';
import { DateEqualsCriteria } from 'design-system/date/entry';

export const BasicInformation = () => {
    const { control, setError, clearErrors } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    const handleDateValidation = (value: DateEqualsCriteria) => {
        const validationResult = validateDateEntry('Date of birth')(value);
        if (typeof validationResult === 'string') {
            setError('bornOn', { type: 'manual', message: validationResult });
        } else {
            clearErrors('bornOn');
        }
    };

    return (
        <SearchCriteria>
            <Controller
                control={control}
                name="lastName"
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
                name="firstName"
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
                name="bornOn"
                rules={{ validate: (value) => validateDateEntry('Date of birth')(value as DateEqualsCriteria) }}
                render={({ field: { onChange, value }, fieldState: { error } }) => {
                    return (
                        <DateEntryCriteria
                            label="Date of birth"
                            onChange={onChange}
                            onBlur={(e) => handleDateValidation(e as DateEqualsCriteria)}
                            value={value}
                            sizing="compact"
                            id={'bornOn'}
                            error={error?.message}
                        />
                    );
                }}
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
