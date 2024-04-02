import { StaticSubsection } from '../StaticSubsection/StaticSubsection';
import styles from './subsection-table.module.scss';

type Props = {
    title: string;
    description?: string;
    columns: string[];
};
export const SubsectionTable = ({ title, columns, description }: Props) => {
    return (
        <StaticSubsection title={title}>
            <>
                {description && <div className={styles.description}>{description}</div>}
                <div className={styles.tableColumns}>
                    {columns.map((c, k) => (
                        <div className={styles.column} key={k}>
                            {c}
                        </div>
                    ))}
                </div>
                <div className={styles.tableText}>Nothing found to display</div>
            </>
        </StaticSubsection>
    );
};
