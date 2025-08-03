import { Controller, UseFormReturn } from 'react-hook-form';
import { EntryFieldsProps } from 'design-system/entry';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { TextAreaField } from 'design-system/input/text/TextAreaField';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';
import { AdministrativeInformation, labels } from '../administrative';

type AdministrativeInformationFieldsProps = {
    form: UseFormReturn<{ administrative?: AdministrativeInformation }>;
} & EntryFieldsProps;

const AdministrativeInformationFields = ({
    form,
    sizing,
    orientation = 'horizontal'
}: AdministrativeInformationFieldsProps) => (
    <>
        <Controller
            control={form.control}
            name="administrative.asOf"
            rules={{ ...validDateRule(labels.asOf), ...validateRequiredRule(labels.asOf) }}
            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                <DatePickerInput
                    id={name}
                    label={labels.asOf}
                    value={value}
                    onBlur={onBlur}
                    onChange={onChange}
                    name={name}
                    orientation={orientation}
                    error={error?.message}
                    sizing={sizing}
                    required
                />
            )}
        />
        <Controller
            control={form.control}
            name="administrative.comment"
            rules={maxLengthRule(2000, labels.comment)}
            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                <TextAreaField
                    label={labels.comment}
                    orientation={orientation}
                    sizing={sizing}
                    onBlur={onBlur}
                    onChange={onChange}
                    value={value}
                    name={name}
                    id={name}
                    error={error?.message}
                />
            )}
        />
    </>
);

export { AdministrativeInformationFields };
