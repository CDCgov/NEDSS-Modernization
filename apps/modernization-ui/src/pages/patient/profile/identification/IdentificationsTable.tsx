import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { Actions as ActionState } from 'components/Table/Actions';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { Headers, IdentificationEntry } from './identification';
import {
    PatientIdentification,
    useAddPatientIdentificationMutation,
    useDeletePatientIdentificationMutation,
    useUpdatePatientIdentificationMutation
} from 'generated/graphql/schema';
import {
    Comparator,
    Direction,
    descending,
    sortByAlpha,
    sortByDate,
    sortByNestedProperty,
    withDirection
} from 'sorting/Sort';
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
import { TableBody, TableComponent } from 'components/Table';
import { transform } from './identificationTransformer';

const asEntry = (identification: PatientIdentification): IdentificationEntry => ({
    patient: identification.patient,
    asOf: internalizeDate(identification.asOf),
    type: maybeId(identification.type),
    value: orNull(identification.value),
    state: maybeId(identification.authority),
    sequence: identification.sequence
});

const asDetail = (data: PatientIdentification): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Type', value: maybeDescription(data.type) },
    { name: 'Authority', value: maybeDescription(data.authority) },
    { name: 'Value', value: data.value }
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
    { name: Headers.AsOf, sortable: true },
    { name: Headers.Type, sortable: true },
    { name: Headers.Authority, sortable: true },
    { name: Headers.Value, sortable: true },
    { name: Headers.Actions, sortable: false }
];

export type SortCriteria = {
    name?: Headers;
    type?: Direction;
};

export const sort = (investigations: PatientIdentification[], { name, type }: SortCriteria): PatientIdentification[] =>
    investigations.slice().sort(withDirection(resolveComparator(name), type));

const resolveComparator = (name: Headers): Comparator<PatientIdentification> => {
    switch (name) {
        case Headers.AsOf:
            return sortByDate('asOf');
        case Headers.Type:
            return sortByNestedProperty('type');
        case Headers.Authority:
            return sortByNestedProperty('authority');
        case Headers.Value:
            return sortByAlpha('value');
        default:
            return defaultSort;
    }
};

const defaultSort: Comparator<PatientIdentification> = descending(sortByDate('asOf'));

export const IdentificationsTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [items, setItems] = useState<PatientIdentification[]>([]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient?.id || '');
    const { changed } = useProfileContext();

    const [isActions, setIsActions] = useState<any>(null);

    const asTableBody =
        (index?: number) =>
        (identification: PatientIdentification): TableBody => ({
            id: identification.__typename,
            tableDetails: [
                {
                    id: 1,
                    title: identification?.asOf && format(identification.asOf, 'MM/dd/yyyy')
                },
                { id: 2, title: identification?.type.description || null },
                { id: 3, title: identification?.authority?.description || null },
                { id: 4, title: identification?.value || null },
                {
                    id: 5,
                    title: 'actions',
                    actions: isActions === index && (
                        <ActionState
                            handleOutsideClick={() => setIsActions(null)}
                            handleAction={(type: string) => {
                                tableActionStateAdapter(actions, identification)(type);
                                setIsActions(null);
                            }}
                        />
                    )
                }
            ]
        });

    const asTableBodies = (idenitifications: PatientIdentification[]): TableBody[] =>
        idenitifications?.map((item, index) => asTableBody(index)(item)) || [];

    const handleComplete = (data: PatientIdentificationResult) => {
        setTotal(data?.findPatientProfile.identification?.total ?? 0);
        const content = transform(data?.findPatientProfile);
        setItems(content);
        const sorted = sort(content, {});
        setBodies(asTableBodies(sorted));
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfileIdentifications({ onCompleted: handleComplete });
    const [add] = useAddPatientIdentificationMutation();
    const [update] = useUpdatePatientIdentificationMutation();
    const [remove] = useDeletePatientIdentificationMutation();

    const { selected, actions } = useTableActionState<PatientIdentification>();
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
            });
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
                    header: 'success',
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
                        header: 'success',
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
                        header: 'success',
                        message: `Deleted Identification`
                    });
                    refetch();
                    changed();
                })
                .then(actions.reset);
        }
    };

    const handleSort = (name: string, direction: string): void => {
        const criteria = { name: name as Headers, type: direction as Direction };
        const sorted = sort(items, criteria);
        setBodies(asTableBodies(sorted));
    };

    useEffect(() => {
        const sorted = sort(items, {});
        setBodies(asTableBodies(sorted));
    }, [isActions]);

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
                pageSize={10}
                totalResults={total}
                currentPage={currentPage}
                handleNext={setCurrentPage}
                sortData={handleSort}
                setIndex={(index) => {
                    setIsActions(isActions === index ? null : index);
                }}
                patient={patient}
            />

            {selected?.type === 'add' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="add-patient-identification-modal"
                    title="Add - Identification">
                    <IdentificationEntryForm action={'Add'} entry={initial} onChange={onAdded} />
                </EntryModal>
            )}

            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-identification-modal"
                    title="Edit - Identification">
                    <IdentificationEntryForm
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
