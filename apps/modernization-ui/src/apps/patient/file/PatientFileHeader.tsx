import { ReactNode } from 'react';

import { PatientDescriptor } from 'libs/patient';

import { Patient } from './patient';
import styles from './patient-file-header.module.scss';

type PatientFileHeaderProps = {
    patient: Patient;
    actions: ReactNode;
};

export const PatientFileHeader = ({ patient, actions }: PatientFileHeaderProps) => {
    return (
        <div className={styles.header}>
            <PatientDescriptor headingLevel={1} patient={patient} />
            <div className={styles.actions}>{actions}</div>
        </div>
    );
};
