import { Field, FieldProps } from 'design-system/field';
import { NumberRangeField, NumberRangeFieldProps } from './NumberRangeField.tsx';

type NumberInputRangeProps = FieldProps & NumberRangeFieldProps;

/**
 * @return {JSX.Element}
 */
const NumberInputRange = ({
    id,
    orientation,
    sizing,
    label,
    helperText,
    required,
    error,
    value,
    ...remaining
}: NumberInputRangeProps) => {
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
            <NumberRangeField id={id} label={label} value={value} sizing={sizing} required={required} {...remaining} />
        </Field>
    );
};

export { NumberInputRange };
