import { Controller, useFormContext } from 'react-hook-form';
import { GeneralInformationEntry } from '../entry';
import { NumericInput } from 'design-system/input';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { usePatientProfilePermissions } from 'apps/patient/profile/permission';
import { useGeneralCodedValues } from 'apps/patient/profile/generalInfo';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';

const AS_OF_DATE_LABEL = 'General information as of';
const MATERNAL_MAIDEN_NAME_LABEL = "Mother's maiden name";
const STATE_HIV_CASE_LABEL = 'State HIV case ID';
const ENTRY_FIELD_PLACEHOLDER = '';

export const GeneralInformationEntryFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const { control } = useFormContext<{ general: GeneralInformationEntry }>();
    const { hivAccess } = usePatientProfilePermissions();
    const coded = useGeneralCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="general.asOf"
                rules={{ ...validateRequiredRule(AS_OF_DATE_LABEL), ...validDateRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label={AS_OF_DATE_LABEL}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.maritalStatus"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Marital status"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.maritalStatuses}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.maternalMaidenName"
                rules={maxLengthRule(50, MATERNAL_MAIDEN_NAME_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label={MATERNAL_MAIDEN_NAME_LABEL}
                        orientation={orientation}
                        placeholder={ENTRY_FIELD_PLACEHOLDER}
                        onBlur={onBlur}
                        onChange={onChange}
                        type="text"
                        defaultValue={value}
                        id={name}
                        name={name}
                        htmlFor={name}
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.adultsInResidence"
                rules={{ min: { value: 0, message: 'Must be greater than 0' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <NumericInput
                        label="Number of adults in residence"
                        placeholder={ENTRY_FIELD_PLACEHOLDER}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        id={name}
                        name={name}
                        min="0"
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.childrenInResidence"
                rules={{ min: { value: 0, message: 'Must be greater than 0' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <NumericInput
                        label="Number of children in residence"
                        placeholder={ENTRY_FIELD_PLACEHOLDER}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        id={name}
                        name={name}
                        min="0"
                        error={error?.message}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.primaryOccupation"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Primary occupation"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        options={coded.primaryOccupations}
                        id={name}
                        name={name}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.educationLevel"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Highest level of education"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.educationLevels}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.primaryLanguage"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Primary language"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.primaryLanguages}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.speaksEnglish"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Speaks English"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.speaksEnglish}
                        sizing={sizing}
                    />
                )}
            />
            {hivAccess && (
                <Controller
                    control={control}
                    name="general.stateHIVCase"
                    rules={maxLengthRule(16, STATE_HIV_CASE_LABEL)}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <Input
                            label={STATE_HIV_CASE_LABEL}
                            orientation={orientation}
                            placeholder={ENTRY_FIELD_PLACEHOLDER}
                            onBlur={onBlur}
                            onChange={onChange}
                            maxLength={16}
                            type="text"
                            defaultValue={value}
                            htmlFor={name}
                            id={name}
                            name={name}
                            error={error?.message}
                            sizing={sizing}
                        />
                    )}
                />
            )}
        </section>
    );
};
