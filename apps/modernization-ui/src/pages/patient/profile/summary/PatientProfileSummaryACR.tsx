import './style.scss';

import { Grid, Button } from '@trussworks/react-uswds';
import { Patient } from 'pages/patient/profile';
import { Address, Email, Name, PatientSummary, Phone } from './PatientSummary';
import { useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

type Props = {
    patient: Patient;
    summary: PatientSummary;
};

enum ACTIVE_TAB {
    FORM_INFO = 'Form Info',
    IDENTIFICATION = 'Identification',
    DEMOGRAPHICS = 'Demographics',
    FACILITY = 'Facility',
    LAB_DATA = 'Lab Data',
    CLINICAL = 'Clinical',
    TREATMENT = 'Treatment',
    TESTING_HISTORY = 'Testing History',
    FOLLOWUP = 'Follow-up Investigation',
    LOCAL_FIELDS = 'Local Fields',
    DUPLICATE_REVIEW = 'Duplicate Review',
    RETIRED = 'Retired',
    COMMENTS = 'Comments'
}

const display = (value: string) => <p>{value}</p>;
const noData = <p className="no-data">No Data</p>;

const formattedPhones = (items: Phone[]) => display(items.map((items) => items.number).join('\n'));

const formattedEmails = (items: Email[]) => display(items.map((item) => item.address).join('\n'));

const formattedAddress = ({ street, city, state, zipcode, country }: Address) => {
    const location = ((city && city + ' ') || '') + ((state && state + ' ') || '') + (zipcode ?? '');
    const address =
        ((street && street + '\n') || '') + ((location && location + '\n') || '') + ((country && country + '\n') || '');
    return display(address);
};

const formattedName = (name: Name | null | undefined) => {
    return (name && [name.last, name.first].join(', ')) || '--';
};

export const PatientProfileSummary = ({ patient, summary }: Props) => {
    const [activeTab, setActiveTab] = useState<ACTIVE_TAB.FORM_INFO | ACTIVE_TAB.IDENTIFICATION | ACTIVE_TAB.DEMOGRAPHICS | ACTIVE_TAB.FACILITY | ACTIVE_TAB.LAB_DATA | ACTIVE_TAB.CLINICAL | ACTIVE_TAB.TREATMENT>(
        ACTIVE_TAB.FORM_INFO
    );

    let hideSummary = 'flex';

    function hideSummaryButton() {

        hideSummary = 'none';
    }


    return (

        <div>
            <div className="grid-row flex-align-center padding-2 border-bottom border-base-lighter bg-white">

                <p className="font-sans-xl text-bold margin-0">Mary Williams: HIV</p>
                <span style={{display: 'inherit'}} onClick={() => hideSummaryButton()}>
                    <img style={{ marginLeft: '5px' }} src={'/info-outline.svg'}/>
                    <p  className="summary-blue">hide summary</p>
                </span>
                <div style={{marginLeft: 'auto'}}>
                    <Button
                        type={'button'}
                        className="display-inline-flex print-btn usa-button--outline"
                        >
                        Cancel
                    </Button>
                    <Button
                        type={'button'}
                        className="display-inline-flex print-btn">
                        Save and close
                    </Button>
                    <Button
                        type={'button'}
                        className="display-inline-flex print-btn">
                        Save and continue
                    </Button>
                </div>
                {/*<h5 className="font-sans-md text-medium margin-0">Patient ID: {patient.shortId}</h5>*/}
            </div>
            <Grid row gap={3} className="padding-3"  style={{display: hideSummary}}>
                <Grid col={2}>
                    <Grid col={12} className="summary-value">
                        <h5 className="margin-right-1">Legal Name</h5>
                        <p className="summary-blue-legal-name">Williams, Mary</p>
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="summary-value">
                        <h5 className="margin-right-1">SEX</h5>
                        <p>{summary.gender}</p>
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="summary-value">
                        <h5>Investigatior</h5>
                        <p>Jane Doe</p>
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="summary-value">
                        <h5>PHONE NUMBER</h5>
                        555-333-4444
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="summary-value">
                        <h5>Investigatior</h5>
                        <p>Jane Doe</p>
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="summary-value">
                        <h5>PREGRANT STATUS ID</h5>
                        No
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="margin-top-3 summary-value">
                        <h5>DATE OF BIRTH</h5>
                        01/01/2000
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="margin-top-3 summary-value">
                        <h5>PATIENT ID</h5>
                        88010
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="margin-top-3 summary-value">
                        <h5>START DATE</h5>
                        7/14/23
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="margin-top-3 summary-value">
                        <h5>JURISDICTION</h5>
                        Carrol County
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="margin-top-3 summary-value">
                        <h5>STATUS</h5>
                        Confirmed
                    </Grid>
                </Grid>

                <Grid col={2}>
                    <Grid col={12} className="margin-top-3 summary-value">
                        <h5>DECEASED</h5>
                        No
                    </Grid>
                </Grid>
            </Grid>




        </div>
    );
};
