import { ReactNode, useEffect, useRef, useState } from 'react';
import { BaseCard } from '../base/BaseCard';
import { Icon } from 'design-system/icon';
import styles from './collapsible-card.module.scss';
import classNames from 'classnames';

export type CollapsibleCardProps = {
    id: string;
    className?: string;
    header: ReactNode;
    children: ReactNode;
};

export const CollapsibleCard = ({ id, header, children, className }: CollapsibleCardProps) => {
    const [height, setHeight] = useState('auto');
    const [collapsed, setCollapsed] = useState<boolean>(false);
    const contentRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const curHeight = contentRef.current?.scrollHeight;
        setHeight(collapsed ? '0' : curHeight ? `${curHeight}px` : 'auto');
    }, [collapsed]);

    return (
        <BaseCard
            id={id}
            className={className}
            header={
                <>
                    <div className={styles.headerContext}>{header}</div>
                    <div className={styles.control}>
                        <button
                            type="button"
                            aria-label={collapsed ? `Show card content` : `Hide card content`}
                            onClick={() => setCollapsed((current) => !current)}>
                            <Icon name={collapsed ? 'expand_more' : 'expand_less'} sizing="small" />
                        </button>
                    </div>
                </>
            }>
            <div
                ref={contentRef}
                className={classNames(styles.collapsible, { [styles.collapsed]: collapsed })}
                style={{
                    maxHeight: height
                }}>
                {children}
            </div>
        </BaseCard>
    );
};
