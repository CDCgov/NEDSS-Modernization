import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { SortableTable } from 'components/Table/SortableTable';
import { Actions as ActionState } from 'components/Table/Actions';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { PatientAdministrative, useUpdatePatientAdministrativeMutation } from 'generated/graphql/schema';
import { Direction, sortByAlpha, withDirection } from 'sorting/Sort';
import { AdministrativeEntry } from './AdminstrativeEntry';
import {
    PatientProfileAdministrativeResult,
    useFindPatientProfileAdministrative
} from './useFindPatientProfileAdministrative';
import { externalizeDateTime, internalizeDate } from 'date';
import { Detail, DetailsModal } from '../DetailsModal';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import EntryModal from 'pages/patient/profile/entry';
import { AdministrativeForm } from './AdminstrativeForm';
import { ConfirmationModal } from 'confirmation';
import { useAlert } from 'alert/useAlert';

const asEntry = (addministrative: PatientAdministrative): AdministrativeEntry => ({
    asOf: internalizeDate(addministrative?.asOf),
    comment: addministrative.comment || ''
});

const asDetail = (data: PatientAdministrative): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Additional comments', value: data.comment }
];

const initial: AdministrativeEntry = {
    asOf: null,
    comment: null
};

type Props = {
    patient: string;
};

export const AdministrativeTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true, sort: 'all' },
        { name: 'General comment', sortable: true, sort: 'all' },
        { name: 'Actions', sortable: false }
    ]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const [isActions, setIsActions] = useState<any>(null);

    const [administratives, setAdministratives] = useState<PatientAdministrative[]>([]);

    const handleComplete = (data: PatientProfileAdministrativeResult) => {
        setTotal(data?.findPatientProfile?.administrative?.total ?? 0);
        setAdministratives(data?.findPatientProfile?.administrative?.content || []);
    };

    const [fetch, { refetch, loading }] = useFindPatientProfileAdministrative({ onCompleted: handleComplete });
    const [update] = useUpdatePatientAdministrativeMutation();

    const { selected, actions } = useTableActionState<PatientAdministrative>();
    const modal = useRef<ModalRef>(null);

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    useEffect(() => {
        fetch({
            variables: {
                patient: patient,
                page: {
                    pageNumber: currentPage - 1,
                    pageSize: TOTAL_TABLE_DATA
                }
            }
        });
    }, [currentPage]);

    const onChanged = (entry: AdministrativeEntry) => {
        update({
            variables: {
                input: {
                    patient: +patient,
                    asOf: externalizeDateTime(entry.asOf),
                    comment: entry.comment
                }
            }
        })
            .then(() => {
                refetch();
                showAlert({ type: 'success', header: 'success', message: 'Updated Comment' });
            })
            .then(() => actions.reset());
    };

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
                    administratives?.slice().sort((a: PatientAdministrative, b: PatientAdministrative) => {
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
    return (
        <>
            <SortableTable
                isLoading={loading}
                isPagination={true}
                buttons={
                    administratives?.length < 1 && (
                        <div className="grid-row">
                            <Button type="button" onClick={actions.prepareForAdd} className="display-inline-flex">
                                <Icon.Add className="margin-right-05" />
                                Add comment
                            </Button>
                        </div>
                    )
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
                                    <ActionState
                                        notDeletable
                                        handleOutsideClick={() => setIsActions(null)}
                                        handleAction={(type: string) => {
                                            tableActionStateAdapter(actions, administrative)(type);
                                            setIsActions(null);
                                        }}
                                    />
                                )}
                            </div>
                        </td>
                    </tr>
                ))}
                totalResults={total}
                currentPage={currentPage}
                handleNext={setCurrentPage}
                sortDirectionData={handleSort}
            />

            {selected?.type === 'add' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="add-patient-identification-modal"
                    title="Add - Administrative">
                    <AdministrativeForm action={'Add'} entry={initial} onCancel={actions.reset} onChange={onChanged} />
                </EntryModal>
            )}

            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-identification-modal"
                    title="Edit - Administrative">
                    <AdministrativeForm
                        action={'Edit'}
                        entry={asEntry(selected.item)}
                        onCancel={actions.reset}
                        onChange={onChanged}
                    />
                </EntryModal>
            )}
            {selected?.type === 'delete' && (
                <ConfirmationModal
                    modal={modal}
                    title="Delete address"
                    message="Are you sure you want to delete this adminstrative record?"
                    confirmText="Yes, delete"
                    onConfirm={() => onChanged(initial)}
                    onCancel={actions.reset}
                />
            )}
            {selected?.type === 'detail' && (
                <DetailsModal
                    title={'View details - Administrative'}
                    modal={modal}
                    details={asDetail(selected.item)}
                    onClose={actions.reset}
                    onEdit={() => actions.selectForEdit(selected.item)}
                    onDelete={() => actions.selectForDelete(selected.item)}
                />
            )}
        </>
    );
};
