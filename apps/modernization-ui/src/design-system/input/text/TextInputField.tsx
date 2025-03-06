import { Field, FieldProps } from 'design-system/field';
import { TextInput, TextInputProps } from './TextInput';

type TextInputFieldProps = FieldProps & TextInputProps & { sorted?: boolean };

const TextInputField = ({
    id,
    label,
    orientation,
    sizing,
    error,
    warning,
    required,
    sorted,
    ...remaining
}: TextInputFieldProps) => {
    return (
        <Field
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}
            warning={warning}>
            <TextInput id={id} sorted={sorted} {...remaining} />
        </Field>
    );
};

export { TextInputField };
export type { TextInputFieldProps };
