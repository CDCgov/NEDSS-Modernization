import { PatientDataEntryProvider } from './PatientDataEntryProvider';
import { AddPatientExtended } from './extended/AddPatientExtended';
import { AddPatientBasic } from './basic/AddPatientBasic';

const routing = [
    {
        element: <PatientDataEntryProvider />,
        children: [
            {
                path: '/patient/add',
                element: <AddPatientBasic />
            },
            {
                path: '/patient/add/extended',
                element: <AddPatientExtended />
            }
        ]
    }
];

export { routing };
