import { ReactNode } from 'react';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import styles from './patient-file.module.scss';

import { displayName } from 'name';
import { NoData } from 'components/NoData';
import { displayAgeAsOfToday, internalizeDate } from 'date';
import { usePatientFileContext } from './PatientFileContext';

interface PatientFileHeaderProps {
    id: string;
    headerActions: () => ReactNode;
}

const RenderAge = (props: { birthday?: Date }) => {
    const { birthday } = props;
    const value = birthday && `${internalizeDate(birthday)} (${displayAgeAsOfToday(birthday)})`;
    return value || <NoData />;
};

export const PatientFileHeader = ({ id, headerActions }: PatientFileHeaderProps) => {
    const { summary } = usePatientFileContext();

    return (
        <header className={styles.header}>
            <div className={styles.headerContent}>
                <h1 className={styles.headerContentTitle}>
                    <span className={styles.headerPatientName}>
                        {(summary?.legalName && displayName('fullLastFirst')(summary.legalName)) ?? '---'}
                    </span>
                    <span className={styles.headerPatientDivider}> | </span>
                    <span className={styles.headerPatientDetail}>{summary?.gender || <NoData />}</span>
                    <span className={styles.headerPatientDivider}> | </span>
                    <span className={styles.headerPatientDetail}>
                        <RenderAge birthday={summary?.birthday} />
                    </span>
                    <span className={styles.headerPatientDivider}> | </span>
                    <span className={styles.headerPatientDetail}>Patient ID: {id}</span>
                </h1>
                <div className={styles.actions}>{headerActions()}</div>
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
