import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { initializationLoader, ProtectedLayout } from 'authorization';

import { RedirectHome } from './RedirectHome';

import { routing as searchRouting } from 'apps/search';
import { routing as patientProfileRouting } from 'apps/patient/profile';
import { routing as addPatientRouting } from 'apps/patient/add';
import { routing as pageBuilderRouting } from 'apps/page-builder';
import { routing as welcomeRouting } from 'apps/landing';
import { routing as logoutRouting } from 'logout';
import { Login } from 'pages/login';
import { Expired } from 'apps/landing/Expired/Expired';
import { DesignSystem } from 'design-system/DesignSystem';

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
            ...patientProfileRouting,
            ...pageBuilderRouting
        ]
    },
    { path: '*', element: <RedirectHome /> },
    { path: 'expired', element: <Expired /> }
];

if (process.env.NODE_ENV === 'development') {
    routing.push({ path: '/designSystem', element: <DesignSystem /> });
}

const router = createBrowserRouter(routing);

export const AppRoutes = () => <RouterProvider router={router} />;
