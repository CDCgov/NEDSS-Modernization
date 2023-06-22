interface SelectableIndicator {
    value: string;
    name: string;
}

enum Indicator {
    Yes = 'Y',
    No = 'N',
    Unknown = 'UNK'
}

const indicators = [
    { value: Indicator.Yes, name: 'Yes' },
    { value: Indicator.No, name: 'No' },
    { value: Indicator.Unknown, name: 'Unknown' }
];

export { Indicator, indicators };
export type { SelectableIndicator };
