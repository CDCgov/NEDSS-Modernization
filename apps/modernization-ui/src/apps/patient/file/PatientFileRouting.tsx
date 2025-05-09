import { Navigate } from 'react-router';
import { FeatureGuard } from 'feature';
import { PageTitle } from 'page';
import { PatientFile } from './PatientFile';
import { loader } from './loader';
import PatientFileSummary from './PatientFileSummary';

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
        loader,
        children: [
            { path: '', index: true, element: <Navigate to="summary" /> },
            {
                path: 'summary',
                element: (
                    <div>
                        <PatientFileSummary />
                    </div>
                )
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
