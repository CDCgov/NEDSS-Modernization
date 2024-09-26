import { Button, Icon } from '@trussworks/react-uswds';
import 'apps/patient/profile/style.scss';
import { Outlet, useParams } from 'react-router-dom';
import { usePatientProfile } from './usePatientProfile';
import { PatientProfileSummary } from './summary/PatientProfileSummary';

import { usePatientProfilePermissions } from './permission';

import { ProfileProvider } from './ProfileContext';
import { TabNavigationEntry, TabNavigation } from 'components/TabNavigation/TabNavigation';

import { DeletePatient } from './delete';

const openPrintableView = (patient: string | undefined) => () => {
    if (patient) {
        window.open(`/nbs/LoadViewFile1.do?method=ViewFile&ContextAction=print&uid=${patient}`, '_blank', 'noreferrer');
    }
};

enum ACTIVE_TAB {
    SUMMARY = 'Summary',
    EVENT = 'Events',
    DEMOGRAPHICS = 'Demographics'
}

export const PatientProfile = () => {
    const { id } = useParams();

    const permissions = usePatientProfilePermissions();

    const { patient, summary } = usePatientProfile(id);

    return (
        <ProfileProvider id={id}>
            <div className="height-full main-banner">
                <div className="padding-left-2 padding-right-1 bg-white grid-row flex-align-center flex-justify border-bottom-style">
                    <h1 className="font-sans-xl text-medium">Patient profile</h1>
                    <div>
                        <Button type={'button'} onClick={openPrintableView(patient?.id)}>
                            <Icon.Print size={3} />
                            Print
                        </Button>
                        {permissions.delete && patient && summary && (
                            <DeletePatient patient={patient} summary={summary} />
                        )}
                    </div>
                </div>
                <div className="main-body">
                    {patient && summary && <PatientProfileSummary summary={summary} patient={patient} />}

                    <TabNavigation className="grid-row flex-align-center margin-y-3">
                        <TabNavigationEntry path={`/patient-profile/${id}/summary`}>
                            {ACTIVE_TAB.SUMMARY}
                        </TabNavigationEntry>
                        <TabNavigationEntry path={`/patient-profile/${id}/events`}>
                            {ACTIVE_TAB.EVENT}
                        </TabNavigationEntry>
                        <TabNavigationEntry path={`/patient-profile/${id}/demographics`}>
                            {ACTIVE_TAB.DEMOGRAPHICS}
                        </TabNavigationEntry>
                    </TabNavigation>
                    <Outlet />

                    <div className="text-center margin-y-5">
                        <Button outline type={'button'} onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}>
                            <Icon.ArrowUpward className="margin-right-1" />
                            Back to top
                        </Button>
                    </div>
                </div>
            </div>
        </ProfileProvider>
    );
};
