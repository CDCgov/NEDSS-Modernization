import { PhoneEmailEntry } from 'apps/patient/data/entry';
import { PhoneEmailEntryFields } from 'apps/patient/data/phoneEmail/PhoneEmailEntryFields';
import { internalizeDate } from 'date';
import { Column } from 'design-system/table';
import { PhoneEntryView } from './PhoneEntryView';
import { ValueView } from 'design-system/entry/multi-value/ValueView';

const defaultValue: Partial<PhoneEmailEntry> = {
    asOf: internalizeDate(new Date()),
    type: undefined,
    use: undefined,
    countryCode: '',
    phoneNumber: '',
    extension: '',
    email: '',
    url: '',
    comment: ''
};
type Props = {
    onChange: (data: PhoneEmailEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
};
export const PhoneAndEmailMultiEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <PhoneEmailEntryFields />;
    const renderView = (entry: PhoneEmailEntry) => <PhoneEntryView entry={entry} />;

    const columns: Column<PhoneEmailEntry>[] = [
        { id: 'phoneEmailAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'phoneEmailType', name: 'Type', render: (v) => v.type?.name },
        { id: 'phoneNumber', name: 'Phone number', render: (v) => v.phoneNumber },
        { id: 'email', name: 'Email address', render: (v) => v.email },
        { id: 'comments', name: 'Comments', render: (v) => v.comment }
    ];

    return (
        <ValueView<PhoneEmailEntry>
            id="section-PhoneAndEmail"
            title="Phone & email"
            defaultValues={defaultValue}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
        />
    );
};
