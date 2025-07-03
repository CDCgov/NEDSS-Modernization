import { useMemo } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { SingleSelect } from 'design-system/select';
import { Input } from 'components/FormInputs/Input';
import { displayAgeAsOfToday, displayAgeAsOf } from 'date';
import { maxLengthRule } from 'validation/entry';
import { EntryFieldsProps } from 'design-system/entry';
import { ValueView } from 'design-system/data-display/ValueView';
import { BasicPersonalDetailsEntry } from '../entry';
import { Indicator, indicators } from 'coded';
import { useSexBirthCodedValues } from 'apps/patient/data/sexAndBirth/useSexBirthCodedValues';
import { useGeneralCodedValues } from 'apps/patient/data/general/useGeneralCodedValues';
import { Permitted } from 'libs/permission';

const BORN_ON_LABEL = 'Date of birth';
const DECEASED_ON_LABEL = 'Date of death';
const STATE_HIV_CASE_LABEL = 'State HIV case ID';
const ENTRY_FIELD_PLACEHOLDER = '';

export const BasicPersonalDetailsFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const { control, formState, getFieldState } = useFormContext<{ personalDetails: BasicPersonalDetailsEntry }>();
    const currentBirthday = useWatch({ control, name: 'personalDetails.bornOn' });
    const deceasedOn = useWatch({ control, name: 'personalDetails.deceasedOn' });
    const selectedDeceased = useWatch({ control, name: 'personalDetails.deceased' });
    const age = useMemo(
        () => (deceasedOn ? displayAgeAsOf(currentBirthday, deceasedOn) : displayAgeAsOfToday(currentBirthday)),
        [currentBirthday, deceasedOn]
    );

    const { invalid: bornOnInvalid } = getFieldState('personalDetails.bornOn', formState);

    return (
        <section>
            <Controller
                control={control}
                name="personalDetails.bornOn"
                rules={validDateRule(BORN_ON_LABEL)}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label={BORN_ON_LABEL}
                        orientation={orientation}
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        error={error?.message}
                    />
                )}
            />
            <ValueView title="Current age" value={!bornOnInvalid ? age : null} sizing={sizing} />
            <Controller
                control={control}
                name="personalDetails.currentSex"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Current sex"
                        orientation={orientation}
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={useSexBirthCodedValues().genders}
                    />
                )}
            />
            <Controller
                control={control}
                name="personalDetails.birthSex"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Birth sex"
                        orientation={orientation}
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={useSexBirthCodedValues().genders}
                    />
                )}
            />
            <Controller
                control={control}
                name="personalDetails.deceased"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Is the patient deceased?"
                        orientation={orientation}
                        sizing={sizing}
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
                    name="personalDetails.deceasedOn"
                    shouldUnregister
                    rules={validDateRule(DECEASED_ON_LABEL)}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <DatePickerInput
                            id={name}
                            label={DECEASED_ON_LABEL}
                            orientation={orientation}
                            sizing={sizing}
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            error={error?.message}
                        />
                    )}
                />
            )}
            <Controller
                control={control}
                name="personalDetails.maritalStatus"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Marital status"
                        orientation={orientation}
                        sizing={sizing}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        options={useGeneralCodedValues().maritalStatuses}
                    />
                )}
            />
            <Permitted permission="HIVQUESTIONS-GLOBAL">
                <Controller
                    control={control}
                    name="personalDetails.stateHIVCase"
                    rules={maxLengthRule(16, STATE_HIV_CASE_LABEL)}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <Input
                            label={STATE_HIV_CASE_LABEL}
                            orientation={orientation}
                            sizing={sizing}
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
                        />
                    )}
                />
            </Permitted>
        </section>
    );
};
