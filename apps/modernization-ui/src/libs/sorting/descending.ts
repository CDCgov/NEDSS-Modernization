import { Comparator } from 'libs/sorting';

const descending =
    <T>(comparator: Comparator<T>): Comparator<T> =>
    (left: T, right: T) =>
        -1 * comparator(left, right);

export { descending };
