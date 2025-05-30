import { Maybe } from 'utils';
import { Comparator } from './sorting';

const nullsLast =
    <V>(comparator: Comparator<V>): Comparator<V> =>
    (value: V | null | undefined, comparing: Maybe<V>) => {
        if (value && comparing) {
            return comparator(value, comparing);
        } else if (value && !comparing) {
            return 1;
        } else if (!value && comparing) {
            return -1;
        } else {
            return 0;
        }
    };

export { nullsLast };
