import React, { useState, InputHTMLAttributes } from 'react';
import classNames from 'classnames';
import { Button } from 'design-system/button/Button';
import { Sizing } from 'design-system/field';
import styles from './SearchBar.module.scss';

type SearchBarProps = {
    size?: Sizing;
    tall?: boolean;
    placeholder?: string;
    value?: string;
    onChange?: (value: string) => void;
    onSearch?: (value: string) => void;
} & Omit<InputHTMLAttributes<HTMLInputElement>, 'size' | 'value' | 'onChange'>;

export const SearchBar = ({
    size = 'medium',
    tall = false,
    placeholder = '',
    value: controlledValue,
    onChange: controlledOnChange,
    onSearch,
    ...props
}: SearchBarProps) => {
    // Internal state only if no controlledValue provided
    const [internalValue, setInternalValue] = useState('');
    const [focused, setFocused] = useState(false);

    // Use controlled or internal value
    const value = controlledValue ?? internalValue;

    // Handle change event
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (controlledOnChange) {
            controlledOnChange(e.target.value); // call external onChange if controlled
        } else {
            setInternalValue(e.target.value);
        }
    };

    const handleClear = () => {
        if (controlledOnChange) {
            controlledOnChange('');
        } else {
            setInternalValue('');
        }
    };

    const inputState = value ? 'filled' : placeholder ? 'placeholder' : 'empty';

    const className = classNames(styles.searchBar, styles[`size-${size}`], styles[inputState], {
        [styles.focused]: focused,
        [styles.tall]: tall
    });

    return (
        <div
            className={classNames(styles.wrapper, styles[`size-${size}`], {
                [styles.tall]: tall
            })}>
            <div className={className}>
                <input
                    className={classNames(styles.input, styles[`size-${size}`])}
                    value={value}
                    onChange={handleChange}
                    onFocus={() => setFocused(true)}
                    onBlur={() => setFocused(false)}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter') {
                            onSearch?.(value);
                        }
                    }}
                    aria-label={props['aria-label'] ?? 'Search-input'}
                    placeholder={placeholder}
                    {...props}
                />

                {value && (
                    <Button
                        type="button"
                        sizing={size}
                        icon="close"
                        onClick={handleClear}
                        className={classNames(styles.clearButton, styles[`size-${size}`])}
                        aria-label="Clear"
                    />
                )}
            </div>
            <div>
                <Button
                    sizing={size}
                    icon="search"
                    className={classNames(styles.searchButton, styles[`size-${size}`], { [styles.tall]: tall })}
                    onClick={() => onSearch?.(value)}
                    aria-label="Search"
                />
            </div>
        </div>
    );
};
