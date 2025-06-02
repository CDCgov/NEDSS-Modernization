import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Section } from '../shared/section/Section';
import { Ethnicity } from './ethnicity/Ethnicity';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const EthnicitySelection = ({ mergeCandidates }: Props) => {
    return (
        <Section
            title="ETHNICITY"
            mergeCandidates={mergeCandidates}
            render={(p) => <Ethnicity personUid={p.personUid} ethnicity={p.ethnicity} />}
        />
    );
};
