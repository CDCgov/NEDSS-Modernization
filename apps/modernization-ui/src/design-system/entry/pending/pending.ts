type PendingEntry = {
    id: string;
    name: string;
    valid: boolean;
};

type HasPendingEntry = {
    pending?: PendingEntry[];
};

export type { PendingEntry, HasPendingEntry };
