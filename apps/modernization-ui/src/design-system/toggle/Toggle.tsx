import classNames from 'classnames';
import styles from './toggle.module.scss';
import { Sizing } from 'design-system/field';

type Props = Omit<JSX.IntrinsicElements['input'], 'onChange' | 'checked' | 'value'> & {
    value?: boolean;
    name: string;
    label: string;
    sizing?: Sizing;
    onChange?: (checked: boolean) => void;
};

export const Toggle = ({ value = false, name, label, sizing, onChange }: Props) => {
    const handleChange = (checked: boolean) => {
        onChange?.(checked);
    };

    return (
        <div className={classNames(styles.toggle, { [styles.medium]: sizing === 'medium' })}>
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
