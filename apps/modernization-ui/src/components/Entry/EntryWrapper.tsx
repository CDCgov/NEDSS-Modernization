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
    children: ReactNode;
};

const EntryWrapper = ({ sizing = 'standard', orientation = 'vertical', className, children, ...remaining }: Props) => {
    if (orientation === 'horizontal') {
        return (
            <HorizontalEntryWrapper
                className={classNames(styles.entry, className, styles.horizontal, {
                    [styles.compact]: sizing === 'compact'
                })}
                {...remaining}>
                {children}
            </HorizontalEntryWrapper>
        );
    }

    return (
        <VerticalEntryWrapper
            className={classNames(styles.entry, className, styles.vertical, { [styles.compact]: sizing === 'compact' })}
            {...remaining}>
            {children}
        </VerticalEntryWrapper>
    );
};

export { EntryWrapper };
export type { Orientation, Sizing };
