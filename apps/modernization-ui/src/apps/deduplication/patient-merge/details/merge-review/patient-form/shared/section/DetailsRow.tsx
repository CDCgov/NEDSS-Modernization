import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import styles from './section.module.scss';
import { ReactNode } from 'react';

type Props = {
    id: string;
    mergeCandidates: MergeCandidate[];
    render: (mergeCandidates: MergeCandidate) => ReactNode | undefined;
};
export const DetailsRow = ({ id, mergeCandidates, render }: Props) => {
    return (
        <section className={styles.section}>
            {mergeCandidates.map((p) => (
                <div key={`detailsSection-${id}-${p.personUid}`} className={styles.entry}>
                    {render(p)}
                </div>
            ))}
        </section>
    );
};
