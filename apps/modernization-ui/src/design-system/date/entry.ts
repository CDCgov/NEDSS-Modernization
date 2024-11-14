type DateEntry = {
    day?: number;
    month?: number;
    year?: number;
};

type DateEqualsCriteria = {
    equals: DateEntry;
};

type DateBetweenCriteria = {
    between: {
        from?: string;
        to?: string;
    };
};

type DateCriteria = DateEqualsCriteria | DateBetweenCriteria;

const isDateEqualsCriteria = (criteria: DateCriteria): criteria is DateEqualsCriteria => 'equals' in criteria;
const isDateBetweenCriteria = (criteria: DateCriteria): criteria is DateBetweenCriteria => 'between' in criteria;

export { isDateEqualsCriteria, isDateBetweenCriteria };
export type { DateCriteria, DateBetweenCriteria, DateEqualsCriteria };

const asDate = (value?: DateEntry) => {
    if (value?.year && value.month && value.day) {
        return new Date(value.year, value.month + 1, value.day);
    }
};

export { asDate };
export type { DateEntry };
