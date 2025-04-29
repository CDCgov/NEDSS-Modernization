import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import styles from './patient-file.module.scss';
import { Button } from 'design-system/button';

interface PatientFileHeaderProps {
    id: string;
}

export const PatientFileHeader = ({ id }: PatientFileHeaderProps) => {
    return (
        <header className={styles.header}>
            <div className={styles.headerContent}>
                <div className={styles.headerContentTitle}>
                    <p className={styles.headerPatientName}>Smith, John</p>
                    <p className={styles.headerPatientDivider}>|</p>
                    <p className={styles.headerPatientDetail}>Male</p>
                    <p className={styles.headerPatientDivider}>|</p>
                    <p className={styles.headerPatientDetail}>01/07/1972 (57 years)</p>
                    <p className={styles.headerPatientDivider}>|</p>
                    <p className={styles.headerPatientDetail}>Patient ID: {id}</p>
                </div>
                <div className={styles.actions}>
                    <Button type="button">Delete</Button>
                    <Button type="button">Print</Button>
                    <Button type="button">Edit</Button>
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
