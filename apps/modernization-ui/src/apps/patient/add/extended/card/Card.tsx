import { Heading } from 'components/heading';
import { ReactNode } from 'react';
import styles from './Card.module.scss';

export const Card = ({ id, title, children }: { id: string; title: string; children: ReactNode }) => {
    return (
        <section id={id} className={styles.input}>
            <header>
                <Heading level={2}>{title}</Heading>
            </header>
            {children}
        </section>
    );
};
