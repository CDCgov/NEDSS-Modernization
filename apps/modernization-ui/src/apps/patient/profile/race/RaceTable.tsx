import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import { PatientRace, useDeletePatientRaceMutation, useUpdatePatientRaceMutation } from 'generated/graphql/schema';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { Direction, sortByNestedProperty, withDirection } from 'sorting';
import { externalizeDate, internalizeDate } from 'date';
import { ConfirmationModal } from 'confirmation';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { TableBody, TableComponent } from 'components/Table';
import { maybeDescription, maybeDescriptions, maybeIds } from 'apps/patient/profile/coded';
import { Detail, DetailsModal } from 'apps/patient/profile/DetailsModal';
import { EntryModal } from 'apps/patient/profile/entry';
import { PatientProfileRaceResult, useFindPatientProfileRace } from './useFindPatientProfileRace';
import { RaceEntry } from './RaceEntry';
import { RaceEntryForm } from './RaceEntryForm';
import { useAlert } from 'alert/useAlert';
import { useProfileContext } from '../ProfileContext';
import { sortingByDate } from 'sorting/sortingByDate';
import { Patient } from '../Patient';
import { PatientTableActions } from 'apps/patient/profile/PatientTableActions';
import { AddPatientRace } from './AddPatientRace';

const headers = [
    { name: 'As of', sortable: true },
    { name: 'Race', sortable: true },
    { name: 'Detailed race', sortable: true },
    { name: 'Actions', sortable: false }
];

const asDetail = (data: PatientRace): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Race', value: maybeDescription(data.category) },
    { name: 'Detailed race', value: maybeDescriptions(data.detailed).join(' | ') }
];

const asEntry = (race: PatientRace): RaceEntry => ({
    category: race.category.id,
    asOf: internalizeDate(race.asOf),
    detailed: maybeIds(race.detailed)
});

const resolveInitialEntry = (): Partial<RaceEntry> => ({
    asOf: internalizeDate(new Date()),
    detailed: []
});

type RacesTableProps = {
    patient: Patient | undefined;
};

const RacesTable = ({ patient }: RacesTableProps) => {
    const { showSuccess, showError } = useAlert();

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const { changed } = useProfileContext();

    const { selected, actions } = useTableActionState<PatientRace>();

    const modal = useRef<ModalRef>(null);

    const [isActions, setIsActions] = useState<number | null>(null);
    const [races, setRaces] = useState<PatientRace[]>([]);

    const handleComplete = (data: PatientProfileRaceResult) => {
        setTotal(data?.findPatientProfile?.races?.total ?? 0);
        setRaces(sortingByDate(data?.findPatientProfile?.races?.content || []) as Array<PatientRace>);
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfileRace({ onCompleted: handleComplete });

    const [update] = useUpdatePatientRaceMutation();
    const [remove] = useDeletePatientRaceMutation();

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

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    const handleSuccess = (message: string) => () => {
        showSuccess(message);
        refetch()
            .then(() => changed())
            .finally(actions.reset);
    };

    const handleFailure = (reason: string) => {
        showError(reason);
        actions.reset();
    };

    const onChanged = (entry: RaceEntry) => {
        if (patient && entry.category) {
            update({
                variables: {
                    input: {
                        patient: Number(patient.id),
                        asOf: externalizeDate(entry.asOf),
                        category: entry.category,
                        detailed: entry.detailed
                    }
                }
            })
                .then(() => showSuccess('Updated race'))
                .then(() => refetch())
                .then(() => changed())
                .finally(actions.reset);
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
                .then(() => showSuccess('Deleted race'))
                .then(() => refetch())
                .then(() => changed())
                .finally(actions.reset);
        }
    };

    const handleSort = (name: string, type: Direction) => {
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
                        return type === 'asc' ? detailedA.localeCompare(detailedB) : detailedB.localeCompare(detailedA);
                    })
                );
                break;
        }
    };

    /**
     * Formats the race object into TableComponent compatible TableBody object which represents a single row.
     * Each "title" in the tableDetails is a template of each column cell of the row being created.
     * @param {PatientRace} race, each item of the morbidity response data
     * @param {number} index, index of the array item
     * @return {TableBody}
     */

    const generateTableRow = (race: PatientRace, index: number): TableBody => {
        return {
            id: index,
            tableDetails: [
                {
                    id: 1,
                    title: race?.asOf ? internalizeDate(race?.asOf) : null
                },
                {
                    id: 2,
                    title: race?.category?.description || null
                },
                {
                    id: 3,
                    title: race?.detailed?.length ? maybeDescriptions(race.detailed).join(' | ') : null
                },
                {
                    id: 4,
                    title: (
                        <PatientTableActions
                            setActiveIndex={setIsActions}
                            activeIndex={isActions}
                            index={index}
                            disabled={patient?.status !== 'ACTIVE'}
                            handleAction={(type: string) => {
                                tableActionStateAdapter(actions, race)(type);
                                setIsActions(null);
                            }}
                        />
                    )
                }
            ]
        };
    };

    /**
     *
     * @return {TableBody[]} list of TableBody each created from Races
     */
    const generateTableBody = () => {
        return (races?.length > 0 && races.map(generateTableRow)) || [];
    };

    return (
        <>
            <TableComponent
                isLoading={!called || loading}
                isPagination={true}
                buttons={
                    <div className="grid-row">
                        <Button
                            disabled={patient?.status !== 'ACTIVE'}
                            type="button"
                            onClick={actions.prepareForAdd}
                            className="display-inline-flex">
                            <Icon.Add className="margin-right-05" />
                            Add race
                        </Button>
                    </div>
                }
                tableHeader={'Races'}
                tableHead={headers}
                tableBody={generateTableBody()}
                totalResults={total}
                currentPage={currentPage}
                handleNext={setCurrentPage}
                sortData={handleSort}
            />
            {patient && selected?.type === 'add' && (
                <AddPatientRace
                    modal={modal}
                    patient={Number(patient.id)}
                    entry={resolveInitialEntry()}
                    onCancel={actions.reset}
                    onSuccess={handleSuccess('Added race')}
                    onFailure={handleFailure}
                />
            )}
            {patient && selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-race-entry"
                    title="Edit - Race"
                    overflow>
                    <RaceEntryForm
                        patient={Number(patient.id)}
                        entry={asEntry(selected.item)}
                        onDelete={() => actions.selectForDelete(selected.item)}
                        onChange={onChanged}
                        editing
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

export { RacesTable };
