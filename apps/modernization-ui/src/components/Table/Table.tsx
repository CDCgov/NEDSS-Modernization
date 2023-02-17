import { Button, Icon, Table, Pagination } from '@trussworks/react-uswds';
import React, { useState } from 'react';
import './style.scss';
import { TOTAL_TABLE_DATA } from '../../utils/util';

export type TableDetail = {
    id: string | number;
    title: React.ReactNode | React.ReactNode[] | string;
};

export type TableBody = {
    id: number | string | undefined | null;
    checkbox?: boolean;
    tableDetails: TableDetail[];
};

export type TableContentProps = {
    tableHeader?: string;
    tableSubHeader?: React.ReactNode | React.ReactNode[] | string;
    tableHead: { name: string; sortable: boolean }[];
    tableBody: TableBody[];
    isPagination?: boolean;
    totalResults?: number;
    currentPage?: number;
    handleNext?: (page: number) => void;
    buttons?: React.ReactNode | React.ReactNode[];
    sortData?: (name: string, type: string) => void;
};

export const TableComponent = ({
    tableHeader,
    tableHead,
    tableBody,
    isPagination = false,
    totalResults = 20,
    currentPage = 1,
    handleNext,
    buttons,
    tableSubHeader,
    sortData
}: TableContentProps) => {
    const [sort, setSort] = useState<boolean>(false);
    const handleSort = (headerName: string) => {
        setSort(!sort);
        sortData?.(headerName, !sort ? 'asc' : 'desc');
    };
    return (
        <div>
            <div className="grid-row flex-align-center flex-justify padding-x-2 padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0">
                    {tableHeader}
                    {tableSubHeader}
                </p>
                {buttons}
            </div>
            <Table scrollable bordered={false} fullWidth>
                <thead>
                    <tr>
                        {tableHead.map((head: any, index) => (
                            <th key={index} scope="col">
                                <div className="tableHead">
                                    {head.name}
                                    {head.sortable && (
                                        <Button
                                            className="usa-button--unstyled"
                                            type={'button'}
                                            onClick={() => handleSort(head.name)}>
                                            <Icon.SortArrow color="black" />
                                        </Button>
                                    )}
                                </div>
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {tableBody?.length > 0 ? (
                        tableBody.map((item: any, index) => (
                            <tr key={index}>
                                {item.tableDetails.map((td: any, ind: number) =>
                                    td.title ? (
                                        td.title === 'Not available yet' ? (
                                            <td key={ind} className="font-sans-md no-data">
                                                {td.title}
                                            </td>
                                        ) : (
                                            <td key={ind}>{td.title}</td>
                                        )
                                    ) : (
                                        <td key={ind} className="font-sans-md no-data">
                                            No data
                                        </td>
                                    )
                                )}
                            </tr>
                        ))
                    ) : (
                        <tr className="text-center no-data">Not Available</tr>
                    )}
                </tbody>
            </Table>
            <div className="padding-2 padding-top-0 grid-row flex-align-center flex-justify">
                <p className="margin-0 show-length-text">
                    Showing {tableBody?.length} of {tableBody?.length}
                </p>
                {isPagination && tableBody?.length >= TOTAL_TABLE_DATA && (
                    <Pagination
                        className="margin-0 pagination"
                        totalPages={Math.ceil(totalResults / TOTAL_TABLE_DATA)}
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
