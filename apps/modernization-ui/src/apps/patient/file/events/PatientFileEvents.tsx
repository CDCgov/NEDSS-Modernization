import { Investigations } from './investigations/Investigations';
import LabReports from './labReports/LabReports';
import styles from './patient-file-events.module.scss';

export const PatientFileEvents = () => {
    return (
        <div className={styles['events-layout']}>
            <Investigations />
            <LabReports />
        </div>
    );
};
