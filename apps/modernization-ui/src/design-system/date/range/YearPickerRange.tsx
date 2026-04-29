import { Field, FieldProps } from 'design-system/field';
import { YearRangeField } from '../criteria/range';
import { YearRangeFieldProps } from '../criteria/range/YearRangeField';

type YearPickerRangeProps = FieldProps & YearRangeFieldProps;

/**
 * @return {JSX.Element}
 */
const YearPickerRange = ({
    id,
    orientation,
    sizing,
    label,
    helperText,
    required,
    error,
    value,
    ...remaining
}: YearPickerRangeProps) => {
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
            <YearRangeField id={id} label={label} value={value} sizing={sizing} required={required} {...remaining} />
        </Field>
    );
};

export { YearPickerRange };
