import classNames from 'classnames';
import { Label } from '@trussworks/react-uswds';
import styles from './numeric-input.module.scss';
import { ChangeEvent, KeyboardEvent as ReactKeyboardEvent } from 'react';

type NumericInputFieldProps = {
    label?: string;
    inputMode?: 'decimal' | 'numeric';
    value: number | string;
    onChange?: (event: ChangeEvent<HTMLInputElement>) => void;
    error?: string;
    required?: boolean;
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

const NumericInputField = ({
    name,
    label,
    inputMode = 'numeric',
    value,
    onChange,
    required,
    className,
    placeholder,
    ...props
}: NumericInputFieldProps) => {
    return (
        <div className={classNames(styles['numeric-input-wrapper'], className)}>
            {label && (
                <Label className={classNames({ required })} htmlFor={name ?? ''}>
                    {label}
                </Label>
            )}
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
                aria-label={label}
                {...props}
            />
        </div>
    );
};

export { NumericInputField };
