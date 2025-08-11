import { Suspense } from 'react';
import { Await, Outlet, useLoaderData } from 'react-router';
import { RedirectHome } from 'routes';
import { Spinner } from 'components/Spinner';
import { ComponentSizingProvider } from 'design-system/sizing';

import { PatientFileProvider, PatientFileData } from './usePatientFileData';
import { AlertProvider } from 'libs/alert';

const PatientFile = () => {
    const data = useLoaderData<PatientFileData>();

    return (
        <Suspense fallback={<Spinner />} key={data.id}>
            <Await resolve={data} errorElement={<RedirectHome />}>
                {(data: PatientFileData) => (
                    <PatientFileProvider data={data}>
                        <AlertProvider>
                            <ComponentSizingProvider>
                                <Outlet />
                            </ComponentSizingProvider>
                        </AlertProvider>
                    </PatientFileProvider>
                )}
            </Await>
        </Suspense>
    );
};

export { PatientFile };
