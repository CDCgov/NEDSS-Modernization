type Comparator<T> = (left: T, right: T) => number;

enum Direction {
    None = 'all',
    Ascending = 'asc',
    Descending = 'desc'
}

export { Direction };
export type { Comparator };
