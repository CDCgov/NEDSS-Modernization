export default class System {
    reportType: 'Case Report' | 'Laboratory Report';
    displayName: string;
    applicationName: string;
    applicationOid: string;
    facilityName: string;
    facilityOid: string;
    facilityDescription?: string;
    sendingSystem: 'No' | 'Yes';
    receivingSystem?: 'No' | 'Yes';
    allowsTransfers?: 'No' | 'Yes';
    useSystemDerivedJurisdiction?: 'No' | 'Yes';
    administrativeComments?: string;
}
