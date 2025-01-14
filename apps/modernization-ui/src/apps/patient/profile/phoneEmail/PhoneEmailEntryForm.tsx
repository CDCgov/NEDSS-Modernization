import { EntryFooter } from 'apps/patient/profile/entry';
import { FieldValues, FormProvider, useForm } from 'react-hook-form';
import { PhoneAndEmailEntryFields } from './PhoneAndEmailEntryFields';
import { PhoneEmailEntry } from './PhoneEmailEntry';

type EntryProps = {
    entry: PhoneEmailEntry;
    onChange: (updated: PhoneEmailEntry) => void;
    onDelete?: () => void;
};

export const PhoneEmailEntryForm = ({ entry, onChange, onDelete }: EntryProps) => {
    const form = useForm<PhoneEmailEntry>({ mode: 'onBlur', defaultValues: entry });

    const onSubmit = (entered: FieldValues) => {
        onChange({
            ...entry,
            ...entered
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <PhoneAndEmailEntryFields />
            </FormProvider>
            <EntryFooter onSave={form.handleSubmit(onSubmit)} onDelete={onDelete} />
        </>
    );
};
