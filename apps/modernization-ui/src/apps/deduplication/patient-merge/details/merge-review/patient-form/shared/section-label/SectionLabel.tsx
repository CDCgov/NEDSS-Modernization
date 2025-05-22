import styles from './section-label.module.scss';

type Props = {
    label: string;
};
export const SectionLabel = ({ label }: Props) => {
    return <div className={styles.sectionLabel}>{label}</div>;
};
