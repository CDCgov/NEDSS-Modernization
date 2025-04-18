import { FeatureGuard } from 'feature';
import { PatientDataEntryProvider } from './PatientDataEntryProvider';
import { AddPatientExtended } from './extended/AddPatientExtended';
import { AddPatientBasic } from './basic/AddPatientBasic';

const routing = [
    {
        element: <PatientDataEntryProvider />,
        children: [
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
                element:  <AddPatientExtended />
            }
        ]
    }
];

export { routing };
