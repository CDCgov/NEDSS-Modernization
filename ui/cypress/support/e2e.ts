// Import commands.js using ES2015 syntax:
import './commands';
import PatientMother from './utils/PatientMother';
import PatientUtil from './utils/PatientUtil';
import UserMother from './utils/UserMother';
import UserUtil from './utils/UserUtil';

beforeEach(() => {
    // NBS throws some javascript exceptions on certain pages. Catching them here prevents failure
    cy.on('uncaught:exception', (error, runnable) => {
        return !(
            error.message.includes('Cannot read properties of undefined (reading') ||
            error.message.includes('Cannot read properties of null (reading') ||
            error.message.includes('maxLength is not')
        );
    });
});

// clean up test created data
after(() => {
    if (Cypress.env('cleanup')) {
        UserUtil.login(UserMother.systemAdmin()).then(() => {
            UserUtil.createOrActivateUser(UserMother.supervisor());
        });
        UserUtil.login(UserMother.supervisor()).then(() => {
            PatientUtil.deletePatientIfExists(PatientMother.patient());
            PatientUtil.deletePatientIfExists(PatientMother.duplicatedPatient());
        });
        UserUtil.login(UserMother.systemAdmin()).then(() => {
            UserUtil.getUserState(UserMother.clericalDataEntry()).then((userState) => {
                if (userState === 'Active') {
                    UserUtil.deactivateUser(UserMother.clericalDataEntry());
                }
            });
            UserUtil.getUserState(UserMother.registryManager()).then((userState) => {
                if (userState === 'Active') {
                    UserUtil.deactivateUser(UserMother.registryManager());
                }
            });
            UserUtil.getUserState(UserMother.supervisor()).then((userState) => {
                if (userState === 'Active') {
                    UserUtil.deactivateUser(UserMother.supervisor());
                }
            });
            UserUtil.getUserState(UserMother.elrImporter()).then((userState) => {
                if (userState === 'Active') {
                    UserUtil.deactivateUser(UserMother.elrImporter());
                }
            });
            UserUtil.getUserState(UserMother.nedssAdmin()).then((userState) => {
                if (userState === 'Active') {
                    UserUtil.deactivateUser(UserMother.nedssAdmin());
                }
            });
        });
    }
});
