import { MergeInvestigation } from 'apps/deduplication/api/model/MergeCandidate';
import styles from './investigation.module.scss';
import { toDateDisplay } from '../../shared/toDateDisplay';

type Props = {
    investigations: MergeInvestigation[];
};
export const Investigation = ({ investigations }: Props) => {
    return (
        <div>
            {investigations.map((i) => (
                <div className={styles.investigation} key={`investigation-${i.id}`}>
                    <div>
                        <span className={styles.label}>{`${i.condition}: `}</span>
                        <span>{i.id}</span>
                    </div>
                    <div>
                        <span className={styles.label}>Start Date: </span>
                        {toDateDisplay(i.startDate)}
                    </div>
                </div>
            ))}
        </div>
    );
};
