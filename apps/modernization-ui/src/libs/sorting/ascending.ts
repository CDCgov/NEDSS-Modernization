import { Comparator } from 'libs/sorting';

const ascending = <T>(comparator: Comparator<T>): Comparator<T> => comparator;

export { ascending };
