import { useEffect, useState } from 'react';
import format from 'date-fns/format';
import { Document, AssociatedWith, Headers } from './PatientDocuments';
import { FindDocumentsForPatientQuery, useFindDocumentsForPatientLazyQuery } from 'generated/graphql/schema';
import { transform } from './PatientDocumentTransformer';
import { sort } from './PatientDocumentSorter';
import { Direction } from 'sorting';
import { SortableTable } from 'components/Table/SortableTable';

const association = (association?: AssociatedWith | null) =>
    association && (
        <>
            <div>
                <p className="margin-0 text-primary text-bold link" style={{ wordBreak: 'break-word' }}>
                    {association.local}
                </p>
            </div>
        </>
    );

type Props = {
    patient?: string;
    pageSize: number;
    nbsBase: string;
};

export const PatientDocumentTable = ({ patient, pageSize, nbsBase }: Props) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [items, setItems] = useState<Document[]>([]);
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: Headers.DateReceived, sortable: true, sort: 'all' },
        { name: Headers.Type, sortable: true, sort: 'all' },
        { name: Headers.SendingFacility, sortable: true, sort: 'all' },
        { name: Headers.DateReported, sortable: true, sort: 'all' },
        { name: Headers.Condition, sortable: true, sort: 'all' },
        { name: Headers.AssociatedWith, sortable: true, sort: 'all' },
        { name: Headers.EventID, sortable: true, sort: 'all' }
    ]);

    const handleComplete = (data: FindDocumentsForPatientQuery) => {
        const total = data?.findDocumentsForPatient?.total || 0;
        setTotal(total);

        const content = transform(data?.findDocumentsForPatient);
        setItems(content);
    };

    const [getDocuments] = useFindDocumentsForPatientLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getDocuments({
                variables: {
                    patient: patient,
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize
                    }
                }
            });
        }
    }, [patient, currentPage]);

    const tableHeadChanges = (name: string, type: string) => {
        tableHead.map((item) => {
            if (item.name.toLowerCase() === name.toLowerCase()) {
                item.sort = type;
            } else {
                item.sort = 'all';
            }
        });
        setTableHead(tableHead);
    };

    const handleSort = (name: string, direction: string): void => {
        tableHeadChanges(name, direction);
        const criteria = { name: name as Headers, type: direction as Direction };
        setItems(sort(items, criteria));
    };

    return (
        <SortableTable
            tableHeader={'Documents'}
            tableHead={tableHead}
            tableBody={
                items?.length > 0 &&
                items?.map((document: any, index: number) => {
                    return (
                        <tr key={index}>
                            <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                                {document?.receivedOn ? (
                                    <a
                                        href={`${nbsBase}/ViewFile1.do?ContextAction=DocumentIDOnEvents&nbsDocumentUid=${document?.document}`}
                                        className="table-span">
                                        {format(document?.receivedOn, 'MM/dd/yyyy')} <br />{' '}
                                        {format(document?.receivedOn, 'hh:mm a')}
                                    </a>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                                {document?.type ? (
                                    <span>{document?.type}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                                {document?.sendingFacility ? (
                                    <span>{document?.sendingFacility}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                                {document?.reportedOn ? (
                                    <span className="table-span">
                                        {format(new Date(document?.reportedOn), 'MM/dd/yyyy')}
                                    </span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
                                {document?.condition ? (
                                    <span>{document?.condition}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                                {!document?.associatedWith ? (
                                    <span className="no-data">No data</span>
                                ) : (
                                    association(document?.associatedWith)
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[6].sort !== 'all' && 'sort-td'}`}>
                                {document?.event ? (
                                    <span>{document?.event}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                        </tr>
                    );
                })
            }
            isPagination={true}
            pageSize={pageSize}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
