import { PatientFileHeaderService } from 'generated';
import { Patient, Deletability } from './patient';

type LoaderParams = { params: { id: string } };
type PatientLoaderResult = { patient: Patient };

const asDeletability = (value: string): Deletability => {
    switch (value) {
        case 'Has_Associations':
        case 'Is_Inactive':
            return value;
        default:
            return 'Deletable';
    }
};

const loader = ({ params }: LoaderParams): Promise<PatientLoaderResult> =>
    PatientFileHeaderService.patientFileHeader({ patientId: Number(params.id) })
        .then((patient) => ({ ...patient, deletability: asDeletability(patient.deletablity) }) as Patient)
        .then((patient) => ({
            patient
        }));

export { loader };
export type { PatientLoaderResult };
