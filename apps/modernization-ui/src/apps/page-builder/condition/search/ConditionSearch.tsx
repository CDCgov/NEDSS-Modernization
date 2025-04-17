import { Button, Icon } from '@trussworks/react-uswds';
import { ConditionSort, useConditionSearch } from 'apps/page-builder/condition/search/useConditionSearch';
import { Search } from 'components/Search';
import { PaginationProvider, Status, usePagination } from 'pagination';
import { useEffect, useState } from 'react';
import { ConditionTable } from './ConditionTable';
import styles from './condition-search.module.scss';

type Props = {
    onConditionSelect: (ids: number[]) => void;
    onCancel: () => void;
    onCreateNew: () => void;
};
export const ConditionSearch = ({ onConditionSelect, onCancel, onCreateNew }: Props) => {
    return (
        <PaginationProvider>
            <ConditionSearchContent
                onCreateNew={onCreateNew}
                onCancel={onCancel}
                onConditionSelect={onConditionSelect}
            />
        </PaginationProvider>
    );
};

const ConditionSearchContent = ({ onConditionSelect, onCancel, onCreateNew }: Props) => {
    const { search, response, isLoading, keyword, reset } = useConditionSearch();
    const { page, ready, request } = usePagination();
    const [sort, setSort] = useState<ConditionSort | undefined>();
    const [selected, setSelected] = useState<number[]>([]);
    const [resetTable, setResetTable] = useState<boolean>(false);

    useEffect(() => {
        search({ page: 0, pageSize: page.pageSize, sort });
    }, []);

    useEffect(() => {
        // on page change, perform search with current keyword
        if (page.status === Status.Requested) {
            handleSearch(keyword ?? '');
        }
    }, [page.status]);

    useEffect(() => {
        ready(response?.totalElements ?? 0, (response?.number ?? 0) + 1);
    }, [response]);

    // Hack to clear the table sort state and search keyword by reloading the components on cancel
    useEffect(() => {
        if (resetTable) {
            setResetTable(false);
        }
    }, [reset]);

    const handleSearch = (keyword: string) => {
        search({ page: page.current - 1, searchText: keyword, pageSize: page.pageSize, sort });
    };

    const handleSort = (sort: ConditionSort | undefined) => {
        setSort(sort);
        request(1);
    };

    const handleAddConditions = () => {
        onConditionSelect(selected);
        clear();
    };

    const handleCancel = () => {
        clear();
        onCancel();
    };

    const clear = () => {
        reset();
        setSelected([]);
        setResetTable(true);
        setSort(undefined);
        request(1);
    };

    return (
        <div className={styles.conditionSearch}>
            <div className={styles.header}>
                <h2>Search and add condition(s)</h2>
                <Icon.Close size={4} onClick={handleCancel} data-testid="closeSearchModalBtn" />
            </div>
            <div className={styles.content}>
                <h3>You can search for existing condition(s) or create a new one.</h3>
                <div className={styles.controls}>
                    {resetTable === false && (
                        <Search
                            className={styles.search}
                            id="condition-search"
                            name="search"
                            ariaLabel="Search by condition name or code"
                            placeholder="Search by condition name or code"
                            onSearch={(e) => handleSearch(e ?? '')}
                        />
                    )}
                    <Button
                        aria-label="Create new condition"
                        type="button"
                        onClick={onCreateNew}
                        data-testid="createNewConditionBtn">
                        Create new condition
                    </Button>
                </div>
                {resetTable === false && (
                    <ConditionTable
                        isLoading={isLoading}
                        conditions={response?.content ?? []}
                        onSelectionChange={setSelected}
                        onSort={handleSort}
                    />
                )}
            </div>
            <div className={styles.footer}>
                <Button
                    aria-label="Cancel"
                    onClick={handleCancel}
                    type="button"
                    outline
                    data-testid="advancedConditionSearchCancelBtn">
                    Cancel
                </Button>
                <Button
                    aria-label="Add conditions"
                    disabled={selected.length === 0}
                    onClick={handleAddConditions}
                    type="button">
                    Add conditions
                </Button>
            </div>
        </div>
    );
};
