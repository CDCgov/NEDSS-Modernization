import { PatientFileContacts } from 'apps/patient/file/events/contacts';
import { MemoizedSupplier } from 'libs/supplying';

import { patientContacts } from './contactsNamed/patientContacts';
import { PatientFileDocument } from './documents/documents';
import { patientDocuments } from './documents/patientDocuments';
import { PatientFileInvestigation, patientInvestigations } from './investigations';
import { patientsNamed } from './patientsNamed/patientNamed';
import { patientBirthRecords, PatientFileBirthRecord } from './record/birth';
import { PatientFileLaboratoryReport, patientLaboratoryReports } from './reports/laboratory';
import { PatientFileMorbidityReport, patientMorbidityReports } from './reports/morbidity';
import { patientTreatments } from './treatments/patientTreatments';
import { PatientFileTreatment } from './treatments/treatment';
import { patientVaccinations } from './vaccinations/patientVaccinations';
import { PatientFileVaccinations } from './vaccinations/vaccinations';

type Reports = {
    laboratory: MemoizedSupplier<Promise<PatientFileLaboratoryReport[]>>;
    morbidity: MemoizedSupplier<Promise<PatientFileMorbidityReport[]>>;
};

type PatientFileEventData = {
    investigations: MemoizedSupplier<Promise<PatientFileInvestigation[]>>;
    vaccination: MemoizedSupplier<Promise<PatientFileVaccinations[]>>;
    treatment: MemoizedSupplier<Promise<PatientFileTreatment[]>>;
    contactNamed: MemoizedSupplier<Promise<PatientFileContacts[]>>;
    documents: MemoizedSupplier<Promise<PatientFileDocument[]>>;
    patientNamed: MemoizedSupplier<Promise<PatientFileContacts[]>>;
    birthRecords: MemoizedSupplier<Promise<PatientFileBirthRecord[]>>;
    reports: Reports;
};

export type { PatientFileEventData };

const events = (patient: number): PatientFileEventData => ({
    investigations: new MemoizedSupplier(() => patientInvestigations(patient)),
    vaccination: new MemoizedSupplier(() => patientVaccinations(patient)),
    treatment: new MemoizedSupplier(() => patientTreatments(patient)),
    contactNamed: new MemoizedSupplier(() => patientContacts(patient)),
    documents: new MemoizedSupplier(() => patientDocuments(patient)),
    patientNamed: new MemoizedSupplier(() => patientsNamed(patient)),
    birthRecords: new MemoizedSupplier(() => patientBirthRecords(patient)),
    reports: {
        laboratory: new MemoizedSupplier(() => patientLaboratoryReports(patient)),
        morbidity: new MemoizedSupplier(() => patientMorbidityReports(patient)),
    },
});

export { events };
