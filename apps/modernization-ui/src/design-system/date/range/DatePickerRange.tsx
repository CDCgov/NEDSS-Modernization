import { Field, FieldProps } from 'design-system/field';
import { DateRangeField } from '../criteria/range';
import { DateRangeFieldProps } from '../criteria/range/DateRangeField';

type DatePickerRangeProps = FieldProps & DateRangeFieldProps;

/**
 * @return {JSX.Element}
 */
const DatePickerRange = ({
    id,
    orientation,
    sizing,
    label,
    helperText,
    required,
    error,
    value,
    ...remaining
}: DatePickerRangeProps) => {
    return (
        <Field
            orientation={orientation}
            sizing={sizing}
            label={label}
            helperText={helperText}
            htmlFor={id}
            required={required}
            error={error}
        >
            <DateRangeField id={id} label={label} value={value} sizing={sizing} required={required} {...remaining} />
        </Field>
    );
};

export { DatePickerRange };
