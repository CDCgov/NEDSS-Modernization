import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import { ReactNode } from 'react';
import { SectionLabel } from '../section-label/SectionLabel';
import styles from './section.module.scss';

type Props = {
    title: string;
    mergePatients: MergePatient[];
    render: (patientData: MergePatient) => ReactNode;
};
export const Section = ({ title, mergePatients, render }: Props) => {
    return (
        <section className={styles.section}>
            {mergePatients.map((p) => (
                <div key={`section-${title}-${p.personUid}`} className={styles.entry}>
                    <SectionLabel label={title} />
                    {render(p)}
                </div>
            ))}
        </section>
    );
};
