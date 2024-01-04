import { Button, Icon } from '@trussworks/react-uswds';
import {
    ConditionSort,
    ConditionSortField,
    useConditionSearch
} from 'apps/page-builder/condition/search/useConditionSearch';
import { Search } from 'components/Search';
import { Heading } from 'components/heading';
import { PageProvider, Status, usePage } from 'page';
import { useEffect, useState } from 'react';
import { Direction } from 'sorting';
import styles from './condition-search.module.scss';
import { ConditionTable } from './ConditionTable';

type Props = {
    onConditionSelect: (ids: number[]) => void;
    onCancel: () => void;
    onCreateNew: () => void;
};
export const ConditionSearch = ({ onConditionSelect, onCancel, onCreateNew }: Props) => {
    return (
        <PageProvider>
            <ConditionSearchContent
                onCreateNew={onCreateNew}
                onCancel={onCancel}
                onConditionSelect={onConditionSelect}
            />
        </PageProvider>
    );
};

const defaultSort = {
    field: ConditionSortField.CONDITION,
    direction: Direction.Ascending
};
const ConditionSearchContent = ({ onConditionSelect, onCancel, onCreateNew }: Props) => {
    const { search, response, isLoading } = useConditionSearch();
    const { page, ready, request } = usePage();
    const [keyword, setKeyword] = useState<string | undefined>('');
    const [selected, setSelected] = useState<number[]>([]);
    const [sort, setSort] = useState<ConditionSort>(defaultSort);
    const [reset, setReset] = useState<boolean>(false);

    useEffect(() => {
        search({ page: 0, sort });
    }, []);

    useEffect(() => {
        if (page.status === Status.Requested) {
            handleSearch(keyword ?? '');
        }
    }, [page.status]);

    useEffect(() => {
        ready(response?.totalElements ?? 0, (response?.number ?? 0) + 1);
    }, [response]);

    // Hack to clear the table sort and search keyword by reloading it on cancel
    useEffect(() => {
        if (reset) {
            setReset(false);
        }
    }, [reset]);

    const handleSearch = (keyword: string) => {
        setKeyword(keyword);
        search({ page: page.current - 1, searchText: keyword, pageSize: page.pageSize, sort });
    };

    const handleSort = (sort: ConditionSort | undefined) => {
        setSort(sort ?? defaultSort);
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
        setKeyword('');
        setSelected([]);
        setReset(true);
        setSort(defaultSort);
        request(1);
    };

    return (
        <div className={styles.conditionSearch}>
            <div className={styles.header}>
                <Heading level={2}>Search and add condition(s)</Heading>
                <Icon.Close size={4} onClick={handleCancel} />
            </div>
            <div className={styles.content}>
                <Heading level={3}>You can search for existing condition(s) or create a new one.</Heading>
                <div className={styles.controls}>
                    {reset === false && (
                        <Search
                            className={styles.search}
                            id="condition-search"
                            name="search"
                            ariaLabel="Search by condition name or code"
                            placeholder="Search by condition name or code"
                            onSearch={(e) => handleSearch(e ?? '')}
                        />
                    )}
                    <Button type="button" onClick={onCreateNew}>
                        Create new condition
                    </Button>
                </div>
                {reset === false && (
                    <ConditionTable
                        isLoading={isLoading}
                        conditions={response?.content ?? []}
                        onSelectionChange={setSelected}
                        onSort={handleSort}
                    />
                )}
            </div>
            <div className={styles.footer}>
                <Button onClick={handleCancel} type="button" outline>
                    Cancel
                </Button>
                <Button onClick={handleAddConditions} type="button">
                    Add conditions
                </Button>
            </div>
        </div>
    );
};
