import { PatientFileService } from 'generated';
import { PatientFileDocumentRequiringReview } from './drr';

const patientDDR = (patient: number): Promise<PatientFileDocumentRequiringReview[]> =>
    PatientFileService.documentsRequiringReview({ patient }).then();

export { patientDDR };
