import { ReactNode } from 'react';
import classNames from 'classnames';
import { HorizontalField } from './HorizontalField';
import { VerticalField } from './VerticalField';

import styles from './field.module.scss';

type Orientation = 'horizontal' | 'vertical';
type Sizing = 'small' | 'medium' | 'large';

type FieldProps = {
    label: string;
    orientation?: Orientation;
    sizing?: Sizing;
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

const Field = ({ sizing = 'large', orientation = 'vertical', className, children, ...remaining }: Props) => {
    const resolvedClasses = classNames(
        styles.entry,
        className,
        sizing && styles[sizing],
        { [styles.warn]: remaining.warning && !remaining.error },
        { [styles.error]: remaining.error }
    );

    if (orientation === 'horizontal') {
        return (
            <HorizontalField className={resolvedClasses} sizing={sizing} {...remaining}>
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
