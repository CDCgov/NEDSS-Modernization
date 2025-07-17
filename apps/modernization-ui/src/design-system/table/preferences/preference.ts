type LabeledColumnPreference = {
    id: string;
    label: string;
};

type NamedColumnPreference = {
    id: string;
    name: string;
    moveable?: boolean;
    toggleable?: boolean;
    hidden?: boolean;
};

type ColumnPreference = NamedColumnPreference | LabeledColumnPreference;

export type { ColumnPreference, NamedColumnPreference, LabeledColumnPreference };

const isLabeled = (preference: ColumnPreference): preference is LabeledColumnPreference => 'label' in preference;

const isNamed = (preference: ColumnPreference): preference is NamedColumnPreference => 'name' in preference;

export { isLabeled, isNamed };
