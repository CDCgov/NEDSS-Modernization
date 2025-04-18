import { ReactNode, useEffect, useRef, useState } from 'react';
import { Icon } from 'design-system/icon';
import styles from './collapsible-card.module.scss';
import classNames from 'classnames';
import { Shown } from 'conditional-render';

export type CollapsibleCardProps = {
    id: string;
    className?: string;
    /** Whether the card is collapsible (shows the collapse header control). Default is true. */
    collapsible?: boolean;
    showCollapseSeparator?: boolean;
    header: ReactNode;
    children: ReactNode;
};

export const CollapsibleCard = ({
    id,
    header,
    children,
    className,
    collapsible = true,
    showCollapseSeparator
}: CollapsibleCardProps) => {
    const [height, setHeight] = useState('auto');
    const [collapsed, setCollapsed] = useState<boolean>(false);
    const contentRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const curHeight = contentRef.current?.scrollHeight;
        setHeight(collapsed ? '0' : curHeight ? `${curHeight}px` : 'auto');
    }, [collapsed]);

    return (
        <section id={id} className={classNames(styles.card, { [styles.showControl]: collapsible }, className)}>
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
                className={classNames(styles.collapsible, { [styles.collapsed]: collapsed })}
                style={{
                    maxHeight: height
                }}>
                {children}
            </div>
        </section>
    );
};
