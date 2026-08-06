import { Params } from 'react-router';

import { MemoizedSupplier } from 'libs/supplying';

import { demographics } from './demographics';
import { description } from './description';
import { events } from './events';
import { summary } from './summary';
import { PatientFileData } from './usePatientFileData';

type LoaderParams = { params: Params<string> };

const loader = ({ params }: LoaderParams): Promise<PatientFileData> =>
    description(Number(params.id)).then((patient) => ({
        id: patient.id,
        patient,
        summary: new MemoizedSupplier(() => summary(patient.id)),
        events: new MemoizedSupplier(() => events(patient.id)),
        demographics: new MemoizedSupplier(() => demographics(patient.id)),
    }));

export { loader };
