import { ReactNode } from 'react';
import { AddPatientHeaderContent } from './headerContent/AddPatientHeaderContent';
import { AddPatientInPageNavigation } from './inPageNavigation/AddPatientInPageNavigation';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';
import styles from './add-layout.module.scss';

interface AddPatientContentProps {
    headerActions: ReactNode;
    headerTitle: string;
    formContent: ReactNode;
    inPageSections: NavSection[];
}

export const AddPatientContent = ({
    headerActions,
    headerTitle,
    formContent,
    inPageSections
}: AddPatientContentProps) => {
    return (
        <div className={styles.content}>
            <AddPatientHeaderContent title={headerTitle}>{headerActions}</AddPatientHeaderContent>
            <main>
                {formContent}
                <AddPatientInPageNavigation sections={inPageSections} />
            </main>
        </div>
    );
};
