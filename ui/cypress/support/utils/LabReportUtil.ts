import LabReport from '../models/LabReport';
import AddLabReportPage from '../pages/AddLabReportPage';
import PatientSearchPage from '../pages/PatientSearchPage';

export default class LabReportUtil {
    // Add lab report and patient to the system
    public static createLabReportAndPatient(labReport: LabReport): void {
        const addLabReportPage = new AddLabReportPage();
        addLabReportPage.navigateTo();
        this.enterData(addLabReportPage, labReport, true);
    }

    // Searches for an existing patient record and adds the lab report
    public static createLabReportForPatient(labReport: LabReport): void {
        const patientSearchResultsPage = PatientSearchPage.searchForPatient(labReport.patient);
        patientSearchResultsPage.getPatientFilePage(0).then((patientFilePage) => {
            patientFilePage.navigateTo();
            const addLabReportPage = patientFilePage.clickAddLabReport();
            this.enterData(addLabReportPage, labReport);
        });
    }

    private static enterData(addLabReportPage: AddLabReportPage, labReport: LabReport, createPatient = false) {
        if (createPatient) {
            addLabReportPage.setActiveTab('Patient');
            addLabReportPage.setFirstName(labReport.patient.firstName);
            addLabReportPage.setMiddleName(labReport.patient.middleName);
            addLabReportPage.setLastName(labReport.patient.lastName);
            addLabReportPage.setSuffix(labReport.patient.suffix);
            addLabReportPage.setMaritalStatus(labReport.patient.maritalStatus);
            addLabReportPage.setDob(labReport.patient.dateOfBirth);
            addLabReportPage.setCurrentSex(labReport.patient.currentSex);
            addLabReportPage.setDeceased(labReport.patient.deceased);
            labReport.patient.identifications.forEach((id) => {
                addLabReportPage.setIdAsOf(new Date());
                addLabReportPage.setIdType(id.identificationType);
                addLabReportPage.setIdAuthority(id.assigningAuthority);
                addLabReportPage.setIdValue(id.idNumber);
                addLabReportPage.clickAddIdButton();
            });
            addLabReportPage.setStreetAddress1(labReport.patient.streetAddress);
            addLabReportPage.setCity(labReport.patient.city);
            addLabReportPage.setState(labReport.patient.state);
            addLabReportPage.setZip(labReport.patient.zip);
            addLabReportPage.setCounty(labReport.patient.county);
            addLabReportPage.setCountry(labReport.patient.country);
            addLabReportPage.setHomePhone(labReport.patient.homePhone);
            addLabReportPage.setWorkPhone(labReport.patient.workPhone);
            addLabReportPage.setWorkPhoneExtension(labReport.patient.workPhoneExtension);
            addLabReportPage.setCellPhone(labReport.patient.cellPhone);
            addLabReportPage.setEmail(labReport.patient.email);
            addLabReportPage.setEthnicity(labReport.patient.ethnicitiy);
            addLabReportPage.setRaces(labReport.patient.races);
        }

        addLabReportPage.setActiveTab('LabReport');

        addLabReportPage.setReportingFacility(labReport.reportingFacility);
        if (labReport.orderingFacility) {
            addLabReportPage.setOrderingFacility(labReport.orderingFacility);
        }
        if (labReport.orderingProvider) {
            addLabReportPage.setOrderingProvider(labReport.orderingProvider);
        }

        addLabReportPage.setProgramArea(labReport.programArea);
        addLabReportPage.setJurisdiction(labReport.jurisdiction);
        if (labReport.orderedTest) {
            addLabReportPage.setOrderedTest(labReport.orderedTest.test);
            addLabReportPage.setAccessionNumber(labReport.orderedTest.accessionNumber);
            addLabReportPage.setSpecimenSource(labReport.orderedTest.specimenSource);
            addLabReportPage.setSpecimentCollectionSite(labReport.orderedTest.specimentSite);
            addLabReportPage.setSpecimentCollectionDate(labReport.orderedTest.collectionDate);
            addLabReportPage.setPatientStatusAtCollection(labReport.orderedTest.patientStatusAtCollection);
        }

        labReport.resultedTests.forEach((resultedTest) => {
            addLabReportPage.setResultedTest(resultedTest.test);
            addLabReportPage.setCodedResult(resultedTest.codedResults);
            addLabReportPage.setNumericResult(resultedTest.numericResult ?? '');
            addLabReportPage.setTextResults(resultedTest.textResult ?? '');
            addLabReportPage.setReferenceRangeFrom(resultedTest.referenceRangeFrom ?? '');
            addLabReportPage.setReferenceRangeTo(resultedTest.referenceRangeTo ?? '');
            addLabReportPage.setResultedTestStatus(resultedTest.status ?? '');
            addLabReportPage.setResultsComment(resultedTest.comments ?? '');
            addLabReportPage.clickAddResultedTest();
        });

        addLabReportPage.setLabReportComments(labReport.comments);

        addLabReportPage.clickSubmit();
    }
}
