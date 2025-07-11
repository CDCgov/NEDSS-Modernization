import { PatientFileService } from 'generated';
import { PatientFileDemographicsSummary } from './summary';

const demographicsSummary = (patient: number): Promise<PatientFileDemographicsSummary> =>
    PatientFileService.summary({ patient }).then();

export { demographicsSummary };
