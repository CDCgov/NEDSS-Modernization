import { Field, FieldProps } from 'design-system/field';
import { TextArea, TextAreaProps } from './TextArea';

type TextAreaFieldProps = FieldProps & TextAreaProps;

const TextAreaField = ({
    id,
    label,
    orientation,
    sizing,
    error,
    warning,
    helperText,
    required,
    ...remaining
}: TextAreaFieldProps) => {
    return (
        <Field
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}
            warning={warning}
            helperText={helperText}>
            <TextArea id={id} required={required} aria-required={required} {...remaining} />
        </Field>
    );
};

export { TextAreaField };
export type { TextAreaFieldProps };
