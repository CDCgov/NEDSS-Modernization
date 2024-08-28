import { EntryFooter } from 'apps/patient/profile/entry';
import { FormProvider, useForm } from 'react-hook-form';
import { RaceEntry } from './RaceEntry';

import { RaceEntryFields } from './RaceEntryFields';

type EntryProps = {
    patient: number;
    entry: Partial<RaceEntry>;
    onChange: (updated: RaceEntry) => void;
    onDelete?: () => void;
    editing?: boolean;
};

export const RaceEntryForm = ({ patient, entry, editing = false, onChange, onDelete }: EntryProps) => {
    const form = useForm<RaceEntry, Partial<RaceEntry>>({ mode: 'onBlur', defaultValues: entry });
    return (
        <>
            <FormProvider {...form}>
                <RaceEntryFields patient={patient} editing={editing} />
            </FormProvider>
            <EntryFooter onSave={form.handleSubmit(onChange)} onDelete={onDelete} />
        </>
    );
};
