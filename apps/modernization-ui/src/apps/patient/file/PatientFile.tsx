import { Suspense } from 'react';
import { Await, Outlet, useLoaderData } from 'react-router';
import { RedirectHome } from 'routes';
import { Spinner } from 'components/Spinner';
import { ComponentSizingProvider } from 'design-system/sizing';

import { PatientFileProvider, PatientFileData } from './usePatientFileData';

const PatientFile = () => {
    const data = useLoaderData<PatientFileData>();

    return (
        <Suspense fallback={<Spinner />}>
            <Await resolve={data} errorElement={<RedirectHome />}>
                {(data: PatientFileData) => (
                    <PatientFileProvider data={data}>
                        <ComponentSizingProvider>
                            <Outlet />
                        </ComponentSizingProvider>
                    </PatientFileProvider>
                )}
            </Await>
        </Suspense>
    );
};

export { PatientFile };
