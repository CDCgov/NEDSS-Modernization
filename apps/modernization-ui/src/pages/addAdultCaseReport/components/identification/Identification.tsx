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
import { IdentificationFields } from 'pages/addAdultCaseReport/components/identificationFields/identificationFields';
import { AlertProvider } from 'alert';

type IdentificationProps = {
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

export const Identification = ({ id }: IdentificationProps) => {
    return (
        <AlertProvider>
            <IdentificationFields
                id={'section-Identification'}
                title="Identification"
                coded={{
                    identificationTypes: coded.identificationTypes,
                    assigningAuthorities: coded.assigningAuthorities
                }}
            />
        </AlertProvider>
    );
};
