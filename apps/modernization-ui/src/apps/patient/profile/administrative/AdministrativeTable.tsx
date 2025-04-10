import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { PatientAdministrative, useUpdatePatientAdministrativeMutation } from 'generated/graphql/schema';
import { AdministrativeEntry, Column } from './AdministrativeEntry';
import {
    PatientProfileAdministrativeResult,
    useFindPatientProfileAdministrative
} from './useFindPatientProfileAdministrative';
import { externalizeDate, internalizeDate } from 'date';
import { Detail, DetailsModal } from '../DetailsModal';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { EntryModal } from 'apps/patient/profile/entry';
import { AdministrativeForm } from './AdministrativeForm';
import { ConfirmationModal } from 'confirmation';
import { useAlert } from 'alert/useAlert';
import { sortingByDate } from 'sorting/sortingByDate';
import { Patient } from '../Patient';
import { TableBody, TableComponent } from 'components/Table';
import { PatientTableActions } from '../PatientTableActions';

const asEntry = (administrative: PatientAdministrative): AdministrativeEntry => ({
    asOf: internalizeDate(administrative?.asOf),
    comment: administrative.comment || ''
});

const asDetail = (data: PatientAdministrative): Detail[] => [
    { name: Column.AsOf, value: internalizeDate(data.asOf) },
    { name: Column.Comment, value: data.comment }
];

const initial: AdministrativeEntry = {
    asOf: internalizeDate(new Date()),
    comment: null
};

type Props = {
    patient: Patient | undefined;
};

const headers = [
    { name: Column.AsOf, sortable: false },
    { name: Column.Comment, sortable: false },
    { name: Column.Actions, sortable: false }
];

export const AdministrativeTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [activeIndex, setActiveIndex] = useState<number | null>(null);

    const [administratives, setAdministratives] = useState<PatientAdministrative[]>([]);

    const handleComplete = (data: PatientProfileAdministrativeResult) => {
        setTotal(data?.findPatientProfile?.administrative?.total ?? 0);
        setAdministratives(
            sortingByDate(data?.findPatientProfile?.administrative?.content || []) as Array<PatientAdministrative>
        );
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfileAdministrative({ onCompleted: handleComplete });
    const [update] = useUpdatePatientAdministrativeMutation();

    const { selected, actions } = useTableActionState<PatientAdministrative>();
    const modal = useRef<ModalRef>(null);

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    useEffect(() => {
        patient &&
            fetch({
                variables: {
                    patient: patient.id,
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize: TOTAL_TABLE_DATA
                    }
                },
                notifyOnNetworkStatusChange: true
            });
    }, [currentPage, patient]);

    const onChanged = (entry: AdministrativeEntry) => {
        patient &&
            update({
                variables: {
                    input: {
                        patient: +patient.id,
                        asOf: externalizeDate(entry.asOf),
                        comment: entry.comment
                    }
                }
            })
                .then(() => {
                    refetch();
                    showAlert({ type: 'success', title: 'success', message: 'Updated Comment' });
                })
                .then(() => actions.reset());
    };

    const generateTableRow = (administrative: PatientAdministrative, index: number): TableBody => {
        return {
            id: index,
            tableDetails: [
                {
                    id: 1,
                    title: administrative?.asOf ? internalizeDate(administrative?.asOf) : null
                },
                {
                    id: 2,
                    title: administrative?.comment
                },
                {
                    id: 3,
                    title: (
                        <PatientTableActions
                            setActiveIndex={setActiveIndex}
                            activeIndex={activeIndex}
                            index={index}
                            disabled={patient?.status !== 'ACTIVE'}
                            handleAction={(type: string) => {
                                tableActionStateAdapter(actions, administrative)(type);
                                setActiveIndex(null);
                            }}
                            notDeletable
                        />
                    )
                }
            ]
        };
    };

    const generateTableBody = () => {
        return (administratives?.length > 0 && administratives.map(generateTableRow)) || [];
    };

    return (
        <>
            <TableComponent
                isLoading={!called || loading}
                isPagination={true}
                buttons={
                    administratives?.length < 1 && (
                        <div className="grid-row">
                            <Button
                                disabled={patient?.status !== 'ACTIVE'}
                                type="button"
                                onClick={actions.prepareForAdd}
                                className="display-inline-flex">
                                <Icon.Add className="margin-right-05" />
                                Add comment
                            </Button>
                        </div>
                    )
                }
                tableHeader={'Administrative'}
                tableHead={headers}
                tableBody={generateTableBody()}
                totalResults={total}
                currentPage={currentPage}
                handleNext={setCurrentPage}
            />

            {selected?.type === 'add' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="add-patient-admin-modal"
                    title="Add - Administrative">
                    <AdministrativeForm action={'Add'} entry={initial} onChange={onChanged} />
                </EntryModal>
            )}

            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-admin-modal"
                    title="Edit - Administrative">
                    <AdministrativeForm
                        action={'Edit'}
                        entry={asEntry(selected.item)}
                        onDelete={() => actions.selectForDelete(selected.item)}
                        onChange={onChanged}
                    />
                </EntryModal>
            )}
            {selected?.type === 'delete' && (
                <ConfirmationModal
                    modal={modal}
                    title="Delete - Administrative"
                    message="Are you sure you want to delete this administrative record?"
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
                />
            )}
        </>
    );
};
