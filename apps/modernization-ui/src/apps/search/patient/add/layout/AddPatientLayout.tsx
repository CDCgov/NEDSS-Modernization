import { ReactNode } from 'react';
import styles from './add-layout.module.scss';
import { AddPatientSideNavigation } from './sideNavigation';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';
import { AddPatientContent } from './AddPatientContent';

interface AddPatientLayoutProps {
    headerActions: ReactNode;
    headerTitle: string;
    formContent: ReactNode;
    inPageSections: NavSection[];
    children?: ReactNode;
}

export const AddPatientLayout = ({
    headerActions,
    headerTitle,
    formContent,
    inPageSections,
    children
}: AddPatientLayoutProps) => {
    return (
        <div className={styles.addLayout}>
            <AddPatientSideNavigation />
            <AddPatientContent
                headerActions={headerActions}
                headerTitle={headerTitle}
                formContent={formContent}
                inPageSections={inPageSections}
            />
            {children}
        </div>
    );
};
