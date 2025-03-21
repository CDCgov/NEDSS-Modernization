import { Navigate } from 'react-router';
import { PatientProfile } from './PatientProfile';
import { Summary } from './Summary';
import { Events } from './Events';
import { Demographics } from './Demographics';
import { FeatureGuard } from 'feature';

const routing = [
    {
        path: '/patient-profile/:id',
        element: (
            <FeatureGuard guard={(features) => features.patient.profile.enabled}>
                <PatientProfile />
            </FeatureGuard>
        ),
        children: [
            { path: '', element: <Navigate to="summary" /> },
            {
                path: 'summary',
                element: <Summary />
            },
            {
                path: 'events',
                element: <Events />
            },
            {
                path: 'demographics',
                element: <Demographics />
            }
        ]
    }
];

export { routing };
