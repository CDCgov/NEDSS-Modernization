import { Outlet, useLoaderData } from 'react-router';
import { PageTitle } from 'page';
import { ComponentSizingProvider } from 'design-system/sizing';

import { PatientFileProvider, PatientFileData } from './usePatientFileData';

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
