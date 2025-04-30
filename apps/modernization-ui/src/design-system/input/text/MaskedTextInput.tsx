import { useCallback, useEffect, useState } from 'react';
import { TextInput, TextInputProps } from './TextInput';
import { masked } from './masked';
import { mapOr } from 'utils/mapping';

type MaskedTextInputProps = {
    mask: string;
} & Omit<TextInputProps, 'maxLength'>;

const MaskedTextInput = ({ mask, value, onChange, ...props }: MaskedTextInputProps) => {
    const applyMask = useCallback(mapOr(masked(mask), undefined), [mask]);

    const [current, setCurrent] = useState(applyMask(value));

    useEffect(() => {
        const adjusted = applyMask(value);
        if (adjusted !== current) {
            //  the value passed to the component has changed and is out of sync with current
            //  update current to match the incoming value.
            setCurrent(adjusted);
        }
    }, [value]);

    const handleChange = (value?: string) => {
        if (value) {
            const next = applyMask(value);
            onChange?.(next);
            setCurrent(next);
        } else {
            onChange?.();
            setCurrent(undefined);
        }
    };

    return <TextInput onChange={handleChange} {...props} maxLength={mask.length} value={current} />;
};

export { MaskedTextInput };
export type { MaskedTextInputProps };
