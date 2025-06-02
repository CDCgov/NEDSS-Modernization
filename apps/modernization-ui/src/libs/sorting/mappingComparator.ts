import { Mapping } from 'utils/mapping';
import { Comparator } from './sorting';
import { Maybe } from 'utils';

const mappingComparator =
    <R, C>(mapping: Mapping<R, Maybe<C>>, comparator: Comparator<Maybe<C>>) =>
    (left: R, right: R) => {
        const value = mapping(left);
        const comparing = mapping(right);

        return comparator(value, comparing);
    };

export { mappingComparator };
