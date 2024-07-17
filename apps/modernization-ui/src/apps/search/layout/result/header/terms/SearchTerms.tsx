import { Term, useSearchResultDisplay } from 'apps/search';

import { useSkipLink } from 'SkipLink/SkipLinkContext';
import { focusedTarget } from 'utils';

import styles from './search-terms.module.scss';
import { useEffect } from 'react';
import Chip from 'apps/search/advancedSearch/components/chips/Chip';

type Props = {
    total: number;
    terms: Term[];
};

const SearchTerms = ({ total, terms }: Props) => {
    const { skipTo } = useSkipLink();
    const { removeTerm } = useSearchResultDisplay();

    useEffect(() => {
        skipTo('resultsCount');
        focusedTarget('resultsCount');
    }, []);

    return (
        <div className={styles.terms} tabIndex={0} id="resultsCount" aria-label={total + ' Results have been found'}>
            <h2>{total} results for:</h2>
            <div className="display-flex grid-gap-2">
                {terms.map((term, index) => (
                    <Chip key={index} name={term.title} value={term.value} handleClose={() => removeTerm(term)} />
                ))}
            </div>
        </div>
    );
};

export { SearchTerms };
