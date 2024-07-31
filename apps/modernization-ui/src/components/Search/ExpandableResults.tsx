import { NoData } from 'components/NoData';
import {
    PatientSearchResultAddress,
    PatientSearchResultIdentification,
    PatientSearchResultName
} from 'generated/graphql/schema';
import { useState } from 'react';
import styles from './expandableResults.module.scss';

type Props = {
    results: PatientSearchResultAddress[] | PatientSearchResultIdentification[] | PatientSearchResultName[] | string[];
    renderResult?: ({ _ }: any) => string;
};

export const ExpandableResults = ({ results, renderResult }: Props) => {
    const [expand, setExpand] = useState<boolean>(false);

    const renderResults = () => {
        let toShow = results;
        if (!expand) {
            toShow = results.slice(0, 1);
        }
        return renderResult ? toShow.map(renderResult).join('\n') : toShow.join('\n');
    };

    return (
        <>
            {results.length > 0 ? renderResults() : <NoData />}
            {results.length > 1 && !expand && (
                <button aria-label="view more" className={styles.expandButton} onClick={() => setExpand(true)}>
                    View more
                </button>
            )}
            {results.length > 1 && expand && (
                <button aria-label="view less" className={styles.expandButton} onClick={() => setExpand(false)}>
                    View less
                </button>
            )}
        </>
    );
};
