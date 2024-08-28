type Allowed = {
    value?: string | null;
};

type Restricted = {
    reason: string;
};

type Sensitive = Allowed | Restricted;

export type { Sensitive, Allowed, Restricted };
