import { ReactNode, useRef, useState } from 'react';
import { Icon } from 'design-system/icon';
import styles from './collapsible-card.module.scss';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { useCollapseObserver } from './useCollapseObserver';

export type CollapsibleCardProps = {
    id: string;
    children: ReactNode;
    /** Whether the card is collapsible (shows the collapse header control). Default is true. */
    collapsible?: boolean;
    showCollapseSeparator?: boolean;
    defaultCollapsed?: boolean;
    header: ReactNode;
} & JSX.IntrinsicElements['section'];

export const CollapsibleCard = ({
    id,
    header,
    children,
    className,
    collapsible = true,
    showCollapseSeparator,
    defaultCollapsed = false,
    ...remaining
}: CollapsibleCardProps) => {
    const [collapsed, setCollapsed] = useState<boolean>(defaultCollapsed);
    const contentRef = useRef<HTMLDivElement>(null);
    const currentHeight = useCollapseObserver({ contentRef, collapsible, collapsed });

    return (
        <section
            id={id}
            className={classNames(styles.card, { [styles.showControl]: collapsible }, className)}
            {...remaining}>
            <header>
                {header}
                <Shown when={collapsible}>
                    <div
                        className={classNames(styles.control, {
                            [styles.separator]: showCollapseSeparator
                        })}>
                        <button
                            type="button"
                            aria-label={collapsed ? `Show card content` : `Hide card content`}
                            onClick={() => setCollapsed((current) => !current)}>
                            <Icon name={collapsed ? 'expand_more' : 'expand_less'} sizing="medium" />
                        </button>
                    </div>
                </Shown>
            </header>
            <div
                ref={contentRef}
                className={classNames(styles.body, {
                    [styles.collapsible]: collapsible,
                    [styles.collapsed]: collapsed
                })}
                style={{
                    maxHeight: currentHeight
                }}>
                {children}
            </div>
        </section>
    );
};
