import { Navigate, RouteObject } from 'react-router';
import { RedirectHome } from 'routes';

const routing: RouteObject[] = [
    {
        path: '/patient/:id',
        lazy: {
            loader: async () => (await import('./loader')).loader,
            Component: async () => (await import('./GuardedPatientFile')).GuardedPatientFile
        },
        errorElement: <RedirectHome />,
        children: [
            { index: true, element: <Navigate to="summary" replace /> },
            {
                path: 'summary',
                lazy: {
                    Component: async () => (await import('./summary/PatientFileSummary')).PatientFileSummary
                }
            },
            {
                path: 'events',
                lazy: {
                    Component: async () => (await import('./events')).PatientFileEvents
                }
            },
            {
                path: 'demographics',
                lazy: {
                    Component: async () => (await import('./demographics')).PatientFileDemographics
                }
            },
            {
                path: 'edit',
                lazy: {
                    Component: async () => (await import('./edit')).GuardedPatientFileEdit
                }
            }
        ]
    }
];

export { routing };
