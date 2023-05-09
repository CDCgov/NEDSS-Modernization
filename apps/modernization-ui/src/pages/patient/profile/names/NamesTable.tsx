import { useEffect, useRef, useState } from 'react';
import format from 'date-fns/format';
import { Button, ButtonGroup, Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import { SortableTable } from 'components/Table/SortableTable';
import { AddNameModal } from 'pages/patient/profile/names/AddNameModal';
import { DetailsNameModal } from 'pages/patient/profile/names/DetailsNameModal';
import { Actions } from 'components/Table/Actions';
import { useFindPatientProfileNames } from '../useFindPatientProfileNames';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { Name } from './names';
import { FindPatientProfileQuery } from 'generated/graphql/schema';
import { Direction, sortByAlpha, sortByNestedProperty, withDirection } from 'sorting/Sort';

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
        { name: 'Actions', sortable: false }
    ]);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const addNameModalRef = useRef<ModalRef>(null);
    const detailsNameModalRef = useRef<ModalRef>(null);
    const deleteModalRef = useRef<ModalRef>(null);

    const [isEditModal, setIsEditModal] = useState<boolean>(false);
    const [nameDetails, setNameDetails] = useState<any>(undefined);
    const [isActions, setIsActions] = useState<any>(null);
    const [names, setNames] = useState<Name[]>([]);

    const [isDeleteModal, setIsDeleteModal] = useState<boolean>(false);

    const handleComplete = (data: FindPatientProfileQuery) => {
        if (data?.findPatientProfile?.names?.content && data?.findPatientProfile?.names?.content?.length > 0) {
            setNames(data?.findPatientProfile?.names?.content);
        }
    };

    const [getProfile, { data }] = useFindPatientProfileNames({ onCompleted: handleComplete });

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

    const handleSort = (name: string, type: Direction): any => {
        tableHeadChanges(name, type);
        switch (name.toLowerCase()) {
            case 'as of':
                setNames(
                    names?.slice().sort((a: Name, b: Name) => {
                        const dateA: any = new Date(a?.asOf);
                        const dateB: any = new Date(b?.asOf);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'prefix':
                setNames(names.slice().sort(withDirection(sortByNestedProperty('prefix'), type)));
                break;
            case 'name ( last, first middle )':
                setNames(names.slice().sort(withDirection(sortByAlpha('prefix') as any, type)));
                break;
            case 'suffix':
                setNames(names.slice().sort(withDirection(sortByNestedProperty('suffix'), type)));
                break;
            case 'degree':
                setNames(names.slice().sort(withDirection(sortByNestedProperty('degree'), type)));
                break;
            case 'type':
                setNames(names.slice().sort(withDirection(sortByNestedProperty('use'), type)));
                break;
        }
    };

    useEffect(() => {
        if (isDeleteModal) {
            deleteModalRef.current?.toggleModal();
        }
    }, [isDeleteModal]);

    return (
        <>
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
                        <AddNameModal
                            modalHead={isEditModal ? 'Edit - Name' : 'Add - Name'}
                            modalRef={addNameModalRef}
                        />
                        <DetailsNameModal data={nameDetails} modalRef={detailsNameModalRef} />
                    </div>
                }
                tableHeader={'Names'}
                tableHead={tableHead}
                tableBody={names?.map((name, index: number) => (
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
                        <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                            {name?.use ? (
                                <span>{name?.use.description}</span>
                            ) : (
                                <span className="no-data">No data</span>
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                            {name?.prefix ? (
                                <span>{name?.prefix.description}</span>
                            ) : (
                                <span className="no-data">No data</span>
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                            {name?.last || name?.first ? (
                                <span>{`${name?.last ? name?.last + ',' : ''} ${name?.first || ''} ${
                                    name?.middle || ''
                                }`}</span>
                            ) : (
                                <span className="no-data">No data</span>
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
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
                                <Button
                                    type="button"
                                    unstyled
                                    onClick={() => setIsActions(isActions === index ? null : index)}>
                                    <Icon.MoreHoriz className="font-sans-lg" />
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
                                                setIsDeleteModal(true);
                                                setNameDetails(name);
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
                sortDirectionData={handleSort}
            />
            {isDeleteModal && (
                <Modal
                    forceAction
                    ref={deleteModalRef}
                    id="example-modal-1"
                    aria-labelledby="modal-1-heading"
                    className="padding-0"
                    aria-describedby="modal-1-description">
                    <ModalHeading
                        id="modal-1-heading"
                        className="border-bottom border-base-lighter font-sans-lg padding-2">
                        Delete name
                    </ModalHeading>
                    <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                        <Icon.Warning className="font-sans-2xl margin-x-2" />
                        <p id="modal-1-description">
                            Are you sure you want to delete Name record, {nameDetails?.last}, {nameDetails?.first}?
                        </p>
                    </div>
                    <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                        <ButtonGroup>
                            <Button type="button" onClick={() => setIsDeleteModal(false)} outline>
                                Cancel
                            </Button>
                            <Button
                                type="button"
                                className="padding-105 text-center"
                                onClick={() => setIsDeleteModal(false)}>
                                Yes, delete
                            </Button>
                        </ButtonGroup>
                    </ModalFooter>
                </Modal>
            )}
        </>
    );
};
