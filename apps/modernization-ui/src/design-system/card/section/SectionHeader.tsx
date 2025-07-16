import React, { ReactNode } from 'react';
import classNames from 'classnames';
import { Card } from 'design-system/card';
import { Tag } from 'design-system/tag';
import styles from './SectionHeader.module.scss';
import { Icon } from '../../icon';
import { Hint } from '../../hint';

type SectionHeaderProps = {
    title: string;
    count?: number;
    subtext?: string;
    tooltip?: ReactNode;
    defaultOpen?: boolean;
    slim?: boolean;
    showCounter?: boolean;
    className?: string;
    children?: ReactNode;
};

export const SectionHeader = ({
    title,
    count,
    subtext,
    tooltip,
    defaultOpen = true,
    slim = false,
    showCounter = false,
    className,
    children
}: SectionHeaderProps) => {
    const composedSubtext =
        subtext || tooltip ? (
            <span className={styles.subtext}>
                {subtext}
                {tooltip && (
                    <Hint
                        id={`section-header-hint`}
                        target={<Icon name="help_outline" sizing="small" className={styles.tooltip} />}>
                        {tooltip}
                    </Hint>
                )}
            </span>
        ) : undefined;

    return (
        <Card
            id={'section-header-card'}
            title={title}
            subtext={composedSubtext}
            flair={
                showCounter && typeof count === 'number' ? (
                    <Tag size="small" weight="bold" variant="default">
                        {count}
                    </Tag>
                ) : undefined
            }
            collapsible
            open={defaultOpen}
            className={classNames(styles.cardContainer, className, {
                [styles.slim]: slim
            })}
            level={4}>
            <>{children}</>
        </Card>
    );
};
