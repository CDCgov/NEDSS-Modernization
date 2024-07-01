import classNames from 'classnames';
import { Selectable } from 'options';
import styles from './checkbox.module.scss';

type Props = Omit<JSX.IntrinsicElements['input'], 'className' | 'onChange' | 'checked' | 'value'> & {
    className?: string;
    selectable: Selectable;
    selected?: boolean;
    onChange?: (value?: Selectable) => void;
};

export const Checkbox = ({ className, selectable, selected = false, onChange, ...inputProps }: Props) => {
    const id = `${inputProps.name ? inputProps.name + '__' : ''}checkbox__${selectable.value}`;

    const handleChange = (checked: boolean) => {
        onChange?.(checked ? selectable : undefined);
    };
    return (
        <div className={classNames(styles.checkbox, className)}>
            <input
                className={styles.input}
                id={id}
                type="checkbox"
                value={selectable.value}
                checked={selected}
                onChange={(e) => handleChange(e.target.checked)}
                {...inputProps}
            />
            <label className={classNames(styles.label, { [styles.disabled]: inputProps.disabled })} htmlFor={id}>
                {selectable.label}
            </label>
        </div>
    );
};
