import { useCallback, useRef, useState } from 'react';
import { useController, UseFormReturn } from 'react-hook-form';
import { HasPendingEntry, PendingEntry } from './pending';

type EntryIdentifier = Pick<PendingEntry, 'id' | 'name'>;

type PendingFormEntrySettings = {
    form: UseFormReturn<HasPendingEntry>;
};

type OnEntry = (entry: EntryIdentifier) => (isPending: boolean) => void;
type OnValid = (entry: EntryIdentifier) => (isValid: boolean) => void;

type PendingFormEntryInteraction = {
    pending: PendingEntry[];
    onEntry: OnEntry;
    onValid: OnValid;
    check: (values: HasPendingEntry) => boolean;
};

const usePendingFormEntry = ({ form }: PendingFormEntrySettings): PendingFormEntryInteraction => {
    const [pending, setPending] = useState<PendingEntry[]>([]);
    const entries = useRef(new Map<string, PendingEntry>());

    const { field } = useController({
        name: 'pending',
        control: form.control,
        defaultValue: [],
        rules: { validate }
    });

    const evaluate = useCallback(() => {
        const values = Array.from(entries.current.values());
        field.onChange(values);
        field.onBlur();

        // if there are pending entries from a check, update the pending list when changes have been made
        setPending((current) => (current.length > 0 ? values : current));
    }, [entries.current, field.onChange, field.onBlur]);

    const onEntry = useCallback(
        (entry: EntryIdentifier) => (isPending: boolean) => {
            if (isPending) {
                entries.current.set(entry.id, { ...entry, valid: true });
            } else {
                entries.current.delete(entry.id);
            }

            evaluate();
        },
        [entries.current, evaluate]
    );

    const onValid = useCallback(
        (entry: EntryIdentifier) => (valid: boolean) => {
            if (entries.current.has(entry.id)) {
                entries.current.set(entry.id, { ...entry, valid });
            }

            evaluate();
        },
        [entries.current, evaluate]
    );

    const check = (values: HasPendingEntry) => {
        const current = values.pending ?? [];

        setPending(current);

        return current.length === 0;
    };

    return { pending, onEntry, onValid, check };
};

const validate = (values?: PendingEntry[]) => {
    if (values) {
        return !values.some((entry) => !entry.valid);
    }

    return true;
};

export { usePendingFormEntry };
export type { PendingFormEntryInteraction };
