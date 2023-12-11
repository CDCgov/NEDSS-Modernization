import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { TableBody, TableComponent } from 'components/Table';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { useUpdatePatientAdministrativeMutation } from 'generated/graphql/schema';
import { Direction } from 'sorting/Sort';
import { Column, AdministrativeEntry, Administrative } from './administrative';
import {
    PatientProfileAdministrativeResult,
    useFindPatientProfileAdministrative
} from './useFindPatientProfileAdministrative';
import { externalizeDateTime, internalizeDate } from 'date';
import { Detail, DetailsModal } from '../DetailsModal';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import EntryModal from 'pages/patient/profile/entry';
import { AdministrativeForm } from './AdministrativeForm';
import { ConfirmationModal } from 'confirmation';
import { useAlert } from 'alert/useAlert';
import { useProfileContext } from '../ProfileContext';
import { Patient } from '../Patient';
import { sort } from './administrativeSorter';
import { transform } from './administrativeTransformer';
import { PatientTableActions } from '../PatientTableActions';

const asEntry = (administrative: Administrative): AdministrativeEntry => ({
    asOf: internalizeDate(administrative?.asOf),
    patient: administrative.patient,
    comment: administrative.comment || ''
});

const asDetail = (data: Administrative): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Additional comments', value: data.comment }
];

const resolveInitialEntry = (patient: string): AdministrativeEntry => ({
    patient: +patient,
    asOf: internalizeDate(new Date()),
    comment: null
});

type Props = {
    patient: Patient | undefined;
};

const headers = [
    { name: Column.AsOf, sortable: true },
    { name: Column.Comment, sortable: true },
    { name: Column.Actions, sortable: false }
];

export const AdministrativeTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();

    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [items, setItems] = useState<Administrative[]>([]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient?.id || '');
    const { changed } = useProfileContext();

    const [activeIndex, setActiveIndex] = useState<number | null>(null);

    const asTableBodies = (administratives: Administrative[]): TableBody[] =>
        administratives?.map((administrative, index) => ({
            id: administrative.comment,
            tableDetails: [
                {
                    id: 1,
                    title: administrative.asOf ? format(administrative.asOf, 'MM/dd/yyyy') : null
                },
                { id: 2, title: administrative?.comment || null },
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
                        />
                    )
                }
            ]
        })) || [];

    const handleComplete = (data: PatientProfileAdministrativeResult) => {
        setTotal(data?.findPatientProfile?.administrative?.total ?? 0);
        const content = transform(data?.findPatientProfile);
        setItems(content);
        const sorted = sort(content, {});
        setBodies(asTableBodies(sorted));
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfileAdministrative({ onCompleted: handleComplete });
    const [update] = useUpdatePatientAdministrativeMutation();

    const { selected, actions } = useTableActionState<Administrative>();
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
                }
            });
    }, [currentPage, patient]);


    const onChanged = (entry: AdministrativeEntry) => {
            update({
                variables: {
                    input: {
                        patient: entry.patient,
                        asOf: externalizeDateTime(entry.asOf),
                        comment: entry.comment
                    }
                }
            })
                .then(() => {
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Updated Comment`
                    });
                    refetch();
                    changed();
                })
                .then(actions.reset);
        
    };

    const handleSort = (name: string, direction: string): void => {
        const criteria = { name: name as Column, type: direction as Direction };
        const sorted = sort(items, criteria);
        setBodies(asTableBodies(sorted));
    };

    useEffect(() => {
        const sorted = sort(items, {});
        setBodies(asTableBodies(sorted));
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
                                Add comment
                            </Button>
                        </div>

                }
                isLoading={!called || loading}
                isPagination={true}
                tableHeader={'Administrative'}
                tableHead={headers}
                tableBody={bodies}
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
                    id="add-patient-administrative-modal"
                    title="Add - Administrative">
                    <AdministrativeForm action={'Add'} entry={initial} onChange={onChanged} />
                </EntryModal>
            )}

            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-administrative-modal"
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
                    title="Delete address"
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
                    onDelete={() => actions.selectForDelete(selected.item)}
                />
            )}
        </>
    );
};
