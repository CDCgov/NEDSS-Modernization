import { Button, Icon, Table, Pagination } from '@trussworks/react-uswds';
import { NoData } from 'design-system/data';

type TableContentProps = {
    tableHeader?: string;
    tableHead: { name: string; sortable: boolean }[];
    tableBody: any[];
    isPagination?: boolean;
    totalResults?: number;
    currentPage?: number;
    handleNext?: (page: number) => void;
};

export const TableComponent = ({
    tableHeader,
    tableHead,
    tableBody,
    isPagination = false,
    totalResults = 20,
    currentPage = 1,
    handleNext
}: TableContentProps) => {
    return (
        <div className="">
            <div className="grid-row flex-align-center flex-justify padding-x-2 padding-y-3 border-bottom border-base-lighter">
                <p className="font-sans-lg text-bold margin-0">{tableHeader}</p>
            </div>
            <Table bordered={false} fullWidth>
                <thead>
                    <tr>
                        {tableHead.map((head: any, index) => (
                            <th key={index} scope="col">
                                <div style={{ display: 'flex', border: 'none', alignItems: 'center' }}>
                                    {head.name}
                                    {head.sortable && (
                                        <Button
                                            className="usa-button--unstyled"
                                            type={'button'}
                                            disabled={tableBody.length <= 1}>
                                            <Icon.SortArrow color="black" />
                                        </Button>
                                    )}
                                </div>
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {tableBody.map((item: any, index) => (
                        <tr key={index}>
                            {item.tableDetails.map((td: any, ind: number) =>
                                td.title ? (
                                    <td key={ind}>{td.title}</td>
                                ) : (
                                    <td key={ind} className="font-sans-md">
                                        <NoData />
                                    </td>
                                )
                            )}
                        </tr>
                    ))}
                </tbody>
            </Table>
            {isPagination && (
                <Pagination
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
    );
};
