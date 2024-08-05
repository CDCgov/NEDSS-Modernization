import styles from './toggle.module.scss';

type Props = {
    onChange: () => void;
    value?: boolean;
    name: string;
    label: string;
};

export const Toggle = ({ onChange, value, name, label }: Props) => {
    return (
        <div className={styles.toggle}>
            <label className={styles.switch}>
                <input
                    type="checkbox"
                    id={name}
                    className={styles.input}
                    onChange={onChange}
                    checked={value}
                    name={name}
                />
                <span className={styles.slider}></span>
            </label>
            <label htmlFor={name}>{label}</label>
        </div>
    );
};
