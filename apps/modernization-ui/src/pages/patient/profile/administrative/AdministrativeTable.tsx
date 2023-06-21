import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { SortableTable } from 'components/Table/SortableTable';
import { Actions as ActionState } from 'components/Table/Actions';
import { TOTAL_TABLE_DATA } from 'utils/util';
import {
    FindPatientProfileQuery,
    PatientAdministrative,
    useUpdateAdministrativeMutation
} from 'generated/graphql/schema';
import { Direction, sortByAlpha, withDirection } from 'sorting/Sort';
import { Administrative, AdministrativeEntry } from './administrative';
import { useFindPatientProfileAdministrative } from './useFindPatientProfileAdministrative';
import { internalizeDate } from 'date';
import { Detail, DetailsModal } from '../DetailsModal';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { EntryModal } from '../EntryModal';
import { AdministrativeForm } from './AdminstrativeForm';

const asEntry = (addministrative: PatientAdministrative): AdministrativeEntry => ({
    patient: +addministrative.patient,
    asOf: internalizeDate(addministrative?.asOf),
    comment: addministrative.comment || ''
});

const asDetail = (data: PatientAdministrative): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Additional comments', value: data.comment }
];

const resolveInitialEntry = (patient: string): AdministrativeEntry => ({
    patient: +patient,
    asOf: null,
    comment: null
});

type Props = {
    patient: string;
};

export const AdministrativeTable = ({ patient }: Props) => {
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true, sort: 'all' },
        { name: 'General comment', sortable: true, sort: 'all' },
        { name: 'Actions', sortable: false }
    ]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient);

    const [isActions, setIsActions] = useState<any>(null);

    const [administratives, setAdministratives] = useState<PatientAdministrative[]>([]);

    const handleComplete = (data: FindPatientProfileQuery) => {
        setTotal(data?.findPatientProfile?.administrative?.total ?? 0);
        setAdministratives(data?.findPatientProfile?.administrative?.content || []);
    };

    const [getProfile, { refetch }] = useFindPatientProfileAdministrative({ onCompleted: handleComplete });
    const [update] = useUpdateAdministrativeMutation();

    const { selected, actions } = useTableActionState<PatientAdministrative>();
    const modal = useRef<ModalRef>(null);

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

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
    }, [currentPage]);

    const onAdded = (entry: AdministrativeEntry) => {
        if (entry.comment) {
            update({
                variables: {
                    input: {
                        patientId: entry.patient.toString(),
                        description: entry.comment
                    }
                }
            })
                .then(() => refetch())
                .then(actions.reset);
        }
    };

    const onChanged = (entry: AdministrativeEntry) => {
        if (entry.comment) {
            update({
                variables: {
                    input: {
                        patientId: entry.patient.toString(),
                        description: entry.comment
                    }
                }
            })
                .then(() => refetch())
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
    return (
        <>
            <SortableTable
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
                <EntryModal modal={modal} id="add-patient-identification-modal" title="Add - Identification">
                    <AdministrativeForm action={'Add'} entry={initial} onCancel={actions.reset} onChange={onAdded} />
                </EntryModal>
            )}

            {selected?.type === 'update' && (
                <EntryModal modal={modal} id="edit-patient-identification-modal" title="Edit - Comment">
                    <AdministrativeForm
                        action={'Edit'}
                        entry={asEntry(selected.item)}
                        onCancel={actions.reset}
                        onChange={onChanged}
                    />
                </EntryModal>
            )}

            {selected?.type === 'detail' && (
                <DetailsModal
                    title={'View details - Administrative'}
                    modal={modal}
                    details={asDetail(selected.item)}
                    onClose={actions.reset}
                />
            )}
        </>
    );
};
