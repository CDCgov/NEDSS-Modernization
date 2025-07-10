import { PatientFileService } from 'generated';
import { EthnicityDemographic } from 'libs/patient/demographics/ethnicity';

const patientEthnicity = (patient: number): Promise<EthnicityDemographic> =>
    PatientFileService.ethnicityDemographics({ patient }).then();

export { patientEthnicity };
