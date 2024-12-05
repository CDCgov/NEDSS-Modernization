import { EntryComponentProps, EntryWrapper } from 'design-system/entry/base';
import { MaskedTextInput, MaskedTextInputProps } from './MaskedTextInput';

type MaskedTextInputFieldProps = EntryComponentProps & MaskedTextInputProps;

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
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}>
            <MaskedTextInput id={id} {...remaining} />
        </EntryWrapper>
    );
};

export { MaskedTextInputField };
export type { MaskedTextInputFieldProps };
