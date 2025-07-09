import { MemoizedSupplier } from 'libs/supplying';
import { PatientFileInvestigation, patientInvestigations } from './investigations';
import { PatientFileLaboratoryReport, patientLaboratoryReports } from './reports/laboratory';

type Reports = {
    laboratory: MemoizedSupplier<Promise<PatientFileLaboratoryReport[]>>;
};

type PatientFileEventData = {
    investigations: MemoizedSupplier<Promise<PatientFileInvestigation[]>>;
    reports: Reports;
};

export type { PatientFileEventData };

const events = (patient: number): PatientFileEventData => ({
    investigations: new MemoizedSupplier(() => patientInvestigations(patient)),
    reports: {
        laboratory: new MemoizedSupplier(() => patientLaboratoryReports(patient))
    }
});

export { events };
