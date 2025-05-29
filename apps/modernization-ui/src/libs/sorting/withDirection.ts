import { ascending } from './ascending';
import { descending } from './descending';
import { Comparator, Direction } from './sorting';

const withDirection = <T>(comparator: Comparator<T>, type?: Direction): Comparator<T> => {
    switch (type) {
        case Direction.Descending:
            return descending(comparator);
        case Direction.Ascending:
            return ascending(comparator);
        default:
            return comparator;
    }
};

export { withDirection };
