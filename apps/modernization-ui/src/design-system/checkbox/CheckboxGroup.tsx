import React, { FocusEvent as ReactFocusEvent } from 'react';
import classNames from 'classnames';
import { Selectable } from 'options';
import { SelectableCheckbox } from './SelectableCheckbox';
import styles from './checkboxGroup.module.scss';
import { ErrorMessage } from '@trussworks/react-uswds';
import { Sizing } from 'components/Entry';

type Props = {
    name: string;
    label: string;
    className?: string;
    disabled?: boolean;
    options: Selectable[];
    value?: Selectable[];
    error?: string;
    requried?: boolean;
    sizing?: Sizing;
    onChange?: (selected: Selectable[]) => void;
    onBlur?: (event: ReactFocusEvent<HTMLElement>) => void;
};

export const CheckboxGroup = ({
    name,
    label,
    options,
    value = [],
    onChange,
    onBlur,
    disabled = false,
    className,
    error,
    requried,
    sizing
}: Props) => {
    const handleChange = (selectable: Selectable) => (selection?: Selectable) => {
        if (onChange) {
            if (selection) {
                onChange([...value, selectable]);
            } else {
                onChange(value.filter((item) => item.value !== selectable.value));
            }
        }
    };

    return (
        <fieldset
            className={classNames(styles.checkboxGroup, className, {
                [styles.required]: requried,
                [styles.compact]: sizing === 'compact'
            })}>
            <legend>{label}</legend>
            {error ? <ErrorMessage>{error}</ErrorMessage> : null}
            <div className={styles.options}>
                {options.map((option, index) => (
                    <SelectableCheckbox
                        name={name}
                        sizing={sizing}
                        key={index}
                        selectable={option}
                        onChange={handleChange(option)}
                        selected={value.some((item) => item.value === option.value)}
                        disabled={disabled}
                        onBlur={onBlur}
                    />
                ))}
            </div>
        </fieldset>
    );
};
