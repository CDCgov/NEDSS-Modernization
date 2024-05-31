import classNames from 'classnames';
import { Selectable } from 'options';
import styles from './checkbox.module.scss';

type Props = {
    option: Selectable;
    selected: boolean;
    onChange?: (checked: boolean) => void;
    onSelect?: (checked: boolean, value: string) => void;
    disabled?: boolean;
    className?: string;
};
export const Checkbox = ({ option, selected, onChange, onSelect, disabled = false, className }: Props) => {
    const handleChange = (checked: boolean) => {
        onChange?.(checked);
        onSelect?.(checked, option.value);
    };
    return (
        <div className={classNames(styles.checkbox, className)}>
            <input
                className={styles.input}
                id={`checkbox-${option.value}`}
                type="checkbox"
                name={option.name}
                value={option.value}
                checked={selected}
                onChange={(e) => handleChange(e.target.checked)}
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
