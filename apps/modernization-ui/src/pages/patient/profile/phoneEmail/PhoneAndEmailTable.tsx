import { useEffect, useRef, useState } from 'react';
import format from 'date-fns/format';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import {
    PatientPhone,
    useAddPatientPhoneMutation,
    useDeletePatientPhoneMutation,
    useUpdatePatientPhoneMutation
} from 'generated/graphql/schema';
import { Direction, sortByAlpha, sortByNestedProperty, withDirection } from 'sorting/Sort';
import { externalizeDateTime, internalizeDate } from 'date';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { orNull } from 'utils/orNull';
import { SortableTable } from 'components/Table/SortableTable';
import { Actions } from 'components/Table/Actions';
import { ConfirmationModal } from 'confirmation';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { Detail, DetailsModal } from 'pages/patient/profile/DetailsModal';
import EntryModal from 'pages/patient/profile/entry';
import { maybeDescription, maybeId } from 'pages/patient/profile/coded';
import {
    PatientProfilePhoneEmailResult,
    useFindPatientProfilePhoneAndEmail
} from './useFindPatientProfilePhoneAndEmail';
import { PhoneEmailEntryForm } from './PhoneEmailEntryForm';
import { PhoneEmailEntry, NewPhoneEmailEntry, UpdatePhoneEmailEntry, isAdd, isUpdate } from './PhoneEmailEntry';
import { useAlert } from 'alert/useAlert';
import { NoData } from 'components/NoData';

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
    asOf: null,
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
    patient: string;
};

export const PhoneAndEmailTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true, sort: 'all' },
        { name: 'Type', sortable: true, sort: 'all' },
        { name: 'Phone number', sortable: true, sort: 'all' },
        { name: 'Email address', sortable: true, sort: 'all' },
        { name: 'Actions', sortable: false }
    ]);
    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient);

    const { selected, actions } = useTableActionState<PatientPhone>();

    const [isActions, setIsActions] = useState<number | null>(null);

    const modal = useRef<ModalRef>(null);

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    const [phoneEmail, setPhoneEmail] = useState<PatientPhone[]>([]);

    const handleComplete = (data: PatientProfilePhoneEmailResult) => {
        setTotal(data?.findPatientProfile?.phones?.total ?? 0);
        setPhoneEmail(data?.findPatientProfile?.phones?.content ?? []);
    };

    const [fetch, { refetch, loading }] = useFindPatientProfilePhoneAndEmail({ onCompleted: handleComplete });

    const [add] = useAddPatientPhoneMutation();
    const [update] = useUpdatePatientPhoneMutation();
    const [remove] = useDeletePatientPhoneMutation();

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

    const onAdded = (entry: PhoneEmailEntry) => {
        if (isAdd(entry) && entry.use && entry.type) {
            add({
                variables: {
                    input: {
                        ...entry,
                        patient: entry.patient,
                        asOf: externalizeDateTime(entry.asOf),
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
                        asOf: externalizeDateTime(entry.asOf),
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

    return (
        <>
            <SortableTable
                isLoading={loading}
                isPagination={true}
                buttons={
                    <div className="grid-row">
                        <Button type="button" onClick={actions.prepareForAdd} className="display-inline-flex">
                            <Icon.Add className="margin-right-05" />
                            Add phone & email
                        </Button>
                    </div>
                }
                tableHeader={'Phone & email'}
                tableHead={tableHead}
                tableBody={phoneEmail?.map((phone, index: number) => (
                    <tr key={index}>
                        <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                            {phone?.asOf ? (
                                <span>
                                    {format(new Date(phone?.asOf), 'MM/dd/yyyy')} <br />{' '}
                                </span>
                            ) : (
                                <NoData />
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                            {phone?.type ? (
                                <span>
                                    {phone?.type.description}
                                    {phone.use?.description ? `/${phone.use?.description}` : ''}
                                </span>
                            ) : (
                                <NoData />
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                            {phone?.number ? <span>{phone?.number}</span> : <NoData />}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                            {phone?.email ? <span>{phone?.email}</span> : <NoData />}
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
                                            tableActionStateAdapter(actions, phone)(type);
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
                    id="add-patient-phone-email-modal"
                    title="Add - Phone & email">
                    <PhoneEmailEntryForm action={'Add'} entry={initial} onChange={onAdded} />
                </EntryModal>
            )}
            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-phone-email-modal"
                    title="Edit - Phone & email">
                    <PhoneEmailEntryForm
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
