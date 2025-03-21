import { Outlet, useParams } from 'react-router';
import { usePatientProfilePermissions } from './permission';
import { ProfileProvider } from './ProfileContext';
import { usePatientProfile } from './usePatientProfile';
import { Icon } from 'design-system/icon';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { TabNavigationEntry, TabNavigation } from 'components/TabNavigation/TabNavigation';
import { PatientProfileSummary } from './summary/PatientProfileSummary';

import { DeletePatient } from './delete';

import styles from './patient-profile.module.scss';

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
            <div className={styles.profile}>
                <header>
                    <Heading level={1}>Patient profile</Heading>
                    <div className={styles.actions}>
                        <Button
                            type={'button'}
                            onClick={openPrintableView(patient?.id)}
                            icon={<Icon name="print" sizing="small" />}
                            labelPosition="right">
                            Print
                        </Button>
                        {permissions.delete && patient && <DeletePatient patient={patient} />}
                    </div>
                </header>
                <main className="main-body">
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
                        <Button
                            outline
                            type={'button'}
                            icon={<Icon name="arrow_upward" />}
                            onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}>
                            Back to top
                        </Button>
                    </div>
                </main>
            </div>
        </ProfileProvider>
    );
};
