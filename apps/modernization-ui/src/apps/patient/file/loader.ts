import { PatientFileService } from 'generated';
import { MemoizedSupplier } from 'libs/supplying';
import { PatientFileData } from './usePatientFileData';
import { Patient } from './patient';
import { summary } from './summary';
import { demographics } from './demographics';

const description = (patientId: number): Promise<Patient> => PatientFileService.file({ patientId });

type LoaderParams = { params: { id: string } };

const loader = ({ params }: LoaderParams): Promise<PatientFileData> =>
    description(Number(params.id)).then((patient) => ({
        id: patient.id,
        patient,
        demographics: new MemoizedSupplier(() => demographics(patient.id)),
        summary: new MemoizedSupplier(() => summary(patient.id))
    }));

export { loader };
