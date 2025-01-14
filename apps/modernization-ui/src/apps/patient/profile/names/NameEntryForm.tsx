import { EntryFooter } from 'apps/patient/profile/entry';
import { FieldValues, FormProvider, useForm } from 'react-hook-form';
import { NameEntry } from './NameEntry';
import { NameEntryFields } from './NameEntryFields';

type EntryProps = {
    action: string;
    entry: NameEntry;
    onChange: (updated: NameEntry) => void;
    onDelete?: () => void;
};

export const NameEntryForm = ({ entry, onChange, onDelete }: EntryProps) => {
    const form = useForm<NameEntry, Partial<NameEntry>>({ mode: 'onBlur', defaultValues: entry });

    const onSubmit = (entered: FieldValues) => {
        onChange({
            ...entry,
            ...entered
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <NameEntryFields />
            </FormProvider>
            <EntryFooter onSave={form.handleSubmit(onSubmit)} onDelete={onDelete} />
        </>
    );
};
