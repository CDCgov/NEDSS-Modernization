import { ReactNode } from 'react';
import classNames from 'classnames';
import { HorizontalEntryWrapper } from './HorizontalEntryWrapper';
import { VerticalEntryWrapper } from './VerticalEntryWrapper';

import styles from './entry-wrapper.module.scss';

type Orientation = 'horizontal' | 'vertical';
type Sizing = 'compact' | 'standard';

type Props = {
    className?: string;
    orientation?: Orientation;
    sizing?: Sizing;
    htmlFor: string;
    label: string;
    error?: string;
    required?: boolean;
    tooltipDirection?: 'top' | 'left' | 'right' | 'bottom';
    children: ReactNode;
};

const EntryWrapper = ({
    sizing = 'standard',
    orientation = 'vertical',
    className,
    tooltipDirection,
    children,
    ...remaining
}: Props) => {
    if (tooltipDirection) {
        return (
            <VerticalEntryWrapper
                tooltipDirection={tooltipDirection}
                className={classNames(styles.entry, styles.tooltip, styles.ASDF, styles.centered, {
                    [styles.compact]: sizing === 'compact'
                })}
                {...remaining}>
                {children}
            </VerticalEntryWrapper>
        );
    }
    if (orientation === 'horizontal' && !tooltipDirection) {
        return (
            <HorizontalEntryWrapper
                className={classNames(styles.entry, className, { [styles.compact]: sizing === 'compact' })}
                {...remaining}>
                {children}
            </HorizontalEntryWrapper>
        );
    }
    if (orientation === 'vertical' && !tooltipDirection) {
        return (
            <VerticalEntryWrapper
                className={classNames(styles.entry, className, styles.vertical, {
                    [styles.compact]: sizing === 'compact'
                })}
                {...remaining}>
                {children}
            </VerticalEntryWrapper>
        );
    }
};

export { EntryWrapper };
export type { Orientation, Sizing };
