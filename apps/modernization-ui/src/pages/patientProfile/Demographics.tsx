import { Grid } from '@trussworks/react-uswds';
import { NamesTable } from 'pages/patient/profile/names';
import { AddressesTable } from 'pages/patient/profile/addresses';
import { PhoneAndEmailTable } from 'pages/patient/profile/phoneEmail';
import { IdentificationsTable } from 'pages/patient/profile/identification';
import { RacesTable } from 'pages/patient/profile/race';
import { AdministrativeTable } from 'pages/patient/profile/administrative';
import { GeneralPatient } from 'pages/patient/profile/generalInfo';
import { Mortality } from 'pages/patient/profile/moratlity';
import { Ethnicity } from 'pages/patient/profile/ethnicity';
import { SexBirth } from 'pages/patient/profile/sexBirth';

type DemographicProps = {
    handleFormSubmission?: (type: 'error' | 'success' | 'warning' | 'info', message: string, data: any) => void;
    id: string;
};

export const Demographics = ({ id }: DemographicProps) => {
    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <AdministrativeTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <NamesTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <AddressesTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PhoneAndEmailTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <IdentificationsTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <RacesTable patient={id} />
            </div>

            <Grid row gap className="margin-auto">
                <Grid col={6}>
                    <Grid row>
                        <GeneralPatient patient={id} />
                        <Mortality patient={id} />
                    </Grid>
                </Grid>
                <Grid col={6}>
                    <Grid row>
                        <Ethnicity patient={id} />
                        <SexBirth patient={id} />
                    </Grid>
                </Grid>
            </Grid>
        </>
    );
};
