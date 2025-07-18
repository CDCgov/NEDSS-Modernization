import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Section } from '../shared/section/Section';
import { AdminComment } from './admin-comment/AdminComment';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const AdminCommentsSelection = ({ mergeCandidates }: Props) => {
    return (
        <Section
            title="ADMINISTRATIVE"
            mergeCandidates={mergeCandidates}
            render={(p) => <AdminComment personUid={p.personUid} adminComments={p.adminComments} />}
        />
    );
};
