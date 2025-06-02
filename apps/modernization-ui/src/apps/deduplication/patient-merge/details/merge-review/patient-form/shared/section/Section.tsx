import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { ReactNode } from 'react';
import { SectionLabel } from '../section-label/SectionLabel';
import styles from './section.module.scss';

type Props = {
    title: string;
    mergeCandidates: MergeCandidate[];
    render: (patientData: MergeCandidate) => ReactNode;
};
export const Section = ({ title, mergeCandidates, render }: Props) => {
    return (
        <section className={styles.section}>
            {mergeCandidates.map((p) => (
                <div key={`section-${title}-${p.personUid}`} className={styles.entry}>
                    <SectionLabel label={title} />
                    {render(p)}
                </div>
            ))}
        </section>
    );
};
