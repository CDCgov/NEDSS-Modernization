import { PatientFileService } from 'generated';
import { MemoizedSupplier } from 'libs/supplying';
import { demographics } from './demographics';
import { PatientFileData } from './usePatientFileData';
import { Patient } from './patient';

const description = (patientId: number): Promise<Patient> => PatientFileService.file({ patientId });

type LoaderParams = { params: { id: string } };

const loader = ({ params }: LoaderParams): Promise<PatientFileData> =>
    description(Number(params.id)).then((patient) => ({
        id: patient.id,
        patient,
        demographics: new MemoizedSupplier(() => demographics(patient.id))
    }));

export { loader };
