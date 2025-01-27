import { useEffect, useRef, useState } from 'react';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import {
    PatientPhone,
    useAddPatientPhoneMutation,
    useDeletePatientPhoneMutation,
    useUpdatePatientPhoneMutation
} from 'generated/graphql/schema';
import { Direction, sortByAlpha, sortByNestedProperty, withDirection } from 'sorting/Sort';
import { externalizeDate, internalizeDate } from 'date';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { orNull } from 'utils/orNull';
import { TableBody, TableComponent } from 'components/Table';
import { ConfirmationModal } from 'confirmation';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { Detail, DetailsModal } from 'apps/patient/profile/DetailsModal';
import { EntryModal } from 'apps/patient/profile/entry';
import { maybeDescription, maybeId } from 'apps/patient/profile/coded';
import {
    PatientProfilePhoneEmailResult,
    useFindPatientProfilePhoneAndEmail
} from './useFindPatientProfilePhoneAndEmail';
import { PhoneEmailEntryForm } from './PhoneEmailEntryForm';
import { PhoneEmailEntry, NewPhoneEmailEntry, UpdatePhoneEmailEntry, isAdd, isUpdate } from './PhoneEmailEntry';
import { useAlert } from 'alert/useAlert';
import { useProfileContext } from '../ProfileContext';
import { sortingByDate } from 'sorting/sortingByDate';
import { Patient } from '../Patient';
import { PatientTableActions } from 'apps/patient/profile/PatientTableActions';

const asDetail = (data: PatientPhone): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Type', value: maybeDescription(data.use) },
    { name: 'Use', value: maybeDescription(data.use) },
    { name: 'Country code', value: data.countryCode },
    { name: 'Phone number', value: data.number },
    { name: 'Extension', value: data.extension },
    { name: 'Email address', value: data.email },
    { name: 'Url', value: data.url },
    { name: 'Additional comments', value: data.comment }
];

const asEntry = (data: PatientPhone): UpdatePhoneEmailEntry => ({
    patient: data.patient,
    id: +data.id,
    asOf: internalizeDate(data.asOf),
    type: maybeId(data.type),
    use: maybeId(data.use),
    countryCode: orNull(data.countryCode),
    number: orNull(data.number),
    extension: orNull(data.extension),
    email: orNull(data.email),
    url: orNull(data.url),
    comment: orNull(data.comment)
});

const resolveInitialEntry = (patient: string): NewPhoneEmailEntry => ({
    patient: +patient,
    asOf: internalizeDate(new Date()),
    type: null,
    use: null,
    countryCode: null,
    number: null,
    extension: null,
    email: null,
    url: null,
    comment: null
});

type Props = {
    patient: Patient | undefined;
};

export const PhoneAndEmailTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true },
        { name: 'Type', sortable: true },
        { name: 'Phone number', sortable: true },
        { name: 'Email address', sortable: true },
        { name: 'Actions', sortable: false }
    ]);
    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient?.id || '');
    const { changed } = useProfileContext();

    const { selected, actions } = useTableActionState<PatientPhone>();

    const [isActions, setIsActions] = useState<number | null>(null);

    const modal = useRef<ModalRef>(null);

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    const [phoneEmail, setPhoneEmail] = useState<PatientPhone[]>([]);

    const handleComplete = (data: PatientProfilePhoneEmailResult) => {
        setTotal(data?.findPatientProfile?.phones?.total ?? 0);
        setPhoneEmail(sortingByDate(data?.findPatientProfile?.phones?.content || []) as Array<PatientPhone>);
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfilePhoneAndEmail({ onCompleted: handleComplete });

    const [add] = useAddPatientPhoneMutation();
    const [update] = useUpdatePatientPhoneMutation();
    const [remove] = useDeletePatientPhoneMutation();

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

    const onAdded = (entry: PhoneEmailEntry) => {
        if (isAdd(entry) && entry.use && entry.type) {
            add({
                variables: {
                    input: {
                        ...entry,
                        patient: entry.patient,
                        asOf: externalizeDate(entry.asOf),
                        use: entry.use,
                        type: entry.type
                    }
                }
            })
                .then(() => {
                    refetch();
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Added Phone & Email`
                    });
                    changed();
                })
                .then(actions.reset);
        }
    };

    const onChanged = (entry: PhoneEmailEntry) => {
        if (isUpdate(entry) && entry.use && entry.type) {
            const updated = entry as UpdatePhoneEmailEntry;
            update({
                variables: {
                    input: {
                        ...entry,
                        patient: updated.patient,
                        id: +updated.id,
                        asOf: externalizeDate(entry.asOf),
                        use: entry.use,
                        type: entry.type
                    }
                }
            })
                .then(() => {
                    refetch();
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Updated Phone & Email`
                    });
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
                        id: +selected.item.id
                    }
                }
            })
                .then(() => {
                    refetch();
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Deleted Phone & Email`
                    });
                    changed();
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
                setPhoneEmail(
                    phoneEmail?.slice().sort((a: PatientPhone, b: PatientPhone) => {
                        const dateA: any = new Date(a?.asOf);
                        const dateB: any = new Date(b?.asOf);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'type':
                setPhoneEmail(phoneEmail.slice().sort(withDirection(sortByNestedProperty('type'), type)));
                break;
            case 'phone number':
                setPhoneEmail(phoneEmail.slice().sort(withDirection(sortByAlpha('number') as any, type)));
                break;
            case 'email address':
                setPhoneEmail(phoneEmail.slice().sort(withDirection(sortByAlpha('email') as any, type)));
                break;
        }
    };

    /**
     * Formats the Phone and Email object into TableComponent compatible TableBody object which represents a single row.
     * Each "title" in the tableDetails is a template of each column cell of the row being created.
     * @param {PatientPhone} phone, each item of the morbidity response data
     * @param {number} index, index of the array item
     * @return {TableBody}
     */

    const generateTableRow = (phone: PatientPhone, index: number): TableBody => {
        return {
            id: index,
            tableDetails: [
                {
                    id: 1,
                    title: phone?.asOf ? internalizeDate(phone?.asOf) : null
                },
                {
                    id: 2,
                    title: phone?.type ? (
                        <span>
                            {phone?.type.description}
                            {phone.use?.description ? `/${phone.use?.description}` : ''}
                        </span>
                    ) : null
                },
                {
                    id: 3,
                    title: phone?.number || null
                },
                {
                    id: 4,
                    title: phone?.email || null
                },
                {
                    id: 5,
                    title: (
                        <PatientTableActions
                            setActiveIndex={setIsActions}
                            activeIndex={isActions}
                            index={index}
                            disabled={patient?.status !== 'ACTIVE'}
                            handleAction={(type: string) => {
                                tableActionStateAdapter(actions, phone)(type);
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
     * @return {TableBody[]} list of TableBody each created from Phone And Email
     */
    const generateTableBody = () => {
        return (phoneEmail?.length > 0 && phoneEmail.map(generateTableRow)) || [];
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
                            Add phone & email
                        </Button>
                    </div>
                }
                tableHeader={'Phone & email'}
                tableHead={tableHead}
                tableBody={generateTableBody()}
                totalResults={total}
                currentPage={currentPage}
                handleNext={setCurrentPage}
                sortData={handleSort}
            />
            {selected?.type === 'add' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="add-patient-phone-email-modal"
                    title="Add - Phone & email">
                    <PhoneEmailEntryForm entry={initial} onChange={onAdded} />
                </EntryModal>
            )}
            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-phone-email-modal"
                    title="Edit - Phone & email">
                    <PhoneEmailEntryForm
                        entry={asEntry(selected.item)}
                        onDelete={() => actions.selectForDelete(selected.item)}
                        onChange={onChanged}
                    />
                </EntryModal>
            )}
            {selected?.type === 'delete' && (
                <ConfirmationModal
                    modal={modal}
                    title="Delete Phone & email"
                    message="Are you sure you want to delete this Phone & email record?"
                    confirmText="Yes, delete"
                    onConfirm={onDeleted}
                    onCancel={actions.reset}
                />
            )}

            {selected?.type === 'detail' && (
                <DetailsModal
                    title={'View details - Phone & email'}
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
