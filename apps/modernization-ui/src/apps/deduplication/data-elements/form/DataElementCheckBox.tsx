import classNames from 'classnames';
import styles from './DataElementCheckBox.module.scss';

type Props = Omit<JSX.IntrinsicElements['input'], 'onChange' | 'checked' | 'value'> & {
    label: string;
    selected?: boolean;
    onChange?: (checked: boolean) => void;
};

const DataElementCheckBox = ({ id, label, className, selected = false, onChange, ...inputProps }: Props) => {
    const handleChange = (checked: boolean) => {
        onChange?.(checked);
    };
    return (
        <div className={classNames(styles.checkbox, className)} aria-checked={selected}>
            <input
                id={id}
                type="checkbox"
                checked={selected}
                aria-hidden
                onChange={(e) => handleChange(e.target.checked)}
                {...inputProps}
            />
            <label className={classNames(styles.label, { [styles.disabled]: inputProps.disabled })} htmlFor={id}>
                {label}
            </label>
        </div>
    );
};

export { DataElementCheckBox };
