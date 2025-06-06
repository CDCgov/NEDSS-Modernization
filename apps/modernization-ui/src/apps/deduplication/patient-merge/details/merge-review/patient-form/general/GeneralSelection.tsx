import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Section } from '../shared/section/Section';
import { GeneralInfo } from './general-info/GeneralInfo';

type Props = {
    mergeCandidates: MergeCandidate[];
};
export const GeneralSelection = ({ mergeCandidates }: Props) => {
    return (
        <Section
            title="GENERAL PATIENT INFORMATION"
            mergeCandidates={mergeCandidates}
            render={(p) => <GeneralInfo personUid={p.personUid} generalInfo={p.general} />}
        />
    );
};
