import { PatientFileService } from 'generated';
import { MortalityDemographic } from 'libs/patient/demographics/mortality';

const patientMortality = (patient: number): Promise<MortalityDemographic> =>
    PatientFileService.mortalityDemographics({ patient }).then();

export { patientMortality };
