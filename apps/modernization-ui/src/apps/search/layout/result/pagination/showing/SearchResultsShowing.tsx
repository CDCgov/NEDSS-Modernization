import { Page } from 'page';

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

    return <div className={className}>{`Showing ${first} to ${adjustedLast} of ${total}`}</div>;
};

export { SearchResultsShowing };
