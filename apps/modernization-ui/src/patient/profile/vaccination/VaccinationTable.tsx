import { useEffect, useState } from 'react';
import { Button, Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { FindVaccinationsForPatientQuery, useFindVaccinationsForPatientLazyQuery } from 'generated/graphql/schema';

import { TOTAL_TABLE_DATA } from 'utils/util';
import { SortableTable } from 'components/Table/SortableTable';

type PatientVaccinationTableProps = {
    patient?: string;
    pageSize?: number;
};

export const VaccinationTable = ({ patient, pageSize = TOTAL_TABLE_DATA }: PatientVaccinationTableProps) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [vaccinationData, setVaccinationData] = useState<any>([]);
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'Date created', sortable: true, sort: 'all' },
        { name: 'Provider', sortable: true, sort: 'all' },
        { name: 'Date administered', sortable: true, sort: 'all' },
        { name: 'Vaccine administered', sortable: true, sort: 'all' },
        { name: 'Associated with', sortable: true, sort: 'all' },
        { name: 'Events', sortable: true, sort: 'all' }
    ]);

    const handleComplete = (data: FindVaccinationsForPatientQuery) => {
        setTotal(data?.findVaccinationsForPatient?.total || 0);
        setVaccinationData(data.findVaccinationsForPatient?.content);
    };

    const [getVaccination] = useFindVaccinationsForPatientLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getVaccination({
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

    const sortData = (name: string, type: string) => {
        setVaccinationData(
            name === 'associatedWith'
                ? vaccinationData?.slice().sort((a: any, b: any) => {
                      if (a[name] && b[name]) {
                          if (a?.associatedWith?.condition.toLowerCase() < b?.associatedWith?.condition.toLowerCase()) {
                              return type === 'asc' ? -1 : 1;
                          }
                          if (a?.associatedWith?.condition.toLowerCase() > b?.associatedWith?.condition.toLowerCase()) {
                              return type === 'asc' ? 1 : -1;
                          }
                      }
                      return 0;
                  })
                : vaccinationData?.slice().sort((a: any, b: any) => {
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
                setVaccinationData(
                    vaccinationData.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.createdOn);
                        const dateB: any = new Date(b.createdOn);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'date administered':
                setVaccinationData(
                    vaccinationData.slice().sort((a: any, b: any) => {
                        const dateA: any = new Date(a.administeredOn);
                        const dateB: any = new Date(b.administeredOn);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'provider':
                sortData('provider', type);
                break;
            case 'vaccine administered':
                sortData('administered', type);
                break;
            case 'associated with':
                sortData('associatedWith', type);
                break;
            case 'events':
                sortData('event', type);
                break;
        }
    };

    console.log('vaccinationData:', vaccinationData);

    return (
        <SortableTable
            isPagination={true}
            buttons={
                <div className="grid-row">
                    <Button type="button" className="grid-row">
                        <Icon.Add className="margin-right-05" />
                        Add vaccination
                    </Button>
                </div>
            }
            tableHeader={'Vaccinations'}
            tableHead={tableHead}
            tableBody={
                vaccinationData?.length > 0 &&
                vaccinationData?.map((vaccination: any, index: number) => {
                    return (
                        <tr key={index}>
                            <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                                {vaccination?.createdOn ? (
                                    <a href="#" className="table-span">
                                        {format(new Date(vaccination?.createdOn), 'MM/dd/yyyy')} <br />{' '}
                                        {format(new Date(vaccination?.createdOn), 'hh:mm a')}
                                    </a>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                                {vaccination?.provider ? (
                                    <span>{vaccination.provider}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                                {vaccination?.administeredOn ? (
                                    <span className="table-span">
                                        {format(new Date(vaccination?.administeredOn), 'MM/dd/yyyy')} <br />{' '}
                                        {format(new Date(vaccination?.administeredOn), 'hh:mm a')}
                                    </span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                                {vaccination?.administered ? (
                                    <span>{vaccination?.administered}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
                                {!vaccination || !vaccination?.associatedWith ? (
                                    <span className="no-data">No data</span>
                                ) : (
                                    <div>
                                        <p
                                            className="margin-0 text-primary text-bold link"
                                            style={{ wordBreak: 'break-word' }}>
                                            {vaccination?.associatedWith?.id}
                                        </p>
                                        <p className="margin-0">{vaccination?.associatedWith?.condition}</p>
                                    </div>
                                )}
                            </td>
                            <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                                {vaccination?.event ? (
                                    <span>{vaccination?.event}</span>
                                ) : (
                                    <span className="no-data">No data</span>
                                )}
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
