import { Selectable } from 'options';
import { useEffect, useState } from 'react';
import { Checkbox } from './Checkbox';
import styles from './checkboxGroup.module.scss';
import classNames from 'classnames';

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

    const handleSelectionChange = (value: string) => {
        setSelected((current) =>
            current.includes(value) ? [...current.filter((v) => v !== value)] : [...current, value]
        );
    };

    useEffect(() => {
        onChange?.(selected);
    }, [selected]);

    useEffect(() => {
        setSelected(initialSelection);
    }, [initialSelection]);

    return (
        <fieldset className={classNames(styles.checkboxGroup, className)}>
            <legend className={styles.label}>{label}</legend>
            <div className={styles.options}>
                {options.map((o, k) => (
                    <Checkbox
                        key={k}
                        option={o}
                        onChange={() => handleSelectionChange(o.value)}
                        selected={selected.includes(o.value)}
                        disabled={disabled}
                    />
                ))}
            </div>
        </fieldset>
    );
};
