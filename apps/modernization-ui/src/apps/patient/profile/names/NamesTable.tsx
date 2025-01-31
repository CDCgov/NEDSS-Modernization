import { useEffect, useRef, useState } from 'react';
import { format } from 'date-fns';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import { Column, Name, NameEntry } from './NameEntry';
import {
    useAddPatientNameMutation,
    useDeletePatientNameMutation,
    useUpdatePatientNameMutation
} from 'generated/graphql/schema';
import { Direction } from 'sorting/Sort';
import { externalizeDate, internalizeDate } from 'date';
import { orNull } from 'utils/orNull';
import { ConfirmationModal } from 'confirmation';
import { Detail, DetailsModal } from 'apps/patient/profile/DetailsModal';
import { EntryModal } from 'apps/patient/profile/entry';
import { maybeDescription, maybeId } from 'apps/patient/profile/coded';
import { PatientNameResult, useFindPatientProfileNames } from './useFindPatientProfileNames';
import { NameEntryForm } from './NameEntryForm';
import { useTableActionState, tableActionStateAdapter } from 'table-action';
import { useAlert } from 'alert/useAlert';
import { useProfileContext } from '../ProfileContext';
import { Patient } from '../Patient';
import { sort } from './NameSorter';
import { TableBody, TableComponent } from 'components/Table';
import { transform } from './NameTransformer';
import { PatientTableActions } from '../PatientTableActions';

const asEntry = (name: Name): NameEntry => ({
    patient: name.patient,
    sequence: name.sequence,
    asOf: internalizeDate(name.asOf),
    type: maybeId(name.use),
    prefix: maybeId(name.prefix),
    first: orNull(name.first),
    middle: orNull(name.middle),
    secondMiddle: orNull(name.secondMiddle),
    last: orNull(name.last),
    secondLast: orNull(name.secondLast),
    suffix: maybeId(name.suffix),
    degree: maybeId(name.degree)
});

const asDetail = (data: Name): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Type', value: maybeDescription(data.use) },
    { name: 'Prefix', value: maybeDescription(data.prefix) },
    { name: 'First name', value: data.first },
    { name: 'Middle name', value: data.middle },
    { name: 'Second middle name', value: data.secondMiddle },
    { name: 'Last name', value: data.last },
    { name: 'Second last name', value: data.secondLast },
    { name: 'Suffix', value: maybeDescription(data.suffix) },
    { name: 'Degree', value: maybeDescription(data.degree) }
];

const resolveInitialEntry = (patient: string): NameEntry => ({
    patient: +patient,
    sequence: null,
    asOf: internalizeDate(new Date()),
    type: null,
    prefix: null,
    first: null,
    middle: null,
    secondMiddle: null,
    last: null,
    secondLast: null,
    suffix: null,
    degree: null
});

type Props = {
    patient: Patient | undefined;
};

const headers = [
    { name: Column.AsOf, sortable: true },
    { name: Column.Use, sortable: true },
    { name: Column.Prefix, sortable: true },
    { name: Column.Name, sortable: true },
    { name: Column.Suffix, sortable: true },
    { name: Column.Degree, sortable: true },
    { name: Column.Actions, sortable: false }
];

function fullName(
    first?: string | null | undefined,
    middle?: string | null | undefined,
    last?: string | null | undefined
) {
    if (first && middle && last) {
        return first + ' ' + middle + ' ' + last;
    } else if (first && !middle && last) {
        return first + ' ' + last;
    } else if (!first && !middle && last) {
        return last;
    } else if (first && !middle && !last) {
        return first;
    } else {
        return null;
    }
}

export const NamesTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();

    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [items, setItems] = useState<Name[]>([]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient?.id || '');
    const { changed } = useProfileContext();

    const [activeIndex, setActiveIndex] = useState<number | null>(null);

    const asTableBodies = (names: Name[]): TableBody[] =>
        names?.map((name, index) => ({
            id: index,
            tableDetails: [
                {
                    id: 1,
                    title: name.asOf ? format(name.asOf, 'MM/dd/yyyy') : null
                },
                { id: 2, title: name?.use?.description || null },
                { id: 3, title: name?.prefix?.description || null },
                { id: 4, title: fullName(name?.first, name?.middle, name?.last) || null },
                { id: 5, title: name?.suffix?.description || null },
                { id: 6, title: name?.degree?.description || null },
                {
                    id: 7,
                    title: (
                        <PatientTableActions
                            setActiveIndex={setActiveIndex}
                            activeIndex={activeIndex}
                            index={index}
                            disabled={patient?.status !== 'ACTIVE'}
                            handleAction={(type: string) => {
                                tableActionStateAdapter(actions, name)(type);
                                setActiveIndex(null);
                            }}
                        />
                    )
                }
            ]
        })) || [];

    const handleComplete = (data: PatientNameResult) => {
        setTotal(data?.findPatientProfile?.names?.total ?? 0);
        const content = transform(data?.findPatientProfile);
        const sorted = sort(content, {});
        setItems(sorted);
        setBodies(asTableBodies(sorted));
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfileNames({ onCompleted: handleComplete });
    const [add] = useAddPatientNameMutation();
    const [update] = useUpdatePatientNameMutation();
    const [remove] = useDeletePatientNameMutation();

    const { selected, actions } = useTableActionState<Name>();
    const modal = useRef<ModalRef>(null);

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    useEffect(() => {
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

    const onAdded = (entry: NameEntry) => {
        if (entry.type) {
            add({
                variables: {
                    input: {
                        patient: entry.patient ?? 0,
                        type: entry.type,
                        asOf: externalizeDate(entry.asOf),
                        prefix: entry.prefix,
                        first: entry.first,
                        middle: entry.middle,
                        secondMiddle: entry.secondMiddle,
                        last: entry.last,
                        secondLast: entry.secondLast,
                        suffix: entry.suffix,
                        degree: entry.degree
                    }
                }
            })
                .then(() => {
                    refetch();
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Added name`
                    });
                    changed();
                })
                .then(actions.reset);
        }
    };

    const onChanged = (entry: NameEntry) => {
        if (entry.type && entry.sequence !== null) {
            update({
                variables: {
                    input: {
                        patient: entry.patient ?? 0,
                        sequence: entry.sequence ?? 0,
                        type: entry.type,
                        asOf: externalizeDate(entry.asOf),
                        prefix: entry.prefix,
                        first: entry.first,
                        middle: entry.middle,
                        secondMiddle: entry.secondMiddle,
                        last: entry.last,
                        secondLast: entry.secondLast,
                        suffix: entry.suffix,
                        degree: entry.degree
                    }
                }
            })
                .then(() => {
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Updated name`
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
                        message: `Deleted name`
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
                            Add name
                        </Button>
                    </div>
                }
                isLoading={!called || loading}
                tableHeader={'Names'}
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
                <EntryModal onClose={actions.reset} modal={modal} id="add-patient-name-modal" title="Add - Name">
                    <NameEntryForm action={'Add'} entry={initial} onChange={onAdded} />
                </EntryModal>
            )}
            {selected?.type === 'update' && (
                <EntryModal onClose={actions.reset} modal={modal} id="edit-patient-name-modal" title="Edit - Name">
                    <NameEntryForm
                        action={'Edit'}
                        entry={asEntry(selected.item)}
                        onChange={onChanged}
                        onDelete={() => actions.selectForDelete(selected.item)}
                    />
                </EntryModal>
            )}
            {selected?.type === 'delete' && (
                <ConfirmationModal
                    modal={modal}
                    title="Delete name"
                    message="Are you sure you want to delete this name record?"
                    confirmText="Yes, delete"
                    onConfirm={onDeleted}
                    onCancel={actions.reset}
                />
            )}

            {selected?.type === 'detail' && (
                <DetailsModal
                    title={'View details - Name'}
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
