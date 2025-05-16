import { MatchRequiringReview } from 'apps/deduplication/api/model/MatchRequiringReview';
import { useMatchesRequiringReview } from 'apps/deduplication/api/useMatchesRequiringReview';
import { SearchResultPageSizeSelect } from 'apps/search/layout/result/pagination/page-size-select';
import { SearchResultsShowing } from 'apps/search/layout/result/pagination/showing';
import { parseISO, format } from 'date-fns';
import { Button } from 'design-system/button';
import { Pagination } from 'design-system/pagination';
import { Column, DataTable } from 'design-system/table';
import { PaginationProvider, Status, usePagination } from 'pagination';
import { useEffect } from 'react';
import styles from './matches-requiring-review.module.scss';
import { Shown } from 'conditional-render';
import { SortingProvider, useSorting, Direction } from 'sorting';
import { useNavigate } from 'react-router';

const DATE_FORMAT = 'MM/dd/yyyy h:mm a';

export const MatchesRequiringReviewTable = ({ onSortChange }: { onSortChange?: (sort: string) => void }) => {
    return (
        <PaginationProvider pageSize={20}>
            <SortingProvider>
                <SortableMatchesRequiringReviewTable onSortChange={onSortChange} />
            </SortingProvider>
        </PaginationProvider>
    );
};

type SortableMatchesRequiringReviewTableProps = {
    onSortChange?: (sort: string) => void;
};

const SortableMatchesRequiringReviewTable = ({ onSortChange }: SortableMatchesRequiringReviewTableProps) => {
    const nav = useNavigate();
    const { response, fetchMatchesRequiringReview } = useMatchesRequiringReview();
    const { property, direction } = useSorting();
    const { page, ready, request, resize, firstPage } = usePagination();

    // Helper to convert Direction enum to string 'asc' or 'desc'
    const fromDirection = (dir: Direction) => {
        switch (dir) {
            case Direction.Ascending:
                return 'asc';
            case Direction.Descending:
                return 'desc';
            default:
                return undefined;
        }
    };

    const sorting =
        property && direction && direction !== Direction.None
            ? [{ field: property, direction: fromDirection(direction) }]
            : [];

    const sortingString = sorting.length > 0 ? `${sorting[0].field},${sorting[0].direction}` : undefined;

    useEffect(() => {
        if (page.current === 1) {
            fetchMatchesRequiringReview(0, page.pageSize, sortingString);
        } else {
            firstPage();
        }

        if (onSortChange && sortingString) {
            onSortChange(sortingString);
        }
    }, [sortingString]);

    useEffect(() => {
        ready(response.total, Math.max(1, page.current));
    }, [response]);

    useEffect(() => {
        if (page.status === Status.Requested) {
            fetchMatchesRequiringReview(page.current - 1, page.pageSize, sortingString);
        }
    }, [page.status]);

    const columns: Column<MatchRequiringReview>[] = [
        {
            id: 'patient-id',
            name: 'Patient ID',
            sortable: true,
            render(match) {
                return <>{match.patientId}</>;
            }
        },
        {
            id: 'name',
            name: 'Person name',
            sortable: true,
            render(match) {
                return <>{match.patientName}</>;
            }
        },
        {
            id: 'created',
            name: 'Date created',
            sortable: true,
            render(match) {
                return <>{format(parseISO(match.createdDate), DATE_FORMAT)}</>;
            }
        },
        {
            id: 'identified',
            name: 'Date identified',
            sortable: true,
            render(match) {
                return <>{format(parseISO(match.identifiedDate), DATE_FORMAT)}</>;
            }
        },
        {
            id: 'count',
            name: 'Number of matching records',
            sortable: true,
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
