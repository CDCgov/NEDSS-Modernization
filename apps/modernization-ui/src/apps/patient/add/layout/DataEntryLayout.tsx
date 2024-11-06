import { ReactNode } from 'react';
import styles from './add-layout.module.scss';
import { DataEntryMenu } from '../DataEntryMenu';

type DataEntryLayoutProps = {
    children?: ReactNode;
};

export const DataEntryLayout = ({ children }: DataEntryLayoutProps) => {
    return (
        <div className={styles.addLayout}>
            <DataEntryMenu />
            {children}
        </div>
    );
};
