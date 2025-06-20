import { ReactNode, useId } from 'react';
import classNames from 'classnames';
import { Sizing } from 'design-system/field';

import styles from './value-field.module.scss';
import { NoData } from 'design-system/data';

type Props = {
    label: string;
    sizing?: Sizing;
    children?: ReactNode;
};

const ValueField = ({ label, children, sizing }: Props) => {
    const id = useId();
    return (
        <div
            className={classNames(styles.view, {
                [styles.small]: sizing === 'small',
                [styles.medium]: sizing === 'medium',
                [styles.large]: sizing === 'large'
            })}>
            <span id={id} role="term" className={styles.label}>
                {label}
            </span>
            <span aria-labelledby={id} role="definition" className={styles.value}>
                {children ?? <NoData />}
            </span>
        </div>
    );
};

export { ValueField };
