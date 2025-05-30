import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import { Section } from '../shared/section/Section';
import { AdminComment } from './admin-comment/AdminComment';

type Props = {
    patientData: MergePatient[];
};
export const AdminCommentsSelection = ({ patientData }: Props) => {
    return (
        <Section
            title="ADMINISTRATIVE COMMENTS"
            patientData={patientData}
            render={(p) => <AdminComment personUid={p.personUid} adminComments={p.adminComments} />}
        />
    );
};
