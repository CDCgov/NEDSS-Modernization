import { ReactNode } from 'react';
import { BackToTop } from 'libs/page/back-to-top';
import { Sizing } from 'design-system/field';
import { Patient } from './patient';
import { PatientFileHeader } from './PatientFileHeader';

import styles from './patient-file-layout.module.scss';

type PatientFileLayoutProps = {
    patient: Patient;
    actions: (patient: Patient) => ReactNode;
    navigation: (patient: Patient) => ReactNode | ReactNode[];
    sizing?: Sizing;
    children: ReactNode | ReactNode[];
};

const PatientFileLayout = ({ patient, actions, navigation, sizing, children }: PatientFileLayoutProps) => {
    return (
        <div className={styles.file}>
            <header>
                <PatientFileHeader patient={patient} actions={actions(patient)} />
                <nav>{navigation(patient)}</nav>
            </header>
            <main>
                {children}
                <BackToTop sizing={sizing} />
            </main>
        </div>
    );
};

export { PatientFileLayout };
