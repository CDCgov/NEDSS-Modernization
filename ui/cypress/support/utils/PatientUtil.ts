import Patient from '../models/Patient';
import PatientSearchPage from '../pages/PatientSearchPage';
import ViewLabReportPage from '../pages/ViewLabReportPage';

export default class PatientUtil {
    public static createPatientIfNotExists(patient: Patient): void {
        const detailedLogs = Cypress.env('detailedLogs');
        const searchResultsPage = PatientSearchPage.searchForPatient(patient);

        cy.document({ log: detailedLogs }).then((d) => {
            const searchResultCount = d.getElementsByClassName('odd').length + d.getElementsByClassName('even').length;
            if (searchResultCount === 0) {
                // user was not found, create it
                const addPatientPage = searchResultsPage.clickAddNew();
                addPatientPage.setComments('TEST user');
                addPatientPage.setLastName(patient.lastName);
                addPatientPage.setFirstName(patient.firstName);
                addPatientPage.setMiddleName(patient.middleName);
                addPatientPage.setSuffix(patient.suffix);
                addPatientPage.setDob(patient.dateOfBirth);
                addPatientPage.setCurrentSex(patient.currentSex);
                addPatientPage.setBirthSex(patient.birthSex);
                addPatientPage.setDeceased(patient.deceased);
                addPatientPage.setMaritalStatus(patient.maritalStatus);
                addPatientPage.setStreetAddress1(patient.streetAddress);
                addPatientPage.setCity(patient.city);
                addPatientPage.setState(patient.state);
                addPatientPage.setCounty(patient.county);
                addPatientPage.setCensusTract(patient.censusTract);
                addPatientPage.setCountry(patient.country);
                addPatientPage.setHomePhone(patient.homePhone);
                addPatientPage.setWorkPhone(patient.workPhone);
                addPatientPage.setWorkPhoneExtension(patient.workPhoneExtension);
                addPatientPage.setCellPhone(patient.cellPhone);
                addPatientPage.setEmail(patient.email);
                addPatientPage.setEthnicity(patient.ethnicitiy);
                addPatientPage.setRaces(patient.races);
                addPatientPage.setIdentifications(patient.identifications);
                addPatientPage.clickSubmit();
            }
        });
    }

    public static deletePatientIfExists(patient: Patient): void {
        const resultsPage = PatientSearchPage.searchForPatient(patient);
        resultsPage.getResultsCount().then((count) => {
            if (count > 0) {
                resultsPage.getPatientFilePage(0).then((patientFilePage) => {
                    patientFilePage.navigateTo();
                    // Delete all lab reports
                    patientFilePage.getAllLabReports().then((links) => {
                        for (let i = 0; i < links.length; i++) {
                            const anchorElement = links[i];
                            // must reset to patientFilePage after viewing each lab report
                            patientFilePage.navigateTo();
                            const uid = anchorElement.href.substring(
                                anchorElement.href.indexOf('observationUID=') + 'observationUID='.length
                            );
                            const viewLabReportPage = new ViewLabReportPage(uid);
                            viewLabReportPage.navigateTo();
                            viewLabReportPage.clickDelete();
                        }
                    });
                    patientFilePage.clickDelete();
                });
            }
        });
    }
}
