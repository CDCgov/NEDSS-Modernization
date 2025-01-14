import './PatientProfileSummary.scss';

import { ReactNode, Key } from 'react';
import {
    PatientSummary,
    PatientSummaryAddress,
    PatientSummaryEmail,
    PatientSummaryIdentification,
    PatientSummaryPhone
} from 'generated/graphql/schema';
import { Loading } from 'components/Spinner';
import { internalizeDate } from 'date';
import { Patient } from 'apps/patient/profile';
import { displayAddressText } from 'address/display';
import { NoData } from 'components/NoData';
import { displayName } from 'name';
import { useProfileContext } from '../ProfileContext';
import { displayAgeAsOfToday } from 'date/displayAge';

type Props = {
    patient?: Patient;
    summary?: PatientSummary;
};

type Renderer<T> = (value: T) => ReactNode;

const asNothing = () => undefined;
const asNoData = () => <NoData />;

const maybeRender = <T,>(value: T | null | undefined, renderer: Renderer<T>, fallback: () => ReactNode = asNoData) => {
    if (Array.isArray(value) && value.length > 0) {
        return renderer(value);
    } else if (value && !Array.isArray(value)) {
        return renderer(value);
    } else {
        return fallback();
    }
};

const asText = (value: string) => <p className="patient-summary-item-value">{value}</p>;
const allAsText = (items: string[]) => asText(items.join('\n'));

const asBirthday = (summary: PatientSummary) => {
    const value = summary.birthday && `${internalizeDate(summary.birthday)} (${displayAgeAsOfToday(summary.birthday)})`;
    return maybeRender(value, asText);
};

const asPhones = (items: PatientSummaryPhone[]) => asText(items.map((items) => items.number).join('\n'));

const asEmails = (items: PatientSummaryEmail[]) => asText(items.map((item) => item.address).join('\n'));

const asAddress = (address: PatientSummaryAddress) => {
    const value = displayAddressText(address);
    return maybeRender(value, asText);
};

const asIdentifications = (identifications: PatientSummaryIdentification[]) => (
    <div className="stacked">
        {identifications.map((id, key) => (
            <SummaryItem key={key} label={id.type}>
                {asText(id.value)}
            </SummaryItem>
        ))}
    </div>
);

type SummaryItemProps = {
    index?: Key;
    label: string;
    children: ReactNode;
};

const SummaryItem = ({ index, label, children }: SummaryItemProps) => (
    <div key={index} className="patient-summary-item">
        <span className="patient-summary-item-label">{label}</span>
        {children}
    </div>
);

export const PatientProfileSummary = ({ patient }: Props) => {
    const { summary } = useProfileContext();

    return (
        <div className="common-card patient-summary">
            {!patient || !summary ? (
                <div className="text-center margin-y-6">
                    <Loading />
                </div>
            ) : (
                <>
                    <div className="border-bottom border-base-lighter patient-summary-title">
                        <h2>{(summary?.legalName && displayName('fullLastFirst')(summary.legalName)) ?? '---'}</h2>
                        <span>Patient ID: {patient.shortId}</span>
                        {patient.status != 'ACTIVE' && <span className="text-red">{patient.status}</span>}
                    </div>
                    <div className="patient-summary-items">
                        <div className="grouped">
                            <SummaryItem label="Current sex">{maybeRender(summary.gender, asText)}</SummaryItem>
                            <SummaryItem label="Phone"> {maybeRender(summary.phone, asPhones)}</SummaryItem>
                            <SummaryItem label={addressLabel(summary.home) ?? 'Address'}>
                                {maybeRender(summary.home ?? summary.address[0], asAddress)}
                            </SummaryItem>
                            <SummaryItem label="Race">{maybeRender(summary.races, allAsText)}</SummaryItem>
                            <SummaryItem label="Date of birth">{asBirthday(summary)}</SummaryItem>
                            <SummaryItem label="Email">{maybeRender(summary.email, asEmails)}</SummaryItem>
                            <div className="patient-summary-item" />
                            <SummaryItem label="Ethnicity">{maybeRender(summary.ethnicity, asText)}</SummaryItem>
                        </div>
                        {maybeRender(summary.identification, asIdentifications, asNothing)}
                    </div>
                </>
            )}
        </div>
    );
};

const addressLabel = (address?: PatientSummaryAddress | null) => (address ? `Address (${address.use})` : 'Address');
