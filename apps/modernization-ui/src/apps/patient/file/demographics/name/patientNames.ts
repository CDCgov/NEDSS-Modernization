import { get, maybeJson } from 'libs/api';
import { NameDemographic } from 'libs/patient/demographics';

const patientNames = (patient: number): Promise<NameDemographic[]> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/names`)).then(maybeJson);

export { patientNames };
