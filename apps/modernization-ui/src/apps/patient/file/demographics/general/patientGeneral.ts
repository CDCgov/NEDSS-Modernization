import { get, maybeJson } from 'libs/api';
import { GeneralInformationDemographic } from 'libs/patient/demographics/general';

const patientGeneral = (patient: number): Promise<GeneralInformationDemographic> =>
    fetch(get(`/nbs/api/patients/${patient}/demographics/general`)).then(maybeJson);

export { patientGeneral };
