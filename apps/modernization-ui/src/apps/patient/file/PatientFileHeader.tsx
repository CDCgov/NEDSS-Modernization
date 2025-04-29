import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import styles from './patient-file.module.scss';

interface PatientFileHeaderProps {
    id: string;
}

export const PatientFileHeader = ({ id }: PatientFileHeaderProps) => {
    return (
        <header className={styles.header}>
            <div className={styles.headerContent}>
                <div className={styles.headerContentTitle}>
                    <p>Smith, John</p>
                    <p>Male</p>
                    <p>01/07/1972 (57 years)</p>
                    <p>Patient ID: {id}</p>
                </div>
            </div>
            <div className={styles.tabNavigation}>
                <TabNavigation className="grid-row flex-align-center" newTab>
                    <TabNavigationEntry path={`/patient/${id}/summary`}>Summary</TabNavigationEntry>
                    <TabNavigationEntry path={`/patient/${id}/events`}>Events</TabNavigationEntry>
                    <TabNavigationEntry path={`/patient/${id}/demographics`}>Demographics</TabNavigationEntry>
                </TabNavigation>
            </div>
        </header>
    );
};
