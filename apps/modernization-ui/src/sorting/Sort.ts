export type Comparator<T> = (left: T, right: T) => number;

export type ComparatorType = 'numeric' | 'alpha' | 'alphanumeric' | 'date';

export enum Direction {
    None = 'all',
    Ascending = 'asc',
    Descending = 'desc'
}

export const ascending = <T>(comparator: Comparator<T>): Comparator<T> => comparator;

export const descending =
    <T>(comparator: Comparator<T>): Comparator<T> =>
    (left: T, right: T) =>
        -1 * comparator(left, right);

export const withDirection = <T>(c: Comparator<T>, type?: Direction): Comparator<T> => {
    switch (type) {
        case Direction.Descending:
            return descending(c);
        case Direction.Ascending:
            return ascending(c);
        default:
            return c;
    }
};

export const sortData = <T>(
    data: T[],
    comparator: ComparatorType | Comparator<T> | undefined,
    property: keyof T,
    direction: Direction | undefined
): T[] => {
    const comparatorFunc = typeof comparator === 'function' ? comparator : resolveComparator<T>(comparator, property);
    return data.slice().sort(withDirection(comparatorFunc, direction));
};

export const maybeSortData = <T>(
    data: T[],
    comparator: ComparatorType | Comparator<T> | undefined,
    property: keyof T | undefined,
    direction: Direction | undefined
): T[] => {
    if (!property || !data) return data;
    return sortData(data, comparator, property, direction);
};

export const sortBy =
    <T>(property: keyof T): Comparator<T> =>
    (left: T, right: T): number => {
        const value = left[property];
        const comparing = right[property];

        if (value > comparing) {
            return 1;
        } else if (value < comparing) {
            return -1;
        } else {
            return 0;
        }
    };

export const sortByNestedProperty =
    (property: any): Comparator<any> =>
    (left: any, right: any): number => {
        const value: any = left[property] && left[property]['description'];
        const comparing: any = right[property] && right[property]['description'];

        if (value > comparing) {
            return 1;
        } else if (value < comparing) {
            return -1;
        } else {
            return 0;
        }
    };

export const sortByNestedPatientLabReportTestArray =
    (property: any): Comparator<any> =>
    (left: any, right: any): number => {
        const value: any = left[property].length && left[property].map((item: any) => item.test)[0];
        const comparing: any = right[property].length && right[property].map((item: any) => item.test)[0];
        if (value > comparing) {
            return 1;
        } else if (value < comparing) {
            return -1;
        } else if (value === 0) {
            return -1;
        } else if (comparing === 0) {
            return 1;
        } else {
            return 0;
        }
    };

export const sortByNestedPatientLabReportAssociatedArray =
    (property: any): Comparator<any> =>
    (left: any, right: any): number => {
        const value: any = left[property].length && left[property].map((item: any) => item.condition)[0];
        const comparing: any = right[property].length && right[property].map((item: any) => item.condition)[0];
        if (value > comparing) {
            return 1;
        } else if (value < comparing) {
            return -1;
        } else if (value === 0) {
            return -1;
        } else if (comparing === 0) {
            return 1;
        } else {
            return 0;
        }
    };

export const sortByAlpha =
    <T>(property: keyof T): Comparator<T> =>
    (left: T, right: T): number => {
        const value = left[property];
        const comparing = right[property];

        if (typeof value === 'string' && typeof comparing === 'string') {
            return value.localeCompare(comparing);
        }
        return fallbackComparator(value, comparing);
    };

export const sortByAlphanumeric =
    <T>(property: keyof T): Comparator<T> =>
    (left: T, right: T): number => {
        const value = left[property];
        const comparing = right[property];

        if (typeof value === 'string' && typeof comparing === 'string') {
            return value.localeCompare(comparing, undefined, { numeric: true });
        }
        return fallbackComparator(value, comparing);
    };

export const sortByDate =
    <T>(property: keyof T) =>
    (left: T, right: T): number => {
        const value = left[property];
        const comparing = right[property];

        if (value instanceof Date && comparing instanceof Date) {
            return value.getTime() - comparing.getTime();
        }
        return fallbackComparator(value, comparing);
    };

const fallbackComparator = (left: any, right: any): number => {
    if (left && !right) {
        return 1;
    } else if (!left && right) {
        return -1;
    } else {
        return 0;
    }
};

const resolveComparator = <T>(type: ComparatorType | undefined, property: keyof T): Comparator<T> => {
    switch (type) {
        case 'alpha':
            return sortByAlpha<T>(property);
        case 'alphanumeric':
            return sortByAlphanumeric<T>(property);
        case 'date':
            return sortByDate<T>(property);
        default:
            return sortBy<T>(property);
    }
};

export const simpleSort = fallbackComparator;
