import { FeatureGuard } from 'feature';
import { AddPatient } from './AddPatient';
import { AddPatientExtended } from './extended/AddPatientExtended';
import { PatientDataProvider } from './usePatientData/usePatientData';
import { Outlet } from 'react-router-dom';

const PatientDataProviderWrapper = () => (
    <PatientDataProvider>
        <Outlet />
    </PatientDataProvider>
);

const routing = [
    {
        element: <PatientDataProviderWrapper />,
        children: [
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
        ]
    }
];

export { routing };
