import { useCallback } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { Permitted } from 'libs/permission';
import { indicators } from 'options/indicator';
import { genders } from 'options/gender';
import { useConceptOptions } from 'options/concepts';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { ValueField } from 'design-system/field';
import { EntryFieldsProps } from 'design-system/entry';
import { TextInputField } from 'design-system/input';
import { SingleSelect } from 'design-system/select';
import { asOfAgeResolver } from 'date';
import { maxLengthRule } from 'validation/entry';

import { BasicPersonalDetailsEntry } from '../entry';

const BORN_ON_LABEL = 'Date of birth';
const DECEASED_ON_LABEL = 'Date of death';
const STATE_HIV_CASE_LABEL = 'State HIV case ID';

export const BasicPersonalDetailsFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const { control } = useFormContext<{ personalDetails: BasicPersonalDetailsEntry }>();
    const currentBirthday = useWatch({ control, name: 'personalDetails.bornOn' });
    const deceasedOn = useWatch({ control, name: 'personalDetails.deceasedOn' });
    const selectedDeceased = useWatch({ control, name: 'personalDetails.deceased' });
    const ageResolver = useCallback(asOfAgeResolver(deceasedOn), [deceasedOn]);
    const age = ageResolver(currentBirthday);

    const maritalStatuses = useConceptOptions('P_MARITAL', { lazy: false });

    return (
        <>
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
            <ValueField label="Current age" sizing={sizing}>
                {age}
            </ValueField>
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
                        options={genders.all}
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
                        options={genders.all}
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
                        options={indicators.all}
                    />
                )}
            />
            {selectedDeceased?.value === indicators.yes.value && (
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
                        options={maritalStatuses.options}
                    />
                )}
            />
            <Permitted permission="HIVQUESTIONS-GLOBAL">
                <Controller
                    control={control}
                    name="personalDetails.stateHIVCase"
                    rules={maxLengthRule(16, STATE_HIV_CASE_LABEL)}
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <TextInputField
                            label={STATE_HIV_CASE_LABEL}
                            orientation={orientation}
                            sizing={sizing}
                            onBlur={onBlur}
                            onChange={onChange}
                            maxLength={16}
                            value={value}
                            id={name}
                            name={name}
                            error={error?.message}
                        />
                    )}
                />
            </Permitted>
        </>
    );
};
