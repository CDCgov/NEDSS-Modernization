import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { validNameRule } from 'validation/entry';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { RecordStatus } from 'generated/graphql/schema';
import styles from './basic-information.module.scss';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { Selectable } from 'options';
import { PatientCriteriaEntry } from '../criteria';
import { SingleSelect } from 'design-system/select';

export const BasicInformation = () => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    const genderOptions: Selectable[] = [
        { name: 'Male', label: 'Male', value: 'M' },
        { name: 'Female', label: 'Female', value: 'F' },
        { name: 'Other', label: 'Other', value: 'U' }
    ];

    const statusOptions: Selectable[] = [
        {
            name: 'Active',
            label: 'Active',
            value: RecordStatus.Active
        },
        {
            name: 'Deleted',
            label: 'Deleted',
            value: RecordStatus.LogDel
        },
        {
            name: 'Superceded',
            label: 'Superceded',
            value: RecordStatus.Superceded
        }
    ];

    return (
        <div className={styles.basic}>
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
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="dateOfBirth"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <DatePickerInput
                        onBlur={onBlur}
                        defaultValue={value}
                        onChange={onChange}
                        name={name}
                        disableFutureDates
                        label="Date of birth"
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
                        label="Sex"
                        id={name}
                        options={genderOptions}
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
                    />
                )}
            />
            <Controller
                control={control}
                name="status"
                rules={{
                    validate: (value) => {
                        if (!value || value.length === 0) return 'At least one status is required';
                        return true; // No error
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <CheckboxGroup
                        name={name}
                        className={styles.statusCheckboxes}
                        label={'Status'}
                        options={statusOptions}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                    />
                )}
            />
        </div>
    );
};
