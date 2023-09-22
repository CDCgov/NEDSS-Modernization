import { Button, Icon, Table, Pagination, Checkbox, Fieldset } from '@trussworks/react-uswds';
import React, { useState } from 'react';
import './style.scss';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import { Actions } from './Actions';
import { Direction } from 'sorting';
import { RangeToggle } from 'components/Table/RangeToggle/RangeToggle';
import { Spinner } from '@cmsgov/design-system';
import { NoData } from 'components/NoData';

type SortState = {
    [key: string]: Direction;
};

export type TableDetail = {
    id: string | number;
    title: React.ReactNode | React.ReactNode[] | string;
    class?: string;
    link?: string;
    textAlign?: string;
    type?: string;
};

type Header = {
    name: string;
    sortable: boolean;
};

export type TableBody = {
    id: number | string | undefined | null;
    checkbox?: boolean;
    tableDetails: TableDetail[];
    data?: any;
    expanded?: boolean;
    expandedViewComponent?: React.ReactNode;
};

type SortHandler = (name: string, type: Direction) => void;

export type TableContentProps = {
    tableHeader?: string;
    tableSubHeader?: React.ReactNode | React.ReactNode[] | string;
    tableHead: Header[];
    tableBody: TableBody[];
    isPagination?: boolean;
    pageSize?: number;
    totalResults?: number;
    currentPage?: number;
    handleNext?: (page: number) => void;
    buttons?: React.ReactNode | React.ReactNode[];
    footerAction?: React.ReactNode | React.ReactNode[];
    dataNotAvailableElement?: React.ReactNode | React.ReactNode[];
    sortData?: SortHandler;
    handleAction?: (type: string, data: any) => void;
    rangeSelector?: boolean;
    handleSelected?: (event: React.ChangeEvent<HTMLInputElement>, item: any) => void;
    isLoading?: boolean;
};

const nextDirection = (direction: Direction) => {
    switch (direction) {
        case Direction.None:
            return Direction.Descending;
        case Direction.Descending:
            return Direction.Ascending;
        case Direction.Ascending:
            return Direction.None;
    }
};

export const renderTitle = (detail: TableDetail) =>
    detail.link ? <a href={detail.link}>{detail.title}</a> : <>{detail.title}</>;

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
    footerAction,
    dataNotAvailableElement,
    tableSubHeader,
    handleAction,
    sortData,
    rangeSelector = false,
    handleSelected,
    isLoading = false
}: TableContentProps) => {
    const initialState: SortState = {};
    tableHead.forEach((header) => (initialState[header.name] = Direction.None));
    const [sortState, setSortState] = useState<SortState>(initialState);
    const [isActions, setIsActions] = useState<any>(null);

    const handleSort = (headerName: string) => {
        const next = nextDirection(sortState[headerName]);

        sortData?.(headerName, next);

        setSortState({
            ...initialState,
            [headerName]: next
        });
    };

    const resolveSortIcon = (direction: Direction) => {
        switch (direction) {
            case Direction.Ascending:
                return <Icon.ArrowDownward color="black" />;
            case Direction.Descending:
                return <Icon.ArrowUpward color="black" />;
            default:
                return <Icon.SortArrow color="black" />;
        }
    };

    const resolveSortAria = (direction: Direction) => {
        switch (direction) {
            case Direction.Ascending:
                return 'ascending';
            case Direction.Descending:
                return 'descending';
            default:
                return;
        }
    };

    const resolveHeaderStyle = (direction: Direction) => (direction !== Direction.None ? 'sort-header' : '');

    const renderHeader = (head: Header, index: number) => {
        const direction = sortState[head.name];
        const ariaSort = resolveSortAria(direction);
        const style = resolveHeaderStyle(direction);
        return (
            <th
                key={index}
                scope="col"
                {...(style && { className: style })}
                {...(ariaSort && { 'aria-sort': ariaSort })}>
                <div className="table-head">
                    <span className="head-name">{head.name}</span>
                    {head.sortable && (
                        <Button
                            disabled={!tableBody || tableBody.length <= 1}
                            className="usa-button--unstyled"
                            type={'button'}
                            aria-label="sort"
                            onClick={() => handleSort(head.name)}>
                            {resolveSortIcon(direction)}
                        </Button>
                    )}
                </div>
            </th>
        );
    };
    const isSorting = (header: string) => sortState[header] !== Direction.None;

    const resolveDetailStyle = (detail: TableDetail, index: number) => {
        let style = 'table-data';

        if (detail.textAlign) {
            style += `text-${detail?.textAlign}`;
        }

        if (isSorting(tableHead[index].name)) {
            style += ' sort-td';
        }

        return style;
    };

    const dataNotAvailalbe = (
        <tr className="text-center no-data not-available">
            <td colSpan={tableHead.length}>{dataNotAvailableElement ? dataNotAvailableElement : <NoData />}</td>
        </tr>
    );

    const renderNoDataDetail = (detail: TableDetail, column: number, isCheckbox?: boolean) => (
        <NoData key={column} className={`${resolveDetailStyle(detail, column)} ${isCheckbox && 'margin-left-4'}`} />
    );

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
                <thead>
                    <tr>{tableHead.map(renderHeader)}</tr>
                </thead>
                <tbody>
                    {isLoading ? (
                        <tr className="text-center not-available">
                            <td colSpan={tableHead.length}>
                                <Spinner className="sortable-table-spinner" />
                            </td>
                        </tr>
                    ) : tableBody?.length > 0 ? (
                        tableBody.map((item: TableBody, row: number) => (
                            <React.Fragment key={row}>
                                <tr>
                                    {item.tableDetails.map((detail: TableDetail, column: number) => (
                                        <td className={resolveDetailStyle(detail, column)} key={column}>
                                            {column === 0 && item.checkbox && (
                                                <Fieldset>
                                                    <Checkbox
                                                        key={row}
                                                        id={`${detail.title}-${row}`}
                                                        name={'tableCheck'}
                                                        label=""
                                                        value={item?.tableDetails[1].title as string}
                                                        onChange={(e) => handleSelected?.(e, item)}
                                                    />
                                                </Fieldset>
                                            )}
                                            {detail.title ? (
                                                <>
                                                    {detail?.type !== 'actions' && (
                                                        <span
                                                            className={
                                                                column === 0 && item.checkbox
                                                                    ? 'check-title'
                                                                    : detail.class
                                                                    ? detail.class
                                                                    : 'table-span'
                                                            }>
                                                            {renderTitle(detail)}
                                                        </span>
                                                    )}
                                                    {detail?.type === 'actions' && (
                                                        <div className="table-span">
                                                            <Button
                                                                onClick={() =>
                                                                    setIsActions(isActions === row ? null : row)
                                                                }
                                                                type="button"
                                                                unstyled>
                                                                {detail.title}
                                                            </Button>
                                                            {isActions === row && (
                                                                <Actions
                                                                    handleOutsideClick={() => setIsActions(null)}
                                                                    handleAction={(data: string) => {
                                                                        handleAction?.(
                                                                            data,
                                                                            JSON.stringify(item?.data)
                                                                        );
                                                                        setIsActions(null);
                                                                    }}
                                                                />
                                                            )}
                                                        </div>
                                                    )}
                                                </>
                                            ) : (
                                                renderNoDataDetail(detail, column, item.checkbox)
                                            )}
                                        </td>
                                    ))}
                                </tr>
                                {item.expanded && (
                                    <tr>
                                        <td colSpan={tableHead.length}>
                                            <div>{item.expandedViewComponent}</div>
                                        </td>
                                    </tr>
                                )}
                            </React.Fragment>
                        ))
                    ) : (
                        dataNotAvailalbe
                    )}
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
                            <RangeToggle />
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
            <div className="table-footer-action">{footerAction}</div>
        </div>
    );
};
