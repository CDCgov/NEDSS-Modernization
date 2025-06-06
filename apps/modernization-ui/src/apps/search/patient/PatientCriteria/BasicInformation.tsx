import { Controller, useFormContext } from 'react-hook-form';
import { CheckboxGroup } from 'design-system/checkbox/CheckboxGroup';
import { DateCriteriaField, validateDateCriteria } from 'design-system/date/criteria';
import { TextCriteriaField } from 'design-system/input/text/criteria';
import { SingleSelect } from 'design-system/select';
import { EntryFieldsProps } from 'design-system/entry';
import { validNameRule } from 'validation/entry';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry, statusOptions } from 'apps/search/patient/criteria';
import { Permitted } from 'libs/permission';
import { searchableGenders } from './searchableGenders';
import { TextInputField } from 'design-system/input';
import { SkipLink } from 'SkipLink';

export const BasicInformation = ({ sizing, orientation }: EntryFieldsProps) => {
    const { control, clearErrors } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();

    return (
        <SearchCriteria sizing={sizing}>
            <SkipLink id="name.lastOperator" />
            <Controller
                control={control}
                name="name.last"
                rules={validNameRule}
                render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                    <TextCriteriaField
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
                    <TextCriteriaField
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
                        clearErrors={() => clearErrors('bornOn')}
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
                        options={searchableGenders}
                        sizing={sizing}
                        orientation={orientation}
                    />
                )}
            />
            <Controller
                control={control}
                name="id"
                rules={{
                    pattern: {
                        value: /^[0-9,; ]*$/,
                        message: 'Only numbers, spaces, commas, and semicolons are allowed'
                    }
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        type="text"
                        label="Patient ID(s)"
                        helperText="Separate IDs by commas, semicolons, or spaces"
                        name={name}
                        id={name}
                        sizing={sizing}
                        orientation={orientation}
                        error={error?.message}
                        aria-description={'Separate IDs by commas, semicolons, or spaces'}
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
