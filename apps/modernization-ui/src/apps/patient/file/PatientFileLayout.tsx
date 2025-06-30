import { ReactNode } from 'react';
import { Patient } from './patient';
import { PatientFileHeader } from './PatientFileHeader';
import { ComponentSizingProvider } from 'design-system/sizing';

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
                <main>{children}</main>
            </div>
        </ComponentSizingProvider>
    );
};

export { PatientFileLayout };
