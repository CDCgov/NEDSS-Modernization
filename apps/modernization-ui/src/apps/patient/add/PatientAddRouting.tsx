import { FeatureGuard } from 'feature';
import { AddPatient } from './AddPatient';
import { AddPatientExtended } from './extended/AddPatientExtended';
import { PatientDataProvider } from './usePatientData/usePatientData';

const routing = [
    {
        path: '/add-patient',
        element: (
            <PatientDataProvider>
                <AddPatient />
            </PatientDataProvider>
        )
    },
    {
        path: '/patient/add/extended',
        element: (
            <PatientDataProvider>
                <FeatureGuard guard={(features) => features?.patient?.add?.extended?.enabled}>
                    <AddPatientExtended />
                </FeatureGuard>
            </PatientDataProvider>
        )
    }
];

export { routing };
