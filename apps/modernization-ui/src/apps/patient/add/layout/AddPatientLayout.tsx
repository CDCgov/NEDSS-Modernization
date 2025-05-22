import { ReactNode } from 'react';
import { InPageNavigation, NavSection } from 'design-system/inPageNavigation/InPageNavigation';

import styles from './add-patient-layout.module.scss';
import { DataEntryLayout } from 'libs/data-entry';
import { Heading } from 'components/heading';

type AddPatientLayoutProps = {
    actions: () => ReactNode | ReactNode[];
    title: string;
    sections: NavSection[];
    children: ReactNode;
};

export const AddPatientLayout = ({ actions, title, sections, children }: AddPatientLayoutProps) => {
    return (
        <DataEntryLayout>
            <div className={styles.layout}>
                <header className={styles.header}>
                    <Heading level={1}>{title}</Heading>
                </header>
                <main>
                    <aside>
                        <InPageNavigation sections={sections} />
                    </aside>
                    <div className={styles.content}>{children}</div>
                </main>
                <div className={styles.actions}>{actions()}</div>
            </div>
        </DataEntryLayout>
    );
};
