import { ReactNode } from 'react';
import styles from './accordion.module.scss';

type Props = {
    title?: string;
    children: ReactNode;
    open?: boolean;
};

export const Accordion = ({ title, children, open }: Props) => {
    return (
        <details className={styles.accordian} {...(open && { open: true })}>
            <summary>
                <h3>{title}</h3> <span></span>
            </summary>
            <div className={styles.content}>{children}</div>
        </details>
    );
};