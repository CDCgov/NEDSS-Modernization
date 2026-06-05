import classNames from 'classnames';
import styles from './toggle.module.scss';
import { Sizing } from 'design-system/field';
import { isLabelVisible, Labeled } from 'design-system/label-utils';

type Props = Omit<JSX.IntrinsicElements['input'], 'onChange' | 'checked' | 'value'> &
    Labeled & {
        value?: boolean;
        name: string;
        sizing?: Sizing;
        onChange?: (checked: boolean) => void;
    };

export const Toggle = ({ value = false, name, sizing, onChange, ...remaining }: Props) => {
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
                    {...remaining}
                />
                <span className={styles.slider}></span>
            </label>
            {isLabelVisible(remaining) && <label htmlFor={name}>{remaining.label}</label>}
        </div>
    );
};
