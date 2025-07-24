import { PatientFileService } from 'generated';
import { MemoizedSupplier } from 'libs/supplying';
import { PatientFileData } from './usePatientFileData';
import { Patient } from './patient';
import { summary } from './summary';
import { events } from './events';
import { demographics } from './demographics';

const description = (patientId: number): Promise<Patient> => PatientFileService.file({ patientId });

type LoaderParams = { params: { id: string } };

const loader = ({ params }: LoaderParams): Promise<PatientFileData> =>
    description(Number(params.id)).then((patient) => ({
        id: patient.id,
        patient,
        summary: new MemoizedSupplier(() => summary(patient.id)),
        events: new MemoizedSupplier(() => events(patient.id)),
        demographics: new MemoizedSupplier(() => demographics(patient.id))
    }));

export { loader };
