import { ReactNode } from 'react';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { Column } from 'design-system/table';
import { PhoneEmailEntry, PhoneEmailEntryFields, initial } from 'apps/patient/data/phoneEmail';
import { PhoneEntryView } from './PhoneEntryView';

const defaultValue: Partial<PhoneEmailEntry> = initial();

type PhoneAndEmailRepeatingBlockProps = {
    id: string;
    values?: PhoneEmailEntry[];
    onChange: (data: PhoneEmailEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
};
export const PhoneAndEmailRepeatingBlock = ({
    id,
    values,
    errors,
    onChange,
    isDirty
}: PhoneAndEmailRepeatingBlockProps) => {
    const renderForm = () => <PhoneEmailEntryFields />;
    const renderView = (entry: PhoneEmailEntry) => <PhoneEntryView entry={entry} />;

    const columns: Column<PhoneEmailEntry>[] = [
        { id: 'phoneEmailAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'phoneEmailType', name: 'Type', render: (v) => v.type?.name + '/' + v.use?.name },
        { id: 'phoneNumber', name: 'Phone number', render: (v) => v.phoneNumber },
        { id: 'email', name: 'Email address', render: (v) => v.email },
        { id: 'comments', name: 'Comments', render: (v) => v.comment }
    ];

    return (
        <RepeatingBlock<PhoneEmailEntry>
            id={id}
            title="Phone & email"
            defaultValues={defaultValue}
            values={values}
            columns={columns}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
            errors={errors}
        />
    );
};

export type { PhoneAndEmailRepeatingBlockProps };
