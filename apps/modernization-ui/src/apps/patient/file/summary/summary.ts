import { MemoizedSupplier } from 'libs/supplying';
import { PatientFileDocumentRequiringReview, patientDDR } from './documentRequiringReview';
import { PatientFileOpenInvestigation, patientOpenInvestigations } from './openInvestigations';

type PatientFileSummaryData = {
    drr: MemoizedSupplier<Promise<PatientFileDocumentRequiringReview[]>>;
    openInvestigations: MemoizedSupplier<Promise<PatientFileOpenInvestigation[]>>;
};

export type { PatientFileSummaryData };

const summary = (patient: number): PatientFileSummaryData => ({
    drr: new MemoizedSupplier(() => patientDDR(patient)),
    openInvestigations: new MemoizedSupplier(() => patientOpenInvestigations(patient))
});

export { summary };
