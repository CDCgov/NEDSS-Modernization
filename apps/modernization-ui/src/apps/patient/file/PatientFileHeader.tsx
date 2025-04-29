import { Icon } from 'design-system/icon';
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
                    <span className={styles.headerPatientName}>Smith, John</span>
                    <span className={styles.headerPatientDivider}> | </span>
                    <span className={styles.headerPatientDetail}>Male</span>
                    <span className={styles.headerPatientDivider}> | </span>
                    <span className={styles.headerPatientDetail}>01/07/1972 (57 years)</span>
                    <span className={styles.headerPatientDivider}> | </span>
                    <span className={styles.headerPatientDetail}>Patient ID: {id}</span>
                </div>
                <div className={styles.actions}>
                    <Button icon={<Icon name="delete" sizing="small" />} secondary disabled />
                    <Button icon={<Icon name="print" sizing="small" />} secondary />
                    <Button icon={<Icon name="edit" sizing="small" />} secondary labelPosition="right">
                        Edit
                    </Button>
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
