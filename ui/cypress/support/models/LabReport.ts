import CodedResult from './enums/CodedResult';
import { CollectionSite } from './enums/CollectionSite';
import { ProgramArea } from './enums/ProgramArea';
import { SpecimenSource } from './enums/SpecimenSource';
import Test from './enums/Test';
import Organization from './Organization';
import Patient from './Patient';

export default class LabReport {
    patient: Patient;
    reportingFacility: Organization;
    orderingFacility?: Organization;
    orderingProvider?: Organization;
    programArea: ProgramArea;
    jurisdiction: string;
    orderedTest?: OrderedTest;
    resultedTests: ResultedTest[];
    comments: string;
}

export enum PatientStatus {
    BLANK = '',
    HOSPITALIZED = 'hospitalized',
    OUTPATIENT = 'outpatient',
    UNKNOWN = 'Unknown'
}

interface OrderedTest {
    test: Test;
    accessionNumber: string;
    specimenSource: SpecimenSource;
    specimentSite: CollectionSite;
    collectionDate: Date;
    patientStatusAtCollection: PatientStatus;
}

interface ResultedTest {
    test: Test;
    codedResults: CodedResult;
    numericResult?: string;
    textResult?: string;
    referenceRangeFrom?: string;
    referenceRangeTo?: string;
    status?: 'Corrected' | 'Final' | 'Preliminary' | 'Results pending' | 'Started but cancelled';
    comments?: string;
}
