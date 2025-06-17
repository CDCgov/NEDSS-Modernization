import classNames from 'classnames';
import { NoData } from 'design-system/data';
import { ReactNode } from 'react';

import styles from './result-item.module.scss';

type ResultItemProps = {
    label: string;
    children?: ReactNode;
    orientation?: 'vertical' | 'horizontal';
};

const ResultItem = ({ label, orientation = 'horizontal', children }: ResultItemProps) => {
    return (
        <div
            className={classNames(styles.item, {
                [styles.vertical]: orientation === 'vertical',
                [styles.horizontal]: orientation === 'horizontal'
            })}>
            <span className={styles.label}>{label}</span>
            <Value>{children}</Value>
        </div>
    );
};

type ResultValueProps = {
    children?: ReactNode;
};

const Value = ({ children }: ResultValueProps) => {
    if (!children) {
        return <NoData />;
    } else if (typeof children === 'string' || typeof children === 'number' || typeof children === 'boolean') {
        return <p>{children}</p>;
    } else {
        return <>{children}</>;
    }
};

export { ResultItem };
