interface Indicator {
    value: string;
    name: string;
}

const indicators = [
    { value: 'Y', name: 'Yes' },
    { value: 'N', name: 'No' },
    { value: 'UNK', name: 'Unknown' }
];

export { indicators };
export type { Indicator };
