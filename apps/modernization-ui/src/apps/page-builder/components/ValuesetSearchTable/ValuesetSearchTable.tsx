import { ValueSetOption } from 'apps/page-builder/generated';
import { TableComponent, TableBody } from 'components/Table';
import { Button, Icon } from '@trussworks/react-uswds';
import { Search } from 'components/Search/Search';
import { Status, usePage } from 'page';
import { useEffect, useState } from 'react';
import styles from './valueset-search-table.module.scss';

const tableHeaders = [
    { name: 'Type', sortable: true },
    { name: 'Value set name', sortable: true },
    { name: 'Value set description', sortable: true }
];

type Props = {
    isLoading?: boolean;
    valuesets: ValueSetOption[];
    query?: string;
    onQuerySubmit: (query: string) => void;
    onSelectionChange: (id: number) => void;
    onCreateNew: () => void;
};
export const ValuesetSearchTable = ({
    isLoading,
    valuesets,
    query,
    onQuerySubmit,
    onSelectionChange,
    onCreateNew
}: Props) => {
    const { page, request } = usePage();
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
                    <Button type="button" className={styles.createNewButton} outline onClick={onCreateNew}>
                        Create new value set
                    </Button>
                </div>
            </div>
            <div className={styles.tableContainer}>
                <TableComponent
                    tableHead={tableHeaders}
                    tableBody={tableRows}
                    isPagination={true}
                    pageSize={page.pageSize}
                    totalResults={page.total}
                    currentPage={page.current}
                    handleNext={request}
                    // sortData={handleSort}
                    selectable={page.status === Status.Ready}
                    rangeSelector={isLoading === true || valuesets.length > 0}
                    isLoading={isLoading}
                    display="zebra"
                />
            </div>
        </>
    );
};
