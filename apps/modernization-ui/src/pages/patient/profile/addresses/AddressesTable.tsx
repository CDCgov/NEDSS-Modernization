import { useEffect, useRef, useState } from 'react';
import format from 'date-fns/format';
import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import {
    PatientAddress,
    useAddPatientAddressMutation,
    useDeletePatientAddressMutation,
    useUpdatePatientAddressMutation
} from 'generated/graphql/schema';
import './AddressesTable.scss';
import { Direction, sortByAlpha, sortByNestedProperty, withDirection } from 'sorting/Sort';
import { internalizeDate } from 'date';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { orNull } from 'utils/orNull';
import { SortableTable } from 'components/Table/SortableTable';
import { Actions } from 'components/Table/Actions';
import { ConfirmationModal } from 'confirmation';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { Detail, DetailsModal } from 'pages/patient/profile/DetailsModal';
import EntryModal from 'pages/patient/profile/entry';
import { maybeDescription, maybeId } from 'pages/patient/profile/coded';
import { PatientProfileAddressesResult, useFindPatientProfileAddresses } from './useFindPatientProfileAddresses';
import { AddressEntryForm } from './AddressEntryForm';
import { AddressEntry, NewAddressEntry, UpdateAddressEntry, isAdd, isUpdate } from './AddressEntry';
import { useAlert } from 'alert/useAlert';
import { NoData } from 'components/NoData';
import { useParams } from 'react-router-dom';
import { usePatientProfile } from '../usePatientProfile';
import { useProfileContext } from '../ProfileContext';

const asDetail = (data: PatientAddress): Detail[] => [
    { name: 'As of', value: internalizeDate(data.asOf) },
    { name: 'Type', value: maybeDescription(data.type) },
    { name: 'Use', value: maybeDescription(data.use) },
    { name: 'Street address 1', value: data.address1 },
    { name: 'Street address 2', value: data.address2 },
    { name: 'City', value: data.city },
    { name: 'State', value: maybeDescription(data.state) },
    { name: 'Zip', value: data.zipcode },
    { name: 'County', value: maybeDescription(data.county) },
    { name: 'Census tract', value: data.censusTract },
    { name: 'Country', value: maybeDescription(data.country) },
    { name: 'Additional comments', value: data.comment }
];

const asEntry = (address: PatientAddress): UpdateAddressEntry => ({
    patient: address.patient,
    id: +address.id,
    asOf: internalizeDate(address.asOf),
    type: maybeId(address.type),
    use: maybeId(address.use),
    address1: orNull(address.address1),
    address2: orNull(address.address2),
    city: orNull(address.city),
    state: maybeId(address.state),
    zipcode: orNull(address.zipcode),
    county: maybeId(address.county),
    censusTract: orNull(address.censusTract),
    country: maybeId(address.country),
    comment: orNull(address.comment)
});

const resolveInitialEntry = (patient: string): NewAddressEntry => ({
    patient: +patient,
    asOf: null,
    type: null,
    use: null,
    address1: null,
    address2: null,
    city: null,
    state: null,
    zipcode: null,
    county: null,
    censusTract: null,
    country: null,
    comment: null
});

type Props = {
    patient: string;
};

export const AddressesTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();
    const { id } = useParams();
    const { profile } = usePatientProfile(id);
    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true, sort: 'all' },
        { name: 'Type', sortable: true, sort: 'all' },
        { name: 'Address', sortable: true, sort: 'all' },
        { name: 'City', sortable: true, sort: 'all' },
        { name: 'State', sortable: true, sort: 'all' },
        { name: 'Zip', sortable: true, sort: 'all' },
        { name: 'Actions', sortable: false }
    ]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient);

    const { selected, actions } = useTableActionState<PatientAddress>();

    const [isActions, setIsActions] = useState<number | null>(null);

    const modal = useRef<ModalRef>(null);
    const { changed } = useProfileContext();

    useEffect(() => {
        modal.current?.toggleModal(undefined, selected !== undefined);
    }, [selected]);

    const [addresses, setAddresses] = useState<PatientAddress[]>([]);

    const handleComplete = (data: PatientProfileAddressesResult) => {
        setTotal(data?.findPatientProfile?.addresses?.total ?? 0);
        setAddresses(data?.findPatientProfile?.addresses?.content ?? []);
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfileAddresses({ onCompleted: handleComplete });

    const [add] = useAddPatientAddressMutation();
    const [update] = useUpdatePatientAddressMutation();
    const [remove] = useDeletePatientAddressMutation();

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

    const onAdded = (entry: AddressEntry) => {
        if (isAdd(entry) && entry.use && entry.type) {
            add({
                variables: {
                    input: {
                        patient: entry.patient,
                        asOf: entry.asOf,
                        use: entry.use,
                        type: entry.type,
                        address1: entry.address1,
                        address2: entry.address2,
                        city: entry.city,
                        state: entry.state,
                        zipcode: entry.zipcode,
                        county: entry.county,
                        censusTract: entry.censusTract,
                        country: entry.country,
                        comment: entry.comment
                    }
                }
            })
                .then(() => {
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Added address`
                    });
                    refetch();
                    changed();
                })
                .then(actions.reset);
        }
    };

    const onChanged = (entry: AddressEntry) => {
        if (isUpdate(entry) && entry.use && entry.type) {
            const updated = entry as UpdateAddressEntry;
            update({
                variables: {
                    input: {
                        patient: updated.patient,
                        id: +updated.id,
                        asOf: entry.asOf,
                        use: entry.use,
                        type: entry.type,
                        address1: entry.address1,
                        address2: entry.address2,
                        city: entry.city,
                        state: entry.state,
                        zipcode: entry.zipcode,
                        county: entry.county,
                        censusTract: entry.censusTract,
                        country: entry.country,
                        comment: entry.comment
                    }
                }
            })
                .then(() => {
                    refetch();
                    showAlert({
                        type: 'success',
                        header: 'success',
                        message: `Updated address`
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
                        message: `Deleted address`
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
                setAddresses(
                    addresses?.slice().sort((a: PatientAddress, b: PatientAddress) => {
                        const dateA: any = new Date(a?.asOf);
                        const dateB: any = new Date(b?.asOf);
                        return type === 'asc' ? dateB - dateA : dateA - dateB;
                    })
                );
                break;
            case 'type':
                setAddresses(addresses.slice().sort(withDirection(sortByNestedProperty('type'), type)));
                break;
            case 'address':
                setAddresses(addresses.slice().sort(withDirection(sortByAlpha('address1'), type)));
                break;
            case 'city':
                setAddresses(addresses.slice().sort(withDirection(sortByAlpha('city'), type)));
                break;
            case 'state':
                setAddresses(addresses.slice().sort(withDirection(sortByNestedProperty('state'), type)));
                break;
            case 'zip':
                setAddresses(addresses.slice().sort(withDirection(sortByAlpha('zipcode'), type)));
                break;
        }
    };

    const getAddress = (name: PatientAddress) => {
        return `${name?.address1 ?? ''} ${name?.address2 ?? ''}`;
    };

    return (
        <>
            <SortableTable
                isLoading={!called || loading}
                isPagination={true}
                buttons={
                    <div className="grid-row">
                        <Button
                            disabled={profile?.patient?.status !== 'ACTIVE'}
                            type="button"
                            onClick={actions.prepareForAdd}
                            className="display-inline-flex">
                            <Icon.Add className="margin-right-05" />
                            Add address
                        </Button>
                    </div>
                }
                tableHeader={'Address'}
                tableHead={tableHead}
                tableBody={addresses?.map((name, index: number) => (
                    <tr key={index}>
                        <td className={`font-sans-md table-data ${tableHead[0].sort !== 'all' && 'sort-td'}`}>
                            {name?.asOf ? (
                                <span>
                                    {format(new Date(name?.asOf), 'MM/dd/yyyy')} <br />{' '}
                                </span>
                            ) : (
                                <NoData />
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[1].sort !== 'all' && 'sort-td'}`}>
                            {name?.type ? (
                                <span>
                                    {name?.type.description}
                                    {name.use?.description ? `/${name.use?.description}` : ''}
                                </span>
                            ) : (
                                <NoData />
                            )}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[2].sort !== 'all' && 'sort-td'}`}>
                            {name?.address1 || name?.address2 ? <span>{getAddress(name)}</span> : <NoData />}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[3].sort !== 'all' && 'sort-td'}`}>
                            {name?.city ? <span>{name?.city}</span> : <NoData />}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[4].sort !== 'all' && 'sort-td'}`}>
                            {name?.state ? <span>{name?.state?.description}</span> : <NoData />}
                        </td>
                        <td className={`font-sans-md table-data ${tableHead[5].sort !== 'all' && 'sort-td'}`}>
                            {name?.zipcode ? <span>{name?.zipcode}</span> : <NoData />}
                        </td>
                        <td>
                            <div className="table-span">
                                <Button
                                    type="button"
                                    unstyled
                                    disabled={profile?.patient?.status !== 'ACTIVE'}
                                    onClick={() => setIsActions(isActions === index ? null : index)}>
                                    <Icon.MoreHoriz className="font-sans-lg" />
                                </Button>

                                {isActions === index && (
                                    <Actions
                                        handleOutsideClick={() => setIsActions(null)}
                                        handleAction={(type: string) => {
                                            tableActionStateAdapter(actions, name)(type);
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
                <EntryModal onClose={actions.reset} modal={modal} id="add-patient-address-modal" title="Add - Address">
                    <AddressEntryForm action={'Add'} entry={initial} onChange={onAdded} />
                </EntryModal>
            )}
            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-address-modal"
                    title="Edit - Address">
                    <AddressEntryForm
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
                    message="Are you sure you want to delete this address record?"
                    confirmText="Yes, delete"
                    onConfirm={onDeleted}
                    onCancel={actions.reset}
                />
            )}

            {selected?.type === 'detail' && (
                <DetailsModal
                    title={'View details - Address'}
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
