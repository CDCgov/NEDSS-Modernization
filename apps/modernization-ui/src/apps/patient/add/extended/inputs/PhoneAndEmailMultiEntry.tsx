import { PhoneAndEmailEntryFields } from 'apps/patient/profile/phoneEmail/PhoneAndEmailEntryFields';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { internalizeDate } from 'date';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';
import { Column } from 'design-system/table';
import { PhoneEntryView } from './PhoneEntryView';

const defaultValue: PhoneEmailFields = {
    asOf: internalizeDate(new Date()),
    type: '',
    use: '',
    countryCode: '',
    number: '',
    extension: '',
    email: '',
    url: '',
    comment: ''
};
const columns: Column<PhoneEmailFields>[] = [
    { id: 'phoneEmailAsOf', name: 'As of', render: (v) => v.asOf },
    { id: 'phoneEmailType', name: 'Type', render: (v) => v.type },
    { id: 'phoneNumber', name: 'Phone number', render: (v) => v.number },
    { id: 'email', name: 'Email address', render: (v) => v.email },
    { id: 'comments', name: 'Comments', render: (v) => v.comment }
];
type Props = {
    onChange: (data: PhoneEmailFields[]) => void;
    isDirty: (isDirty: boolean) => void;
};
export const PhoneAndEmailMultiEntry = ({ onChange, isDirty }: Props) => {
    const renderForm = () => <PhoneAndEmailEntryFields />;
    const renderView = (entry: PhoneEmailFields) => <PhoneEntryView entry={entry} />;

    return (
        <MultiValueEntry
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
