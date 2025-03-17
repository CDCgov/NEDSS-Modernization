import { ReactNode } from 'react';
import { DataEntryMenu } from '../DataEntryMenu';

import styles from './data-entry-layout.module.scss';

type DataEntryLayoutProps = {
    children?: ReactNode;
};

export const DataEntryLayout = ({ children }: DataEntryLayoutProps) => {
    return (
        <div className={styles.layout}>
            <DataEntryMenu />
            <div className={styles.content}>{children}</div>
        </div>
    );
};
