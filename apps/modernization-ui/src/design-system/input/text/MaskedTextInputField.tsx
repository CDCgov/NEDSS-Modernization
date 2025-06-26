import { Field, FieldProps } from 'design-system/field';
import { MaskedTextInput, MaskedTextInputProps } from './MaskedTextInput';

type MaskedTextInputFieldProps = FieldProps & MaskedTextInputProps;

const MaskedTextInputField = ({
    id,
    label,
    orientation,
    sizing,
    error,
    required,
    helperText,
    ...remaining
}: MaskedTextInputFieldProps) => {
    return (
        <Field
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}
            helperText={helperText}>
            <MaskedTextInput id={id} aria-required={required} {...remaining} />
        </Field>
    );
};

export { MaskedTextInputField };
export type { MaskedTextInputFieldProps };
