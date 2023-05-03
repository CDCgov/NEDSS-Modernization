import { useEffect, useState } from 'react';
import { TOTAL_TABLE_DATA } from 'utils/util';

import { FindContactsNamedByPatientQuery, useFindContactsNamedByPatientLazyQuery } from 'generated/graphql/schema';
import { format } from 'date-fns';
import { SortableTable } from 'components/Table/SortableTable';
import { Tracing } from './PatientContacts';

export type Result = FindContactsNamedByPatientQuery['findContactsNamedByPatient'];

type Props = {
    patient?: string;
    pageSize?: number;
};

export const ContactNamedByPatientTable = ({ patient, pageSize = TOTAL_TABLE_DATA }: Props) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [namedByPatientData, setNamedByPatientData] = useState<any>([]);
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'Date created', sortable: true, sort: 'all' },
        { name: 'Named by', sortable: true, sort: 'all' },
        { name: 'Date named', sortable: true, sort: 'all' },
        { name: 'Description', sortable: true, sort: 'all' },
        { name: 'Associated with', sortable: true, sort: 'all' },
        { name: 'Event #', sortable: true, sort: 'all' }
    ]);

    const handleComplete = (data: FindContactsNamedByPatientQuery) => {
        const total = data?.findContactsNamedByPatient?.total || 0;
        setTotal(total);
        setNamedByPatientData(data.findContactsNamedByPatient?.content);
    };

    const [getContacts] = useFindContactsNamedByPatientLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getContacts({
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

    const description = (contact: Tracing) => (
        <>
            {contact.condition && (
                <>
                    <b>{contact.condition}</b>
                    <br />
                </>
            )}
            {contact.priority && (
                <>
                    <b>Priority:</b> {contact.priority}
                    <br />
                </>
            )}
            {contact.disposition && (
                <>
                    <b>Disposition:</b> {contact.disposition}
                    <br />
                </>
            )}
        </>
    );

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

    const sortData = (name: string, type: string) => {
        setNamedByPatientData(
            name === 'associatedWith'
                ? namedByPatientData?.slice().sort((a: any, b: any) => {
                      if (a?.associatedWith?.condition && b?.associatedWith?.condition) {
                          if (a?.associatedWith?.condition.toLowerCase() < b?.associatedWith?.condition.toLowerCase()) {
                              return type === 'asc' ? -1 : 1;
                          }
                          if (a?.associatedWith?.condition.toLowerCase() > b?.associatedWith?.condition.toLowerCase()) {
                              return type === 'asc' ? 1 : -1;
                          }
                      }
                      return 0;
                  })
                : name === 'name'
                ? namedByPatientData?.slice().sort((a: any, b: any) => {
                      if (a?.contact?.name && b?.contact?.name) {
                          if (a?.contact?.name.toLowerCase() < b?.contact?.name.toLowerCase()) {
                              return type === 'asc' ? -1 : 1;
                          }
                          if (a?.contact?.name.toLowerCase() > b?.contact?.name.toLowerCase()) {
                              return type === 'asc' ? 1 : -1;
                          }
                      }
                      return 0;
                  })
                : namedByPatientData?.slice().sort((a: any, b: any) => {
                      if (a[name] && b[name]) {
                          if (a[name].toLowerCase() < b[name].toLowerCase()) {
                              return type === 'asc' ? -1 : 1;
                          }
                          if (a[name].toLowerCase() > b[name].toLowerCase()) {
                              return type === 'asc' ? 1 : -1;
                          }
                      }
                      return 0;
                  })
        );
    };

    const handleSort = (name: string, type: string) => {
        tableHeadChanges(name, type);
        switch (name.toLowerCase()) {
            case 'date created':
                setNamedByPatientData(
                    namedByPatientData?.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.createdOn);
                        const dateB: any = new Date(b.createdOn);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'named by':
                sortData('name', type);
                break;
            case 'date named':
                setNamedByPatientData(
                    namedByPatientData?.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.namedOn);
                        const dateB: any = new Date(b.namedOn);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'description':
                sortData('condition', type);
                break;
            case 'associated with':
                sortData('associatedWith', type);
                break;
            case 'event #':
                sortData('event', type);
        }
    };

    return (
        <SortableTable
            isPagination={true}
            tableHeader={'Contact records (contacts named by patient)'}
            tableHead={tableHead}
            tableBody={
                namedByPatientData?.length > 0 &&
                namedByPatientData?.map((item: any, index: number) => {
                    return (
                        <tr key={index}>
                            <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                                {item?.createdOn ? (
                                    <a href="#" className="table-span">
                                        {format(new Date(item?.createdOn), 'MM/dd/yyyy')} <br />{' '}
                                        {format(new Date(item?.createdOn), 'hh:mm a')}
                                    </a>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                                {item?.contact?.name ? item?.contact?.name : <span className="no-data">No data</span>}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                                {item?.namedOn ? (
                                    format(new Date(item?.namedOn), 'MM/dd/yyyy')
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                                {item ? description(item) : <span className="no-data">No data</span>}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
                                {item?.associatedWith ? (
                                    <div>
                                        <p
                                            className="margin-0 text-primary text-bold link"
                                            style={{ wordBreak: 'break-word' }}>
                                            {item?.associatedWith?.local}
                                        </p>
                                        <p className="margin-0">{item?.associatedWith?.condition}</p>
                                    </div>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                                {item?.event ? item?.event : <span className="no-data">No data</span>}
                            </td>
                        </tr>
                    );
                })
            }
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
