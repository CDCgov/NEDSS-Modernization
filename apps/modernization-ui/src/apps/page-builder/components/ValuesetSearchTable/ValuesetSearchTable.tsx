import { Button, Icon } from '@trussworks/react-uswds';
import { ValueSetOption } from 'apps/page-builder/generated';
import { SortField, ValuesetSort } from 'apps/page-builder/hooks/api/useFindValueset';
import { Search } from 'components/Search/Search';
import { TableBody, TableComponent } from 'components/Table';
import { Status, usePagination } from 'pagination';
import { useEffect, useState } from 'react';
import { Direction } from 'libs/sorting';
import styles from './valueset-search-table.module.scss';

const enum Header {
    TYPE = 'Type',
    NAME = 'Value set name',
    CODE = 'Code',
    DESCRIPTION = 'Value set description'
}

const tableHeaders = [
    { name: Header.TYPE, sortable: true },
    { name: Header.CODE, sortable: true },
    { name: Header.NAME, sortable: true },
    { name: Header.DESCRIPTION, sortable: true }
];

type Props = {
    isLoading?: boolean;
    valuesets: ValueSetOption[];
    query?: string;
    onQuerySubmit: (query: string) => void;
    onSelectionChange: (id: number) => void;
    onSortChange: (sort: ValuesetSort | undefined) => void;
    onCreateNew?: () => void;
};
export const ValuesetSearchTable = ({
    isLoading,
    valuesets,
    query,
    onQuerySubmit,
    onSelectionChange,
    onSortChange,
    onCreateNew
}: Props) => {
    const { page, request } = usePagination();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);

    const doQuerySearch = (query?: string) => {
        if (onQuerySubmit) {
            onQuerySubmit(query ?? '');
        }
    };

    const toTableRow = (valueset: ValueSetOption): TableBody => {
        return {
            id: valueset.id,
            key: valueset.id,
            checkbox: false,
            selectable: false,
            radioButton: true,
            onSelect: () => valueset.id && onSelectionChange(valueset.id),
            tableDetails: [
                {
                    id: 1,
                    title: valueset.type
                },
                { id: 2, title: valueset.id },
                { id: 2, title: valueset.name },
                {
                    id: 3,
                    title: valueset.description
                }
            ]
        };
    };

    useEffect(() => {
        setTableRows(valuesets.map(toTableRow));
    }, [valuesets]);

    const handleSort = (name: string, direction: Direction) => {
        if (direction === Direction.None) {
            onSortChange(undefined);
            return;
        }

        let sortField: SortField | undefined = undefined;
        switch (name) {
            case Header.TYPE:
                sortField = SortField.TYPE;
                break;
            case Header.CODE:
                sortField = SortField.CODE;
                break;
            case Header.NAME:
                sortField = SortField.NAME;
                break;
            case Header.DESCRIPTION:
                sortField = SortField.DESCRIPTION;
                break;
        }
        if (sortField) {
            onSortChange({ field: sortField, direction });
        } else {
            onSortChange(undefined);
        }
    };

    return (
        <>
            <div className={styles.searchBar}>
                <div>
                    {query && (
                        <div className={styles.searchText}>
                            <div> {query}</div>
                            <Button
                                className={styles.clearSearchButton}
                                type="button"
                                onClick={() => onQuerySubmit?.('')}>
                                <Icon.Close />
                            </Button>
                        </div>
                    )}
                </div>
                <div>
                    <Search
                        onSearch={doQuerySearch}
                        placeholder="Search by name, description or code"
                        name="question-search"
                        id="question-search"
                        value={query}
                    />
                    {onCreateNew && (
                        <Button type="button" className={styles.createNewButton} outline onClick={onCreateNew}>
                            Create new value set
                        </Button>
                    )}
                </div>
            </div>
            <div className={styles.tableContainer}>
                <TableComponent
                    className={styles.searchResultTable}
                    tableHead={tableHeaders}
                    tableBody={tableRows}
                    isPagination={true}
                    pageSize={page.pageSize}
                    totalResults={page.total}
                    currentPage={page.current}
                    handleNext={request}
                    sortData={handleSort}
                    selectable={page.status === Status.Ready}
                    rangeSelector={isLoading === true || valuesets.length > 0}
                    isLoading={isLoading}
                    display="zebra"
                />
            </div>
        </>
    );
};
