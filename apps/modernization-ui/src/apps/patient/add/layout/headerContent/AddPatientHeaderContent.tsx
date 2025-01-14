import { ReactNode } from 'react';
import styles from './add-patient-header-content.module.scss';

type HeaderContentProps = {
    title: string;
    children: ReactNode;
};

export const AddPatientHeaderContent = ({ title, children }: HeaderContentProps) => {
    return (
        <header className={styles.header}>
            <h1>{title}</h1>
            {children}
        </header>
    );
};
