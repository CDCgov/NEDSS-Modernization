import { FeatureGuard } from 'feature';
import { AddPatient } from './AddPatient';
import { AddPatientExtended } from './extended/AddPatientExtended';

const routing = [
    {
        path: '/add-patient',
        element: <AddPatient />
    },
    {
        path: '/patient/add/extended',
        element: (
            <FeatureGuard guard={(features) => features?.patient?.add?.extended?.enabled}>
                <AddPatientExtended />
            </FeatureGuard>
        )
    }
];

export { routing };
