import { ReactNode } from 'react';
import { Patient } from './patient';
import { PatientDescriptor } from 'libs/patient';

import styles from './patient-file-header.module.scss';

type PatientFileHeaderProps = {
    patient: Patient;
    actions: ReactNode;
};

export const PatientFileHeader = ({ patient, actions }: PatientFileHeaderProps) => {
    return (
        <header className={styles.header}>
            <PatientDescriptor headingLevel={1} patient={patient} />
            <div className={styles.actions}>{actions}</div>
        </header>
    );
};
