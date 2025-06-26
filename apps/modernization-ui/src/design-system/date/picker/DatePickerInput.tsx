import { Field, FieldProps } from 'design-system/field';
import { DatePicker, DatePickerProps } from './DatePicker';

type DatePickerInputProps = FieldProps & DatePickerProps;

/**
 * @return {JSX.Element}
 */
const DatePickerInput = ({
    id,
    orientation,
    sizing,
    label,
    helperText,
    required,
    error,
    value,
    ...remaining
}: DatePickerInputProps) => {
    return (
        <Field
            orientation={orientation}
            sizing={sizing}
            label={label}
            helperText={helperText}
            htmlFor={id}
            required={required}
            error={error}>
            <DatePicker id={id} label={label} value={value} sizing={sizing} required={required} {...remaining} />
        </Field>
    );
};

export { DatePickerInput };
