import classNames from 'classnames';
import styles from './value-view.module.scss';

type Props = {
    title: string;
    required?: boolean;
    value?: string | null;
};
export const ValueView = ({ title, value, required = false }: Props) => {
    return (
        <div className={styles.dataRow}>
            <span className={classNames(styles.title, { required: required })}>{title}</span>
            <span className={styles.value}>{value}</span>
        </div>
    );
};
