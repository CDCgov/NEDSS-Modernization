import { ReactNode } from 'react';
import styles from './add-layout.module.scss';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';
import { AddPatientLayout } from './AddPatientLayout';
import { DataEntryMenu } from './DataEntryMenu';

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
            <DataEntryMenu>
                {/* Data entry menu goes here */}
                New patient
            </DataEntryMenu>
            <AddPatientLayout headerActions={headerActions} headerTitle={headerTitle} sections={sections}>
                {entryComponent}
            </AddPatientLayout>
            {children}
        </div>
    );
};
