import { Term } from 'apps/search';

import { useSkipLink } from 'SkipLink/SkipLinkContext';
import { focusedTarget } from 'utils';

import styles from './search-terms.module.scss';
import { useEffect } from 'react';

type Props = {
    total: number;
    terms: Term[];
};

const SearchTerms = ({ total, terms }: Props) => {
    const { skipTo } = useSkipLink();

    useEffect(() => {
        skipTo('resultsCount');
        focusedTarget('resultsCount');
    }, []);

    return (
        <div className={styles.terms} tabIndex={0} id="resultsCount" aria-label={total + ' Results have been found'}>
            <h2>{total} results for:</h2>
            {terms.map((term) => term.name)}
        </div>
    );
};

export { SearchTerms };
