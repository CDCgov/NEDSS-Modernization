import { createBrowserRouter, Navigate, RouterProvider } from 'react-router-dom';
import { initializationLoader, ProtectedLayout } from 'authorization';

import { RedirectHome } from './RedirectHome';

import { routing as searchRouting } from 'apps/search';
import { routing as patientProfileRouting } from 'apps/patient/profile';
import { routing as addPatientRouting } from 'apps/patient/add';
import { routing as pageBuilderRouting } from 'apps/page-builder';
import { routing as loginRouting } from 'loginLayout/loginRouting';
import { LoginLayout } from 'loginLayout';

const routing = [
    {
        path: '/login',
        element: <LoginLayout />,
        children: [{ index: true, element: <Navigate to="/login/about" /> }, ...loginRouting]
    },
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
