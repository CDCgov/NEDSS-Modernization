import { useCallback, useState } from 'react';
import { TextInput, TextInputProps } from './TextInput';
import { masked } from './masked';
import { mapOr } from 'utils/mapping';

type MaskedTextInputProps = {
    mask: string;
} & Omit<TextInputProps, 'maxLength'>;

const MaskedTextInput = ({ mask, value, onChange, ...props }: MaskedTextInputProps) => {
    const [current, setCurrent] = useState(value);
    const applyMask = useCallback(mapOr(masked(mask), undefined), [mask]);

    if (current !== value) {
        setCurrent(applyMask(value));
    }

    const handleChange = (value?: string) => {
        if (value) {
            const next = applyMask(value);
            setCurrent(next);
            onChange?.(next);
        } else {
            setCurrent(undefined);
            onChange?.();
        }
    };

    return <TextInput onChange={handleChange} {...props} maxLength={mask.length} value={current} />;
};

export { MaskedTextInput };
export type { MaskedTextInputProps };
