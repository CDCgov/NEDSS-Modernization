import { ReactNode, useState } from 'react';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { CardHeader, CardHeaderProps } from './CardHeader';
import { Collapsible } from './Collapsible';

import styles from './card.module.scss';

type CardProps = {
    id: string;
    title: string;
    children: ReactNode;
    collapsible?: boolean;
    open?: boolean;
    footer?: ReactNode;
} & Omit<CardHeaderProps, 'control'> &
    JSX.IntrinsicElements['section'];

const Card = ({
    id,
    title,
    info,
    subtext,
    className,
    level,
    flair,
    actions,
    collapsible = false,
    open = true,
    footer,
    children,
    ...remaining
}: CardProps) => {
    const [collapsed, setCollapsed] = useState<boolean>(!open);

    return (
        <section id={id} aria-labelledby={`${id}-title`} className={classNames(styles.card, className)} {...remaining}>
            <CardHeader
                id={`${id}-title`}
                title={title}
                level={level}
                flair={flair}
                subtext={subtext}
                info={info}
                actions={actions}
                control={
                    <Shown when={collapsible}>
                        <Button
                            className={styles.toggle}
                            tertiary
                            aria-label={collapsed ? `Show ${title} content` : `Hide ${title} content`}
                            sizing="small"
                            onClick={() => setCollapsed((current) => !current)}>
                            <Icon name={collapsed ? 'expand_more' : 'expand_less'} />
                        </Button>
                    </Shown>
                }
            />

            <Shown when={collapsible} fallback={children}>
                <Collapsible open={!collapsed}>{children}</Collapsible>
            </Shown>
            <Shown when={!collapsed}>
                <footer>{footer}</footer>
            </Shown>
        </section>
    );
};

export { Card };
export type { CardProps };
