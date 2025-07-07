import { FocusEvent as ReactFocusEvent, useEffect } from 'react';
import { Selectable, useMultiSelection } from 'options';
import { SelectableCheckbox } from './SelectableCheckbox';
import styles from './checkboxGroup.module.scss';
import { Orientation, Sizing } from 'components/Entry';
import classNames from 'classnames';
import { Field } from 'design-system/field';

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
    orientation?: Orientation;
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
    error,
    required,
    orientation = 'vertical',
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
        <Field
            className={classNames(styles.checkboxGroup, {
                [styles.horizontalCheckboxGroup]: orientation === 'horizontal'
            })}
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={name}
            required={required}
            error={error}>
            <fieldset
                className={classNames(
                    {
                        [styles.verticalOptions]: orientation === 'vertical',
                        [styles.horizontalOptions]: orientation === 'horizontal'
                    },
                    styles.fieldSet
                )}
                aria-required={required}
                aria-label={`${label} ${required ? '*' : ''}`}>
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
            </fieldset>
        </Field>
    );
};
