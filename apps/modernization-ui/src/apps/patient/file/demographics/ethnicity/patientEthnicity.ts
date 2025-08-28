import { get, maybeJson } from 'libs/api';
import { EthnicityDemographic } from 'libs/patient/demographics/ethnicity';

const patientEthnicity = (patient: number): Promise<EthnicityDemographic> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/ethnicity`)).then(maybeJson);

export { patientEthnicity };
