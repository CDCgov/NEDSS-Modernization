import { Heading, HeadingLevel } from 'components/heading';
import { ReactNode } from 'react';
import { CollapsibleCard } from './collapsible';
import styles from './card.module.scss';

type Props = {
    id: string;
    title: ReactNode;
    info?: ReactNode;
    subtext?: string;
    level?: HeadingLevel;
    className?: string;
    children: ReactNode;
    collapsible?: boolean;
};
export const Card = ({ id, title, info, subtext, children, className, level = 2, collapsible = false }: Props) => {
    return (
        <CollapsibleCard
            id={id}
            className={className}
            collapsible={collapsible}
            header={
                <>
                    <div className={styles.titleBlock}>
                        <Heading level={level}>{title}</Heading>
                        {subtext && <div className={styles.subtext}>{subtext}</div>}
                    </div>
                    {info && <div className={styles.info}>{info}</div>}
                </>
            }>
            {children}
        </CollapsibleCard>
    );
};
