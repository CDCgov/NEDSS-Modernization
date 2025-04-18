import { useParams } from 'react-router';
import { PatientFileHeader } from './PatientFileHeader';

import styles from './patient-file.module.scss';

export const PatientFile = () => {
    const { id } = useParams();

    return (
        <div className={styles.file}>
            <PatientFileHeader id={id ?? ''} />
            <main className="main-body"></main>
        </div>
    );
};
