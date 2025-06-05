import { displayAddress } from 'address/display';
import { NoData } from 'components/NoData';
import { Card } from 'design-system/card';
import { ItemGroup } from 'design-system/item';
import { PatientDemographicsSummary, DisplayableAddress, DisplayablePhone, DisplayableIdentification } from 'generated';
import { ReactNode } from 'react';
import { displayIdentification } from '../identification/displayIdentification';
import { displayPhone } from '../phone/displayPhone';
import styles from './patient-file-summary.module.scss';

type SummaryItemProps = {
    label: string;
    children: ReactNode;
};

const SummaryItem = ({ label, children }: SummaryItemProps) => (
    <div className={styles.summaryItem}>
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

const maybeRenderEmail = (email?: string) => (
    <div className={styles.itemContent}>
        <ItemGroup>{email}</ItemGroup>
    </div>
);

const maybeRenderIdentification = (identifications?: Array<DisplayableIdentification>) => (
    <div className={styles.itemContent}>
        {(identifications?.length ?? 0 > 0) ? displayIdentification(identifications) : <NoData display="dashes" />}
    </div>
);

const maybeRenderRace = (races?: Array<string>) => <ItemGroup>{races?.map((race) => race).join(', ')}</ItemGroup>;

const maybeRenderEthnicity = (ethnicity?: string) => <ItemGroup>{ethnicity}</ItemGroup>;

export const PatientSummaryContent = ({ summary }: SummaryContentProps) => {
    return (
        <Card title={'Patient summary'} id={'summary-card'} collapsible={true}>
            <div className={styles.content}>
                <div className={styles.group}>
                    <SummaryItem label="ADDRESS">{maybeRenderAddress(summary?.address)}</SummaryItem>
                </div>
                <div className={styles.group}>
                    <SummaryItem label="PHONE">{maybeRenderPhone(summary?.phone)}</SummaryItem>
                    <SummaryItem label="EMAIL">{maybeRenderEmail(summary?.email)}</SummaryItem>
                </div>
                <div className={styles.groupID}>
                    <SummaryItem label="IDENTIFICATION">
                        {maybeRenderIdentification(summary?.identifications)}
                    </SummaryItem>
                </div>
                <div className={styles.group}>
                    <SummaryItem label="RACE">{maybeRenderRace(summary?.races)}</SummaryItem>
                    <SummaryItem label="ETHNICITY">{maybeRenderEthnicity(summary?.ethnicity)}</SummaryItem>
                </div>
            </div>
        </Card>
    );
};
