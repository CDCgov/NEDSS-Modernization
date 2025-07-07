import { PatientFileService } from 'generated';
import { GeneralInformationDemographic } from 'libs/patient/demographics/general';

const patientGeneral = (patient: number): Promise<GeneralInformationDemographic> =>
    PatientFileService.generalInformationDemographics({ patient }).then();

export { patientGeneral };
