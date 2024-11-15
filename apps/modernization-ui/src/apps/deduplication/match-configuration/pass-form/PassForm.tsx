import { DataElementsConfiguration } from 'apps/deduplication/data-elements/DataElement';
import { BlockingCriteriaForm } from './blocking/BlockingCriteriaForm';
import { MatchingBoundsForm } from './bounds/MatchingBoundsForm';
import { MatchingCriteriaForm } from './matching/MatchingCriteriaForm';
import styles from './pass-form.module.scss';
type Props = {
    activePass?: number;
    dataElementConfiguration: DataElementsConfiguration;
};
export const PassForm = ({ activePass, dataElementConfiguration }: Props) => {
    return (
        <div className={styles.passForm}>
            {activePass !== undefined && (
                <>
                    <BlockingCriteriaForm activePass={activePass} />
                    <MatchingCriteriaForm activePass={activePass} dataElementConfiguration={dataElementConfiguration} />
                    <MatchingBoundsForm />
                </>
            )}
        </div>
    );
};
