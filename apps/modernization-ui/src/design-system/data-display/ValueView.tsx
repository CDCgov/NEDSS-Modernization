import classNames from 'classnames';
import styles from './value-view.module.scss';
import { Sizing } from 'design-system/field';

type Props = {
    title: string;
    required?: boolean;
    value?: string | null;
    sizing?: Sizing;
    centerAlign?: boolean;
};

export const ValueView = ({ title, value, required = false, sizing, centerAlign = false }: Props) => {
    return (
        <div className={classNames(styles.dataRow, sizing && styles[sizing], { [styles.center]: centerAlign })}>
            <span className={classNames(styles.title, { required: required })}>{title}</span>
            <span className={styles.value}>{value}</span>
        </div>
    );
};
