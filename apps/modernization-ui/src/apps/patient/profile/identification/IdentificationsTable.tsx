import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import { format } from 'date-fns';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { Column, Identification, IdentificationEntry } from './identification';
import {
    useAddPatientIdentificationMutation,
    useDeletePatientIdentificationMutation,
    useUpdatePatientIdentificationMutation
} from 'generated/graphql/schema';
import { Direction } from 'sorting/Sort';
import {
    PatientIdentificationResult,
    useFindPatientProfileIdentifications
} from './useFindPatientProfileIdentifications';
import { maybeDescription, maybeId } from '../coded';
import { internalizeDate } from 'date';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { EntryModal } from '../entry/EntryModal';
import { IdentificationEntryForm } from './IdentificationEntryForm';
import { ConfirmationModal } from 'confirmation';
import { Detail, DetailsModal } from '../DetailsModal';
import { useAlert } from 'alert/useAlert';
import { useProfileContext } from '../ProfileContext';
import { orNull } from 'utils';
import { Patient } from '../Patient';
import { sort } from './identificationSorter';
import { TableBody, TableComponent } from 'components/Table';
import { transform } from './identificationTransformer';
import { PatientTableActions } from '../PatientTableActions';

const asEntry = (identification: Identification): IdentificationEntry => ({
    patient: identification.patient,
    asOf: internalizeDate(identification.asOf),
    type: maybeId(identification.type),
    value: orNull(identification.value),
    state: maybeId(identification.authority),
    sequence: identification.sequence
});

const asDetail = (data: Identification): Detail[] => [
    { name: Column.AsOf, value: internalizeDate(data.asOf) },
    { name: Column.Type, value: maybeDescription(data.type) },
    { name: Column.Authority, value: maybeDescription(data.authority) },
    { name: Column.Value, value: data.value }
];

const resolveInitialEntry = (patient: string): IdentificationEntry => ({
    patient: +patient,
    asOf: internalizeDate(new Date()),
    type: null,
    value: null,
    state: null
});

type Props = {
    patient: Patient | undefined;
};

const headers = [
    { name: Column.AsOf, sortable: true },
    { name: Column.Type, sortable: true },
    { name: Column.Authority, sortable: true },
    { name: Column.Value, sortable: true },
    { name: Column.Actions, sortable: false }
];

export const IdentificationsTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [items, setItems] = useState<Identification[]>([]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient?.id || '');
    const { changed } = useProfileContext();

    const [activeIndex, setActiveIndex] = useState<number | null>(null);

    const asTableBodies = (idenitifications: Identification[]): TableBody[] =>
        idenitifications?.map((identification, index) => ({
            id: identification.value,
            tableDetails: [
                {
                    id: 1,
                    title: identification.asOf ? format(identification.asOf, 'MM/dd/yyyy') : null
                },
                { id: 2, title: identification?.type?.description || null },
                { id: 3, title: identification?.authority?.description || null },
                { id: 4, title: identification?.value || null },
                {
                    id: 5,
                    title: (
                        <PatientTableActions
                            setActiveIndex={setActiveIndex}
                            activeIndex={activeIndex}
                            index={index}
                            disabled={patient?.status !== 'ACTIVE'}
                            handleAction={(type: string) => {
                                tableActionStateAdapter(actions, identification)(type);
                                setActiveIndex(null);
                            }}
                        />
                    )
                }
            ]
        })) || [];

    const handleComplete = (data: PatientIdentificationResult) => {
        setTotal(data?.findPatientProfile.identification?.total ?? 0);
        const content = transform(data?.findPatientProfile);
        const sorted = sort(content, {});
        setItems(sorted);
        setBodies(asTableBodies(sorted));
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfileIdentifications({ onCompleted: handleComplete });
    const [add] = useAddPatientIdentificationMutation();
    const [update] = useUpdatePatientIdentificationMutation();
    const [remove] = useDeletePatientIdentificationMutation();

    const { selected, actions } = useTableActionState<Identification>();
    const modal = useRef<ModalRef>(null);

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    useEffect(() => {
        patient &&
            fetch({
                variables: {
                    patient: patient?.id,
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize: TOTAL_TABLE_DATA
                    }
                },
                notifyOnNetworkStatusChange: true
            }).then(() => refetch());
    }, [currentPage, patient]);

    const onAdded = (entry: IdentificationEntry) => {
        add({
            variables: {
                input: {
                    patient: entry.patient,
                    asOf: entry.asOf,
                    value: entry.value || '',
                    type: entry.type || '',
                    authority: entry.state
                }
            }
        })
            .then(() => {
                showAlert({
                    type: 'success',
                    title: 'success',
                    message: `Added Identification`
                });
                refetch();
                changed();
            })
            .then(actions.reset);
    };

    const onChanged = (entry: IdentificationEntry) => {
        if (entry.sequence) {
            update({
                variables: {
                    input: {
                        patient: entry.patient,
                        sequence: entry.sequence,
                        asOf: entry.asOf,
                        value: entry.value || '',
                        type: entry.type || '',
                        authority: entry.state
                    }
                }
            })
                .then(() => {
                    showAlert({
                        type: 'success',
                        title: 'success',
                        message: `Updated Identification`
                    });
                    refetch();
                    changed();
                })
                .then(actions.reset);
        }
    };

    const onDeleted = () => {
        if (selected?.type == 'delete') {
            remove({
                variables: {
                    input: {
                        patient: selected.item.patient,
                        sequence: selected.item.sequence
                    }
                }
            })
                .then(() => {
                    showAlert({
                        type: 'success',
                        title: 'success',
                        message: `Deleted Identification`
                    });
                    refetch();
                    changed();
                })
                .then(actions.reset);
        }
    };

    const handleSort = (name: string, direction: string): void => {
        const criteria = { name: name as Column, type: direction as Direction };
        const sorted = sort(items, criteria);
        setItems(sorted);
        setBodies(asTableBodies(sorted));
    };

    useEffect(() => {
        setBodies(asTableBodies(items));
    }, [activeIndex]);

    return (
        <>
            <TableComponent
                buttons={
                    <div className="grid-row">
                        <Button
                            disabled={patient?.status !== 'ACTIVE'}
                            type="button"
                            onClick={actions.prepareForAdd}
                            className="display-inline-flex">
                            <Icon.Add className="margin-right-05" />
                            Add identification
                        </Button>
                    </div>
                }
                isLoading={!called || loading}
                tableHeader={'Identifications'}
                tableHead={headers}
                tableBody={bodies}
                isPagination={true}
                pageSize={TOTAL_TABLE_DATA}
                totalResults={total}
                currentPage={currentPage}
                handleNext={setCurrentPage}
                sortData={handleSort}
            />

            {selected?.type === 'add' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="add-patient-identification-modal"
                    title="Add - Identification">
                    <IdentificationEntryForm entry={initial} onChange={onAdded} />
                </EntryModal>
            )}

            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-identification-modal"
                    title="Edit - Identification">
                    <IdentificationEntryForm
                        entry={asEntry(selected.item)}
                        onDelete={() => actions.selectForDelete(selected.item)}
                        onChange={onChanged}
                    />
                </EntryModal>
            )}

            {selected?.type === 'delete' && (
                <ConfirmationModal
                    modal={modal}
                    title="Delete Identification"
                    message="Are you sure you want to delete this identification record?"
                    confirmText="Yes, delete"
                    onConfirm={onDeleted}
                    onCancel={actions.reset}
                />
            )}

            {selected?.type === 'detail' && (
                <DetailsModal
                    title={'View details - Identification'}
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
