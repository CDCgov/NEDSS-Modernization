type CodedValue = {
    value: string;
    name: string;
};

type GroupedCodedValue = CodedValue & {
    group: string;
};

export type { CodedValue, GroupedCodedValue };
