import { ReactNode } from 'react';
import classNames from 'classnames';
import { HorizontalField } from './HorizontalField';
import { VerticalField } from './VerticalField';

import styles from './field.module.scss';

type Orientation = 'horizontal' | 'vertical';
type Sizing = 'compact' | 'standard';

type FieldProps = {
    orientation?: Orientation;
    sizing?: Sizing;
    label: string;
    helperText?: string;
    error?: string;
    required?: boolean;
    warning?: string;
};

type Props = {
    className?: string;
    htmlFor: string;
    children: ReactNode;
} & FieldProps;

const Field = ({ sizing = 'standard', orientation = 'vertical', className, children, ...remaining }: Props) => {
    const resolvedClasses = classNames(
        styles.entry,
        className,
        styles.vertical,
        { [styles.compact]: sizing === 'compact' },
        { [styles.warn]: remaining.warning && !remaining.error },
        { [styles.error]: remaining.error }
    );

    if (orientation === 'horizontal') {
        return (
            <HorizontalField className={resolvedClasses} {...remaining}>
                {children}
            </HorizontalField>
        );
    }

    return (
        <VerticalField className={resolvedClasses} {...remaining}>
            {children}
        </VerticalField>
    );
};

export { Field };
export type { FieldProps, Orientation, Sizing };
