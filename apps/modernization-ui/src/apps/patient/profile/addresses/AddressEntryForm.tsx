import { EntryFooter } from 'apps/patient/profile/entry';
import { externalizeDate } from 'date';
import { FormProvider, useForm } from 'react-hook-form';
import { orNull } from 'utils';

import { AddressEntry } from './AddressEntry';
import { AddressEntryFields } from './AddressEntryFields';
import './AddressEntryForm.scss';

type EntryProps = {
    entry: AddressEntry;
    onChange: (updated: AddressEntry) => void;
    onDelete?: () => void;
};

export const AddressEntryForm = ({ entry, onChange, onDelete }: EntryProps) => {
    const form = useForm<AddressEntry, Partial<AddressEntry>>({ mode: 'onBlur', defaultValues: entry });

    const onSubmit = (entered: AddressEntry) => {
        onChange({
            ...entry,
            asOf: externalizeDate(entered.asOf),
            use: orNull(entered.use),
            type: orNull(entered.type),
            address1: entered.address1,
            address2: entered.address2,
            city: entered.city,
            state: orNull(entered.state),
            zipcode: entered.zipcode,
            county: orNull(entered.county),
            censusTract: entered.censusTract,
            country: orNull(entered.country),
            comment: entered.comment
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <AddressEntryFields />
            </FormProvider>
            <EntryFooter onSave={form.handleSubmit(onSubmit)} onDelete={onDelete} />
        </>
    );
};
