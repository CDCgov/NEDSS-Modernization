import { useEffect } from 'react';
import { Chip } from 'design-system/chips';
import { useSearchInteraction } from 'apps/search';
import { Term } from 'apps/search/terms';

import { useSkipLink } from 'SkipLink/SkipLinkContext';
import { focusedTarget, pluralize } from 'utils';

import styles from './search-terms.module.scss';

type Props = {
    total: number;
    terms: Term[];
};

const SearchTerms = ({ total, terms }: Props) => {
    const { skipTo } = useSkipLink();
    const resultsText = pluralize('Result', total);
    const verbText = pluralize('has', total, 'have');
    const ariaLabel = `${total} ${resultsText} ${verbText} been found`;

    useEffect(() => {
        skipTo('resultsCount');
        focusedTarget('resultsCount');
    }, []);

    const { without } = useSearchInteraction();

    return (
        <div className={styles.terms} aria-label={ariaLabel}>
            <div className={styles.term}>
                <h2 tabIndex={0} id="resultsCount">
                    {total} {resultsText.toLowerCase()} for:
                </h2>
                {terms.map((term, index) => (
                    <Chip
                        key={index}
                        name={term.title}
                        value={term.name}
                        operator={term.operator}
                        handleClose={() => without(term)}
                    />
                ))}
            </div>
        </div>
    );
};

export { SearchTerms };
