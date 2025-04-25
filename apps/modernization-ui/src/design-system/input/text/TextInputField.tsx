import { Field, FieldProps } from 'design-system/field';
import { TextInput, TextInputProps } from './TextInput';
import { useEffect, useState } from 'react';

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
    value,
    onChange,
    ...remaining
}: TextInputFieldProps) => {
    const [current, setCurrent] = useState(value);

    useEffect(() => {
        if (value === '') {
            setCurrent(value);
        }
    }, [value]);

    const handleChange = (value?: string) => {
        if (value) {
            setCurrent(value);
            onChange?.(value);
        } else {
            setCurrent(undefined);
            onChange?.();
        }
    };

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
            <TextInput id={id} value={current} onChange={handleChange} {...remaining} />
        </Field>
    );
};

export { TextInputField };
export type { TextInputFieldProps };
