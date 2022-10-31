import CodedResult from '../models/enums/CodedResult';
import { ProgramArea } from '../models/enums/ProgramArea';
import Test from '../models/enums/Test';
import LabReport from '../models/LabReport';
import Patient from '../models/Patient';
import OrganizationMother from './OrganizationMother';
import PatientMother from './PatientMother';

export default class LabReportMother {
    public static acidFastStain(patient: Patient = PatientMother.patient()): LabReport {
        return {
            patient,
            reportingFacility: OrganizationMother.emoryHospital(),
            programArea: ProgramArea.STD,
            jurisdiction: 'Dekalb County',
            resultedTests: [
                {
                    test: Test.ACID_FAST_STAIN,
                    codedResults: CodedResult.ABNORMAL
                }
            ],
            comments: 'TEST LAB REPORT'
        };
    }
}
