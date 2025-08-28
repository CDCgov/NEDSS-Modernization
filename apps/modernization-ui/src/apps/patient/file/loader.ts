import { MemoizedSupplier } from 'libs/supplying';
import { PatientFileData } from './usePatientFileData';
import { summary } from './summary';
import { events } from './events';
import { demographics } from './demographics';
import { description } from './description';
import { Params } from 'react-router';

type LoaderParams = { params: Params<string> };

const loader = ({ params }: LoaderParams): Promise<PatientFileData> =>
    description(Number(params.id)).then((patient) => ({
        id: patient.id,
        patient,
        summary: new MemoizedSupplier(() => summary(patient.id)),
        events: new MemoizedSupplier(() => events(patient.id)),
        demographics: new MemoizedSupplier(() => demographics(patient.id))
    }));

export { loader };
