import { ReactNode } from 'react';
import { Patient } from './patient';
import { PatientProvider } from './usePatient';
import { PatientFileHeader } from './PatientFileHeader';

import styles from './patient-file-layout.module.scss';
import { ComponentSizingProvider } from 'design-system/sizing';

type PatientFileLayoutProps = {
    patient: Patient;
    actions: (patient: Patient) => ReactNode;
    navigation: (patient: Patient) => ReactNode | ReactNode[];
    children: ReactNode | ReactNode[];
};

const PatientFileLayout = ({ patient, actions, navigation, children }: PatientFileLayoutProps) => {
    return (
        <ComponentSizingProvider>
            <PatientProvider patient={patient}>
                <div className={styles.file}>
                    <header>
                        <PatientFileHeader patient={patient} actions={actions(patient)} />
                        <nav>{navigation(patient)}</nav>
                    </header>
                    <main>{children}</main>
                </div>
            </PatientProvider>
        </ComponentSizingProvider>
    );
};

export { PatientFileLayout };
