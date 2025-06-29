import { ReactNode } from 'react';
import { Column } from 'design-system/table';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { AddressEntry, AddressEntryFields, initial } from 'apps/patient/data/address';
import { asAddressTypeUse } from 'apps/patient/data/address/utils';
import { Sizing } from 'design-system/field';

import { AddressView } from './AddressView';

const defaultValue: Partial<AddressEntry> = initial();

type Props = {
    id: string;
    values?: AddressEntry[];
    onChange: (data: AddressEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
    sizing?: Sizing;
};
export const AddressRepeatingBlock = ({ id, values, errors, onChange, isDirty, sizing = 'medium' }: Props) => {
    const renderForm = () => <AddressEntryFields sizing={sizing} />;
    const renderView = (entry: AddressEntry) => <AddressView entry={entry} sizing={sizing} />;

    const columns: Column<AddressEntry>[] = [
        { id: 'addressAsOf', name: 'As of', render: (v) => v.asOf },
        {
            id: 'addressType',
            name: 'Type',
            render: (v) => asAddressTypeUse({ type: v.type?.name, use: v.use?.name })
        },
        { id: 'address', name: 'Address', render: (v) => v.address1 },
        { id: 'city', name: 'City', render: (v) => v.city },
        { id: 'state', name: 'State', render: (v) => v.state?.name },
        { id: 'zip', name: 'Zip', render: (v) => v.zipcode }
    ];

    return (
        <RepeatingBlock<AddressEntry>
            id={id}
            title="Address"
            defaultValues={defaultValue}
            data={values}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
            errors={errors}
            sizing={sizing}
        />
    );
};
