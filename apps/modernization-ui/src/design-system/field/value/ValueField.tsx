import { ReactNode } from 'react';
import classNames from 'classnames';
import { Sizing } from 'design-system/field';
import { NoData } from 'components/NoData';

import styles from './value-field.module.scss';

type Props = {
    title: string;
    sizing?: Sizing;
    centered?: boolean;
    children?: ReactNode;
};

const ValueField = ({ title, children, sizing, centered = false }: Props) => {
    return (
        <div
            className={classNames(styles.view, {
                [styles.centered]: centered,
                [styles.small]: sizing === 'small',
                [styles.medium]: sizing === 'medium',
                [styles.large]: sizing === 'large'
            })}>
            <span className={styles.content}>
                <span className={styles.title}>{title}</span>
                <span className={styles.value}>{children ?? <NoData />}</span>
            </span>
        </div>
    );
};

export { ValueField };
