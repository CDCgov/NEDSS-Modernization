import { usePatient } from '../usePatient';
import { usePatientFileSummary } from './usePatientFileSummary';
import { PatientSummaryContent } from './PatientSummaryContent';
import { PatientDocumentRequiringReview } from './PatientDocumentRequiringReview';

import styles from './patient-file-summary.module.scss';

export const PatientFileSummary = () => {
    const { id } = usePatient();

    const { summary } = usePatientFileSummary(id);
    return (
        <body className={styles.summary}>
            <PatientSummaryContent summary={summary} />
            <PatientDocumentRequiringReview />
        </body>
    );
};
