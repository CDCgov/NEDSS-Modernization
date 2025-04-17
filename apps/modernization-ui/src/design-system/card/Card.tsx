import { Heading, HeadingLevel } from 'components/heading';
import { ReactNode } from 'react';
import styles from './card.module.scss';
import { BaseCard } from './base/BaseCard';

type Props = {
    id: string;
    title: ReactNode;
    info?: ReactNode;
    subtext?: string;
    level?: HeadingLevel;
    className?: string;
    children: ReactNode;
};
export const Card = ({ id, title, info, subtext, children, className, level = 2 }: Props) => {
    return (
        <BaseCard
            id={id}
            className={className}
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
        </BaseCard>
    );
};
