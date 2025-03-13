import styles from './DataElemetsCard.module.scss';
import { ReactNode } from 'react';

interface DataElementsCardProps {
    id?: string;
    title: string;
    subtext?: string;
    children: ReactNode;
}

export const DataElementsCard = ({ id, title, subtext, children }: DataElementsCardProps) => {
    return (
        <div id={id} className={styles.card}>
            <div className={styles.cardHeader}>
                <h2 className={styles.cardTitle}>{title}</h2>
                {subtext && <p className={styles.cardSubtext}>{subtext}</p>}
            </div>
            <div className={styles.cardContent}>{children}</div>
        </div>
    );
};
