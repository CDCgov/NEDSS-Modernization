import './style.scss';

import {
    PatientSummary,
    PatientSummaryAddress,
    PatientSummaryEmail,
    PatientSummaryIdentification,
    PatientSummaryPhone
} from 'generated/graphql/schema';
import { Patient } from 'pages/patient/profile';
import { NoData } from 'components/NoData';

import { Spinner } from '@cmsgov/design-system';
import { formattedName } from 'utils';
import { ReactNode, Key } from 'react';
import { internalizeDate } from 'date';

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
    const value = summary.birthday && `${internalizeDate(summary.birthday)} (${summary.age} years)`;
    return maybeRender(value, asText);
};

const asPhones = (items: PatientSummaryPhone[]) => asText(items.map((items) => items.number).join('\n'));

const asEmails = (items: PatientSummaryEmail[]) => asText(items.map((item) => item.address).join('\n'));

const asAddress = ({ street, city, state, zipcode }: PatientSummaryAddress) => {
    const location = [city, state, zipcode].filter((i) => i).join(' ');
    const address = [location, street].filter((i) => i).join('\n');
    return maybeRender(address, asText);
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
    key?: Key;
    label: string;
    children: ReactNode;
};

const SummaryItem = ({ key, label, children }: SummaryItemProps) => (
    <div key={key} className="patient-summary-item">
        <span className="patient-summary-item-label">{label}</span>
        {children}
    </div>
);

export const PatientProfileSummary = ({ patient, summary }: Props) => {
    return (
        <div className="common-card patient-summary">
            {!patient || !summary ? (
                <div className="text-center margin-y-6">
                    <Spinner className="sortable-table-spinner" />
                </div>
            ) : (
                <>
                    <div className="border-bottom border-base-lighter patient-summary-title">
                        <h2>{`${formattedName(
                            `${summary?.legalName?.first || ''} ${summary?.legalName?.middle || ''}  ${
                                summary?.legalName?.last || ''
                            }`,
                            summary?.legalName?.suffix
                        )}`}</h2>
                        <span>
                            Patient ID: {patient.shortId}
                            {patient.status != 'ACTIVE' && (
                                <span className="text-red text-right margin-left-2">
                                    {patient.status === 'LOG_DEL' ? 'INACTIVE' : patient.status}
                                </span>
                            )}
                        </span>
                    </div>
                    <div className="patient-summary-items">
                        <div className="grouped">
                            <SummaryItem label="Sex">{maybeRender(summary.gender, asText)}</SummaryItem>
                            <SummaryItem label="Phone"> {maybeRender(summary.phone, asPhones)}</SummaryItem>
                            <SummaryItem label="Address">{maybeRender(summary.address, asAddress)}</SummaryItem>
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
