import { AddressEntry } from 'apps/patient/data/entry';
import { today } from 'date';
import { Column } from 'design-system/table';
import { AddressView } from './AddressView';
import { AddressEntryFields } from 'apps/patient/data/address/AddressEntryFields';
import { RepeatingBlock } from 'design-system/entry/multi-value/RepeatingBlock';
import { ReactNode } from 'react';

const defaultValue: Partial<AddressEntry> = {
    asOf: today(),
    type: undefined,
    use: undefined,
    address1: '',
    address2: '',
    city: '',
    state: undefined,
    zipcode: '',
    county: undefined,
    country: undefined,
    censusTract: '',
    comment: ''
};

type Props = {
    onChange: (data: AddressEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
};
export const AddressRepeatingBlock = ({ onChange, isDirty, errors }: Props) => {
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
        <RepeatingBlock<AddressEntry>
            id="address"
            title="Address"
            defaultValues={defaultValue}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
            errors={errors}
        />
    );
};
