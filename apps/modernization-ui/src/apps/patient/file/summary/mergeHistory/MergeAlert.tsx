import { AlertMessage } from 'design-system/message';
import { useNavigate } from 'react-router';
import { permissions, Permitted } from 'libs/permission';
import { FeatureToggle } from 'feature';
import { usePatientMergeQueueStatus } from './api/usePatientMergeQueueStatus';

type MergeAlertProps = {
    patientId?: string;
};

export const MergeAlert = ({ patientId }: MergeAlertProps) => {
    return (
        <FeatureToggle guard={(features) => features.patient.file.mergeHistory?.enabled}>
            <MergeAlertContent patientId={patientId} />
        </FeatureToggle>
    );
};

const MergeAlertContent = ({ patientId }: { patientId?: string }) => {
    const nav = useNavigate();
    const { inMergeQueue, mergeGroup, loading } = usePatientMergeQueueStatus(patientId);

    if (loading || !inMergeQueue) return null;

    return (
        <Permitted permission={permissions.patient.merge}>
            <AlertMessage type="warning" slim>
                We found potential duplicates for this patient in system-identified matches.&nbsp;&nbsp;&nbsp;
                <a
                    href={`/deduplication/merge/${mergeGroup}`}
                    onClick={(e) => {
                        e.preventDefault();
                        nav(`/deduplication/merge/${mergeGroup}`, {
                            state: { fromPatientFileSummary: true, patientId }
                        });
                    }}>
                    Review Matches
                </a>
            </AlertMessage>
        </Permitted>
    );
};
