import { get, maybeJson } from 'libs/api';
import { RaceDemographic } from 'libs/patient/demographics/race';

const patientRace = (patient: number): Promise<RaceDemographic[]> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/races`)).then(maybeJson);

export { patientRace };
