import { Pagination as PaginationUswds } from '@trussworks/react-uswds';
import styles from './pagination.module.scss';
import { RangeToggle } from 'components/Table/RangeToggle/RangeToggle';
import { usePage } from 'page';

const Pagination = () => {
    const {
        request,
        page: { total, pageSize, current }
    } = usePage();

    return (
        <div className={total > 0 ? styles.pagination : styles.hidden}>
            <div className={styles.range}>
                Showing <RangeToggle initial={pageSize} aria-label="selected page size" /> of {total}
            </div>
            <div className={styles.pages}>
                <PaginationUswds
                    totalPages={Math.ceil(total / pageSize)}
                    currentPage={current}
                    pathname={''}
                    onClickNext={() => request(current + 1)}
                    onClickPrevious={() => request(current - 1)}
                    onClickPageNumber={(_, page) => request(page)}
                />
            </div>
        </div>
    );
};

export { Pagination };
