import { PatientFileService } from 'generated';

import { PatientFileMergeHistory } from './model/mergeHistory';

const patientMergeHistory = (patient: number): Promise<PatientFileMergeHistory[]> =>
    PatientFileService.mergeHistory({ patient }).catch(() => []);

export { patientMergeHistory };
