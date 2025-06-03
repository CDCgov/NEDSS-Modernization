import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Section } from '../shared/section/Section';
import { SexAndBirth } from './sex-and-birth/SexAndBirth';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const SexAndBirthSelection = ({ mergeCandidates }: Props) => {
    return (
        <Section
            title="SEX & BIRTH"
            mergeCandidates={mergeCandidates}
            render={(p) => <SexAndBirth personUid={p.personUid} sexAndBirth={p.sexAndBirth} />}
        />
    );
};
