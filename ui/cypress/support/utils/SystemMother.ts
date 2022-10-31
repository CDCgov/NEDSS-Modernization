import System from '../models/System';

export default class SystemMother {
    public static caseReportSystem(overrides?: Partial<System>): System {
        return {
            reportType: 'Case Report',
            displayName: 'TestCR',
            applicationName: 'Test case report applicaiton name \\&<>\'"',
            applicationOid: '1234',
            facilityName: 'tcr\\&<>\'"',
            facilityOid: '4321',
            facilityDescription: 'TEST facility description \\&<>\'"',
            sendingSystem: 'Yes',
            receivingSystem: 'Yes',
            allowsTransfers: 'Yes',
            useSystemDerivedJurisdiction: 'Yes',
            administrativeComments: 'TEST case report comments',
            ...overrides
        };
    }

    public static labReportSystem(overrides?: Partial<System>): System {
        return {
            reportType: 'Laboratory Report',
            displayName: 'TestLR',
            applicationName: 'TEST lab report application name  \\&<>\'"',
            applicationOid: '0987',
            facilityName: 'tlr\\&<>\'"',
            facilityOid: '7890',
            facilityDescription: 'TEST facility description \\&<>\'"',
            sendingSystem: 'Yes',
            administrativeComments: 'TEST case report comments',
            ...overrides
        };
    }
}
