import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import {
    PatientRace,
    useAddPatientRaceMutation,
    useDeletePatientRaceMutation,
    useUpdatePatientRaceMutation
} from 'generated/graphql/schema';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { Direction, sortByNestedProperty, withDirection } from 'sorting';
import { internalizeDate } from 'date';
import { ConfirmationModal } from 'confirmation';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { SortableTable } from 'components/Table/SortableTable';
import { Actions } from 'components/Table/Actions';
import { maybeDescription, maybeDescriptions, maybeId, maybeIds } from 'pages/patient/profile/coded';
import { Detail, DetailsModal } from 'pages/patient/profile/DetailsModal';
import EntryModal from 'pages/patient/profile/entry';
import { PatientProfileRaceResult, useFindPatientProfileRace } from './useFindPatientProfileRace';
import { RaceEntry } from './RaceEntry';
import { RaceEntryForm } from './RaceEntryForm';
import { useAlert } from 'alert/useAlert';

const asDetail = (data: PatientRace): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Race', value: maybeDescription(data.category) },
    { name: 'Detailed race', value: maybeDescriptions(data.detailed).join(' | ') }
];

const asEntry = (race: PatientRace): RaceEntry => ({
    patient: race.patient,
    category: maybeId(race?.category),
    asOf: internalizeDate(race?.asOf),
    detailed: maybeIds(race?.detailed)
});

const resolveInitialEntry = (patient: string): RaceEntry => ({
    patient: +patient,
    category: null,
    asOf: null,
    detailed: []
});

type Props = {
    patient: string;
};

export const RacesTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true, sort: 'all' },
        { name: 'Race', sortable: true, sort: 'all' },
        { name: 'Detailed race', sortable: true, sort: 'all' },
        { name: 'Actions', sortable: false }
    ]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient);

    const { selected, actions } = useTableActionState<PatientRace>();

    const modal = useRef<ModalRef>(null);

    const [isActions, setIsActions] = useState<number | null>(null);
    const [races, setRaces] = useState<PatientRace[]>([]);

    const handleComplete = (data: PatientProfileRaceResult) => {
        setTotal(data?.findPatientProfile?.races?.total ?? 0);
        setRaces(data?.findPatientProfile?.races?.content ?? []);
    };

    const [fetch, { refetch }] = useFindPatientProfileRace({ onCompleted: handleComplete });

    const [add] = useAddPatientRaceMutation();
    const [update] = useUpdatePatientRaceMutation();
    const [remove] = useDeletePatientRaceMutation();

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

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    const onAdded = (entry: RaceEntry) => {
        if (entry.category) {
            add({
                variables: {
                    input: {
                        patient: entry.patient,
                        asOf: entry.asOf,
                        category: entry.category,
                        detailed: entry.detailed
                    }
                }
            })
                .then(() => {
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Added race`
                    });
                    refetch();
                })
                .then(actions.reset);
        }
    };

    const onChanged = (entry: RaceEntry) => {
        if (entry.category) {
            update({
                variables: {
                    input: {
                        patient: entry.patient,
                        asOf: entry.asOf,
                        category: entry.category,
                        detailed: entry.detailed
                    }
                }
            })
                .then(() => {
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Updated race`
                    });
                    refetch();
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
                        category: selected.item.category.id
                    }
                }
            })
                .then(() => {
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Deleted race`
                    });
                    refetch();
                })
                .then(actions.reset);
        }
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
                setRaces(
                    races?.slice().sort((a: PatientRace, b: PatientRace) => {
                        const dateA: any = new Date(a?.asOf);
                        const dateB: any = new Date(b?.asOf);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'race':
                setRaces(races.slice().sort(withDirection(sortByNestedProperty('category'), type)));
                break;
            case 'detailed race':
                setRaces(
                    races?.slice().sort((a: PatientRace, b: PatientRace) => {
                        const detailedA: any = a?.detailed?.[0]?.description;
                        const detailedB: any = b?.detailed?.[0]?.description;
                        return type === 'asc' ? detailedB - detailedA : detailedA - detailedB;
                    })
                );
                break;
        }
    };

    return (
        <>
            <SortableTable
                isPagination={true}
                buttons={
                    <div className="grid-row">
                        <Button type="button" onClick={actions.prepareForAdd} className="display-inline-flex">
                            <Icon.Add className="margin-right-05" />
                            Add race
                        </Button>
                    </div>
                }
                tableHeader={'Races'}
                tableHead={tableHead}
                tableBody={races?.map((race, index: number) => (
                    <tr key={index}>
                        <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                            {race?.asOf ? (
                                <span>
                                    {format(new Date(race?.asOf), 'MM/dd/yyyy')} <br />{' '}
                                </span>
                            ) : (
                                <span className="no-data">No data</span>
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                            {race?.category?.description ? (
                                <span>{race?.category?.description}</span>
                            ) : (
                                <span className="no-data">No data</span>
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                            {maybeDescriptions(race.detailed).join(' | ') || <span className="no-data">No data</span>}
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
                                            tableActionStateAdapter(actions, race)(type);
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
                    id="add-patient-name-modal"
                    title="Add - Race"
                    overflow>
                    <RaceEntryForm action={'Add'} entry={initial} onCancel={actions.reset} onChange={onAdded} />
                </EntryModal>
            )}
            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-name-modal"
                    title="Edit - Race"
                    overflow>
                    <RaceEntryForm
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
                    title="Delete race"
                    message="Are you sure you want to delete this name race?"
                    confirmText="Yes, delete"
                    onConfirm={onDeleted}
                    onCancel={actions.reset}
                />
            )}
            {selected?.type === 'detail' && (
                <DetailsModal
                    title={'View details - Race'}
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
