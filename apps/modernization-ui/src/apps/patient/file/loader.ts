import { PatientFileService } from 'generated';
import { Patient } from './patient';

type LoaderParams = { params: { id: string } };
type PatientLoaderResult = { patient: Patient };

const loader = ({ params }: LoaderParams): Promise<PatientLoaderResult> =>
    PatientFileService.file({ patientId: Number(params.id) }).then((patient) => ({
        patient
    }));

export { loader };
export type { PatientLoaderResult };
