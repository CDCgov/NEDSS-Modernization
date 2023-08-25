import { Button, Icon, Table, Pagination } from '@trussworks/react-uswds';
import React from 'react';
import './style.scss';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import { Direction } from 'sorting';
import { Spinner } from '@cmsgov/design-system';

export type TableDetail = {
    id: string | number;
    title: React.ReactNode | React.ReactNode[] | string;
    class?: string;
    link?: string;
    textAlign?: string;
    type?: string;
};
export type TableContentProps = {
    tableHeader?: string;
    tableSubHeader?: React.ReactNode | React.ReactNode[] | string;
    tableHead: { name: string; sortable: boolean; sort?: string }[];
    isPagination?: boolean;
    pageSize?: number;
    totalResults?: number;
    currentPage?: number;
    handleNext?: (page: number) => void;
    buttons?: React.ReactNode | React.ReactNode[];
    sortData?: (name: string, type: string) => void;
    sortDirectionData?: (name: string, type: Direction) => void;
    tableBody: any;
    currentPageLength?: number;
    isLoading?: boolean;
};

export const SortableTable = ({
    tableHeader,
    tableHead,
    tableBody,
    isPagination = false,
    totalResults = 20,
    pageSize = TOTAL_TABLE_DATA,
    currentPage = 1,
    handleNext,
    buttons,
    tableSubHeader,
    sortData,
    sortDirectionData,
    currentPageLength,
    isLoading = false
}: TableContentProps) => {
    const handleSort = (headerName: string, type: string) => {
        sortData?.(headerName, type);
        sortDirectionData?.(headerName, type as Direction);
    };

    return (
        <div className="table-component">
            <div className="grid-row flex-align-center flex-justify padding-x-2 padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0 table-header">
                    {tableHeader}
                    {tableSubHeader}
                </p>
                {buttons}
            </div>
            <Table bordered={false} fullWidth>
                <thead>
                    <tr>
                        {tableHead.map((head: any, index) => (
                            <th
                                key={index}
                                scope="col"
                                className={head.sortable && head.sort !== 'all' ? 'sort-header' : ''}>
                                <div className="table-head">
                                    <span className="head-name">{head.name}</span>
                                    {head.sortable && (
                                        <>
                                            {head.sort === 'all' && (
                                                <Button
                                                    disabled={!tableBody || tableBody.length <= 1}
                                                    className="usa-button--unstyled"
                                                    type={'button'}
                                                    onClick={() => handleSort(head.name, 'desc')}>
                                                    <Icon.SortArrow color="black" />
                                                </Button>
                                            )}
                                            {head.sort === 'desc' && (
                                                <Button
                                                    disabled={!tableBody || tableBody.length <= 1}
                                                    className="usa-button--unstyled"
                                                    type={'button'}
                                                    onClick={() => handleSort(head.name, 'asc')}>
                                                    <Icon.ArrowUpward color="black" />
                                                </Button>
                                            )}
                                            {head.sort === 'asc' && (
                                                <Button
                                                    disabled={!tableBody || tableBody.length <= 1}
                                                    className="usa-button--unstyled"
                                                    type={'button'}
                                                    onClick={() => handleSort(head.name, 'all')}>
                                                    <Icon.ArrowDownward color="black" />
                                                </Button>
                                            )}
                                        </>
                                    )}
                                </div>
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {isLoading ? (
                        <tr className="text-center not-available">
                            <td colSpan={tableHead.length}>
                                <Spinner className="sortable-table-spinner" />
                            </td>
                        </tr>
                    ) : tableBody ? (
                        tableBody
                    ) : (
                        <tr className="text-center no-data not-available">
                            <td colSpan={tableHead.length}>No Data</td>
                        </tr>
                    )}
                </tbody>
            </Table>
            <div className="padding-2 grid-row flex-align-center flex-justify">
                <p className="margin-0 show-length-text">
                    Showing {currentPageLength ? currentPageLength + (currentPage - 1) * pageSize : totalResults} of{' '}
                    {totalResults}
                </p>
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
