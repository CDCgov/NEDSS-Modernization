import { ReactNode, useRef, useState } from 'react';
import { Icon } from 'design-system/icon';
import styles from './collapsible-card.module.scss';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { useCollapseObserver } from './useCollapseObserver';

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
    //const [height, setHeight] = useState('auto');
    const [collapsed, setCollapsed] = useState<boolean>(false);
    const contentRef = useRef<HTMLDivElement>(null);
    const currentHeight = useCollapseObserver({ contentRef, collapsible, collapsed });

    // useEffect(() => {
    //     const updateHeight = () => {
    //         if (contentRef.current) {
    //             // Temporarily disable overflow to measure the full height
    //             const previousOverflow = contentRef.current.style.overflow;
    //             contentRef.current.style.overflow = 'visible';

    //             const currentHeight = contentRef.current.scrollHeight;
    //             setHeight(!collapsible ? 'auto' : collapsed ? '0' : `${currentHeight}px`);

    //             // Restore the previous overflow style
    //             contentRef.current.style.overflow = previousOverflow;
    //         }
    //     };

    //     // Create observers to handle resize and mutation events
    //     // These observers will detect changes in size of the content
    //     const resizeObserver = new ResizeObserver(updateHeight);
    //     const mutationObserver = new MutationObserver(updateHeight);

    //     if (contentRef.current) {
    //         resizeObserver.observe(contentRef.current);
    //         mutationObserver.observe(contentRef.current, { childList: true, subtree: true, characterData: true });
    //     }

    //     // Initial height update
    //     updateHeight();

    //     return () => {
    //         if (contentRef.current) {
    //             resizeObserver.unobserve(contentRef.current);
    //             mutationObserver.disconnect();
    //         }
    //     };
    // }, [collapsible, collapsed]);

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
