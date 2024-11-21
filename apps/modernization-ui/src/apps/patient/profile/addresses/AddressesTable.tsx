import { useEffect, useRef, useState } from 'react';
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
import { ConfirmationModal } from 'confirmation';
import { tableActionStateAdapter, useTableActionState } from 'table-action';
import { Detail, DetailsModal } from 'apps/patient/profile/DetailsModal';
import { EntryModal } from 'apps/patient/profile/entry';
import { maybeDescription, maybeId } from 'apps/patient/profile/coded';
import { PatientProfileAddressesResult, useFindPatientProfileAddresses } from './useFindPatientProfileAddresses';
import { AddressEntryForm } from './AddressEntryForm';
import { AddressEntry, NewAddressEntry, UpdateAddressEntry, isAdd, isUpdate } from './AddressEntry';
import { useAlert } from 'alert/useAlert';
import { useProfileContext } from '../ProfileContext';
import { sortingByDate } from 'sorting/sortingByDate';
import { Patient } from '../Patient';
import { TableBody, TableComponent } from 'components/Table';
import { PatientTableActions } from 'apps/patient/profile/PatientTableActions';
import { asAddressTypeUse } from 'apps/patient/data/address/utils';

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
    asOf: internalizeDate(new Date()),
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
    patient: Patient | undefined;
};

export const AddressesTable = ({ patient }: Props) => {
    const { showAlert } = useAlert();

    const [tableHead, setTableHead] = useState<{ name: string; sortable: boolean; sort?: string }[]>([
        { name: 'As of', sortable: true },
        { name: 'Type', sortable: true },
        { name: 'Address', sortable: true },
        { name: 'City', sortable: true },
        { name: 'State', sortable: true },
        { name: 'Zip', sortable: true },
        { name: 'Actions', sortable: false }
    ]);

    const [total, setTotal] = useState<number>(0);
    const [currentPage, setCurrentPage] = useState<number>(1);

    const initial = resolveInitialEntry(patient?.id || '');

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
        setAddresses(
            sortingByDate(
                (data?.findPatientProfile?.addresses?.content as Array<PatientAddress>) || []
            ) as Array<PatientAddress>
        );
    };

    const [fetch, { refetch, called, loading }] = useFindPatientProfileAddresses({ onCompleted: handleComplete });

    const [add] = useAddPatientAddressMutation();
    const [update] = useUpdatePatientAddressMutation();
    const [remove] = useDeletePatientAddressMutation();

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

    /**
     * Formats the Address object into TableComponent compatible TableBody object which represents a single row.
     * Each "title" in the tableDetails is a template of each column cell of the row being created.
     * @param {PatientAddress} address, each item of the morbidity response data
     * @param {number} index, index of the array item
     * @return {TableBody}
     */
    const generateTableRow = (address: PatientAddress, index: number): TableBody => {
        return {
            id: index,
            tableDetails: [
                {
                    id: 1,
                    title: address?.asOf ? internalizeDate(address?.asOf) : null
                },
                {
                    id: 2,
                    title: address?.type ? (
                        <span>
                            {asAddressTypeUse({ type: address?.type.description, use: address.use?.description })}
                        </span>
                    ) : null
                },
                {
                    id: 3,
                    title: ((address?.address1 || address?.address2) && getAddress(address)) || null
                },
                {
                    id: 4,
                    title: address?.city || null
                },
                {
                    id: 5,
                    title: address?.state?.description || null
                },
                {
                    id: 6,
                    title: address?.zipcode || null
                },
                {
                    id: 7,
                    title: (
                        <PatientTableActions
                            setActiveIndex={setIsActions}
                            activeIndex={isActions}
                            index={index}
                            disabled={patient?.status !== 'ACTIVE'}
                            handleAction={(type: string) => {
                                tableActionStateAdapter(actions, address)(type);
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
     * @return {TableBody[]} list of TableBody each created from Address
     */
    const generateTableBody = () => {
        return (addresses?.length > 0 && addresses.map(generateTableRow)) || [];
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
                            Add address
                        </Button>
                    </div>
                }
                tableHeader={'Address'}
                tableHead={tableHead}
                tableBody={generateTableBody()}
                totalResults={total}
                currentPage={currentPage}
                handleNext={setCurrentPage}
                sortData={handleSort}
            />
            {selected?.type === 'add' && (
                <EntryModal onClose={actions.reset} modal={modal} id="add-patient-address-modal" title="Add - Address">
                    <AddressEntryForm entry={initial} onChange={onAdded} />
                </EntryModal>
            )}
            {selected?.type === 'update' && (
                <EntryModal
                    onClose={actions.reset}
                    modal={modal}
                    id="edit-patient-address-modal"
                    title="Edit - Address">
                    <AddressEntryForm
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
