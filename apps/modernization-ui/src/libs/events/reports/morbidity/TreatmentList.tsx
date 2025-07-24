import { Shown } from 'conditional-render';
import styles from './treatment-list.module.scss';

type TreatmentListProps = {
    children?: string[];
};

const TreatmentList = ({ children }: TreatmentListProps) => (
    <Shown when={Boolean(children?.length)}>
        <ul className={styles.treatments}>
            {children?.map((treatment, index) => (
                <li key={index}>{treatment}</li>
            ))}
        </ul>
    </Shown>
);

export { TreatmentList };
