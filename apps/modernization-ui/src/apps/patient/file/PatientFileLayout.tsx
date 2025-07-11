import { ReactNode } from 'react';
import { BackToTop } from 'libs/page/back-to-top';
import { ComponentSizing, ComponentSizingProvider } from 'design-system/sizing';
import { PatientFileHeader } from './PatientFileHeader';
import { Patient } from './patient';

import styles from './patient-file-layout.module.scss';

type PatientFileLayoutProps = {
    patient: Patient;
    actions: (patient: Patient) => ReactNode;
    navigation: (patient: Patient) => ReactNode | ReactNode[];
    children: ReactNode | ReactNode[];
};

const PatientFileLayout = ({ patient, actions, navigation, children }: PatientFileLayoutProps) => {
    return (
        <ComponentSizingProvider>
            <div className={styles.file}>
                <header>
                    <PatientFileHeader patient={patient} actions={actions(patient)} />
                    <nav>{navigation(patient)}</nav>
                </header>
                <main>
                    {children}
                    <ComponentSizing>{(sizing) => <BackToTop sizing={sizing} />}</ComponentSizing>
                </main>
            </div>
        </ComponentSizingProvider>
    );
};

export { PatientFileLayout };
