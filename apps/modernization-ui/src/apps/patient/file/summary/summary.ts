import { MemoizedSupplier } from 'libs/supplying';
import { PatientFileDocumentRequiringReview, patientDDR } from './documentRequiringReview';
import { PatientFileOpenInvestigation, patientOpenInvestigations } from './openInvestigations';
import { patientMergeHistory } from './mergeHistory/patientMergeHistory';
import { PatientFileMergeHistory } from './mergeHistory/model/mergeHistory';

type PatientFileSummaryData = {
    drr: MemoizedSupplier<Promise<PatientFileDocumentRequiringReview[]>>;
    openInvestigations: MemoizedSupplier<Promise<PatientFileOpenInvestigation[]>>;
    mergeHistory: MemoizedSupplier<Promise<PatientFileMergeHistory[]>>;
};

export type { PatientFileSummaryData };

const summary = (patient: number): PatientFileSummaryData => ({
    drr: new MemoizedSupplier(() => patientDDR(patient)),
    openInvestigations: new MemoizedSupplier(() => patientOpenInvestigations(patient)),
    mergeHistory: new MemoizedSupplier(() => patientMergeHistory(patient))
});

export { summary };
