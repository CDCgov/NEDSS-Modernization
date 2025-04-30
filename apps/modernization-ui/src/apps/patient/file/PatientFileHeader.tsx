import { Icon } from 'design-system/icon';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import styles from './patient-file.module.scss';
import { Button } from 'components/button';
import { displayName } from 'name';
import { NoData } from 'components/NoData';
import { displayAgeAsOfToday, internalizeDate } from 'date';
import { usePatientFile } from './patientData/usePatientFile';

interface PatientFileHeaderProps {
    id: string;
}

const RenderAge = (props: { birthday?: Date }) => {
    const { birthday } = props;
    const value = birthday && `${internalizeDate(birthday)} (${displayAgeAsOfToday(birthday)})`;
    return value || <NoData />;
};

export const PatientFileHeader = ({ id }: PatientFileHeaderProps) => {
    const { summary } = usePatientFile(id);

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
                <div className={styles.actions}>
                    <Button
                        className={styles['usa-button']}
                        aria-label="Delete"
                        data-tooltip-position="top"
                        data-tooltip-offset="center"
                        icon={<Icon name="delete" />}
                        sizing={'medium'}
                        secondary
                        disabled
                    />
                    <Button
                        aria-label="Print"
                        data-tooltip-position="top"
                        data-tooltip-offset="center"
                        icon={<Icon name="print" />}
                        sizing={'medium'}
                        secondary
                    />
                    <Button
                        aria-label="Edit"
                        icon={<Icon name="edit" />}
                        secondary
                        labelPosition="right"
                        sizing={'medium'}>
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
