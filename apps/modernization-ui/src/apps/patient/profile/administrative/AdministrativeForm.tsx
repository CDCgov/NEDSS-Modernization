import { EntryFooter } from 'apps/patient/profile/entry';
import { FieldValues, FormProvider, useForm } from 'react-hook-form';
import { AdministrativeEntry } from './AdministrativeEntry';
import { AdministrativeEntryFields } from './AdministrativeEntryFields';

type EntryProps = {
    action: string;
    entry: AdministrativeEntry;
    onChange: (updated: AdministrativeEntry) => void;
    onDelete?: () => void;
};

export const AdministrativeForm = ({ entry, onChange }: EntryProps) => {
    const form = useForm<AdministrativeEntry, Partial<AdministrativeEntry>>({
        mode: 'onBlur',
        defaultValues: entry
    });

    const onSubmit = (entered: FieldValues) => {
        onChange({
            asOf: entered.asOf,
            comment: entered.comment
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <AdministrativeEntryFields />
            </FormProvider>
            <EntryFooter onSave={form.handleSubmit(onSubmit)} />
        </>
    );
};
