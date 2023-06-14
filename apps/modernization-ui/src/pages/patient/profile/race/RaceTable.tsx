import { RefObject, useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { SortableTable } from 'components/Table/SortableTable';
import { Actions } from 'components/Table/Actions';
import { TOTAL_TABLE_DATA } from 'utils/util';
import {
    FindPatientProfileQuery,
    useAddPatientRaceMutation,
    useDeletePatientRaceMutation,
    useUpdatePatientRaceMutation
} from 'generated/graphql/schema';
import { Direction, sortByNestedProperty, withDirection } from 'sorting/Sort';
import { externalizeDateTime, internalizeDate } from 'date';
import { ConfirmationModal } from 'confirmation';
import { maybeId, maybeIds } from 'pages/patient/profile/coded';
import { PatientRace } from './Race';
import { useFindPatientProfileRace } from './useFindPatientProfileRace';
import { RaceEntryModal } from './RaceEntryModal';
import { RaceDetailModal } from './RaceDetailModal';
import { RaceEntry } from './RaceEntry';

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

type EntryState = {
    action: 'add' | 'update' | 'delete';
    entry: RaceEntry;
};

type Props = {
    patient: string;
};

export const RacesTable = ({ patient }: Props) => {
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true, sort: 'all' },
        { name: 'Race', sortable: true, sort: 'all' },
        { name: 'Detailed race', sortable: true, sort: 'all' },
        { name: 'Actions', sortable: false }
    ]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient);

    const [active, setActive] = useState<EntryState | undefined>(undefined);

    const addModal = useRef<ModalRef>(null);
    const editModal = useRef<ModalRef>(null);

    const detailModal = useRef<ModalRef>(null);
    const deleteModal = useRef<ModalRef>(null);

    const [details, setDetails] = useState<PatientRace | undefined>(undefined);
    const [isActions, setIsActions] = useState<any>(null);
    const [races, setRaces] = useState<PatientRace[]>([]);

    const handleComplete = (data: FindPatientProfileQuery) => {
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
                patient: patient.toString(),
                page5: {
                    pageNumber: currentPage - 1,
                    pageSize: TOTAL_TABLE_DATA
                }
            }
        });
    }, [currentPage]);

    useEffect(() => {
        if (active) {
            if (active.action == 'add') {
                addModal.current?.toggleModal();
            } else if (active.action == 'update') {
                editModal.current?.toggleModal();
            } else if (active.action == 'delete') {
                deleteModal.current?.toggleModal();
            }
        }
    }, [active]);

    useEffect(() => {
        if (details) {
            detailModal.current?.toggleModal();
        }
    }, [details]);

    const handleDetails = (race: PatientRace) => {
        setDetails(race);
    };

    const handleAdd = () => {
        setActive({ action: 'add', entry: initial });
    };

    const onAdded = (entry: RaceEntry) => {
        if (entry.category) {
            add({
                variables: {
                    input: {
                        patient: entry.patient,
                        asOf: externalizeDateTime(entry.asOf),
                        category: entry.category,
                        detailed: entry.detailed
                    }
                }
            }).then(() => refetch());
        }
        addModal.current?.toggleModal();
    };

    const handleEdit = (race: PatientRace) => {
        setActive({ action: 'update', entry: asEntry(race) });
    };

    const onChanged = (entry: RaceEntry) => {
        if (entry.category) {
            update({
                variables: {
                    input: {
                        patient: entry.patient,
                        asOf: externalizeDateTime(entry.asOf),
                        category: entry.category,
                        detailed: entry.detailed
                    }
                }
            }).then(() => refetch());
        }
        editModal.current?.toggleModal();
    };

    const handleDelete = (race: PatientRace) => {
        setActive({ action: 'delete', entry: asEntry(race) });
    };

    const onDeleted = () => {
        if (active?.entry && active.entry.category) {
            remove({
                variables: {
                    input: {
                        patient: active.entry.patient,
                        category: active.entry.category
                    }
                }
            }).then(() => refetch());
        }
    };

    const toggle = (modal: RefObject<ModalRef>) => () => {
        setActive(undefined);
        modal.current?.toggleModal();
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
                        <Button type="button" onClick={handleAdd} className="display-inline-flex">
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
                            {race?.detailed?.map((detail) => (
                                <span key={detail?.id}>
                                    {detail?.description} <br />
                                </span>
                            )) || <span className="no-data">No data</span>}
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
                                                handleEdit(race);
                                            }
                                            if (type === 'delete') {
                                                handleDelete(race);
                                            }
                                            if (type === 'details') {
                                                handleDetails(race);
                                            }
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

            <RaceEntryModal
                action={'Add'}
                modal={addModal}
                entry={() => initial}
                onCancel={toggle(addModal)}
                onChange={onAdded}
            />

            <RaceEntryModal
                action={'Edit'}
                modal={editModal}
                entry={() => active?.entry}
                onCancel={toggle(editModal)}
                onChange={onChanged}
            />

            <RaceDetailModal data={details} modal={detailModal} />

            <ConfirmationModal
                modal={deleteModal}
                title="Delete race"
                message="Are you sure you want to delete this race record?"
                confirmText="Yes, delete"
                onConfirm={onDeleted}
                onCancel={toggle(deleteModal)}
            />
        </>
    );
};
