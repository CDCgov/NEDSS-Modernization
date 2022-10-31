import { OrganizationIdentificationType } from '../models/enums/OrganizationIdentificationType';
import { OrganizationRole } from '../models/enums/OrganizationRole';
import { StandardIndustryClass } from '../models/enums/StandardIndustryClass';
import { State } from '../models/enums/State';
import Organization from '../models/Organization';

export default class OrganizationMother {
    public static piedmontHospital(): Organization {
        return {
            name: 'Piedmont Hospital',
            quickCode: '1',
            standardIndustryClass: StandardIndustryClass.HEALTH_CARE_AND_SOCIAL_ASSISTANCE,
            roles: [OrganizationRole.HOSPITAL],
            comments: '',
            identifications: [
                {
                    type: OrganizationIdentificationType.ORGANIZATION_IDENTIFIER,
                    assigningAuthority: 'CMS Provider',
                    id: 'CMS1234'
                }
            ],
            addresses: [
                {
                    use: 'Primary Work Place',
                    type: 'Office',
                    streetAddress1: '1968 Peachtree Road NW',
                    streetAddress2: '',
                    city: 'Atlanta',
                    state: State.GEORGIA,
                    zip: '30056',
                    county: 'Fulton County',
                    comments: ''
                }
            ],
            telephoneInformation: [
                {
                    use: 'Primary Work Place',
                    type: 'Phone',
                    telephone: '404-605-5000',
                    telephoneExtension: '',
                    countryCode: '001',
                    email: 'admin@piedmont.org',
                    url: 'www.piedmonthospital.org',
                    comments: ''
                }
            ]
        };
    }
    public static choaScottishRite(): Organization {
        return {
            name: 'CHOA - Scottish Rite',
            quickCode: '2',
            standardIndustryClass: StandardIndustryClass.HEALTH_CARE_AND_SOCIAL_ASSISTANCE,
            roles: [OrganizationRole.HOSPITAL],
            comments: '',
            identifications: [
                {
                    type: OrganizationIdentificationType.ABCS_HOSPITAL_ID,
                    assigningAuthority: 'AHA',
                    id: 'AHA0012354'
                }
            ],
            addresses: [
                {
                    use: 'Primary Work Place',
                    type: 'Office',
                    streetAddress1: '1001 Johnson Ferry Rd NE',
                    streetAddress2: '',
                    city: 'Atlanta',
                    state: State.GEORGIA,
                    zip: '30034',
                    county: 'Dekalb County',
                    comments: ''
                }
            ],
            telephoneInformation: [
                {
                    use: 'Primary Work Place',
                    type: 'Phone',
                    telephone: '404-785-5252',
                    telephoneExtension: '',
                    countryCode: '001',
                    email: '',
                    url: '',
                    comments: ''
                }
            ]
        };
    }

    public static emoryHospital(): Organization {
        return {
            name: 'Emory University Hospital',
            quickCode: '3',
            standardIndustryClass: StandardIndustryClass.HEALTH_CARE_AND_SOCIAL_ASSISTANCE,
            roles: [OrganizationRole.HOSPITAL],
            comments: '',
            identifications: [
                {
                    type: OrganizationIdentificationType.ABCS_HOSPITAL_ID,
                    assigningAuthority: 'AHA',
                    id: 'AHA345645687'
                }
            ],
            addresses: [
                {
                    use: 'Primary Work Place',
                    type: 'Office',
                    streetAddress1: '1364 Clifton Road',
                    streetAddress2: '',
                    city: 'Atlanta',
                    state: State.GEORGIA,
                    zip: '30329',
                    county: 'Dekalb County',
                    comments: ''
                }
            ],
            telephoneInformation: [
                {
                    use: 'Primary Work Place',
                    type: 'Phone',
                    telephone: '404-712-2000',
                    telephoneExtension: '',
                    countryCode: '001',
                    email: '',
                    url: '',
                    comments: ''
                }
            ]
        };
    }

    public static northsideHospital(): Organization {
        return {
            name: 'Northside Hospital',
            quickCode: '4',
            standardIndustryClass: StandardIndustryClass.HEALTH_CARE_AND_SOCIAL_ASSISTANCE,
            roles: [OrganizationRole.HOSPITAL],
            comments: '',
            identifications: [
                { type: OrganizationIdentificationType.ABCS_HOSPITAL_ID, assigningAuthority: 'AHA', id: 'AHA0198324' }
            ],
            addresses: [
                {
                    use: 'Primary Work Place',
                    type: 'Office',
                    streetAddress1: '1000 Johnson Ferry Road NE',
                    streetAddress2: '',
                    city: 'Atlanta',
                    state: State.GEORGIA,
                    zip: '30300',
                    county: 'Fulton County',
                    comments: ''
                }
            ],
            telephoneInformation: [
                {
                    use: 'Primary Work Place',
                    type: 'Phone',
                    telephone: '404-851-8000',
                    telephoneExtension: '',
                    countryCode: '001',
                    email: '',
                    url: '',
                    comments: ''
                }
            ]
        };
    }

    public static stJosephsHospital(): Organization {
        return {
            name: "St. Joseph's Hospital",
            quickCode: '5',
            standardIndustryClass: StandardIndustryClass.HEALTH_CARE_AND_SOCIAL_ASSISTANCE,
            roles: [OrganizationRole.HOSPITAL],
            comments: '',
            identifications: [
                {
                    type: OrganizationIdentificationType.ABCS_HOSPITAL_ID,
                    assigningAuthority: 'AHA',
                    id: 'AHA1234578'
                }
            ],
            addresses: [
                {
                    use: 'Primary Work Place',
                    type: 'Office',
                    streetAddress1: '5665 Peachtree Dunwoody Road',
                    streetAddress2: '',
                    city: 'Atlanta',
                    state: State.GEORGIA,
                    zip: '30230',
                    county: 'Fulton County',
                    comments: ''
                }
            ],
            telephoneInformation: [
                {
                    use: 'Primary Work Place',
                    type: 'Phone',
                    telephone: '404-851-7001',
                    telephoneExtension: '',
                    countryCode: '001',
                    email: '',
                    url: '',
                    comments: ''
                }
            ]
        };
    }
}
