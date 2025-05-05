import { Predicate } from './predicate';

const equalsIgnoreCase =
    (criteria: string): Predicate<string> =>
    (value: string) =>
        criteria.toUpperCase() === value.toUpperCase();

export { equalsIgnoreCase };
