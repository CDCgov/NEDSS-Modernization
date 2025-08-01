import { MemoizedSupplier } from 'libs/supplying';
import { PatientFileInvestigation, patientInvestigations } from './investigations';
import { PatientFileLaboratoryReport, patientLaboratoryReports } from './reports/laboratory';
import { PatientFileMorbidityReport, patientMorbidityReports } from './reports/morbidity';
import { PatientFileVaccinations } from './vaccinations/vaccinations';
import { patientVaccinations } from './vaccinations/patientVaccinations';
import { PatientFileTreatment } from './treatments/treatment';
import { patientTreatments } from './treatments/patientTreatments';

type Reports = {
    laboratory: MemoizedSupplier<Promise<PatientFileLaboratoryReport[]>>;
    morbidity: MemoizedSupplier<Promise<PatientFileMorbidityReport[]>>;
    vaccination: MemoizedSupplier<Promise<PatientFileVaccinations[]>>;
    treatment: MemoizedSupplier<Promise<PatientFileTreatment[]>>;
};

type PatientFileEventData = {
    investigations: MemoizedSupplier<Promise<PatientFileInvestigation[]>>;
    reports: Reports;
};

export type { PatientFileEventData };

const events = (patient: number): PatientFileEventData => ({
    investigations: new MemoizedSupplier(() => patientInvestigations(patient)),
    reports: {
        laboratory: new MemoizedSupplier(() => patientLaboratoryReports(patient)),
        morbidity: new MemoizedSupplier(() => patientMorbidityReports(patient)),
        vaccination: new MemoizedSupplier(() => patientVaccinations(patient)),
        treatment: new MemoizedSupplier(() => patientTreatments(patient))
    }
});

export { events };
