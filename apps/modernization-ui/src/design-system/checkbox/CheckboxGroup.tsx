import React, { FocusEvent as ReactFocusEvent, useEffect } from 'react';
import classNames from 'classnames';
import { Selectable, useMultiSelection } from 'options';
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
    required?: boolean;
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
    required,
    sizing
}: Props) => {
    const { items, selected, select, deselect, reset } = useMultiSelection({ available: options });

    useEffect(() => {
        if (onChange) {
            onChange(selected);
        }
    }, [selected]);

    useEffect(() => {
        reset(value);
    }, [JSON.stringify(value)]);

    const handleChange = (selectable: Selectable) => (selection?: Selectable) => {
        if (selection) {
            select(selectable);
        } else {
            deselect(selectable);
        }
    };

    return (
        <fieldset
            className={classNames(styles.checkboxGroup, className, {
                [styles.required]: required,
                [styles.compact]: sizing === 'compact'
            })}>
            <legend>{label}</legend>
            {error && <ErrorMessage className={styles.error}>{error}</ErrorMessage>}
            <div className={styles.options}>
                {items.map((item, index) => (
                    <SelectableCheckbox
                        name={name}
                        sizing={sizing}
                        key={index}
                        selectable={item.value}
                        onChange={handleChange(item.value)}
                        selected={item.selected}
                        disabled={disabled}
                        onBlur={onBlur}
                    />
                ))}
            </div>
        </fieldset>
    );
};
