import { AddPatient } from './AddPatient';
import { AddPatientExtended } from './extended/AddPatientExtended';

const routing = [
    {
        path: '/add-patient',
        element: <AddPatient />
    },
    {
        path: '/patient/add/extended',
        element: <AddPatientExtended />
    }
];

export { routing };
