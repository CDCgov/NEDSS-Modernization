import { MergeInvestigation } from 'apps/deduplication/api/model/MergeCandidate';

type Props = {
    investigations: MergeInvestigation[];
};
export const Investigation = ({ investigations }: Props) => {
    return <div>{investigations.map((i) => i.condition)}</div>;
};
