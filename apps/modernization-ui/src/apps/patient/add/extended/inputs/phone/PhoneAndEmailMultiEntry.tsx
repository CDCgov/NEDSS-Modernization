import { PhoneAndEmailEntryFields } from 'apps/patient/profile/phoneEmail/PhoneAndEmailEntryFields';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { internalizeDate } from 'date';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';
import { Column } from 'design-system/table';
import { PhoneEntryView } from './PhoneEntryView';
import { usePatientPhoneCodedValues } from 'apps/patient/profile/phoneEmail/usePatientPhoneCodedValues';

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
type Props = {
    onChange: (data: PhoneEmailFields[]) => void;
    isDirty: (isDirty: boolean) => void;
};
export const PhoneAndEmailMultiEntry = ({ onChange, isDirty }: Props) => {
    const coded = usePatientPhoneCodedValues();
    const renderForm = () => <PhoneAndEmailEntryFields />;
    const renderView = (entry: PhoneEmailFields) => <PhoneEntryView entry={entry} />;

    const columns: Column<PhoneEmailFields>[] = [
        { id: 'phoneEmailAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'phoneEmailType', name: 'Type', render: (v) => coded.types.find((t) => t.value === v.type)?.name },
        { id: 'phoneNumber', name: 'Phone number', render: (v) => v.number },
        { id: 'email', name: 'Email address', render: (v) => v.email },
        { id: 'comments', name: 'Comments', render: (v) => v.comment }
    ];

    return (
        <MultiValueEntry<PhoneEmailFields>
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
