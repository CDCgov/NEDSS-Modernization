import classNames from 'classnames';
import { Selectable } from 'options';
import { useEffect, useState } from 'react';
import { Checkbox } from './Checkbox';
import styles from './checkboxGroup.module.scss';

type Props = {
    label?: string;
    options: Selectable[];
    initialSelection?: string[];
    className?: string;
    onChange?: (selected: string[]) => void;
    disabled?: boolean;
};
export const CheckboxGroup = ({
    label,
    options,
    initialSelection = [],
    onChange,
    disabled = false,
    className
}: Props) => {
    const [selected, setSelected] = useState<string[]>(initialSelection);
    // Prevents emitting `onChange` when setting initialValue
    const [initialized, setInitialized] = useState<boolean>(false);

    useEffect(() => {
        if (!initialized) {
            setInitialized(true);
        } else {
            onChange?.(selected);
        }
    }, [selected]);

    useEffect(() => {
        setSelected(initialSelection);
    }, [JSON.stringify(initialSelection)]);

    const handleSelectionChange = (checked: boolean, value: string) => {
        if (checked) {
            setSelected((previous) => [...previous, value]);
        } else {
            setSelected((previous) => [...previous.filter((v) => v !== value)]);
        }
    };

    return (
        <fieldset className={classNames(styles.checkboxGroup, className)}>
            <legend className={styles.label}>{label}</legend>
            <div className={styles.options}>
                {options.map((o, k) => (
                    <Checkbox
                        key={k}
                        option={o}
                        onSelect={handleSelectionChange}
                        selected={selected.includes(o.value)}
                        disabled={disabled}
                    />
                ))}
            </div>
        </fieldset>
    );
};
