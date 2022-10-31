import Patient from '../models/Patient';
import BasePage from './BasePage';
import DateUtil from '../utils/DateUtil';

enum Selector {
    COMPARE_TABLE = 'div[name=N10010] table'
}
export default class PatientComparePage extends BasePage {
    constructor(url: string) {
        super(url);
    }

    assertPatientDataIsDisplayed(patient1: Patient, patient2: Patient): void {
        const compareTable = this.getElement(Selector.COMPARE_TABLE);
        // Patient1 info
        compareTable.should('contain', `${patient1.firstName} ${patient1.middleName} ${patient1.lastName}`);
        compareTable.should('contain', DateUtil.getNBSFormattedDate(patient1.dateOfBirth, true));
        compareTable.should('contain', patient1.streetAddress);
        compareTable.should('contain', patient1.cellPhone);
        compareTable.should('contain', patient1.homePhone);
        compareTable.should('contain', patient1.workPhone);
        compareTable.should('contain', patient1.email);

        // Patient 2 info
        compareTable.should('contain', `${patient2.firstName} ${patient2.middleName} ${patient2.lastName}`);
        compareTable.should('contain', DateUtil.getNBSFormattedDate(patient2.dateOfBirth, true));
        compareTable.should('contain', patient2.streetAddress);
        compareTable.should('contain', patient2.cellPhone);
        compareTable.should('contain', patient2.homePhone);
        compareTable.should('contain', patient2.workPhone);
        compareTable.should('contain', patient2.email);
    }
}
