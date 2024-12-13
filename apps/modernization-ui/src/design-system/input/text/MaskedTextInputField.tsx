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
    ...remaining
}: MaskedTextInputFieldProps) => {
    return (
        <Field orientation={orientation} sizing={sizing} label={label} htmlFor={id} required={required} error={error}>
            <MaskedTextInput id={id} {...remaining} />
        </Field>
    );
};

export { MaskedTextInputField };
export type { MaskedTextInputFieldProps };
