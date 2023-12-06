type WithName = { name: string };
type WithLabel = { label: string };

type WithDisplay = WithName & WithLabel;

type Selectable = {
    value: string;
    order?: number;
} & WithDisplay;

export type { Selectable };
