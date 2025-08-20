import { PatientDemographicsEntry } from 'libs/patient/demographics';
import { Params } from 'react-router';
import { demographics } from '../demographics';
import { description } from '../description';
import { evaluated } from './evaluated';

type LoaderParams = { params: Params<string> };

const loader = ({ params }: LoaderParams): Promise<PatientDemographicsEntry> =>
    description(Number(params.id))
        .then((patient) => demographics(patient.id))
        .then(evaluated);

export { loader };
