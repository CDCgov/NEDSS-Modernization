import { Grid } from '@trussworks/react-uswds';
import { NamesTable } from 'pages/patient/profile/names';
import { AddressesTable } from 'pages/patient/profile/addresses';
import { PhoneAndEmailTable } from 'pages/patient/profile/phoneEmail';
import { IdentificationsTable } from 'pages/patient/profile/identification';
import { RacesTable } from 'pages/patient/profile/race';
import { AdministrativeTable } from 'pages/patient/profile/administrative';
import { GeneralPatient } from 'pages/patient/profile/generalInfo';
import { Mortality } from 'pages/patient/profile/mortality';
import { Ethnicity } from 'pages/patient/profile/ethnicity';
import { SexBirth } from 'pages/patient/profile/sexBirth';
import { AlertProvider } from 'alert';

type DemographicProps = {
    handleFormSubmission?: (type: 'error' | 'success' | 'warning' | 'info', message: string, data: any) => void;
    id: string;
    fetchSummary: () => void;
};

export type AlertType = {
    type: 'Updated' | 'Deleted' | 'Added';
    table:
        | 'Comment'
        | 'Name'
        | 'Address'
        | 'Phone & Email'
        | 'Race'
        | 'Identification'
        | 'General patient information'
        | 'Mortality'
        | 'Sex & Birth'
        | 'Ethnicity';
    name?: string;
} | null;

export const Demographics = ({ id, fetchSummary }: DemographicProps) => {
    return (
        <AlertProvider>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <AdministrativeTable patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <NamesTable fetchSummary={fetchSummary} patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <AddressesTable fetchSummary={fetchSummary} patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PhoneAndEmailTable fetchSummary={fetchSummary} patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <IdentificationsTable fetchSummary={fetchSummary} patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <RacesTable fetchSummary={fetchSummary} patient={id} />
            </div>

            <Grid row gap className="margin-auto">
                <Grid col={6}>
                    <Grid row>
                        <GeneralPatient fetchSummary={fetchSummary} patient={id} />
                        <Mortality fetchSummary={fetchSummary} patient={id} />
                    </Grid>
                </Grid>
                <Grid col={6}>
                    <Grid row>
                        <Ethnicity fetchSummary={fetchSummary} patient={id} />
                        <SexBirth fetchSummary={fetchSummary} patient={id} />
                    </Grid>
                </Grid>
            </Grid>
        </AlertProvider>
    );
};
