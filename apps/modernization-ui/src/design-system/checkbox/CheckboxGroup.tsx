import { useEffect, FocusEvent as ReactFocusEvent } from 'react';
import classNames from 'classnames';
import { Selectable, useMultiSelection } from 'options';
import { Checkbox } from './Checkbox';
import styles from './checkboxGroup.module.scss';

type Props = {
    name: string;
    label: string;
    className?: string;
    disabled?: boolean;
    options: Selectable[];
    value?: Selectable[];
    onChange?: (selected: Selectable[]) => void;
    onBlur?: (event: ReactFocusEvent<HTMLElement>) => void;
};
export const CheckboxGroup = ({ name, label, options, value = [], onChange, disabled = false, className }: Props) => {
    const { items, selected, select, deselect } = useMultiSelection({ available: options, selected: value });

    useEffect(() => {
        if (onChange) {
            onChange(selected);
        }
    }, [selected]);

    const handleChange = (selectable: Selectable) => (selection?: Selectable) => {
        if (selection) {
            select(selectable);
        } else {
            deselect(selectable);
        }
    };

    return (
        <fieldset className={classNames(styles.checkboxGroup, className)}>
            <legend>{label}</legend>
            <div className={styles.options}>
                {items.map((item, index) => (
                    <Checkbox
                        name={name}
                        key={index}
                        selectable={item.value}
                        onChange={handleChange(item.value)}
                        selected={item.selected}
                        disabled={disabled}
                    />
                ))}
            </div>
        </fieldset>
    );
};
