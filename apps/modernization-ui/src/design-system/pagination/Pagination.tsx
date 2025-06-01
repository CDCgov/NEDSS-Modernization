import classnames from 'classnames';
import { Button } from 'components/button';

import styles from './pagination.module.scss';

type PaginationPageProps = {
    page: number;
    selected: boolean;
    onSelectPage: (page: number) => void;
};

const PageSelector = ({ page, selected, onSelectPage }: PaginationPageProps) => {
    return (
        <li key={`pagination_page_${page}`} className="usa-pagination__item usa-pagination__page-no">
            <Button
                tertiary
                className={classnames('usa-pagination__button', styles.page, {
                    'usa-current': selected
                })}
                active={selected}
                aria-label={`Page ${page}`}
                aria-current={selected ? 'page' : undefined}
                onClick={() => onSelectPage(page)}>
                {page}
            </Button>
        </li>
    );
};

const PaginationOverflow = () => (
    <li
        className={classnames('usa-pagination__item', 'usa-pagination__overflow', styles.overflow)}
        aria-label="ellipsis indicating non-visible pages">
        <span>…</span>
    </li>
);

type PageSelection = number | 'overflow';

type PaginationProps = {
    total: number;
    current: number;
    maxPagesShown?: number;
    ariaLabel?: string;
    onNext: () => void;
    onPrevious: () => void;
    onSelectPage: (page: number) => void;
};

const Pagination = ({
    total,
    current,
    className,
    maxPagesShown = 7,
    ariaLabel = 'pagination',
    onPrevious,
    onNext,
    onSelectPage,
    ...props
}: PaginationProps & JSX.IntrinsicElements['nav']) => {
    const isOnFirstPage = current === 1;
    const isOnLastPage = current === total;

    const pageSelections: PageSelection[] =
        total > maxPagesShown
            ? adjustedForOverflow(maxPagesShown, current, total)
            : Array.from({ length: total }).map((_, i) => i + 1);

    const showPreviousPage = !isOnFirstPage && current - 1;
    const showNextPage = !isOnLastPage && current + 1;

    return (
        <nav aria-label={ariaLabel} className={classnames('usa-pagination', styles.pagination, className)} {...props}>
            <ul className="usa-pagination__list">
                {showPreviousPage && (
                    <li className={classnames('usa-pagination__item', 'usa-pagination__arrow', styles.arrow)}>
                        <Button
                            className="usa-pagination__link usa-pagination__previous-page"
                            aria-label="Previous page"
                            tertiary
                            icon="navigate_before"
                            onClick={onPrevious}>
                            <span className="usa-pagination__link-text">Previous</span>
                        </Button>
                    </li>
                )}

                {pageSelections.map((pageNum, i) =>
                    typeof pageNum === 'number' ? (
                        <PageSelector
                            key={`pagination_page_${pageNum}`}
                            page={pageNum}
                            selected={pageNum === current}
                            onSelectPage={onSelectPage}
                        />
                    ) : (
                        <PaginationOverflow key={`pagination_overflow_${i}`} />
                    )
                )}

                {showNextPage && (
                    <li className={classnames('usa-pagination__item', 'usa-pagination__arrow', styles.arrow)}>
                        <Button
                            className="usa-pagination__link usa-pagination__next-page"
                            aria-label="Next page"
                            tertiary
                            icon="navigate_next"
                            labelPosition="left"
                            onClick={onNext}>
                            <span className="usa-pagination__link-text">Next</span>
                        </Button>
                    </li>
                )}
            </ul>
        </nav>
    );
};

const adjustedForOverflow = (maxPagesShown: number, current: number, total: number): PageSelection[] => {
    const visiblePages = Math.round(maxPagesShown / 2);
    const hasOverflowBefore = current > visiblePages;
    const hasOverflowAfter = total - current >= visiblePages;

    const previous = current === 1 ? 0 : hasOverflowBefore ? 2 : 1; // first page + prev overflow
    const next = current === total ? 0 : hasOverflowAfter ? 2 : 1; // next overflow + last page
    const pageCount = maxPagesShown - 1 - (previous + next); // remaining selections to show (minus one for the current page)

    // Determine how many selections we have before/after the current page
    let before = 0;
    let after = 0;
    if (hasOverflowBefore && hasOverflowAfter) {
        // We are in the middle of the set, there will be overflow (...) at both the beginning & end
        // Ex: [1] [...] [9] [10] [11] [...] [24]
        before = Math.round((pageCount - 1) / 2);
        after = pageCount - before;
    } else if (hasOverflowBefore) {
        // We are in the end of the set, there will be overflow (...) at the beginning
        // Ex: [1] [...] [20] [21] [22] [23] [24]
        after = (total || 0) - current - 1; // current & last
        after = after < 0 ? 0 : after;
        before = pageCount - after;
    } else if (hasOverflowAfter) {
        // We are in the beginning of the set, there will be overflow (...) at the end
        // Ex: [1] [2] [3] [4] [5] [...] [24]
        before = current - 2; // first & current
        before = before < 0 ? 0 : before;
        after = pageCount - before;
    }

    const adjusted: PageSelection[] = [current];

    for (let index = 1; before > 0; index++, before--) {
        adjusted.unshift(current - index);
    }

    for (let index = 1; after > 0; index++, after--) {
        adjusted.push(current + index);
    }

    // Add prev/next overflow indicators, and first/last pages as needed
    if (hasOverflowBefore) adjusted.unshift('overflow');
    if (current !== 1) adjusted.unshift(1);
    if (hasOverflowAfter) adjusted.push('overflow');
    if (current !== total) adjusted.push(total);

    return adjusted;
};

export { Pagination };
