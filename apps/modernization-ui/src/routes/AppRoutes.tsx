import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { initializationLoader, ProtectedLayout } from 'authorization';

import { Login } from 'pages/login';
import { RedirectHome } from './RedirectHome';

import { routing as searchRouting } from 'apps/search';
import { routing as patientProfileRouting } from 'apps/patient/profile';
import { routing as addPatientRouting } from 'apps/patient/add';
import { routing as pageBuilderRouting } from 'apps/page-builder';

const routing = [
    { path: '/login', element: <Login /> },
    {
        path: '/',
        element: <ProtectedLayout />,
        loader: initializationLoader,
        children: [
            { index: true, element: <RedirectHome /> },
            ...searchRouting,
            ...addPatientRouting,
            ...patientProfileRouting,
            ...pageBuilderRouting
        ]
    },
    { path: '*', element: <RedirectHome /> }
];

const router = createBrowserRouter(routing);

export const AppRoutes = () => <RouterProvider router={router} />;
