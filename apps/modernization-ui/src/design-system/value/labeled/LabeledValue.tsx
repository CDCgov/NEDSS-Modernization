import { ReactNode } from 'react';

import styles from './labeled-value.module.scss';
import { Orientation } from 'design-system/field';
import classNames from 'classnames';

type LabeledValueProps = {
    label: string;
    orientation?: Orientation;
    children: ReactNode;
};

const LabeledValue = ({ label, orientation, children }: LabeledValueProps) => {
    return (
        <span className={classNames(styles.labeled, { [styles.vertical]: orientation === 'vertical' })}>
            <strong>{label}</strong>
            {children}
        </span>
    );
};

export { LabeledValue };
export type { LabeledValueProps };
