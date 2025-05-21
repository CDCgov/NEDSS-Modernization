import { PatientData } from 'apps/deduplication/api/model/PatientData';
import { SectionLabel } from '../shared/section-label/SectionLabel';
import styles from './admin-comment-selection.module.scss';
import { AdminComment } from './admin-comment/AdminComment';

type Props = {
    patientData: PatientData[];
};
export const AdminCommentsSelection = ({ patientData }: Props) => {
    return (
        <section className={styles.adminCommentSelection}>
            {patientData.map((p) => (
                <div key={`admin-comment: $${p.personUid}`} className={styles.entry}>
                    <SectionLabel label="ADMINISTRATIVE COMMENTS" />
                    <AdminComment personUid={p.personUid} adminComments={p.adminComments} />
                </div>
            ))}
        </section>
    );
};
