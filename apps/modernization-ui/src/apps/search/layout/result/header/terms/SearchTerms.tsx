import { Chip } from 'design-system/chips';
import { useSearchInteraction } from 'apps/search';
import { Term } from 'apps/search/terms';
import { pluralize } from 'utils';

import styles from './search-terms.module.scss';
import { SkipLink } from 'SkipLink';

type Props = {
    total: number;
    terms: Term[];
};

const SearchTerms = ({ total, terms }: Props) => {
    const resultsText = pluralize('Result', total);
    const verbText = pluralize('has', total, 'have');
    const ariaLabel = `${total} ${resultsText} ${verbText} been found`;

    const { without } = useSearchInteraction();

    return (
        <div tabIndex={0} id="resultsCount" className={styles.terms} aria-label={ariaLabel}>
            <SkipLink id="resultsCount" autoFocus />
            <div className={styles.term}>
                <h2>
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
