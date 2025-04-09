import classNames from 'classnames';
import styles from './checkbox.module.scss';
import { maybeUseComponentSizing } from 'design-system/sizing/useComponentSizing';

type Props = Omit<JSX.IntrinsicElements['input'], 'onChange' | 'checked' | 'value'> & {
    label: string;
    selected?: boolean;
    onChange?: (checked: boolean) => void;
};

const Checkbox = ({ id, label, className, selected = false, onChange, ...inputProps }: Props) => {
    const handleChange = (checked: boolean) => {
        onChange?.(checked);
    };
    const sizing = maybeUseComponentSizing();
    return (
        <div
            className={classNames(styles.checkbox, className, { [styles.checkbox_small]: sizing === 'small' })}
            aria-checked={selected}>
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

export { Checkbox };
