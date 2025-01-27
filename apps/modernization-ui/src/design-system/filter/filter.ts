type FilterType = 'text';
type FilterDescriptor = { id: string; type: FilterType };

type Filter = {
    [key: string]: string;
};

export type { Filter, FilterType, FilterDescriptor };
