import classNames from 'classnames';
import styles from './toggle.module.scss';

type Props = {
    onChange: () => void;
    value?: boolean;
    name: string;
    label: string;
    sizing?: 'compact' | 'standard';
};

export const Toggle = ({ onChange, value, name, label, sizing }: Props) => {
    return (
        <div className={classNames(styles.toggle, { [styles.compact]: sizing === 'compact' })}>
            <label className={styles.switch}>
                <input type="checkbox" id={name} onChange={onChange} checked={value} name={name} />
                <span className={styles.slider}></span>
            </label>
            <label htmlFor={name}>{label}</label>
        </div>
    );
};
