import { get, maybeJson } from 'libs/api';
import { AddressDemographic } from 'libs/patient/demographics/address';

const patientAddress = (patient: number): Promise<AddressDemographic[]> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/addresses`)).then(maybeJson);

export { patientAddress };
