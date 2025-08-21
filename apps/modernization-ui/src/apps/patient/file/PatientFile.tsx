import { Suspense } from 'react';
import { Await, Outlet, useLoaderData } from 'react-router';
import { RedirectHome } from 'routes';
import { Spinner } from 'components/Spinner';
import { PageTitle } from 'page';
import { ComponentSizingProvider } from 'design-system/sizing';

import { PatientFileProvider, PatientFileData } from './usePatientFileData';

const PatientFile = () => {
    const data = useLoaderData<PatientFileData>();

    return (
        <ComponentSizingProvider>
            <PageTitle title="Patient file" />
            <Suspense fallback={<Spinner />} key={data.id}>
                <Await resolve={data} errorElement={<RedirectHome />}>
                    {(data: PatientFileData) => (
                        <PatientFileProvider data={data}>
                            <Outlet />
                        </PatientFileProvider>
                    )}
                </Await>
            </Suspense>
        </ComponentSizingProvider>
    );
};

export { PatientFile };
