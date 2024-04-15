import { Navigate } from 'react-router-dom';
import { PatientProfile } from './PatientProfile';
import { Summary } from './Summary';
import { Events } from './Events';
import { Demographics } from './Demographics';

const routing = [
    {
        path: '/patient-profile/:id',
        element: <PatientProfile />,
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
