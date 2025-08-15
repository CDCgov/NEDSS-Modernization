import { ReactNode } from 'react';
import { permissions, Permitted } from 'libs/permission';
import { Button, NavLinkButton } from 'design-system/button';
import { Sizing } from 'design-system/field';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import { BackToTop } from 'libs/page/back-to-top';
import { Patient } from './patient';
import { PatientFileLayout } from './PatientFileLayout';
import { DeleteAction } from './delete';

import styles from './patient-file-view.module.scss';
import { useLocation } from 'react-router';

type PatientFileViewProps = {
    patient: Patient;
    sizing?: Sizing;
    children: ReactNode | ReactNode[];
};

const PatientFileView = ({ patient, sizing, children }: PatientFileViewProps) => (
    <PatientFileLayout patient={patient} actions={ViewActions} navigation={ViewNavigation}>
        <div className={styles.content}>
            {children}
            <BackToTop sizing={sizing} />
        </div>
    </PatientFileLayout>
);

const ViewActions = (patient: Patient) => {
    const { pathname } = useLocation();
    return (
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
                <NavLinkButton icon="edit" secondary sizing="medium" to="../edit" state={{ return: pathname }}>
                    Edit
                </NavLinkButton>
            </Permitted>
        </>
    );
};

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
