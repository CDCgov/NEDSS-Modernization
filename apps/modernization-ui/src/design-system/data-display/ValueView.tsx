import classNames from 'classnames';

import { Sizing } from 'design-system/field';

import styles from './value-view.module.scss';

type Props = {
    title: string;
    required?: boolean;
    value?: string | null;
    sizing?: Sizing;
};

export const ValueView = ({ title, value, required = false, sizing }: Props) => {
    return (
        <div className={classNames(styles.dataRow, sizing && styles[sizing])}>
            <span className={classNames(styles.title, { required })}>{title}</span>
            <span className={styles.value}>{value}</span>
        </div>
    );
};
