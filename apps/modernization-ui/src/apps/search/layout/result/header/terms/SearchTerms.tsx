import { useEffect } from 'react';
import { Chip } from 'design-system/chips';
import { useSearchInteraction } from 'apps/search';
import { Term } from 'apps/search/terms';

import { useSkipLink } from 'SkipLink/SkipLinkContext';
import { focusedTarget } from 'utils';

import styles from './search-terms.module.scss';

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

    const { without } = useSearchInteraction();

    return (
        <div className={styles.terms} tabIndex={0} id="resultsCount" aria-label={`${total} Results have been found`}>
            <div className={styles.term}>
                <h2>{total} results for:</h2>
                {terms.map((term, index) => (
                    <Chip key={index} name={term.title} value={term.name} handleClose={() => without(term)} />
                ))}
            </div>
        </div>
    );
};

export { SearchTerms };
