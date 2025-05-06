import { FeatureGuard } from 'feature';
import { PatientFile } from './PatientFile';
import { PageTitle } from 'page';
import { Navigate } from 'react-router';
import { PatientFileSummary } from './summary/PatientFileSummary';

const routing = [
    {
        path: '/patient/:id',
        element: (
            <FeatureGuard guard={(features) => features.patient.file.enabled}>
                <PageTitle title="Patient file">
                    <PatientFile />
                </PageTitle>
            </FeatureGuard>
        ),
        children: [
            { path: '', index: true, element: <Navigate to="summary" /> },
            {
                path: 'summary',
                element: <PatientFileSummary />
            },
            {
                path: 'events',
                element: <div></div>
            },
            {
                path: 'demographics',
                element: <div></div>
            }
        ]
    }
];

export { routing };
