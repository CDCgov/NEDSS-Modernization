import { ReactNode } from 'react';

import { initial, PhoneEmailEntry, PhoneEmailEntryFields } from 'apps/patient/data/phoneEmail';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { Sizing } from 'design-system/field';
import { Column } from 'design-system/table';

import { PhoneEntryView } from './PhoneEntryView';

const defaultValue: Partial<PhoneEmailEntry> = initial();

type PhoneAndEmailRepeatingBlockProps = {
    id: string;
    values?: PhoneEmailEntry[];
    onChange: (data: PhoneEmailEntry[]) => void;
    isDirty: (isDirty: boolean) => void;
    errors?: ReactNode[];
    sizing?: Sizing;
};
export const PhoneAndEmailRepeatingBlock = ({
    id,
    values,
    errors,
    onChange,
    isDirty,
    sizing,
}: PhoneAndEmailRepeatingBlockProps) => {
    const renderForm = () => <PhoneEmailEntryFields sizing={sizing} />;
    const renderView = (entry: PhoneEmailEntry) => <PhoneEntryView entry={entry} sizing={sizing} />;

    const columns: Column<PhoneEmailEntry>[] = [
        { id: 'phoneEmailAsOf', name: 'As of', render: (v) => v.asOf },
        { id: 'phoneEmailType', name: 'Type', render: (v) => v.type?.name + '/' + v.use?.name },
        { id: 'phoneNumber', name: 'Phone number', render: (v) => v.phoneNumber },
        { id: 'email', name: 'Email address', render: (v) => v.email },
        { id: 'comments', name: 'Comments', render: (v) => v.comment },
    ];

    return (
        <RepeatingBlock<PhoneEmailEntry>
            id={id}
            title="Phone & email"
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

export type { PhoneAndEmailRepeatingBlockProps };
