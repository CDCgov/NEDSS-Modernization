import { lazy, Suspense } from 'react';
import { Navigate } from 'react-router';
import { permissions, Permitted } from 'libs/permission';
import { PageTitle } from 'page';
import { Guarded } from 'libs/guard';
import { RedirectHome } from 'routes';
import { loader } from './loader';

const LazyPatientFile = lazy(() => import('./PatientFile').then((module) => ({ default: module.PatientFile })));
const LazyPatientFileSummary = lazy(() =>
    import('./summary/PatientFileSummary').then((module) => ({ default: module.PatientFileSummary }))
);
const LazyPatientFileEvents = lazy(() => import('./events').then((module) => ({ default: module.PatientFileEvents })));
const LazyPatientFileDemographics = lazy(() =>
    import('./demographics').then((module) => ({ default: module.PatientFileDemographics }))
);

const LazyPatientFileEdit = lazy(() => import('./edit').then((module) => ({ default: module.PatientFileEdit })));

const routing = [
    {
        path: '/patient/:id',
        element: (
            <Guarded
                feature={(features) => features.patient.file.enabled}
                permission={permissions.patient.file}
                fallback={<RedirectHome />}>
                <Suspense>
                    <PageTitle title="Patient file">
                        <LazyPatientFile />
                    </PageTitle>
                </Suspense>
            </Guarded>
        ),
        errorElement: <RedirectHome />,
        loader,
        children: [
            { index: true, element: <Navigate to="summary" replace /> },
            {
                path: 'summary',
                element: (
                    <Suspense>
                        <LazyPatientFileSummary />
                    </Suspense>
                )
            },
            {
                path: 'events',
                element: (
                    <Suspense>
                        <LazyPatientFileEvents />
                    </Suspense>
                )
            },
            {
                path: 'demographics',
                element: (
                    <Suspense>
                        <LazyPatientFileDemographics />
                    </Suspense>
                )
            },
            {
                path: 'edit',
                element: (
                    <Permitted permission={permissions.patient.update} fallback={<RedirectHome />}>
                        <Suspense>
                            <LazyPatientFileEdit />
                        </Suspense>
                    </Permitted>
                )
            }
        ]
    }
];

export { routing };
