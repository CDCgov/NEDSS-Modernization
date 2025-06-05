import { createBrowserRouter, Navigate, RouterProvider } from 'react-router';
import { initializationLoader, ProtectedLayout } from 'authorization';

import { RedirectHome } from './RedirectHome';

import { routing as searchRouting } from 'apps/search';
import { routing as addPatientRouting } from 'apps/patient/add';
import { routing as pageBuilderRouting } from 'apps/page-builder';
import { routing as deduplicationRouting } from 'apps/deduplication';
import { routing as welcomeRouting } from 'apps/landing';
import { routing as systemManagementRouting } from 'apps/system-management';
import { routing as logoutRouting } from 'logout';
import { Login } from 'pages/login';
import { Expired } from 'apps/landing/Expired/Expired';
import { routing as patientFileRouting } from 'apps/patient/file/PatientFileRouting';

const routing = [
    welcomeRouting,
    logoutRouting,
    { path: '/login', element: <Login /> },
    {
        path: '/',
        element: <ProtectedLayout />,
        loader: initializationLoader,
        children: [
            { index: true, element: <RedirectHome /> },
            ...searchRouting,
            ...addPatientRouting,
            ...pageBuilderRouting,
            ...deduplicationRouting,
            ...patientFileRouting,
            ...systemManagementRouting
        ]
    },
    { path: '*', element: <Navigate to={'/'} /> },
    { path: 'expired', element: <Expired /> }
];

const router = createBrowserRouter(routing);

export const AppRoutes = () => <RouterProvider router={router} />;
