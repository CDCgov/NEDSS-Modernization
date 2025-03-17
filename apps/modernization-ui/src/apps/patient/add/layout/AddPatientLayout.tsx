import { ReactNode } from 'react';
import { AddPatientHeaderContent } from './headerContent/AddPatientHeaderContent';
import { InPageNavigation, NavSection } from 'design-system/inPageNavigation/InPageNavigation';

import styles from './add-patient-layout.module.scss';

type AddPatientLayoutProps = {
    headerActions: () => ReactNode;
    headerTitle: string;
    sections: NavSection[];
    children: ReactNode;
};

export const AddPatientLayout = ({ headerActions, headerTitle, sections, children }: AddPatientLayoutProps) => {
    return (
        <div className={styles.layout}>
            <AddPatientHeaderContent title={headerTitle}>{headerActions()}</AddPatientHeaderContent>
            <main>
                <div className={styles.content}>{children}</div>
                <aside>
                    <InPageNavigation sections={sections} />
                </aside>
            </main>
        </div>
    );
};
