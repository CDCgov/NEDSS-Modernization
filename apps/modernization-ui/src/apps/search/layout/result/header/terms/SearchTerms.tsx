import { Chip } from 'design-system/chips';
import { useSearchInteraction } from 'apps/search';
import { Term } from 'apps/search/terms';
import { pluralize } from 'utils';

import styles from './search-terms.module.scss';
import { SkipLink } from 'SkipLink';

type Props = {
    total: number;
    filteredTotal?: number;
    terms: Term[];
};

/**
 * Renders the search terms with the total number of results and individual terms.
 * If filteredTotal is provided, displays "Tf of T results for:".
 * @param {Props} props - The properties for the component.
 * @return {JSX.Element} The rendered search terms component.
 */
const SearchTerms = ({ total, filteredTotal, terms }: Props) => {
    const resultsText = pluralize('Result', filteredTotal ?? total);
    const verbText = pluralize('has', filteredTotal ?? total, 'have');
    const ariaLabel =
        filteredTotal !== undefined
            ? `${filteredTotal} of ${total} ${resultsText} ${verbText} been found`
            : `${total} ${resultsText} ${verbText} been found`;

    const { without } = useSearchInteraction();

    return (
        <div tabIndex={0} id="resultsCount" className={styles.terms} aria-label={ariaLabel}>
            <SkipLink id="resultsCount" autoFocus />
            <div className={styles.term}>
                <h2>
                    {filteredTotal !== undefined
                        ? `${filteredTotal} of ${total} found:`
                        : `${total} ${resultsText.toLowerCase()} for:`}
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
