import { ReactNode } from 'react';

import styles from './labeled-value.module.scss';
import { Orientation } from 'design-system/field';
import classNames from 'classnames';

type LabeledValueProps = {
    label: string;
    orientation?: Orientation;
    children: ReactNode;
} & JSX.IntrinsicElements['span'];

const LabeledValue = ({ label, orientation, children, ...remaining }: LabeledValueProps) => {
    return (
        <span className={classNames(styles.labeled, { [styles.vertical]: orientation === 'vertical' })} {...remaining}>
            <strong>{label}</strong>
            {children}
        </span>
    );
};

export { LabeledValue };
export type { LabeledValueProps };
