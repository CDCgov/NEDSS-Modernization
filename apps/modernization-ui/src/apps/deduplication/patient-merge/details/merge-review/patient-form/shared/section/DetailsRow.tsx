import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import styles from './section.module.scss';
import { ReactNode } from 'react';

type Props = {
    id: string;
    patientData: MergePatient[];
    render: (patientData: MergePatient) => ReactNode | undefined;
};
export const DetailsRow = ({ id, patientData, render }: Props) => {
    return (
        <section className={styles.section}>
            {patientData.map((p) => (
                <div key={`detailsSection-${id}-${p.personUid}`} className={styles.entry}>
                    {render(p)}
                </div>
            ))}
        </section>
    );
};
