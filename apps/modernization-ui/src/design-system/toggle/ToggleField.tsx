import { Field, FieldProps } from 'design-system/field';
import { Toggle, ToggleProps } from './Toggle';

type ToggleFieldProps = FieldProps & Omit<ToggleProps, 'aria-label' | 'name'> & { sorted?: boolean; id: string };

const ToggleField = ({
    id,
    label,
    orientation,
    sizing,
    helperText,
    error,
    warning,
    required,
    ...remaining
}: ToggleFieldProps) => {
    return (
        <Field
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            helperText={helperText}
            required={required}
            error={error}
            warning={warning}>
            <Toggle
                name={id}
                required={required}
                aria-label={label}
                aria-required={required}
                sizing={sizing}
                {...remaining}
            />
        </Field>
    );
};

export { ToggleField };
export type { ToggleFieldProps };
