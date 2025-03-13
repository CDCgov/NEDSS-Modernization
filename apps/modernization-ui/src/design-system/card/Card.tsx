import { Heading, HeadingLevel } from 'components/heading';
import { ReactNode } from 'react';
import styles from './card.module.scss';

type Props = {
    id: string;
    title: ReactNode;
    info?: ReactNode;
    subtext?: string;
    level?: HeadingLevel;
    children: ReactNode;
};
export const Card = ({ id, title, info, subtext, children, level = 2 }: Props) => {
    const cardClassName = subtext ? `${styles.card} ${styles.withSubtext}` : styles.card;

    return (
        <section id={id} className={cardClassName}>
            <header>
                <Heading level={level}>{title}</Heading>
                {info}
                {subtext && <div className={styles.subtext}>{subtext}</div>}
            </header>
            {children}
        </section>
    );
};
