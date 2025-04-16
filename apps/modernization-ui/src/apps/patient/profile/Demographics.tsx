import { Grid } from '@trussworks/react-uswds';
import { NamesTable } from 'apps/patient/profile/names';
import { AddressesTable } from 'apps/patient/profile/addresses';
import { PhoneAndEmailTable } from 'apps/patient/profile/phoneEmail';
import { IdentificationsTable } from 'apps/patient/profile/identification';
import { RacesTable } from 'apps/patient/profile/race';
import { AdministrativeTable } from 'apps/patient/profile/administrative';
import { GeneralPatient } from 'apps/patient/profile/generalInfo';
import { Mortality } from 'apps/patient/profile/mortality';
import { Ethnicity } from 'apps/patient/profile/ethnicity';
import { SexBirth } from 'apps/patient/profile/sexBirth';
import { AlertProvider } from 'alert';
import { useParams } from 'react-router';
import { usePatientProfile } from './usePatientProfile';

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
        | 'Sex & birth'
        | 'Ethnicity';
    name?: string;
} | null;

export const Demographics = () => {
    const { id } = useParams();
    const { patient } = usePatientProfile(id);
    return (
        <div role="tabpanel" id="demographics-tabpanel">
            <AlertProvider>
                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <AdministrativeTable patient={patient} />
                </div>

                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <NamesTable patient={patient} />
                </div>

                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <AddressesTable patient={patient} />
                </div>

                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <PhoneAndEmailTable patient={patient} />
                </div>

                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <IdentificationsTable patient={patient} />
                </div>

                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <RacesTable patient={patient} />
                </div>

                <Grid row gap className="margin-auto">
                    <Grid col={6}>
                        <Grid row>
                            <GeneralPatient patient={patient} />
                            <Mortality patient={patient} />
                        </Grid>
                    </Grid>
                    <Grid col={6}>
                        <Grid row>
                            <Ethnicity patient={patient} />
                            <SexBirth patient={patient} />
                        </Grid>
                    </Grid>
                </Grid>
            </AlertProvider>
        </div>
    );
};
