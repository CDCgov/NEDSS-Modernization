import { EntryComponentProps, EntryWrapper } from 'design-system/entry/base';
import { TextInput, TextInputProps } from './TextInput';

type TextInputFieldProps = EntryComponentProps & TextInputProps;

const TextInputField = ({ id, label, orientation, sizing, error, required, ...remaining }: TextInputFieldProps) => {
    return (
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}>
            <TextInput id={id} {...remaining} />
        </EntryWrapper>
    );
};

export { TextInputField };
export type { TextInputFieldProps };
