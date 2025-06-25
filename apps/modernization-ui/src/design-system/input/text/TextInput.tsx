import { ChangeEvent as ReactChangeEvent } from 'react';
import classNames from 'classnames';
import { Icon } from 'design-system/icon';

import styles from './text-input.module.scss';

export type TextOnChange = (value?: string) => void;

type TextInputProps = {
    id: string;
    type?: 'text' | 'email' | 'number' | 'password' | 'search' | 'tel' | 'url';
    inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'search';
    value?: string;
    onChange?: TextOnChange;
    onBlur?: () => void;
    onClear?: () => void;
    clearable?: boolean;
} & Omit<
    JSX.IntrinsicElements['input'],
    'defaultValue' | 'onChange' | 'onBlur' | 'value' | 'type' | 'inputMode' | 'autoComplete'
>;

const TextInput = ({
    id,
    type = 'text',
    inputMode = 'text',
    placeholder,
    value,
    onChange,
    onBlur,
    className,
    clearable = false,
    onClear,
    ...props
}: TextInputProps) => {
    const current = value ?? '';

    const handleChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        const next = event.target.value;
        if (next) {
            onChange?.(next);
        } else {
            onChange?.();
        }
    };

    const handleClear = () => {
        onChange?.();
        onClear?.();
    };

    return (
        <span className={classNames({ [styles.grouped]: clearable })}>
            <input
                autoComplete="off"
                id={id}
                name={props.name ?? id}
                type={type}
                inputMode={inputMode}
                className={classNames('usa-input', styles.input, className)}
                onChange={handleChange}
                onBlur={onBlur}
                placeholder={placeholder}
                value={current}
                aria-required={props.required}
                {...props}
            />
            {clearable && current && (
                <span className={styles.suffix}>
                    <Icon role="button" name="close" sizing="small" onClick={handleClear} />
                </span>
            )}
        </span>
    );
};

export { TextInput };
export type { TextInputProps };
