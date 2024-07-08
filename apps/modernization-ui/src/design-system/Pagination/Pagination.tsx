import { Pagination as PaginationUswds } from '@trussworks/react-uswds';
import styles from './pagination.module.scss';
import { RangeToggle } from 'components/Table/RangeToggle/RangeToggle';
import { useEffect } from 'react';
import { usePage } from 'page';

type Props = {
    path: string;
    total: number;
    pageSize: number;
};

export const Pagination = ({ path, total, pageSize }: Props) => {
    const { ready, page } = usePage();

    useEffect(() => {
        ready(total ?? 0, page.current);
    }, [total, page]);

    return (
        <div className={styles.pagination}>
            <div className={styles.range}>
                Showing <RangeToggle /> of <span id="totalRowCount">{total}</span>
            </div>
            <div className={styles.pages}>
                <PaginationUswds
                    totalPages={Math.ceil(total / pageSize)}
                    currentPage={page.current}
                    pathname={`/${path}`}
                    onClickNext={() => ready(total, page.current + 1)}
                    onClickPrevious={() => ready(total, page.current - 1)}
                    onClickPageNumber={(_, page) => ready(total, page)}
                />
            </div>
        </div>
    );
};
