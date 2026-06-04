import { ReactNode, useId } from 'react';
import classNames from 'classnames';
import { Sizing } from 'design-system/field';
import { OrElseNoData } from 'design-system/data';
import { HelperText } from '../HelperText';

import styles from '../horizontal-field.module.scss';

type ValueFieldProps = {
    label: string;
    helperText?: string;
    sizing?: Sizing;
    children?: ReactNode;
};

const ValueField = ({ label, helperText, children, sizing }: ValueFieldProps) => {
    const id = useId();
    return (
        <div
            className={classNames(styles.horizontal, {
                [styles.small]: sizing === 'small',
                [styles.medium]: sizing === 'medium',
                [styles.large]: sizing === 'large',
            })}
        >
            <div className={styles.left}>
                <span id={id} role="term" className={styles.label}>
                    {label}
                </span>
                {helperText && <HelperText id={`${id}-hint`}>{helperText}</HelperText>}
            </div>
            <div className={styles.right}>
                <span aria-labelledby={id} role="definition" className={styles.value}>
                    <OrElseNoData>{children}</OrElseNoData>
                </span>
            </div>
        </div>
    );
};

export { ValueField };
export type { ValueFieldProps };
