import { usePatient } from '../usePatient';
import { usePatientFileSummary } from './usePatientFileSummary';
import { PatientSummaryContent } from './PatientSummaryContent';
import OpenInvestigationsCard from './openInvestigations/OpenInvestigationsCard';
import styles from './patient-file-summary.module.scss';

export const PatientFileSummary = () => {
    const { id } = usePatient();

    const { summary } = usePatientFileSummary(id);
    return (
        <div className={styles['summary-layout']}>
            <PatientSummaryContent summary={summary} />
            <OpenInvestigationsCard />
        </div>
    );
};
