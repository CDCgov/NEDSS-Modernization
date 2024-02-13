import { AddPatient } from './AddPatient';
import { AddedPatient } from './SuccessForm/AddedPatient';

const routing = [
    {
        path: '/add-patient',
        element: <AddPatient />
    },
    {
        path: '/add-patient/patient-added',
        element: <AddedPatient />
    }
];

export { routing };
