import styles from './details-section.module.scss';

type Props = {
    details: { label: string; value?: string }[];
};
export const DetailsSection = ({ details }: Props) => {
    const toDisplay = (value?: string): string => {
        if (value == undefined || value.trim().length === 0) {
            return '---';
        } else {
            return value;
        }
    };
    return (
        <div className={styles.detailsSection}>
            {details.map((d) => (
                <div className={styles.row} key={`${d.label}-${d.value}`}>
                    <div className={styles.label}>{d.label}</div>
                    <div>{toDisplay(d.value)}</div>
                </div>
            ))}
        </div>
    );
};
