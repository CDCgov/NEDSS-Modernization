import { useState, ReactNode } from 'react';
import classNames from 'classnames';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { Collapsible } from '../../card/Collapsible';

import styles from './SectionHeader.module.scss';
import { Tag } from '../../tag';

type SectionHeaderProps = {
    title: string;
    children: ReactNode;
    count?: number;
    subtext?: string;
    tooltip?: string;
    defaultOpen?: boolean;
    tall?: boolean;
    slim?: boolean;
    showCounter?: boolean;
    className?: string;
};

export const SectionHeader = ({
    title,
    children,
    count,
    subtext,
    tooltip,
    defaultOpen = true,
    tall = false,
    slim = false,
    showCounter = false,
    className
}: SectionHeaderProps) => {
    const [collapsed, setCollapsed] = useState<boolean>(!defaultOpen);

    return (
        <section
            className={classNames(styles.section, className, {
                [styles.tall]: tall,
                [styles.slim]: slim
            })}>
            <div className={styles.header}>
                <h2 className={styles.title}>{title}</h2>

                {showCounter && typeof count === 'number' && (
                    <Tag size="small" weight="bold" variant="default">
                        {count}
                    </Tag>
                )}

                <Button
                    sizing="small"
                    tertiary
                    className={classNames(styles.toggle, { [styles.collapsed]: collapsed })}
                    icon={<Icon name="expand_less" />}
                    aria-label={collapsed ? `Show ${title}` : `Hide ${title}`}
                    onClick={() => setCollapsed((c) => !c)}
                />
            </div>

            {subtext && (
                <div className={styles.subtext}>
                    {subtext}
                    {tooltip && (
                        <span className={styles.tooltip} title={tooltip}>
                            <Icon name="info" />
                        </span>
                    )}
                </div>
            )}

            <Collapsible open={!collapsed}>{children}</Collapsible>
        </section>
    );
};
