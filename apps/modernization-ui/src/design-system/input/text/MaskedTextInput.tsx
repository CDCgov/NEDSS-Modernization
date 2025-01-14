import { useCallback, useRef } from 'react';
import { TextInput, TextInputProps } from './TextInput';
import { masked } from './masked';

type MaskedTextInputProps = {
    mask: string;
} & Omit<TextInputProps, 'maxLength'>;

const MaskedTextInput = ({ mask, onChange, ...props }: MaskedTextInputProps) => {
    const applyMask = useCallback(masked(mask), [mask]);
    const inputRef = useRef<HTMLInputElement>(null);

    const handleChange = (value?: string) => {
        let next = value;
        if (value) {
            next = applyMask(value);
            if (inputRef.current && next !== value) {
                inputRef.current.value = next;
            }
        }
        onChange?.(next);
    };

    return <TextInput onChange={handleChange} inputRef={inputRef} {...props} maxLength={mask.length} />;
};

export { MaskedTextInput };
export type { MaskedTextInputProps };
