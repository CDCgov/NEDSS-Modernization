export type DataElement = {
    name: string;
    label: string;
    active: boolean;
    m: number;
    u: number;
    threshold: number;
};

export type Method = { value: string; name: string }; // Update Method type to be an array of objects with value and name

export type BlockingCriteria = {
    field: DataElement;
    method: Method;
};

export type MatchingCriteria = {
    field: DataElement;
    method: Method;
};
