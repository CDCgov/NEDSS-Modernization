import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import styles from './section.module.scss';
import { ReactNode } from 'react';

type Props = {
    id: string;
    mergePatients: MergePatient[];
    render: (mergePatients: MergePatient) => ReactNode | undefined;
};
export const DetailsRow = ({ id, mergePatients, render }: Props) => {
    return (
        <section className={styles.section}>
            {mergePatients.map((p) => (
                <div key={`detailsSection-${id}-${p.personUid}`} className={styles.entry}>
                    {render(p)}
                </div>
            ))}
        </section>
    );
};
