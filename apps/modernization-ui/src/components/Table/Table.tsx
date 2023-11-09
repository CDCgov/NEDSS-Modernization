import { Table, Pagination, Checkbox } from '@trussworks/react-uswds';
import './style.scss';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import { RangeToggle } from 'components/Table/RangeToggle/RangeToggle';
import { Spinner } from '@cmsgov/design-system';
import { NoData } from 'components/NoData';
import { SortHandler, Sorting, useTableSorting } from './useTableSorting';
import { TableHeader } from './TableHeader';
import classNames from 'classnames';
import { ChangeEvent, ChangeEventHandler, Fragment, ReactNode } from 'react';
import { Column, resolveColumns } from './resolveColumns';

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
    id: number | string | undefined | null;
    tableDetails: Cell[];
    data?: any;
    expanded?: boolean;
    expandedViewComponent?: ReactNode;
    selectable?: boolean | (() => boolean);
    onSelect?: SelectionHandler;
};

export type Props = {
    tableHeader?: string;
    tableSubHeader?: ReactNode | ReactNode[] | string;
    tableHead: Header[];
    tableBody: TableBody[];
    isPagination?: boolean;
    pageSize?: number;
    totalResults?: number;
    currentPage?: number;
    handleNext?: (page: number) => void;
    buttons?: ReactNode | ReactNode[];
    dataNotAvailableElement?: ReactNode | ReactNode[];
    sortData?: SortHandler;
    rangeSelector?: boolean;
    selectable?: boolean;
    handleSelected?: OldSelectionHandler;
    isLoading?: boolean;
    contextName?: 'pages' | 'conditions' | 'questions' | 'valuesets' | 'templates';
};

export const TableComponent = ({
    tableHeader,
    tableHead,
    tableBody,
    isPagination = false,
    pageSize = TOTAL_TABLE_DATA,
    totalResults = 20,
    currentPage = 1,
    handleNext,
    buttons,
    dataNotAvailableElement = <NoData />,
    tableSubHeader,
    sortData,
    rangeSelector = false,
    selectable = false,
    handleSelected,
    isLoading = false,
    contextName
}: Props) => {
    const sorting = useTableSorting({ enabled: tableBody && tableBody.length > 1, onSort: sortData });

    const columns = resolveColumns(selectable, tableHead);

    const dataNotAvailalbe = (columns: number) => (
        <tr className="text-center no-data not-available">
            <td colSpan={columns}>{dataNotAvailableElement}</td>
        </tr>
    );

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

        return rows.map((row: TableBody, index: number) => (
            <Fragment key={index}>
                <tr>
                    {selectable && (
                        <td className="table-data selection">
                            <Checkbox
                                disabled={!row.selectable}
                                key={`selection-${index}`}
                                id={`selection-${index}`}
                                name={'selection'}
                                label=""
                                onChange={handleRowSelection(row, handleSelected)}
                            />
                        </td>
                    )}
                    {row.tableDetails.map((detail: Cell, column: number) => {
                        const isSorting = sorting.isSorting(columns[offset + column].name);
                        const className = classNames('table-data', { 'sort-td': isSorting });
                        return (
                            <td className={className} key={column}>
                                {detail.title ? (
                                    <span className={'table-span'}>{detail.title}</span>
                                ) : (
                                    <NoData key={column} className={className} />
                                )}
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
        ));
    };

    return (
        <div>
            <div className="grid-row flex-align-center flex-justify padding-x-2 search-box padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0 table-header">
                    {tableHeader}
                    {tableSubHeader}
                </p>
                {buttons}
            </div>
            <Table bordered={false} fullWidth>
                <TableHeaders sorting={sorting} columns={columns} />
                <tbody>
                    {isLoading ? <Loading columns={columns.length} /> : renderRows(sorting, tableBody, selectable)}
                </tbody>
            </Table>
            <div className="padding-2 padding-top-0 grid-row flex-align-center flex-justify">
                <div className="table__range">
                    {!rangeSelector ? (
                        <p className="margin-0 show-length-text">
                            Showing {tableBody?.length} of {totalResults}
                        </p>
                    ) : (
                        <>
                            <span>Showing &nbsp;</span>
                            <RangeToggle contextName={contextName} />
                            <span> &nbsp;of {totalResults}</span>
                        </>
                    )}
                </div>
                {isPagination && totalResults >= pageSize && (
                    <Pagination
                        className="margin-0 pagination"
                        totalPages={Math.ceil(totalResults / pageSize)}
                        currentPage={currentPage}
                        pathname={'/patient-profile'}
                        onClickNext={() => handleNext?.(currentPage + 1)}
                        onClickPrevious={() => handleNext?.(currentPage - 1)}
                        onClickPageNumber={(_, page) => handleNext?.(page)}
                    />
                )}
            </div>
        </div>
    );
};

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

const SelectionHeader = () => (
    <th scope="col" className="selection">
        <div className="table-head">
            <span className="head-name" />
        </div>
    </th>
);

//  Rows
type LoadingProps = {
    columns: number;
};
const Loading = ({ columns }: LoadingProps) => (
    <tr className="text-center not-available">
        <td colSpan={columns}>
            <Spinner className="sortable-table-spinner" />
        </td>
    </tr>
);

export type { SelectionMode, SelectionHandler };
