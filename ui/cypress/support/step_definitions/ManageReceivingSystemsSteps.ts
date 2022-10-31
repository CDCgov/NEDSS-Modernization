import { Then, When } from 'cypress-cucumber-preprocessor/steps';
import { SystemType } from '../models/enums/SystemType';
import System from '../models/System';
import AddSystemPage from '../pages/system-management/messaging-management/AddSystemPage';
import ManageSystemsPage from '../pages/system-management/messaging-management/ManageSystemsPage';
import SystemMother from '../utils/SystemMother';

enum EditedSystemType {
    EDITED_CASE_REPORT = 'edited Case Report',
    EDITED_LABORATORY_REPORT = 'edited Laboratory Report'
}
When(/I create a new (.*) system/, (systemType: SystemType) => {
    const system = getSystemByType(systemType);
    // check if system already exists
    const manageSystemsPage = new ManageSystemsPage();
    manageSystemsPage.navigateTo();
    manageSystemsPage.getDisplayedSystems().then((systemsList) => {
        const existingSystem = systemsList.find((s) => s.displayName === system.displayName);
        if (existingSystem) {
            // system already exists, lets update to default values
            manageSystemsPage.clickEditSystem(system.displayName).then((editSystemPage) => {
                editSystemPage.updateSystem(system);
            });
        } else {
            // error means system doesn't already exist, lets create it
            const addSystemPage = new AddSystemPage();
            addSystemPage.navigateTo();
            addSystemPage.createSystem(system);
        }
    });
});

When(/I make changes to the (.*) system/, (systemType: SystemType.CASE_REPORT | SystemType.LABORATORY_REPORT) => {
    const systemWithEdits =
        systemType === SystemType.CASE_REPORT
            ? getSystemByType(EditedSystemType.EDITED_CASE_REPORT)
            : getSystemByType(EditedSystemType.EDITED_LABORATORY_REPORT);
    const manageSystemsPage = new ManageSystemsPage();
    manageSystemsPage.navigateTo();
    manageSystemsPage.clickEditSystem(systemWithEdits.displayName).then((editSystemPage) => {
        // ** NOTE ** do not update system displayName as that is how the tests locate the proper system.
        editSystemPage.updateSystem(systemWithEdits);
    });
});

Then(/I can view the (.*) system details/, (systemType: SystemType | EditedSystemType) => {
    const manageSystemsPage = new ManageSystemsPage();
    manageSystemsPage.navigateTo();
    manageSystemsPage.getDisplayedSystems().then((systems) => {
        const system = getSystemByType(systemType);
        const displayedSystem = systems.find((s) => s.displayName === system.displayName);
        if (!displayedSystem) {
            throw new Error('Failed to locate system in table');
        }
        // validate data displayed in table
        expect(displayedSystem.reportType).equal(system.reportType);
        expect(displayedSystem.displayName).equal(system.displayName);
        expect(displayedSystem.applicationName).equal(encode(system.applicationName));
        expect(displayedSystem.facilityName).equal(encode(system.facilityName));
        expect(displayedSystem.sender).equal(system.sendingSystem);
        expect(displayedSystem.recipient).equal(system.receivingSystem ?? '');
        expect(displayedSystem.transfer).equal(system.allowsTransfers ?? '');

        // validate data displayed in details section
        manageSystemsPage.clickViewSystem(encode(system.displayName)).then((viewSystemPage) => {
            viewSystemPage.navigateTo();
            viewSystemPage.getReportType().should('contain', system.reportType);
            viewSystemPage.getDisplayName().should('contain', system.displayName);
            viewSystemPage.getApplicationName().should('contain', system.applicationName);
            viewSystemPage.getApplicationOid().should('contain', system.applicationOid);
            viewSystemPage.getFacilityName().should('contain', system.facilityName);
            viewSystemPage.getFacilityOid().should('contain', system.facilityOid);
            viewSystemPage.getFacilityDescription().should('contain', system.facilityDescription);
            viewSystemPage.getSendingSystem().should('contain', system.sendingSystem);
            viewSystemPage.getReceivingSystem().should('contain', system.receivingSystem ?? '');
            viewSystemPage.getAllowsTransfer().should('contain', system.allowsTransfers ?? '');
            viewSystemPage
                .getUseSystemDerivedJursidiction()
                .should('contain', system.useSystemDerivedJurisdiction ?? '');
            viewSystemPage.getAdministrativeComments().should('contain', system.administrativeComments);
        });
    });
});

// encodes a string so that it matches value returned from 'innerHTML' javascript calls
function encode(original: string): string {
    return original.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function getSystemByType(type: SystemType | EditedSystemType): System {
    switch (type) {
        case SystemType.CASE_REPORT:
            return SystemMother.caseReportSystem();
        case EditedSystemType.EDITED_CASE_REPORT:
            return SystemMother.caseReportSystem({
                applicationName: '\\&<>\'" EDITED APPLICATION NAME',
                applicationOid: '7865',
                facilityName: 'EDITEDFN',
                facilityOid: '019283',
                facilityDescription: 'EDITED FACILITY DESCRIPTION',
                sendingSystem: 'No',
                useSystemDerivedJurisdiction: 'No',
                administrativeComments: 'EDITED ADMIN COMMENTS'
            });
        case SystemType.LABORATORY_REPORT:
            return SystemMother.labReportSystem();
        case EditedSystemType.EDITED_LABORATORY_REPORT:
            return SystemMother.labReportSystem({
                applicationName: '\\&<>\'" EDITED APPLICATION NAME',
                applicationOid: '0000000',
                facilityName: 'EDITEDFN',
                facilityOid: '67676767',
                facilityDescription: 'EDITED FACILITY DESCRIPTION',
                sendingSystem: 'No',
                receivingSystem: undefined,
                allowsTransfers: undefined,
                useSystemDerivedJurisdiction: undefined,
                administrativeComments: 'EDITED ADMIN COMMENTS'
            });
        default:
            throw new Error('Unsupported system type');
    }
}
