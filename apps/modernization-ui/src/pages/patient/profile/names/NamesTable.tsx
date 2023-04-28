/* eslint-disable @typescript-eslint/no-unused-vars */
import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { SortableTable } from 'components/Table/SortableTable';
import { AddNameModal } from 'pages/patientProfile/components/AddNameModal';
import { DetailsNameModal } from 'pages/patientProfile/components/DemographicDetails/DetailsNameModal';
import { Actions } from 'components/Table/Actions';
import { useFindPatientProfileNames } from '../useFindPatientProfileNames';
import { TOTAL_TABLE_DATA } from 'utils/util';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

export const NamesTable = ({ patient }: PatientLabReportTableProps) => {
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true, sort: 'all' },
        { name: 'Type', sortable: true, sort: 'all' },
        { name: 'Prefix', sortable: true, sort: 'all' },
        { name: 'Name ( last, first middle )', sortable: true, sort: 'all' },
        { name: 'Suffix', sortable: true, sort: 'all' },
        { name: 'Degree', sortable: true, sort: 'all' },
        { name: 'Actions', sortable: true, sort: 'all' }
    ]);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const addNameModalRef = useRef<ModalRef>(null);
    const detailsNameModalRef = useRef<ModalRef>(null);
    const deleteModalRef = useRef<ModalRef>(null);

    const [isEditModal, setIsEditModal] = useState<boolean>(false);
    const [nameDetails, setNameDetails] = useState<any>(undefined);
    const [isActions, setIsActions] = useState<any>(null);

    const [getProfile, { data }] = useFindPatientProfileNames();

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    patient: patient,
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize: TOTAL_TABLE_DATA
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

    return (
        <SortableTable
            isPagination={true}
            buttons={
                <div className="grid-row">
                    <Button
                        type="button"
                        onClick={() => {
                            addNameModalRef.current?.toggleModal();
                            setNameDetails(null);
                            setIsEditModal(false);
                        }}
                        className="display-inline-flex">
                        <Icon.Add className="margin-right-05" />
                        Add name
                    </Button>
                    <AddNameModal modalHead={isEditModal ? 'Edit - Name' : 'Add - Name'} modalRef={addNameModalRef} />
                    <DetailsNameModal data={nameDetails} modalRef={detailsNameModalRef} />
                </div>
            }
            tableHeader={'Names'}
            tableHead={tableHead}
            tableBody={data?.findPatientProfile?.names?.content?.map((name, index: number) => (
                <tr key={index}>
                    <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                        {name?.asOf ? (
                            <a href="#">
                                {format(new Date(name?.asOf), 'MM/dd/yyyy')} <br />{' '}
                            </a>
                        ) : (
                            <span className="no-data">No data</span>
                        )}
                    </td>
                    <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                        <span className="no-data">No data</span>
                    </td>
                    <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                        {name?.prefix ? (
                            <span>{name?.prefix.description}</span>
                        ) : (
                            <span className="no-data">No data</span>
                        )}
                    </td>
                    <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                        {name?.last || name?.first ? (
                            <span>{`${name?.last}, ${name?.first}, ${name?.middle}`}</span>
                        ) : (
                            <span className="no-data">No data</span>
                        )}
                    </td>
                    <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                        {name?.suffix ? (
                            <span>{name?.suffix.description}</span>
                        ) : (
                            <span className="no-data">No data</span>
                        )}
                    </td>
                    <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                        {name?.degree ? (
                            <span>{name?.degree.description}</span>
                        ) : (
                            <span className="no-data">No data</span>
                        )}
                    </td>
                    <td>
                        <div className="table-span">
                            <Button type="button" unstyled>
                                <Button
                                    type="button"
                                    unstyled
                                    onClick={() => setIsActions(isActions === index ? null : index)}>
                                    <Icon.MoreHoriz className="font-sans-lg" />
                                </Button>
                            </Button>
                            {isActions === index && (
                                <Actions
                                    handleOutsideClick={() => setIsActions(null)}
                                    handleAction={(type: string) => {
                                        if (type === 'edit') {
                                            setIsEditModal(true);
                                            addNameModalRef.current?.toggleModal();
                                        }
                                        if (type === 'delete') {
                                            // setIsDeleteModal(true);
                                            deleteModalRef.current?.toggleModal();
                                        }
                                        if (type === 'details') {
                                            setNameDetails(name);
                                            detailsNameModalRef.current?.toggleModal();
                                        }
                                        setIsActions(null);
                                    }}
                                />
                            )}
                        </div>
                    </td>
                </tr>
            ))}
            totalResults={data?.findPatientProfile?.names?.total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            // sortData={handleSort}
        />
    );
};
