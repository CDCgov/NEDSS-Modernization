import { MatchRequiringReview } from 'apps/deduplication/api/model/MatchRequiringReview';
import { useMatchesRequiringReview } from 'apps/deduplication/api/useMatchesRequiringReview';
import { SearchResultPageSizeSelect } from 'apps/search/layout/result/pagination/page-size-select';
import { SearchResultsShowing } from 'apps/search/layout/result/pagination/showing';
import { Loading } from 'components/Spinner';
import { Shown } from 'conditional-render';
import { format, parseISO } from 'date-fns';
import { Button } from 'design-system/button';
import { Pagination } from 'design-system/pagination';
import { Column, DataTable } from 'design-system/table';
import { PaginationProvider, Status, usePagination } from 'pagination';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { Direction, SortingProvider, useSorting } from 'sorting';
import styles from './matches-requiring-review.module.scss';

const DATE_FORMAT = 'MM/dd/yyyy h:mm a';

export const MatchesRequiringReviewTable = () => {
    return (
        <PaginationProvider pageSize={20}>
            <SortingProvider>
                <SortableMatchesRequiringReviewTable />
            </SortingProvider>
        </PaginationProvider>
    );
};

const SortableMatchesRequiringReviewTable = () => {
    const nav = useNavigate();
    const { loading, response, fetchMatchesRequiringReview } = useMatchesRequiringReview();
    const { sorting, sortBy } = useSorting();
    const { page, ready, request, resize, firstPage } = usePagination();
    // required to prevent toggling through date identified sort triggering default sort
    const [previousSort, setPreviousSort] = useState<string | undefined>();

    useEffect(() => {
        if (sorting === undefined) {
            if (previousSort === 'identified,desc') {
                sortBy('identified', Direction.Ascending);
            } else {
                sortBy('identified', Direction.Descending);
            }
        } else {
            firstPage();
        }
        setPreviousSort(sorting);
    }, [sorting]);

    useEffect(() => {
        ready(response.total, Math.max(1, page.current));
    }, [response]);

    useEffect(() => {
        if (page.status === Status.Requested) {
            fetchMatchesRequiringReview(page.current - 1, page.pageSize, sorting);
        }
    }, [page.status]);

    const columns: Column<MatchRequiringReview>[] = [
        {
            id: 'patient-id',
            name: 'Patient ID',
            sortable: true,
            sortIconType: 'numeric',
            render(match) {
                return <>{match.patientId}</>;
            }
        },
        {
            id: 'name',
            name: 'Person name',
            sortable: true,
            sortIconType: 'alpha',
            render(match) {
                return <>{match.patientName}</>;
            }
        },
        {
            id: 'created',
            name: 'Date created',
            sortable: true,
            sortIconType: 'numeric',
            render(match) {
                return <>{format(parseISO(match.createdDate), DATE_FORMAT)}</>;
            }
        },
        {
            id: 'identified',
            name: 'Date identified',
            sortable: true,
            sortIconType: 'numeric',
            render(match) {
                return <>{format(parseISO(match.identifiedDate), DATE_FORMAT)}</>;
            }
        },
        {
            id: 'count',
            name: 'Number of matching records',
            sortable: true,
            sortIconType: 'numeric',
            render(match) {
                return <>{match.numOfMatchingRecords}</>;
            }
        },
        {
            id: 'review',
            name: '',
            render(match) {
                return (
                    <Button
                        sizing="small"
                        className={styles.reviewButton}
                        onClick={() => nav(`/deduplication/merge/${match.patientId}`)}>
                        Review
                    </Button>
                );
            }
        }
    ];

    return (
        <div className={styles.matchesRequiringReviewTable}>
            <Shown when={loading}>
                <div className={styles.loadingContainer}>
                    <Loading center />
                </div>
            </Shown>
            <div className={styles.tableWrapper}>
                <DataTable<MatchRequiringReview> id="review-table" columns={columns} data={response.matches} />
            </div>
            <Shown when={page.total > 0}>
                <div className={styles.pagination}>
                    <SearchResultPageSizeSelect
                        id={`page-size-select`}
                        value={page.pageSize}
                        selections={[20, 30, 50, 100]}
                        onPageSizeChanged={resize}
                    />
                    <SearchResultsShowing page={page} />
                    <Pagination
                        total={Math.ceil(page.total / page.pageSize)}
                        current={page.current}
                        onNext={() => request(page.current + 1)}
                        onPrevious={() => request(page.current - 1)}
                        onSelectPage={(page) => request(page)}
                    />
                </div>
            </Shown>
        </div>
    );
};
