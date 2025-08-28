import { get, maybeJson } from 'libs/api';
import { PhoneEmailDemographic } from 'libs/patient/demographics/phoneEmail';

const patientPhoneEmail = (patient: number): Promise<PhoneEmailDemographic[]> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/phones`)).then(maybeJson);

export { patientPhoneEmail };
