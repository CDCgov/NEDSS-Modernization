import { Field, FieldProps } from 'design-system/field';

import { MonthYearRangeField } from '../criteria/range';
import { MonthYearRangeFieldProps } from '../criteria/range/MonthYearRangeField';

type MonthYearPickerRangeProps = FieldProps & MonthYearRangeFieldProps;

const MonthYearPickerRange = ({
    id,
    orientation,
    sizing,
    label,
    helperText,
    required,
    error,
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
            <MonthYearRangeField id={id} label={label} sizing={sizing} required={required} {...remaining} />
        </Field>
    );
};

export { MonthYearPickerRange };
