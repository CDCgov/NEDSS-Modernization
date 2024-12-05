import { ChangeEvent as ReactChangeEvent, useEffect, useState } from 'react';
import classNames from 'classnames';

type TextOnChange = (value?: string) => void;

type TextInputProps = {
    id: string;
    type?: 'text' | 'email' | 'number' | 'password' | 'search' | 'tel' | 'url';
    inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'search';
    value?: string;
    onChange?: TextOnChange;
    onBlur?: () => void;
} & Omit<
    JSX.IntrinsicElements['input'],
    'defaultValue' | 'onChange' | 'onBlur' | 'value' | 'type' | 'inputMode' | 'autoComplete'
>;

const TextInput = ({
    id,
    type = 'text',
    inputMode = 'text',
    placeholder,
    mask,
    value,
    onChange,
    onBlur,
    className,
    ...props
}: TextInputProps) => {
    const [current, setCurrent] = useState<string>(value ?? '');

    useEffect(() => {
        setCurrent(value ?? '');
    }, [value]);

    useEffect(() => {
        const inputElement = inputRef.current;
        if (inputElement && mask) {
            inputMask.on(inputElement);
        }

        return () => {
            if (inputElement && mask) {
                inputMask.off(inputElement);
            }
        };
    });

    const handleChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        const next = event.target.value;
        if (next) {
            setCurrent(next);
            onChange?.(next);
        } else {
            setCurrent('');
            onChange?.();
        }
    };

    return (
        <input
            autoComplete="off"
            id={id}
            name={props.name ?? id}
            type={type}
            inputMode={inputMode}
            className={classNames('usa-input', { 'usa-masked': mask }, className)}
            onChange={handleChange}
            onBlur={onBlur}
            placeholder={placeholder}
            value={current}
            {...props}
        />
    );
};

export { TextInput };
export type { TextInputProps };
