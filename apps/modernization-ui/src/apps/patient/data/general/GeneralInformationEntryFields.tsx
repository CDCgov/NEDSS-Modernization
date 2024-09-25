import { GeneralInformationEntry } from '../entry';
import { Controller, useFormContext } from 'react-hook-form';
import { usePatientProfilePermissions } from 'apps/patient/profile/permission';
import { usePatientGeneralCodedValues } from 'apps/patient/profile/generalInfo';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SingleSelect } from 'design-system/select';
import { maxLengthRule } from 'validation/entry';
import { Input } from 'components/FormInputs/Input';

export const GeneralInformationEntryFields = () => {
    const { control } = useFormContext<{ general: GeneralInformationEntry }>();
    const { hivAccess } = usePatientProfilePermissions();
    const coded = usePatientGeneralCodedValues();

    return (
        <section>
            <Controller
                control={control}
                name="general.asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="General information as of"
                        orientation="horizontal"
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
            <Controller
                control={control}
                name="general.maritalStatus"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Marital status"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.maritalStatuses}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.maternalMaidenName"
                rules={maxLengthRule(50)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Mother's maiden name"
                        orientation="horizontal"
                        placeholder="No Data"
                        onBlur={onBlur}
                        onChange={onChange}
                        type="text"
                        defaultValue={value}
                        id={name}
                        name={name}
                        htmlFor={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.adultsInResidence"
                rules={{ min: { value: 0, message: 'Must be greater than 0' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Number of adults in residence"
                        orientation="horizontal"
                        placeholder="No Data"
                        onBlur={onBlur}
                        onChange={onChange}
                        type="number"
                        defaultValue={value?.toString() ?? ''}
                        id={name}
                        name={name}
                        htmlFor={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.childrenInResidence"
                rules={{ min: { value: 0, message: 'Must be greater than 0' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Number of children in residence"
                        orientation="horizontal"
                        placeholder="No Data"
                        onBlur={onBlur}
                        onChange={onChange}
                        type="number"
                        defaultValue={value?.toString() ?? ''}
                        id={name}
                        name={name}
                        htmlFor={name}
                        error={error?.message}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.primaryOccupation"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Primary occupation"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        options={coded.primaryOccupations}
                        id={name}
                        name={name}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.educationLevel"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Highest level of education"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.educationLevels}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.primaryLanguage"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Primary language"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.primaryLanguages}
                    />
                )}
            />
            <Controller
                control={control}
                name="general.speaksEnglish"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Speaks English"
                        orientation="horizontal"
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={coded.speaksEnglish}
                    />
                )}
            />
            {hivAccess && (
                <Controller
                    control={control}
                    name="general.stateHIVCase"
                    rules={maxLengthRule(20)}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <Input
                            label="State HIV case ID"
                            orientation="horizontal"
                            placeholder="No Data"
                            onBlur={onBlur}
                            onChange={onChange}
                            type="text"
                            defaultValue={value}
                            htmlFor={name}
                            id={name}
                            name={name}
                            error={error?.message}
                        />
                    )}
                />
            )}
        </section>
    );
};
