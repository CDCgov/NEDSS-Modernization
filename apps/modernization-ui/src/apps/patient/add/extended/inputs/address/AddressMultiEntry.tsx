import { AddressEntry } from 'apps/patient/data/entry';
import { internalizeDate } from 'date';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';
import { Column } from 'design-system/table';
import { AddressView } from './AddressView';
import { AddressEntryFields } from 'apps/patient/data/address/AddressEntryFields';

const defaultValue: AddressEntry = {
    asOf: internalizeDate(new Date()),
    type: { name: '', value: '' },
    use: { name: '', value: '' },
    address1: '',
    address2: '',
    city: '',
    state: { name: '', value: '' },
    zipcode: '',
    county: { name: '', value: '' },
    country: { name: '', value: '' },
    censusTract: '',
    comment: ''
};

type Props = {
    onChange: (data: AddressEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
};
export const AddressMultiEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <AddressEntryFields />;
    const renderView = (entry: AddressEntry) => <AddressView entry={entry} />;

    const columns: Column<AddressEntry>[] = [
        { id: 'addressAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'addressType', name: 'Type', render: (v) => v.type.name },
        { id: 'address', name: 'Address', render: (v) => v.address1 },
        { id: 'city', name: 'City', render: (v) => v.city },
        { id: 'state', name: 'State', render: (v) => v.state?.name },
        { id: 'zip', name: 'Zip', render: (v) => v.zipcode }
    ];

    return (
        <MultiValueEntry<AddressEntry>
            id="address"
            title="Address"
            defaultValues={defaultValue}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
        />
    );
};
