import { exists } from 'utils/exists';

/**
 * Evaluates the arguments in order and returns the first non null, defined value.
 *
 * @param {I} items The items.
 * @return {I} the first non null, defined value.
 */
const coalesce = <I>(...items: I[]) => {
    for (let index = 0; index < items.length; index++) {
        const item = items[index];

        if (exists(item)) {
            return item;
        }
    }
};

export { coalesce };
