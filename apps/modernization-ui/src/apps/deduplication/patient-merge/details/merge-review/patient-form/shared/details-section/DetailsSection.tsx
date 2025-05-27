import styles from './details-section.module.scss';

type Props = {
    details: { label: string; value?: string }[];
};
export const DetailsSection = ({ details }: Props) => {
    return (
        <div className={styles.detailsSection}>
            {details.map((d) => (
                <div className={styles.row} key={`${d.label}-${d.value}`}>
                    <div className={styles.label}>{d.label}</div>
                    <div>{d.value ?? '---'}</div>
                </div>
            ))}
        </div>
    );
};
