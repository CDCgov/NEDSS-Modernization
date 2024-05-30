import classNames from 'classnames';
import { Selectable } from 'options';
import styles from './checkbox.module.scss';

type Props = {
    option: Selectable;
    selected: boolean;
    onChange: (checked: boolean) => void;
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
                onChange={(e) => onChange(e.target.checked)}
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
