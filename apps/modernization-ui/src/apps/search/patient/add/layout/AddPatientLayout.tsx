import { ReactNode } from 'react';
import styles from './add-layout.module.scss';
import { AddPatientSideNavigation } from './sideNavigation/AddPatientSideNavigation';
import { AddPatientHeaderContent } from './headerContent/AddPatientHeaderContent';
import { AddPatientInPageNavigation } from './inPageNavigation/AddPatientInPageNavigation';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';

interface AddPatientLayoutProps {
    headerActions: ReactNode;
    headerTitle: string;
    patientForm: ReactNode;
    inPageSections: NavSection[];
    inPageTitle: string;
    children?: ReactNode;
}

export const AddPatientLayout = ({
    headerActions,
    headerTitle,
    patientForm,
    inPageTitle,
    inPageSections,
    children
}: AddPatientLayoutProps) => {
    return (
        <div className={styles.addLayout}>
            <AddPatientSideNavigation />
            <div className={styles.content}>
                <AddPatientHeaderContent title={headerTitle}>{headerActions}</AddPatientHeaderContent>
                <main>
                    {patientForm}
                    <AddPatientInPageNavigation title={inPageTitle} sections={inPageSections} />
                </main>
            </div>
            {children}
        </div>
    );
};
