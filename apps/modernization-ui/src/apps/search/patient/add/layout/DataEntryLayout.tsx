import { ReactNode } from 'react';
import styles from './add-layout.module.scss';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';
import { AddPatientLayout } from './AddPatientLayout';

type DataEntryLayoutProps = {
    headerActions: ReactNode;
    headerTitle: string;
    sections: NavSection[];
    children?: ReactNode;
    entryComponent: ReactNode;
};

export const DataEntryLayout = ({
    headerActions,
    headerTitle,
    sections,
    entryComponent,
    children
}: DataEntryLayoutProps) => {
    return (
        <div className={styles.addLayout}>
            {/* Data entry menu goes here */}
            <AddPatientLayout headerActions={headerActions} headerTitle={headerTitle} sections={sections}>
                {entryComponent}
            </AddPatientLayout>
            {children}
        </div>
    );
};
