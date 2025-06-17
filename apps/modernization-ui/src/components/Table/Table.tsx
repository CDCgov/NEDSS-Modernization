import { Table, Pagination, Checkbox } from '@trussworks/react-uswds';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import { RangeToggle } from 'components/Table/RangeToggle/RangeToggle';
import { NoData } from 'design-system/data';
import { Loading } from 'components/Spinner';
import { SortHandler, Sorting, useTableSorting } from './useTableSorting';
import { TableHeader } from './TableHeader';
import classNames from 'classnames';
import { ChangeEvent, ChangeEventHandler, Fragment, ReactNode } from 'react';
import { Column, resolveColumns } from './resolveColumns';
import styles from './table.module.scss';

type SelectionMode = 'select' | 'deselect';

type SelectionHandler = (mode: SelectionMode) => void;

type OldSelectionHandler = (event: ChangeEvent<HTMLInputElement>, item: any) => void;

export type Cell = {
    id: string | number;
    title: ReactNode | ReactNode[] | string;
};

export type Header = {
    name: string;
    sortable?: boolean;
};

export type TableBody = {
    key?: number | string | undefined;
    checkbox?: boolean;
    radioButton?: boolean;
    id: number | string | undefined | null;
    tableDetails: Cell[];
    data?: any;
    expanded?: boolean;
    expandedViewComponent?: ReactNode;
    selectable?: boolean | (() => boolean);
    onSelect?: SelectionHandler;
};

export type Props = {
    display?: 'standard' | 'zebra';
    className?: string;
    tableHeader?: string;
    tableHead: Header[];
    tableBody: TableBody[];
    isPagination?: boolean;
    pageSize?: number;
    totalResults?: number;
    currentPage?: number;
    handleNext?: (page: number) => void;
    buttons?: ReactNode | ReactNode[];
    sortData?: SortHandler;
    rangeSelector?: boolean;
    selectable?: boolean;
    handleSelected?: OldSelectionHandler;
    isLoading?: boolean;
};

export const TableComponent = ({
    display = 'standard',
    className,
    tableHeader,
    tableHead,
    tableBody,
    isPagination = false,
    pageSize = TOTAL_TABLE_DATA,
    totalResults = 20,
    currentPage = 1,
    handleNext,
    buttons,
    sortData,
    rangeSelector = false,
    selectable = false,
    handleSelected,
    isLoading = false
}: Props) => {
    const sorting = useTableSorting({ enabled: tableBody && totalResults > 1, onSort: sortData });

    const columns = resolveColumns(selectable, tableHead);

    const showHeader = tableHeader || buttons;

    const handleRowSelection =
        (row: TableBody, handleSelected?: OldSelectionHandler): ChangeEventHandler<HTMLInputElement> =>
        (event) => {
            handleSelected?.(event, row);
            if (row.onSelect) {
                const mode = event.target.checked ? 'select' : 'deselect';
                row.onSelect(mode);
            }
        };

    const renderRows = (sorting: Sorting, rows: TableBody[], selectable: boolean) => {
        if (rows?.length === 0) {
            return dataNotAvailalbe(columns.length);
        }

        const offset = selectable ? 1 : 0;

        return rows.map((row: TableBody, index: number) => {
            return (
                <Fragment key={index}>
                    <tr>
                        {selectable && (
                            <td className={styles.selectable}>
                                {row.radioButton ? (
                                    <input
                                        key={`selection-${index}`}
                                        id={`selection-${index}`}
                                        name={'selection'}
                                        type="radio"
                                        onChange={handleRowSelection(row, handleSelected)}
                                    />
                                ) : (
                                    <Checkbox
                                        disabled={!row.selectable}
                                        key={`selection-${index}`}
                                        id={`selection-${index}`}
                                        name={'selection'}
                                        label=""
                                        onChange={handleRowSelection(row, handleSelected)}
                                    />
                                )}
                            </td>
                        )}
                        {row.tableDetails.map((detail: Cell, column: number) => {
                            const isSorting = sorting.isSorting(columns[offset + column].name);
                            const className = classNames({ [styles.sorted]: isSorting });
                            return (
                                <td className={className} key={column}>
                                    {detail.title ? detail.title : <NoData key={column} />}
                                </td>
                            );
                        })}
                    </tr>
                    {row.expanded && (
                        <tr>
                            <td colSpan={columns.length}>
                                <div>{row.expandedViewComponent}</div>
                            </td>
                        </tr>
                    )}
                </Fragment>
            );
        });
    };

    return (
        <div className={styles.table}>
            {showHeader && (
                <header>
                    <h2>{tableHeader}</h2>
                    {buttons}
                </header>
            )}
            <>
                <Table
                    bordered={false}
                    fullWidth
                    className={classNames(
                        {
                            [styles.standard]: display === 'standard',
                            [styles.zebra]: display === 'zebra'
                        },
                        className
                    )}>
                    <TableHeaders sorting={sorting} columns={columns} />
                    <tbody>
                        {isLoading ? (
                            <LoadingRow columns={columns.length} />
                        ) : (
                            renderRows(sorting, tableBody, selectable)
                        )}
                    </tbody>
                </Table>
                <footer>
                    <div className={styles.range}>
                        {!rangeSelector ? (
                            <>
                                Showing {tableBody?.length} of {totalResults}
                            </>
                        ) : (
                            <>
                                Showing <RangeToggle /> of <span id="totalRowCount">{totalResults}</span>
                            </>
                        )}
                    </div>

                    {isPagination && totalResults >= pageSize && (
                        <Pagination
                            className={styles.pagination}
                            totalPages={Math.ceil(totalResults / pageSize)}
                            currentPage={currentPage}
                            pathname={'/patient-profile'}
                            onClickNext={() => handleNext?.(currentPage + 1)}
                            onClickPrevious={() => handleNext?.(currentPage - 1)}
                            onClickPageNumber={(_, page) => handleNext?.(page)}
                        />
                    )}
                </footer>
            </>
        </div>
    );
};

const dataNotAvailalbe = (columns: number) => (
    <tr className="text-center no-data not-available">
        <td colSpan={columns}>
            <NoData />
        </td>
    </tr>
);

// Header
type TableHeadersProps = {
    sorting: Sorting;
    columns: Column[];
};
const TableHeaders = ({ sorting, columns }: TableHeadersProps) => (
    <thead>
        <tr>
            {columns.map((column, index) =>
                column.type === 'data' ? (
                    <TableHeader key={index} sorting={sorting} header={column} />
                ) : (
                    <SelectionHeader key={index} />
                )
            )}
        </tr>
    </thead>
);

const SelectionHeader = () => <th scope="col" className={styles.selectable}></th>;

//  Rows
type LoadingProps = {
    columns: number;
};
const LoadingRow = ({ columns }: LoadingProps) => (
    <tr>
        <td colSpan={columns}>
            <Loading center />
        </td>
    </tr>
);

export type { SelectionMode, SelectionHandler };
