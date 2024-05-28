import React from 'react';
import { Selectable } from 'options';
import styles from './checkbox.module.scss';
import classNames from 'classnames';

type Props = {
    option: Selectable;
    selected: boolean;
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    disabled?: boolean;
    className?: string;
};
export const Checkbox = ({ option, selected, onChange, disabled = false, className }: Props) => {
    return (
        <div className={classNames(styles.checkbox, className)}>
            <input
                className={styles.input}
                id={`checkbox-${option.value}`}
                type="checkbox"
                name={option.name}
                value={option.value}
                checked={selected}
                onChange={onChange}
                disabled={disabled}
            />
            <label
                className={classNames(styles.label, { [styles.disabled]: disabled })}
                htmlFor={`checkbox-${option.value}`}>
                {option.label}
            </label>
        </div>
    );
};
