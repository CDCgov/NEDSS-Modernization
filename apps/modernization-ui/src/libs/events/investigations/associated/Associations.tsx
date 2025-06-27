import { exists } from 'utils';
import { Shown } from 'conditional-render';
import { AssociatedInvestigation } from './associated';

import styles from './associations.module.scss';

type AssociationsProps = {
    patient: number;
    children?: AssociatedInvestigation[];
};

const Associations = ({ patient, children }: AssociationsProps) => (
    <Shown when={exists(children)}>
        <span className={styles.associations}>
            {children?.map((association, index) => (
                <AssociatedWith key={index} patient={patient}>
                    {association}
                </AssociatedWith>
            ))}
        </span>
    </Shown>
);

type AssociatedWithProps = {
    patient: number;
    children: AssociatedInvestigation;
};

const AssociatedWith = ({ patient, children }: AssociatedWithProps) => (
    <span className={styles.association}>
        <a href={`/nbs/api/profile/${patient}/investigation/${children.id}`}>{children.local}</a>
        <strong>{children.condition}</strong>
        {children.status}
    </span>
);

export { Associations, AssociatedWith };
