import { AddressFields } from 'apps/patient/profile/addresses/AddressEntry';
import { AddressEntryFields } from 'apps/patient/profile/addresses/AddressEntryFields';
import { internalizeDate } from 'date';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';
import { Column } from 'design-system/table';
import { AddressView } from './AddressView';
import { usePatientAddressCodedValues } from 'apps/patient/profile/addresses/usePatientAddressCodedValues';
import { useLocationCodedValues } from 'location';

const defaultValue: AddressFields = {
    asOf: internalizeDate(new Date()),
    type: '',
    use: '',
    address1: '',
    address2: '',
    city: '',
    state: '',
    zipcode: '',
    county: '',
    country: '',
    censusTract: '',
    comment: ''
};

type Props = {
    onChange: (data: AddressFields[]) => void;
    isDirty: (isDirty: boolean) => void;
};
export const AddressMultiEntry = ({ onChange, isDirty }: Props) => {
    const coded = usePatientAddressCodedValues();
    const location = useLocationCodedValues();
    const renderForm = () => <AddressEntryFields />;
    const renderView = (entry: AddressFields) => <AddressView entry={entry} />;

    const columns: Column<AddressFields>[] = [
        { id: 'addressAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'addressType', name: 'Type', render: (v) => coded.types.find((t) => t.value === v.type)?.name },
        { id: 'address', name: 'Address', render: (v) => v.address1 },
        { id: 'city', name: 'City', render: (v) => v.city },
        { id: 'state', name: 'State', render: (v) => location.states.all.find((s) => s.value === v.state)?.name },
        { id: 'zip', name: 'Zip', render: (v) => v.zipcode }
    ];

    return (
        <MultiValueEntry<AddressFields>
            id="section-Address"
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
