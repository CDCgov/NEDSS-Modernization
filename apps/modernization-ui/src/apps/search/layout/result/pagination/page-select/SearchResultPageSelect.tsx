import { Page } from 'page';
import { Pagination as PaginationUswds } from '@trussworks/react-uswds';

type SearchResultPageSelectProps = {
    className?: string;
    page: Page;
    onSelected: (page: number) => void;
};

const SearchResultPageSelect = ({ className, page, onSelected }: SearchResultPageSelectProps) => {
    const { total, pageSize, current } = page;

    return (
        <div className={className}>
            <PaginationUswds
                totalPages={Math.ceil(total / pageSize)}
                currentPage={current}
                pathname={''}
                onClickNext={() => onSelected(current + 1)}
                onClickPrevious={() => onSelected(current - 1)}
                onClickPageNumber={(_, page) => onSelected(page)}
            />
        </div>
    );
};

export { SearchResultPageSelect };
