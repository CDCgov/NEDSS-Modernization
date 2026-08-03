import { Maybe } from 'utils';
import { Mapping } from 'utils/mapping';

import { Comparator } from './sorting';

const mappingComparator =
    <R, C>(mapping: Mapping<R, Maybe<C>>, comparator: Comparator<Maybe<C>>) =>
    (left: R, right: R) => {
        const value = mapping(left);
        const comparing = mapping(right);

        return comparator(value, comparing);
    };

export { mappingComparator };
