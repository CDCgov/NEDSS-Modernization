import { PatientName } from 'apps/deduplication/api/model/PatientData';
import styles from './name-details.module.scss';
import { format, parseISO } from 'date-fns';

type Props = {
    name: PatientName;
};
export const NameDetails = ({ name }: Props) => {
    return (
        <div className={styles.nameDetails}>
            <div className={styles.row}>
                <div className={styles.label}>As of date</div>
                <div>{name.asOf ? format(parseISO(name.asOf), 'MM/dd/yyyy') : '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>Type</div>
                <div>{name.type ?? '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>Prefix</div>
                <div>{name.prefix ?? '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>Last</div>
                <div>{name.last ?? '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>Second last</div>
                <div>{name.secondLast ?? '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>First</div>
                <div>{name.first ?? '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>Middle</div>
                <div>{name.middle ?? '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>Second middle</div>
                <div>{name.secondMiddle ?? '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>Suffix</div>
                <div>{name.suffix ?? '---'}</div>
            </div>
            <div className={styles.row}>
                <div className={styles.label}>Degree</div>
                <div>{name.degree ?? '---'}</div>
            </div>
        </div>
    );
};
