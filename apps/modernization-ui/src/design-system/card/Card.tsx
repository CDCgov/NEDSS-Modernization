import { Heading, HeadingLevel } from 'components/heading';
import { ReactNode } from 'react';
import styles from './card.module.scss';

type Props = {
    id: string;
    title: ReactNode;
    info?: ReactNode;
    subtext?: ReactNode;
    level?: HeadingLevel;
    children: ReactNode;
};
export const Card = ({ id, title, info, subtext, children, level = 2 }: Props) => {
    return (
        <section id={id} className={styles.card}>
            <header>
                <div className={styles.titleWrapper}>
                    <Heading level={level}>{title}</Heading>
                    {subtext && <p className={styles.subtext}>{subtext}</p>}
                </div>
                {info}
            </header>
            {children}
        </section>
    );
};
