import { Field, FieldProps } from 'design-system/field';
import { MonthYearRangeField } from '../criteria/range';
import { MonthYearRangeFieldProps } from '../criteria/range/MonthYearRangeField';

type MonthYearPickerRangeProps = FieldProps & MonthYearRangeFieldProps;

/**
 * @return {JSX.Element}
 */
const MonthYearPickerRange = ({
    id,
    orientation,
    sizing,
    label,
    helperText,
    required,
    error,
    value,
    ...remaining
}: MonthYearPickerRangeProps) => {
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
            <MonthYearRangeField
                id={id}
                label={label}
                value={value}
                sizing={sizing}
                required={required}
                {...remaining}
            />
        </Field>
    );
};

export { MonthYearPickerRange };
