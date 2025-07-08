import { PatientFileService } from 'generated';
import { RaceDemographic } from 'libs/patient/demographics/race';

const patientRace = (patient: number): Promise<Array<RaceDemographic>> =>
    PatientFileService.raceDemographics({ patient }).then();

export { patientRace };
