import { EntryFooter } from 'apps/patient/profile/entry';
import { externalizeDateTime } from 'date';
import { FormProvider, useForm } from 'react-hook-form';
import { orNull } from 'utils';
import { IdentificationEntry } from './identification';
import { IdentificationEntryFields } from './IdentificationEntryFields';

type Props = {
    entry: IdentificationEntry;
    onChange: (updated: IdentificationEntry) => void;
    onDelete?: () => void;
};

export const IdentificationEntryForm = ({ entry, onChange, onDelete }: Props) => {
    const form = useForm<IdentificationEntry>({ mode: 'onBlur', defaultValues: entry });

    const onSubmit = (entered: IdentificationEntry) => {
        onChange({
            patient: entry.patient,
            sequence: entry.sequence,
            asOf: externalizeDateTime(entered.asOf),
            type: orNull(entered.type),
            value: orNull(entered.value),
            state: orNull(entered.state)
        });
    };

    return (
        <>
            <FormProvider {...form}>
                <IdentificationEntryFields />
            </FormProvider>
            <EntryFooter onSave={form.handleSubmit(onSubmit)} onDelete={onDelete} />
        </>
    );
};
