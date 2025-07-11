import { lazy, Suspense } from 'react';
import { Navigate } from 'react-router';
import { permissions } from 'libs/permission';
import { PageTitle } from 'page';
import { Guarded } from 'libs/guard';
import { loader } from './loader';

const LazyPatientFile = lazy(() => import('./PatientFile').then((module) => ({ default: module.PatientFile })));
const LazyPatientFileSummary = lazy(() =>
    import('./summary/PatientFileSummary').then((module) => ({ default: module.PatientFileSummary }))
);
const LazyPatientFileEvents = lazy(() => import('./events').then((module) => ({ default: module.PatientFileEvents })));
const LazyPatientFileDemographics = lazy(() =>
    import('./demographics').then((module) => ({ default: module.PatientFileDemographics }))
);

const routing = [
    {
        path: '/patient/:id',
        element: (
            <Guarded feature={(features) => features.patient.file.enabled} permission={permissions.patient.file}>
                <PageTitle title="Patient file">
                    <Suspense>
                        <LazyPatientFile />
                    </Suspense>
                </PageTitle>
            </Guarded>
        ),
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
            }
        ]
    }
];

export { routing };
