import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Section } from '../shared/section/Section';
import { Investigation } from './investigation/Investigation';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const InvestigationDisplay = ({ mergeCandidates }: Props) => {
    return (
        <Section
            title="INVESTIGATIONS"
            mergeCandidates={mergeCandidates}
            render={(p) => <Investigation investigations={p.investigations} />}
        />
    );
};
