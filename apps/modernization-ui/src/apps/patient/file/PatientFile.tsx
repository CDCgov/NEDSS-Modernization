import { Outlet, useLoaderData } from 'react-router';

import { ComponentSizingProvider } from 'design-system/sizing';
import { PageTitle } from 'page';

import { PatientFileData, PatientFileProvider } from './usePatientFileData';

const PatientFile = () => {
    const data = useLoaderData<PatientFileData>();

    return (
        <ComponentSizingProvider>
            <PageTitle title="Patient file" />
            <PatientFileProvider data={data} key={data.id}>
                <Outlet />
            </PatientFileProvider>
        </ComponentSizingProvider>
    );
};

export { PatientFile };
