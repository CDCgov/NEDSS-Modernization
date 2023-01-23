import { Button, Icon, Table, Pagination } from '@trussworks/react-uswds';
import React from 'react';

export type TableContentProps = {
    tableHeader?: string;
    tableSubHeader?: React.ReactNode | React.ReactNode[] | string;
    tableHead: { name: string; sortable: boolean }[];
    tableBody: any[];
    isPagination?: boolean;
    totalResults?: number;
    currentPage?: number;
    handleNext?: (page: number) => void;
    buttons?: React.ReactNode | React.ReactNode[];
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
    tableSubHeader
}: TableContentProps) => {
    return (
        <div className="">
            <div className="grid-row flex-align-center flex-justify padding-x-2 padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0">
                    {tableHeader}
                    {tableSubHeader}
                </p>
                {buttons}
            </div>
            <Table bordered={false} fullWidth>
                <thead>
                    <tr>
                        {tableHead.map((head: any, index) => (
                            <th key={index} scope="col">
                                <div style={{ display: 'flex', border: 'none', alignItems: 'center' }}>
                                    {head.name}
                                    {head.sortable && (
                                        <Button className="usa-button--unstyled" type={'button'}>
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
                                        <td key={ind}>{td.title}</td>
                                    ) : (
                                        <td
                                            key={ind}
                                            style={{ color: '#747474', fontStyle: 'italic' }}
                                            className="font-sans-md">
                                            No data
                                        </td>
                                    )
                                )}
                            </tr>
                        ))
                    ) : (
                        <tr className="text-center" style={{ color: '#747474', fontStyle: 'italic' }}>
                            No entries
                        </tr>
                    )}
                </tbody>
            </Table>
            <div className="padding-2 padding-top-0 grid-row flex-align-center flex-justify">
                <p style={{ color: '#71767A' }} className="margin-0">
                    Showing {tableBody?.length} of {tableBody?.length}
                </p>
                {isPagination && tableBody.length > 9 && (
                    <Pagination
                        className="margin-0"
                        style={{ justifyContent: 'flex-end' }}
                        totalPages={Math.ceil(totalResults / 10)}
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
