import { Field, FieldProps } from 'design-system/field';
import { ProtoTextarea, ProtoTextareaProps } from './ProtoTextarea';

type ProtoTextareaFieldProps = FieldProps & ProtoTextareaProps;

const ProtoTextareaField = ({
    id,
    label,
    orientation,
    sizing,
    error,
    warning,
    helperText,
    required,
    ...remaining
}: ProtoTextareaFieldProps) => {
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
            <ProtoTextarea id={id} {...remaining} />
        </Field>
    );
};

export { ProtoTextareaField };
export type { ProtoTextareaFieldProps };
