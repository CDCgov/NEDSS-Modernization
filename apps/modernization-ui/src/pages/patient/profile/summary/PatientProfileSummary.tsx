import './style.scss';

import { Grid } from '@trussworks/react-uswds';
import { Patient } from 'pages/patient/profile';
import { Address, Email, Name, PatientSummary, Phone } from './PatientSummary';
import { NoData } from 'components/NoData';

type Props = {
    patient: Patient;
    summary: PatientSummary;
};

const display = (value: string) => <p>{value}</p>;
const noData = <NoData />;

const formattedPhones = (items: Phone[]) => display(items.map((items) => items.number).join('\n'));

const formattedEmails = (items: Email[]) => display(items.map((item) => item.address).join('\n'));

const formattedAddress = ({ street, city, state, zipcode }: Address) => {
    const location = ((city && city + ' ') || '') + ((state && state + ' ') || '') + (zipcode ?? '');
    const address =
        ((street && street + '\n') || '') + ((location && location + '\n') || '');

    return display(address);
};

const formattedName = (name: Name | null | undefined) => {
    return (name && [name.last, name.first].join(', ')) || '--';
};

export const PatientProfileSummary = ({ patient, summary }: Props) => {
    return (
        <div className="margin-y-2 flex-row common-card">
            <div className="grid-row flex-align-center flex-justify padding-2 border-bottom border-base-lighter">
                <p className="font-sans-xl text-bold margin-0">{`${formattedName(summary.legalName)}`}</p>
                <h5 className="font-sans-md text-medium margin-0">Patient ID: {patient.shortId}</h5>
            </div>
            <Grid row gap={3} className="padding-3">
                <Grid col={3}>
                    <Grid col={12} className=" summary-value">
                        <h5 className="margin-right-1">SEX</h5>
                        <p>{summary.gender || noData}</p>
                    </Grid>
                </Grid>

                <Grid col={3}>
                    <Grid col={12} className="summary-value">
                        <h5>PHONE NUMBER</h5>
                        {summary.phone && summary.phone.length > 0 ? formattedPhones(summary.phone) : noData}
                    </Grid>
                </Grid>

                <Grid col={3}>
                    <Grid col={12} className="summary-value">
                        <h5>ADDRESS</h5>
                        {summary.address ? formattedAddress(summary.address) : noData}
                    </Grid>
                </Grid>

                <Grid col={3}>
                    <Grid col={12} className="summary-value">
                        <h5 className="margin-right-1">RACE</h5>
                        {summary.race ? display(summary.race) : noData}
                    </Grid>
                </Grid>

                <Grid col={3}>
                    <Grid col={12} className="margin-top-3 summary-value">
                        <h5 className="margin-right-1">DATE OF BIRTH</h5>
                        <p>{summary.birthday ? `${summary.birthday} (${summary.age})` : noData}</p>
                    </Grid>
                </Grid>

                <Grid col={3}>
                    <Grid col={12} className="margin-top-3 summary-value">
                        <h5>EMAIL</h5>
                        {summary.email && summary.email.length > 0 ? formattedEmails(summary.email) : noData}
                    </Grid>
                </Grid>

                <Grid col={3}>
                    <Grid col={12} className="summary-value margin-top-3">
                        <h5 className="margin-right-1">ETHNICITY</h5>
                        {summary.ethnicity ? display(summary.ethnicity) : noData}
                    </Grid>
                </Grid>
            </Grid>
        </div>
    );
};
