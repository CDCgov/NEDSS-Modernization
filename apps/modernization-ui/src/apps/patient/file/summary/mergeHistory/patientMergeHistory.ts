import { PatientFileMergeHistory } from './mergeHistory';
import { PatientFileService } from '../../../../../generated';

const patientMergeHistory = (patient: number): Promise<PatientFileMergeHistory[]> =>
    PatientFileService.mergeHistory({ patient }).catch(() => []);

export { patientMergeHistory };
