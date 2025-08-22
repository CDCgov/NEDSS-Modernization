import { Controller, UseFormReturn } from 'react-hook-form';
import { Permitted } from 'libs/permission';
import { isAllowed } from 'libs/sensitive';
import { NumericInput, TextInputField } from 'design-system/input';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { maxLengthRule, numericRangeRule, validateRequiredRule } from 'validation/entry';
import { HasGeneralInformationDemographic, labels } from '../general';
import { GeneralInformationOptions } from './useGeneralInformationOptions';

type GeneralInformationDemographicFieldsProps = {
    form: UseFormReturn<HasGeneralInformationDemographic>;
    options: GeneralInformationOptions;
} & EntryFieldsProps;

const GeneralInformationDemographicFields = ({
    orientation = 'horizontal',
    sizing,
    form,
    options
}: GeneralInformationDemographicFieldsProps) => (
    <>
        <Controller
            control={form.control}
            name="general.asOf"
            rules={{ ...validateRequiredRule(labels.asOf), ...validDateRule(labels.asOf) }}
            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                <DatePickerInput
                    id={name}
                    label={labels.asOf}
                    orientation={orientation}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    error={error?.message}
                    required
                    sizing={sizing}
                    aria-description="This field defaults to today's date and can be changed if needed."
                />
            )}
        />
        <Controller
            control={form.control}
            name="general.maritalStatus"
            render={({ field: { onChange, onBlur, value, name } }) => (
                <SingleSelect
                    label={labels.maritalStatus}
                    orientation={orientation}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    id={name}
                    name={name}
                    options={options.maritalStatuses}
                    sizing={sizing}
                />
            )}
        />
        <Controller
            control={form.control}
            name="general.maternalMaidenName"
            rules={maxLengthRule(50, labels.maternalMaidenName)}
            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                <TextInputField
                    label={labels.maternalMaidenName}
                    orientation={orientation}
                    onBlur={onBlur}
                    onChange={onChange}
                    value={value}
                    id={name}
                    name={name}
                    error={error?.message}
                    sizing={sizing}
                />
            )}
        />
        <Controller
            control={form.control}
            name="general.adultsInResidence"
            rules={numericRangeRule(0, 99999)}
            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                <NumericInput
                    label={labels.adultsInResidence}
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
            control={form.control}
            name="general.childrenInResidence"
            rules={numericRangeRule(0, 99999)}
            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                <NumericInput
                    label={labels.childrenInResidence}
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
            control={form.control}
            name="general.primaryOccupation"
            render={({ field: { onChange, onBlur, value, name } }) => (
                <SingleSelect
                    label={labels.primaryOccupation}
                    orientation={orientation}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    options={options.primaryOccupations}
                    id={name}
                    name={name}
                    sizing={sizing}
                />
            )}
        />
        <Controller
            control={form.control}
            name="general.educationLevel"
            render={({ field: { onChange, onBlur, value, name } }) => (
                <SingleSelect
                    label={labels.educationLevel}
                    orientation={orientation}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    id={name}
                    name={name}
                    options={options.educationLevels}
                    sizing={sizing}
                />
            )}
        />
        <Controller
            control={form.control}
            name="general.primaryLanguage"
            render={({ field: { onChange, onBlur, value, name } }) => (
                <SingleSelect
                    label={labels.primaryLanguage}
                    orientation={orientation}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    id={name}
                    name={name}
                    options={options.primaryLanguages}
                    sizing={sizing}
                />
            )}
        />
        <Controller
            control={form.control}
            name="general.speaksEnglish"
            render={({ field: { onChange, onBlur, value, name } }) => (
                <SingleSelect
                    label={labels.speaksEnglish}
                    orientation={orientation}
                    value={value}
                    onChange={onChange}
                    onBlur={onBlur}
                    id={name}
                    name={name}
                    options={options.speaksEnglish.all}
                    sizing={sizing}
                />
            )}
        />
        <Permitted permission="HIVQUESTIONS-GLOBAL">
            <Controller
                control={form.control}
                name="general.stateHIVCase"
                rules={maxLengthRule(16, labels.stateHIVCase)}
                shouldUnregister
                render={({ field: { onChange, value, onBlur, name }, fieldState: { error } }) => (
                    <>
                        <TextInputField
                            label={labels.stateHIVCase}
                            orientation={orientation}
                            onBlur={onBlur}
                            onChange={(value) => onChange({ value: value })}
                            maxLength={16}
                            value={isAllowed(value) ? value.value : undefined}
                            id={name}
                            name={name}
                            error={error?.message}
                            sizing={sizing}
                        />
                    </>
                )}
            />
        </Permitted>
    </>
);

export { GeneralInformationDemographicFields };
