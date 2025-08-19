import { AlertMessage } from 'design-system/message';
import { useNavigate } from 'react-router';
import { permissions, Permitted } from 'libs/permission';
import { usePatientMergeQueueStatus } from './api/usePatientMergeQueueStatus';
import { FeatureToggle } from 'feature';

type MergeAlertProps = {
    patientId: string;
};

export const MergeAlert = ({ patientId }: MergeAlertProps) => {
    const nav = useNavigate();
    const { inMergeQueue, mergeGroup, loading: mergeQueueLoading } = usePatientMergeQueueStatus(patientId);

    return (
        <FeatureToggle guard={(features) => features.patient.file.mergeHistory?.enabled}>
            {inMergeQueue && !mergeQueueLoading && (
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
            )}
        </FeatureToggle>
    );
};
