import { useEffect, useRef, useState } from 'react';
import {
    Button,
    ButtonGroup,
    Icon,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton
} from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { SortableTable } from 'components/Table/SortableTable';
import { Actions } from 'components/Table/Actions';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { FindPatientProfileQuery } from 'generated/graphql/schema';
import { Direction, sortByAlpha, withDirection } from 'sorting/Sort';
import { Administrative } from './administrative';
import { useFindPatientProfileAdministrative } from './useFindPatientProfileAdministrative';
import { AddCommentModal } from 'pages/patient/profile/administrative/AddCommentModal';

type PatientLabReportTableProps = {
    patient: string | undefined;
};

export const AdministrativeTable = ({ patient }: PatientLabReportTableProps) => {
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true, sort: 'all' },
        { name: 'General comment', sortable: true, sort: 'all' },
        { name: 'Actions', sortable: false }
    ]);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const addModalRef = useRef<ModalRef>(null);
    const detailsModalRef = useRef<ModalRef>(null);
    const deleteModalRef = useRef<ModalRef>(null);

    const [isEditModal, setIsEditModal] = useState<boolean>(false);
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const [details, setDetails] = useState<any>(undefined);
    const [isActions, setIsActions] = useState<any>(null);
    const [administratives, setAdministratives] = useState<Administrative[]>([]);
    const [isDeleteModal, setIsDeleteModal] = useState<boolean>(false);

    const handleComplete = (data: FindPatientProfileQuery) => {
        if (
            data?.findPatientProfile?.administrative?.content &&
            data?.findPatientProfile?.administrative?.content?.length > 0
        ) {
            setAdministratives(data?.findPatientProfile?.administrative?.content);
        }
    };

    const [getProfile, { data }] = useFindPatientProfileAdministrative({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getProfile({
                variables: {
                    patient: patient,
                    page1: {
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
                setAdministratives(
                    administratives?.slice().sort((a: Administrative, b: Administrative) => {
                        const dateA: any = new Date(a?.asOf);
                        const dateB: any = new Date(b?.asOf);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'general comment':
                setAdministratives(administratives.slice().sort(withDirection(sortByAlpha('comment') as any, type)));
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
                                addModalRef.current?.toggleModal();
                                setDetails(null);
                                setIsEditModal(false);
                            }}
                            className="display-inline-flex">
                            <Icon.Add className="margin-right-05" />
                            Add comment
                        </Button>
                        <AddCommentModal
                            modalHead={isEditModal ? 'Edit - Comment' : 'Add - Comment'}
                            modalRef={addModalRef}
                        />
                        {/* <DetailsRaceModal data={details} modalRef={detailsModalRef} /> */}
                    </div>
                }
                tableHeader={'Administrative'}
                tableHead={tableHead}
                tableBody={administratives?.map((administrative, index: number) => (
                    <tr key={index}>
                        <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                            {administrative?.asOf ? (
                                <span>
                                    {format(new Date(administrative?.asOf), 'MM/dd/yyyy')} <br />{' '}
                                </span>
                            ) : (
                                <span className="no-data">No data</span>
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                            {administrative?.comment ? (
                                <span>{administrative?.comment}</span>
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
                                                addModalRef.current?.toggleModal();
                                            }
                                            if (type === 'delete') {
                                                setIsDeleteModal(true);
                                                setDetails(administrative);
                                                deleteModalRef.current?.toggleModal();
                                            }
                                            if (type === 'details') {
                                                setDetails(administrative);
                                                detailsModalRef.current?.toggleModal();
                                            }
                                            setIsActions(null);
                                        }}
                                    />
                                )}
                            </div>
                        </td>
                    </tr>
                ))}
                totalResults={data?.findPatientProfile?.administrative?.total}
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
                        <p id="modal-1-description">Are you sure you want to delete this document?</p>
                    </div>
                    <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                        <ButtonGroup>
                            <Button type="button" onClick={() => setIsDeleteModal(false)} outline>
                                Cancel
                            </Button>
                            <ModalToggleButton modalRef={deleteModalRef} closer className="padding-105 text-center">
                                Yes, delete
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </Modal>
            )}
        </>
    );
};
