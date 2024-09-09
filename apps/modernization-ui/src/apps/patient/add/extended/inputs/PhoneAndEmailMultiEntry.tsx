import { PhoneAndEmailEntryFields } from 'apps/patient/profile/phoneEmail/PhoneAndEmailEntryFields';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { usePatientPhoneCodedValues } from 'apps/patient/profile/phoneEmail/usePatientPhoneCodedValues';
import { EntryWrapper } from 'components/Entry';
import { internalizeDate } from 'date';
import { MultiValueEntry } from 'design-system/entry/multi-value/MultiValueEntry';
import { Column } from 'design-system/table';

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
    const coded = usePatientPhoneCodedValues();
    const renderForm = () => <PhoneAndEmailEntryFields />;

    const renderView = (entry: PhoneEmailFields) => (
        <>
            <EntryWrapper orientation="horizontal" htmlFor="asOf" label="As of:">
                {entry.asOf}
            </EntryWrapper>
            <EntryWrapper orientation="horizontal" htmlFor="phone" label="Type:">
                {coded.types.find((e) => e.value === entry.type)?.name}
            </EntryWrapper>
            <EntryWrapper orientation="horizontal" htmlFor="phone" label="Use:">
                {coded.uses.find((e) => e.value === entry.use)?.name}
            </EntryWrapper>
            <EntryWrapper orientation="horizontal" htmlFor="phone" label="Country code:">
                {entry.countryCode}
            </EntryWrapper>
            <EntryWrapper orientation="horizontal" htmlFor="phone" label="Phone number:">
                {entry.number}
            </EntryWrapper>
            <EntryWrapper orientation="horizontal" htmlFor="phone" label="Extension:">
                {entry.extension}
            </EntryWrapper>
            <EntryWrapper orientation="horizontal" htmlFor="phone" label="Url:">
                {entry.url}
            </EntryWrapper>
            <EntryWrapper orientation="horizontal" htmlFor="phone" label="Additional comments">
                {entry.comment}
            </EntryWrapper>
        </>
    );

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
