import { OrganizationIdentificationType } from './enums/OrganizationIdentificationType';
import { OrganizationRole } from './enums/OrganizationRole';
import { StandardIndustryClass } from './enums/StandardIndustryClass';
import { State } from './enums/State';

export default interface Organization {
    name: string;
    quickCode: string;
    standardIndustryClass: StandardIndustryClass;
    roles: OrganizationRole[];
    comments: string;
    identifications: Identification[];
    addresses: Address[];
    telephoneInformation: TelephoneInformation[];
}

export interface Identification {
    type: OrganizationIdentificationType;
    assigningAuthority: 'AHA' | 'CLIA (CMS)' | 'CMS Provider' | 'Other';
    id: string;
}
export interface Address {
    use: 'Alternate Work Place' | 'Organizational Contact' | 'Primary Work Place';
    type: 'Office' | 'Postal/Mailing';
    streetAddress1: string;
    streetAddress2: string;
    city: string;
    state: State;
    zip: string;
    county: string;
    comments: string;
}
export interface TelephoneInformation {
    use: 'Alternate Work Place' | 'Organizational Contact' | 'Primary Work Place';
    type: 'Answering service' | 'FAX' | 'Phone';
    countryCode: string;
    telephone: string;
    telephoneExtension: string;
    email: string;
    url: string;
    comments: string;
}
