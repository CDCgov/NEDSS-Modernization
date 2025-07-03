import { Suspense } from 'react';
import { Await, Outlet, useLoaderData } from 'react-router';
import { RedirectHome } from 'routes';
import { Spinner } from 'components/Spinner';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { Patient } from './patient';
import { PatientFileLayout } from './PatientFileLayout';
import { DeleteAction } from './delete';
import { PatientFileProvider, PatientFileData } from './usePatientFileData';

const PatientFile = () => {
    const data = useLoaderData<PatientFileData>();

    return (
        <Suspense fallback={<Spinner />}>
            <Await resolve={data} errorElement={<RedirectHome />}>
                {(data: PatientFileData) => (
                    <PatientFileProvider data={data}>
                        <PatientFileLayout patient={data.patient} actions={ViewActions} navigation={ViewNavigation}>
                            <Outlet />
                        </PatientFileLayout>
                    </PatientFileProvider>
                )}
            </Await>
        </Suspense>
    );
};

export { PatientFile };

const ViewActions = (patient: Patient) => {
    return (
        <>
            <DeleteAction patient={patient} />
            <Button
                onClick={openPrintableView(patient.id.toString())}
                aria-label="Print"
                data-tooltip-position="top"
                data-tooltip-offset="center"
                icon={<Icon name="print" />}
                sizing={'medium'}
                secondary
            />
            <Button aria-label="Edit" icon={<Icon name="edit" />} secondary labelPosition="right" sizing={'medium'}>
                Edit
            </Button>
        </>
    );
};

const ViewNavigation = (patient: Patient) => (
    <TabNavigation newTab>
        <TabNavigationEntry path={`/patient/${patient.patientId}/summary`}>Summary</TabNavigationEntry>
        <TabNavigationEntry path={`/patient/${patient.patientId}/events`}>Events</TabNavigationEntry>
        <TabNavigationEntry path={`/patient/${patient.patientId}/demographics`}>Demographics</TabNavigationEntry>
    </TabNavigation>
);

const openPrintableView = (patient: string | undefined) => () => {
    if (patient) {
        window.open(`/nbs/LoadViewFile1.do?method=ViewFile&ContextAction=print&uid=${patient}`, '_blank', 'noreferrer');
    }
};
