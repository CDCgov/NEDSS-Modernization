import { Page } from 'pagination';
import styles from './search-result-showing.module.scss';
import classNames from 'classnames';

type SearchResultsShowingProps = {
    className?: string;
    page: Page;
};

const SearchResultsShowing = ({ className, page }: SearchResultsShowingProps) => {
    const { total, pageSize, current } = page;

    const offset = pageSize - 1;
    const first = current * pageSize - offset;
    const last = first + offset;
    const adjustedLast = last > total ? total : last;

    return (
        <div
            className={classNames(
                styles.searchResultsShowing,
                className
            )}>{`Showing ${first} to ${adjustedLast} of ${total}`}</div>
    );
};

export { SearchResultsShowing };
