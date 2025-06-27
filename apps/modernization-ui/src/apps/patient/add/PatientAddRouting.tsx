import { PatientDataEntryProvider } from './PatientDataEntryProvider';
import { AddPatientExtended } from './extended/AddPatientExtended';
import { AddPatientBasic } from './basic/AddPatientBasic';
import { PageTitle } from 'page';

const routing = [
    {
        element: <PatientDataEntryProvider />,
        children: [
            {
                path: '/patient/add',
                element: (
                    <PageTitle title="Add patient">
                        <AddPatientBasic />
                    </PageTitle>
                )
            },
            {
                path: '/patient/add/extended',
                element: (
                    <PageTitle title="Add patient extended">
                        <AddPatientExtended />
                    </PageTitle>
                )
            }
        ]
    }
];

export { routing };
