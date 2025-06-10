import { Suspense } from 'react';
import { Await, Outlet, useLoaderData } from 'react-router';
import { RedirectHome } from 'routes';
import { Button } from 'design-system/button';
import { Spinner } from 'components/Spinner';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import { Patient } from './patient';
import { PatientLoaderResult } from './loader';
import { PatientFileLayout } from './PatientFileLayout';
import { DeleteAction } from './delete';

const PatientFile = () => {
    const data = useLoaderData<PatientLoaderResult>();

    return (
        <Suspense fallback={<Spinner />}>
            <Await resolve={data.patient} errorElement={<RedirectHome />}>
                {WithMeta}
            </Await>
        </Suspense>
    );
};

export { PatientFile };

const ViewActions = (patient: Patient) => {
    return (
        <>
            <DeleteAction />
            <Button
                onClick={openPrintableView(patient.id.toString())}
                aria-label="Print"
                data-tooltip-position="top"
                data-tooltip-offset="center"
                icon="print"
                sizing={'medium'}
                secondary
            />
            <Button aria-label="Edit" icon="edit" secondary labelPosition="right" sizing={'medium'}>
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

const WithMeta = (meta: Patient) => (
    <PatientFileLayout patient={meta} actions={ViewActions} navigation={ViewNavigation}>
        <Outlet />
    </PatientFileLayout>
);
