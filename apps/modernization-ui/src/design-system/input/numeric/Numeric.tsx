import classNames from 'classnames';
import { ChangeEvent, KeyboardEvent as ReactKeyboardEvent } from 'react';

type NumericProps = {
    label?: string;
    inputMode?: 'decimal' | 'numeric';
    value: number | string;
    onChange?: (event: ChangeEvent<HTMLInputElement>) => void;
    error?: string;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const isNonNumericKey = (event: ReactKeyboardEvent<HTMLInputElement>) => {
    const value = event.key as string;
    return value.length == 1 ? !/[0-9]/.test(value) : false;
};

const handleKeydown = (event: ReactKeyboardEvent<HTMLInputElement>) => {
    if (!event.altKey && !event.ctrlKey && !event.metaKey && isNonNumericKey(event)) {
        event.preventDefault();
    }
};

const Numeric = ({ name, inputMode = 'numeric', value, onChange, className, placeholder, ...props }: NumericProps) => (
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
        onKeyDown={handleKeydown}
        aria-label={name}
        {...props}
    />
);

export { Numeric };
