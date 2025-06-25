import { Field, FieldProps } from 'design-system/field';
import { TextInput, TextInputProps } from './TextInput';

type TextInputFieldProps = FieldProps & TextInputProps & { sorted?: boolean };

const TextInputField = ({
    id,
    label,
    orientation,
    sizing,
    helperText,
    error,
    warning,
    required,
    ...remaining
}: TextInputFieldProps) => {
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
            <TextInput id={id} required={required} {...remaining} />
        </Field>
    );
};

export { TextInputField };
export type { TextInputFieldProps };
