import React, { useState, InputHTMLAttributes } from 'react';
import classNames from 'classnames';
import { Button } from 'design-system/search/Button';
import { Icon } from 'design-system/icon';
import styles from './SearchBar.module.scss';

type SearchBarSize = 'SM' | 'MD' | 'LG';

type SearchBarProps = {
    size?: SearchBarSize;
    tall?: boolean;
    placeholder?: string;
} & Omit<InputHTMLAttributes<HTMLInputElement>, 'size'>;

export const SearchBar = ({ size = 'MD', tall = false, placeholder = '', ...props }: SearchBarProps) => {
    const [value, setValue] = useState('');
    const [focused, setFocused] = useState(false);

    const handleClear = () => setValue('');

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
                    onChange={(e) => setValue(e.target.value)}
                    onFocus={() => setFocused(true)}
                    onBlur={() => setFocused(false)}
                    placeholder={placeholder}
                    {...props}
                />

                {value && (
                    <button
                        type="button"
                        onClick={handleClear}
                        className={classNames(styles.clearButton, styles[`size-${size}`])}
                        aria-label="Clear">
                        <Icon name="close" />
                    </button>
                )}
            </div>

            <Button
                size={size}
                icon={<Icon name="search" aria-hidden />}
                className={classNames(styles.searchButton, styles[`size-${size}`])}
            />
        </div>
    );
};
