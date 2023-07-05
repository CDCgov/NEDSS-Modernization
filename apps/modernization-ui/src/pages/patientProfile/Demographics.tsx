import { Alert, Button, Grid, Icon } from '@trussworks/react-uswds';
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
import { useEffect, useState } from 'react';

type DemographicProps = {
    handleFormSubmission?: (type: 'error' | 'success' | 'warning' | 'info', message: string, data: any) => void;
    id: string;
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

export const Demographics = ({ id }: DemographicProps) => {
    const [alert, setAlert] = useState<AlertType>(null);

    const handleAlert = (data: any) => {
        setAlert(data);
    };

    useEffect(() => {
        if (alert) {
            const timeout = setTimeout(() => {
                setAlert(null);
            }, 3000);

            return () => clearTimeout(timeout);
        }
    }, [alert]);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <AdministrativeTable patient={id} handleAlert={handleAlert} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <NamesTable patient={id} handleAlert={handleAlert} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <AddressesTable patient={id} handleAlert={handleAlert} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PhoneAndEmailTable patient={id} handleAlert={handleAlert} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <IdentificationsTable handleAlert={handleAlert} patient={id} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <RacesTable handleAlert={handleAlert} patient={id} />
            </div>

            <Grid row gap className="margin-auto">
                <Grid col={6}>
                    <Grid row>
                        <GeneralPatient handleAlert={handleAlert} patient={id} />
                        <Mortality handleAlert={handleAlert} patient={id} />
                    </Grid>
                </Grid>
                <Grid col={6}>
                    <Grid row>
                        <Ethnicity handleAlert={handleAlert} patient={id} />
                        <SexBirth handleAlert={handleAlert} patient={id} />
                    </Grid>
                </Grid>
            </Grid>

            {alert && (
                <Alert
                    type="success"
                    heading="Success"
                    headingLevel="h4"
                    cta={
                        <Button type="button" unstyled onClick={() => setAlert(null)}>
                            <Icon.Close />
                        </Button>
                    }>
                    {alert?.type} {alert?.table} {alert?.name && <strong>{alert?.name}</strong>}
                </Alert>
            )}
        </>
    );
};
