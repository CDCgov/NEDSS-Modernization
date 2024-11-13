import { BlockingCriteriaForm } from './blocking/BlockingCriteriaForm';
import { MatchingBoundsForm } from './bounds/MatchingBoundsForm';
import { MatchingCriteriaForm } from './matching/MatchingCriteriaForm';
import styles from './pass-form.module.scss';
type Props = {
    activePass: number;
};
export const PassForm = ({ activePass }: Props) => {
    return (
        <div className={styles.passForm}>
            <BlockingCriteriaForm activePass={activePass} />
            <MatchingCriteriaForm />
            <MatchingBoundsForm />
        </div>
    );
};
