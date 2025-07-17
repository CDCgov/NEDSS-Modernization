import { PatientFileService } from 'generated';
import { PatientFileMergeHistory } from './mergeHistory';

const patientMergeHistory = (patient: number): Promise<PatientFileMergeHistory[]> =>
    PatientFileService.documentsRequiringReview({ patient }).then();

export { patientMergeHistory };