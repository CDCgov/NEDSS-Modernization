import { Field, FieldProps } from 'design-system/field';
import { TextInput, TextInputProps } from './TextInput';

type TextInputFieldProps = FieldProps & TextInputProps;

const TextInputField = ({
    id,
    label,
    orientation,
    sizing,
    error,
    warnings,
    required,
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
            warnings={warnings}>
            <TextInput id={id} {...remaining} />
        </Field>
    );
};

export { TextInputField };
export type { TextInputFieldProps };
