import { useMemo } from 'react';
import { Controller, useFormContext, useFormState, useWatch } from 'react-hook-form';
import { usePatientSexBirthCodedValues } from 'apps/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { Input } from 'components/FormInputs/Input';
import { displayAgeAsOfToday } from 'date/displayAge';
import { maxLengthRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { ValueView } from 'design-system/data-display/ValueView';
import { BasicPersonalDetailsEntry } from '../entry';
import { usePatientProfilePermissions } from 'apps/patient/profile/permission';
import { Indicator, indicators } from 'coded';
import { usePatientGeneralCodedValues } from 'apps/patient/profile/generalInfo';

const BORN_ON_LABEL = 'Date of birth';
const DECEASED_ON_LABEL = 'Date of death';
const STATE_HIV_CASE_LABEL = 'State HIV case ID';
const ENTRY_FIELD_PLACEHOLDER = '';

type BasicPersonalDetailsProps = EntryFieldsProps;

export const BasicPersonalDetailsFields = ({ orientation = 'horizontal' }: BasicPersonalDetailsProps) => {
    const { control } = useFormContext<BasicPersonalDetailsEntry>();
    const currentBirthday = useWatch({ control, name: 'bornOn' });
    const selectedDeceased = useWatch({ control, name: 'deceased' });
    const age = useMemo(() => displayAgeAsOfToday(currentBirthday), [currentBirthday]);
    const sexBirthValues = usePatientSexBirthCodedValues();
    const generalValues = usePatientGeneralCodedValues();
    const { hivAccess } = usePatientProfilePermissions();
    const { isValid: bornOnValid } = useFormState({ control, name: 'bornOn' });

    return (
        <section>
            <Controller
                control={control}
                name="bornOn"
                rules={validDateRule(BORN_ON_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label={BORN_ON_LABEL}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
            <ValueView title="Current age" value={bornOnValid ? age : null} />
            <Controller
                control={control}
                name="currentSex"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Current sex"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={sexBirthValues.genders}
                    />
                )}
            />
            <Controller
                control={control}
                name="birthSex"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Birth sex"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={sexBirthValues.genders}
                    />
                )}
            />
            <Controller
                control={control}
                name="deceased"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Is the patient deceased?"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={indicators}
                    />
                )}
            />
            {selectedDeceased?.value === Indicator.Yes && (
                <Controller
                    control={control}
                    name="deceasedOn"
                    shouldUnregister
                    rules={validDateRule(DECEASED_ON_LABEL)}
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <DatePickerInput
                            id={name}
                            label={DECEASED_ON_LABEL}
                            orientation={orientation}
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                        />
                    )}
                />
            )}
            <Controller
                control={control}
                name="maritalStatus"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Marital status"
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={generalValues.maritalStatuses}
                    />
                )}
            />
            {hivAccess && (
                <Controller
                    control={control}
                    name="stateHIVCase"
                    rules={maxLengthRule(20, STATE_HIV_CASE_LABEL)}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <Input
                            label={STATE_HIV_CASE_LABEL}
                            orientation={orientation}
                            placeholder={ENTRY_FIELD_PLACEHOLDER}
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