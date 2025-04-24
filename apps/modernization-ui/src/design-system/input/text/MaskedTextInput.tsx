import { useCallback, useEffect, useState } from 'react';
import { TextInput, TextInputProps } from './TextInput';
import { masked } from './masked';
import { orUndefined } from 'utils';

type MaskedTextInputProps = {
    mask: string;
} & Omit<TextInputProps, 'maxLength'>;

const MaskedTextInput = ({ mask, value, onChange, ...props }: MaskedTextInputProps) => {
    const [current, setCurrent] = useState(value);
    const applyMask = useCallback(masked(mask), [mask]);

    useEffect(() => {
        if (value === '') {
            setCurrent(value);
        }
    }, [value]);

    const handleChange = (value?: string) => {
        if (value) {
            const next = orUndefined(applyMask(value));
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
