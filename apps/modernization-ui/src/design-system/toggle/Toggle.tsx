import classNames from 'classnames';
import styles from './toggle.module.scss';

type Props = Omit<JSX.IntrinsicElements['input'], 'onChange' | 'checked' | 'value'> & {
    value?: boolean;
    name: string;
    label: string;
    sizing?: 'compact' | 'standard';
    onChange?: (checked: boolean) => void;
};

export const Toggle = ({ value = false, name, label, sizing, onChange }: Props) => {
    const handleChange = (checked: boolean) => {
        onChange?.(checked);
    };

    return (
        <div className={classNames(styles.toggle, { [styles.compact]: sizing === 'compact' })}>
            <label className={styles.switch}>
                <input
                    type="checkbox"
                    id={name}
                    checked={value}
                    name={name}
                    onChange={(e) => handleChange(e.target.checked)}
                />
                <span className={styles.slider}></span>
            </label>
            <label htmlFor={name}>{label}</label>
        </div>
    );
};
