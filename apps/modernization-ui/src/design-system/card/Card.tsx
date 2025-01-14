import { Heading, HeadingLevel } from 'components/heading';
import { ReactNode } from 'react';
import styles from './card.module.scss';

type Props = {
    id: string;
    title: ReactNode;
    info?: ReactNode;
    level?: HeadingLevel;
    children: ReactNode;
};
export const Card = ({ id, title, info, children, level = 2 }: Props) => {
    return (
        <section id={id} className={styles.card}>
            <header>
                <Heading level={level}>{title}</Heading>
                {info}
            </header>
            {children}
        </section>
    );
};
