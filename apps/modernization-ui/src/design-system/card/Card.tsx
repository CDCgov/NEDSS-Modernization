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
    return (
        <section id={id} className={styles.card}>
            <header>
                <div>
                    <Heading level={level}>{title}</Heading>
                    {subtext && <div className={styles.subtext}>{subtext}</div>}
                </div>
                {info}
            </header>
            {children}
        </section>
    );
};
