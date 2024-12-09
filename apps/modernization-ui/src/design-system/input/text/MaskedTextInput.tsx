import { KeyboardEvent as ReactKeyboardEvent, useCallback } from 'react';
import { TextInput, TextInputProps } from './TextInput';
import { masked } from './masked';

type MaskedTextInputProps = {
    mask: string;
} & Omit<TextInputProps, 'maxLength'>;

const MaskedTextInput = ({ mask, ...props }: MaskedTextInputProps) => {
    const applyMask = useCallback(masked(mask), [mask]);

    const handleKeyup = (event: ReactKeyboardEvent) => {
        const input = event.target as HTMLInputElement;
        input.value = applyMask(input.value);
    };

    return <TextInput onKeyUp={handleKeyup} {...props} maxLength={mask.length} />;
};

export { MaskedTextInput };
export type { MaskedTextInputProps };
