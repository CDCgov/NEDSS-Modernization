import { Results } from 'apps/search';

import { useSkipLink } from 'SkipLink/SkipLinkContext';
import { focusedTarget } from 'utils';

import styles from './search-terms.module.scss';

type Props = {
    results: Results;
};

const SearchTerms = ({ results }: Props) => {
    const { skipTo } = useSkipLink();

    skipTo('resultsCount');
    focusedTarget('resultsCount');

    return (
        <div
            className={styles.terms}
            tabIndex={0}
            id="resultsCount"
            aria-label={results.total + ' Results have been found'}>
            <h2>{results.total} results for:</h2>
        </div>
    );
};

export { SearchTerms };
