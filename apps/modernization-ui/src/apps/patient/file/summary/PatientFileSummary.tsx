import { CollapsibleCard } from 'design-system/card';

import styles from './patient-file-summary.module.scss';
import { Key, ReactNode } from 'react';
import { usePatientFileContext } from '../PatientFileContext';
import { PatientDemographicsSummary } from 'generated';

type SummaryItemProps = {
    index?: Key;
    label: string;
    children: ReactNode;
};

const SummaryItem = ({ index, label, children }: SummaryItemProps) => (
    <div key={index} className={styles.summaryItem}>
        <span className={styles.summaryLabel}>{label}</span>
        <span className={styles.summaryContent}>{children}</span>
    </div>
);

type SummaryContentProps = {
    summary?: PatientDemographicsSummary;
};

const SummaryContent = ({ summary }: SummaryContentProps) => {
    return (
        <div className={styles.content}>
            <SummaryItem label="ADDRESS">{summary?.address?.address2}</SummaryItem>
            <div className={styles.group}>
                <SummaryItem label="PHONE">1112223333</SummaryItem>
                <SummaryItem label="EMAIL">test@test.com</SummaryItem>
            </div>
            <SummaryItem label="IDENTIFICATION">PID</SummaryItem>
            <div className={styles.group}>
                <SummaryItem label="RACE">Asian, American</SummaryItem>
                <SummaryItem label="ETHNICITY">Race</SummaryItem>
            </div>
        </div>
    );
};

export const PatientFileSummary = () => {
    const { summary } = usePatientFileContext();

    return (
        <body>
            <CollapsibleCard header={'Patient Summary'} id={'summary-card'}>
                <SummaryContent summary={summary} />
            </CollapsibleCard>
        </body>
    );
};
