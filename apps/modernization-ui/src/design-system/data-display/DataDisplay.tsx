import classNames from 'classnames';
import styles from './data-display.module.scss';

type Props = {
    title: string;
    required?: boolean;
    value?: string | null;
};
export const DataDisplay = ({ title, value, required = false }: Props) => {
    return (
        <div className={styles.dataRow}>
            <span className={classNames(styles.title, { required: required })}>{title}</span>
            <span className={styles.value}>{value}</span>
        </div>
    );
};
