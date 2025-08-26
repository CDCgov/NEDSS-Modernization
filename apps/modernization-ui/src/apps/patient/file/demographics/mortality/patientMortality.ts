import { get, maybeJson } from 'libs/api';
import { MortalityDemographic } from 'libs/patient/demographics/mortality';

const patientMortality = (patient: number): Promise<MortalityDemographic> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/mortality`)).then(maybeJson);

export { patientMortality };
