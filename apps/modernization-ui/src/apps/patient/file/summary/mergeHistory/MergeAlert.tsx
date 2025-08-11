import { AlertMessage } from 'design-system/message';
import { useNavigate } from 'react-router';
import { permissions, Permitted } from 'libs/permission';

type MergeAlertProps = {
    mergeGroup: number | null;
    patientId: string;
};

export const MergeAlert = ({ mergeGroup, patientId }: MergeAlertProps) => {
    const nav = useNavigate();

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
