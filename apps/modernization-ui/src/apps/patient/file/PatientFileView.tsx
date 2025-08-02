import { ReactNode } from 'react';
import { permissions, Permitted } from 'libs/permission';
import { Button } from 'design-system/button';
import { Sizing } from 'design-system/field';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import { Patient } from './patient';
import { PatientFileLayout } from './PatientFileLayout';
import { DeleteAction } from './delete';

type PatientFileViewProps = {
    patient: Patient;
    sizing?: Sizing;
    children: ReactNode | ReactNode[];
};

const PatientFileView = ({ patient, sizing, children }: PatientFileViewProps) => (
    <PatientFileLayout patient={patient} sizing={sizing} actions={ViewActions} navigation={ViewNavigation}>
        {children}
    </PatientFileLayout>
);

const ViewActions = (patient: Patient) => (
    <>
        <DeleteAction patient={patient} />
        <Button
            onClick={openPrintableView(patient.id)}
            aria-label="Print"
            data-tooltip-position="top"
            data-tooltip-offset="center"
            icon="print"
            sizing="medium"
            secondary
        />
        <Permitted permission={permissions.patient.update}>
            <Button aria-label="Edit" icon="edit" secondary sizing="medium">
                Edit
            </Button>
        </Permitted>
    </>
);

export { PatientFileView };

const ViewNavigation = (patient: Patient) => (
    <TabNavigation newTab>
        <TabNavigationEntry path={`/patient/${patient.patientId}/summary`}>Summary</TabNavigationEntry>
        <TabNavigationEntry path={`/patient/${patient.patientId}/events`}>Events</TabNavigationEntry>
        <TabNavigationEntry path={`/patient/${patient.patientId}/demographics`}>Demographics</TabNavigationEntry>
    </TabNavigation>
);

const openPrintableView = (patient: number) => () =>
    window.open(`/nbs/LoadViewFile1.do?method=ViewFile&ContextAction=print&uid=${patient}`, '_blank', 'noreferrer');
