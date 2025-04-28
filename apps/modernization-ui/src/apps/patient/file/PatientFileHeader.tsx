import { Heading } from 'components/heading';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import styles from './patient-file.module.scss';

interface PatientFileHeaderProps {
    id: string;
}

export const PatientFileHeader = ({ id }: PatientFileHeaderProps) => {
    return (
        <header className={styles.header}>
            <head className={styles.headerContent}>
                <Heading level={1}>Patient ID: {id}</Heading>
            </head>
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
