

import { routing as deduplicationRouting } from 'apps/deduplication';
import { routing as welcomeRouting } from 'apps/landing';
import { Expired } from 'apps/landing/Expired/Expired';
import { routing as pageBuilderRouting } from 'apps/page-builder';
import { routing as addPatientRouting } from 'apps/patient/add';
import { routing as patientFileRouting } from 'apps/patient/file/PatientFileRouting';
import { routing as reportRouting } from 'apps/report';
import { loadReportResult, ResultDataPage } from 'apps/report/run/ResultDataPage';
import { routing as searchRouting } from 'apps/search';
import { routing as systemManagementRouting } from 'apps/system-management';
import { initializationLoader, ProtectedLayout } from 'authorization';
import { LoadingBlock } from 'libs/loading/block';
import { routing as logoutRouting } from 'logout';
import { PageProvider } from 'page';
import { ErrorPage } from 'pages/error/ErrorPage';
import { Login } from 'pages/login';
import { createBrowserRouter, RouteObject, RouterProvider } from 'react-router';

import { RedirectHome } from './RedirectHome';

const routing: RouteObject[] = [
    welcomeRouting,
    logoutRouting,
    {
        path: '/login',
        element: (
            <PageProvider>
                <Login />
            </PageProvider>
        ),
    },
    {
        path: '/',
        element: <ProtectedLayout />,
        loader: initializationLoader,
        HydrateFallback: LoadingBlock,
        ErrorBoundary: ErrorPage,
        children: [
            { index: true, element: <RedirectHome /> },
            ...searchRouting,
            ...addPatientRouting,
            ...pageBuilderRouting,
            ...deduplicationRouting,
            ...patientFileRouting,
            ...systemManagementRouting,
            ...reportRouting,
        ],
    },
    // This path is for rendering report results. It opens in a new tab and relies on
    // being called in sequence from the report configuration page that loads a result
    // with the ID into local storage and then opens the tab pointing here. That tab
    // cleans up the stored data, so it does not stay around. It is purposefully
    // un-protected as of now as the UX of the timeout is annoying, does not match 6,
    // does not present a true breech opportunity to access any more of the system,
    // and also loads the "Back to NBS" screen, which would encourage multiple tabs :'(
    {
        path: '/report/result/:resultId',
        element: <ResultDataPage />,
        loader: loadReportResult,
        HydrateFallback: LoadingBlock,
        ErrorBoundary: ErrorPage,
    },
    { path: 'expired', element: <Expired /> },
];

const router = createBrowserRouter(routing);

export const AppRoutes = () => <RouterProvider router={router} />;
