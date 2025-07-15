import React, { useState } from 'react';
import classNames from 'classnames';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { Hint } from 'design-system/hint';
import { Tag } from 'design-system/tag';
import styles from './SectionHeader.module.scss';
import { Heading } from '../../../components/heading';

type SectionHeaderProps = {
    title: string;
    count?: number;
    subtext?: string;
    tooltip?: React.ReactNode;
    defaultOpen?: boolean;
    tall?: boolean;
    slim?: boolean;
    showCounter?: boolean;
    className?: string;
    onToggle?: (collapsed: boolean) => void;
};

export const SectionHeader = ({
    title,
    count,
    subtext,
    tooltip,
    defaultOpen = true,
    tall = true,
    slim = false,
    showCounter = false,
    className,
    onToggle
}: SectionHeaderProps) => {
    const [collapsed, setCollapsed] = useState<boolean>(!defaultOpen);

    const toggle = () => {
        const next = !collapsed;
        setCollapsed(next);
        onToggle?.(next);
    };

    return (
        <div
            className={classNames(styles.headerContainer, className, {
                [styles.tall]: tall,
                [styles.slim]: slim
            })}>
            <div className={styles.header}>
                <div className={classNames(styles.textContainer, { [styles.withSubtext]: !!subtext })}>
                    <div className={styles.titleRow}>
                        <Heading level={2} className={styles.title}>
                            {title}
                        </Heading>
                        {showCounter && typeof count === 'number' && (
                            <Tag size="small" weight="bold" variant="default">
                                {count}
                            </Tag>
                        )}
                    </div>
                    {subtext && (
                        <div className={styles.subtext}>
                            {subtext}
                            {tooltip && (
                                <Hint
                                    id={`${title.replace(/\s+/g, '-').toLowerCase()}-hint`}
                                    target={<Icon name="help_outline" sizing="small" className={styles.tooltip} />}>
                                    {tooltip}
                                </Hint>
                            )}
                        </div>
                    )}
                </div>

                <Button
                    sizing="small"
                    tertiary
                    className={classNames(styles.toggle, {
                        [styles.collapsed]: collapsed
                    })}
                    icon={<Icon name="expand_less" />}
                    aria-label={collapsed ? `Show ${title}` : `Hide ${title}`}
                    onClick={toggle}
                />
            </div>
        </div>
    );
};
