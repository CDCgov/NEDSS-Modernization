import { EntryFieldsProps } from 'design-system/entry';
import { Controller, useFormContext } from 'react-hook-form';
import { IdentificationDemographic, labels } from '../identifications';
import { validDateRule, DatePickerInput } from 'design-system/date';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { SingleSelect } from 'design-system/select';
import { TextInputField } from 'design-system/input';
import { IdentificationOptions } from './useIdentificationOptions';

type IdentificationDemographicFieldsProps = { options: IdentificationOptions } & EntryFieldsProps;

const IdentificationDemographicFields = ({
    orientation = 'horizontal',
    sizing = 'medium',
    options
}: IdentificationDemographicFieldsProps) => {
    const { control } = useFormContext<IdentificationDemographic>();

    return (
        <>
            <Controller
                control={control}
                name="asOf"
                rules={{ ...validateRequiredRule(labels.asOf), ...validDateRule(labels.asOf) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label={labels.asOf}
                        id={name}
                        orientation={orientation}
                        onBlur={onBlur}
                        value={value}
                        onChange={onChange}
                        error={error?.message}
                        required
                        sizing={sizing}
                        aria-description="This date defaults to today and can be changed if needed"
                    />
                )}
            />
            <Controller
                control={control}
                name="type"
                rules={{ ...validateRequiredRule(labels.type) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <SingleSelect
                        label={labels.type}
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        id={name}
                        options={options.types}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="issuer"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label={labels.issuer}
                        orientation={orientation}
                        value={value}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        options={options.authorities}
                        sizing={sizing}
                    />
                )}
            />
            <Controller
                control={control}
                name="value"
                rules={{ ...validateRequiredRule(labels.value), ...maxLengthRule(100, labels.value) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <TextInputField
                        label={labels.value}
                        orientation={orientation}
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        id={name}
                        error={error?.message}
                        required
                        sizing={sizing}
                    />
                )}
            />
        </>
    );
};

export { IdentificationDemographicFields };
