import { PatientData } from 'apps/deduplication/api/model/PatientData';
import styles from './section.module.scss';
import { ReactNode } from 'react';

type Props = {
    id: string;
    patientData: PatientData[];
    render: (patientData: PatientData) => ReactNode | undefined;
};
export const DetailsSection = ({ id, patientData, render }: Props) => {
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
