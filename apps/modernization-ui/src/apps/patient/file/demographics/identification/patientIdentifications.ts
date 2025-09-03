import { get, maybeJson } from 'libs/api';
import { IdentificationDemographic } from 'libs/patient/demographics';

const patientIdentifications = (patient: number): Promise<IdentificationDemographic[]> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/identifications`)).then(maybeJson);

export { patientIdentifications };
