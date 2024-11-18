import classNames from 'classnames';
import { ChangeEvent, KeyboardEvent as ReactKeyboardEvent } from 'react';

type NumericProps = {
    label?: string;
    inputMode?: 'decimal' | 'numeric';
    value: number | string;
    onChange?: (event: ChangeEvent<HTMLInputElement>) => void;
    onKeyDown?: (event: ReactKeyboardEvent<HTMLInputElement>) => void;
    error?: string;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const Numeric = ({
    name,
    inputMode = 'numeric',
    value,
    onChange,
    onKeyDown,
    className,
    placeholder,
    ...props
}: NumericProps) => (
    <input
        id={name}
        name={name}
        className={classNames('usa-input', className)}
        type="number"
        inputMode={inputMode}
        onChange={onChange}
        placeholder={placeholder}
        value={value}
        pattern="[0-9]*"
        onKeyDown={onKeyDown}
        aria-label={name}
        {...props}
    />
);

export { Numeric };
