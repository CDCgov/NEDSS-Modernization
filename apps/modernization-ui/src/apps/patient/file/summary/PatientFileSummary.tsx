import { CollapsibleCard } from 'design-system/card';

import styles from './patient-file-summary.module.scss';
import { Key, ReactNode } from 'react';
import { DisplayableAddress, DisplayableIdentification, DisplayablePhone, PatientDemographicsSummary } from 'generated';
import { usePatient } from '../usePatient';
import { usePatientFileSummary } from '../usePatientFileSummary';
import { displayAddress } from 'address/display';
import { displayPhone } from '../phone/displayPhone';
import { NoData } from 'components/NoData';
import { displayIdentification } from '../identification/displayIdentification';

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

const maybeRenderAddress = (address?: DisplayableAddress) => (
    <div className={styles.itemContent}>{displayAddress(address ?? {})}</div>
);

const maybeRenderPhone = (phone?: DisplayablePhone) => <div className={styles.itemContent}>{displayPhone(phone)}</div>;

const maybeRenderEmail = (email?: string) => <div className={styles.itemContent}>{email ? email : <NoData />}</div>;

const maybeRenderIdentification = (identifications?: Array<DisplayableIdentification>) => (
    <div className={styles.itemContent}>
        {(identifications?.length ?? 0 > 0) ? displayIdentification(identifications) : <NoData />}
    </div>
);

const maybeRenderRace = (races?: Array<string>) => (
    <>{(races?.length ?? 0 > 0) ? races?.map((race) => race).join(', ') : <NoData />}</>
);

const SummaryContent = ({ summary }: SummaryContentProps) => {
    return (
        <div className={styles.content}>
            <SummaryItem label="ADDRESS">{maybeRenderAddress(summary?.address)}</SummaryItem>
            <div className={styles.group}>
                <SummaryItem label="PHONE">{maybeRenderPhone(summary?.phone)}</SummaryItem>
                <SummaryItem label="EMAIL">{maybeRenderEmail(summary?.email)}</SummaryItem>
            </div>
            <SummaryItem label="IDENTIFICATION">{maybeRenderIdentification(summary?.identifications)}</SummaryItem>
            <div className={styles.group}>
                <SummaryItem label="RACE">{maybeRenderRace(summary?.races)}</SummaryItem>
                <SummaryItem label="ETHNICITY">{summary?.ethnicity ? summary.ethnicity : <NoData />}</SummaryItem>
            </div>
        </div>
    );
};

export const PatientFileSummary = () => {
    const { id } = usePatient();

    const { summary } = usePatientFileSummary(id);
    return (
        <body>
            <CollapsibleCard header={'Patient Summary'} id={'summary-card'}>
                <SummaryContent summary={summary} />
            </CollapsibleCard>
        </body>
    );
};
