import { Shown } from 'conditional-render';
import styles from './treatment-list.module.scss';
import { exists } from 'utils/exists';

type TreatmentListProps = {
    children?: string[];
};

const TreatmentList = ({ children }: TreatmentListProps) => (
    <Shown when={exists(children)}>
        <ul className={styles.treatments}>{children?.map((treatment, index) => <li key={index}>{treatment}</li>)}</ul>
    </Shown>
);

export { TreatmentList };
