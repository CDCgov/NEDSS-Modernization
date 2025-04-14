import { FeatureGuard } from 'feature';
import { PatientDataEntryProvider } from './PatientDataEntryProvider';
import { AddPatient } from './AddPatient';
import { AddPatientExtended } from './extended';
import { AddPatientBasic } from './basic/AddPatientBasic';

const routing = [
    {
        element: <PatientDataEntryProvider />,
        children: [
            {
                path: '/add-patient',
                element: <AddPatient />
            },
            {
                path: '/patient/add',
                element: (
                    <FeatureGuard guard={(features) => features?.patient?.add?.enabled}>
                        <AddPatientBasic />
                    </FeatureGuard>
                )
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
