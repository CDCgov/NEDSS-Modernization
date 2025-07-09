import { ReactNode } from 'react';
import { NoData } from 'design-system/data';
import { Card, CardProps } from 'design-system/card';
import { ItemGroup } from 'design-system/item';
import { DisplayableAddress, displayAddress } from 'address/display';
import { PatientFileDemographicsSummary, DisplayablePhone, DisplayableIdentification } from '.';

import styles from './patient-file-summary.module.scss';

type SummaryItemProps = {
    label: string;
    children: ReactNode;
};

const displayPhone = (phone?: DisplayablePhone) => {
    return (
        <ItemGroup type="phone" label={phone?.use}>
            {phone?.number}
        </ItemGroup>
    );
};

const SummaryItem = ({ label, children }: SummaryItemProps) => (
    <div className={styles.summaryItem}>
        <span className={styles.summaryLabel}>{label}</span>
        <span className={styles.summaryContent}>{children}</span>
    </div>
);

const maybeRenderAddress = (address?: DisplayableAddress) => (
    <div className={styles.itemContent}>{displayAddress(address ?? {})}</div>
);

const maybeRenderPhone = (phone?: DisplayablePhone) => <div className={styles.itemContent}>{displayPhone(phone)}</div>;

const maybeRenderEmail = (email?: string) => (
    <div className={styles.itemContent}>
        <ItemGroup>{email}</ItemGroup>
    </div>
);

const displayIdentification = (identifications?: Array<DisplayableIdentification>) => {
    return (
        <>
            {identifications?.map((id, key) => (
                <ItemGroup key={key} label={id.type}>
                    {id?.value}
                </ItemGroup>
            ))}
        </>
    );
};

const maybeRenderIdentification = (identifications?: DisplayableIdentification[]) => (
    <div className={styles.itemContent}>
        {(identifications?.length ?? 0 > 0) ? displayIdentification(identifications) : <NoData />}
    </div>
);

const maybeRenderRace = (races?: string[]) => <ItemGroup>{races?.map((race) => race).join(', ')}</ItemGroup>;

const maybeRenderEthnicity = (ethnicity?: string) => <ItemGroup>{ethnicity}</ItemGroup>;

type PatientDemographicsSummaryCardProps = {
    title?: string;
    summary?: PatientFileDemographicsSummary;
} & Omit<CardProps, 'subtext' | 'children' | 'title'>;

const PatientDemographicsSummaryCard = ({
    title = 'Patient summary',
    summary,
    collapsible = true,
    sizing,
    ...remaining
}: PatientDemographicsSummaryCardProps) => {
    return (
        <Card title={title} sizing={sizing} collapsible={collapsible} {...remaining}>
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

export { PatientDemographicsSummaryCard };
export type { PatientDemographicsSummaryCardProps };
