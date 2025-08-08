import { ReactNode, useId, useState } from 'react';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
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

    const collapsibleId = useId();

    return (
        <section
            id={id}
            role="group"
            aria-labelledby={`${id}-title`}
            className={classNames(styles.card, className)}
            {...remaining}>
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
                            className={classNames(styles.toggle, { [styles.collapsed]: collapsed })}
                            sizing="small"
                            tertiary
                            icon="expand_less"
                            aria-label={collapsed ? `Show ${title} content` : `Hide ${title} content`}
                            aria-controls={collapsibleId}
                            aria-expanded={!collapsed}
                            onClick={() => setCollapsed((current) => !current)}
                        />
                    </Shown>
                }
            />

            <Shown when={collapsible} fallback={children}>
                <Collapsible id={collapsibleId} open={!collapsed}>
                    {children}
                </Collapsible>
            </Shown>
            <Shown when={!collapsed}>
                <footer>{footer}</footer>
            </Shown>
        </section>
    );
};

export { Card };
export type { CardProps };
